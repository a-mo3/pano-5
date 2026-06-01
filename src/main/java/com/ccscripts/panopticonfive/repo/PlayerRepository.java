package com.ccscripts.panopticonfive.repo;

import com.ccscripts.panopticonfive.model.Player;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PlayerRepository extends JpaRepository<Player, Long> {
    Optional<Player> findByUsername(String username);

    List<Player> findByLastHiscoresLookupIsNullOrLastHiscoresLookupLessThan(Long cutoff);
}