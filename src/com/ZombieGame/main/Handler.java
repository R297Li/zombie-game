package com.ZombieGame.main;

import java.awt.*;
import java.util.HashMap;
import java.util.LinkedList;


/**
 * Class to handle all game objects
 */
public class Handler {

    public HashMap<ObjectID, LinkedList<GameObject>> idForObjectList;

    public LinkedList<GameObject> enemyObjects;
    public LinkedList<GameObject> playerObjects;
    public LinkedList<GameObject> bulletObjects;
    public LinkedList<GameObject> mapObjects;
    public LinkedList<SpawnPoint> spawnPoints;

    private LinkedList<GameObject> _removeObjects;
    private LinkedList<GameObject> _addObjects;

    private Game _game;

    public Handler(Game game) {
        this._game = game;

        this.idForObjectList = new HashMap<ObjectID, LinkedList<GameObject>>();
        this.spawnPoints = new LinkedList<SpawnPoint>();

        this._removeObjects = new LinkedList<GameObject>();
        this._addObjects = new LinkedList<GameObject>();

        for (ObjectID objectId : ObjectID.values()) {
            if (objectId == ObjectID.PLAYER2) {
                continue;
            }

            idForObjectList.put(objectId, new LinkedList<GameObject>());

            switch (objectId) {
                case PLAYER1:
                    playerObjects = idForObjectList.get(objectId);
                    break;
                case ENEMY:
                    enemyObjects = idForObjectList.get(objectId);
                    break;
                case MAPBLOCK:
                    mapObjects = idForObjectList.get(objectId);
                    break;
                case BULLET:
                    bulletObjects = idForObjectList.get(objectId);
                    break;
            }
        }
    }

    public void tick() {
        for (GameObject gameObject : _addObjects) {
            ObjectID id = gameObject.getID();
            LinkedList<GameObject> gameObjects = idForObjectList.get(id);
            gameObjects.add(gameObject);
        }
        for (GameObject gameObject : _removeObjects) {
            ObjectID id = gameObject.getID();
            LinkedList<GameObject> gameObjects = idForObjectList.get(id);
            gameObjects.remove(gameObject);
        }
        _addObjects.clear();
        _removeObjects.clear();

        for(GameObject gameObject : playerObjects) {
            if (gameObject.getObjectState() == ObjectState.ACTIVE) {
                gameObject.tick();
            }
            else {
                this.removeGameObject(gameObject);
            }
        }
        for(GameObject gameObject : enemyObjects) {
            if (gameObject.getObjectState() == ObjectState.ACTIVE) {
                gameObject.tick();
            }
            else {
                this.removeGameObject(gameObject);
            }
        }
        for(GameObject gameObject : bulletObjects) {
            if (gameObject.getObjectState() == ObjectState.ACTIVE) {
                gameObject.tick();
            }
            else {
                this.removeGameObject(gameObject);
            }
        }
        for(GameObject gameObject : mapObjects) {
            if (gameObject.getObjectState() == ObjectState.ACTIVE) {
                gameObject.tick();
            }
            else {
                this.removeGameObject(gameObject);
            }
        }
        if (isGameLost()) {
            _game.setGameState(GameState.LOST);
        }
    }

    public void render(Graphics graphics) {
        for(GameObject gameObject : playerObjects) {
            if (gameObject.getObjectState() == ObjectState.ACTIVE) {
                gameObject.render(graphics);
            }
        }
        for(GameObject gameObject : enemyObjects) {
            if (gameObject.getObjectState() == ObjectState.ACTIVE) {
                gameObject.render(graphics);
            }
        }
        for(GameObject gameObject : bulletObjects) {
            if (gameObject.getObjectState() == ObjectState.ACTIVE) {
                gameObject.render(graphics);
            }
        }
        for(GameObject gameObject : mapObjects) {
            if (gameObject.getObjectState() == ObjectState.ACTIVE) {
                gameObject.render(graphics);
            }
        }
    }

    public void addGameObject(GameObject gameObject) {
        _addObjects.add(gameObject);
    }

    public void addGameObjectDirect(GameObject gameObject) {
        ObjectID id = gameObject.getID();
        LinkedList<GameObject> gameObjects = idForObjectList.get(id);
        gameObjects.add(gameObject);
    }

    public void removeGameObject(GameObject gameObject) {
        boolean containsObject = _removeObjects.contains(gameObject);
        if (!containsObject) {
            _removeObjects.add(gameObject);
        }
    }

    public void clearAllObjects() {
        playerObjects.clear();
        enemyObjects.clear();
        bulletObjects.clear();
        mapObjects.clear();
        spawnPoints.clear();
        _addObjects.clear();
        _removeObjects.clear();
    }

    public void addSpawnPoint(SpawnPoint spawnPoint) {
        spawnPoints.add(spawnPoint);
    }

    public void removeSpawnPoint(SpawnPoint spawnPoint) {
        boolean containsObject = spawnPoints.contains(spawnPoint);
        if (containsObject) {
            spawnPoints.remove(spawnPoint);
        }
    }

    public boolean isGameLost() {
        boolean gameOver = true;
        for (GameObject gameObject : playerObjects) {
            if (gameObject.getObjectState() == ObjectState.ACTIVE) {
                gameOver = false;
            }
        }

        return gameOver;
    }

}
