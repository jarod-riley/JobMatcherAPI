package com.swipejobs.api.controller;

import com.swipejobs.api.dto.jobs.JobResponse;
import com.swipejobs.api.service.JobMatcherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class MatchJobsController {
    @Autowired
    JobMatcherService service;

    @GetMapping("/findJobs")
    public JobResponse findJobs(@RequestParam("userId") int userId) {
        return service.fetchMatchingJobs(userId);
    }
}