package com.ccscripts.panopticonfive.model;

import com.ccscripts.panopticonfive.model.hiscore.HiscoreReport;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Player {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String username;

    // used to enforce a min time between lookups
    @Column()
    private Long lastHiscoresLookup;

    @OneToMany(
            mappedBy = "player",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @Builder.Default
    private List<HiscoreReport> reports = new ArrayList<>();

}