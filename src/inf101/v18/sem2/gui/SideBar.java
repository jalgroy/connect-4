package inf101.v18.sem2.gui;

import inf101.v18.sem2.Game;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

import java.awt.*;

public class SideBar extends Group {
    private Game game;
    private double width;
    private double height;
    private double sidebarWidth;
    private double SF;

    private Rectangle sideBar;
    private Text gameStatus;
    private Canvas currentDisc;
    private Button btnUndo;
    private Button btnNewGame;

    public SideBar(Game game, double width, double height, double sidebarWidth, double scalingFactor){
        super();
        this.game = game;
        this.width = width;
        this.height = height;
        this.sidebarWidth = sidebarWidth;
        this.SF = scalingFactor;
        generate();
    }

    public void update(){
        switch (game.getState()){
            case PLAYING:
                gameStatus.setText(game.getCurrentPlayer().getName() + "'s turn");
                break;
            case WIN:
                gameStatus.setText(game.getCurrentPlayer().getName() + " won!");
                break;
            case DRAW:
                gameStatus.setText("It's a draw!");
                break;
        }
    }

    public void setOnUndo(EventHandler<ActionEvent> eventHandler){
        btnUndo.setOnAction(eventHandler);
    }

    public void setOnNewGame(EventHandler<ActionEvent> eventHandler){
        btnNewGame.setOnAction(eventHandler);
    }

    private void generate(){
        double padding = .05*sidebarWidth;
        sideBar = new Rectangle(sidebarWidth - 2*padding, height - 2*padding);
        sideBar.setX(width-sidebarWidth+padding);
        sideBar.setY(padding);
        sideBar.setArcHeight(padding);
        sideBar.setArcWidth(padding);
        sideBar.setFill(Color.LIGHTGRAY);

        gameStatus = new Text();
        gameStatus.setScaleX(3*SF);
        gameStatus.setScaleY(3*SF);
        gameStatus.setX(width-sidebarWidth);
        gameStatus.setY(100*SF);
        gameStatus.setWrappingWidth(sidebarWidth);
        gameStatus.setTextAlignment(TextAlignment.CENTER);



        update();



        btnUndo = new Button("Undo");
        btnUndo.setScaleX(1.5*SF);
        btnUndo.setScaleY(1.5*SF);
        btnUndo.setLayoutX(width - .7*sidebarWidth);
        btnUndo.setLayoutY(.8*height);
        btnUndo.setMinWidth(.35*sidebarWidth);
        btnUndo.setOnAction(actionEvent -> {
            game.undo();
        });

        btnNewGame = new Button("New game");
        btnNewGame.setScaleX(1.5*SF);
        btnNewGame.setScaleY(1.5*SF);
        btnNewGame.setLayoutX(width - .7*sidebarWidth);
        btnNewGame.setLayoutY(.9*height);
        btnNewGame.setMinWidth(.35*sidebarWidth);
        btnNewGame.setOnAction(actionEvent -> {
            game = new Game();
        });


        getChildren().addAll(sideBar, gameStatus, btnNewGame, btnUndo);
    }
}
