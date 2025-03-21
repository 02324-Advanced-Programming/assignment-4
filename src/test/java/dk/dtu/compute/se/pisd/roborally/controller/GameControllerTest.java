package dk.dtu.compute.se.pisd.roborally.controller;

import dk.dtu.compute.se.pisd.roborally.model.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameControllerTest {
    /**
     * s247273
     * s245231
     */

    private final int TEST_WIDTH = 8;
    private final int TEST_HEIGHT = 8;

    private GameController gameController;
    private Board board;

    @BeforeEach
    void setUp() {
        board = new Board(TEST_WIDTH, TEST_HEIGHT);
        gameController = new GameController(board);
        for (int i = 0; i < 6; i++) {
            Player player = new Player(board, null, "Player " + i);
            board.addPlayer(player);
            player.setSpace(board.getSpace(i, i));
            player.setHeading(Heading.values()[i % Heading.values().length]);
        }
        board.setCurrentPlayer(board.getPlayer(0));
    }

    @AfterEach
    void tearDown() {
        gameController = null;
        board = null;
    }

    @Test
    void testMoveCurrentPlayerToSpace() {
        Player player1 = board.getPlayer(0);
        Player player2 = board.getPlayer(1);

        gameController.moveCurrentPlayerToSpace(board.getSpace(0, 4));

        assertEquals(player1, board.getSpace(0, 4).getPlayer(), "Player " + player1.getName() + " should be on Space (0,4)!");
        assertNull(board.getSpace(0, 0).getPlayer(), "Space (0,0) should be empty!");
        assertEquals(player2, board.getCurrentPlayer(), "Current player should be " + player2.getName() + "!");
    }
    @Test
    void testStartProgrammingPhase() {
        gameController.startProgrammingPhase();

        assertEquals(Phase.PROGRAMMING, board.getPhase(), "Phase should be PROGRAMMING.");
        assertEquals(board.getPlayer(0), board.getCurrentPlayer(), "Current player should be Player 0.");
        assertEquals(0, board.getStep(), "Step should be 0.");

        for (int i = 0; i < board.getPlayersNumber(); i++) {
            Player player = board.getPlayer(i);
            for (int j = 0; j < Player.NO_REGISTERS; j++) {
                assertNull(player.getProgramField(j).getCard(), "Program field should be empty.");
                assertTrue(player.getProgramField(j).isVisible(), "Program field should be visible.");
            }
            for (int j = 0; j < Player.NO_CARDS; j++) {
                assertNotNull(player.getCardField(j).getCard(), "Card field should not be empty.");
                assertTrue(player.getCardField(j).isVisible(), "Card field should be visible.");
            }
        }
    }

    @Test
    void testFinishProgrammingPhase() {
        gameController.startProgrammingPhase();
        gameController.finishProgrammingPhase();

        assertEquals(Phase.ACTIVATION, board.getPhase(), "Phase should be ACTIVATION.");
        assertEquals(board.getPlayer(0), board.getCurrentPlayer(), "Current player should be Player 0.");
        assertEquals(0, board.getStep(), "Step should be 0.");

        for (int i = 0; i < board.getPlayersNumber(); i++) {
            Player player = board.getPlayer(i);
            for (int j = 0; j < Player.NO_REGISTERS; j++) {
                if (j == 0) {
                    assertTrue(player.getProgramField(j).isVisible(), "First program field should be visible.");
                } else {
                    assertFalse(player.getProgramField(j).isVisible(), "Other program fields should be invisible.");
                }
            }
        }
    }

    @Test
    void testExecutePrograms() {
        gameController.startProgrammingPhase();
        gameController.finishProgrammingPhase();
        gameController.executePrograms();

        assertEquals(Phase.PROGRAMMING, board.getPhase(), "Phase should be PROGRAMMING after executing programs.");
    }

    @Test
    void testExecuteStep() {
        gameController.startProgrammingPhase();
        gameController.finishProgrammingPhase();
        gameController.executeStep();

        assertEquals(Phase.ACTIVATION, board.getPhase(), "Phase should still be ACTIVATION after executing a step.");
    }

    @Test
    void testMoveCards() {
        Player player = board.getPlayer(0);
        CommandCardField source = player.getProgramField(0);
        CommandCardField target = player.getProgramField(1);

        CommandCard card = new CommandCard(Command.FORWARD);
        source.setCard(card);

        assertTrue(gameController.moveCards(source, target), "Move should be successful.");
        assertNull(source.getCard(), "Source field should be empty after move.");
        assertEquals(card, target.getCard(), "Target field should contain the moved card.");
    }

    @Test
    void testMoveCardsFail() {
        Player player = board.getPlayer(0);
        CommandCardField source = player.getProgramField(0);
        CommandCardField target = player.getProgramField(1);

        CommandCard card1 = new CommandCard(Command.FORWARD);
        CommandCard card2 = new CommandCard(Command.RIGHT);
        source.setCard(card1);
        target.setCard(card2);

        assertFalse(gameController.moveCards(source, target), "Move should fail because target is not empty.");
        assertEquals(card1, source.getCard(), "Source field should still contain card.");
        assertEquals(card2, target.getCard(), "Target field should still contain card.");
    }

    @Test
    void testNotImplemented() {
        assertThrows(AssertionError.class, () -> gameController.notImplemented(), "Not implemented should throw assertion error.");
    }

    @Test
    void testMoveToSpace() {
        Player pusher = board.getPlayer(0);
        Player pushed = board.getPlayer(1);
        Space space = board.getSpace(1, 1);
        pushed.setSpace(space);

        assertDoesNotThrow(() -> gameController.moveToSpace(pusher, space, Heading.SOUTH), "Move should not throw an exception.");
        assertEquals(pusher, space.getPlayer(), "Pusher should be on the space.");
        assertEquals(pushed, board.getSpace(1, 2).getPlayer(), "Pushed player should be on the next space.");
    }

    @Test
    void testExecuteFieldActionsCheckPoint() {

        Player player = board.getSpace(0, 0).getPlayer();
        // game flow to place the player on a specific space
        gameController.startProgrammingPhase();
        gameController.finishProgrammingPhase();

        // add checkpoint to player's current space
        Space space = player.getSpace();
        CheckPoint checkPoint = new CheckPoint();
        checkPoint.setCheckPointNumber(1);
        checkPoint.setLastCP(false);
        space.getActions().add(checkPoint);
        //try execute checkpoint action
        gameController.executeFieldActions();

        assertEquals(1, player.getCollectedCP(), "Player should have collected checkpoint 1.");
        assertEquals(Phase.ACTIVATION, board.getPhase(), "Phase should remain ACTIVATION.");
    }


    @Test
    void testExecuteNextStepLeftOrRight() {

        Player player = board.getSpace(0, 0).getPlayer();

        gameController.startProgrammingPhase();
        gameController.finishProgrammingPhase();

        board.setCurrentPlayer(player);
        board.setStep(0);

        // Add LEFT_OR_RIGHT command card to the player's program field and then try execute
        CommandCard card = new CommandCard(Command.LEFT_OR_RIGHT);
        player.getProgramField(0).setCard(card);

        gameController.executeNextStep();

        assertEquals(Phase.PLAYER_INTERACTION, board.getPhase(), "Phase should be PLAYER_INTERACTION.");
    }

    @Test
    void testExecuteNextStepForward() {
        Player player = board.getSpace(0, 0).getPlayer();
        player.setHeading(Heading.NORTH);
        gameController.startProgrammingPhase();
        gameController.finishProgrammingPhase();

        board.setCurrentPlayer(player);
        board.setStep(0);

        CommandCard card = new CommandCard(Command.FORWARD);
        player.getProgramField(0).setCard(card);

        gameController.executeNextStep();

        assertEquals(Phase.ACTIVATION, board.getPhase(), "Phase should be PLAYER_INTERACTION.");
        assertEquals(board.getSpace(0,7).getPlayer() , player, "Player should be on space (0,7)");
    }

    @Test
    void testExecuteRight() {
        Player current = board.getCurrentPlayer();
        current.setSpace(board.getSpace(1, 1));
        Heading prev = current.getHeading();

        gameController.executeCommand(current, Command.RIGHT);

        assertEquals(current, board.getSpace(1, 1).getPlayer(), "Player " + current.getName() + " should be on Space (1,1)!");
        assertEquals(prev.next(), current.getHeading(), "Player " + current.getName() + " should be heading " + prev.next());
    }
    @Test
    void testExecuteLeft() {
        Player current = board.getCurrentPlayer();
        current.setSpace(board.getSpace(1, 1));
        Heading prev = current.getHeading();

        gameController.executeCommand(current, Command.LEFT);

        assertEquals(current, board.getSpace(1, 1).getPlayer(), "Player " + current.getName() + " should be on Space (1,1)!");
        assertEquals(prev.prev(), current.getHeading(), "Player " + current.getName() + " should be heading " + prev.prev());
    }

    @Test
    void testExecuteFastForward() {
        Player current = board.getCurrentPlayer();
        current.setSpace(board.getSpace(1, 1));
        Heading heading = current.getHeading();

        gameController.executeCommand(current, Command.FAST_FORWARD);

        assertEquals(current, board.getSpace(1, 3).getPlayer(), "Player " + current.getName() + " should be on Space (1,3)!");
        assertEquals(heading, current.getHeading(), "The direction the player is moving shouldn't change");
        assertNull(board.getSpace(1, 1).getPlayer(), "Space (1,1) should be empty!");
    }

    @Test
    void testExecuteBackward() {
        Player current = board.getCurrentPlayer();
        current.setSpace(board.getSpace(1, 1));
        Heading prev = current.getHeading();

        gameController.executeCommand(current, Command.BACKWARD);

        assertEquals(current, board.getSpace(1, 0).getPlayer(), "Player " + current.getName() + " should be on Space (1,0)!");
        assertEquals(prev, current.getHeading(), "The direction the player is moving shouldn't change");
        assertNull(board.getSpace(1, 1).getPlayer(), "Space (1,1) should be empty!");
    }

    @Test
    void testExecuteUTurn() {
        Player current = board.getCurrentPlayer();
        Heading prev = current.getHeading();

        gameController.executeCommand(current, Command.UTURN);

        assertEquals(prev.next().next(), current.getHeading(), "Player's direction should be reversed.");
    }

}