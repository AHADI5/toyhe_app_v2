package com.toyhe.app.Hr.Controllers.Position;

import com.toyhe.app.Hr.Dtos.Requests.PositionRequest;
import com.toyhe.app.Hr.Dtos.Response.PositionResponse;
import com.toyhe.app.Hr.HrService.PositionService;
import com.toyhe.app.Hr.Models.Position;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/v1/position")
public record PositionController(
        PositionService positionService
) {
    @PostMapping("/")
    public ResponseEntity<PositionResponse> createPosition(
            @RequestBody PositionRequest position
    ) {
        PositionResponse  positionResponse = PositionResponse.fromEntity(positionService.createPosition(position)) ;
        return ResponseEntity.ok(positionResponse) ;
    }

    @GetMapping("/")
    public ResponseEntity<List<PositionResponse>> getPosition(){
        List<Position>  positions  = positionService.getAllPositions() ;
        List<PositionResponse> positionResponses  =  new ArrayList<>() ;
        for (Position position : positions) {
            PositionResponse positionResponse = PositionResponse.fromEntity(position) ;
            positionResponses.add(positionResponse) ;
        }
        return  ResponseEntity.ok(positionResponses) ;
    }
}
