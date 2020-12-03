package worldofzuul.PresentationLayer;

import javafx.animation.AnimationTimer;
import javafx.geometry.VPos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

import java.util.ArrayList;
import java.util.List;

public class Transition {

    private GraphicsContext gc;
    private AnimationTimer animationTimer;
    private ArrayList<String> text;
    private int windowHeight;
    private int windowWidth;
    private Font textFont;
    private boolean isActive;


    /**
     * Unix epoch representing when the animation starts.
     */
    private long animationStart;

    /**
     * A number in milliseconds that represent how long each animation will take.
     */
    private long animationTime;

    /**
     * A number representing the current state of the animation.
     * -1 is the opening transition.
     */
    private int animationState;

    /**
     * If true, the animation is done, and the transition can be deactivated.
     */
    private boolean animationIsDone;

    private AnimationDoneHandler doneHandler;

    public Transition(Canvas canvas){
        this.gc = canvas.getGraphicsContext2D();


        this.windowHeight = (int) canvas.getHeight();
        this.windowWidth = (int) canvas.getWidth();

        System.out.println("WindowWidth: " + this.windowWidth);
        System.out.println("WindowHeigt: " + this.windowHeight);


        this.animationStart = 0;
        this.animationTime = 1000;
        this.animationState = -1;
        this.animationIsDone = false;
        this.textFont = new Font("Consolas", 36 );
        this.text = new ArrayList<String>();
        this.isActive = false;


        this.animationTimer = new AnimationTimer() {

            @Override
            public void handle(long now) {
                draw();
            }

        };
        this.doneHandler = null;
    }


    /**
     * Enables or disables the drawing of the transition.
     * @param active If true, the Grid will begin to paint the Canvas.
     */
    public void setActive(boolean active) {
        this.isActive = active;
        if (active) {
            this.animationTimer.start();
            this.animationStart = System.currentTimeMillis();
        } else {
            this.animationTimer.stop();
        }
    }

    public boolean isActive(){
        return this.isActive;
    }


    /**
     * Runs every frame, when set active by setActive(boolean);
     */
    private void draw() {
        gc.clearRect(0,0, windowWidth, windowHeight);
        gc.setFill(Color.WHITE);
        gc.setTextAlign(TextAlignment.CENTER);
        gc.setTextBaseline(VPos.CENTER);



        double animationProgress = this.getAnimationProgress();
        if(this.animationState == -1) {
            gc.clearRect(0,0, windowWidth, windowHeight);
            gc.setGlobalAlpha(animationProgress);
            gc.fillRect(0,0,windowWidth, windowHeight);
            if(animationProgress >= 1){
                this.animationTime = 2000;
                this.advanceAnimationState();
            }
        }else{
            gc.setGlobalAlpha(1);
            gc.fillRect(0,0,windowWidth, windowHeight);
            if (this.text.size() > this.animationState) {
                String[] lines = this.text.get(this.animationState).split("\n");
                gc.setFill(Color.BLACK);
                gc.setGlobalAlpha(animationProgress);
                gc.setFont(this.textFont);

                gc.save();
                gc.translate(0,lines.length*this.textFont.getSize() * -1);
                for(int i = 0; i < lines.length; i++) {
                    String currentLine = lines[i];
                    gc.fillText(currentLine, windowWidth / 2, (windowHeight / 2) + (i+1)*this.textFont.getSize());
                }
                gc.restore();
            }else{
                this.animationIsDone = true;
                this.setActive(false);
                if(this.doneHandler != null) {
                    this.doneHandler.animationDone();
                }
            }

            if(animationProgress > 2){
                gc.setGlobalAlpha(Math.min(animationProgress-2, 0.8));
                String helpText = "Press 'E' to continue";
                gc.setFont(new Font("Consolas", 16));

                gc.fillText(helpText, windowWidth/2, windowHeight/12*11);
            }

        }


    }

    /**
     * Advances the state to show the next text.
     * Also calls the doneCallback if done.
     */
    public void advanceAnimationState(){
        this.animationState++;
        this.animationStart = System.currentTimeMillis();
    }

    private double getAnimationProgress(){
        return (double) (System.currentTimeMillis() - this.animationStart) / (double) this.animationTime;
    }

    public void addText(List<String> text){
        this.text.addAll(text);
    }

    public void addLine(String line){
        this.text.add(line);
    }

    public void clearLines(){
        this.text.clear();
    }

    public boolean isAnimationDone(){
        return this.animationIsDone;
    }

    /**
     * Completely resets the Transition so it can be used agian.
     */
    public void reset(){
        this.clearLines();
        this.setActive(false);
        this.animationState = -1;
    }

    public void setDoneHandler(AnimationDoneHandler doneHandler) {
        this.doneHandler = doneHandler;
    }
}

