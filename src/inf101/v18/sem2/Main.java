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
    private final double SF = 1.5; // Scaling factor
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
        btnMultiplayer.setLayoutX(.375*width);
        btnMultiplayer.setLayoutY(height/2);
        btnMultiplayer.setMinWidth(width/4);
        btnMultiplayer.setOnAction(actionEvent -> {
            stage.setScene(getPlayerScene(false, 1));
        });

        Button btnAI = new Button("Start game vs HAL 9000");
        btnAI.setScaleX(1.5*SF);
        btnAI.setScaleY(1.5*SF);
        btnAI.setLayoutX(.375*width);
        btnAI.setLayoutY(3*height/4);
        btnAI.setMinWidth(width/ 4);
        btnAI.setOnAction(actionEvent -> {
            stage.setScene(getPlayerScene(true, 1));
        });

        root.getChildren().addAll(getTitleImage(), btnMultiplayer, btnAI);
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

        gameScene.setOnKeyPressed(e -> {
            game.keyPressed(e.getCode());
            game.draw(context,gameWidth,gameHeight);
            updateSidebar(root, width-gameWidth, height);
        });

        game.draw(context,gameWidth,gameHeight);

        root.getChildren().add(canvas);

        int cl = game.getColumns();

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

        updateSidebar(root,width-gameWidth, height);

        return gameScene;
    }

    private void updateSidebar(Group root, double sWidth, double sHeight){
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

        root.getChildren().addAll(sideBar,gameStatus);
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
        info.setLayoutY(3*height/8);
        info.setWrappingWidth(width/4);

        TextField nameInput = new TextField();
        nameInput.setPromptText("Enter player name");
        nameInput.setScaleX(1.5*SF);
        nameInput.setScaleY(1.5*SF);
        nameInput.setLayoutX(.375*width);
        nameInput.setLayoutY(height/2);
        nameInput.setMinWidth(width/4);

        Button btnSubmit = new Button("Submit");
        btnSubmit.setScaleX(1.5*SF);
        btnSubmit.setScaleY(1.5*SF);
        btnSubmit.setLayoutX(.375*width);
        btnSubmit.setLayoutY(3*height/4);
        btnSubmit.setMinWidth(width/ 4);
        btnSubmit.setOnAction(actionEvent -> {
            if(nameInput.getText().equals("")) return;
            Player p = new Player(nameInput.getText(), n == 1 ? Disc.GREEN : Disc.YELLOW);
            p.setName(nameInput.getCharacters().toString());
            game.addPlayer(p);
            if(n == 1){
                if(vsAI){
                    game.addPlayer(new AI(game));
                    stage.setScene(gameScene());
                }else {
                    stage.setScene(getPlayerScene(vsAI, 2));
                }
            } else {
                stage.setScene(gameScene());
            }
        });

        root.getChildren().addAll(getTitleImage(), info, btnSubmit, nameInput);

        return getPlayerScene;
    }

    private ImageView getTitleImage(){
        ImageView title = new ImageView(new Image("/inf101/v18/sem2/images/title.png"));
        title.setX(.1*width);
        title.setY(.1*height);
        double titleWidth = .8*width;
        title.setFitWidth(titleWidth);
        title.setFitHeight(titleWidth*3/20); // Title image has 20:3 aspect ratio
        return title;
    }
}
