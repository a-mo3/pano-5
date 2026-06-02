package com.ccscripts.panopticonfive.service;

import com.ccscripts.panopticonfive.model.hiscore.HiscoreReport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

@Service
public class HiscoreResponseParser {

    private final ObjectMapper mapper;

    public HiscoreResponseParser(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    public HiscoreReport parse(String response) {
        return mapper.readValue(response, HiscoreReport.class);
    }
}