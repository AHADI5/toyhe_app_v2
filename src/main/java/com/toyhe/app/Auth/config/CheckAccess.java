package com.toyhe.app.Auth.config;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
public record CheckAccess(JwtService jwtService) {

    /**
     * Checks if the user has the required access rights for the specified model and operations.
     *
     * @param request            the HTTP request containing the JWT in the "Authorization" header.
     * @param modelName          the model name (e.g., "CUSTOMER").
     * @param requiredOperations a string of required operations separated by underscores (e.g., "READ_WRITE").
     */
    public void checkAccess(HttpServletRequest request, String modelName, String requiredOperations) {
        // Extract the Authorization header (e.g., "Bearer <token>")
        String authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            throw new RuntimeException("Access Denied: Missing or Invalid Token");
        }

        // Extract the JWT token from the Authorization header
        String token = authorizationHeader.substring(7);

        // Get all authorities from the JWT token (both ROLE_ and MODEL_ authorities)
        List<String> authorities = jwtService.getAuthorities(token);
        log.info("Extracted authorities: {}", authorities);

        // Validate the access rights based on the union of all authorities for the model
        if (!hasRequiredAccess(authorities, modelName, requiredOperations)) {
            throw new RuntimeException("Access Denied: Insufficient Permissions");
        }
    }

    /**
     * Validates if the user has the required access rights for the given model and operations.
     *
     * This method finds all authorities for the specified model, extracts and groups the granted operations,
     * and then checks whether the union of these operations includes all required operations.
     *
     * @param authorities        the list of authorities extracted from the JWT token.
     * @param modelName          the model name (e.g., "CUSTOMER").
     * @param requiredOperations a string of required operations separated by underscores (e.g., "READ_WRITE").
     * @return true if the union of granted operations includes all required operations; false otherwise.
     */
    private boolean hasRequiredAccess(List<String> authorities, String modelName, String requiredOperations) {
        // Parse the required operations (e.g., "READ_WRITE" -> ["READ", "WRITE"])
        List<String> requiredOps = Arrays.asList(requiredOperations.split("_"));
        log.info("Required operations: {}", requiredOps);

        // Define the prefix pattern for authorities related to this model (e.g., "MODEL_CUSTOMER_")
        String prefix = "MODEL_" + modelName + "_";

        // Group all authorities for this model, extract their operations, and take the union
        Set<String> grantedOps = authorities.stream()
                .filter(auth -> auth.startsWith(prefix))
                .map(auth -> auth.substring(prefix.length()))
                .flatMap(ops -> Arrays.stream(ops.split("_")))
                .collect(Collectors.toSet());

        log.info("Granted operations for model {}: {}", modelName, grantedOps);

        // Check if all required operations are present in the union of granted operations
        return grantedOps.containsAll(requiredOps);
    }
}
