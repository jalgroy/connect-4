package inf101.v18.sem2.game.objects;

import inf101.v18.sem2.gui.GUI;
import javafx.scene.canvas.GraphicsContext;

public enum Disc {
    HAL,
    EARTH,
    MOON,
    MARS,
    JUPITER;

    public void draw(GraphicsContext context, double x, double y, double width, double height){
        context.drawImage(GUI.getDiscImage(this), x, y, width, height);
    }

    public static Disc[] nonReservedValues(){
        return new Disc[]{EARTH, MOON, MARS, JUPITER};
    }
}
