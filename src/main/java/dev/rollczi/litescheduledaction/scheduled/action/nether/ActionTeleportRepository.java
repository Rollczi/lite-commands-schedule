package dev.rollczi.litescheduledaction.scheduled.action.nether;

import org.bukkit.event.player.PlayerTeleportEvent;

public interface ActionTeleportRepository {

    boolean isTeleportEnabled(PlayerTeleportEvent.TeleportCause cause);

    void setTeleportEnabled(PlayerTeleportEvent.TeleportCause cause, boolean enabled);

}
