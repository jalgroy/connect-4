package inf101.v18.sem2.gui;

import inf101.v18.sem2.game.Game;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import static inf101.v18.sem2.gui.GuiUtil.HEIGHT;
import static inf101.v18.sem2.gui.GuiUtil.SF;
import static inf101.v18.sem2.gui.GuiUtil.WIDTH;

/**
 * Scene for title screen. Allows user to select type of game.
 */
public class TitleScene extends Scene {
    private Stage stage;
    private Game game;

    public TitleScene(Stage stage, Group root, Game game){
        super(root, WIDTH, HEIGHT);
        this.stage = stage;
        this.game = game;

        root.getChildren().addAll(GuiUtil.getBackgroundImage(), GuiUtil.getTitleImage(), btnMultiplayer(), btnAI());
    }

    /**
     * @return Button for starting a local multiplayer game
     */
    private Button btnMultiplayer(){
        Button button = new Button("Start 2-player game");
        button.setScaleX(1.5*SF);
        button.setScaleY(1.5*SF);
        button.setLayoutX(.4*WIDTH);
        button.setLayoutY(HEIGHT/2);
        button.setMinWidth(.2*WIDTH);
        button.setOnAction(actionEvent -> {
            GetPlayerScene getPlayerScene = new GetPlayerScene(stage, new Group(), game, false);
            stage.setScene(getPlayerScene);
        });
        return button;
    }

    /**
     * @return Button for starting a game vs an AI
     */
    private Button btnAI(){
        Button button = new Button("Start game vs HAL 9000");
        button.setScaleX(1.5*SF);
        button.setScaleY(1.5*SF);
        button.setLayoutX(.4*WIDTH);
        button.setLayoutY(3*HEIGHT/4);
        button.setMinWidth(.2*WIDTH);
        button.setOnAction(actionEvent -> {
            GetPlayerScene getPlayerScene = new GetPlayerScene(stage, new Group(), game, true);
            stage.setScene(getPlayerScene);
        });
        return button;
    }
}
