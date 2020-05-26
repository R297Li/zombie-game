package com.ZombieGame.main;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.LinkedList;

public class KeyInput extends KeyAdapter {

    private Handler _handler;
    private Game _game;
    private PlayerControls _player1;
    private PlayerControls _player2;
    private Camera _camera;

    public KeyInput(Handler handler, Game game, Camera camera) {
        this._handler = handler;
        this._game = game;
        this._camera = camera;

        for (GameObject gameObject : _handler.playerObjects) {
            if (gameObject.getID() == ObjectID.PLAYER1) {
                Player player = (Player)gameObject;
                _player1 = player.getPlayerControls();
            }
            if (gameObject.getID() == ObjectID.PLAYER2) {
                Player player = (Player)gameObject;
                _player2 = player.getPlayerControls();
            }
        }
    }


    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        for (GameObject gameObject : _handler.playerObjects) {
            if (gameObject.getID() == ObjectID.PLAYER1) {
                float velocityX = gameObject.getVelocityX();
                float velocityY = gameObject.getVelocityY();

                switch(key) {
                    case KeyEvent.VK_W:
                        gameObject.setVelocityY(velocityY - 5);
                        gameObject.setDirection(Direction.NORTH);
                        break;
                    case KeyEvent.VK_S:
                        gameObject.setVelocityY(velocityY + 5);
                        gameObject.setDirection(Direction.SOUTH);
                        break;
                    case KeyEvent.VK_A:
                        gameObject.setVelocityX(velocityX - 5);
                        gameObject.setDirection(Direction.WEST);
                        break;
                    case KeyEvent.VK_D:
                        gameObject.setVelocityX(velocityX + 5);
                        gameObject.setDirection(Direction.EAST);
                        break;
                    case KeyEvent.VK_P:
                        GameState state = (_game.getGameState() == GameState.PAUSE) ? GameState.PLAY : GameState.PAUSE;
                        _game.setGameState(state);
                        break;
                }
                gameObject.setVelocityY(Game.clamp(gameObject.getVelocityY(), -5, 5));
                gameObject.setVelocityX(Game.clamp(gameObject.getVelocityX(), -5, 5));
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();

        for (GameObject gameObject : _handler.playerObjects) {
            if (gameObject.getID() == ObjectID.PLAYER1) {
                float velocityX = gameObject.getVelocityX();
                float velocityY = gameObject.getVelocityY();

                switch(key) {
                    case KeyEvent.VK_W:
                        gameObject.setVelocityY(velocityY + 5);
                        break;
                    case KeyEvent.VK_S:
                        gameObject.setVelocityY(velocityY - 5);
                        break;
                    case KeyEvent.VK_A:
                        gameObject.setVelocityX(velocityX + 5);
                        break;
                    case KeyEvent.VK_D:
                        gameObject.setVelocityX(velocityX - 5);
                        break;
                    case KeyEvent.VK_SPACE:
                        Direction currentDirection = gameObject.getDirectionFromVelocity();
                        int playerPositionX = (int)(gameObject.getPositionX() + (Player.PLAYER_WIDTH / 2));
                        int playerPositionY = (int)(gameObject.getPositionY() + (Player.PLAYER_HEIGHT / 2));
                        _handler.addGameObject(new Bullet(playerPositionX, playerPositionY, currentDirection, ObjectID.BULLET, _handler, _camera));
                        break;
                }
                gameObject.setVelocityY(Game.clamp(gameObject.getVelocityY(), -5, 5));
                gameObject.setVelocityX(Game.clamp(gameObject.getVelocityX(), -5, 5));
            }
        }
    }
}
