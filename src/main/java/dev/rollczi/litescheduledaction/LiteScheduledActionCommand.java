package dev.rollczi.litescheduledaction;

import dev.rollczi.litecommands.command.execute.Execute;
import dev.rollczi.litecommands.command.permission.Permission;
import dev.rollczi.litecommands.command.route.Route;
import dev.rollczi.litescheduledaction.config.ConfigurationService;

@Route(name = "lite-command-schedule", aliases = "lsa")
@Permission("litescheduledaction.admin")
class LiteScheduledActionCommand {

    private final ConfigurationService configurationService;

    LiteScheduledActionCommand(ConfigurationService configurationService) {
        this.configurationService = configurationService;
    }

    @Execute
    @Route(name = "reload")
    void reload() {
        configurationService.reloadAll();
    }

}
