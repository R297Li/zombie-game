package com.ZombieGame.main;

import java.awt.*;

public class MapBlocks extends GameObject {

    public MapBlocks(int positionX, int positionY, ObjectID objectId) {
        super(positionX, positionY, objectId);
    }

    @Override
    public void tick() {

    }

    @Override
    public void render(Graphics graphics) {
        graphics.setColor(Color.GREEN);
        graphics.fillRect((int)positionX,(int)positionY,32,32);
    }

    @Override
    public Rectangle getBounds() {
        Rectangle bounds = new Rectangle((int)positionX, (int)positionY, 32, 32);

        return bounds;
    }
}
