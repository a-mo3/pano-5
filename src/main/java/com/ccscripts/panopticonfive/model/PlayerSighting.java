package com.ccscripts.panopticonfive.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "player_sightings")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class PlayerSighting {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int world;
    private int time;
    private int region;

    @Lob
    private int[] equipment;

    @Lob
    private int[] appearance;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "player_id", nullable = false)
    private Player player;
}