package com.ccscripts.panopticonfive;

import com.ccscripts.panopticonfive.controller.PlayerSightingController;
import com.ccscripts.panopticonfive.model.PlayerSightingRequest;
import com.ccscripts.panopticonfive.service.PlayerSightingService;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PlayerSightingController.class)
class PlayerSightingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private PlayerSightingService playerSightingService;

    @Test
    void reportSightings_acceptsArrayOfSightings() throws Exception {
        List<PlayerSightingRequest> request = List.of(
            new PlayerSightingRequest(
                "zezima",
                301,
                1717231000,
                new int[]{4151, 6585},
                new int[]{1, 2, 3},
                12850
            ),
            new PlayerSightingRequest(
                "lynx titan",
                302,
                1717231010,
                new int[]{11840},
                new int[]{4, 5, 6},
                12851
            )
        );

        mockMvc.perform(post("/api/player-sightings")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isCreated());

        ArgumentCaptor<List<PlayerSightingRequest>> captor =
            ArgumentCaptor.forClass(List.class);

        verify(playerSightingService).reportSightings(captor.capture());

        List<PlayerSightingRequest> captured = captor.getValue();

        assertThat(captured).hasSize(2);

        assertThat(captured.get(0).username()).isEqualTo("zezima");
        assertThat(captured.get(0).world()).isEqualTo(301);
        assertThat(captured.get(0).time()).isEqualTo(1717231000);
        assertThat(captured.get(0).equipment()).containsExactly(4151, 6585);
        assertThat(captured.get(0).appearance()).containsExactly(1, 2, 3);
        assertThat(captured.get(0).region()).isEqualTo(12850);

        assertThat(captured.get(1).username()).isEqualTo("lynx titan");
        assertThat(captured.get(1).world()).isEqualTo(302);
        assertThat(captured.get(1).equipment()).containsExactly(11840);
    }
}