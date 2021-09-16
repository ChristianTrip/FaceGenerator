    package com.example.facegenerator;
    import javafx.animation.KeyFrame;
    import javafx.animation.Timeline;
    import javafx.application.Application;
    import javafx.fxml.FXMLLoader;
    import javafx.scene.Group;
    import javafx.scene.Scene;
    import javafx.scene.canvas.Canvas;
    import javafx.scene.canvas.GraphicsContext;
    import javafx.scene.control.Button;
    import javafx.scene.paint.Color;
    import javafx.stage.Stage;
    import javafx.util.Duration;
    import java.io.IOException;

    public class HelloApplication extends Application {

        static int WIDTH = 600;
        static int HEIGHT = 600;
        static int shapePosX = WIDTH/4;
        static int shapePosY = HEIGHT/4;
        static boolean hasOneEye = false;
        static boolean noseType = false;

        static Canvas canvas = new Canvas(WIDTH, HEIGHT);
        static GraphicsContext gc = canvas.getGraphicsContext2D();

        static Button button;
        static Button autoButton;

        @Override
        public void start(Stage stage) throws IOException {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));

            button = new Button("Generate a random face");
            button.setScaleX(1);
            button.setPrefWidth(150);
            button.setLayoutX(WIDTH / 2 - (button.getPrefWidth() / 2));
            button.setLayoutY((4 * HEIGHT) / 5);
            button.setOnAction(e -> drawPrimitiveFace());
            autoButton = new Button("Auto generate a random face every two seconds");
            autoButton.setScaleX(1);
            autoButton.setPrefWidth(300);
            autoButton.setLayoutX(WIDTH / 2 - (autoButton.getPrefWidth() / 2));
            autoButton.setLayoutY((8 * HEIGHT) / 9);
            autoButton.setOnAction(e -> autoGenerateFace());

            Group root = new Group();
            Scene scene = new Scene(root, WIDTH, HEIGHT, Color.BEIGE);
            stage.setTitle("Face Generator");
            stage.setScene(scene);

            gc.clearRect(0, 0, WIDTH, HEIGHT);
            root.getChildren().add(canvas);
            root.getChildren().add(button);
            root.getChildren().add(autoButton);

            stage.show();
        }

        public static void autoGenerateFace() {

            Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0.5), event -> {
                gc.clearRect(0, 0, WIDTH, HEIGHT);
                try {
                    drawPrimitiveFace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }));
            timeline.setCycleCount(360);
            timeline.play();
        }

        public static void drawPrimitiveFace() {
            gc.clearRect(0, 0, WIDTH, HEIGHT);

            int randomNum = generateRandomNumber(0, 1);
            int randomNum2 = generateRandomNumber(0, 1);
            int randomNum3 = generateRandomNumber(0,1);

            if (randomNum == 0){
                hasOneEye = true;
            } else if (randomNum == 1){
                hasOneEye = false;
            }

            if (randomNum2 == 0){
                drawHair();
            }

            if (randomNum3 == 0){
                noseType = true;
            } else if (randomNum3 == 1){
                noseType = false;
            }
            drawShape();
            drawMouth(generateRandomNumber(10, 50));
            drawNose(noseType);
            drawEyes(generateRandomNumber(20, 40), generateRandomNumber(20, 50), hasOneEye);
            drawEyeBrows();
        }

        public static void drawShape() {
            gc.setFill(getRandomColor());
            gc.fillOval(shapePosX, shapePosY, 275, 300);
            gc.strokeOval(shapePosX, shapePosY, 275, 300);
        }

        public static void drawMouth(int mouthSize) {
            gc.setFill(getRandomColor());
            gc.fillOval((WIDTH /2) - 50,370,100,mouthSize);
            gc.strokeOval((WIDTH /2) - 50, 370, 100, mouthSize);
        }

        public static void drawNose(boolean noseType) {

            if (noseType){
                int polygonPosX = WIDTH /2 - generateRandomNumber(10, 30);
                int polygonPosY = HEIGHT /2 + generateRandomNumber(20, 30);
                double[] xPoints = {0 + polygonPosX, 25 + polygonPosX, 50 + polygonPosX};
                double[] yPoints= {0 + polygonPosY, -50 + polygonPosY, 0 + polygonPosY};
                gc.setFill(getRandomColor());
                gc.fillPolygon(xPoints, yPoints, 3);
                gc.strokePolygon(xPoints, yPoints, 3);
            } else {
            gc.strokeLine(280, 300, 270, 350);
            gc.strokeLine(270, 350, 290, 350);
            }
        }

        public static void drawEyes(int sizeLeftEye, int sizeRightEye, boolean hasOneEye) {
            int leftEyePosX = generateRandomNumber(225, 250);
            int leftEyePosY = generateRandomNumber(220, 250);
            int rightEyePosX = generateRandomNumber(350, 370);
            int rightEyePosY = leftEyePosY;

            int leftEyeSize = generateRandomNumber(20, 50);
            int rightEyeSize = generateRandomNumber(20, 50);

            if (!hasOneEye){
                gc.setFill(getRandomColor());
                gc.fillOval(leftEyePosX,leftEyePosY,leftEyeSize,leftEyeSize);
                gc.fillOval(rightEyePosX,rightEyePosY,rightEyeSize,rightEyeSize);
                gc.strokeOval(leftEyePosX,leftEyePosY,leftEyeSize,leftEyeSize);
                gc.strokeOval(rightEyePosX,rightEyePosY,rightEyeSize,rightEyeSize);

                gc.setFill(Color.DARKBLUE);
                gc.fillOval(leftEyePosX + leftEyeSize/2,leftEyePosY + leftEyeSize/2,leftEyeSize/2,leftEyeSize/2);
                gc.fillOval(rightEyePosX,rightEyePosY,rightEyeSize/2,rightEyeSize/2);

            } else {
                gc.setFill(getRandomColor());
                gc.fillOval(leftEyePosX,leftEyePosY,leftEyeSize,leftEyeSize);
                gc.strokeOval(leftEyePosX,leftEyePosY,leftEyeSize,leftEyeSize);
                gc.setFill(Color.DARKBLUE);
                gc.fillOval(leftEyePosX + leftEyeSize/2,leftEyePosY + leftEyeSize/2,leftEyeSize/2,leftEyeSize/2);
                gc.strokeLine(rightEyePosX,rightEyePosY,rightEyePosX + 40,rightEyePosY);

                for (int i = 0; i < 40; i += 5) {
                    gc.strokeLine(rightEyePosX + i, rightEyePosY - 5, rightEyePosX + i, rightEyePosY + 10);
                }
            }
        }

        public static void drawEyeBrows(){
            gc.setFill(Color.DARKBLUE);
            gc.fillRect(235, 200, 50, 15);
            gc.fillRect(350, 200, 50, 15);
            gc.strokeRect(235, 200, 50, 15);
            gc.strokeRect(350, 200, 50, 15);
        }

        public static void drawHair(){

            for (int i = 0; i < 40; i++) {
                gc.setStroke(getRandomColor());
                gc.strokeLine((WIDTH/2 - 60) + i * 2, shapePosY, WIDTH/2 + i * 2, shapePosY + 30);
                gc.setStroke(Color.BLACK);
            }
        }

        static int generateRandomNumber(int minValue, int maxValue){
            int randomNum = (int) (Math.random() * (maxValue - minValue + 1) + minValue);

            return randomNum;
        }

        static Color getRandomColor(){
            double red = Math.random();
            double green = Math.random();
            double blue = Math.random();
            double alpha = Math.random();

            return new Color(red, green, blue, alpha);
        }

        public static void main(String[] args) {

            launch(args);
        }
    }