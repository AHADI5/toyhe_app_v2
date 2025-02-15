package com.toyhe.app.Hr.HrService;

import com.toyhe.app.Hr.Models.Position;
import com.toyhe.app.Hr.Repository.PositionRepository;
import org.springframework.stereotype.Service;

@Service
public record PositionService(
        PositionRepository positionRepository
) {
    public Position getPosition(long positionId) {
        return  positionRepository.findById(positionId).orElse(null);
    }
}
