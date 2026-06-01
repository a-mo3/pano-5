package com.ccscripts.panopticonfive.model;

public record CreatePlayerSightingRequest(
    String username,
    int world,
    int time,
    int[] equipment,
    int[] appearance,
    int region
) {}