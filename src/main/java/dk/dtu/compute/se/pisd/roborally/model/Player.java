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

import static dk.dtu.compute.se.pisd.roborally.model.Heading.SOUTH;

/**
 * ...
 *
 * Made changes to the player to implement checkpoint and winner features. New fields and methods were added.
 *
 * @author Ekkart Kindler, ekki@dtu.dk
 * @author s235444
 */
public class Player extends Subject {

    final public static int NO_REGISTERS = 5;
    final public static int NO_CARDS = 8;

    final public Board board;

    private String name;
    private String color;

    private Space space;
    private Heading heading = SOUTH;

    private CommandCardField[] program;
    private CommandCardField[] cards;

    private int collectedCP;
    private boolean winner;

    public Player(@NotNull Board board, String color, @NotNull String name) {
        this.board = board;
        this.name = name;
        this.color = color;

        this.space = null;

        collectedCP = 0;
        winner = false;

        program = new CommandCardField[NO_REGISTERS];
        for (int i = 0; i < program.length; i++) {
            program[i] = new CommandCardField(this);
        }

        cards = new CommandCardField[NO_CARDS];
        for (int i = 0; i < cards.length; i++) {
            cards[i] = new CommandCardField(this);
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name != null && !name.equals(this.name)) {
            this.name = name;
            notifyChange();
            if (space != null) {
                space.playerChanged();
            }
        }
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
        notifyChange();
        if (space != null) {
            space.playerChanged();
        }
    }

    public Space getSpace() {
        return space;
    }

    /**
     * Checks that the new space exists, and that it does not match the old space
     * (so, actual new space),
     * Also, we set null on the player on the old board and place it on the new board.
     * Then ofc, broadcast the changes.
     *
     * @param space This space is a property of the player (so, a reference on the player
     *              to the board/field), so the player sets its own field here.
     * @return Nothing, as we are simply setting a new player-state. Also, broadcasts changes.
     */
    public void setSpace(Space space) {
        Space oldSpace = this.space;
        if (space != oldSpace &&
                (space == null || space.board == this.board)) {
            this.space = space;
            if (oldSpace != null) {
                oldSpace.setPlayer(null);
            }
            if (space != null) {
                space.setPlayer(this);
            }
            notifyChange();
        }
    }

    public Heading getHeading() {
        return heading;
    }

    public void setHeading(@NotNull Heading heading) {
        if (heading != this.heading) {
            this.heading = heading;
            notifyChange();
            if (space != null) {
                space.playerChanged();
            }
        }
    }

    public CommandCardField getProgramField(int i) {
        return program[i];
    }

    public CommandCardField getCardField(int i) {
        return cards[i];
    }

    /**
     * A method to check whether a player is a winner or not.
     * @return boolean of winner status.
     */
    public boolean isWinner() {
        return winner;
    }

    /**
     * A method to set a player to have the winner status as true.
     * @param player The player who is to be set as a winner.
     */
    public void setWinner(Player player) {
        player.winner = true;
    }


    /**
     * A method to get the players latest collected checkpoint.
     * @return int of players highest value checkpoint.
     */
    public int getCollectedCP() {
        return collectedCP;
    }

    /**
     * A setter method for the players highest value collected checkpoint. It also calls notifyChange();
     * @param CPNumber int wished to set the players highest checkpoint to.
     */
    public void setCollectedCP (int CPNumber) {
        this.collectedCP = CPNumber;
        notifyChange();
    }

    @Override
    public String toString() {
        String spaceDescription = (space != null) ? "x=" + space.x + ", y=" + space.y : "No space assigned";
        return "Player{" +
                "name='" + name + '\'' +
                ", color='" + color + '\'' +
                ", heading=" + heading +
                ", space=(" + spaceDescription + ")" +
                ", collectedCP=" + collectedCP +
                ", winner=" + winner +
                '}';
    }


}
