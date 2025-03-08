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
package dk.dtu.compute.se.pisd.roborally.model;

import dk.dtu.compute.se.pisd.designpatterns.observer.Subject;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import static dk.dtu.compute.se.pisd.roborally.model.Phase.INITIALISATION;

/**
 * This represents a RoboRally game board. Which gives access to
 * all the information of current state of the games. Note that
 * the terms board and game are used almost interchangeably.
 *
 * @author Ekkart Kindler, ekki@dtu.dk
 *
 */
public class Board extends Subject {

    public final int width;

    public final int height;

    public final String boardName;

    private Integer gameId;

    private final Space[][] spaces;

    private final List<Player> players = new ArrayList<>();

    private Player current;

    private Phase phase = INITIALISATION;

    private int step = 0;

    private boolean stepMode;

    private int counter;

    public Board(int width, int height, @NotNull String boardName) {
        this.boardName = boardName;
        this.width = width;
        this.height = height;
        spaces = new Space[width][height];
        for (int x = 0; x < width; x++) {
            for(int y = 0; y < height; y++) {
                Space space = new Space(this, x, y);
                spaces[x][y] = space;
            }
        }
        this.stepMode = false;
    }

    public Board(int width, int height) {
        this(width, height, "defaultboard");
    }

    public Integer getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        if (this.gameId == null) {
            this.gameId = gameId;
        } else {
            if (!this.gameId.equals(gameId)) {
                throw new IllegalStateException("A game with a set id may not be assigned a new id!");
            }
        }
    }

    public Space getSpace(int x, int y) {
        if (x >= 0 && x < width &&
                y >= 0 && y < height) {
            return spaces[x][y];
        } else {
            return null;
        }
    }

    public int getPlayersNumber() {
        return players.size();
    }

    public void addPlayer(@NotNull Player player) {
        if (player.board == this && !players.contains(player)) {
            players.add(player);
            notifyChange();
        }
    }

    public Player getPlayer(int i) {
        if (i >= 0 && i < players.size()) {
            return players.get(i);
        } else {
            return null;
        }
    }
    /**
     *
     * Returns the current player, i.e. of type Player.
     * Contained in the Board property "current", so this is a part
     * of the Board class; it is also accessed through that object type.
     * @return the Board's current player.
     * */
    public Player getCurrentPlayer() {
        return current;
    }

    /**
     *
     * This function sets a new, current player and ensures that the player is
     * actually a participant of the game currently played.
     * @param player is the param that sets the Board-object's property
     *               of the same name. It also has an internal check that
     *               this player is a non-current player, and that
     *               it is actually a participating player inside the game.
     * @return Nothing, as we are merely setting the current player, and then
     * notifying listeners that the change has happened.
     */
    public void setCurrentPlayer(Player player) {
        if (player != this.current && players.contains(player)) {
            this.current = player;
            notifyChange();
        }
    }

    public Phase getPhase() {
        return phase;
    }

    public void setPhase(Phase phase) {
        if (phase != this.phase) {
            this.phase = phase;
            notifyChange();
        }
    }

    /**
     * Returns the current step of the game.
     *
     * @return the current step
     */
    public int getStep() {
        return step;
    }

    public void setStep(int step) {
        if (step != this.step) {
            this.step = step;
            notifyChange();
        }
    }

    public boolean isStepMode() {
        return stepMode;
    }

    public void setStepMode(boolean stepMode) {
        if (stepMode != this.stepMode) {
            this.stepMode = stepMode;
            notifyChange();
        }
    }

    public int getPlayerNumber(@NotNull Player player) {
        if (player.board == this) {
            return players.indexOf(player);
        } else {
            return -1;
        }
    }

    /**
     * Returns the neighbour of the given space of the board in the given heading.
     * The neighbour is returned only, if it can be reached from the given space
     * (no walls or obstacles in either of the involved spaces); otherwise,
     * null will be returned.
     *
     * @param space the space for which the neighbour should be computed
     * @param heading the heading of the neighbour
     * @return the space in the given direction; null if there is no (reachable) neighbour
     */
    public Space getNeighbour(@NotNull Space space, @NotNull Heading heading) {
        // TODO A3: This implementation needs to be adjusted so that walls on
        //          spaces (and maybe other obstacles) are taken into account
        //          (see above JavaDoc comment for this method).
        int x = space.x;
        int y = space.y;
        switch (heading) {
            case SOUTH:
                y = (y + 1) % height;
                break;
            case WEST:
                x = (x + width - 1) % width;
                break;
            case NORTH:
                y = (y + height - 1) % height;
                break;
            case EAST:
                x = (x + 1) % width;
                break;
        }

        return getSpace(x, y);
    }

    /**
     *
     * This function returns a string representation - across two lines - of
     * where a certain player is located currently on the board.
     * @return A two-lined string of the player's status and position on board.
     */
    public String getStatusMessage() {
        // this is actually a view aspect, but for making assignment V1 easy for
        // the students, this method gives a string representation of the current
        // status of the game
        
        // TODO V2: changed the status so that it shows the phase, the current player, and the current register
        return "Player = " + getCurrentPlayer().getName()
                + " Amount of steps: " + getCounter();
    }

    /**
     * A stringbuilder method, which returns a string containing the winner or winners of the game.
     *
     * @author s235444
     * @return a string used to declare the winner
     */
    private String wonMessage() {
        Player winner = players.getFirst();
        for (Player player : players) {
            if (player.getCollectedCP() > winner.getCollectedCP()) {
                winner = player;
            }
        }

        // The stringbuilder which also iterates through the players to check if there are multiple winners and also adds them as winners if necessary.
        StringBuilder wonMessage = new StringBuilder("The winner is " + winner.getName() + " who has collected " + winner.getCollectedCP() + " checkpoints during this game.\n");
        int x = getPlayersNumber() - 1;
        while (x >= 0) {

            if(getPlayer(x).equals(winner)) {
                x--;
                continue;
            }

            wonMessage.append(getPlayer(x).getName()).append(" : ").append(getPlayer(x).getCollectedCP()).append(" checkpoints during this game.\n");
            x--;
        }
        return wonMessage.toString();
    }

    public String getWonMessage() {
        return wonMessage();
    }


    /**
     * Returns the current counter value.
     *
     * @return the current counter value
     */
    public int getCounter() {
        return counter;
    }

    /**
     * Sets the counter to the specified value (should generally increment by 1)
     * and notifies observers of the change.
     * This method also notifies observers of any change.
     *
     */
    public void setCounter(int counter) {
        this.counter = counter;
        notifyChange();
    }
}
