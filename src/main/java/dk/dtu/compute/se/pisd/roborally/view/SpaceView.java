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
import dk.dtu.compute.se.pisd.roborally.controller.CheckPoint;
import dk.dtu.compute.se.pisd.roborally.controller.ConveyorBelt;
import dk.dtu.compute.se.pisd.roborally.model.Heading;
import dk.dtu.compute.se.pisd.roborally.model.Player;
import dk.dtu.compute.se.pisd.roborally.model.Space;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import org.jetbrains.annotations.NotNull;

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
    private Polygon playerArrow = null;

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
        drawWalls();
        drawFieldActions();

        // updatePlayer();

        // This space view should listen to changes of the space
        space.attach(this);
        update(space);
    }

    private void updatePlayer() {
        Player player = space.getPlayer();
        if (player != null) {
            if (playerArrow == null) { // create player arrow if it hasnt been created yet
                playerArrow = new Polygon(0.0, 0.0,
                        10.0, 20.0,
                        20.0, 0.0);
                this.getChildren().add(playerArrow);
            }

            try {
                playerArrow.setFill(Color.valueOf(player.getColor()));
            } catch (Exception e) {
                playerArrow.setFill(Color.MEDIUMPURPLE);
            }

            playerArrow.setRotate((90 * player.getHeading().ordinal()) % 360);
        } else if (playerArrow != null) {
            this.getChildren().remove(playerArrow);
            playerArrow = null;
        }
    }

    private void drawWalls() {
        Pane pane = new Pane();

        Rectangle rectangle = new Rectangle(0.0, 0.0, SPACE_WIDTH, SPACE_HEIGHT);
        rectangle.setFill(Color.TRANSPARENT);
        pane.getChildren().add(rectangle);

        for (Heading heading : space.getWalls()) {
            Line line = null;
            switch (heading) {
                case NORTH:
                    line = new Line(2, 2, SPACE_WIDTH-2, 2);
                    break;
                case SOUTH:
                    line = new Line(2, SPACE_HEIGHT-2, SPACE_WIDTH-2, SPACE_HEIGHT-2);
                    break;
                case EAST:
                    line = new Line(SPACE_WIDTH-2, 2, SPACE_WIDTH-2, SPACE_HEIGHT-2);
                    break;
                case WEST:
                    line = new Line(2, 2, 2, SPACE_HEIGHT-2);
                    break;
            }
            if(line != null) {
                line.setStroke(Color.RED);
                line.setStrokeWidth(3);
                pane.getChildren().add(line);
            }
        }
        this.getChildren().add(pane);
    }

    private void drawFieldActions() {
        Pane pane = new Pane();

        if (!space.getActions().isEmpty()) {
            for (var action : space.getActions()) {
                if (action instanceof CheckPoint) {
                    drawCheckPoint(pane, (CheckPoint) action);
                } else if (action instanceof ConveyorBelt) {
                    drawConveyorBelt(pane, (ConveyorBelt) action);
                }
            }
        }
        this.getChildren().add(pane);
    }



    private void drawConveyorBelt(Pane pane, ConveyorBelt action) {
        var conveyorBelt = (ConveyorBelt) action;
        Polygon conveyorArrow = new Polygon(
                0.0, 0.0,
                10.0, 20.0,
                20.0, 0.0
        );
        conveyorArrow.setFill(Color.LIGHTGREY);

        conveyorArrow.setRotate(90 * conveyorBelt.getHeading().ordinal() % 360);


        double centerX = SPACE_WIDTH / 2.0;
        double centerY = SPACE_HEIGHT / 2.0;

        //centre arrow
        var arrowCenterX = centerX - (conveyorArrow.getBoundsInLocal().getWidth() / 2);
        var arrowCenterY = centerY - (conveyorArrow.getBoundsInLocal().getHeight() / 2);
        conveyorArrow.setTranslateX(arrowCenterX);
        conveyorArrow.setTranslateY(arrowCenterY);

        pane.getChildren().add(conveyorArrow);
    }

    private void drawCheckPoint(Pane pane, CheckPoint action) {
        var checkpoint = (CheckPoint) action;
        Circle circle = new Circle(SPACE_WIDTH / 2.0, SPACE_HEIGHT / 2.0, 15);
        circle.setFill(Color.YELLOW);

        Text text = new Text(String.valueOf(checkpoint.getCheckPointNumber()));
        text.setFont(Font.font("System", FontWeight.BOLD, 12));
        text.setFill(Color.BLACK);

        var textWidth = text.getLayoutBounds().getWidth();
        var textHeight = text.getLayoutBounds().getHeight();
        text.setX(circle.getCenterX() - textWidth / 2);
        text.setY(circle.getCenterY() + textHeight / 4);


        pane.getChildren().addAll(circle, text);
    }


    @Override
    public void updateView(Subject subject) {
        if (subject == this.space) {
//            this.getChildren().clear(); i removed this line to avoid removing the walls on each update

            // XXX A3: drawing walls and action on the space (could be done
            //         here); it would be even better if fixed things on
            //         spaces  are only drawn once (and not on every update)
            updatePlayer();
        }
    }

}
