package com.ZombieGame.main;

import java.awt.event.KeyEvent;
import java.security.Key;

public class PlayerControls {

    public int up;
    public int down;
    public int left;
    public int right;
    public int shoot;
    public int pause = KeyEvent.VK_ESCAPE;

    private boolean _isMovingUp;
    private boolean _isMovingDown;
    private boolean _isMovingLeft;
    private boolean _isMovingRight;

    public PlayerControls(ObjectID objectId) {
        switch(objectId) {
            case PLAYER1:
                up = KeyEvent.VK_W;
                down = KeyEvent.VK_S;
                left = KeyEvent.VK_A;
                right = KeyEvent.VK_D;
                shoot = KeyEvent.VK_SPACE;
                break;
            case PLAYER2:
                up = KeyEvent.VK_UP;
                down = KeyEvent.VK_DOWN;
                left = KeyEvent.VK_LEFT;
                right = KeyEvent.VK_RIGHT;
                shoot = KeyEvent.VK_ENTER;
                break;
        }

        _isMovingUp = false;
        _isMovingDown = false;
        _isMovingLeft = false;
        _isMovingRight = false;
    }

    public void setIsMovingUp(boolean moving) {
        this._isMovingUp = moving;
    }

    public void setIsMovingDown(boolean moving) {
        this._isMovingDown = moving;
    }

    public void setIsMovingLeft(boolean moving) {
        this._isMovingLeft = moving;
    }

    public void setIsMovingRight(boolean moving) {
        this._isMovingRight = moving;
    }

    public boolean getIsMovingUp() {
        return _isMovingUp;
    }

    public boolean getIsMovingDown() {
        return _isMovingDown;
    }

    public boolean getIsMovingLeft() {
        return _isMovingLeft;
    }

    public boolean getIsMovingRight() {
        return _isMovingRight;
    }
}
