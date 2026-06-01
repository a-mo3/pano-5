package com.ccscripts.panopticonfive.service;

import com.ccscripts.panopticonfive.model.HiscoreLookupResult;
import com.ccscripts.panopticonfive.model.Player;
import com.ccscripts.panopticonfive.model.hiscore.HiscoreReport;
import com.ccscripts.panopticonfive.repo.HiscoreReportRepository;
import com.ccscripts.panopticonfive.repo.PlayerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HiscorePersistenceService {

    private final PlayerRepository playerRepository;
    private final HiscoreReportRepository hiscoreReportRepository;

    @Transactional
    public void saveSuccessfulResults(List<HiscoreLookupResult> results) {
        long lookupTimestamp = Instant.now().toEpochMilli();
        for (HiscoreLookupResult result : results) {
            if (!result.success()) {
                continue;
            }

            Player player = playerRepository.findById(result.playerId())
                    .orElseThrow();

            HiscoreReport report = result.report();
            report.assignPlayer(player);

            hiscoreReportRepository.save(report);

            player.markLookupComplete(lookupTimestamp);
        }
    }
}