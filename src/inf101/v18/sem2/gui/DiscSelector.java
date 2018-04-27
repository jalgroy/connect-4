package inf101.v18.sem2.gui;

import inf101.v18.sem2.game.objects.Disc;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

/**
 * Canvas for selecting a disc
 */
public class DiscSelector extends Canvas {
    private int selected;
    private Disc[] discs;
    private double width;
    private double height;

    DiscSelector(double width, double height, Disc[] discs){
        super(width, height);
        if(discs == null){
            throw new IllegalArgumentException("Disc array cannot be null");
        } else if(discs.length < 1) {
            throw new IllegalArgumentException("Disc array cannot be empty");
        }
        this.width = width;
        this.height = height;
        this.discs = discs;
        selected = 0;

        draw();

        setOnMousePressed(e -> mousePressed(e));
    }

    /**
     * Draw DiscSelector
     */
    private void draw(){
        GraphicsContext context = getGraphicsContext2D();
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

    /**
     * Handle mouse click
     * @param e Mouse event
     */
    private void mousePressed(MouseEvent e){
        double x = e.getX();
        int nDiscs = Disc.nonReservedValues().length;
        int i = (int)(x*nDiscs/width);
        setSelected(i);
        draw();
    }

    /**
     * Set which disc is currently selected
     * @param i Index of selected disc
     */
    private void setSelected(int i){
        if(i < 0 || i > discs.length-1){
            throw new IndexOutOfBoundsException("Index: " + i + ", Length: " + discs.length);
        }
        selected = i;
    }

    /**
     * @return Selected Disc
     */
    public Disc getSelected() {
        return discs[selected];
    }
}
