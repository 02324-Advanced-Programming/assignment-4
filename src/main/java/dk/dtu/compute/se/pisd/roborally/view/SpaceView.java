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
package dk.dtu.compute.se.pisd.roborally.view;

import dk.dtu.compute.se.pisd.designpatterns.observer.Subject;
import dk.dtu.compute.se.pisd.roborally.controller.ConveyorBelt;
import dk.dtu.compute.se.pisd.roborally.controller.FieldAction;
import dk.dtu.compute.se.pisd.roborally.model.Heading;
import dk.dtu.compute.se.pisd.roborally.model.Player;
import dk.dtu.compute.se.pisd.roborally.model.Space;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.StrokeLineCap;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;
import java.util.List;

/**
 * ...
 *
 * @author Ekkart Kindler, ekki@dtu.dk
 *
 */
public class SpaceView extends StackPane implements ViewObserver {

    final public static int SPACE_HEIGHT = 40; // 60; // 75;
    final public static int SPACE_WIDTH = 40;  // 60; // 75;

    public final Space space;


    public SpaceView(@NotNull Space space) {
        this.space = space;

        // XXX the following styling should better be done with styles
        this.setPrefWidth(SPACE_WIDTH);
        this.setMinWidth(SPACE_WIDTH);
        this.setMaxWidth(SPACE_WIDTH);

        this.setPrefHeight(SPACE_HEIGHT);
        this.setMinHeight(SPACE_HEIGHT);
        this.setMaxHeight(SPACE_HEIGHT);

        if ((space.x + space.y) % 2 == 0) {
            this.setStyle("-fx-background-color: white;");
        } else {
            this.setStyle("-fx-background-color: black;");
        }


        // updatePlayer();

        // This space view should listen to changes of the space
        space.attach(this);

        drawWalls();
        drawConveyorBelts();

        update(space);
    }

    private void updatePlayer() {
        Player player = space.getPlayer();
        if (player != null) {
            Polygon arrow = new Polygon(0.0, 0.0,
                    10.0, 20.0,
                    20.0, 0.0);
            try {
                arrow.setFill(Color.valueOf(player.getColor()));
            } catch (Exception e) {
                arrow.setFill(Color.MEDIUMPURPLE);
            }

            arrow.setRotate((90 * player.getHeading().ordinal()) % 360);
            this.getChildren().add(arrow);
        }
    }

    private void drawWalls() {
        Canvas canvas = new Canvas(SPACE_WIDTH, SPACE_HEIGHT);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setStroke(Color.RED);
        gc.setLineWidth(5);
        gc.setLineCap(StrokeLineCap.ROUND);

        List<Heading> walls = space.getWalls();
        for (Heading heading : walls) {
            switch (heading) {
                case NORTH:
                    gc.strokeLine(3, 2, SPACE_WIDTH - 3, 2);
                    break;
                case EAST:
                    gc.strokeLine(SPACE_WIDTH - 2, 3, SPACE_WIDTH - 2, SPACE_HEIGHT - 3);
                    break;
                case SOUTH:
                    gc.strokeLine(3, SPACE_HEIGHT - 2, SPACE_WIDTH - 3, SPACE_HEIGHT - 2);
                    break;
                case WEST:
                    gc.strokeLine(2, 3, 2, SPACE_HEIGHT - 3);
                    break;
            }
        }
        this.getChildren().add(canvas);
    }

    private void drawConveyorBelts() {
        Canvas canvas = new Canvas(SPACE_WIDTH, SPACE_HEIGHT);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        List<FieldAction> actions = space.getActions();
        for (FieldAction action : actions) {
            if (action instanceof ConveyorBelt) {
                gc.setFill(Color.LIME); // Color for conveyor belt
                gc.setStroke(Color.LIME); // Color for conveyor belt border
                gc.setLineWidth(2);

                ConveyorBelt conveyorBelt = (ConveyorBelt) action;
                double[] xPoints = new double[3];
                double[] yPoints = new double[3];

                switch (conveyorBelt.getHeading()) {
                    case NORTH:
                        xPoints = new double[]{SPACE_WIDTH / 2.0, SPACE_WIDTH / 4.0, 3.0 * SPACE_WIDTH / 4};
                        yPoints = new double[]{1.0 * SPACE_HEIGHT / 6, 3.5 * SPACE_HEIGHT / 4, 3.5 * SPACE_HEIGHT / 4};
                        break;
                    case EAST:
                        xPoints = new double[]{3.5 * SPACE_WIDTH / 4, 1.0 * SPACE_WIDTH / 6, 1.0 * SPACE_WIDTH / 6};
                        yPoints = new double[]{SPACE_HEIGHT / 2.0, SPACE_HEIGHT / 4.0, 3.0 * SPACE_HEIGHT / 4};
                        break;
                    case SOUTH:
                        xPoints = new double[]{SPACE_WIDTH / 2.0, SPACE_WIDTH / 4.0, 3.0 * SPACE_WIDTH / 4};
                        yPoints = new double[]{5.0 * SPACE_HEIGHT / 6, 0.5 * SPACE_HEIGHT / 4, 0.5 * SPACE_HEIGHT / 4};
                        break;
                    case WEST:
                        xPoints = new double[]{1.0 * SPACE_WIDTH / 6, 3.5 * SPACE_WIDTH / 4, 3.5 * SPACE_WIDTH / 4};
                        yPoints = new double[]{SPACE_HEIGHT / 2.0, SPACE_HEIGHT / 4.0, 3.0 * SPACE_HEIGHT / 4};
                        break;
                }

                gc.fillPolygon(xPoints, yPoints, 3);
                gc.strokePolygon(xPoints, yPoints, 3);
            }
        }
        this.getChildren().add(canvas);
    }

        @Override
    public void updateView(Subject subject) {
        if (subject == this.space) {
            this.getChildren().removeIf(node -> node instanceof Polygon);

            // XXX A3: drawing walls and action on the space (could be done
            //         here); it would be even better if fixed things on
            //         spaces  are only drawn once (and not on every update)
            updatePlayer();
        }
    }
}

