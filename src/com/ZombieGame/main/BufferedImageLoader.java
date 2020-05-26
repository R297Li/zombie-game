package com.ZombieGame.main;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.nio.Buffer;
import java.nio.file.Path;
import java.nio.file.Paths;

public class BufferedImageLoader {

    private BufferedImage _image;

    public BufferedImage loadImageRelativePath(String relativePath) {
        URL path = getClass().getResource(relativePath);
        BufferedImage image = loadImage(path);

        return image;
    }

    public BufferedImage loadImageAbsolutePath(String absolutePath) {
        File file = new File(absolutePath);
        BufferedImage image = loadImage(file);

        return image;
    }

    public BufferedImage loadImage(URL path) {
        try {
            _image = ImageIO.read(path);
        } catch (IOException e) {
        }

        return _image;
    }

    public BufferedImage loadImage(File file) {
        try {
            _image = ImageIO.read(file);
        } catch (IOException e) {
        }

        return _image;
    }
}
