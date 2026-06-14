package com.ccscripts.panopticonfive.client;

import com.ccscripts.panopticonfive.model.hiscore.HiscoreReport;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import tools.jackson.databind.ObjectMapper;

@Component
@RequiredArgsConstructor
public class OsrsHiscoreClient implements HiscoreClient {

    private final RestClient restClient;
    final ObjectMapper mapper;

    @Override
    public HiscoreReport lookup(String username) {
        System.out.println("Looking up " + username);
        String resBody = restClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/m=hiscore_oldschool/index_lite.json")
                        .queryParam("player", username)
                        .build())
                .retrieve()
                .body(String.class);

        System.out.println(resBody);
        // fucked up
        HiscoreReport r = mapper.readValue(resBody, HiscoreReport.class);
        r.getSkills().forEach(x -> x.setHiscore(r));
        r.getActivities().forEach(x -> x.setHiscore(r));
        return r;
    }
}