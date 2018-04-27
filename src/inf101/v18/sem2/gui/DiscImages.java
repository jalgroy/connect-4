package inf101.v18.sem2.gui;

import inf101.v18.sem2.game.objects.Disc;
import javafx.scene.image.Image;

import java.util.HashMap;
import java.util.Map;

public class DiscImages {
    private static Map<Disc, Image> discImages = generateDiscImages();

    public static Image get(Disc disc){
        return discImages.get(disc);
    }

    private static Map<Disc, Image> generateDiscImages(){
        Map<Disc, Image> discImages = new HashMap<>();
        for(Disc d : Disc.values()){
            discImages.put(d, new Image("inf101/v18/sem2/images/" + d.toString() + ".png"));
        }
        return discImages;
    }
}
