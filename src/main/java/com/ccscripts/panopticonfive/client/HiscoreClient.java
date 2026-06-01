package com.ccscripts.panopticonfive.client;

import com.ccscripts.panopticonfive.model.hiscore.HiscoreReport;

public interface HiscoreClient {
    HiscoreReport lookup(String username);
}