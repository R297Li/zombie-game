package com.ZombieGame.main;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

public class Game extends Canvas implements Runnable {

    private static final long serialVersionUID = 3817611506369188777L;

    public static final int WINDOW_WIDTH = 1680;
    public static final int WINDOW_HEIGHT = (WINDOW_WIDTH / 16) * 9;  // 787.5

    public static final int GAME_WIDTH = WINDOW_WIDTH;
    public static final int GAME_HEIGHT = (WINDOW_HEIGHT / 6) * 5;  // 787.5

    public static final String TITLE = "Zombie Game";

    private Thread _thread;
    private boolean _isThreadRunning = false;
    private Handler _handler;
    private HUD _hud;
    private Spawner _spawner;
    private GameState _gameState = GameState.START;
    private Window _window;
    private BufferedImage _currentMap;
    private BufferedImageLoader _bufferedImageLoader;
    private Camera _camera;
    private LoadMap _loadMap;
    private StartScreen _startScreen;
    private EndScreen _endScreen;

    public Game() {
        _handler = new Handler(this);
        _bufferedImageLoader = new BufferedImageLoader();
        _loadMap = new LoadMap(_bufferedImageLoader, _handler);
        _window = new Window(WINDOW_WIDTH, WINDOW_HEIGHT, TITLE, this);

        _startScreen = new StartScreen(_window, _bufferedImageLoader, _loadMap, this);
        _endScreen = new EndScreen(_window, this);

        this.start();

        _camera = new Camera();
        _hud = new HUD();
        _spawner = new Spawner();

        this.addKeyListener(new KeyInput(_handler, this, _camera));
        this.addMouseListener(_startScreen);
        this.addMouseListener(_endScreen);
        this.requestFocusInWindow();
    }

    public static void main(String[] args) {
        new Game();
    }

    // Method to create thread by executing run method for this class
    public synchronized void start() {
        _thread = new Thread(this, "Game Thread");
        _thread.start();
        _isThreadRunning = true;
    }

    public synchronized void stop() {
        try {
            _thread.join();
            _isThreadRunning = false;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void run() {
        long prevTime = System.nanoTime();
        double ticksPerSecond = 60.0;
        double nanoSecPerTick = 1000000000 / ticksPerSecond;
        double delta = 0;
        long timer = System.currentTimeMillis();
        int fps = 0;

        while (_isThreadRunning) {
            long currentTime = System.nanoTime();
            delta += (currentTime - prevTime) / nanoSecPerTick; // Returns a unit of ticks
            prevTime = currentTime;

            while (delta >= 1) {
                tick();
                delta--;
            }

            if (_isThreadRunning) {
                render();
            }

            fps++;

            // Print FPS every second and update timer
            if (System.currentTimeMillis() - timer > 1000) {
                timer += 1000;
                System.out.println("FPS:" + Integer.toString(fps));
                fps = 0;
            }
        }
        stop();
    }

    public static int clamp(int position, float minPosition, float maxPosition) {
        float value = clamp((float)position, minPosition, maxPosition);
        return (int)value;
    }

    public static float clamp(float position, float minPosition, float maxPosition) {
        if (position <= minPosition) {
            position = minPosition;
        }
        else if (position >= maxPosition) {
            position = maxPosition;
        }

        return position;
    }

    public GameState getGameState() {
        return _gameState;
    }

    public void setGameState(GameState newState) {
        this._gameState = newState;
    }

    public void startGame(BufferedImage map) {
        _loadMap.loadNewMap(map);
        _camera.initialize(0,0, _handler);
        _hud.initialize(_handler, _camera, this);
        _spawner.initialize(_handler, _hud, this);

        setGameState(GameState.PLAY);
    }

    public void endGameCleanUp() {
        _handler.clearAllObjects();
        _startScreen.reset();
    }

    public void startGameCleanUp() {
        _endScreen.reset();
    }

    private void tick() {
        switch(_gameState) {
            case START:
                _startScreen.tick();
                break;
            case PLAY:
                _handler.tick();
                _spawner.tick();
                _camera.tick();
                _hud.tick();
                break;
            case PAUSE:
                break;
            case LEVELTRANSITION:
                _handler.tick();
                _spawner.tick();
                _camera.tick();
                _hud.tick();
                break;
            case WON:
                _endScreen.tick();
                break;
            case LOST:
                _endScreen.tick();
                break;
        }
    }

    private void render() {
        BufferStrategy bufferStrat = this.getBufferStrategy();
        int numBuffers = 3;

        // Create buffer strategy if none existing
        if (bufferStrat == null) {
            this.createBufferStrategy(numBuffers);
            return;
        }

        // Repeat loop if contents are lost
        do {
            Graphics graphics = bufferStrat.getDrawGraphics();
            Graphics2D graphics2D = (Graphics2D)graphics;

            switch(_gameState) {
                case START:
                    graphics.setColor(new Color(245, 245, 220));
                    graphics.fillRect(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);
                    _startScreen.render(graphics);
                    break;
                case PLAY:
                    graphics.setColor(Color.WHITE);
                    graphics.fillRect(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);
                    // Render all game objects onto window
                    graphics2D.translate(-_camera.getX(), -_camera.getY());
                    _handler.render(graphics);
                    graphics2D.translate(_camera.getX(), _camera.getY());
                    _hud.render(graphics);
                    break;
                case PAUSE:
                    graphics.setColor(Color.WHITE);
                    graphics.fillRect(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);
                    break;
                case LEVELTRANSITION:
                    graphics.setColor(Color.WHITE);
                    graphics.fillRect(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);
                    // Render all game objects onto window
                    graphics2D.translate(-_camera.getX(), -_camera.getY());
                    _handler.render(graphics);
                    graphics2D.translate(_camera.getX(), _camera.getY());
                    _hud.render(graphics);
                    break;
                case WON:
                    graphics.setColor(Color.WHITE);
                    graphics.fillRect(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);
                    _endScreen.render(graphics);
                    break;
                case LOST:
                    graphics.setColor(Color.WHITE);
                    graphics.fillRect(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);
                    _endScreen.render(graphics);
                    break;
            }

            // Release system held resources and display rendered image to screen
            graphics.dispose();
            bufferStrat.show();
        } while (bufferStrat.contentsLost());
    }
}
