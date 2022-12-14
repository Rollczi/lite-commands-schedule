package dev.rollczi.litescheduledaction.scheduler;

public interface Task {

    void cancel();

    boolean isCanceled();

    boolean isAsync();

}
