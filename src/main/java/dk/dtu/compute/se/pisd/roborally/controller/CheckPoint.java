package dk.dtu.compute.se.pisd.roborally.controller;

import dk.dtu.compute.se.pisd.roborally.model.Space;

/**
 * Checkpoints are locations on the board, and when all of them have been 'collected' the phase is changed to winner and the game is over.
 * checkpointNumber is the number of the checkpoint, and lastCP is the boolean indicating whether it is the last checkpoint.
 * @s235444
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
     * @param checkPointNumber
     * @return
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
     * @param lastCP
     */
    public void setLastCP(boolean lastCP) {
        this.lastCP = lastCP;
    }

    @Override
    public boolean doAction(GameController gameController, Space space) {
        return false;
    }
}
