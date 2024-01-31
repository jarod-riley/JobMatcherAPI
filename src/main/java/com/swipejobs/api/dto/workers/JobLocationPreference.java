package com.swipejobs.api.dto.workers;

public record JobLocationPreference(
        String unit,
        int maxJobDistance,
        String longitude,
        String latitude
) {}
