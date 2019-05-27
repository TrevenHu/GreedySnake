package model;

import util.Global;
import util.SnakeListener;

import java.awt.*;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Random;
import java.util.Set;

public class Snake {
    private int oldDirection, newDirection;
    private boolean isLive;
    private Point oldTail;
    public int bodyLength;
    public boolean isDead;
    public Point foodPoint = null;

    private Set<SnakeListener> listeners = new HashSet<>();
    private LinkedList<Point> body = new LinkedList<>();

    public Snake() {
        init();
    }

    public void init() {
        int x = Global.WIDTH / 2 - 3;
        int y = Global.HEIGHT / 2;
        // init snake body with 3 nodes
        for (int i = 0; i < 3; i++) {
            body.addLast(new Point(x--, y));
        }
        // init direction: right
        oldDirection = newDirection = Direction.RIGHT.getDirectionCode();
        isLive = true;
    }

    public void changeDirection(int newDirection) {
        this.newDirection = newDirection;
    }

    public Point createFood(LinkedList<Point> body) {
        foodPoint = getRandomPoint();
        while (checkIfOverlappedWithBody(body)) {
            foodPoint = getRandomPoint();
        }
        return foodPoint;
    }

    public void eat() {
        body.addLast(oldTail);
    }

    public void move() {
        if (oldDirection != newDirection) {
            oldDirection = newDirection;
        }
        // remove the current tail node
        oldTail = body.removeLast();
        // get current head position
        int x = body.getFirst().x;
        int y = body.getFirst().y;
        switch (oldDirection) {
            // up
            case 0:
                y--;
                if (y < 0) {
                    y = Global.HEIGHT - 1;
                }
                break;
            // down
            case 1:
                y++;
                if (y >= Global.HEIGHT) {
                    y = 0;
                }
                break;
            // right
            case 2:
                x++;
                if (x >= Global.WIDTH) {
                    x = 0;
                }
                break;
            // left
            case 3:
                x--;
                if (x < 0) {
                    x = Global.WIDTH - 1;
                }
                break;
        }
        //记录蛇头的坐标
        Point newHead = new Point(x, y);
        //加头
        body.addFirst(newHead);
    }

    public boolean isHittingBody() {
        for (int i = 1; i < body.size(); i++) {
            if (body.get(i).equals(getHead())) {
                return true;
            }
        }
        return false;
    }

    public void draw(Graphics g) {
        for (Point p : body) {
            g.setColor(Color.GREEN);
            g.fill3DRect(p.x * Global.CELL_SIZE, p.y * Global.CELL_SIZE, Global.CELL_SIZE, Global.CELL_SIZE, true);
        }
        // re-draw head
        g.setColor(Color.RED);
        g.fill3DRect(getHead().x * Global.CELL_SIZE, getHead().y * Global.CELL_SIZE, Global.CELL_SIZE, Global.CELL_SIZE, true);
    }

    public void die() {
        isLive = false;
        isDead = true;
    }

    public class SnakeDriver implements Runnable {
        public void run() {
            while (isLive) {
                move();
                setBodyLength();
                for (SnakeListener sl : listeners) {
                    sl.snakeMove(Snake.this);
                }
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public void start() {
        new Thread(new SnakeDriver()).start();
    }

    public void addSnakeListener(SnakeListener sl) {
        if(sl != null) {
            this.listeners.add(sl);
        }
    }

    public void setBodyLength() {
        bodyLength = body.size();
    }

    public Point getHead() {
        return body.getFirst();
    }

    public Point addTail(Point pos) {
        this.body.addLast(pos);
        return pos;
    }

    public LinkedList<Point> getBody() {
        return body;
    }

    private Point getRandomPoint() {
        Random random = new Random();
        int x = 0, y = 0;
        x = random.nextInt(Global.WIDTH);
        y = random.nextInt(Global.HEIGHT);
        return new Point(x, y);
    }

    private boolean checkIfOverlappedWithBody(LinkedList<Point> body) {
        for (Point p : body) {
            if (p.x == foodPoint.x && p.y == foodPoint.y) {
                return true;
            }
        }
        return false;
    }

    public Point getFoodPoint() {
        return createFood(body);
    }

    public void bodyClear() {
        body.clear();
    }
}
