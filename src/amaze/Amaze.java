package amaze;

import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PGraphics;
import processing.core.PImage;

public class Amaze extends PApplet {

    public static void main(String[] args) {
        PApplet.main(new String[]{Amaze.class.getName()});
    }

    PImage playerImg;
    PFont f;

    int playerX, playerY;
    int speed = 30;
    int playerSize = 30;

    boolean start = false;
    boolean reset = false;
    boolean startButtonPressed = false;
    boolean resetButtonPressed = false;

    PGraphics spotlight;
    /*int[][] maze = {
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            {0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 1, 1, 1, 0, 0, 0},
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
    };*/

    boolean[][] maze = {
            {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},
            {true, true, true, false, true, true, true, true, true, true, true, false, true, false, false, false, false, true, true, true},
            {false, false, true, false, true, false, false, false, false, false, true, false, true, true, true, false, false, true, false, false},
            {false, true, true, true, true, false, true, true, true, true, true, false, true, false, true, true, true, true, true, false},
            {false, true, false, false, false, false, false, true, false, false, true, false, true, true, true, false, true, false, false, false},
            {false, true, true, true, true, true, false, true, false, false, true, false, false, false, true, false, false, false, false, false},
            {false, true, false, false, false, true, false, true, false, false, true, false, true, true, true, false, true, true, true, false},
            {false, true, true, false, true, true, false, true, false, false, true, false, false, false, true, false, false, false, true, false},
            {false, false, false, false, true, false, false, true, true, true, true, false, true, true, true, true, true, false, true, false},
            {false, true, true, true, true, true, false, false, false, false, true, false, true, false, false, false, true, false, true, false},
            {false, false, false, false, false, true, false, false, true, false, true, false, true, true, true, false, true, false, true, false},
            {false, true, true, true, false, true, true, true, true, false, true, false, true, false, false, false, true, false, true, false},
            {false, true, false, false, false, true, false, false, false, false, true, true, true, true, true, false, true, false, true, false},
            {false, true, true, true, true, true, true, true, true, false, true, false, false, false, true, false, true, false, true, false},
            {false, true, false, false, false, false, false, false, false, false, true, false, true, false, true, true, true, false, true, false},
            {false, true, false, true, true, true, true, true, true, false, true, false, true, false, true, false, true, false, true, false},
            {false, true, false, false, false, false, false, false, true, false, true, false, true, true, true, false, true, false, true, false},
            {false, true, true, true, true, true, true, true, true, true, true, false, false, false, false, false, true, false, true, false},
            {false, false, false, false, false, true, false, false, false, false, false, true, true, true, true, true, true, true, true, false},
            {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false}
    };

    int cellSize = 30;

    public void settings() {
        size(600, 800);
    }

    public void setup() {
        playerImg = loadImage("./ressources/knight.png");
        f = createFont("Arial", 16, true);
        playerImg.resize(playerSize, 0);
        playerX = 1;
        playerY = 30;

        spotlight = createGraphics(width, height);
    }

    public void draw() {
        background(255);

        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < maze[i].length; j++) {
                if (!maze[i][j]) {
                    fill(0); // Draw boundaries in black
                } else {
                    fill(255); // Draw open spaces in white
                }
                noStroke();
                rect(j * cellSize, i * cellSize, cellSize, cellSize);
            }
        }
        //fill(0);
        //rect(0, 0, width, height);

        //fill(255);
        //ellipse(playerX + playerImg.width / 2, playerY + playerImg.height/2, 100, 100);

        fill(255);
        rect(playerX + 5, playerY + 5, playerSize - 15, playerSize - 15);
        image(playerImg, playerX, playerY);

        /*textFont(f, 16);
        fill(0);
        text("Um zu spielen, klicke auf \"Start\"", 190, 650);*/

        if (start) {
            textFont(f, 16);
            fill(0);
            text("", 190, 650);
        } else {
            textFont(f, 16);
            fill(0);
            text("Um zu spielen, klicke auf \"Start\"", 190, 650);
        }

        if(reset){
            playerX = 1;
            playerY = 30;
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

        playerX = constrain(playerX, 0, width - playerImg.width);
        playerY = constrain(playerY, 0, height - playerImg.height);
    }

    public void keyPressed() {
        if (!start) {
            return;
        }

        if (reset) {
            return;
        }

        try {
            if (keyCode == 37) {
                if (playerX > 0 && playerX < 600 && maze[playerY / 30][(playerX / 30) - 1]) {
                    playerX -= speed; // Move left
                    delay(200);
                }
            } else if (keyCode == 39) {
                if (playerX > 0 && playerX < 600 && maze[playerY / 30][(playerX / 30) + 1]) {
                    playerX += speed; // Move right
                    delay(200);
                }
            } else if (keyCode == 38) {
                if (playerY > 0 && playerY < 600 && maze[(playerY / 30) - 1][playerX / 30]) {
                    playerY -= speed; // Move up
                    delay(200);
                }
            } else if (keyCode == 40) {
                if (playerY > 0 && playerY < 600 && maze[(playerY / 30) + 1][playerX / 30]) {
                    playerY += speed; // Move down
                    delay(200);
                }
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            //ignored
        }
    }
    //playerX = constrain(playerX, 0, width - playerImg.width);
    //playerY = constrain(playerY, 0, height - playerImg.height);


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

