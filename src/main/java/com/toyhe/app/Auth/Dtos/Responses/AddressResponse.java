package com.toyhe.app.Auth.Dtos.Responses;


import com.toyhe.app.Auth.Model.Address;

public record AddressResponse(
        Long id,
        String province,
        String town,
        String commune,
        String quarter,
        String avenue
) {
    //  Convert Entity to Response DTO
    public static  AddressResponse fromEntity(Address address) {
        return new AddressResponse(
                address.getId(),
                address.getProvince(),
                address.getTown(),
                address.getCommune(),
                address.getQuarter(),
                address.getAvenue()
        );
    }
}
