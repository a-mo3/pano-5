package com.ccscripts.panopticonfive;

import com.ccscripts.panopticonfive.client.HiscoreClient;
import com.ccscripts.panopticonfive.model.Player;
import com.ccscripts.panopticonfive.model.PlayerSightingRequest;
import com.ccscripts.panopticonfive.model.hiscore.Activity;
import com.ccscripts.panopticonfive.model.hiscore.HiscoreReport;
import com.ccscripts.panopticonfive.model.hiscore.Skill;
import com.ccscripts.panopticonfive.repo.HiscoreReportRepository;
import com.ccscripts.panopticonfive.repo.PlayerRepository;
import com.ccscripts.panopticonfive.service.HiscoreLookupService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class PlayerHiscoreE2ETest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private HiscoreReportRepository hiscoreReportRepository;

    @Autowired
    private HiscoreLookupService hiscoreLookupService;

    @MockitoBean
    private HiscoreClient hiscoreClient;

    @Test
    void reportingPlayerSightingCreatesPlayerAndHiscoreLookupStoresReport() throws Exception {
        String username = "zezima";

        List<PlayerSightingRequest> request = List.of(
                new PlayerSightingRequest(
                        username,
                        301,
                        1717231000,
                        new int[]{4151, 6585},
                        new int[]{1, 2, 3},
                        12850
                )
        );

        mockMvc.perform(post("/api/player-sightings")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());

        Player player = playerRepository.findByUsername(username)
                .orElseThrow();

        assertThat(player.getLastHiscoresLookup()).isNull();

        HiscoreReport mockReport = HiscoreReport.builder()
                .skills(List.of(
                        Skill.builder()
                                .skillId(0)
                                .name("Attack")
                                .rank(1)
                                .level(99)
                                .xp(200_000_000)
                                .build()
                ))
                .activities(List.of(
                        Activity.builder()
                                .activityId(0)
                                .name("League Points")
                                .rank(10)
                                .score(5000)
                                .build()
                ))
                .build();

        when(hiscoreClient.lookup(username)).thenReturn(mockReport);

        hiscoreLookupService.lookupStalePlayers();

        Player updatedPlayer = playerRepository.findByUsername(username)
                .orElseThrow();

        assertThat(updatedPlayer.getLastHiscoresLookup()).isNotNull();

        List<HiscoreReport> reports = hiscoreReportRepository.findAll();

        assertThat(reports).hasSize(1);

        HiscoreReport savedReport = reports.getFirst();

        assertThat(savedReport.getPlayer().getUsername()).isEqualTo(username);
        assertThat(savedReport.getSkills()).hasSize(1);
        assertThat(savedReport.getActivities()).hasSize(1);

        assertThat(savedReport.getSkills().getFirst().getName()).isEqualTo("Attack");
        assertThat(savedReport.getSkills().getFirst().getLevel()).isEqualTo(99);
        assertThat(savedReport.getActivities().getFirst().getName()).isEqualTo("League Points");
    }
}