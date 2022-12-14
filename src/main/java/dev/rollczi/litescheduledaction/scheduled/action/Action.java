package dev.rollczi.litescheduledaction.scheduled.action;

public interface Action {

    void execute();

    @Override
    String toString();

}
