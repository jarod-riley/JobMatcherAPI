package com.swipejobs.api.exception;

public class WorkerNotFoundException extends RuntimeException {
    public WorkerNotFoundException(int userId) {
        super("Worker with userId " + userId + " not found");
    }
}
