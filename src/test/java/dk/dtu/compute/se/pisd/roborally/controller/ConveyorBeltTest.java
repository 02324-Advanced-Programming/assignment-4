package dk.dtu.compute.se.pisd.roborally.controller;

import dk.dtu.compute.se.pisd.roborally.model.Board;
import dk.dtu.compute.se.pisd.roborally.model.Heading;
import dk.dtu.compute.se.pisd.roborally.model.Player;
import dk.dtu.compute.se.pisd.roborally.model.Space;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

class ConveyerBeltTest {

    private static final int TEST_WIDTH = 8;
    private static final int TEST_HEIGHT = 8;

    private GameController gameController;
    private Board board;

    @BeforeEach
    void setUp() {
        board = new Board(TEST_WIDTH, TEST_HEIGHT, "testBoard");
        gameController = new GameController(board);
    }

    @AfterEach
    void tearDown() {
        gameController = null;
        board = null;
    }

    @Test
    void testConveyorBeltPush() {
        gameController = new GameController(board);

        int x = 4;
        int y = 4;

        setupPushBoard(board, x, y);

        Player player1 = board.getPlayer(0); // (x, y), heading east

        Player player2 = board.getPlayer(1); // (x+1, y), heading north

        assertNotNull(player1, "Player 1 should not be null at (" + x + ", " + y + ")");
        assertNotNull(player2, "Player 2 should not be null at (" + (x+1) + ", " + y + ")");

        try {
            Space conveyorSpace = board.getSpace(x, y);
            var conveyorBelt = conveyorSpace.getActions().get(0);
            conveyorBelt.doAction(gameController, conveyorSpace);
        } catch (ImpossibleMoveException e) {
            fail("Conveyor belt action failed at (" + x + ", " + y + "): " + e.getMessage());
        }

        Space player1FinalPos = board.getSpace(x +1, y);
        Space player2FinalPos = board.getSpace(x + 2, y);

        assertEquals(player1, player1FinalPos.getPlayer(),
                "Player 1 should move to right");
        assertEquals(player2, player2FinalPos.getPlayer(),
                "Player 2 should have moved to the right right");
    }

    @Test
    void testConveyorBeltPushOutOfBounds() {
        board = new Board(TEST_WIDTH, TEST_HEIGHT, "outOfBoundsBoard");
        gameController = new GameController(board);
        int x = 0 ;
        int y = 7;
        setupOutOfBoundsBoard(board,x,y);

        Player player1 = board.getPlayer(0);// (x, y-2), heading SOUTH and standing on the first belt. There are two belts
        Player player2 = board.getPlayer(1);  // (x, y), heading WEST

        try {
            Space firstConveyorSpace = board.getSpace(x, y-2);
            Space secondConveyorSpace = board.getSpace(x, y-1);

            var firstConveyorBelt = firstConveyorSpace.getActions().get(0);
            firstConveyorBelt.doAction(gameController, firstConveyorSpace);
            var secondConveyorBelt = secondConveyorSpace.getActions().get(0);
            secondConveyorBelt.doAction(gameController, secondConveyorSpace);
        } catch (ImpossibleMoveException e) {
            fail("Conveyor belt action failed at (" + x + ", " + y + "): " + e.getMessage());
        }

        Space player1FinalPos = board.getSpace(0, 7);
        Space player2FinalPos = board.getSpace(0, 0);
        assertEquals(player1, player1FinalPos.getPlayer(),
                "Player 1 should move to (0,7)");
        assertEquals(player2, player2FinalPos.getPlayer(),
                "Player 2 should have moved to the (0,0)");
    }


    @Test
    void testConveyorBeltAcrossBoard() {
        // all positions where a conveyor belt can be placed.
        for (int x = 1; x < TEST_WIDTH - 1; x++) {
            for (int y = 1; y < TEST_HEIGHT-1; y++) {
                setupSimpleTestBoard(x, y);

                var player = board.getPlayer(0);
                assertNotNull(player, "Player should not be null at (" + x + ", " + y + ")");

                try {
                    Space conveyorSpace = board.getSpace(x, y);
                    var conveyorBelt = conveyorSpace.getActions().get(0);
                    conveyorBelt.doAction(gameController, board.getSpace(x, y));
                } catch (ImpossibleMoveException e) {
                    fail("Conveyor belt action failed at (" + x + ", " + y + "): " + e.getMessage());
                }

                Space destination = board.getSpace(x - 1, y);
                assertNotNull(destination, "Destination space should not be null at (" + (x - 1) + ", " + y + ")");
                assertEquals(player, destination.getPlayer(),
                        "Player should have moved to (" + (x - 1) + ", " + y + ")");
            }
        }
    }

    private void setupSimpleTestBoard(int x, int y) {
        Space conveyorSpace = board.getSpace(x, y);
        assertNotNull(conveyorSpace, "Conveyor space should not be null at (" + x + ", " + y + ")");
        ConveyorBelt conveyorBelt = new ConveyorBelt();
        conveyorBelt.setHeading(Heading.WEST);
        conveyorSpace.getActions().add(conveyorBelt);

        // place one player on the same space as the conveyor belt.
        var player = new Player(board, null, "player0");
        player.setSpace(conveyorSpace);
        board.clearPlayers();
        board.addPlayer(player);
        player.setHeading(Heading.WEST);
        board.setCurrentPlayer(player);
    }

    private void setupPushBoard(Board board, int x, int y) {
        Space conveyorSpace = board.getSpace(x, y);
        assertNotNull(conveyorSpace, "Conveyor space should not be null at (" + x + ", " + y + ")");
        ConveyorBelt conveyorBelt = new ConveyorBelt();
        conveyorBelt.setHeading(Heading.EAST);
        conveyorSpace.getActions().add(conveyorBelt);

        // Player 1 at (x, y), heading EAST and standing on the conveyer belt
        Player player1 = new Player(board, null, "player1");
        player1.setSpace(conveyorSpace);
        player1.setHeading(Heading.EAST);

        // Player 2 at (x, y), heading NORTH
        Player player2 = new Player(board, null, "player2");
        player2.setSpace(board.getSpace(x+1, y));
        player2.setHeading(Heading.NORTH);

        // Add players to board
        board.clearPlayers();
        board.addPlayer(player1);
        board.addPlayer(player2);
    }

    private void setupOutOfBoundsBoard( Board board, int x, int y) {
        Space firstConveyorSpace = board.getSpace(x, y-2);
        assertNotNull(firstConveyorSpace, "Conveyor space should not be null at (" + x + ", " + y + ")");
        ConveyorBelt conveyorBelt = new ConveyorBelt();
        conveyorBelt.setHeading(Heading.SOUTH);
        firstConveyorSpace.getActions().add(conveyorBelt);

        Space secondConveyorSpace = board.getSpace(x, y-1);
        assertNotNull(secondConveyorSpace, "Conveyor space should not be null at (" + x + ", " + y + ")");
        conveyorBelt = new ConveyorBelt();
        conveyorBelt.setHeading(Heading.SOUTH);
        secondConveyorSpace.getActions().add(conveyorBelt);

        // Player 1 at (x, y-2), heading SOUTH and standing on the conveyer belt
        Player player1 = new Player(board, null, "player1");
        player1.setSpace(firstConveyorSpace);
        player1.setHeading(Heading.SOUTH);

        // Player 2 at (x, y), heading WEST
        Player player2 = new Player(board, null, "player2");
        player2.setSpace(board.getSpace(x, y));
        player2.setHeading(Heading.WEST);

        board.clearPlayers();
        board.addPlayer(player1);
        board.addPlayer(player2);
    }

}
