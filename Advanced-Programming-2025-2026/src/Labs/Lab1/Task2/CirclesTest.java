package Labs.Lab1.Task2;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

enum TYPE {
    POINT,
    CIRCLE
}

enum DIRECTION {
    UP,
    DOWN,
    LEFT,
    RIGHT
}

interface Movable {
    void moveUp() throws ObjectCanNotBeMovedException;
    void moveDown() throws ObjectCanNotBeMovedException;
    void moveLeft() throws ObjectCanNotBeMovedException;
    void moveRight() throws ObjectCanNotBeMovedException;

    int getCurrentXPosition();
    int getCurrentYPosition();
}

class ObjectCanNotBeMovedException extends Exception {
    public ObjectCanNotBeMovedException(String message) {
        super(message);
    }
}

class MovableObjectNotFittableException extends Exception {
    public MovableObjectNotFittableException(String message) {
        super(message);
    }
}

class MovablePoint implements Movable {
    private int x;
    private int y;
    private int xSpeed;
    private int ySpeed;

    public MovablePoint(int x, int y, int xSpeed, int ySpeed) {
        this.x = x;
        this.y = y;
        this.xSpeed = xSpeed;
        this.ySpeed = ySpeed;
    }

    @Override
    public void moveUp() throws ObjectCanNotBeMovedException {
        if (y + ySpeed > MovablesCollection.getYMax()) {
            int newY = y + ySpeed;
            throw new ObjectCanNotBeMovedException(
                    String.format("Point (%d,%d) is out of bounds", x, newY));
        }
        y += ySpeed;
    }

    @Override
    public void moveDown() throws ObjectCanNotBeMovedException {
        if (y - ySpeed < 0) {
            int newY = y - ySpeed;
            throw new ObjectCanNotBeMovedException(
                    String.format("Point (%d,%d) is out of bounds", x, newY));
        }
        y -= ySpeed;
    }

    @Override
    public void moveLeft() throws ObjectCanNotBeMovedException {
        if (x - xSpeed < 0) {
            int newX = x - xSpeed;
            throw new ObjectCanNotBeMovedException(
                    String.format("Point (%d,%d) is out of bounds", newX, y));
        }
        x -= xSpeed;
    }

    @Override
    public void moveRight() throws ObjectCanNotBeMovedException {
        if (x + xSpeed > MovablesCollection.getXMax()) {
            int newX = x + xSpeed;
            throw new ObjectCanNotBeMovedException(
                    String.format("Point (%d,%d) is out of bounds", newX, y));
        }
        x += xSpeed;
    }

    @Override
    public int getCurrentXPosition() {
        return x;
    }

    @Override
    public int getCurrentYPosition() {
        return y;
    }

    public int getXSpeed() {
        return xSpeed;
    }

    public int getYSpeed() {
        return ySpeed;
    }

    @Override
    public String toString() {
        return String.format("Movable point with coordinates (%d,%d)", x, y);
    }
}

class MovableCircle implements Movable {
    private int radius;
    private MovablePoint center;

    public MovableCircle(int radius, MovablePoint center) {
        this.radius = radius;
        this.center = center;
    }

    @Override
    public void moveUp() throws ObjectCanNotBeMovedException {
        int newY = center.getCurrentYPosition() + center.getYSpeed();
        if (newY > MovablesCollection.getYMax()) {
            throw new ObjectCanNotBeMovedException(
                    String.format("Point (%d,%d) is out of bounds",
                            center.getCurrentXPosition(), newY));
        }
        center.moveUp();
    }

    @Override
    public void moveDown() throws ObjectCanNotBeMovedException {
        int newY = center.getCurrentYPosition() - center.getYSpeed();
        if (newY < 0) {
            throw new ObjectCanNotBeMovedException(
                    String.format("Point (%d,%d) is out of bounds",
                            center.getCurrentXPosition(), newY));
        }
        center.moveDown();
    }

    @Override
    public void moveLeft() throws ObjectCanNotBeMovedException {
        int newX = center.getCurrentXPosition() - center.getXSpeed();
        if (newX < 0) {
            throw new ObjectCanNotBeMovedException(
                    String.format("Point (%d,%d) is out of bounds",
                            newX, center.getCurrentYPosition()));
        }
        center.moveLeft();
    }

    @Override
    public void moveRight() throws ObjectCanNotBeMovedException {
        int newX = center.getCurrentXPosition() + center.getXSpeed();
        if (newX > MovablesCollection.getXMax()) {
            throw new ObjectCanNotBeMovedException(
                    String.format("Point (%d,%d) is out of bounds",
                            newX, center.getCurrentYPosition()));
        }
        center.moveRight();
    }

    @Override
    public int getCurrentXPosition() {
        return center.getCurrentXPosition();
    }

    @Override
    public int getCurrentYPosition() {
        return center.getCurrentYPosition();
    }

    public int getRadius() {
        return radius;
    }

    @Override
    public String toString() {
        return String.format("Movable circle with center coordinates (%d,%d) and radius %d",
                center.getCurrentXPosition(), center.getCurrentYPosition(), radius);
    }
}

class MovablesCollection {
    private static int xMax;
    private static int yMax;
    private List<Movable> movables;

    public MovablesCollection(int maxX, int maxY) {
        xMax = maxX;
        yMax = maxY;
        movables = new ArrayList<>();
    }

    public static void setxMax(int newXMax) {
        xMax = newXMax;
    }

    public static void setyMax(int newYMax) {
        yMax = newYMax;
    }

    public static int getXMax() {
        return xMax;
    }

    public static int getYMax() {
        return yMax;
    }

    public void addMovableObject(Movable movable) {
        try {
            if (movable instanceof MovablePoint) {
                MovablePoint point = (MovablePoint) movable;
                if (point.getCurrentXPosition() < 0 || point.getCurrentXPosition() > xMax ||
                        point.getCurrentYPosition() < 0 || point.getCurrentYPosition() > yMax) {
                    throw new MovableObjectNotFittableException(
                            String.format("Point with coordinates (%d,%d) can not be fitted into the collection",
                                    point.getCurrentXPosition(), point.getCurrentYPosition()));
                }
            } else if (movable instanceof MovableCircle) {
                MovableCircle circle = (MovableCircle) movable;
                int x = circle.getCurrentXPosition();
                int y = circle.getCurrentYPosition();
                int r = circle.getRadius();
                if (x - r < 0 || x + r > xMax || y - r < 0 || y + r > yMax) {
                    throw new MovableObjectNotFittableException(
                            String.format("Movable circle with center (%d,%d) and radius %d can not be fitted into the collection",
                                    x, y, r));
                }
            }
            movables.add(movable);
        } catch (MovableObjectNotFittableException e) {
            System.out.println(e.getMessage());
        }
    }

    public void moveObjectsFromTypeWithDirection(TYPE type, DIRECTION direction) {
        for (Movable movable : movables) {
            boolean isPoint = movable instanceof MovablePoint;
            boolean isCircle = movable instanceof MovableCircle;

            if ((type == TYPE.POINT && isPoint) || (type == TYPE.CIRCLE && isCircle)) {
                try {
                    switch (direction) {
                        case UP: movable.moveUp(); break;
                        case DOWN: movable.moveDown(); break;
                        case LEFT: movable.moveLeft(); break;
                        case RIGHT: movable.moveRight(); break;
                    }
                } catch (ObjectCanNotBeMovedException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("Collection of movable objects with size %d:\n", movables.size()));
        for (Movable movable : movables) {
            sb.append(movable.toString()).append("\n");
        }
        return sb.toString();
    }
}

// ===================== MAIN TEST =====================
public class CirclesTest {
    public static void main(String[] args) {
        System.out.println("===COLLECTION CONSTRUCTOR AND ADD METHOD TEST===");
        MovablesCollection collection = new MovablesCollection(100, 100);
        Scanner sc = new Scanner(System.in);
        int samples = Integer.parseInt(sc.nextLine());
        for (int i = 0; i < samples; i++) {
            String inputLine = sc.nextLine();
            String[] parts = inputLine.split(" ");

            int x = Integer.parseInt(parts[1]);
            int y = Integer.parseInt(parts[2]);
            int xSpeed = Integer.parseInt(parts[3]);
            int ySpeed = Integer.parseInt(parts[4]);

            if (Integer.parseInt(parts[0]) == 0) { //point
                collection.addMovableObject(new MovablePoint(x, y, xSpeed, ySpeed));
            } else { //circle
                int radius = Integer.parseInt(parts[5]);
                collection.addMovableObject(new MovableCircle(radius, new MovablePoint(x, y, xSpeed, ySpeed)));
            }
        }

        System.out.println(collection.toString());
        System.out.println("MOVE POINTS TO THE LEFT");
        collection.moveObjectsFromTypeWithDirection(TYPE.POINT, DIRECTION.LEFT);
        System.out.println(collection.toString());

        System.out.println("MOVE CIRCLES DOWN");
        collection.moveObjectsFromTypeWithDirection(TYPE.CIRCLE, DIRECTION.DOWN);
        System.out.println(collection.toString());

        System.out.println("CHANGE X_MAX AND Y_MAX");
        MovablesCollection.setxMax(90);
        MovablesCollection.setyMax(90);

        System.out.println("MOVE POINTS TO THE RIGHT");
        collection.moveObjectsFromTypeWithDirection(TYPE.POINT, DIRECTION.RIGHT);
        System.out.println(collection.toString());

        System.out.println("MOVE CIRCLES UP");
        collection.moveObjectsFromTypeWithDirection(TYPE.CIRCLE, DIRECTION.UP);
        System.out.println(collection.toString());
    }
}
