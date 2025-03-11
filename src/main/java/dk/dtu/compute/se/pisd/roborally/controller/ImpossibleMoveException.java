package dk.dtu.compute.se.pisd.roborally.controller;

import dk.dtu.compute.se.pisd.roborally.model.Heading;
import dk.dtu.compute.se.pisd.roborally.model.Player;
import dk.dtu.compute.se.pisd.roborally.model.Space;
import org.jetbrains.annotations.NotNull;

public class ImpossibleMoveException extends Throwable {
    public ImpossibleMoveException(@NotNull Player pusher, @NotNull Space space, @NotNull Heading heading) {
    }
}
