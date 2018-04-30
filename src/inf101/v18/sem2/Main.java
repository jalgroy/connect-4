package inf101.v18.sem2;

import inf101.v18.sem2.game.Game;
import inf101.v18.sem2.gui.TitleScene;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        Game game = new Game();

        Scene titleScene = new TitleScene(primaryStage, new Group(), game);

        primaryStage.setScene(titleScene);
        primaryStage.setTitle(game.getTitle());
        primaryStage.show();
    }
}
