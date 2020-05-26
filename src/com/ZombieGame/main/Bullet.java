package com.ZombieGame.main;

import java.awt.*;

public class Bullet extends GameObject {

    public static final int BULLET_WIDTH = 20;
    public static final int BULLET_HEIGHT = 1;
    public static final int BULLET_THICKNESS = 3;

    private Handler _handler;
    private Camera _camera;
    private Direction _direction;

    public Bullet(int positionX, int positionY, Direction direction, ObjectID objectId, Handler handler, Camera camera) {
        super(positionX, positionY, objectId);
        this._handler = handler;
        this._camera = camera;
        this._direction = direction;

        switch(direction) {
            case NORTH:
                velocityX = 0;
                velocityY = -50;
                break;
            case SOUTH:
                velocityX = 0;
                velocityY = 50;
                break;
            case EAST:
                velocityX = 50;
                velocityY = 0;
                break;
            case WEST:
                velocityX = -50;
                velocityY = 0;
                break;
            case NORTHEAST:
                velocityX = 50;
                velocityY = -50;
                break;
            case NORTHWEST:
                velocityX = -50;
                velocityY = -50;
                break;
            case SOUTHEAST:
                velocityX = 50;
                velocityY = 50;
                break;
            case SOUTHWEST:
                velocityX = -50;
                velocityY = 50;
                break;
        }
    }

    @Override
    public void tick() {
        positionX += velocityX;
        positionY += velocityY;

        collision();
    }

    @Override
    public void render(Graphics graphics) {
        Graphics2D graphics2D = (Graphics2D)graphics;

        graphics2D.setStroke(new BasicStroke(BULLET_THICKNESS));
        graphics2D.setColor(Color.BLACK);

        switch (_direction) {
            case NORTH:
                graphics2D.drawLine((int)positionX, (int)positionY, (int)positionX, (int)positionY + BULLET_WIDTH);
                break;
            case SOUTH:
                graphics2D.drawLine((int)positionX, (int)positionY, (int)positionX, (int)positionY - BULLET_WIDTH);
                break;
            case EAST:
                graphics2D.drawLine((int)positionX, (int)positionY, (int)positionX + BULLET_WIDTH, (int)positionY);
                break;
            case WEST:
                graphics2D.drawLine((int)positionX, (int)positionY, (int)positionX - BULLET_WIDTH, (int)positionY);
                break;
            case NORTHEAST:
                graphics2D.drawLine((int)positionX, (int)positionY, (int)positionX + BULLET_WIDTH, (int)positionY - BULLET_WIDTH);
                break;
            case NORTHWEST:
                graphics2D.drawLine((int)positionX, (int)positionY, (int)positionX - BULLET_WIDTH, (int)positionY - BULLET_WIDTH);
                break;
            case SOUTHEAST:
                graphics2D.drawLine((int)positionX, (int)positionY, (int)positionX + BULLET_WIDTH, (int)positionY + BULLET_WIDTH);
                break;
            case SOUTHWEST:
                graphics2D.drawLine((int)positionX, (int)positionY, (int)positionX - BULLET_WIDTH, (int)positionY + BULLET_WIDTH);
                break;
        }

        graphics2D.setStroke(new BasicStroke());
    }

    @Override
    public Rectangle getBounds() {
        Rectangle bounds = new Rectangle((int)positionX, (int)positionY, BULLET_WIDTH, BULLET_HEIGHT);

        return bounds;
    }

    private void collision() {
        for (GameObject gameObject : _handler.mapObjects) {
            if (gameObject.getID() == ObjectID.MAPBLOCK) {
                Rectangle bulletBounds = this.getBounds();
                Rectangle mapBlockBounds = gameObject.getBounds();
                boolean isCollided = bulletBounds.intersects(mapBlockBounds);

                if (isCollided) {
                    this.setObjectState(ObjectState.INACTIVE);
                }
            }
        }
    }

}
