package dk.dtu.compute.se.pisd.roborally.controller;

import dk.dtu.compute.se.pisd.roborally.model.Board;
import dk.dtu.compute.se.pisd.roborally.model.Heading;
import dk.dtu.compute.se.pisd.roborally.model.Space;

import java.util.*;
import java.util.function.Function;

/**
 * A factory for creating boards. The factory itself is implemented as a singleton.
 *
 * @author Ekkart Kindler, ekki@dtu.dk
 */
// XXX A3: might be used for creating a first slightly more interesting board.
public class BoardFactory {

    /**
     * The single instance of this class, which is lazily instantiated on demand.
     */
    static private BoardFactory instance = null;

    /**
     * Constructor for BoardFactory. It is private in order to make the factory a singleton.
     */
//    private BoardFactory() {
//    }

    //the list of boards
//    private final List<String> availableBoardNames = List.of("default", "advanced");

    private final Map<String, Function<Board, Board>> boardCreators = new HashMap<>();

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

    //might need to check something for null
//    public List<String> getBoardNames() {
//        return Collections.unmodifiableList(availableBoardNames);
//    }

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

        Space space = board.getSpace(0,0);
        space.getWalls().add(Heading.SOUTH);
        ConveyorBelt action  = new ConveyorBelt();
        action.setHeading(Heading.WEST);
        space.getActions().add(action);

        space = board.getSpace(1,0);
        space.getWalls().add(Heading.NORTH);
        action  = new ConveyorBelt();
        action.setHeading(Heading.WEST);
        space.getActions().add(action);

        space = board.getSpace(1,1);
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
        var checkPoint3 = new CheckPoint();
        checkPoint3.setCheckPointNumber(3);
        space.getActions().add(checkPoint3);

        space = board.getSpace(5,3);
        var checkPoint4 = new CheckPoint();
        checkPoint4.setCheckPointNumber(4);
        space.getActions().add(checkPoint4);

        return board;

    }
    Board createAdvancedBoard(Board board){
        Space space = board.getSpace(0,0);
        space.getWalls().add(Heading.SOUTH);
        ConveyorBelt action  = new ConveyorBelt();
        action.setHeading(Heading.WEST);
        space.getActions().add(action);

        space = board.getSpace(1,0);
        space.getWalls().add(Heading.NORTH);
        action  = new ConveyorBelt();
        action.setHeading(Heading.WEST);
        space.getActions().add(action);

        space = board.getSpace(1,1);
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


        space = board.getSpace(3,3);
        action  = new ConveyorBelt();
        action.setHeading(Heading.EAST);
        space.getActions().add(action);


        space = board.getSpace(4,6);
        action  = new ConveyorBelt();
        action.setHeading(Heading.NORTH);
        space.getActions().add(action);
        return board;

    }

}
