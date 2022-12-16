package dev.rollczi.litescheduledaction.scheduled;

import dev.rollczi.litescheduledaction.scheduled.action.Action;
import dev.rollczi.litescheduledaction.scheduled.action.ActionParserService;
import dev.rollczi.litescheduledaction.scheduler.Scheduler;
import panda.std.Result;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ScheduledActionManager {

    private static final Logger LOGGER = Logger.getLogger("Action scheduler");

    private static final Duration REFRESH_DELAY = Duration.ofSeconds(15);
    private static final Duration SCHEDULE_BEFORE = Duration.ofSeconds(30);

    private final ScheduledActionRepository scheduledActionRepository;
    private final ActionParserService parserService;
    private final Scheduler scheduler;

    private ScheduledActionManager(ScheduledActionRepository scheduledActionRepository, ActionParserService parserService, Scheduler scheduler) {
        this.scheduledActionRepository = scheduledActionRepository;
        this.parserService = parserService;
        this.scheduler = scheduler;
    }

    private void refresh() {
        for (ScheduledAction scheduledAction : scheduledActionRepository.findAll()) {
            LocalDateTime scheduleMoment = scheduledAction.getDate().atTime(scheduledAction.getTime());
            LocalDateTime now = LocalDateTime.now();

            LocalDateTime scheduleBeforeMoment = scheduleMoment.minus(SCHEDULE_BEFORE);

            if (now.isAfter(scheduleBeforeMoment) || now.isEqual(scheduleBeforeMoment)) {
                if (now.isBefore(scheduleMoment)) {
                    continue;
                }

                Result<Action, String> result = this.parserService.parse(scheduledAction.getRawAction());

                if (result.isErr()) {
                    LOGGER.log(Level.SEVERE, result.getError(), new RuntimeException("Can not schedule action, because can not parse " + scheduledAction.getRawAction()));
                    return;
                }

                Action action = result.get();
                Duration delay = Duration.between(now, scheduleMoment);

                scheduler.laterSync(action::execute, delay);
            }
        }

        scheduler.laterSync(this::refresh, REFRESH_DELAY);
    }

    public static ScheduledActionManager create(ScheduledActionRepository scheduledActionRepository, ActionParserService parserService, Scheduler scheduler) {
        ScheduledActionManager scheduledActionService = new ScheduledActionManager(scheduledActionRepository, parserService, scheduler);

        scheduledActionService.refresh();
        return scheduledActionService;
    }

}
