package com.ccscripts.panopticonfive.model.hiscore;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Table(name = "activities")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@ToString
public class Activity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long tableId;

    // runescape hiscores id
    private int id;

    private int activityId;

    @Column(nullable = false)
    private String name;

    private long rank;

    private long score;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hiscore_id", nullable = false)
    @JsonIgnore
    @Setter
    @ToString.Exclude
    private HiscoreReport hiscore;
}