package inf101.v18.sem2;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import java.util.HashMap;
import java.util.Map;

public enum Disc {
    HAL {
        @Override
        public void draw(GraphicsContext context, double x, double y, double width, double height){
            drawImage(context, x, y, width, height, "HAL9000");
        }
    },GREEN {
        @Override
        public void draw(GraphicsContext context, double x, double y, double width, double height){
            drawColor(context,x,y,width,height,Color.GREEN);
        }
    },YELLOW {
        @Override
        public void draw(GraphicsContext context, double x, double y, double width, double height){
            drawColor(context,x,y,width,height,Color.YELLOW);
        }
    },BLACK {
        @Override
        public void draw(GraphicsContext context, double x, double y, double width, double height){
            drawColor(context,x,y,width,height,Color.BLACK);
        }
    },WHITE {
        @Override
        public void draw(GraphicsContext context, double x, double y, double width, double height){
            drawColor(context,x,y,width,height,Color.WHITE);
        }
    };

    public abstract void draw(GraphicsContext context, double x, double y, double width, double height);

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
        discImages.put("HAL9000", new Image("inf101/v18/sem2/images/HAL9000.png"));
        return discImages;
    }
}
