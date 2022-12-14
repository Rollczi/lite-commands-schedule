package dev.rollczi.litescheduledaction.scheduled;

import java.time.LocalDate;
import java.time.LocalTime;

public interface ScheduledAction {

    String getName();

    String getRawAction();

    LocalDate getDate();

    LocalTime getTime();

}
