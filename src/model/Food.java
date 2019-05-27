package model;

import util.Global;

import java.awt.*;

public class Food extends Point {
    Point point = null;

    public void newFood(Point p) {
        this.point = p;
        this.setLocation(p);
    }

    public boolean isSnakeEatFood(Snake snake) {
        return this.equals(snake.getHead());
    }

    public void draw(Graphics g) {
        System.out.println("orange");
        g.setColor(Color.ORANGE);
        g.fill3DRect(point.x * Global.CELL_SIZE, point.y * Global.CELL_SIZE, Global.CELL_SIZE, Global.CELL_SIZE, true);
    }
}
