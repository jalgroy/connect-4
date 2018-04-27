package inf101.v18.sem2.game.objects;

import inf101.v18.sem2.gui.GuiUtil;
import javafx.scene.canvas.GraphicsContext;

public enum Disc {
    HAL,
    EARTH,
    MOON,
    MARS,
    JUPITER;

    /**
     * Draw disc with given parameters
     * @param context Graphics context
     * @param x x-location of top-left corner
     * @param y y-location of top-left corner
     * @param width Disc width
     * @param height Disc height
     */
    public void draw(GraphicsContext context, double x, double y, double width, double height){
        context.drawImage(GuiUtil.getDiscImage(this), x, y, width, height);
    }

    /**
     * @return Discs available for all players
     */
    public static Disc[] nonReservedValues(){
        return new Disc[]{EARTH, MOON, MARS, JUPITER};
    }
}
