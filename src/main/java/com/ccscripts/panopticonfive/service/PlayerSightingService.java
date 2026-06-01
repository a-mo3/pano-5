package com.ccscripts.panopticonfive.service;

import com.ccscripts.panopticonfive.model.Player;
import com.ccscripts.panopticonfive.model.PlayerSighting;
import com.ccscripts.panopticonfive.model.PlayerSightingRequest;
import com.ccscripts.panopticonfive.repo.PlayerRepository;
import com.ccscripts.panopticonfive.repo.PlayerSightingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PlayerSightingService {

    private final PlayerRepository playerRepository;
    private final PlayerSightingRepository playerSightingRepository;

    @Transactional
    public void reportSightings(List<PlayerSightingRequest> sightings) {
        for (PlayerSightingRequest request : sightings) {
            Player player = playerRepository.findByUsername(request.username())
                .orElseGet(() -> playerRepository.save(
                    Player.builder()
                        .username(request.username())
                        .build()
                ));

            PlayerSighting sighting = PlayerSighting.builder()
                .player(player)
                .world(request.world())
                .time(request.time())
                .region(request.region())
                .equipment(request.equipment())
                .appearance(request.appearance())
                .build();

            playerSightingRepository.save(sighting);
        }
    }
}