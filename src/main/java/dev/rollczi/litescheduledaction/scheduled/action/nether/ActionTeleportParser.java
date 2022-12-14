package dev.rollczi.litescheduledaction.scheduled.action.nether;

import dev.rollczi.litescheduledaction.scheduled.action.Action;
import dev.rollczi.litescheduledaction.scheduled.action.ActionParser;
import org.bukkit.Server;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import panda.std.Result;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ActionTeleportParser implements ActionParser {

    private static final String FORMAT = "teleport:%s:%b";
    private static final Pattern PATTERN = Pattern.compile("teleport:(.+):(true|false)");

    private final ActionTeleportRepository repository;

    public ActionTeleportParser(ActionTeleportRepository repository) {
        this.repository = repository;
    }

    @Override
    public Result<Action, String> parse(String action) {
        Matcher matcher = PATTERN.matcher(action);

        if (!matcher.matches()) {
            return Result.error("Invalid action");
        }

        String cause = matcher.group(1);
        String enabled = matcher.group(2);

        if (!enabled.equalsIgnoreCase("true") && !enabled.equalsIgnoreCase("false")) {
            return Result.error("Invalid action: enabled must be true or false");
        }

        boolean enable = Boolean.parseBoolean(enabled);

        try {
            PlayerTeleportEvent.TeleportCause teleportCause = PlayerTeleportEvent.TeleportCause.valueOf(cause);

            return Result.ok(new ActionTeleportImpl(teleportCause, enable));
        }
        catch (IllegalArgumentException exception) {
            return Result.error("Invalid input: " + cause);
        }
    }

    public static String rawAction(PlayerTeleportEvent.TeleportCause teleportCause, boolean enabled) {
        return FORMAT.formatted(teleportCause, enabled);
    }

    public static ActionTeleportParser createAndRegisterController(Plugin plugin, ActionTeleportRepository repository) {
        ActionTeleportParser parser = new ActionTeleportParser(repository);
        Server server = plugin.getServer();
        PluginManager pluginManager = server.getPluginManager();

        pluginManager.registerEvents(parser.new TeleportController(), plugin);

        return parser;
    }

    public static ActionTeleportParser createWithoutController(ActionTeleportRepository repository) {
        return new ActionTeleportParser(repository);
    }

    private class ActionTeleportImpl implements Action {

        private final PlayerTeleportEvent.TeleportCause cause;
        private final boolean enabled;

        public ActionTeleportImpl(PlayerTeleportEvent.TeleportCause cause, boolean enabled) {
            this.cause = cause;
            this.enabled = enabled;
        }

        @Override
        public void execute() {
            repository.setTeleportEnabled(cause, enabled);
        }


        @Override
        public String toString() {
            return FORMAT.formatted(cause.name(), enabled);
        }

    }

    private class TeleportController implements Listener {

        @EventHandler
        public void onTeleport(PlayerTeleportEvent event) {
            if (repository.isTeleportEnabled(event.getCause())) {
                event.setCancelled(true);
            }
        }

    }

}
