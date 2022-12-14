package dev.rollczi.litescheduledaction.scheduled.action;

import panda.std.Result;

import java.util.regex.Pattern;

public interface ActionParser {

    Result<Action, String> parse(String action);

}
