package com.ZombieGame.main;

import javafx.application.Application;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.nio.Buffer;
import java.util.LinkedList;

public class StartScreen extends MouseAdapter {

    private enum ScreenState {
        MENU,
        MAPSELECTION,
        QUIT,
        NONE
    }

    private class Button {
        public String text;
        public Font font;
        public int x;
        public int y;
        public int height;
        public int width;
        public Rectangle button;

        public Button(String text, Font font, int x, int y, int height, int width) {
            this.text = text;
            this.font = font;
            this.x = x;
            this.y = y;
            this.height = height;
            this.width = width;

            // For rectangle, height starts at bottom of text. So need to subtract it to shift starting point to top of text
            this.button = new Rectangle(x, y - height, width, height);
        }
    }

    private Font _titleFont;
    private Font _buttonFontUnselected;
    private Font _buttonFontSelected;

    private String _title = "BoxHead";

    private String _play = "PLAY";
    private Button _playButton;
    private Font _playButtonFont;

    private String _quit = "QUIT";
    private Button _quitButton;
    private Font _quitButtonFont;

    private String _next = "NEXT >";
    private Button _nextButton;
    private Font _nextButtonFont;

    private String _previous = "< PREV";
    private Button _previousButton;
    private Font _previousButtonFont;

    private String _start = "START";
    private Button _startButton;
    private Font _startButtonFont;

    private Container _baseContainer;
    private BufferedImageLoader _bufferedImageLoader;
    private LoadMap _loadMap;
    private Game _game;

    private LinkedList<BufferedImage> _maps;
    private int _mapsIndex;

    private ScreenState _screenState = ScreenState.MENU;

    public StartScreen(Window window, BufferedImageLoader bufferedImageLoader, LoadMap loadMap, Game game) {
        this._titleFont = new Font("Arial", Font.BOLD, 80);
        this._buttonFontUnselected = new Font("Arial", Font.BOLD, 40);
        this._buttonFontSelected = new Font("Arial", Font.ITALIC, 40);
        this._quitButton = null;
        this._playButton = null;
        this._nextButton = null;
        this._previousButton = null;
        this._startButton = null;
        this._playButtonFont = _buttonFontUnselected;
        this._quitButtonFont = _buttonFontUnselected;
        this._nextButtonFont = _buttonFontUnselected;
        this._previousButtonFont = _buttonFontUnselected;
        this._startButtonFont = _buttonFontUnselected;

        this._baseContainer = window.getBaseContainer();
        this._bufferedImageLoader = bufferedImageLoader;
        this._loadMap = loadMap;
        this._game = game;

        this._maps = _loadMap.getMaps();
        this._mapsIndex = 0;
    }

    public void reset() {
        this._mapsIndex = 0;
        this._screenState = ScreenState.MENU;

        this._titleFont = new Font("Arial", Font.BOLD, 80);
        this._buttonFontUnselected = new Font("Arial", Font.BOLD, 40);
        this._buttonFontSelected = new Font("Arial", Font.ITALIC, 40);
        this._quitButton = null;
        this._playButton = null;
        this._nextButton = null;
        this._previousButton = null;
        this._startButton = null;
        this._playButtonFont = _buttonFontUnselected;
        this._quitButtonFont = _buttonFontUnselected;
        this._nextButtonFont = _buttonFontUnselected;
        this._previousButtonFont = _buttonFontUnselected;
        this._startButtonFont = _buttonFontUnselected;
    }

    public void mousePressed(MouseEvent e) {
        int mouseX = e.getX();
        int mouseY = e.getY();

        switch (_screenState) {
            case MENU: {
                if (_playButton != null) {
                    boolean mouseOver = isMouseOver(_playButton.button, mouseX, mouseY);

                    System.out.println(mouseOver);

                    if (mouseOver) {
                        _screenState = ScreenState.MAPSELECTION;
                    }
                }

                if (_quitButton != null) {
                    boolean mouseOver = isMouseOver(_quitButton.button, mouseX, mouseY);

                    if (mouseOver) {
                        _screenState = ScreenState.QUIT;
                    }
                }

                break;
            }

            case MAPSELECTION: {
                if (_previousButton != null) {
                    boolean mouseOver = isMouseOver(_previousButton.button, mouseX, mouseY);

                    if (mouseOver) {
                        _mapsIndex--;
                        _mapsIndex = Game.clamp(_mapsIndex, 0, _maps.size() - 1);
                    }
                }

                if (_nextButton != null) {
                    boolean mouseOver = isMouseOver(_nextButton.button, mouseX, mouseY);

                    if (mouseOver) {
                        _mapsIndex++;
                        _mapsIndex = Game.clamp(_mapsIndex, 0, _maps.size() - 1);
                    }
                }

                if (_startButton != null) {
                    boolean mouseOver = isMouseOver(_startButton.button, mouseX, mouseY);

                    if (mouseOver) {
                        BufferedImage map = _maps.get(_mapsIndex);
                        _game.startGame(map);
                        _screenState = ScreenState.NONE;
                        _game.startGameCleanUp();
                    }
                }

                break;
            }
        }

    }

    public void tick() {
        switch (_screenState) {
            case MENU:
                menuScreen();
                break;

            case MAPSELECTION:
                mapSelectionScreen();
                break;

            case QUIT:
                System.exit(0);
                break;
        }
    }


    private void menuScreen() {
        Point mousePosition = _baseContainer.getMousePosition();
        int mouseX = 0;
        int mouseY = 0;

        if (mousePosition != null) {
            mouseX = (int)mousePosition.getX();
            mouseY = (int)mousePosition.getY();
        }

        if (_playButton != null) {
            boolean mouseOver = isMouseOver(_playButton.button, mouseX, mouseY);
            _playButtonFont = (mouseOver) ? _buttonFontSelected : _buttonFontUnselected;
        }

        if (_quitButton != null) {
            boolean mouseOver = isMouseOver(_quitButton.button, mouseX, mouseY);
            _quitButtonFont = (mouseOver) ? _buttonFontSelected : _buttonFontUnselected;
        }
    }

    private void mapSelectionScreen() {
        Point mousePosition = _baseContainer.getMousePosition();
        int mouseX = 0;
        int mouseY = 0;

        if (mousePosition != null) {
            mouseX = (int)mousePosition.getX();
            mouseY = (int)mousePosition.getY();
        }

        if (_nextButton != null) {
            boolean mouseOver = isMouseOver(_nextButton.button, mouseX, mouseY);
            _nextButtonFont = (mouseOver) ? _buttonFontSelected : _buttonFontUnselected;
        }
        if (_previousButton != null) {
            boolean mouseOver = isMouseOver(_previousButton.button, mouseX, mouseY);
            _previousButtonFont = (mouseOver) ? _buttonFontSelected : _buttonFontUnselected;
        }
        if (_startButton != null) {
            boolean mouseOver = isMouseOver(_startButton.button, mouseX, mouseY);
            _startButtonFont = (mouseOver) ? _buttonFontSelected : _buttonFontUnselected;
        }
    }

    public void render(Graphics graphics) {
        switch (_screenState) {
            case MENU:
                setTitle("BoxHead");
                renderTitle(graphics);
                renderPlayButton(graphics, _playButtonFont);
                renderQuitButton(graphics, _quitButtonFont);
                break;
            case MAPSELECTION:
                setTitle("MAP SELECTION");
                renderTitle(graphics);
                renderMap(graphics);
                renderNextButton(graphics, _nextButtonFont);
                renderPreviousButton(graphics, _previousButtonFont);
                renderStartButton(graphics, _startButtonFont);
                break;
        }
    }

    private void setTitle(String title) {
        this._title = title;
    }

    private void renderTitle(Graphics graphics) {
        FontMetrics fontMetrics = graphics.getFontMetrics(_titleFont);
        int titleLength = fontMetrics.stringWidth(_title);
        graphics.setFont(_titleFont);
        graphics.setColor(Color.BLACK);
        graphics.drawString(_title, (Game.WINDOW_WIDTH /2) - (titleLength / 2), Game.WINDOW_HEIGHT / 5);
    }

    private void renderPlayButton(Graphics graphics, Font buttonFont) {
        FontMetrics fontMetrics = graphics.getFontMetrics(buttonFont);
        int buttonLength = fontMetrics.stringWidth(_play);
        int buttonHeight = fontMetrics.getHeight();
        int x = (Game.WINDOW_WIDTH / 2) - (buttonLength / 2);
        int y = Game.WINDOW_HEIGHT / 2;

        _playButton = new Button(_play, buttonFont, x, y, buttonHeight, buttonLength);
        renderButton(graphics, _playButton);
    }

    private void renderQuitButton(Graphics graphics, Font buttonFont) {
        FontMetrics fontMetrics = graphics.getFontMetrics(buttonFont);
        int buttonLength = fontMetrics.stringWidth(_quit);
        int buttonHeight = fontMetrics.getHeight();
        int x = (Game.WINDOW_WIDTH / 2) - (buttonLength / 2);
        int y = (Game.WINDOW_HEIGHT / 2) + (Game.WINDOW_HEIGHT / 10);

        _quitButton = new Button(_quit, buttonFont, x, y, buttonHeight, buttonLength);
        renderButton(graphics, _quitButton);
    }

    private void renderMap(Graphics graphics) {
        BufferedImage map = _maps.get(_mapsIndex);

        int imageWidth = 400;
        int imageHeight = 400;
        int x = (Game.WINDOW_WIDTH / 2) - (imageWidth / 2);
        int y = (Game.WINDOW_HEIGHT / 2) - (imageHeight / 2);

        graphics.drawImage(map, x, y, imageWidth, imageHeight, null);
    }

    private void renderNextButton(Graphics graphics, Font buttonFont) {
        FontMetrics fontMetrics = graphics.getFontMetrics(buttonFont);
        int buttonLength = fontMetrics.stringWidth(_next);
        int buttonHeight = fontMetrics.getHeight();
        int x = (Game.WINDOW_WIDTH / 2) + (300);
        int y = (Game.WINDOW_HEIGHT / 2) + (buttonHeight / 2);

        _nextButton = new Button(_next, buttonFont, x, y, buttonHeight, buttonLength);
        renderButton(graphics, _nextButton);
    }

    private void renderPreviousButton(Graphics graphics, Font buttonFont) {
        FontMetrics fontMetrics = graphics.getFontMetrics(buttonFont);
        int buttonLength = fontMetrics.stringWidth(_previous);
        int buttonHeight = fontMetrics.getHeight();
        int x = (Game.WINDOW_WIDTH / 2) - (300) - buttonLength;
        int y = (Game.WINDOW_HEIGHT / 2) + (buttonHeight / 2);

        _previousButton = new Button(_previous, buttonFont, x, y, buttonHeight, buttonLength);
        renderButton(graphics, _previousButton);
    }

    private void renderStartButton(Graphics graphics, Font buttonFont) {
        FontMetrics fontMetrics = graphics.getFontMetrics(buttonFont);
        int buttonLength = fontMetrics.stringWidth(_start);
        int buttonHeight = fontMetrics.getHeight();
        int x = (Game.WINDOW_WIDTH / 2) - (buttonLength / 2);
        int y = (Game.WINDOW_HEIGHT / 2) + 300;

        _startButton = new Button(_start, buttonFont, x, y, buttonHeight, buttonLength);
        renderButton(graphics, _startButton);
    }

    private void renderButton(Graphics graphics, Button button) {
        graphics.setFont(button.font);
        graphics.setColor(Color.BLACK);
        graphics.drawString(button.text, button.x, button.y);
        //graphics.drawRect(button.x, button.y - button.height, button.width, button.height);
    }

    private boolean isMouseOver(Rectangle rectangle, int x, int y) {
        if (rectangle.contains(x, y)) {
            return true;
        }
        return false;
    }
}
