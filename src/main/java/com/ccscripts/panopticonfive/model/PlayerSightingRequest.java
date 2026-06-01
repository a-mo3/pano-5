package com.ccscripts.panopticonfive.model;

public record PlayerSightingRequest(
    String username,
    int world,
    int time,
    int[] equipment,
    int[] appearance,
    int region
) {
}