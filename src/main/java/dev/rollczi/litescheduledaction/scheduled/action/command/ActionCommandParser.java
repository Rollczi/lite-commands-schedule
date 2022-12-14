package dev.rollczi.litescheduledaction.scheduled.action.command;

import dev.rollczi.litescheduledaction.scheduled.action.Action;
import dev.rollczi.litescheduledaction.scheduled.action.ActionParser;
import org.bukkit.Server;
import panda.std.Result;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ActionCommandParser implements ActionParser {

    private static final String FORMAT = "command:%s";
    private static final Pattern PATTERN = Pattern.compile("command:(.+)");

    private final Server server;

    public ActionCommandParser(Server server) {
        this.server = server;
    }

    @Override
    public Result<Action, String> parse(String action) {
        Matcher matcher = PATTERN.matcher(action);

        if (!matcher.matches()) {
            return Result.error("Invalid action: " + action);
        }

        String command = matcher.group(1);

        return Result.ok(new ActionCommandImpl(command));
    }

    public static String rawAction(String command) {
        return FORMAT.formatted(command);
    }

    private class ActionCommandImpl implements Action {

        private final String command;

        ActionCommandImpl(String command) {
            this.command = command;
        }

        @Override
        public void execute() {
            server.dispatchCommand(server.getConsoleSender(), command);
        }

        @Override
        public String toString() {
            return FORMAT.formatted(command);
        }

    }

}
