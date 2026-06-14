package com.ccscripts.panopticonfive.model.hiscore;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

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
    @JsonIgnore
    private Long tableId;

    // runescape id
    private int id;

    private int skillId;

    @Column(nullable = false)
    private String name;

    private long rank;

    private long level;

    private long xp;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hiscore_id", nullable = false)
    @JsonIgnore
    @Setter
    @ToString.Exclude
    private HiscoreReport hiscore;
}