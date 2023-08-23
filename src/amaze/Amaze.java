package amaze;

import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PImage;

public class Amaze extends PApplet {

    public static void main(String[] args) {
        PApplet.main(new String[]{Amaze.class.getName()});
    }

    PImage playerImg;
    int playerX, playerY;
    int speed = 2;
    int playerSize = 30;
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
    int rows = 20;
    int columns = 20;

    public void settings() {
        size(600, 600);
    }

    public void setup() {
        noLoop(); // Draw the maze only once
        playerImg = loadImage("./data/knight.png");
        playerImg.resize(playerSize, 0);
        playerX = 0;
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
        rect(playerX, playerY, playerSize, playerSize);
        image(playerImg, playerX, playerY);

        if (keyPressed) {
            if (keyCode == LEFT) {
                playerX -= speed; // Move left
            } else if (keyCode == RIGHT) {
                playerX += speed; // Move right
            } else if (keyCode == UP) {
                playerY -= speed; // Move up
            } else if (keyCode == DOWN) {
                playerY += speed; // Move down
            }
        }
        playerX = constrain(playerX, 0, width - playerImg.width);
        playerY = constrain(playerY, 0, height - playerImg.height);

    }

    public void keyPressed() {
        if (keyCode == UP) {
            if (playerY > 0 && playerY < 600 && !maze[(playerY / 30) - 1][playerX / 30]) {
                playerY--;
            }
        } else if (keyCode == LEFT) {
            if (playerX > 0 && playerX < 600 && !maze[playerY / 30][(playerX / 30) - 1]) {
                playerX--;
            }
        } else if (keyCode == RIGHT) {
            if (playerX > 0 && playerX < 600 && !maze[playerY / 30][(playerX / 30) + 1]) {
                playerX++;
            }
        } else if (keyCode == DOWN) {
            if (playerY > 0 && playerY < 600 && !maze[(playerY / 30) + 1][playerX / 30]) {
                playerY++;
            }
        }
    }
}
