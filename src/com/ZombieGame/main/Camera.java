package com.ZombieGame.main;

public class Camera {

    private float _x;
    private float _y;
    private float _multiplier = 0.05f;

    private Handler _handler;
    private GameObject _player;

    public Camera() {}

    public Camera(float x, float y, Handler handler) {
        this._x = x;
        this._y = y;
        this._handler = handler;

        for (GameObject gameObject : _handler.playerObjects) {
            if (gameObject.getID() == ObjectID.PLAYER1) {
                _player = gameObject;
            }
        }
    }

    public void initialize(float x, float y, Handler handler) {
        this._x = x;
        this._y = y;
        this._handler = handler;

        for (GameObject gameObject : _handler.playerObjects) {
            if (gameObject.getID() == ObjectID.PLAYER1) {
                _player = gameObject;
            }
        }
    }

    public void tick(GameObject gameObject) {
        _x += ((gameObject.getPositionX() - _x) - Game.GAME_WIDTH / 2) * _multiplier;
        _y += ((gameObject.getPositionY() - _y) - Game.GAME_HEIGHT / 2) * _multiplier;

    }

    public void tick() {
        _x += ((_player.getPositionX() - _x) - Game.GAME_WIDTH / 2) * _multiplier;
        _y += ((_player.getPositionY() - _y) - Game.GAME_HEIGHT / 2) * _multiplier;

        // TODO: To make it easier to clamp, make the map such that:
        //         Width: (1680 / 32) * 2 = 105 pixels wide
        //         Height: (787.5 / 32) * 2 = 50 pixels tall
        //         32 is the width and height of the map blocks

        _x = Game.clamp(_x, 32, Game.GAME_WIDTH / 4 - Player.PLAYER_WIDTH);
        _y = Game.clamp(_y, 32, Game.GAME_HEIGHT + HUD.HUD_HEIGHT * 3);

    }

    public void setX(float x) {
        this._x = x;
    }

    public void setY(float y) {
        this._y = y;
    }

    public float getX() {
        return _x;
    }

    public float getY() {
        return _y;
    }

}
