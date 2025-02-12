package com.toyhe.app.Flotte.Dtos.Boat;

import com.toyhe.app.Flotte.Models.Boat;

public record BoatRegisterResponse(
        long boatID  ,
        String boatName  ,
        String abbreviation
) {

    public static BoatRegisterResponse fromBoatToResponseDTO  (Boat boat) {

        return new BoatRegisterResponse(
                boat.getBoatID(),
                boat.getName() ,
                boat.getAbbreviation()
        ) ;
    }


}
