package inf101.v18.sem2.gui;

import inf101.v18.sem2.game.Game;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import static inf101.v18.sem2.gui.GuiUtil.HEIGHT;
import static inf101.v18.sem2.gui.GuiUtil.SF;
import static inf101.v18.sem2.gui.GuiUtil.WIDTH;

public class TitleScene extends Scene {
    private Stage stage;
    private Group root;
    private Game game;


    public TitleScene(Stage stage, Group root, Game game){
        super(root, WIDTH, HEIGHT);
        this.stage = stage;
        this.root = root;
        this.game = game;
        initialize();
    }

    private void initialize(){
        Button btnMultiplayer = new Button("Start 2-player game");
        btnMultiplayer.setScaleX(1.5*SF);
        btnMultiplayer.setScaleY(1.5*SF);
        btnMultiplayer.setLayoutX(.4*WIDTH);
        btnMultiplayer.setLayoutY(HEIGHT/2);
        btnMultiplayer.setMinWidth(.2*WIDTH);
        btnMultiplayer.setOnAction(actionEvent -> {
            GetPlayerScene getPlayerScene = new GetPlayerScene(stage, new Group(), game, false);
            stage.setScene(getPlayerScene);
        });

        Button btnAI = new Button("Start game vs HAL 9000");
        btnAI.setScaleX(1.5*SF);
        btnAI.setScaleY(1.5*SF);
        btnAI.setLayoutX(.4*WIDTH);
        btnAI.setLayoutY(3*HEIGHT/4);
        btnAI.setMinWidth(.2*WIDTH);
        btnAI.setOnAction(actionEvent -> {
            GetPlayerScene getPlayerScene = new GetPlayerScene(stage, new Group(), game, true);
            stage.setScene(getPlayerScene);
        });

        root.getChildren().addAll(GuiUtil.getBackgroundImage(), GuiUtil.getTitleImage(), btnMultiplayer, btnAI);
    }
}
