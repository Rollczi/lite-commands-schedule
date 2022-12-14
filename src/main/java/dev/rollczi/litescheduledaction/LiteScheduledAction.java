package dev.rollczi.litescheduledaction;

import dev.rollczi.litecommands.LiteCommands;
import dev.rollczi.litecommands.bukkit.adventure.platform.LiteBukkitAdventurePlatformFactory;
import dev.rollczi.litescheduledaction.config.ConfigurationService;
import dev.rollczi.litescheduledaction.scheduled.ScheduleActionCommand;
import dev.rollczi.litescheduledaction.scheduled.ScheduledActionManager;
import dev.rollczi.litescheduledaction.scheduled.action.ActionParserService;
import dev.rollczi.litescheduledaction.scheduled.action.command.ActionCommandParser;
import dev.rollczi.litescheduledaction.scheduled.action.nether.ActionTeleportParser;
import dev.rollczi.litescheduledaction.scheduler.BukkitSchedulerImpl;
import dev.rollczi.litescheduledaction.scheduler.Scheduler;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.java.JavaPluginLoader;

import java.io.File;

public class LiteScheduledAction extends JavaPlugin {

    private Scheduler scheduler;

    private ConfigurationService configurationService;
    private ActionParserService actionParserService;
    private ScheduledActionManager scheduledActionManager;

    private BukkitAudiences bukkitAudiences;
    private LiteCommands<CommandSender> liteCommands;

    @Override
    public void onEnable() {
        this.scheduler = new BukkitSchedulerImpl(this);

        this.configurationService = new ConfigurationService(this.getDataFolder());
        LiteScheduledActionSettings settings = this.configurationService.load(new LiteScheduledActionSettings());

        this.actionParserService = new ActionParserService();
        this.actionParserService.registerParser(new ActionCommandParser(this.getServer()));
        this.actionParserService.registerParser(new ActionTeleportParser(settings));

        this.scheduledActionManager = ScheduledActionManager.create(settings, actionParserService, scheduler);

        this.bukkitAudiences = BukkitAudiences.create(this);
        this.liteCommands = LiteBukkitAdventurePlatformFactory.builder(this.getServer(), "lite-command-schedule", bukkitAudiences, true)
                .commandInstance(
                        new LiteScheduledActionCommand(configurationService),
                        new ScheduleActionCommand(settings, actionParserService)
                )

                .register();
    }

}
