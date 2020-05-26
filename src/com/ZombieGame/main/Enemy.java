package com.ZombieGame.main;

import com.sun.nio.file.SensitivityWatchEventModifier;

import java.awt.*;
import java.util.Random;

public class Enemy extends GameObject {

    public static final int ENEMY_WIDTH = 24;
    public static final int ENEMY_HEIGHT = 56;

    private float _velocityMultiplier = 2;
    private float _maxVelocity = 4;
    private float _minVelocity = 2;
    private int _health;
    private int _id;
    private Direction _mapCollisionDirection;
    private long _mapCollisionTimer;
    private long _walkThroughWallTimer = 2000;
    private boolean _canWalkThroughWall;

    private Handler _handler;
    private GameObject _player;
    private Game _game;

    public Enemy(int positionX, int positionY, ObjectID objectId, Handler handler, Game game) {
        super(positionX, positionY, objectId);

        this._handler = handler;
        this._game = game;
        this._canWalkThroughWall = false;
        this._mapCollisionTimer = 0;

        for (GameObject gameObject : _handler.playerObjects) {
            if (gameObject.getID() == ObjectID.PLAYER1) {
                _player = gameObject;
                break;
            }
        }

        double randomVelocity = _minVelocity + Math.random() * (_maxVelocity - _minVelocity);
        _velocityMultiplier = Game.clamp((float)randomVelocity, _minVelocity, _maxVelocity);

        if (_velocityMultiplier < 2) {
            _health = 100;
        } else if (_velocityMultiplier < 3) {
            _health = 75;
        } else {
            _health = 50;
        }
    }

    @Override
    public void tick() {
        if (_game.getGameState() != GameState.LEVELTRANSITION) {
            positionX += velocityX;
            positionY += velocityY;

            float playerX = _player.getPositionX();
            float playerY = _player.getPositionY();

            float diffDistanceX = playerX - positionX;
            float diffDistanceY = playerY - positionY;

            float diffDistanceTotal = (float) Math.sqrt((diffDistanceX * diffDistanceX) + (diffDistanceY * diffDistanceY));

            velocityX = ((1 / diffDistanceTotal) * diffDistanceX) * _velocityMultiplier;
            velocityY = ((1 / diffDistanceTotal) * diffDistanceY) * _velocityMultiplier;

            collision();
        }
    }

    @Override
    public void render(Graphics graphics) {
        if (_game.getGameState() != GameState.LEVELTRANSITION) {
            graphics.setColor(Color.RED);
            graphics.fillRect((int) positionX, (int) positionY, ENEMY_WIDTH, ENEMY_HEIGHT);
        }
    }

    @Override
    public Rectangle getBounds() {
        Rectangle bounds = new Rectangle((int)positionX, (int)positionY, ENEMY_WIDTH, ENEMY_HEIGHT);

        return bounds;
    }

    public int getId() {
        return _id;
    }

    public void setId(int id) {
        this._id = id;
    }

    private void collision() {
        for (GameObject gameObject : _handler.bulletObjects) {
            if (gameObject.getObjectState() == ObjectState.ACTIVE) {
                Rectangle enemyBounds = this.getBounds();
                Rectangle bulletBounds = gameObject.getBounds();
                boolean isCollided = enemyBounds.intersects(bulletBounds);

                if (isCollided) {
                    this._health -= 25;
                    gameObject.setObjectState(ObjectState.INACTIVE);
                }

                if (_health <= 0) {
                    this.setObjectState(ObjectState.INACTIVE);
                }
            }
        }

        for (GameObject gameObject : _handler.mapObjects) {
            ObjectID objectId = gameObject.getID();

            if (objectId == ObjectID.MAPBLOCK) {
                Rectangle enemyBounds = this.getBounds();
                Rectangle mapBounds = gameObject.getBounds();

                boolean horizontalCollision = isThereCollision((int)(positionX + velocityX), (int)positionY, enemyBounds, mapBounds);
                boolean verticalCollision = isThereCollision((int)positionX, (int)(positionY + velocityY), enemyBounds, mapBounds);

                if (!_canWalkThroughWall && horizontalCollision) {
                    positionX += velocityX * -1;

                    if (_mapCollisionTimer == 0) {
                        _mapCollisionTimer = System.currentTimeMillis();
                    }
                    if (System.currentTimeMillis() - _mapCollisionTimer > _walkThroughWallTimer) {
                        _canWalkThroughWall = true;
                        _mapCollisionTimer = System.currentTimeMillis();
                    }
                }

                if (!_canWalkThroughWall && verticalCollision) {
                    positionY += velocityY * -1;

                    if (_mapCollisionTimer == 0) {
                        _mapCollisionTimer = System.currentTimeMillis();
                    }
                    if (System.currentTimeMillis() - _mapCollisionTimer > _walkThroughWallTimer) {
                        _canWalkThroughWall = true;
                        _mapCollisionTimer = System.currentTimeMillis();
                    }
                }

                if (_canWalkThroughWall && (System.currentTimeMillis() - _mapCollisionTimer > _walkThroughWallTimer)) {
                    _canWalkThroughWall = false;
                    _mapCollisionTimer = 0;
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

    private Direction getCollisionDirection(Direction travelDirection) {

        Direction blockedDirection = Direction.NONE;

        switch (travelDirection) {
            case HORIZONTAL:
                if (velocityX < 0) {
                    blockedDirection = Direction.WEST;
                }
                else if (velocityX > 0) {
                    blockedDirection = Direction.EAST;
                }
                break;
            case VERTICAL:
                if (velocityY < 0) {
                    blockedDirection = Direction.NORTH;
                }
                else if (velocityY > 0) {
                    blockedDirection = Direction.SOUTH;
                }
                break;
        }

        return blockedDirection;
    }
}
