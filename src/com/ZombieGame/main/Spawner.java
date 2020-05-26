package com.ZombieGame.main;

import java.util.LinkedList;
import java.util.Random;

public class Spawner {

    private Handler _handler;
    private HUD _hud;
    private Random _random;
    private Game _game;

    // Used to ensure enemy id remains unique
    private int _enemyIdMultiplier = 10000;

    private int _timer = 10000;
    private long _previousTime;


    public Spawner() {}

    public void initialize(Handler handler, HUD hud, Game game) {
        this._handler = handler;
        this._hud = hud;
        this._random = new Random();
        this._game = game;

        this._previousTime = System.currentTimeMillis();
    }

    public void tick() {
        if (_handler.enemyObjects.size() == 0) {

            _hud.setIsLevelComplete(true);
            _game.setGameState(GameState.LEVELTRANSITION);

            if (_hud.getIsLevelComplete()) {
                _hud.setIsLevelComplete(false);
                int nextLevel = _hud.getLevel() + 1;
                _hud.setLevel(nextLevel);

                int numEnemyToSpawn = 10 * nextLevel;
                LinkedList<SpawnPoint> spawnPoints = _handler.spawnPoints;
                int numSpawnPoints = spawnPoints.size();

                for (int i = 0; i < numEnemyToSpawn; i++) {
                    int randomNum = _random.nextInt(numSpawnPoints);
                    SpawnPoint spawnPoint = spawnPoints.get(randomNum);

                    Enemy enemy = new Enemy(spawnPoint.x, spawnPoint.y, ObjectID.ENEMY, _handler, _game);

                    int newId = (nextLevel * _enemyIdMultiplier) + i;
                    enemy.setId(newId);

                    _handler.addGameObject(enemy);
                }

                this._previousTime = System.currentTimeMillis();
            }
        }

        if (_game.getGameState() == GameState.LEVELTRANSITION) {
            startNextLevel();
        }
    }

    private void startNextLevel() {
        if (System.currentTimeMillis() - _previousTime > _timer) {
            _game.setGameState(GameState.PLAY);
        }
    }


}
