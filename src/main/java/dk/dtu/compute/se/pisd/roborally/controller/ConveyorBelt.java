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

import dk.dtu.compute.se.pisd.roborally.model.Board;
import dk.dtu.compute.se.pisd.roborally.model.Heading;
import dk.dtu.compute.se.pisd.roborally.model.Player;
import dk.dtu.compute.se.pisd.roborally.model.Space;
import org.jetbrains.annotations.NotNull;

import javax.print.attribute.standard.PrinterMakeAndModel;

/**
 * This class represents a conveyor belt on a space.
 *
 * @author Ekkart Kindler, ekki@dtu.dk
 */
public class ConveyorBelt extends FieldAction {

    private Heading heading;

    /**
     * Gets the direction of the conveyorBelt; i.e. which way it sends the player.
     */

    public Heading getHeading() {
        return heading;
    }

    /**
     * Sets the heading of the conveyorBel.
     * @param heading The enum to which is assigned a string of direction.
     */
    public void setHeading(Heading heading) {
        this.heading = heading;
    }

    /**
     *Executes the action of the conveyor belt.
     * If player is on a conveyor belt, the player will be moved to the next space in the direction of the conveyor belt.
     * @param gameController the gameController of the respective game
     * @param space the space this action should be executed for
     * @return Whether the requested action was actually carried out.
     */
    @Override
    public boolean doAction(@NotNull GameController gameController, @NotNull Space space) throws ImpossibleMoveException {
        Player player = space.getPlayer();
        Heading heading = getHeading();
        try {
            Space nextSpace = gameController.board.getNeighbour(space,heading);
            if(nextSpace!=null){
                gameController.moveToSpace(player, nextSpace, heading);
            }
            return true;
        } catch (ImpossibleMoveException e){
            return false;
        }
    }

}
