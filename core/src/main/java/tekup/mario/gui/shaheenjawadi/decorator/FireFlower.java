package tekup.mario.gui.shaheenjawadi.decorator;

import tekup.mario.gui.shaheenjawadi.states.MarioContext;
import tekup.mario.gui.shaheenjawadi.states.MarioState;

class FireFlower extends MarioPowerUpDecorator {
    public FireFlower(MarioState mario) {
        super(mario);
    }

    public void shootFireball() {
        System.out.println("Mario lance une boule de feu !");
    }
}
