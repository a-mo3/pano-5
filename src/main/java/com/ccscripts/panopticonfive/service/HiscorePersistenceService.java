package com.ccscripts.panopticonfive.service;

import com.ccscripts.panopticonfive.model.HiscoreLookupResult;
import com.ccscripts.panopticonfive.model.Player;
import com.ccscripts.panopticonfive.model.hiscore.HiscoreReport;
import com.ccscripts.panopticonfive.model.hiscore.HiscoreResponseType;
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
            System.out.println(result);
            // we want to save failure because it will indicate bans.
//            if (!result.success()) {
//                continue;
//            }

            Player player = playerRepository.findById(result.playerId())
                    .orElseThrow();

            HiscoreReport report = result.report();
            // null if failed lookup (maybe from ban, maybe from bad connection)
            if (report != null) {
                report.assignPlayer(player);
                hiscoreReportRepository.save(report);
            } else {
                System.out.println("record a failure " + player);
                HiscoreReport failure = HiscoreReport.builder()
                        .player(player)
                        .name(player.getUsername())
                        .status(HiscoreResponseType.FAILURE)
                        .build();
                hiscoreReportRepository.save(failure);
            }

            player.markLookupComplete(lookupTimestamp);
        }
    }
}