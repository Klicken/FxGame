package sample;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.animation.AnimationTimer;

public class Main extends Application {
    final int WIDTH = 1280;
    final int HEIGHT = WIDTH * 9/16;
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception
    {
        stage.setTitle("Basic JavaFX demo");
        stage.setWidth(WIDTH);
        stage.setHeight(HEIGHT);
        stage.centerOnScreen();

        Group root = new Group();
        Scene scene = new Scene( root );

        stage.setScene( scene );
        stage.show();

        Canvas canvas = new Canvas( WIDTH, HEIGHT );
        root.getChildren().add( canvas );

        GraphicsContext gc = canvas.getGraphicsContext2D();

        GameObject megaman = new GameObject(0,0, 1);
        megaman.setImage("MegaSprite.png");
        megaman.render(gc);

        AnimationTimer animator = new AnimationTimer()
        {
            long lastTime = System.nanoTime();
            final int TARGET_FPS = 60;
            final long OPTIMAL_TIME = 1000000000 / TARGET_FPS;
            int counter = 0;

            @Override
            public void handle(long now) {

                long elapsedTime = now - lastTime;
                lastTime = now;
                double delta = elapsedTime / ((double)OPTIMAL_TIME);

                // FPS COUNTER
                long fps = 1000000000 / elapsedTime;
                counter++;
                if (counter>TARGET_FPS/2){ // displays fps counter approx every .5s
                    System.out.println("FPS: " + fps);
                    counter = 0;
                }

                // UPDATE
                megaman.update(delta);

                // RENDER
                megaman.render(gc);



                /*
                    TODO
                    Do use Thread.sleep inside here, find other solution
                    https://stackoverflow.com/questions/30146560/how-to-change-animationtimer-speed
                */

                // SLEEP
                try{
                    Thread.sleep( (lastTime-System.nanoTime() + OPTIMAL_TIME)/1000000 );
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        };
        animator.start();
    }
}