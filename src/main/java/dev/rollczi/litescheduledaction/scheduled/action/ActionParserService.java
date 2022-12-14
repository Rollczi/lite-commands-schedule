package dev.rollczi.litescheduledaction.scheduled.action;

import dev.rollczi.litescheduledaction.scheduled.action.command.ActionCommandParser;
import panda.std.Result;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class ActionParserService {

    private final Map<String, ActionParser> actionParsers = new HashMap<>();

    public Result<Action, String> parse(String action) {
        Result<Action, String> errorResult = Result.error("No action factory found for: " + action);

        for (ActionParser actionParser : actionParsers.values()) {
            Result<Action, String> parsedAction = actionParser.parse(action);

            if (parsedAction.isOk()) {
                return parsedAction;
            }

            errorResult = parsedAction;
        }

        return errorResult;
    }

    public void registerParser(ActionParser actionParser) {
        actionParsers.put(actionParser.getClass().getSimpleName(), actionParser);
    }

}
