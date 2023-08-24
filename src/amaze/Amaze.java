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

    PImage playerImgRight;
    PImage playerImgLeft;
    PImage keyImg;
    PImage doorClosedImg;
    PImage doorOpenImg;
    PImage visionImg;
    PImage flashlightImg;
    PFont text;
    PFont winMessage;

    int playerX, playerY;
    int visionX, visionY;
    int speed = 30;
    int playerSize = 30;
    int doorSize = 22;
    int doorSizeOpen = 19;
    int flashlightSize = 30;
    int gridSize = 600;
    boolean isRight = true;

    boolean start = false;
    boolean reset = false;
    boolean startButtonPressed = false;
    boolean resetButtonPressed = false;
    boolean visionActivate = false;
    boolean scheduled = false;

    boolean gameCompleted = false;
    boolean inventarKey = false;
    boolean flashlight = false;

    int exitX = 0;
    int exitY = 0;
    int keyX = 0;
    int keyY = 0;
    int flashlightX = 0;
    int flashlightY = 0;
    boolean revealMaze = false;
    boolean restart = false;

    private final ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();


    int[][] maze = new int[][]{
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            {5, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 1, 1, 1, 0, 0, 6},
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
            {1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 1},
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}
    };
    //flashLight = 2
    //vision = 3
    //key = 4
    //start=5
    //end=6

    int cellSize = 30;

    public void settings() {
        size(gridSize, gridSize + 200);
    }

    public void setup() {
        playerImgRight = loadImage("./ressources/knight_right.png");
        playerImgLeft = loadImage("./ressources/knight_left.png");
        keyImg = loadImage("./ressources/key.png");
        flashlightImg = loadImage("./ressources/Flashlight.png");
        doorClosedImg = loadImage("./ressources/doorClosed.jpg");
        doorOpenImg = loadImage("./ressources/doorOpen.jpg");
        text = createFont("Arial", 16, true);
        winMessage = createFont("./ressources/Gameplay.ttf", 28);
        playerImgRight.resize(playerSize, 0);
        playerImgLeft.resize(playerSize, 0);
        keyImg.resize(playerSize, 0);
        doorClosedImg.resize(doorSize, 0);
        doorOpenImg.resize(doorSizeOpen, 0);
        flashlightImg.resize(flashlightSize, 0);
        visionImg = loadImage("./ressources/vision.png");
        visionImg.resize(playerSize, 20);
        playerX = 1;
        playerY = 30;

        extracted();
    }

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
        background(255);

        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < maze[i].length; j++) {
                if (maze[i][j] == 1) {
                    fill(0); // Draw boundaries in black
                } else if (maze[i][j] == 2) {
                    fill(255);
                    flashlightX = (j * 30) + 1;
                    flashlightY = i * 30; //Get the key coordinates
                } else if (maze[i][j] == 3) {
                    fill(255);
                    visionX = (j * cellSize) + 1;
                    visionY = i * cellSize;
                } else if (maze[i][j] == 4) {
                    fill(255);
                    keyX = (j * 30) + 1;
                    keyY = i * 30; //Get the key coordinates
                } else if (maze[i][j] == 6) {
                    fill(255);
                    exitX = (j * 30) + 1;
                    exitY = i * 30; //Get the end coordinates
                } else {
                    fill(255); // Draw open spaces in white
                }
                noStroke();
                rect(j * cellSize, i * cellSize, cellSize, cellSize);
            }
        }

        if (isRight) {
            image(playerImgRight, playerX, playerY);
        } else {
            image(playerImgLeft, playerX, playerY);
        }

        if (!inventarKey) {
            fill(255, 153, 255);
            rect(keyX, keyY, cellSize, cellSize);
            image(keyImg, keyX, keyY);
            tint(255, 128);
            image(keyImg, 30, 7);
            tint(255, 255);
        } else {
            image(keyImg, 30, 7);
        }

        if (inventarKey) {
            image(doorOpenImg, exitX, exitY);
        } else {
            image(doorClosedImg, exitX, exitY);
        }

        if (playerX == keyX && playerY == keyY) {
            inventarKey = true;
        }

        if (!flashlight) {
            fill(255, 153, 255);
            rect(flashlightX, flashlightY, cellSize, cellSize);
            image(flashlightImg, flashlightX, flashlightY);
            tint(255, 128);
            image(flashlightImg, 0, 0);
            tint(255, 255);
        } else {
            image(flashlightImg, 1, 0);
        }

        if (playerX == flashlightX && playerY == flashlightY) {
            flashlight = true;
        }

        if (!visionActivate) {
            fill(255, 153, 255);
            rect(visionX, visionY, cellSize, cellSize);
            image(visionImg, visionX, visionY);
        }

        float rectX = playerX - gridSize, rectY = playerY - gridSize - 200, rectWidth = 2 * width, rectHeight = 2 * height;

        if (!revealMaze) {
            drawRadialGradient(rectX, rectY, rectWidth, rectHeight, color(0, 0, 0));
        }

        if (start) {
            textFont(text, 16);
            fill(0);
            text("", 190, 650);
        } else {
            textFont(text, 16);
            fill(255);
            text("To play press \"Start\"", 220, 650);
        }

        //Restart Game After Completion
        if (restart) {
            textFont(text, 16);
            fill(0);
            text("To play press \"Start\"", 220, 650);
        } else {
            textFont(text, 16);
            fill(255);
            text("", 190, 650);
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

        if (playerX == exitX && playerY == exitY && inventarKey) {
            gameCompleted = true;
            revealMaze = true;
        } else if (playerX == exitX && playerY == exitY && !inventarKey) {
            textFont(winMessage);
            textAlign(CENTER, CENTER);
            fill(255, 215, 0);
            text("Find the Key first!", width / 2, height / 3);

            textFont(text);
            textAlign(LEFT, BASELINE);
            fill(0);
        }

        if (gameCompleted) {
            revealMaze = true;
            restart = true;
            textFont(winMessage);
            textAlign(CENTER, CENTER);
            rectMode(CENTER);
            fill(5, 100);
            rect(width / 2, height / 3, width, 80);
            rectMode(CORNER);
            fill(255, 215, 0);
            stroke(0);
            text("You have found the exit!\nCongratulations!", width / 2, height / 3);

            textFont(text);
            textAlign(LEFT, BASELINE);
            fill(0);

            if (mousePressed && mouseX > 150 && mouseX < 250 && mouseY > 700 && mouseY < 740) {
                startButtonPressed = true;
                if (gameCompleted) {
                    reset = true;
                    playerX = 1;
                    playerY = 30;
                    gameCompleted = false;
                    revealMaze = false;
                    inventarKey = false;
                    flashlight = false;
                    visionActivate = false;
                    extracted();
                }
                restart = false;
            } else {
                startButtonPressed = false;
            }

            if (mousePressed && mouseX > 350 && mouseX < 450 && mouseY > 700 && mouseY < 740) {
                resetButtonPressed = true;
                if (gameCompleted) {
                    reset = true;
                    playerX = 1;
                    playerY = 30;
                    gameCompleted = false;
                    revealMaze = false;
                    restart = false;
                    inventarKey = false;
                    flashlight = false;
                    visionActivate = false;
                    extracted();
                }
            } else {
                resetButtonPressed = false;
            }

            if (playerX == exitX && playerY == exitY && inventarKey) {
                gameCompleted = true;
            }
        }
    }

    void drawRadialGradient(float x, float y, float w, float h, int c) {
        if (revealMaze) {
            fill(255, 100);  // Slightly transparent white
            rect(0, 0, gridSize, gridSize);  // Cover the entire screen
        } else {
            float centerX = x + w / 2.0f;
            float centerY = y + h / 2.0f;
            float maxDist = dist(centerX, centerY, x, y);

            float transparentRadius = 0;
            float gradientRadius; // Set the gradient radius to one fifth of the maximum dimension of the rectangle

            int numSegmentsX = 50; // Number of segments in the X direction
            int numSegmentsY = 50; // Number of segments in the Y direction

            float segmentWidth = w / numSegmentsX;
            float segmentHeight = h / numSegmentsY;

            if (playerX == visionX && playerY == visionY) {
                revealMaze = true;
                maze[visionY / 30][(visionX - 1) / 30] = 0;
                visionX = 0;
                visionY = 0;
                if (!scheduled) {
                    scheduled = true;
                    executorService.schedule(() -> {
                        revealMaze = false;
                        scheduled = false;
                    }, 2, TimeUnit.SECONDS);
                }
            }

            if (flashlight) {
                gradientRadius = max(w, h) / 8.0f;
            } else {
                gradientRadius = max(w, h) / 12.0f;

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
                    isRight = false;
                }
            } else if (keyCode == 39) {  //right
                if (playerX > 0 && playerX < 600 && maze[playerY / 30][(playerX / 30) + 1] != 1) {
                    playerX += speed; // Move right
                    isRight = true;
                }
            } else if (keyCode == 38) {   //up
                if (maze[(playerY / 30) - 1][playerX / 30] != 1) {
                    playerY -= speed; // Move up
                }
            } else if (keyCode == 40) {  //down
                if (maze[(playerY / 30) + 1][playerX / 30] != 1) {
                    playerY += speed; // Move down
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
