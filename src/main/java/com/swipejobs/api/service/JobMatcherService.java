package com.swipejobs.api.service;

import com.swipejobs.api.exception.WorkerNotFoundException;
import com.swipejobs.api.dto.jobs.Job;
import com.swipejobs.api.dto.jobs.JobResponse;
import com.swipejobs.api.dto.workers.Worker;
import com.swipejobs.api.dto.workers.WorkersResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.HashSet;
import java.util.stream.Collectors;

@Service
public class JobMatcherService {
    private final SwipejobsService service;

    @Autowired
    public JobMatcherService(SwipejobsService service) {
        this.service = service;
    }

    public JobResponse fetchMatchingJobs(int userId) {
        WorkersResponse workerList = service.getWorkersResponse();
        Worker worker = findWorkerById(workerList, userId);

        JobResponse jobsList = service.getJobsResponse();

        return matchingJobs(worker, jobsList);
    }

    private Worker findWorkerById(WorkersResponse workers, int userId) {
        return workers.stream()
                .filter(worker -> worker.userId() == userId)
                .findFirst()
                .orElseThrow(() -> new WorkerNotFoundException(userId));
    }

    private JobResponse matchingJobs(Worker worker, JobResponse jobs) {
        // mandatory workersRequired, driversLicense (only if the job requires it), certificates all match, is within their job search distance
        // secondary is sorting by the money for the job
        return jobs.stream()
                .filter(job -> job.workersRequired() > 0)
                .filter(job -> !(job.driverLicenseRequired() && !worker.hasDriversLicense()))
                .filter(job -> new HashSet<>(worker.certificates()).containsAll(job.requiredCertificates()))
                .filter(job -> isWithinMaxDistance(worker, job))
                .sorted(Comparator.comparing(Job::billRate).reversed())
                .limit(3)
                .collect(Collectors.toCollection(JobResponse::new));
    }

    private boolean isWithinMaxDistance(Worker worker, Job job) {
        double workerLatitude = Double.parseDouble(worker.jobSearchAddress().latitude());
        double workerLongitude = Double.parseDouble(worker.jobSearchAddress().longitude());
        int maxJobDistance = worker.jobSearchAddress().maxJobDistance();

        double jobLatitude = Double.parseDouble(job.location().latitude());
        double jobLongitude = Double.parseDouble(job.location().longitude());

        // Calculate distance between worker and job
        double distance = calculateDistance(workerLatitude, workerLongitude, jobLatitude, jobLongitude);

        return distance <= maxJobDistance;
    }

    /* I DID NOT WRITE THIS METHOD - CHATGPT DID */
    private double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        double earthRadius = 6371; // Radius of the Earth in kilometers
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                        Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return earthRadius * c;
    }
}
