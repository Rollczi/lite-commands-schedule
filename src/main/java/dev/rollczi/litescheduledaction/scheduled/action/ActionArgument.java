package dev.rollczi.litescheduledaction.scheduled.action;

import dev.rollczi.litecommands.argument.simple.OneArgument;
import dev.rollczi.litecommands.command.LiteInvocation;
import dev.rollczi.litecommands.suggestion.Suggestion;
import panda.std.Result;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ActionArgument implements OneArgument<Action> {

    private final ActionParserService actionParserService;

    public ActionArgument(ActionParserService actionParserService) {
        this.actionParserService = actionParserService;
    }

    @Override
    public Result<Action, ?> parse(LiteInvocation invocation, String argument) {
        return actionParserService.parse(argument);
    }

}
