package dk.dtu.compute.se.pisd.roborally.controller;

import dk.dtu.compute.se.pisd.roborally.model.Board;
import dk.dtu.compute.se.pisd.roborally.model.Heading;
import dk.dtu.compute.se.pisd.roborally.model.Space;

import java.util.*;
import java.util.function.Function;

/**
 * @author s247273
 */

public class BoardFactory {

    /**
     * The single instance of this class, which is lazily instantiated on demand.
     */
    static private BoardFactory instance = null;

    //A map of board creators, where the key is the name of the board and the value is a function that creates the board
    private final Map<String, Function<Board, Board>> boardCreators = new HashMap<>();

    //If you want to create a new board, create your board function, then register it here with a name :

    private BoardFactory() {
        registerBoard("default", board -> createDefaultBoard(board));
        registerBoard("advanced", board -> createAdvancedBoard(board));
    }

    public void registerBoard(String name, Function<Board, Board> creator) {
        boardCreators.put(name, creator);
    }

    public List<String> getBoardNames() {
        return Collections.unmodifiableList(new ArrayList<>(boardCreators.keySet()));
    }
    /**
     * Returns the single instance of this factory. The instance is lazily
     * instantiated when requested for the first time.
     *
     * @return the single instance of the BoardFactory
     */
    public static BoardFactory getInstance() {
        if (instance == null) {
            instance = new BoardFactory();
        }
        return instance;
    }


    /**
     * Creates a new board of given name of a board, which indicates
     * which type of board should be created. For now the name is ignored.
     *
     * @param name the given name board
     * @return the new board corresponding to that name
     */
    public Board createBoard(String name) {
        Function<Board, Board> creator = boardCreators.get(name);
        if (creator == null) {
            throw new IllegalArgumentException("Unknown board type: " + name);
        }
        return creator.apply(new Board(8, 8, name));
    }

    Board createDefaultBoard (Board board){
        // add some walls, actions and checkpoints to some spaces

        Space space = board.getSpace(2,0);
        space.getWalls().add(Heading.EAST);
        ConveyorBelt action  = new ConveyorBelt();
        action.setHeading(Heading.WEST);
        space.getActions().add(action);

        space = board.getSpace(1,0);
        space.getWalls().add(Heading.NORTH);
        action  = new ConveyorBelt();
        action.setHeading(Heading.WEST);
        space.getActions().add(action);

        space = board.getSpace(2,1);
        space.getWalls().add(Heading.WEST);
        action  = new ConveyorBelt();
        action.setHeading(Heading.NORTH);
        space.getActions().add(action);

        space = board.getSpace(5,5);
        space.getWalls().add(Heading.SOUTH);
        action  = new ConveyorBelt();
        action.setHeading(Heading.WEST);
        space.getActions().add(action);

        space = board.getSpace(6,5);
        action  = new ConveyorBelt();
        action.setHeading(Heading.WEST);
        space.getActions().add(action);

        space = board.getSpace(4,2);
        var checkPoint = new CheckPoint();
        checkPoint.setCheckPointNumber(1);
        space.getActions().add(checkPoint);

        space = board.getSpace(2,6);
        var checkPoint2 = new CheckPoint();
        checkPoint2.setCheckPointNumber(2);
        space.getActions().add(checkPoint2);

        space = board.getSpace(1,4);
        space.getWalls().add(Heading.NORTH);
        space.getWalls().add(Heading.WEST);
        var checkPoint3 = new CheckPoint();
        checkPoint3.setCheckPointNumber(3);
        space.getActions().add(checkPoint3);

        space = board.getSpace(5,3);
        space.getWalls().add(Heading.NORTH);
        space.getWalls().add(Heading.WEST);
        var checkPoint4 = new CheckPoint();
        checkPoint4.setCheckPointNumber(4);
        checkPoint4.setLastCP(true);
        space.getActions().add(checkPoint4);

        space = board.getSpace(0,3);
        space.getWalls().add(Heading.NORTH);
        action  = new ConveyorBelt();
        action.setHeading(Heading.WEST);
        space.getActions().add(action);

        space = board.getSpace(1,3);
        space.getWalls().add(Heading.NORTH);
        action  = new ConveyorBelt();
        action.setHeading(Heading.WEST);
        space.getActions().add(action);

        space = board.getSpace(2,3);
        space.getWalls().add(Heading.SOUTH);
        action  = new ConveyorBelt();
        action.setHeading(Heading.WEST);
        space.getActions().add(action);

        space = board.getSpace(3,3);
        space.getWalls().add(Heading.SOUTH);
        space.getWalls().add(Heading.EAST);
        action  = new ConveyorBelt();
        action.setHeading(Heading.WEST);
        space.getActions().add(action);

        space = board.getSpace(0,5);
        space.getWalls().add(Heading.NORTH);
        action  = new ConveyorBelt();
        action.setHeading(Heading.WEST);
        space.getActions().add(action);

        space = board.getSpace(1,5);
        space.getWalls().add(Heading.NORTH);
        action  = new ConveyorBelt();
        action.setHeading(Heading.WEST);
        space.getActions().add(action);

        space = board.getSpace(2,5);
        space.getWalls().add(Heading.SOUTH);
        action  = new ConveyorBelt();
        action.setHeading(Heading.WEST);
        space.getActions().add(action);

        space = board.getSpace(3,5);
        space.getWalls().add(Heading.SOUTH);
        space.getWalls().add(Heading.EAST);
        action  = new ConveyorBelt();
        action.setHeading(Heading.WEST);
        space.getActions().add(action);

        return board;

    }
    Board createAdvancedBoard(Board board){
        Space space = board.getSpace(0,1);
        space.getWalls().add(Heading.EAST);
        ConveyorBelt action = new ConveyorBelt();
        action.setHeading(Heading.NORTH);
        space.getActions().add(action);

        space = board.getSpace(3,1);
        space.getWalls().add(Heading.WEST);
        action = new ConveyorBelt();
        action.setHeading(Heading.SOUTH);
        space.getActions().add(action);

        space = board.getSpace(4,0);
        space.getWalls().add(Heading.NORTH);
        action = new ConveyorBelt();
        action.setHeading(Heading.EAST);
        space.getActions().add(action);

        space = board.getSpace(5,2);
        space.getWalls().add(Heading.SOUTH);
        action = new ConveyorBelt();
        action.setHeading(Heading.NORTH);
        space.getActions().add(action);

        space = board.getSpace(6,6);
        action = new ConveyorBelt();
        action.setHeading(Heading.WEST);
        space.getActions().add(action);

        space = board.getSpace(1,2);
        var checkPoint = new CheckPoint();
        checkPoint.setCheckPointNumber(1);
        space.getActions().add(checkPoint);

        space = board.getSpace(3,5);
        var checkPoint2 = new CheckPoint();
        checkPoint2.setCheckPointNumber(2);
        space.getActions().add(checkPoint2);

        space = board.getSpace(5,1);
        space.getWalls().add(Heading.NORTH);
        space.getWalls().add(Heading.WEST);
        var checkPoint3 = new CheckPoint();
        checkPoint3.setCheckPointNumber(3);
        space.getActions().add(checkPoint3);

        space = board.getSpace(2,4);
        space.getWalls().add(Heading.SOUTH);
        space.getWalls().add(Heading.NORTH);
        var checkPoint4 = new CheckPoint();
        checkPoint4.setCheckPointNumber(4);
        checkPoint4.setLastCP(true);
        space.getActions().add(checkPoint4);

        space = board.getSpace(0,4);
        space.getWalls().add(Heading.NORTH);
        action = new ConveyorBelt();
        action.setHeading(Heading.EAST);
        space.getActions().add(action);

        space = board.getSpace(1,4);
        space.getWalls().add(Heading.SOUTH);
        action = new ConveyorBelt();
        action.setHeading(Heading.EAST);
        space.getActions().add(action);

        space = board.getSpace(4,5);
        space.getWalls().add(Heading.NORTH);
        action = new ConveyorBelt();
        action.setHeading(Heading.WEST);
        space.getActions().add(action);

        return board;

    }

}
