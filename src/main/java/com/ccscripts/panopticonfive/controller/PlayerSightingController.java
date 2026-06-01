package com.ccscripts.panopticonfive.controller;

import com.ccscripts.panopticonfive.model.PlayerSightingRequest;
import com.ccscripts.panopticonfive.service.PlayerSightingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/player-sightings")
@RequiredArgsConstructor
public class PlayerSightingController {
    private final PlayerSightingService playerSightingService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void reportSightings(@RequestBody List<PlayerSightingRequest> sightings) {
        playerSightingService.reportSightings(sightings);
    }
}