package dk.dtu.compute.se.pisd.roborally.controller;

import dk.dtu.compute.se.pisd.roborally.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CheckPointTest {

    private CheckPoint checkPoint;

    @BeforeEach
    void setUp() {
        checkPoint = new CheckPoint();
    }

    @Test
    void getCheckPointNumber() {
        checkPoint.setCheckPointNumber(3);

        int result = checkPoint.getCheckPointNumber();

        assertEquals(3, result, "Checkpoint number should be 3.");
    }

    @Test
    void testGetLast() {
        checkPoint.setLastCP(true);
        checkPoint.setCheckPointNumber(4);

        boolean result = checkPoint.getLastCP(4);

        assertTrue(result, "getLastCP should return true for checkpoint number 4.");
    }


    @Test
    void testSetLast() {
        checkPoint.setLastCP(true);

        boolean result = checkPoint.getLastCP(4);

        assertTrue(result, "setLastCP should set the last checkpoint flag to true.");
    }

    @Test
    void testCollect() {
        Board board = new Board(8, 8, "testBoard");
        GameController gameController = new GameController(board);
        Player player = new Player(board, "blue", "Player 1");
        board.addPlayer(player);
        Space space = board.getSpace(0, 0);
        player.setSpace(space);

        CheckPoint checkPoint = new CheckPoint();
        checkPoint.setCheckPointNumber(1);
        checkPoint.setLastCP(false);
        space.getActions().add(checkPoint);

        boolean result = checkPoint.doAction(gameController, space);

        assertTrue(result, "doAction should return true for collecting the checkpoint.");
        assertEquals(1, player.getCollectedCP(), "Player should have collected checkpoint 1.");
    }

    @Test
    void testCollectLast() {
        Board board = new Board(8, 8, "testBoard");
        GameController gameController = new GameController(board);
        Player player = new Player(board, "blue", "Player 1");
        board.addPlayer(player);
        Space space = board.getSpace(0, 0);
        player.setSpace(space);

        player.setCollectedCP(3);


        CheckPoint checkPoint = new CheckPoint();
        checkPoint.setCheckPointNumber(4);
        checkPoint.setLastCP(true);
        space.getActions().add(checkPoint);

        boolean result = checkPoint.doAction(gameController, space);

        assertTrue(result, "doAction should return true for collecting the last checkpoint.");
        assertEquals(4, player.getCollectedCP(), "Player should have collected checkpoint 4.");
        assertEquals(Phase.WINNER, board.getPhase(), "Phase should transition to WINNER.");
    }

    @Test
    void testCollectWrongOrder() {
        Board board = new Board(8, 8, "testBoard");
        GameController gameController = new GameController(board);
        Player player = new Player(board, "blue", "Player 1");
        board.addPlayer(player);
        Space space = board.getSpace(0, 0);
        player.setSpace(space);

        CheckPoint checkPoint = new CheckPoint();
        checkPoint.setCheckPointNumber(2);
        checkPoint.setLastCP(false);
        space.getActions().add(checkPoint);

        boolean result = checkPoint.doAction(gameController, space);

        assertFalse(result, "doAction should return false for collecting checkpoints out of order.");
        assertEquals(0, player.getCollectedCP(), "Player should not have collected any checkpoints.");
    }
}