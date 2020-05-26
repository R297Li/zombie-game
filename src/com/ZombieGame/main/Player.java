package com.ZombieGame.main;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.LinkedList;

public class Player extends GameObject {

    public static final int PLAYER_WIDTH = 24;
    public static final int PLAYER_HEIGHT = 56;

    private PlayerControls _playerControls;
    private float _velocityMultiplier;
    private Handler _handler;

    public Player(int positionX, int positionY, ObjectID objectId, Handler handler) {
        super(positionX, positionY, objectId);
        this._handler = handler;
        this.health = 100;
        _playerControls = new PlayerControls(objectId);
    }

    @Override
    public void tick() {
        positionX += velocityX;
        positionY += velocityY;

        collision();

        if (health <= 0) {
            this.objectState = ObjectState.INACTIVE;
        }
    }

    @Override
    public void render(Graphics graphics) {
        graphics.setColor(Color.BLACK);
        graphics.fillRect((int)positionX, (int)positionY, PLAYER_WIDTH, PLAYER_HEIGHT);
    }

    @Override
    public Rectangle getBounds() {
        Rectangle bounds = new Rectangle((int)positionX, (int)positionY, PLAYER_WIDTH, PLAYER_HEIGHT);

        return bounds;
    }

    public float getVelocityMultiplier() {
        return _velocityMultiplier;
    }

    public void setVelocityMultiplier(float velocityMultiplier) {
        this._velocityMultiplier = velocityMultiplier;
    }

    public PlayerControls getPlayerControls() {
        return _playerControls;
    }

    public void setPlayerControls(PlayerControls playerControls) {
        this._playerControls = playerControls;
    }

    private void collision() {

        for (GameObject gameObject :  _handler.enemyObjects) {
            if (gameObject.getObjectState() == ObjectState.ACTIVE) {
                Rectangle playerBounds = this.getBounds();
                Rectangle enemyBounds = gameObject.getBounds();
                boolean isCollided = playerBounds.intersects(enemyBounds);

                if (isCollided) {
                    this.health -= 1;
                }
            }
        }

        for (GameObject gameObject : _handler.mapObjects) {
            ObjectID objectId = gameObject.getID();

            if (objectId == ObjectID.MAPBLOCK) {
                Rectangle playerBounds = this.getBounds();
                Rectangle mapBounds = gameObject.getBounds();

                if (isThereCollision((int)(positionX + velocityX), (int)positionY, playerBounds, mapBounds)) {
                    positionX += velocityX * -1;
                }

                if (isThereCollision((int)positionX, (int)(positionY + velocityY), playerBounds, mapBounds)) {
                    positionY += velocityY * -1;
                }
            }
        }
    }

    private boolean isThereCollision(int x, int y, Rectangle playerBounds, Rectangle collisionBounds) {
        playerBounds.x = x;
        playerBounds.y = y;

        boolean isCollided = playerBounds.intersects(collisionBounds);

        if (isCollided) {
            return true;
        }
        return false;
    }

}
