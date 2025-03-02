/*
 *  This file is part of the initial project provided for the
 *  course "Project in Software Development (02362)" held at
 *  DTU Compute at the Technical University of Denmark.
 *
 *  Copyright (C) 2019, 2020: Ekkart Kindler, ekki@dtu.dk
 *
 *  This software is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; version 2 of the License.
 *
 *  This project is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this project; if not, write to the Free Software
 *  Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 *
 */
package dk.dtu.compute.se.pisd.roborally.controller;

import dk.dtu.compute.se.pisd.designpatterns.observer.Observer;
import dk.dtu.compute.se.pisd.designpatterns.observer.Subject;
import dk.dtu.compute.se.pisd.roborally.RoboRally;
import dk.dtu.compute.se.pisd.roborally.model.Board;
import dk.dtu.compute.se.pisd.roborally.model.Player;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceDialog;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * ...
 *
 * @author Ekkart Kindler, ekki@dtu.dk
 *
 */
public class AppController implements Observer {

    final private List<Integer> PLAYER_NUMBER_OPTIONS = Arrays.asList(2, 3, 4, 5, 6);
    final private List<String> PLAYER_COLORS = Arrays.asList("red", "green", "blue", "orange", "grey", "magenta");
    final private List<String> BOARD_NAMES = Arrays.asList("classic", "advanced");

    final private RoboRally roboRally;

    private GameController gameController;
    BoardFactory boardFactory = BoardFactory.getInstance();


    public AppController(@NotNull RoboRally roboRally) {
        this.roboRally = roboRally;
    }

    public void newGame() {

        // ask the user to select a board type
        List<String> availableBoards = boardFactory.getBoardNames();
//        var availableBoards = List.of("board1","board2");
        ChoiceDialog<String> boardDialog = new ChoiceDialog<>(availableBoards.get(0), availableBoards);
        boardDialog.setTitle("Select Board");
        boardDialog.setHeaderText("Choose a board type");
        Optional<String> boardResult = boardDialog.showAndWait();

<<<<<<< Updated upstream
        if (boardResult.isEmpty()) {
            return; // User canceled the board selection
=======
            // XXX the board should eventually be created programmatically or loaded from a file
            //     here we just create an empty board with the required number of players.
            ChoiceDialog<String> dialog2 = new ChoiceDialog<>(BOARD_NAMES.get(0), BOARD_NAMES);
            dialog2.setTitle("Board type");
            dialog2.setHeaderText("Select type of board");
            Optional<String> result2 = dialog2.showAndWait();
            Board board = new Board(8,8);
            gameController = new GameController(board);
            int no = result.get();
            for (int i = 0; i < no; i++) {
                Player player = new Player(board, PLAYER_COLORS.get(i), "Player " + (i + 1));
                board.addPlayer(player);
                player.setSpace(board.getSpace(i % board.width, i));
            }

            // XXX V2
            gameController.startProgrammingPhase();

            roboRally.createBoardView(gameController);
>>>>>>> Stashed changes
        }
        String selectedBoard = boardResult.get();

        //ask user for number of players
        ChoiceDialog<Integer> playerDialog = new ChoiceDialog<>(PLAYER_NUMBER_OPTIONS.get(0), PLAYER_NUMBER_OPTIONS);
        playerDialog.setTitle("Player number");
        playerDialog.setHeaderText("Select number of players");
        Optional<Integer> playerResult = playerDialog.showAndWait();

        if (playerResult.isEmpty()) {
            return; // User canceled the player selection
        }
        int noOfPlayers = playerResult.get();

        Board board = boardFactory.createBoard(selectedBoard);

        if (gameController != null) {
            // The UI should not allow this, but in case this happens anyway,
            // give the user the option to save the game or abort this operation!
            if (!stopGame()) {
                return;
            }
        }

        gameController = new GameController(board);

        // Add players to the board
        for (int i = 0; i < noOfPlayers; i++) {
            Player player = new Player(board, PLAYER_COLORS.get(i), "Player " + (i + 1));
            board.addPlayer(player);
            player.setSpace(board.getSpace(i % board.width, i));
        }

        // Start the programming phase
        gameController.startProgrammingPhase();

        // Update the UI with the new board
        roboRally.createBoardView(gameController);
    }

    public void saveGame() {
        // TODO V4a: needs to be implemented
    }

    public void loadGame() {
        // TODO V4a: needs to be implemented
        // for now, we just create a new game
        if (gameController == null) {
            newGame();
        }
    }

    /**
     * Stop playing the current game, giving the user the option to save
     * the game or to cancel stopping the game. The method returns true
     * if the game was successfully stopped (with or without saving the
     * game); returns false, if the current game was not stopped. In case
     * there is no current game, false is returned.
     *
     * @return true if the current game was stopped, false otherwise
     */
    public boolean stopGame() {
        if (gameController != null) {

            // here we save the game (without asking the user).
            saveGame();

            gameController = null;
            roboRally.createBoardView(null);
            return true;
        }
        return false;
    }

    public void exit() {
        if (gameController != null) {
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Exit RoboRally?");
            alert.setContentText("Are you sure you want to exit RoboRally?");
            Optional<ButtonType> result = alert.showAndWait();

            if (!result.isPresent() || result.get() != ButtonType.OK) {
                return; // return without exiting the application
            }
        }

        // If the user did not cancel, the RoboRally application will exit
        // after the option to save the game
        if (gameController == null || stopGame()) {
            Platform.exit();
        }
    }

    public boolean isGameRunning() {
        return gameController != null;
    }


    @Override
    public void update(Subject subject) {
        // XXX do nothing for now
    }

}
