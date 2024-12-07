package tekup.mario.gui.shaheenjawadi.decorator;

import tekup.mario.gui.shaheenjawadi.states.MarioContext;

class FireFlower extends MarioPowerUpDecorator {
    public FireFlower(MarioContext mario) {
        super(mario);
    }

    public void shootFireball() {
        System.out.println("Mario lance une boule de feu !");
    }
}
