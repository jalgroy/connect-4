package inf101.v18.sem2;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import java.util.HashMap;
import java.util.Map;

public enum Disc {
    HAL(true, Color.RED)
    ,GREEN(false, Color.GREEN)
    ,YELLOW(false, Color.YELLOW)
    ,BLACK(false, Color.BLACK)
    ,WHITE(false, Color.WHITE);

    //public abstract void draw(GraphicsContext context, double x, double y, double width, double height);

    private boolean hasImage;
    private Color color;

    Disc(boolean hasImage, Color color){
        this.hasImage = hasImage;
        this.color = color;
    }

    public void draw(GraphicsContext context, double x, double y, double width, double height){
        if(hasImage){
            drawImage(context, x, y, width, height, this.toString());
        } else {
            drawColor(context, x, y, width, height, color);
        }
    }

    private static void drawColor(GraphicsContext context, double x, double y, double width, double height, Color color){
        context.setFill(color);
        context.fillOval(x, y, width, height);
    }

    private static void drawImage(GraphicsContext context, double x, double y, double width, double height, String filename){
        context.drawImage(discImages.get(filename), x, y, width, height);
    }

    private static Map<String, Image> discImages = getDiscImages();

    private static Map<String, Image> getDiscImages(){
        Map<String, Image> discImages = new HashMap<>();
        discImages.put("HAL", new Image("inf101/v18/sem2/images/HAL.png"));
        return discImages;
    }
}
