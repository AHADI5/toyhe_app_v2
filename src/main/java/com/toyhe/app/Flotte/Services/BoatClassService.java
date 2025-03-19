package com.toyhe.app.Flotte.Services;

import com.toyhe.app.Flotte.Dtos.BoatClasses.BoatClassRegisterRequest;
import com.toyhe.app.Flotte.Dtos.BoatClasses.BoatClassResponse;
import com.toyhe.app.Flotte.Models.Boat;
import com.toyhe.app.Flotte.Models.BoatClass;
import com.toyhe.app.Flotte.Repositories.BoatClassRepository;
import com.toyhe.app.Flotte.Repositories.BoatRepository;
import com.toyhe.app.Price.Model.PriceModel;
import com.toyhe.app.Price.Repository.PriceRepository;
import com.toyhe.app.Products.Modal.Products;
import com.toyhe.app.Products.Repository.ProductRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public record BoatClassService(
        BoatClassRepository boatClassRepository,
        BoatRepository boatRepository ,
        PriceRepository priceRepository ,
        ProductRepository  productRepository
) {
    public ResponseEntity<List<BoatClass>> registerBoatClass(List<BoatClassRegisterRequest> requests) {
        List<BoatClass> boatClasses = new ArrayList<>();
        List<Products> products = new ArrayList<>();

        for (BoatClassRegisterRequest boatClassRegisterRequest : requests) {
            // Find the boat corresponding to the ID
            Optional<Boat> optionalBoat = boatRepository.findById(boatClassRegisterRequest.boatID());
            //Finding products if exists
            for(int productId  : boatClassRegisterRequest.productID()) {
                Products product = productRepository.findById((long) productId).get() ;
                products.add(product);
            }

            if (optionalBoat.isEmpty()) {
                // If the boat does not exist, return an error response
                return ResponseEntity.badRequest().body(
                        List.of()
                );
            }
            PriceModel priceModel = getPrice(boatClassRegisterRequest.priceListID());

            // Create and add a new BoatClass object
            BoatClass boatClass = BoatClass.builder()
                    .priceModel(priceModel)
                    .boat(optionalBoat.get())
                    .name(boatClassRegisterRequest.name())
                    .products(products)
                    .placesNumber(boatClassRegisterRequest.placesNumber())
                    .build();

            boatClasses.add(boatClass);
        }

        // Save all BoatClass objects
        List<BoatClass> savedBoatClasses = boatClassRepository.saveAll(boatClasses);

        // Return the list of registered BoatClass objects
        return ResponseEntity.ok(savedBoatClasses);
    }

    public ResponseEntity<List<BoatClassResponse>> getBoatClasses(long boatID) {
        Boat boat = boatRepository.findById(boatID).orElse(null);
        assert boat != null;
        List<BoatClass> boatClasses = boat.getBoatClasses();
        List<BoatClassResponse> boatClassResponses = new ArrayList<>();
        for (BoatClass boatClass : boatClasses) {
            boatClassResponses.add(BoatClassResponse.fromBoatClassToDTO(boatClass));
        }
        return ResponseEntity.ok(boatClassResponses);
    }

    public ResponseEntity<BoatClassResponse> addBoatClassToABoat(long boatID , BoatClassRegisterRequest boatClassRegisterRequest) {
        Boat boat = boatRepository.findById(boatID).orElse(null);
        assert boat != null;
        BoatClass boatClass = BoatClass.builder()
                .name(boatClassRegisterRequest.name())
                .placesNumber(boatClassRegisterRequest.placesNumber())
                .boat(boat)
                .priceModel(getPrice(boatClassRegisterRequest.priceListID()))
                .build();
        BoatClass savedBoatClass  = boatClassRepository.save(boatClass);
        return ResponseEntity.ok(BoatClassResponse.fromBoatClassToDTO(savedBoatClass));

    }

    public ResponseEntity<BoatClassResponse> updateClassBoat(
            long boatClassID,
            BoatClassRegisterRequest boatClassRegisterRequest) {
        BoatClass existingBoatClass = boatClassRepository.findById(boatClassID).orElse(null);
        assert existingBoatClass != null;
        existingBoatClass.setName(boatClassRegisterRequest.name());
        existingBoatClass.setPlacesNumber(boatClassRegisterRequest.placesNumber());
         existingBoatClass.setPriceModel(getPrice(boatClassRegisterRequest.priceListID()));
        existingBoatClass = boatClassRepository.save(existingBoatClass);

        return ResponseEntity.ok(BoatClassResponse.fromBoatClassToDTO(existingBoatClass));
    }

    public void updateClassSeat(BoatClass boatClass) {
        boatClass.setPlacesNumber(boatClass.getPlacesNumber() - 1);
        boatClassRepository.save(boatClass);
    }

    public PriceModel getPrice(long priceID) {
        return  priceRepository.findById(priceID).orElse(null);
    }
}
