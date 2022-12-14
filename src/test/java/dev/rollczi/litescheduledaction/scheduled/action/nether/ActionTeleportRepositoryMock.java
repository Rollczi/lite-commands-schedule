package dev.rollczi.litescheduledaction.scheduled.action.nether;

import org.bukkit.event.player.PlayerTeleportEvent;

import java.util.HashMap;
import java.util.Map;

public class ActionTeleportRepositoryMock implements ActionTeleportRepository {

    private final Map<PlayerTeleportEvent.TeleportCause, Boolean> teleportCauses = new HashMap<>();

    @Override
    public boolean isTeleportEnabled(PlayerTeleportEvent.TeleportCause cause) {
        return teleportCauses.getOrDefault(cause, true);
    }

    @Override
    public void setTeleportEnabled(PlayerTeleportEvent.TeleportCause cause, boolean enabled) {
        teleportCauses.put(cause, enabled);
    }

}
