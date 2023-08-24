package amaze;

import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PImage;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Amaze extends PApplet {

    public static void main(String[] args) {
        PApplet.main(new String[]{Amaze.class.getName()});
    }

    PImage playerImg;
    PImage visionImg;
    PFont text;
    PFont winMessage;

    int playerX, playerY;
    int visionX, visionY;
    int speed = 30;
    int playerSize = 30;
    int gridSize = 600;

    /*
    int flashLight = 2;
    int vision = 3;
    int key = 4;
    */

    boolean start = false;
    boolean reset = false;
    boolean startButtonPressed = false;
    boolean resetButtonPressed = false;
    boolean visionActivate = false;
    boolean scheduled = false;

    boolean gameCompleted = false;
    int exitX = 19 * 30;
    int exitY = 1 * 30;

    private final ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();


    int[][] maze = new int[][]{
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            {0, 0, 3, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 1, 1, 1, 0, 0, 0},
            {1, 1, 0, 1, 0, 1, 1, 1, 1, 1, 0, 1, 0, 0, 0, 1, 1, 0, 1, 1},
            {1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 1},
            {1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 0, 0, 0, 1, 0, 1, 1, 1},
            {1, 0, 0, 0, 0, 0, 1, 0, 1, 1, 0, 1, 1, 1, 0, 1, 1, 1, 1, 1},
            {1, 0, 1, 1, 1, 0, 1, 0, 1, 1, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1},
            {1, 0, 0, 1, 0, 0, 1, 0, 1, 1, 0, 1, 1, 1, 0, 1, 1, 1, 0, 1},
            {1, 1, 1, 1, 0, 1, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 1},
            {1, 0, 0, 0, 0, 0, 1, 1, 1, 1, 0, 1, 0, 1, 1, 1, 0, 1, 0, 1},
            {1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 0, 1, 0, 0, 0, 1, 0, 1, 0, 1},
            {1, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 1, 0, 1, 1, 1, 0, 1, 0, 1},
            {1, 0, 1, 1, 1, 0, 1, 1, 1, 1, 0, 0, 0, 0, 0, 1, 0, 1, 0, 1},
            {1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 1, 1, 0, 1, 0, 1, 0, 1},
            {1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 0, 1, 0, 0, 0, 1, 0, 1},
            {1, 0, 1, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1},
            {1, 0, 1, 1, 1, 1, 1, 1, 0, 1, 0, 1, 0, 0, 0, 1, 0, 1, 0, 1},
            {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 0, 1, 0, 1},
            {1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1},
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}
    };

    int cellSize = 30;

    public void settings() {
        size(gridSize, gridSize + 200);
    }

    public void setup() {
        playerImg = loadImage("./ressources/knight.png");
        text = createFont("Arial", 16, true);
        winMessage = createFont("./ressources/Gameplay.ttf", 28);
        visionImg = loadImage("./ressources/vision.png");

        visionImg.resize(playerSize, 20);
        playerImg.resize(playerSize, 0);
        playerX = 1;
        playerY = 30;

        //extracted();
        System.out.println(maze[0][0]);
    }

    //randomize position for powerups - do not change
    private void extracted() {
        int positionA = 0;
        int positionB = 0;
        for (int x = 2; x < 5; x++) {
            while (maze[positionA][positionB] != 0) {
                positionA = (int) random(0, 20);
                positionB = (int) random(0, 20);
            }
            maze[positionA][positionB] = x;
        }
    }

    public void draw() {

        if (gameCompleted) {
            textFont(winMessage);
            textAlign(CENTER, CENTER);
            fill(255, 215, 0);
            text("You have found the exit!\nCongratulations!", width / 2, height / 3);

            textFont(text);
            textAlign(LEFT, BASELINE);
            fill(0);
            return;

        }
        background(255);

        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < maze[i].length; j++) {
                if (maze[i][j] == 1) {
                    fill(0); // Draw boundaries in black
                } else if (maze[i][j] == 3) {
                    fill(255);
                    visionX = (j * cellSize) + 1;
                    visionY = i * cellSize;
                } else {
                    fill(255); // Draw open spaces in white
                }
                noStroke();
                rect(j * cellSize, i * cellSize, cellSize, cellSize);
            }
        }

        if (!visionActivate) {
            image(visionImg, visionX, visionY);
        }

        fill(255, 240);
        rect(playerX + 5, playerY + 5, playerSize - 15, playerSize - 15);
        image(playerImg, playerX, playerY);

        float rectX = playerX - gridSize, rectY = playerY - gridSize - 200, rectWidth = 2 * width, rectHeight = 2 * height;
        drawRadialGradient(rectX, rectY, rectWidth, rectHeight, color(0, 0, 0));


        if (start) {
            textFont(text, 16);
            fill(0);
            text("", 190, 650);
        } else {
            textFont(text, 16);
            fill(0);
            text("Um zu spielen, klicke auf \"Start\"", 190, 650);
        }

        if (reset) {
            playerX = 1;
            playerY = 30;
            gameCompleted = false;
            reset = false;
        }

        //Start Button
        if (startButtonPressed) {
            fill(34, 139, 34, 100);
        } else {
            fill(34, 139, 34);
        }
        rect(150, 700, 100, 40, 10, 10, 10, 10);
        fill(0, 0, 0);
        text("Start", 180, 725);

        //End Button
        if (resetButtonPressed) {
            fill(178, 34, 34, 100);
        } else {
            fill(178, 34, 34);
        }
        rect(350, 700, 100, 40, 10, 10, 10, 10);
        fill(0, 0, 0);
        text("Reset", 380, 725);

        if (playerX == exitX && playerY == exitY) {
            gameCompleted = true;
        }

        playerX = constrain(playerX, 0, width - playerImg.width);
        playerY = constrain(playerY, 0, height - playerImg.height);
    }

    void drawRadialGradient(float x, float y, float w, float h, int c) {
        float centerX = x + w / 2.0f;
        float centerY = y + h / 2.0f;
        float maxDist = dist(centerX, centerY, x, y);

        float transparentRadius = 0;
        float gradientRadius = visionActivate ? max(w, h) : (max(w, h) / 12.0f); // Set the gradient radius to one fifth of the maximum dimension of the rectangle

        int numSegmentsX = 50; // Number of segments in the X direction
        int numSegmentsY = 50; // Number of segments in the Y direction

        float segmentWidth = w / numSegmentsX;
        float segmentHeight = h / numSegmentsY;

        if (playerX == visionX && playerY == visionY) {
            visionActivate = true;
            maze[visionY / 30][(visionX - 1) / 30] = 0;
            visionX = 0;
            visionY = 0;
            if (!scheduled) {
                scheduled = true;
                executorService.schedule(() -> {
                    visionActivate = false;
                    scheduled = false;
                }, 2, TimeUnit.SECONDS);
            }
        }

        for (int i = 0; i < numSegmentsX; i++) {
            for (int j = 0; j < numSegmentsY; j++) {
                float segmentX = x + i * segmentWidth;
                float segmentY = y + j * segmentHeight;

                float d = dist(centerX, centerY, segmentX + segmentWidth / 2, segmentY + segmentHeight / 2);
                float t;
                if (d < transparentRadius) {
                    t = 0;
                } else if (d < gradientRadius) {
                    t = map(d, transparentRadius, gradientRadius, 0, 1);
                } else {
                    t = 1;
                }
                int currentColor = lerpColor(color(0, 0, 0, 0), c, t);
                noStroke();
                fill(currentColor);
                rect(segmentX, segmentY, segmentWidth, segmentHeight);
            }
        }
    }


    public void keyPressed() {
        if (!start || gameCompleted) {
            return;
        }

        if (reset) {
            return;
        }

        try {
            if (keyCode == 37) {   //left
                if (playerX > 0 && playerX < 600 && maze[playerY / 30][(playerX / 30) - 1] != 1) {
                    playerX -= speed; // Move left
                    delay(200);
                }
            } else if (keyCode == 39) {  //right
                if (playerX > 0 && playerX < 600 && maze[playerY / 30][(playerX / 30) + 1] != 1) {
                    playerX += speed; // Move right
                    delay(200);
                }
            } else if (keyCode == 38) {   //up
                if (maze[(playerY / 30) - 1][playerX / 30] != 1) {
                    playerY -= speed; // Move up
                    delay(200);
                }
            } else if (keyCode == 40) {  //down
                if (maze[(playerY / 30) + 1][playerX / 30] != 1) {
                    playerY += speed; // Move down
                    delay(200);
                }
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            //ignored
        }
    }

    public void mousePressed() {
        if (mouseX > 150 && mouseX < 250 && mouseY > 700 && mouseY < 740) {
            startButtonPressed = true;
            redraw();
        }
        if (mouseX > 350 && mouseX < 450 && mouseY > 700 && mouseY < 740) {
            resetButtonPressed = true;
            redraw();
        }
    }

    public void mouseReleased() {
        startButtonPressed = false;
        resetButtonPressed = false;
        redraw();
    }

    public void mouseClicked() {
        if (mouseX > 150 && mouseX < 250 && mouseY > 700 && mouseY < 740) {
            start = true;
            redraw();
        }
        if (mouseX > 350 && mouseX < 450 && mouseY > 700 && mouseY < 740) {
            reset = true;
            redraw();
        }
    }
}
