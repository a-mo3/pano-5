package com.ccscripts.panopticonfive.model;

import com.ccscripts.panopticonfive.model.hiscore.HiscoreReport;

public record HiscoreLookupResult(
        Long playerId,
        String username,
        HiscoreReport report,
        Exception error
) {
    public boolean success() {
        return error == null;
    }

    public static HiscoreLookupResult success(
            Long playerId,
            String username,
            HiscoreReport report
    ) {
        return new HiscoreLookupResult(playerId, username, report, null);
    }

    public static HiscoreLookupResult failure(
            Long playerId,
            String username,
            Exception error
    ) {
        return new HiscoreLookupResult(playerId, username, null, error);
    }
}