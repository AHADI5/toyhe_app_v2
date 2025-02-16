package com.toyhe.app.Hr.HrService;

import com.toyhe.app.Hr.Dtos.Requests.PositionRequest;
import com.toyhe.app.Hr.Models.Position;
import com.toyhe.app.Hr.Repository.PositionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public record PositionService(
        PositionRepository positionRepository
) {
    public Position  createPosition(PositionRequest positionRequest) {
        Position position = Position.builder()
                .positionName(positionRequest.positionName())
                .description(positionRequest.description())
                .build();
        return positionRepository.save(position);
    }
    public Position getPosition(long positionId) {
        return  positionRepository.findById(positionId).orElse(null);
    }

    public List<Position> getAllPositions() {
        return positionRepository.findAll();
    }
}
