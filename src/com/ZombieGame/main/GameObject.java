package com.ZombieGame.main;

import java.awt.*;

public abstract class GameObject {

    protected float positionX;
    protected float positionY;
    protected ObjectID id;

    protected float velocityX;
    protected float velocityY;
    protected float health;
    protected Direction direction;
    protected ObjectState objectState;

    public GameObject(int positionX, int positionY, ObjectID objectId) {
        this.positionX = positionX;
        this.positionY = positionY;
        this.id = objectId;
        this.objectState = ObjectState.ACTIVE;
    }

    public abstract void tick();
    public abstract void render(Graphics graphics);
    public abstract Rectangle getBounds();

    public void setPositionX(float x) {
        this.positionX = x;
    }

    public void setPositionY(float y) {
        this.positionY = y;
    }

    public float getPositionX() {
        return this.positionX;
    }

    public float getPositionY() {
        return this.positionY;
    }

    public void setVelocityX(float x) {
        this.velocityX = x;
    }

    public void setVelocityY(float y) {
        this.velocityY = y;
    }

    public float getVelocityX() {
        return this.velocityX;
    }

    public float getVelocityY() {
        return this.velocityY;
    }

    public void setID(ObjectID id) {
        this.id = id;
    }

    public ObjectID getID() {
        return this.id;
    }

    public float getHealth() {
        return health;
    }

    public void setHealth(float health) {
        this.health = health;
    }

    public void setDirection(Direction newDirection) {
        this.direction = newDirection;
    }

    public Direction getDirection() {
        return direction;
    }

    public Direction getDirectionFromVelocity() {
        Direction vertical = (velocityY > 0)? Direction.SOUTH : Direction.NORTH;
        Direction horizontal = (velocityX > 0)? Direction.EAST : Direction.WEST;
        Direction currentDirection = Direction.NONE;

        if (velocityY == 0) {
            vertical = Direction.NONE;
        }
        if (velocityX == 0) {
            horizontal = Direction.NONE;
        }

        switch (vertical) {
            case NORTH:
                switch (horizontal) {
                    case WEST:
                        currentDirection = Direction.NORTHWEST;
                        break;
                    case EAST:
                        currentDirection = Direction.NORTHEAST;
                        break;
                    case NONE:
                        currentDirection = Direction.NORTH;
                        break;
                }
                break;

            case SOUTH:
                switch (horizontal) {
                    case WEST:
                        currentDirection = Direction.SOUTHWEST;
                        break;
                    case EAST:
                        currentDirection = Direction.SOUTHEAST;
                        break;
                    case NONE:
                        currentDirection = Direction.SOUTH;
                        break;
                }
                break;

            case NONE:
                switch (horizontal) {
                    case WEST:
                        currentDirection = Direction.WEST;
                        break;
                    case EAST:
                        currentDirection = Direction.EAST;
                        break;
                    case NONE:
                        currentDirection = Direction.NONE;
                        break;
                }
                break;
        }

        if (currentDirection == Direction.NONE) {
            currentDirection = direction;
        }

        return currentDirection;
    }

    public void setObjectState(ObjectState state) {
        this.objectState = state;
    }

    public ObjectState getObjectState() {
        return this.objectState;
    }

}
