package inf101.v18.sem2;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class Main extends Application {
    private Scene scene;
    private Group root;
    private final double SF = 1;
    private double width = 1050*SF;
    private double height = 900*SF;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        root = new Group();
        scene = new Scene(root,width,height);

        drawTitleScreen();

        stage.setTitle("Connect Four");
        stage.setScene(scene);
        stage.show();
    }

    public void drawTitleScreen(){
        ImageView title = new ImageView(new Image("/inf101/v18/sem2/images/title.png"));
        title.setX(.1*width);
        title.setY(.1*height);
        double titleWidth = .8*width;
        title.setFitWidth(titleWidth);
        title.setFitHeight(titleWidth*3/20);

        Button btnMultiplayer = new Button("Start 2-player game.");
        btnMultiplayer.setScaleX(2*SF);
        btnMultiplayer.setScaleY(2*SF);
        btnMultiplayer.setLayoutX(.375*width);
        btnMultiplayer.setLayoutY(height/2);
        btnMultiplayer.setMinWidth(width/4);
        btnMultiplayer.setOnAction(actionEvent -> {
            startGame(false);
        });


        Button btnAI = new Button("Start game vs HAL 9000");
        btnAI.setScaleX(2*SF);
        btnAI.setScaleY(2*SF);
        btnAI.setLayoutX(.375*width);
        btnAI.setLayoutY(3*height/4);
        btnAI.setMinWidth(width/ 4);
        btnAI.setOnAction(actionEvent -> {
            startGame(true);
        });

        root.getChildren().addAll(title, btnMultiplayer, btnAI);
    }

    private void startGame(boolean AI){
        return;
    }
}
