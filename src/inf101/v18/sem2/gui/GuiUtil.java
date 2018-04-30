package inf101.v18.sem2.gui;

import inf101.v18.sem2.game.objects.Disc;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.HashMap;
import java.util.Map;

/**
 * Utility class for things relating to graphics
 */
public class GuiUtil {
    // Scaling factor (.8 is ok for 1080p)
    static final double SF = .8;
    // Window width and height in pixels
    static final double WIDTH = 1550*SF;
    static final double HEIGHT = 950*SF;

    private static Map<Disc, Image> discImages = generateDiscImages();

    /**
     * @param disc
     * @return Image corresponding to disc
     */
    public static Image getDiscImage(Disc disc){
        return discImages.get(disc);
    }

    /**
     * @return Map with Discs mapped to their corresponding image
     */
    private static Map<Disc, Image> generateDiscImages(){
        Map<Disc, Image> discImages = new HashMap<>();
        for(Disc d : Disc.values()){
            discImages.put(d, new Image("inf101/v18/sem2/images/" + d.toString() + ".png"));
        }
        return discImages;
    }

    /**
     * @return ImageView of the title image
     */
    public static ImageView getTitleImage(){
        ImageView title = new ImageView(new Image("/inf101/v18/sem2/images/title-white.png"));
        title.setX(.2*WIDTH);
        title.setY(.1*HEIGHT);
        double titleWidth = .6*WIDTH;
        title.setFitWidth(titleWidth);
        title.setFitHeight(titleWidth*3/20); // Title image has 20:3 aspect ratio
        return title;
    }

    /**
     * @return Game background image
     */
    public static ImageView getBackgroundImage(){
        ImageView background = new ImageView(new Image("/inf101/v18/sem2/images/milky-way.jpg"));
        background.setX(0);
        background.setY(0);
        background.setPreserveRatio(true);
        background.setFitHeight(HEIGHT);
        return background;
    }
}
