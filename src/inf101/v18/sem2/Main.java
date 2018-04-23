package inf101.v18.sem2;

import inf101.v18.sem2.player.AI;
import inf101.v18.sem2.player.Player;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

public class Main extends Application {
    private Game game;
    private Stage stage;
    private static final double SF = 1.5; // Scaling factor
    // Window dimensions
    private double width = 1550*SF;
    private double height = 950*SF;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        game = new Game();

        stage = primaryStage;
        stage.setScene(titleScene());
        stage.setTitle("Connect Four");
        stage.show();
    }

    private Scene titleScene(){
        Group root = new Group();
        Scene titleScene = new Scene(root,width,height);

        Button btnMultiplayer = new Button("Start 2-player game");
        btnMultiplayer.setScaleX(1.5*SF);
        btnMultiplayer.setScaleY(1.5*SF);
        btnMultiplayer.setLayoutX(.4*width);
        btnMultiplayer.setLayoutY(height/2);
        btnMultiplayer.setMinWidth(.2*width);
        btnMultiplayer.setOnAction(actionEvent -> {
            stage.setScene(getPlayerScene(false, 1));
        });

        Button btnAI = new Button("Start game vs HAL 9000");
        btnAI.setScaleX(1.5*SF);
        btnAI.setScaleY(1.5*SF);
        btnAI.setLayoutX(.4*width);
        btnAI.setLayoutY(3*height/4);
        btnAI.setMinWidth(.2*width);
        btnAI.setOnAction(actionEvent -> {
            stage.setScene(getPlayerScene(true, 1));
        });

        root.getChildren().addAll(getBackgroundImage(), getTitleImage(), btnMultiplayer, btnAI);
        return titleScene;
    }

    private Scene gameScene(){
        Group root = new Group();
        Scene gameScene = new Scene(root,width,height);
        double gameWidth = width - 500*SF;
        double gameHeight = height - 50*SF;

        Canvas canvas = new Canvas(gameWidth,gameHeight);
        canvas.setLayoutY(height - gameHeight);
        GraphicsContext context = canvas.getGraphicsContext2D();

        game.setContext(context);
        game.setWidth(gameWidth);
        game.setHeight(gameHeight);

        gameScene.setOnKeyPressed(e -> {
            game.keyPressed(e.getCode());
            game.draw();
            updateSidebar(root, width-gameWidth);
        });

        canvas.setOnMousePressed(e -> {
            game.mouseClicked(e.getX());
            game.draw();
            updateSidebar(root, width - gameWidth);
        });

        game.draw();

        root.getChildren().add(canvas);

        int cl = game.getColumns();

        // Numbers
        for (int i = 1; i <= cl; i++) {
            Rectangle r = new Rectangle(gameWidth/cl, height-gameHeight);
            r.setX((i-1)*gameWidth/cl);
            r.setY(0);
            if(i % 2 == 0){
                r.setFill(Color.LIGHTGRAY);
            } else {
                r.setFill(Color.WHITE.darker());
            }

            Text num = new Text();
            num.setX(i*gameWidth/cl - gameWidth/(2*cl));
            num.setY((height-gameHeight)/2);
            num.setText(Integer.toString(i));
            num.setFill(Color.BLACK);
            num.setScaleX(3*SF);
            num.setScaleY(3*SF);

            root.getChildren().addAll(r, num);
        }

        updateSidebar(root,width-gameWidth);

        return gameScene;
    }

    private void updateSidebar(Group root, double sWidth){
        Rectangle sideBar = new Rectangle(sWidth, height);
        sideBar.setX(width-sWidth);
        sideBar.setFill(Color.LIGHTGRAY);

        Text gameStatus = new Text();
        gameStatus.setScaleX(3*SF);
        gameStatus.setScaleY(3*SF);
        switch (game.getState()){
            case PLAYING:
                gameStatus.setText(game.getCurrentPlayer().getName() + "'s turn");
                break;
            case ONE_WON:
                gameStatus.setText(game.getCurrentPlayer().getName() + " won!");
                break;
            case TWO_WON:
                gameStatus.setText(game.getCurrentPlayer().getName() + " won!");
                break;
            case DRAW:
                gameStatus.setText("It's a draw!");
                break;
        }
        gameStatus.setX(width-sWidth);
        gameStatus.setY(100*SF);
        gameStatus.setWrappingWidth(sWidth);
        gameStatus.setTextAlignment(TextAlignment.CENTER);

        Button btnUndo = new Button("Undo");
        btnUndo.setScaleX(1.5*SF);
        btnUndo.setScaleY(1.5*SF);
        btnUndo.setLayoutX(width - .7*sWidth);
        btnUndo.setLayoutY(.8*height);
        btnUndo.setMinWidth(.4*sWidth);
        btnUndo.setOnAction(actionEvent -> {
            game.undo();
            game.draw();
            updateSidebar(root, sWidth);
        });

        Button btnNewGame = new Button("New game");
        btnNewGame.setScaleX(1.5*SF);
        btnNewGame.setScaleY(1.5*SF);
        btnNewGame.setLayoutX(width - .7*sWidth);
        btnNewGame.setLayoutY(.9*height);
        btnNewGame.setMinWidth(.4*sWidth);
        btnNewGame.setOnAction(actionEvent -> {
            game = new Game();
            stage.setScene(titleScene());
        });


        root.getChildren().addAll(sideBar, gameStatus, btnNewGame, btnUndo);
    }

    private Scene getPlayerScene(boolean vsAI, int n){
        if(n < 1 || n > 2)
            throw new IllegalArgumentException("Player must have n == 1 or 2");

        Group root = new Group();
        Scene getPlayerScene = new Scene(root,width,height);

        Text info = new Text("Player " + n);
        info.setTextAlignment(TextAlignment.CENTER);
        info.setScaleX(3*SF);
        info.setScaleY(3*SF);
        info.setLayoutX(.375*width);
        info.setLayoutY(.3*height);
        info.setWrappingWidth(width/4);
        info.setFill(Color.WHITE);

        TextField nameInput = new TextField();
        nameInput.setPromptText("Enter player name");
        nameInput.setScaleX(1.5*SF);
        nameInput.setScaleY(1.5*SF);
        nameInput.setLayoutX(.45*width);
        nameInput.setLayoutY(.4*height);
        nameInput.setMinWidth(.1*width);

        double dWidth = .8*width;
        double dHeight = .2*height;
        DiscSelector discSelector = new DiscSelector(dWidth, dHeight, Disc.nonReservedValues());
        discSelector.setLayoutX(.1*width);
        discSelector.setLayoutY(.5*height);
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
        errorText.setLayoutX(.375*width);
        errorText.setLayoutY(.9*height);
        errorText.setWrappingWidth(width/4);
        errorText.setFill(Color.RED);

        Button btnSubmit = new Button("Submit");
        btnSubmit.setScaleX(1.5*SF);
        btnSubmit.setScaleY(1.5*SF);
        btnSubmit.setLayoutX(.45*width);
        btnSubmit.setLayoutY(.8*height);
        btnSubmit.setMinWidth(.1*width);
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
                    game.addPlayer(new AI());
                    stage.setScene(gameScene());
                }else {
                    stage.setScene(getPlayerScene(false, 2));
                }
            } else {
                stage.setScene(gameScene());
            }
        });

        root.getChildren().addAll(getBackgroundImage(),getTitleImage(), info, errorText, btnSubmit, nameInput, discSelector);

        return getPlayerScene;
    }

    private ImageView getTitleImage(){
        ImageView title = new ImageView(new Image("/inf101/v18/sem2/images/title-white.png"));
        title.setX(.2*width);
        title.setY(.1*height);
        double titleWidth = .6*width;
        title.setFitWidth(titleWidth);
        title.setFitHeight(titleWidth*3/20); // Title image has 20:3 aspect ratio
        return title;
    }

    private ImageView getBackgroundImage(){
        ImageView background = new ImageView(new Image("/inf101/v18/sem2/images/milky-way.jpg"));
        background.setX(0);
        background.setY(0);
        background.setPreserveRatio(true);
        background.setFitHeight(height);
        return background;
    }
}
