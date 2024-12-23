package com.toyhe.app.Auth.Services;

import com.toyhe.app.Auth.Dtos.Requests.ModelRequest;
import com.toyhe.app.Auth.Model.Model;
import com.toyhe.app.Auth.Repositories.ModelRespository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.metamodel.EntityType;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Optional;
import java.util.Set;

@Service
public class ModelService {

    private final ModelRespository modelRespository;
    private final EntityManager entityManager;

    public ModelService(ModelRespository modelRespository, EntityManager entityManager) {
        this.modelRespository = modelRespository;
        this.entityManager = entityManager;
    }

    /**
     * Automatically registers all entities as models at application startup.
     */
    @PostConstruct
    public void initializeModels() {
        Set<EntityType<?>> entities = entityManager.getMetamodel().getEntities();

        for (EntityType<?> entity : entities) {
            String entityName = entity.getName();

            // Check if the model is already registered
            Optional<Model> existingModel = modelRespository.findByModelName(entityName);
            if (existingModel.isEmpty()) {
                Model model = Model.builder()
                        .modelName(entityName)
                        .description("Auto-registered model for entity: " + entityName)
                        .build();
                modelRespository.save(model);
                System.out.println("Registered new model: " + entityName);
            } else {
                System.out.println("Model already registered: " + entityName);
            }
        }
    }

    /**
     * Registers a new model in the database.
     *
     * @param modelRequest the details of the model to register.
     * @return the newly created model.
     */
    public Model registerNewModel(ModelRequest modelRequest) {
        Model model = Model.builder()
                .modelName(modelRequest.modelName())
                .description(modelRequest.description())
                .build();

        return modelRespository.save(model);
    }

    /**
     * Updates an existing model in the database.
     *
     * @param modelRequest the new details to update the model with.
     * @param modelId      the ID of the model to update.
     * @return the updated model, or throws an exception if the model is not found.
     */
    public Model updateModel(ModelRequest modelRequest, long modelId) {
        Model model = modelRespository.findById(modelId)
                .orElseThrow(() -> new IllegalArgumentException("Model with ID " + modelId + " not found."));

        model.setModelName(modelRequest.modelName());
        model.setDescription(modelRequest.description());

        return modelRespository.save(model);
    }

    /**
     * Deletes a model from the database.
     *
     * @param modelId the ID of the model to delete.
     * @return a ResponseEntity indicating the success or failure of the operation.
     */
    public ResponseEntity<String> deleteModel(long modelId) {
        Optional<Model> modelOptional = modelRespository.findById(modelId);

        if (modelOptional.isPresent()) {
            Model model = modelOptional.get();
            modelRespository.delete(model);
            return ResponseEntity.ok("Model " + model.getModelName() + " deleted successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Model with ID " + modelId + " not found.");
        }
    }
}
