package com.ZombieGame.main;

import javax.swing.*;
import java.awt.*;

public class Window extends Canvas {

    private static final long serialVersionUID = -1740703595972149847L;

    private JFrame _frame;
    private Container _baseContainer;
    private StartScreen _startScreen;

    public Window(int width, int height, String title, Game game) {
        _frame = new JFrame(title);
        _frame.setFocusable(true);

        Dimension frameDimensions = new Dimension(width, height);

        // Set a constant frame size
        _frame.setPreferredSize(frameDimensions);
        _frame.setMaximumSize(frameDimensions);
        _frame.setMinimumSize(frameDimensions);
        _frame.setResizable(false);

        _frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        _frame.setVisible(true);
        _baseContainer = _frame.getContentPane();

        // Passing in null allows window to start at center of screen
        _frame.setLocationRelativeTo(null);

        // Add game to window and start
        _frame.add(game);
    }

    public Container getBaseContainer() {
        return _baseContainer;
    }
}
