package com.ccscripts.panopticonfive.client;

import com.ccscripts.panopticonfive.model.hiscore.HiscoreReport;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
@RequiredArgsConstructor
public class OsrsHiscoreClient implements HiscoreClient {

    private final RestClient restClient;

    @Override
    public HiscoreReport lookup(String username) {
        String csv = restClient.get()
            .uri(uriBuilder -> uriBuilder
                .path("/m=hiscore_oldschool/index_lite.json")
                .queryParam("player", username)
                .build())
            .retrieve()
            .body(String.class);

        return parse(username, csv);
    }

    private HiscoreReport parse(String username, String csv) {
        // parse csv into Skills + Activities
        // build HiscoreReport
        return HiscoreReport.builder()
            .build();
    }
}