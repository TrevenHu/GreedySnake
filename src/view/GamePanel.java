package view;

import model.Food;
import model.Snake;
import util.Global;

import javax.swing.*;
import java.awt.*;

@SuppressWarnings("serial")
public class GamePanel extends JPanel {
    private Snake snake;
    private Food food;

    public void display(Snake snake, Food food) {
        this.snake = snake;
        this.food = food;
        this.repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        g.setColor(Color.LIGHT_GRAY);
        g.fillRect(0, 0, Global.WIDTH * Global.CELL_SIZE,
                Global.HEIGHT * Global.CELL_SIZE);
        System.out.println(food);
        if (snake != null && food != null) {
            System.out.println("paint");
            this.snake.draw(g);
            this.food.draw(g);
        }
    }
}
