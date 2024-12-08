package tekup.mario.gui.shaheenjawadi.decorator;

import tekup.mario.gui.shaheenjawadi.states.InvincibleMario;
import tekup.mario.gui.shaheenjawadi.states.MarioContext;
import tekup.mario.gui.shaheenjawadi.states.MarioState;

public class Star extends MarioPowerUpDecorator {
    public Star( ) {
        super(new InvincibleMario());
    }

    @Override
    public void run() {
        System.out.println("Mario court avec invincibilité !");
    }
}
