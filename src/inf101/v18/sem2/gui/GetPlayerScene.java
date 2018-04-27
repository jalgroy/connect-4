package inf101.v18.sem2.gui;

import inf101.v18.sem2.game.Game;
import inf101.v18.sem2.game.objects.Disc;
import inf101.v18.sem2.player.HAL;
import inf101.v18.sem2.player.Player;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;


import static inf101.v18.sem2.gui.GUI.HEIGHT;
import static inf101.v18.sem2.gui.GUI.SF;
import static inf101.v18.sem2.gui.GUI.WIDTH;

public class GetPlayerScene extends Scene {
    private Stage stage;
    private Group root;
    private Game game;
    private boolean vsAI;

    public GetPlayerScene(Stage stage, Group root, Game game, boolean vsAI) {
        super(root, WIDTH, HEIGHT);
        this.stage = stage;
        this.root = root;
        this.game = game;
        this.vsAI = vsAI;
        initialize();
    }

    private void initialize(){
        int n = game.getPlayers().size() + 1;

        Text info = new Text("Player " + n);
        info.setTextAlignment(TextAlignment.CENTER);
        info.setScaleX(3*SF);
        info.setScaleY(3*SF);
        info.setLayoutX(.375*WIDTH);
        info.setLayoutY(.3*HEIGHT);
        info.setWrappingWidth(WIDTH/4);
        info.setFill(Color.WHITE);

        TextField nameInput = new TextField();
        nameInput.setPromptText("Enter player name");
        nameInput.setScaleX(1.5*SF);
        nameInput.setScaleY(1.5*SF);
        nameInput.setLayoutX(.45*WIDTH);
        nameInput.setLayoutY(.4*HEIGHT);
        nameInput.setMinWidth(.1*WIDTH);

        double dWidth = .8*WIDTH;
        double dHeight = .2*HEIGHT;
        DiscSelector discSelector = new DiscSelector(dWidth, dHeight, Disc.nonReservedValues());
        discSelector.setLayoutX(.1*WIDTH);
        discSelector.setLayoutY(.5*HEIGHT);
        GraphicsContext context = discSelector.getGraphicsContext2D();
        discSelector.draw(context, dWidth, dHeight);
        discSelector.setOnMousePressed(e -> {
            double x = e.getX();
            int nDiscs = Disc.nonReservedValues().length;
            int i = (int)(x*nDiscs/dWidth);
            discSelector.setSelected(i);
            discSelector.draw(context, dWidth, dHeight);
        });

        Text errorText = new Text();
        errorText.setTextAlignment(TextAlignment.CENTER);
        errorText.setScaleX(2*SF);
        errorText.setScaleY(2*SF);
        errorText.setLayoutX(.375*WIDTH);
        errorText.setLayoutY(.9*HEIGHT);
        errorText.setWrappingWidth(WIDTH/4);
        errorText.setFill(Color.RED);

        Button btnSubmit = new Button("Submit");
        btnSubmit.setScaleX(1.5*SF);
        btnSubmit.setScaleY(1.5*SF);
        btnSubmit.setLayoutX(.45*WIDTH);
        btnSubmit.setLayoutY(.8*HEIGHT);
        btnSubmit.setMinWidth(.1*WIDTH);
        btnSubmit.setOnAction(actionEvent -> {
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
                errorText.setText("Name or disc already taken.");
                return;
            }
            if(n == 1){
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
        });

        root.getChildren().addAll(GUI.getBackgroundImage(),GUI.getTitleImage(), info, errorText, btnSubmit, nameInput, discSelector);
    }
}
