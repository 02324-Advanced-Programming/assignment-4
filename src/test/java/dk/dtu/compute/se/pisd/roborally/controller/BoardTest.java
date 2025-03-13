
package dk.dtu.compute.se.pisd.roborally.controller;

import dk.dtu.compute.se.pisd.roborally.model.*;
import dk.dtu.compute.se.pisd.roborally.view.BoardView;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javafx.scene.control.Label;

import static dk.dtu.compute.se.pisd.roborally.model.Phase.INITIALISATION;

@SuppressWarnings("")


class BoardTest {

    private final int TEST_WIDTH = 8;
    private final int TEST_HEIGHT = 8;

    private GameController gameController;

    //public final int width;

    //public final int height;

    //public final String boardName;

    private Integer gameId;

    //private final Space[][] spaces;

    private final List<Player> players = new ArrayList<>();

    private Player current;

    private Phase phase = INITIALISATION;

    private int step = 0;

    private boolean stepMode;

    private int counter;


    private Board setupBoard () {

        return new Board(55,55,"testBoardTotal");

    }
    private Space setupSpace (int x, int y) {
        Board testBoard = setupBoard();
        return new Space(testBoard, x,y);
    }

    @Test
    void testGetGameId_notnull() {

        int testval1 = 2;
        String errorMess_wrongRes = "Expected testboard to have gameid of "+testval1;
        Board testBoard = setupBoard();
        testBoard.setGameId(testval1);

        Assertions.assertEquals(2,testBoard.getGameId(), errorMess_wrongRes);
    }

    //Testing with negative index, but this might lead to a memory leak in the end
    //because these indexes < 0 cannot be accessed.
    @Test
    void testGetGameId_null() {

        int testval1 = -2;
        String errorMess_wrongRes = "testGame has gameid null; has to have value.";
        Board testBoard = setupBoard();

        Assertions.assertThrows (IndexOutOfBoundsException.class,() -> testBoard.setGameId(testval1), errorMess_wrongRes);
        //testBoard.setGameId(testval1);


    }

    //public Integer getGameId() - Done

    @Test
    void testGameId_notnull() {

        Board testBoard = setupBoard();
        testBoard.setGameId(3);
        String errorMess_wrongRes = "Expected testboard to have gameid 3";

        Assertions.assertEquals(3, testBoard.getGameId(), errorMess_wrongRes);
    }

    //public Space getSpace(int x, int y) - done
    @Test
    void testGetSpace_notnull() {

        int[] coords = new int[]{2,5};
        String errorMess_wrongRes = "Expected testboard to have spaces of "+coords[0]+" and "+coords[1];
        Board testBoard = setupBoard();
        Space testSpace = setupSpace(coords[0],coords[1]);

        Assertions.assertEquals(testBoard.getSpace(coords[0],coords[1]),testBoard.getSpace(2,5), errorMess_wrongRes);
    }

    //public int getPlayersNumber() - done
    @Test
    void testGetPlayersNumber_notnull() {

        Board testBoard = setupBoard();
        String errorMess_wrongRes = "Expected testboard to have 2 players";

        Player testPlayer1 = new Player(testBoard,"blue","TP1");
        Player testPlayer2 = new Player(testBoard,"orange","TP2");

        testBoard.addPlayer(testPlayer1);testBoard.addPlayer(testPlayer2);

        Assertions.assertEquals(2, testBoard.getPlayersNumber(), errorMess_wrongRes);
    }

    //public Player getPlayer(int i)
    @Test
    void testGetPlayer_notnull() {

        Board testBoard = setupBoard();

        String errorMess_wrongRes = "Expected testboard to have first player";

        Player testPlayer1 = new Player(testBoard,"blue","TP1");
        Player testPlayer2 = new Player(testBoard,"orange","TP2");
        testBoard.addPlayer(testPlayer1); testBoard.addPlayer(testPlayer2);

        Assertions.assertEquals(testPlayer1, testBoard.getPlayer(0), errorMess_wrongRes);
    }

    //public Player getCurrentPlayer()
    @Test
    void testGetCurrentPlayer_notnull() {

        Board testBoard = setupBoard();

        String errorMess_wrongRes = "Expected testboard to have current player 2";

        Player testPlayer1 = new Player(testBoard,"blue","TP1");
        Player testPlayer2 = new Player(testBoard,"orange","TP2");
        testBoard.addPlayer(testPlayer1); testBoard.addPlayer(testPlayer2);
        testBoard.setCurrentPlayer(testPlayer2);

        Assertions.assertEquals(testPlayer2, testBoard.getCurrentPlayer(), errorMess_wrongRes);
    }

    //public Phase getPhase()
    @Test
    void testGetPhase_notnull() {

        Board testBoard = setupBoard();

        String errorMess_wrongRes = "Expected testboard to have phase winner";

        Player testPlayer1 = new Player(testBoard,"blue","TP1");
        Player testPlayer2 = new Player(testBoard,"orange","TP2");
        testBoard.addPlayer(testPlayer1); testBoard.addPlayer(testPlayer2);

        Phase testPhase = Phase.WINNER;
        testBoard.setPhase(testPhase);
        Assertions.assertEquals(Phase.WINNER, testBoard.getPhase(), errorMess_wrongRes);
    }

    //public int getStep() - done
    @Test
    void testGetStep_notnull() {

        Board testBoard = setupBoard();

        String errorMess_wrongRes = "Expected testboard to have step 7";

        Player testPlayer1 = new Player(testBoard,"blue","TP1");
        Player testPlayer2 = new Player(testBoard,"orange","TP2");
        testBoard.addPlayer(testPlayer1); testBoard.addPlayer(testPlayer2);

        testBoard.setStep(7);
        Assertions.assertEquals(7, testBoard.getStep(), errorMess_wrongRes);
    }

    //@Test

    //public boolean isStepMode() - done
    @Test
    void testStepMode_notnull() {

        Board testBoard = setupBoard();

        String errorMess_wrongRes = "Expected testboard to have first player";

        Player testPlayer1 = new Player(testBoard,"blue","TP1");
        Player testPlayer2 = new Player(testBoard,"orange","TP2");
        testBoard.addPlayer(testPlayer1); testBoard.addPlayer(testPlayer2);

        testBoard.setStepMode(false);
        Assertions.assertEquals(false, testBoard.isStepMode(), errorMess_wrongRes);
    }

    //public int getPlayerNumber(@NotNull Player player) - done
    @Test
    void testGetPlayerNumber_notnull() {

        Board testBoard = setupBoard();

        String errorMess_wrongRes = "Expected testboard to have testplayer2 as 2";

        Player testPlayer1 = new Player(testBoard,"blue","TP1");
        Player testPlayer2 = new Player(testBoard,"orange","TP2");
        testBoard.addPlayer(testPlayer1); testBoard.addPlayer(testPlayer2);


        Assertions.assertEquals(1, testBoard.getPlayerNumber(testPlayer2), errorMess_wrongRes);
    }


    //public Space getNeighbour(@NotNull Space space, @NotNull Heading heading) - has errors
    //does not return a space in itself, but it returns null.
    @Test
    void testGetNeighbour_notnull() {
        Board testBoard = new Board(45, 75, "testBoardTotal");

        String errorMess_wrongRes = "Expected testboard to have a Space at (44, 74)";

        Player testPlayer1 = new Player(testBoard, "blue", "TP1");
        Player testPlayer2 = new Player(testBoard, "orange", "TP2");
        testBoard.addPlayer(testPlayer1);
        testBoard.addPlayer(testPlayer2);

        testPlayer1.setSpace(testBoard.getSpace(44, 74));
        testPlayer2.setSpace(testBoard.getSpace(43, 74));
        testPlayer2.setHeading(Heading.EAST);

        Space neighbourSpace = testBoard.getNeighbour(testBoard.getSpace(43, 74), Heading.EAST);

        //test if the neighbour of player2 is player1
        Assertions.assertEquals(testPlayer1.getSpace(), neighbourSpace, errorMess_wrongRes);
    }

    //public String getStatusMessage() {} - not done, throws errors when trying to assign
    //a gamecontroller to a boardview (fails on the boardview-init part).
    @SuppressWarnings("")
    @Test

    void testGetStatusMessage_notnull() {

        Board testBoard = setupBoard();

        String errorMess_wrongRes = "Expected testboard to have first player";

        Player testPlayer1 = new Player(testBoard,"blue","TP1");
        Player testPlayer2 = new Player(testBoard,"orange","TP2");
        testBoard.addPlayer(testPlayer1); testBoard.addPlayer(testPlayer2);

        GameController gc = new GameController(testBoard);
        BoardView tbv = new BoardView(gc);

        //String expectedStatus = "Just some stuff";
        //Label tLabel = new Label(expectedStatus);
        //tbv.getChildren().add(tLabel);

        //Assertions.assertEquals(expectedStatus, testBoard.getStatusMessage(), errorMess_wrongRes);
    }

    //public int getCounter()

    //public void setCounter(int counter)




}