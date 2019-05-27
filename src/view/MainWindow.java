package view;

import controller.Controller;
import model.Food;
import model.Snake;
import util.Global;

import javax.swing.*;
import java.awt.*;


public class MainWindow extends JFrame {
    GamePanel gamePanel = new GamePanel();

    Snake snake = new Snake();
    Food food = new Food();
    Controller controller = new Controller(snake, food, gamePanel);

    public MainWindow() {
        setResizable(false);
        setTitle("Greedy Snake");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocation(getToolkit().getScreenSize().width / 2 - Global.CELL_SIZE * Global.WIDTH / 2,
                getToolkit().getScreenSize().height / 2 - Global.CELL_SIZE * Global.WIDTH / 2);

        setSize(400, 200);
        addKeyListener(controller);

        gamePanel.setFocusTraversalPolicyProvider(true);
        gamePanel.setFocusCycleRoot(true);
        gamePanel.setSize(Global.CELL_SIZE * Global.WIDTH, Global.CELL_SIZE * Global.HEIGHT);
        gamePanel.setLayout(new BorderLayout(0, 0));

        snake.addSnakeListener(controller);
        controller.beginGame();

        add(gamePanel);
    }
}
