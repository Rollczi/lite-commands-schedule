package dev.rollczi.litescheduledaction.scheduled.action.nether;

import dev.rollczi.litescheduledaction.scheduled.action.Action;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.junit.jupiter.api.Test;
import panda.std.Result;

import static org.junit.jupiter.api.Assertions.*;

class ActionTeleportParserTest {

    @Test
    void test() {
        ActionTeleportRepositoryMock repository = new ActionTeleportRepositoryMock();
        ActionTeleportParser parser = ActionTeleportParser.createWithoutController(repository);

        repository.setTeleportEnabled(PlayerTeleportEvent.TeleportCause.NETHER_PORTAL, false);

        {
            Result<Action, String> parse = parser.parse("teleport:NETHER_PORTAL:true");
            assertTrue(parse.isOk());

            Action action = parse.get();
            assertEquals("teleport:NETHER_PORTAL:true", action.toString());

            action.execute();
            assertTrue(repository.isTeleportEnabled(PlayerTeleportEvent.TeleportCause.NETHER_PORTAL));
        }
    }

    @Test
    void testRawParseAction() {
        String action = ActionTeleportParser.rawAction(PlayerTeleportEvent.TeleportCause.NETHER_PORTAL, true);

        assertEquals("teleport:NETHER_PORTAL:true", action);
    }

}