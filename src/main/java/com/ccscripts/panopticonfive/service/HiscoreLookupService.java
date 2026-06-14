package com.ccscripts.panopticonfive.service;

import com.ccscripts.panopticonfive.client.HiscoreClient;
import com.ccscripts.panopticonfive.model.HiscoreLookupResult;
import com.ccscripts.panopticonfive.model.Player;
import com.ccscripts.panopticonfive.model.hiscore.HiscoreReport;
import com.ccscripts.panopticonfive.repo.PlayerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;

@Service
@RequiredArgsConstructor
@EnableScheduling
public class HiscoreLookupService {
    private static final long FIFTEEN_MINUTES_MILLIS = 15 * 60 * 1000L;

    private final PlayerRepository playerRepository;
    private final HiscoreClient hiscoreClient;
    private final ExecutorService hiscoreExecutorService;
    private final HiscorePersistenceService persistenceService;

    @Scheduled(fixedRate = 15_000)
    public void lookupStalePlayers() {
        System.out.println("Players lookup");
        long now = Instant.now().toEpochMilli();
        long cutoff = now - FIFTEEN_MINUTES_MILLIS;

        List<Player> stalePlayers = playerRepository.findByLastHiscoresLookupIsNullOrLastHiscoresLookupLessThan(cutoff);

        List<CompletableFuture<HiscoreLookupResult>> futures = stalePlayers.stream()
            .map(player -> CompletableFuture.supplyAsync(
                () -> lookupPlayer(player.getId(), player.getUsername()),
                hiscoreExecutorService
            ))
            .toList();

        List<HiscoreLookupResult> results = futures.stream()
            .map(CompletableFuture::join)
            .toList();

        persistenceService.saveSuccessfulResults(results);
    }

    private HiscoreLookupResult lookupPlayer(Long playerId, String username) {
        try {
            HiscoreReport report = hiscoreClient.lookup(username);
            return HiscoreLookupResult.success(playerId, username, report);
        } catch (Exception e) {
            return HiscoreLookupResult.failure(playerId, username, e);
        }
    }
}