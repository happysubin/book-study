package com.object.book.부록_B.B_interface;

import java.awt.*;

public class Monster implements Collidable{
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
