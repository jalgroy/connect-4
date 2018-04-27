package inf101.v18.sem2.gui;

import inf101.v18.sem2.game.Game;
import inf101.v18.sem2.game.objects.Disc;
import inf101.v18.sem2.player.HAL;
import inf101.v18.sem2.player.Player;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import static inf101.v18.sem2.gui.GuiUtil.HEIGHT;
import static inf101.v18.sem2.gui.GuiUtil.SF;
import static inf101.v18.sem2.gui.GuiUtil.WIDTH;

/**
 * Scene for creating a player and adding it to the game.
 */
public class GetPlayerScene extends Scene {
    private Stage stage;
    private Game game;
    private boolean vsAI;

    private Text errorText;
    private TextField nameInput;
    private DiscSelector discSelector;
    private int nPlayer;

    public GetPlayerScene(Stage stage, Group root, Game game, boolean vsAI) {
        super(root, WIDTH, HEIGHT);
        this.stage = stage;
        this.game = game;
        this.vsAI = vsAI;

        nPlayer = game.getPlayers().size() + 1;
        nameInput = nameInput();
        discSelector = discSelector();
        errorText = errorText();
        root.getChildren().addAll(GuiUtil.getBackgroundImage(), GuiUtil.getTitleImage(),
                info(), btnSubmit(), errorText, nameInput, discSelector);
    }

    /**
     * @return Text placeholder for displaying errors
     */
    private Text errorText(){
        Text errorText = new Text();
        errorText.setTextAlignment(TextAlignment.CENTER);
        errorText.setScaleX(2*SF);
        errorText.setScaleY(2*SF);
        errorText.setLayoutX(.375*WIDTH);
        errorText.setLayoutY(.9*HEIGHT);
        errorText.setWrappingWidth(WIDTH/4);
        errorText.setFill(Color.RED);
        return errorText;
    }

    /**
     * @return Text for information to the user
     */
    private Text info(){
        Text info = new Text("Player " + nPlayer);
        info.setTextAlignment(TextAlignment.CENTER);
        info.setScaleX(3*SF);
        info.setScaleY(3*SF);
        info.setLayoutX(.375*WIDTH);
        info.setLayoutY(.3*HEIGHT);
        info.setWrappingWidth(WIDTH/4);
        info.setFill(Color.WHITE);
        return info;
    }

    /**
     * @return Input field for player name
     */
    private TextField nameInput(){
        TextField name = new TextField();
        name.setPromptText("Enter player name");
        name.setScaleX(1.5*SF);
        name.setScaleY(1.5*SF);
        name.setLayoutX(.45*WIDTH);
        name.setLayoutY(.4*HEIGHT);
        name.setMinWidth(.1*WIDTH);
        return name;
    }

    /**
     * @return Object for selecting a disc
     */
    private DiscSelector discSelector(){
        double dWidth = .8*WIDTH;
        double dHeight = .2*HEIGHT;
        DiscSelector ds = new DiscSelector(dWidth, dHeight, Disc.nonReservedValues());
        ds.setLayoutX(.1*WIDTH);
        ds.setLayoutY(.5*HEIGHT);
        return ds;
    }

    /**
     * @return Button for submitting the created player
     */
    private Button btnSubmit(){
        Button btnSubmit = new Button("Create player");
        btnSubmit.setScaleX(1.5*SF);
        btnSubmit.setScaleY(1.5*SF);
        btnSubmit.setLayoutX(.45*WIDTH);
        btnSubmit.setLayoutY(.8*HEIGHT);
        btnSubmit.setMinWidth(.1*WIDTH);
        btnSubmit.setOnAction(actionEvent -> submit());
        return btnSubmit;
    }

    /**
     * Checks if player is valid, adds it to the game and moves to the next scene
     */
    private void submit(){
        if(nameInput.getText().equals("")) {
            errorText.setText("Name cannot be empty!");
            return;
        }
        if(nameInput.getText().length() > 20) {
            errorText.setText("Name cannot be longer than 20 characters!");
            return;
        }
        Player p = new Player(nameInput.getText(), discSelector.getSelected());
        p.setName(nameInput.getCharacters().toString());
        if(!game.addPlayer(p)){
            if(p.getName().equals(game.getPlayer(0).getName())){
                errorText.setText("Name already taken.");
            } else {
                errorText.setText("Disc already taken");
            }
            return;
        }
        if(nPlayer == 1){
            if(vsAI){
                game.addPlayer(new HAL());
                GameScene gameScene = new GameScene(stage, new Group(), game);
                stage.setScene(gameScene);
            }else {
                GetPlayerScene scene = new GetPlayerScene(stage, new Group(), game, false);
                stage.setScene(scene);
            }
        } else {
            GameScene gameScene = new GameScene(stage, new Group(), game);
            stage.setScene(gameScene);
        }
    }
}
