package inf101.v18.sem2;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.HashMap;
import java.util.Map;

public enum Disc {
    HAL,
    EARTH,
    MOON,
    MARS,
    JUPITER;

    public void draw(GraphicsContext context, double x, double y, double width, double height){
        context.drawImage(discImages.get(this), x, y, width, height);
    }

    private static Map<Disc, Image> discImages = getDiscImages();

    private static Map<Disc, Image> getDiscImages(){
        Map<Disc, Image> discImages = new HashMap<>();
        for(Disc d : values()){
            discImages.put(d, new Image("inf101/v18/sem2/images/" + d.toString() + ".png"));
        }
        return discImages;
    }

    public static Disc[] nonReservedValues(){
        return new Disc[]{EARTH, MOON, MARS, JUPITER};
    }
}
