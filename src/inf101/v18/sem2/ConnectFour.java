package inf101.v18.sem2;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class ConnectFour extends Application {
    private GameState gameState = GameState.PLAYING;
    private Board board;
    private Canvas canvas;
    private final double SF = 1;
    private double width = 1050*SF;
    private double height = 900*SF;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {

        board = new Board(7,6);

        Group root = new Group();
        Scene scene = new Scene(root,width,height);

        canvas = new Canvas(width,height);

        root.getChildren().add(canvas);

        stage.setTitle("Connect Four");
        stage.setScene(scene);
        stage.show();

        draw();
    }

    public void draw(){
        GraphicsContext context = canvas.getGraphicsContext2D();
        context.save();
        context.setFill(Color.BLUE);
        context.fillRect(0,0,1050*SF,900*SF);
        context.restore();
        for (int i = 0; i < board.getWidth(); i++) {
            for (int j = 0; j < board.getHeight(); j++) {
                context.save();
                context.setFill(Color.WHITE);
                double xUnit = width/board.getWidth();
                double yUnit = height/board.getHeight();
                double x = i*xUnit + .1*xUnit;
                double y = j*yUnit + .1*yUnit;
                context.fillOval(x,y,.8*xUnit,.8*yUnit);
                context.restore();
            }
        }
    }
}
