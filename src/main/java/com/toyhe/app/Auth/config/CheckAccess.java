package com.toyhe.app.Auth.config;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public record CheckAccess(
        JwtService jwtService
) {

    /**
     * Checks if the user has the required access rights for the specified model and operations.
     *
     * @param request            the HTTP request containing the JWT in the "Authorization" header.
     * @param modelName          the model name (e.g., "MODEL_ACCESSRIGHTS").
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

        List<String> authorities = jwtService.getAuthorities(token);

        // Validate the access rights
        if (!hasRequiredAccess(authorities, modelName, requiredOperations)) {
            throw new RuntimeException("Access Denied: Insufficient Permissions");
        }
    }

    /**
     * Validates if the user has the required access rights for the given model and operations.
     *
     * @param authorities        the list of authorities extracted from the JWT token.
     * @param modelName          the model name (e.g., "MODEL_ACCESSRIGHTS").
     * @param requiredOperations a string of required operations separated by underscores (e.g., "READ_WRITE").
     * @return true if the user has all required access rights; false otherwise.
     */
    private boolean hasRequiredAccess(List<String> authorities, String modelName, String requiredOperations) {
        // Extract required operations as a list (e.g., "READ_WRITE" -> ["READ", "WRITE"])
        List<String> requiredOps = Arrays.asList(requiredOperations.split("_"));
        log.info("requiredOps: {}", requiredOps);

        // Slice the list to exclude the first element
        List<String> slicedAuthorities = authorities.subList(1, authorities.size());

// Find the authority entry for the specified model (e.g., "MODEL_ACCESSRIGHTS_READ_WRITE_UPDATE_DELETE")
        String modelAuthority = slicedAuthorities.stream()
                .filter(auth -> auth.startsWith("MODEL" + "_" + modelName + "_"))
                .findFirst()
                .orElse(null);

        log.info("modelAuthority: {}", modelAuthority);


        if (modelAuthority == null) {
            return false; // No authority exists for the model
        }

        // Extract granted operations (e.g., "MODEL_ACCESSRIGHTS_READ_WRITE_UPDATE_DELETE" -> ["READ", "WRITE", "UPDATE", "DELETE"])
        List<String> grantedOps = Arrays.asList(modelAuthority.replace(modelName + "_", "").split("_"));
        log.info("grantedOps: {}", grantedOps);

        // Ensure all required operations are present in the granted operations
        return new HashSet<>(grantedOps).containsAll(requiredOps);
    }
}
