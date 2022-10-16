package com.object.부록_B.B_interface;

import java.awt.*;

public class Player implements Collidable{
    @Override
    public boolean collideWith(Collidable other) {
        return false;
    }

    @Override
    public Point getPosition() {
        return null;
    }

    @Override
    public void update(Graphics graphics) {

    }

    @Override
    public String getName() {
        return null;
    }
}
