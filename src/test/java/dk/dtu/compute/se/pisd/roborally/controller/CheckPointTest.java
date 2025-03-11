
package dk.dtu.compute.se.pisd.roborally.controller;

import dk.dtu.compute.se.pisd.roborally.model.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CheckPointTest {


    private GameController gameController;

    @BeforeEach
    void setUp() {
        Board defaultBoard = BoardFactory.getInstance().createBoard("default");
        gameController = new GameController(defaultBoard);

        Player player1 = new Player(defaultBoard, "red", "player1");
        Player player2 = new Player(defaultBoard, "blue", "player2");

        defaultBoard.addPlayer(player1);
        defaultBoard.addPlayer(player2);

        defaultBoard.setCurrentPlayer(player1);
    }

    @Test
    void testMoveCurrentPlayerToSpace() {
        Board board = gameController.board;

        Player player1 = board.getPlayer(0);
        Player player2 = board.getPlayer(1);

        gameController.moveCurrentPlayerToSpace(board.getSpace(0, 4));


        Assertions.assertEquals(player1, board.getSpace(0, 4).getPlayer(), "Player " + player1.getName() + " should be on Space (0, 4)!");

        Assertions.assertNull(board.getSpace(0, 0).getPlayer(), "Space (0, 0) should be empty!");

        Assertions.assertEquals(player2, board.getCurrentPlayer(), "Current player should be " + player2.getName() + "!");
    }


    @Test
    void testMoveForward() {
        Board board = gameController.board;
        Player current = board.getCurrentPlayer();
        current.setHeading(Heading.SOUTH);
        board.getSpace(0, 0).setPlayer(current);
        gameController.moveForward(current);

        Assertions.assertEquals(current, board.getSpace(0, 1).getPlayer(),
                "Player " + current.getName() + " should be on Space (0, 1)!");

        Assertions.assertEquals(Heading.SOUTH, current.getHeading(),
                "Player " + current.getName() + " should be heading SOUTH!");

        Assertions.assertNull(board.getSpace(0, 0).getPlayer(),
                "Space (0, 0) should be empty!");
    }

//     private final int TEST_WIDTH = 8;
//     private final int TEST_HEIGHT = 8;

//     private GameController gameController;



    @Test
    void testCheckPointNumber() {
        CheckPoint cp1 = new CheckPoint(); cp1.setCheckPointNumber(2);
        Assertions.assertEquals(2, cp1.getCheckPointNumber(),"Not the right checkpoint-number");
    }

    /*public int getCheckPointNumber() {
        return checkPointNumber;
    }*/

    @Test
    void testGetLastCP() {
        CheckPoint cp1 = new CheckPoint(); cp1.setLastCP(true);cp1.setCheckPointNumber(8);
        Assertions.assertEquals(true,cp1.getLastCP(8),"Not the right last one.");
    }


    /*public boolean getLastCP(int checkPointNumber) {
        return lastCP;
    }*/

    /*public void setCheckPointNumber(int checkPointNumber) {
        this.checkPointNumber = checkPointNumber;
    }*/


    @Test
    void testSetLastCP() {
        CheckPoint cp1 = new CheckPoint(); cp1.setLastCP(true);cp1.setCheckPointNumber(7);
        Assertions.assertEquals(true,cp1.getLastCP(7),"Not the right last one.");
    }

    @Test
    void testDoAction() {
        Board brd = new Board(35,35,"testBoard");
        GameController gc = new GameController(brd);
        Player player = new Player(brd,"blue","someName");
        Space sp = new Space(brd,4,4);
        sp.setPlayer(player);

        CheckPoint cp1 = new CheckPoint(); cp1.doAction(gc,sp);
        Assertions.assertEquals(false,cp1.getLastCP(5),"Not the final space one.");
    }


    /*public void setLastCP(boolean lastCP) {
        this.lastCP = lastCP;
    }*/

    /*public boolean doAction(GameController gameController, Space space) {}*/

}