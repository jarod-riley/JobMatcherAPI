package com.swipejobs.api.dto.jobs;

import java.time.Instant;
import java.util.List;

public record Job(
        boolean driverLicenseRequired,
        List<String> requiredCertificates,
        Location location,
        String billRate,
        int workersRequired,
        Instant startDate,
        String about,
        String jobTitle,
        String company,
        String guid,
        int jobId
) {}

