package inf101.v18.sem2.gui;

import inf101.v18.sem2.Disc;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class DiscSelector extends Canvas {
    private int selected;
    private Disc[] discs;

    public DiscSelector(double width, double height, Disc[] discs){
        super(width, height);
        if(discs == null){
            throw new IllegalArgumentException("Disc array cannot be null");
        } else if(discs.length < 1) {
            throw new IllegalArgumentException("Disc array cannot be empty");
        }
        this.discs = discs;
        selected = 0;
    }

    public void draw(GraphicsContext context, double width, double height){
        int nDiscs = discs.length;
        double diameter = .8*height;
        for (int i = 0; i < nDiscs; i++) {
            double x = i*width/nDiscs + (width/nDiscs-diameter)/2;
            discs[i].draw(context, x, .1*height, diameter, diameter);
            if(i == selected) {
                context.setStroke(Color.RED);
            }else{
                context.setStroke(Color.BLACK);
            }
            context.setLineWidth(.03*diameter);
            context.strokeOval(x, .1*height, diameter, diameter);
        }
    }

    public void setSelected(int i){
        if(i < 0 || i > discs.length-1){
            throw new IndexOutOfBoundsException("Index: " + i + ", Length: " + discs.length);
        }
        selected = i;
    }

    public Disc getSelected() {
        return discs[selected];
    }
}