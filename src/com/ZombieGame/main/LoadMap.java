package com.ZombieGame.main;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URI;
import java.nio.file.Path;
import java.util.LinkedList;

public class LoadMap {

    private BufferedImage _currentMap;
    private BufferedImageLoader _bufferedImageLoader;
    private Handler _handler;
    private Node[][] _mapGrid;
    private LinkedList<BufferedImage> _maps;

    public LoadMap(BufferedImageLoader bufferedImageLoader, Handler handler) {
        this._handler = handler;
        this._bufferedImageLoader = bufferedImageLoader;

        _maps = new LinkedList<BufferedImage>();
        getAllMaps();
    }

    public LoadMap(BufferedImageLoader bufferedImageLoader, Handler handler, String mapPath) {
        this._handler = handler;
        this._bufferedImageLoader = bufferedImageLoader;
        _maps = new LinkedList<BufferedImage>();

        _currentMap = _bufferedImageLoader.loadImageRelativePath(mapPath);
        loadMap(_currentMap);
        getAllMaps();
    }

    public void loadNewMap(String mapPath) {
        _currentMap = _bufferedImageLoader.loadImageRelativePath(mapPath);
        loadMap(_currentMap);
    }

    public void loadNewMap(BufferedImage map) {
        loadMap(map);
    }

    public void loadNewMap(int mapIndex) {
        loadMap(_maps.get(mapIndex));
    }

    public LinkedList<BufferedImage> getMaps() {
        return _maps;
    }

    private void getAllMaps() {
        URI uri = null;
        try {
            uri = getClass().getResource("/").toURI();
        } catch (Exception e) {
            e.printStackTrace();
        }

        File directory = new File(uri);

        for (File file : directory.listFiles()) {
            String fileName = file.getName();

            if (fileName.toLowerCase().contains("map")) {
                BufferedImage image = _bufferedImageLoader.loadImage(file);
                _maps.add(image);
            }
        }
    }

    private void loadMap(BufferedImage map) {
        int width = map.getWidth();
        int height = map.getHeight();

        Color mapBlockBlack = new Color(0,0,0);
        Color player1Blue = new Color(0,0,255);
        Color player2Green = new Color(0,255,0);
        Color spawnPointRed = new Color(255, 0, 0);

        this._mapGrid = new Node[width][height];

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                int pixel = map.getRGB(i, j);
                Color pixelColorRgb = new Color(pixel);

                boolean isMapBlock = false;

                if (pixelColorRgb.equals(mapBlockBlack)) {
                    _handler.addGameObject(new MapBlocks(i * 32, j * 32, ObjectID.MAPBLOCK));
                    isMapBlock = true;
                }
                else if (pixelColorRgb.equals(player1Blue)) {
                    _handler.addGameObjectDirect(new Player(i*32, j*32, ObjectID.PLAYER1, _handler));
                }
                else if (pixelColorRgb.equals(spawnPointRed)) {
                    _handler.addSpawnPoint(new SpawnPoint(i*32, j*32));
                }

                _mapGrid[i][j] = new Node(i, j, null, isMapBlock);
            }
        }
    }

}
