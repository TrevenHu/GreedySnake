package controller;

import model.Direction;
import model.Food;
import model.Snake;
import util.Global;
import util.SnakeListener;
import view.GamePanel;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.*;

public class Controller extends KeyAdapter implements SnakeListener {
    private Snake snake;
    private Food food;
    private GamePanel gamePanel;

    public int score = 0;
    public int maxScore;
    public Thread thread;

    public Controller(Snake snake, Food food, GamePanel gamePanel) {
        super();
        this.snake = snake;
        this.food = food;
        this.gamePanel = gamePanel;
        readFile();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP:
                snake.changeDirection(Direction.UP.getDirectionCode());
                break;
            case KeyEvent.VK_DOWN:
                snake.changeDirection(Direction.DOWN.getDirectionCode());
                break;
            case KeyEvent.VK_RIGHT:
                snake.changeDirection(Direction.RIGHT.getDirectionCode());
                break;
            case KeyEvent.VK_LEFT:
                snake.changeDirection(Direction.LEFT.getDirectionCode());
                break;
            case KeyEvent.VK_SHIFT:
                newGame();
                break;
        }
    }

    public void beginGame() {
        score = 0;
        readFile();
        System.out.println(String.format("%d", maxScore));
        food.newFood(snake.getFoodPoint());
        snake.start();
        new Thread(thread).start();
    }

    public void newGame() {
        snake.bodyClear();
        snake.init();
        System.out.println("snake init");
        score = 0;
        food.newFood(snake.getFoodPoint());
        if (snake.isDead) {
            beginGame();
            snake.isDead = false;
        }
    }

    @Override
    public void snakeMove(Snake snake) {
        if (Global.count - this.snake.bodyLength < 3) {
            snake.die();
            writeMaxScore();
            JOptionPane.showMessageDialog(gamePanel, "Game Over\n       Score：" + score);
        }
        if (food.isSnakeEatFood(snake)) {
            snake.eat();
            food.newFood(snake.getFoodPoint());
            this.score += 10;

        }
        if (snake.isHittingBody()) {
            snake.die();
            writeMaxScore();
            JOptionPane.showMessageDialog(gamePanel, "Game Over！\n       游戏得分：" + score);
        }

        if (!snake.isHittingBody()) {
            System.out.println("display");
            gamePanel.display(snake, food);
        }
    }

    public void readFile() {
        File file = new File("MaxScore.txt");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        BufferedReader br;
        try {
            br = new BufferedReader(
                    new InputStreamReader(
                            new FileInputStream(file), "UTF-8"));
            maxScore = br.read();
            br.close();

        } catch (UnsupportedEncodingException e) {
            System.out.println("Error: Caught UnsupportedEncodingException!");
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            System.out.println("Error: Caught FileNotFoundException!");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("Error: Caught IOException!");
            e.printStackTrace();
        }
    }

    public void writeMaxScore() {
        if (score > maxScore) {
            maxScore = score;
            writeFile();
        }
    }

    public void writeFile() {

        File file = new File("MaxScore.txt");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {

            BufferedWriter bw = new BufferedWriter(
                    new OutputStreamWriter(
                            new FileOutputStream(file), "UTF-8"));
            bw.write(maxScore);
            bw.close();

        } catch (UnsupportedEncodingException e) {
            System.out.println("Error: Caught UnsupportedEncodingException!");
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            System.out.println("Error: Caught FileNotFoundException!");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("Error: Caught IOException!");
            e.printStackTrace();
        }
    }

    public Thread startRefresh(Thread thread) {
        this.thread = thread;
        return this.thread;
    }

}
