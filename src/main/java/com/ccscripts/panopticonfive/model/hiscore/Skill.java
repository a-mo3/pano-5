package com.ccscripts.panopticonfive.model.hiscore;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "skills")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@ToString
public class Skill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int skillId;

    @Column(nullable = false)
    private String name;

    private int rank;

    private int level;

    private int xp;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hiscore_id", nullable = false)
    private HiscoreReport hiscore;
}