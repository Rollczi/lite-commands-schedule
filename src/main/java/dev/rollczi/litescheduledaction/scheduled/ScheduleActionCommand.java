package dev.rollczi.litescheduledaction.scheduled;


import dev.rollczi.litecommands.argument.Arg;
import dev.rollczi.litecommands.argument.Name;
import dev.rollczi.litecommands.command.execute.Execute;
import dev.rollczi.litecommands.command.permission.Permission;
import dev.rollczi.litecommands.command.route.Route;
import dev.rollczi.litescheduledaction.scheduled.action.Action;
import dev.rollczi.litescheduledaction.scheduled.action.ActionParserService;
import dev.rollczi.litescheduledaction.scheduled.action.command.ActionCommandParser;
import dev.rollczi.litescheduledaction.scheduled.action.nether.ActionTeleportParser;
import org.bukkit.event.player.PlayerTeleportEvent;
import panda.std.Result;

import java.time.LocalDate;
import java.time.LocalTime;

@Route(name = "schedule-action")
@Permission("litescheduledaction.schedule")
public class ScheduleActionCommand {

    private final ScheduledActionRepository scheduledActionRepository;
    private final ActionParserService actionParserService;

    public ScheduleActionCommand(ScheduledActionRepository scheduledActionRepository, ActionParserService actionParserService) {
        this.scheduledActionRepository = scheduledActionRepository;
        this.actionParserService = actionParserService;
    }


    @Execute(route = "add command")
    void addCommand(
            @Arg @Name("name") String name,
            @Arg @Name("command") String command,
            @Arg @Name("date") LocalDate date,
            @Arg @Name("time") LocalTime time
    ) {
        scheduledActionRepository.createOrUpdate(name, ActionCommandParser.rawAction(command), date, time);
    }

    @Execute(route = "add teleport")
    void addTeleport(
            @Arg @Name("name") String name,
            @Arg @Name("teleport") PlayerTeleportEvent.TeleportCause teleportCause,
            @Arg @Name("true/false") boolean enabled,
            @Arg @Name("date") LocalDate date,
            @Arg @Name("time") LocalTime time
    ) {
        scheduledActionRepository.createOrUpdate(name, ActionTeleportParser.rawAction(teleportCause, enabled), date, time);
    }


}
