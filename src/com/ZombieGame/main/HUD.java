package com.ZombieGame.main;

import java.awt.*;
import java.util.LinkedList;

public class HUD {
    public static int HUD_WIDTH = Game.WINDOW_WIDTH;
    public static int HUD_HEIGHT = Game.WINDOW_HEIGHT - Game.GAME_HEIGHT;

    public static int HEALTH = 100;
    private int _player1HealthLevelRgbGreen = 255;
    private int _player2HealthLevelRgbGreen = 255;
    private int _level = 0;
    private boolean _isLevelComplete = true;
    private boolean _startNextLevel = false;

    private GameObject _player1;
    private GameObject _player2;
    private Handler _handler;
    private Camera _camera;
    private Game _game;

    public HUD() {}

    public void initialize(Handler handler, Camera camera, Game game) {
        this._handler = handler;
        this._camera = camera;
        this._game = game;
        this._player1 = null;
        this._player2 = null;
        this._level = 0;
        this._player1HealthLevelRgbGreen = 255;
        this._player2HealthLevelRgbGreen = 255;
        this._isLevelComplete = true;
        this._startNextLevel = false;

        for (GameObject gameObject : _handler.playerObjects) {
            if (gameObject.getID() == ObjectID.PLAYER1) {
                _player1 = gameObject;
            }
            else if (gameObject.getID() == ObjectID.PLAYER2) {
                _player2 = gameObject;
            }
        }
    }

    public void tick() {
        if (_player1 != null) {
            updatePlayer1HealthBar();
        }
        if (_player2 != null) {
            updatePlayer2HealthBar();
        }
    }

    public void render(Graphics graphics) {
        renderHud(graphics);
        graphics.drawString("Level: " + Integer.toString(_level), (Game.WINDOW_WIDTH / 2), (Game.WINDOW_HEIGHT - HUD_HEIGHT + (HUD_HEIGHT / 5)));

        if (_player1 != null) {
            renderPlayer1HealthBar(graphics);
        }
        if (_player2 != null) {
            renderPlayer2HealthBar(graphics);
        }
    }

    public void setLevel(int level) {
        this._level = level;
    }

    public int getLevel() {
        return _level;
    }

    public void setIsLevelComplete(boolean isLevelComplete) {
        this._isLevelComplete = isLevelComplete;
    }

    public boolean getIsLevelComplete() {
        return _isLevelComplete;
    }

    public void setStartNextLevel(boolean startNextLevel) {
        this._startNextLevel = startNextLevel;
    }

    public boolean getStartNextLevel() {
        return _startNextLevel;
    }

    private void renderHud(Graphics graphics) {
        graphics.setColor(Color.GRAY);
        graphics.fillRect(0,(Game.WINDOW_HEIGHT - HUD_HEIGHT), HUD_WIDTH, HUD_HEIGHT);
        graphics.setColor(Color.BLACK);
        graphics.drawLine(0,(Game.WINDOW_HEIGHT - HUD_HEIGHT), HUD_WIDTH, (Game.WINDOW_HEIGHT - HUD_HEIGHT));
    }


    private void updatePlayer1HealthBar() {
        _player1.setHealth(Game.clamp(_player1.getHealth(), 0, 100));
        _player1HealthLevelRgbGreen = (int)(_player1.getHealth() * 3);
        _player1HealthLevelRgbGreen = Game.clamp(_player1HealthLevelRgbGreen, 0, 255);
    }

    private void updatePlayer2HealthBar() {
        _player2.setHealth(Game.clamp(_player2.getHealth(), 0, 100));
        _player2HealthLevelRgbGreen = (int)(_player2.getHealth() * 3);
        _player2HealthLevelRgbGreen = Game.clamp(_player2HealthLevelRgbGreen, 0, 255);
    }

    private void renderPlayer1HealthBar(Graphics graphics) {
        graphics.setColor(Color.LIGHT_GRAY);
        graphics.fillRect(10,(Game.WINDOW_HEIGHT - HUD_HEIGHT + (HUD_HEIGHT / 3)), 200, 16);
        graphics.setColor(new Color(200, _player1HealthLevelRgbGreen, 0));
        graphics.fillRect(10,(Game.WINDOW_HEIGHT - HUD_HEIGHT + (HUD_HEIGHT / 3)),(int)(_player1.health * 2),16);
        graphics.setColor(Color.BLACK);
        graphics.drawRect(10,(Game.WINDOW_HEIGHT - HUD_HEIGHT + (HUD_HEIGHT / 3)),200,16);
    }

    private void renderPlayer2HealthBar(Graphics graphics) {
        graphics.setColor(Color.LIGHT_GRAY);
        graphics.fillRect(10,(Game.WINDOW_HEIGHT - HUD_HEIGHT + (HUD_HEIGHT / 3)), 200, 16);
        graphics.setColor(new Color(200, _player2HealthLevelRgbGreen, 0));
        graphics.fillRect(10,(Game.WINDOW_HEIGHT - HUD_HEIGHT + (HUD_HEIGHT / 3)),(int)(_player2.health * 2),16);
        graphics.setColor(Color.BLACK);
        graphics.drawRect(10,(Game.WINDOW_HEIGHT - HUD_HEIGHT + (HUD_HEIGHT / 3)),200,16);
    }
}
