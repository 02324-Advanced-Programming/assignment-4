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

import dk.dtu.compute.se.pisd.roborally.model.*;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;


/**
 * ...
 *
 * @author Ekkart Kindler, ekki@dtu.dk
 */
public class GameController {

    final public Board board;

    public GameController(@NotNull Board board) {
        this.board = board;
    }

    /**
     * This is just some dummy controller operation to make a simple move to see something
     * happening on the board. This method should eventually be deleted!
     *
     * @param space the space to which the current player should move
     */
    public void moveCurrentPlayerToSpace(@NotNull Space space)  {

        if (space.getPlayer() == null && board.getCurrentPlayer() != null ) {
            space.setPlayer(board.getCurrentPlayer());
            board.setCounter(board.getCounter() + 1);
        }
        int nextPlayer = (board.getPlayerNumber(board.getCurrentPlayer()) +1) % board.getPlayersNumber();
        board.setCurrentPlayer(board.getPlayer(nextPlayer));
    }



    /**
     * The function to initiate the programming phase, which
     * is the phase/state where the user is able to drag-and-drop cards from the
     * bottom, shuffled, panel of random movement cards, up to the card-slots, which -
     * if the cards are placed inside of any of these - will be executed/acted-upon during
     * the next execution phase.
     * Returns nothing, as this function only begins this phase and makes everything ready.
     */
    public void startProgrammingPhase() {
        board.setPhase(Phase.PROGRAMMING);
        board.setCurrentPlayer(board.getPlayer(0));
        board.setStep(0);

        for (int i = 0; i < board.getPlayersNumber(); i++) {
            Player player = board.getPlayer(i);
            if (player != null) {
                for (int j = 0; j < Player.NO_REGISTERS; j++) {
                    CommandCardField field = player.getProgramField(j);
                    field.setCard(null);
                    field.setVisible(true);
                }
                for (int j = 0; j < Player.NO_CARDS; j++) {
                    CommandCardField field = player.getCardField(j);
                    field.setCard(generateRandomCommandCard());
                    field.setVisible(true);
                }
            }
        }
    }

    /**
     * This function generates each and every random movement card in the bottom of
     * the player's action-screen. These cards can then be dragged into the just-above-fields
     * for executing cards. These cards are, ofc, randomly generated, so that we avoid
     * any bias of action.
     * @return a command card, which can then be moved into the execution-of-cards-places
     * during the programming-phase and before the execution-phase is initiated.
     */
    private CommandCard generateRandomCommandCard() {
        Command[] commands = Command.values();
        int random = (int) (Math.random() * commands.length);
        return new CommandCard(commands[random]);
    }


    /**
     * This function is called in the specific situation where the user is done
     * with selecting cards for the user's robot, and then after that, then the user clicks
     * the "Finish programming" button, and the button triggers this function, which effectively
     * changes/transitions the state of the game, and then it is the other user's turn, by changing phase.
     */

    public void finishProgrammingPhase() {
        makeProgramFieldsInvisible();
        makeProgramFieldsVisible(0);
        board.setPhase(Phase.ACTIVATION);
        board.setCurrentPlayer(board.getPlayer(0));
        board.setStep(0);
    }

    /**
     * The program fields are the fields that the user can select to put cards into.
     * @param register is the index of the register, which the user can place a game card into.
     */

    private void makeProgramFieldsVisible(int register) {
        if (register >= 0 && register < Player.NO_REGISTERS) {
            for (int i = 0; i < board.getPlayersNumber(); i++) {
                Player player = board.getPlayer(i);
                CommandCardField field = player.getProgramField(register);
                field.setVisible(true);
            }
        }
    }

    /**
     * Effectively - as the function states - hides the sequence-buttons on the display,
     * and this is a part of the transitioning phase from the programming phase to the execution phase.
     */

    private void makeProgramFieldsInvisible() {
        for (int i = 0; i < board.getPlayersNumber(); i++) {
            Player player = board.getPlayer(i);
            for (int j = 0; j < Player.NO_REGISTERS; j++) {
                CommandCardField field = player.getProgramField(j);
                field.setVisible(false);
            }
        }
    }

    /**
     * After the player-command-card-sequence has been set up, this function is activated - via the side-panel -
     * and it triggers a new mode where the robot is no longer controlled by the user's direct clicking,
     * but instead by the sequence of command cards placed prior to this action.
     */

    public void executePrograms() {
        board.setStepMode(false);
        continuePrograms();
    }

    /**
     * This is roughly the same function as the executePrograms(), except for a major difference;
     * this executeStep will allow the user to take only 1 step pr time, instead of the sequence
     * of pre-programmed cards.
     */
    public void executeStep() {
        board.setStepMode(true);
        continuePrograms();
    }

    /**
     * This is a method used to execute the actions of fields. An example could be Checkpoints.
     *
     * @author s235444
     */
    private void executeFieldActions() {
        for (int x = 0; x < board.getPlayersNumber(); x++) {
            Player player = board.getPlayer(x);
            Space space = player.getSpace();

            for (FieldAction action : space.getActions()) {
                try {
                    action.doAction(this, space);
                } catch (ImpossibleMoveException e) {
                    System.err.println("Action could not be executed: " + e.getMessage());
                }

                if (board.getPhase() != Phase.ACTIVATION) {
                    break;
                }
            }
        }
    }


    /**
     This method is used to create a popup declaring who is the Winner of the game.
     @author s235444
      * @param message is a string which declares the winner.
     */
    private void displayPopup(String message){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("GAME OVER THERE IS A WINNER");
        alert.setHeaderText(null);
        alert.setContentText(message);
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.setAlwaysOnTop(true);
        alert.setOnHidden(event -> Platform.exit());

        alert.showAndWait();
    }

    /**
     * Called when the program's step mode is disabled, and the game simply needs to
     * keep executing the command cards.
     */
    private void continuePrograms() {
        do {
            executeNextStep();
        } while (board.getPhase() == Phase.ACTIVATION && !board.isStepMode());
    }

    /**
     * A stepwise function which allows the user to have the command cards execute pr request
     * - one at a time - instead of executing all of them automatically all at once.
     */
    private void executeNextStep() {
        Player currentPlayer = board.getCurrentPlayer();
        if (board.getPhase() == Phase.ACTIVATION && currentPlayer != null) {
            int step = board.getStep();
            if (step >= 0 && step < Player.NO_REGISTERS) {
                CommandCard card = currentPlayer.getProgramField(step).getCard();
                if (card != null) {

                    if (card.command == Command.LEFT_OR_RIGHT) {
                        board.setPhase(Phase.PLAYER_INTERACTION);
                        board.notifyChange();
                        return;
                    }
                    Command command = card.command;
                    executeCommand(currentPlayer, command);
                }
                int nextPlayerNumber = board.getPlayerNumber(currentPlayer) + 1;
                if (nextPlayerNumber < board.getPlayersNumber()) {
                    board.setCurrentPlayer(board.getPlayer(nextPlayerNumber));
                } else {

                    executeFieldActions();

                    if (board.getPhase() != Phase.ACTIVATION && board.getPhase() == Phase.WINNER) {
                        // get winner message should popup here.
                        displayPopup(board.getWonMessage());

                        return;
                    }

                    step++;
                    if (step < Player.NO_REGISTERS) {
                        makeProgramFieldsVisible(step);
                        board.setStep(step);
                        board.setCurrentPlayer(board.getPlayer(0));
                    } else {
                        startProgrammingPhase();
                    }
                }
            } else {
                // this should not happen
                assert false;
            }
        } else {
            // this should not happen
            assert false;
        }
    }

    /**
     * When having given the robot a specific command through the drag-and-drop-field
     * of command cards, executeCommand will be called to make that specific action happen
     * for the robot in question.
     * @param player Is the player, whose robot on the board will have this specific execution of action.
     * @param command Is the command that the robot will receive, and then this function
     *                aids with the implementation of the action itself.
     */
    
    private void executeCommand(@NotNull Player player, Command command) {
        if (player.board == board && command != null) {
            switch (command) {
                case FORWARD:
                    this.moveForward(player);
                    break;
                case RIGHT:
                    this.turnRight(player);
                    break;
                case LEFT:
                    this.turnLeft(player);
                    break;
                case FAST_FORWARD:
                    this.fastForward(player);
                    break;
                case UTURN:
                    this.uTurn(player);
                    break;
                case BACKWARD:
                    this.moveBackward(player);
                    break;
                default:

            }
        }
    }


    /**
     * method to turn right or left
     * @param direction specifies the direction to turn
     */
    public void executeLeftOrRight(Command direction) {
        Player currentPlayer = board.getCurrentPlayer();

        executeCommand(currentPlayer, direction);
        board.setPhase(Phase.ACTIVATION);

        advanceGameState();

    }

    /**
     * Move to a player's turn or move a step forward in the game
     * If more players who need to execute current step , move to next player's turn.
     * If all players executed current step, move to the next step.
     * If all steps executed, transition to programming phase.
     */
    private void advanceGameState() {
        Player currentPlayer = board.getCurrentPlayer();
        int nextPlayerNumber = board.getPlayerNumber(currentPlayer) + 1;

        // check if more players need to execute current step
        if (nextPlayerNumber < board.getPlayersNumber()) {
            board.setCurrentPlayer(board.getPlayer(nextPlayerNumber));
            return;
        }

        int nextStep = board.getStep() + 1;
        // check all steps have been executed
        if (nextStep >= Player.NO_REGISTERS) {
            startProgrammingPhase();
            return;
        }
        makeProgramFieldsVisible(nextStep);
        board.setStep(nextStep);
        board.setCurrentPlayer(board.getPlayer(0));
    }




    /**
     * Moves the player forward on the current board in the current direction. Will wrap around if the player is at the boundaries of the board.
     *
     * @param player the player which should be moved
     */
    public void moveForward(@NotNull Player player) {
        if (player.board == board) {
            Space forward = this.board.getNeighbour(player.getSpace(), player.getHeading());

            Heading heading = player.getHeading();
            if (forward != null) {
                try {
                    moveToSpace(player, forward, heading);
                } catch (ImpossibleMoveException e) {
                }
            }
        }
    }

        /**
         * Moves the player backward on the current board in the current direction. Will wrap around if the player is at the boundaries of the board.
         *
         * @param player the player which should be moved
         */
        public void moveBackward (@NotNull Player player) {
            if (player.board == board) {
                Heading heading = player.getHeading();
                Heading oppositeHeading = player.getHeading().next().next();
                Space backward = this.board.getNeighbour(player.getSpace(), oppositeHeading);
                if (backward != null) {
                    try {
                        moveToSpace(player, backward, oppositeHeading);
                    } catch (ImpossibleMoveException e) {
                    }
                }
            }
        }

    /**
     * Reverses the Heading of the given player on the current board.
     *
     * @param player The player, whose direction should be changed.
     */
    public void uTurn(@NotNull Player player) {
        player.setHeading(player.getHeading().next().next());
    }


    /**
     * Moves the player forward twice on the current board in the current direction. Will wrap around if the player is at the boundaries of the board.
     *
     * @param player the player which should be moved
     */
    public void fastForward(@NotNull Player player) {
        moveForward(player);
        moveForward(player);
    }


        /**
         * Turns the direction of the player clockwise 90 degrees on the current board.
         *
         * @param player the player which should be turned
         */
        public void turnRight (@NotNull Player player){
            player.setHeading(player.getHeading().next());
        }

        /**
         * Turns the direction of the player counter-clockwise 90 degrees on the current board.
         *
         * @param player the player which should be turned
         */
        public void turnLeft (@NotNull Player player){
            player.setHeading(player.getHeading().prev());
        }

        public boolean moveCards (@NotNull CommandCardField source, @NotNull CommandCardField target){
            CommandCard sourceCard = source.getCard();
            CommandCard targetCard = target.getCard();
            if (sourceCard != null && targetCard == null) {
                target.setCard(sourceCard);
                source.setCard(null);
                return true;
            } else {
                return false;
            }
        }

        /**
         * A method called when no corresponding controller operation is implemented yet.
         * This should eventually be removed.
         */
        public void notImplemented () {
            // XXX just for now to indicate that the actual method is not yet implemented
            assert false;
        }

    /**
     * A recursive method that when moving into another player pushes them in the given direction if the next space is available.
     *
     * @param pusher  the player who's moving card is being used
     * @param space   the space we want to move into
     * @param heading the direction in which the player is moving
     * @throws ImpossibleMoveException If the move is impossible it throws an exception
     */
    public void moveToSpace(@NotNull Player pusher,
                            @NotNull Space space,
                            @NotNull Heading heading) throws ImpossibleMoveException {
        Player pushed = space.getPlayer();
        if (pushed != null) {
            Space nextSpace = board.getNeighbour(space, heading);
            if (nextSpace != null) {
                moveToSpace(pushed, nextSpace, heading);
            } else {
                throw new ImpossibleMoveException(pusher, space, heading);
            }
        }
        pusher.setSpace(space);
    }
}

