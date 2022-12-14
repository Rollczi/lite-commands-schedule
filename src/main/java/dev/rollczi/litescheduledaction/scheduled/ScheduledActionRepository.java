package dev.rollczi.litescheduledaction.scheduled;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface ScheduledActionRepository {

    ScheduledAction createOrUpdate(String name, String action, LocalDate date, LocalTime time);

    void delete(ScheduledAction scheduleAction);

    void delete(String name);

    List<ScheduledAction> findAll();

}
