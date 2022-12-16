package dev.rollczi.litescheduledaction;

import dev.rollczi.litescheduledaction.config.Reloadable;
import dev.rollczi.litescheduledaction.scheduled.ScheduledAction;
import dev.rollczi.litescheduledaction.scheduled.ScheduledActionRepository;
import dev.rollczi.litescheduledaction.scheduled.action.nether.ActionTeleportRepository;
import net.dzikoysk.cdn.entity.Contextual;
import net.dzikoysk.cdn.source.Resource;
import net.dzikoysk.cdn.source.Source;
import org.bukkit.event.player.PlayerTeleportEvent;

import java.io.File;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class LiteScheduledActionSettings implements Reloadable, ActionTeleportRepository, ScheduledActionRepository {

    String reloadMessage = "Configuration has been reloaded!";

    Map<String, ScheduledAction> scheduledActions = new HashMap<>();
    Map<PlayerTeleportEvent.TeleportCause, Boolean> teleportCauses = new HashMap<>();

    @Override
    public Resource resource(File folder) {
        return Source.of(folder, "config.yml");
    }

    @Override
    public boolean isTeleportEnabled(PlayerTeleportEvent.TeleportCause cause) {
        return teleportCauses.getOrDefault(cause, true);
    }

    @Override
    public void setTeleportEnabled(PlayerTeleportEvent.TeleportCause cause, boolean enabled) {
        teleportCauses.put(cause, enabled);
    }

    @Override
    public ScheduledAction createOrUpdate(String name, String action, LocalDate date, LocalTime time) {
        ScheduledActionSection scheduledActionSection = new ScheduledActionSection(name, action, date, time);

        Map<String, ScheduledAction> newScheduledActions = new HashMap<>(scheduledActions);
        newScheduledActions.put(name, scheduledActionSection);
        scheduledActions = newScheduledActions;

        return scheduledActionSection;
    }

    @Override
    public void delete(ScheduledAction scheduleAction) {
        scheduledActions.remove(scheduleAction.getName());
    }

    @Override
    public void delete(String name) {
        scheduledActions.remove(name);
    }

    @Override
    public List<ScheduledAction> findAll() {
        return List.copyOf(scheduledActions.values());
    }

    @Contextual
    static class ScheduledActionSection implements ScheduledAction {

        String name = "example";
        String rawAction = "teleport:END_PORTAL:true";
        LocalDate date = LocalDate.now();
        LocalTime time = LocalTime.now();

        public ScheduledActionSection() {
        }

        public ScheduledActionSection(String name, String action, LocalDate date, LocalTime time) {
            this.name = name;
            this.rawAction = action;
            this.date = date;
            this.time = time;
        }

        @Override
        public String getName() {
            return name;
        }

        @Override
        public String getRawAction() {
            return rawAction;
        }

        @Override
        public LocalDate getDate() {
            return date;
        }

        @Override
        public LocalTime getTime() {
            return time;
        }

    }

}
