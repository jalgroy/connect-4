package inf101.v18.sem2.gui;

import inf101.v18.sem2.game.Game;
import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

import static inf101.v18.sem2.gui.GuiUtil.HEIGHT;
import static inf101.v18.sem2.gui.GuiUtil.SF;
import static inf101.v18.sem2.gui.GuiUtil.WIDTH;

public class GameScene extends Scene {
    private Stage stage;
    private Group root;
    private Game game;
    private AnimationTimer animationTimer;
    private double gameWidth = WIDTH - 500*SF;
    private double gameHeight = HEIGHT - 50*SF;

    private SideBar sideBar;
    private List<Text> numbers;
    private Canvas canvas;

    public GameScene(Stage stage, Group root, Game game){
        super(root, WIDTH, HEIGHT);
        this.stage = stage;
        this.root = root;
        this.game = game;

        sideBar = sideBar();
        canvas = gameCanvas();
        numbers = numbers();

        setEventHandlers();
        setAnimationTimer();

        root.getChildren().addAll(GuiUtil.getBackgroundImage(), sideBar, canvas);
        root.getChildren().addAll(numbers);
    }

    private SideBar sideBar(){
        SideBar sideBar = new SideBar(game, WIDTH, HEIGHT, WIDTH-gameWidth, SF);
        sideBar.setOnUndo(actionEvent -> game.undo());
        sideBar.setOnNewGame(actionEvent -> {
            animationTimer.stop();
            game = new Game();
            TitleScene titleScene = new TitleScene(stage, new Group(), game);
            stage.setScene(titleScene);
        });
        return sideBar;
    }

    private Canvas gameCanvas(){
        Canvas gameCanvas = new Canvas(gameWidth,gameHeight);
        gameCanvas.setLayoutY(HEIGHT - gameHeight);
        GraphicsContext context = gameCanvas.getGraphicsContext2D();

        game.setContext(context);
        game.setWidth(gameWidth);
        game.setHeight(gameHeight);

        Group clip = new Group();
        List<Node> circles = game.getClip();
        clip.getChildren().addAll(circles);
        gameCanvas.setClip(clip);

        game.draw();

        return gameCanvas;
    }

    private List<Text> numbers(){
        List<Text> nums = new ArrayList<>();
        int cl = game.getColumns();
        for (int i = 1; i <= cl; i++) {
            Text num = new Text();
            num.setX(i*gameWidth/cl - gameWidth/(2*cl));
            num.setY((HEIGHT-gameHeight)/2);
            num.setText(Integer.toString(i));
            num.setFill(Color.WHITE);
            num.setScaleX(3*SF);
            num.setScaleY(3*SF);
            nums.add(num);
        }
        return nums;
    }

    private void setAnimationTimer(){
        animationTimer = new AnimationTimer() {
            private long lastUpdate = 0;
            @Override
            public void handle(long now) {
                if(now - lastUpdate >= 16_666_666L){
                    game.step();
                    sideBar.update();
                    lastUpdate = now;
                }
            }
        };
        animationTimer.start();
    }

    private void setEventHandlers(){
        setOnKeyPressed(e -> {
            game.keyPressed(e.getCode());
        });

        setOnMousePressed(e -> {
            if(e.getX() <= gameWidth){
                game.mouseClicked(e.getX());
            }
        });
    }
}
