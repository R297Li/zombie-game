package com.ZombieGame.main;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class EndScreen extends MouseAdapter {

    private enum ScreenState {
        ENDMENU,
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

    private String _mainMenu = "MAIN MENU";
    private Button _mainMenuButton;
    private Font _mainMenuButtonFont;

    private String _quit = "QUIT";
    private Button _quitButton;
    private Font _quitButtonFont;

    private Container _baseContainer;
    private Game _game;

    private ScreenState _screenState = ScreenState.ENDMENU;

    public EndScreen(Window window, Game game) {
        this._titleFont = new Font("Arial", Font.BOLD, 80);
        this._buttonFontUnselected = new Font("Arial", Font.BOLD, 40);
        this._buttonFontSelected = new Font("Arial", Font.ITALIC, 40);
        this._quitButton = null;
        this._mainMenuButton = null;
        this._mainMenuButtonFont = _buttonFontUnselected;
        this._quitButtonFont = _buttonFontUnselected;

        this._baseContainer = window.getBaseContainer();
        this._game = game;
    }

    public void reset() {
        this._titleFont = new Font("Arial", Font.BOLD, 80);
        this._buttonFontUnselected = new Font("Arial", Font.BOLD, 40);
        this._buttonFontSelected = new Font("Arial", Font.ITALIC, 40);
        this._quitButton = null;
        this._mainMenuButton = null;
        this._mainMenuButtonFont = _buttonFontUnselected;
        this._quitButtonFont = _buttonFontUnselected;

        _screenState = ScreenState.ENDMENU;
    }

    public void mousePressed(MouseEvent e) {
        int mouseX = e.getX();
        int mouseY = e.getY();

        switch (_screenState) {
            case ENDMENU: {
                if (_mainMenuButton != null) {
                    boolean mouseOver = isMouseOver(_mainMenuButton.button, mouseX, mouseY);

                    if (mouseOver) {
                        _game.endGameCleanUp();
                        _game.setGameState(GameState.START);
                        _screenState = ScreenState.NONE;
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
        }

    }

    public void tick() {
        switch (_screenState) {
            case ENDMENU:
                endMenuScreen();
                break;
            case QUIT:
                System.exit(0);
                break;
        }
    }


    private void endMenuScreen() {
        Point mousePosition = _baseContainer.getMousePosition();
        int mouseX = 0;
        int mouseY = 0;

        if (mousePosition != null) {
            mouseX = (int)mousePosition.getX();
            mouseY = (int)mousePosition.getY();
        }

        if (_mainMenuButton != null) {
            boolean mouseOver = isMouseOver(_mainMenuButton.button, mouseX, mouseY);
            _mainMenuButtonFont = (mouseOver) ? _buttonFontSelected : _buttonFontUnselected;
        }

        if (_quitButton != null) {
            boolean mouseOver = isMouseOver(_quitButton.button, mouseX, mouseY);
            _quitButtonFont = (mouseOver) ? _buttonFontSelected : _buttonFontUnselected;
        }
    }

    public void render(Graphics graphics) {

        if (_game.getGameState() == GameState.WON) {
            setTitle("YOU WIN");
        }
        else if (_game.getGameState() == GameState.LOST) {
            setTitle("YOU LOSE");
        }

        switch (_screenState) {
            case ENDMENU:
                renderTitle(graphics);
                renderMainMenuButton(graphics, _mainMenuButtonFont);
                renderQuitButton(graphics, _quitButtonFont);
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

    private void renderMainMenuButton(Graphics graphics, Font buttonFont) {
        FontMetrics fontMetrics = graphics.getFontMetrics(buttonFont);
        int buttonLength = fontMetrics.stringWidth(_mainMenu);
        int buttonHeight = fontMetrics.getHeight();
        int x = (Game.WINDOW_WIDTH / 2) - (buttonLength / 2);
        int y = Game.WINDOW_HEIGHT / 2;

        _mainMenuButton = new Button(_mainMenu, buttonFont, x, y, buttonHeight, buttonLength);
        renderButton(graphics, _mainMenuButton);
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
