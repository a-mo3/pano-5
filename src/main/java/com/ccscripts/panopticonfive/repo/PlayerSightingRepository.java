package com.ccscripts.panopticonfive.repo;

import com.ccscripts.panopticonfive.model.PlayerSighting;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlayerSightingRepository extends JpaRepository<PlayerSighting, Long> {
}