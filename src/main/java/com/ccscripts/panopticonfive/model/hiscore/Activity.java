package com.ccscripts.panopticonfive.model.hiscore;

import jakarta.persistence.*;
import lombok.*;

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
    private Long id;

    private int activityId;

    @Column(nullable = false)
    private String name;

    private int rank;

    private int score;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hiscore_id", nullable = false)
    private HiscoreReport hiscore;
}