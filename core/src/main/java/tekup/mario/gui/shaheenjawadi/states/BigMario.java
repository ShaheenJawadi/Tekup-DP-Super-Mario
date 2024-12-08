package tekup.mario.gui.shaheenjawadi.states;

class BigMario implements MarioState {
    @Override
    public void jump() {
        System.out.println("Mario saute plus haut.");
    }

    @Override
    public void run() {
        System.out.println("Mario court normalement.");
    }

    @Override
    public void takeDamage(MarioContext context) {
        System.out.println("Mario retourne à son état normal.");
        context.setState(new NormalMario());
    }

    @Override
    public float getHeight() {
        return 70;
    }
}
