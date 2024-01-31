package com.swipejobs.api.service;

import com.swipejobs.api.dto.jobs.JobResponse;
import com.swipejobs.api.dto.workers.WorkersResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class SwipejobsService {
    @Value("${client.swipejobs.url}")
    private String URL;

    private final String GET_JOBS_PATH = "/jobs";
    private final String GET_WORKERS_PATH = "/workers";

    private final RestTemplate restTemplate;

    @Autowired
    public SwipejobsService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public JobResponse getJobsResponse() {
        return restTemplate.getForObject(URL + GET_JOBS_PATH, JobResponse.class);
    }

    public WorkersResponse getWorkersResponse() {
        return restTemplate.getForObject(URL + GET_WORKERS_PATH, WorkersResponse.class);
    }
}
