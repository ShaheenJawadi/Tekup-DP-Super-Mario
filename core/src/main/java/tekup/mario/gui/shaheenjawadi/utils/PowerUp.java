package tekup.mario.gui.shaheenjawadi.utils;

import com.badlogic.gdx.math.Rectangle;

public class PowerUp {
    Rectangle bounds;
    String type;

    public Rectangle getBounds() {
        return bounds;
    }

    public void setBounds(Rectangle bounds) {
        this.bounds = bounds;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public PowerUp(Rectangle bounds, String type) {
        this.bounds = bounds;
        this.type = type;
    }
}
