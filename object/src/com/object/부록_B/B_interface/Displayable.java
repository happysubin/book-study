package com.object.부록_B.B_interface;

import java.awt.*;

public interface Displayable extends GameObject{
    Point getPosition();
    void update(Graphics graphics);
}
