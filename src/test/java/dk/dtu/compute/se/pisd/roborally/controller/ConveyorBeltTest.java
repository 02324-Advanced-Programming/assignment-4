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
        Board board = new Board(TEST_WIDTH, TEST_HEIGHT, "board");
        GameController edgeCaseGameController = new GameController(board);

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
            conveyorBelt.doAction(edgeCaseGameController, conveyorSpace);
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

    private void setupPushBoard(Board edgeCaseBoard, int x, int y) {
        Space conveyorSpace = edgeCaseBoard.getSpace(x, y);
        assertNotNull(conveyorSpace, "Conveyor space should not be null at (" + x + ", " + y + ")");
        ConveyorBelt conveyorBelt = new ConveyorBelt();
        conveyorBelt.setHeading(Heading.EAST);
        conveyorSpace.getActions().add(conveyorBelt);

        // Player 1 at (x, y), heading EAST and standing on the conveyer belt
        Player player1 = new Player(edgeCaseBoard, null, "player1");
        player1.setSpace(conveyorSpace);
        player1.setHeading(Heading.EAST);

        // Player 2 at (x, y), heading NORTH
        Player player2 = new Player(edgeCaseBoard, null, "player2");
        player2.setSpace(edgeCaseBoard.getSpace(x+1, y));
        player2.setHeading(Heading.NORTH);

        // Add players to board
        edgeCaseBoard.clearPlayers();
        edgeCaseBoard.addPlayer(player1);
        edgeCaseBoard.addPlayer(player2);
    }


}
