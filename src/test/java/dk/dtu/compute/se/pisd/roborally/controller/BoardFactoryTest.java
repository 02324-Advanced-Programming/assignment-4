package dk.dtu.compute.se.pisd.roborally.controller;

import dk.dtu.compute.se.pisd.roborally.model.Board;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.function.Function;
import static org.junit.jupiter.api.Assertions.*;

/**
 * s247273
 */
class BoardFactoryTest {

    private BoardFactory boardFactory;

    @BeforeEach
    void setUp() {
        boardFactory = BoardFactory.getInstance();
    }

    @Test
    void testGetInstanceSingleton() {
        BoardFactory instance1 = BoardFactory.getInstance();
        BoardFactory instance2 = BoardFactory.getInstance();

        assertSame(instance1, instance2, "BoardFactory should be a singleton.");
    }

    @Test
    void registerBoard() {
        String boardName = "advanced";
        Function<Board, Board> creator = board -> board;

        boardFactory.registerBoard(boardName, creator);

        List<String> boardNames = boardFactory.getBoardNames();
        assertTrue(boardNames.contains(boardName), "Registered board should be in the list of board names.");
    }

    @Test
    void getBoardNames() {
        List<String> expectedBoardNames = List.of("default", "advanced");

        List<String> boardNames = boardFactory.getBoardNames();

        assertEquals(expectedBoardNames, boardNames, "Board names should match the registered boards.");
    }
}