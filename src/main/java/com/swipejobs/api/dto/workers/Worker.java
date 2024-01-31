package com.swipejobs.api.dto.workers;

import java.util.List;

public record Worker(
        int rating,
        boolean isActive,
        List<String> certificates,
        List<String> skills,
        JobLocationPreference jobSearchAddress,
        String transportation,
        boolean hasDriversLicense,
        List<Availability> availability,
        String phone,
        String email,
        Name name,
        int age,
        String guid,
        int userId
) {}

