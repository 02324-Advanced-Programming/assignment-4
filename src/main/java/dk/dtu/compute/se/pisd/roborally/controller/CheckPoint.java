package dk.dtu.compute.se.pisd.roborally.controller;

import dk.dtu.compute.se.pisd.roborally.model.Phase;
import dk.dtu.compute.se.pisd.roborally.model.Player;
import dk.dtu.compute.se.pisd.roborally.model.Space;

/**
 * Checkpoints are locations on the board, and when all of them have been 'collected' the phase is changed to winner and the game is over.
 * checkpointNumber is the number of the checkpoint, and lastCP is the boolean indicating whether it is the last checkpoint.
 * @author s235444
 */
public class CheckPoint extends FieldAction{

    private int checkPointNumber;

    private boolean lastCP;

    /**
     * A getter method for the checkpoints number.
     * @return the checkpoints number
     */
    public int getCheckPointNumber() {
        return checkPointNumber;
    }

    /**
     *
     * @param checkPointNumber The number of the checkpoint.
     * @return boolean indicating if it is the last checkpoint.
     */
    public boolean getLastCP(int checkPointNumber) {
        return lastCP;
    }

    /**
     * A setter method for the checkPointNumber.
     * @param checkPointNumber
     */
    public void setCheckPointNumber(int checkPointNumber) {
        this.checkPointNumber = checkPointNumber;
    }

    /**
     * A setter method for lastCP.
     * @param lastCP boolean
     */
    public void setLastCP(boolean lastCP) {
        this.lastCP = lastCP;
    }


    /**
     * Logic for the checkpoints. It checks whether the checkpoints have been collected in the correct order.
     * When the player reaches the last checkpoint correctly, the phase is changed to winner.
     * Missing:
     * - Winner phase implementation for popup and when to check for winner status.
     *
     * @param gameController the gameController of the respective game
     * @param space the space this action should be executed for
     * @return Whether the CP was collected.
     */
    @Override
    public boolean doAction(GameController gameController, Space space) {
        Player player = space.getPlayer();
        if (player.getCollectedCP() + 1 == checkPointNumber) {
            player.setCollectedCP(checkPointNumber);

            if (lastCP) {
                gameController.board.setPhase(Phase.WINNER);
            }
            return true;
        }

        return false;
    }
}
