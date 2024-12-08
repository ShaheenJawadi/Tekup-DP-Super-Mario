package tekup.mario.gui.shaheenjawadi.states;

class NormalMario implements MarioState {
    @Override
    public void jump() {
        System.out.println("Mario saute normalement.");
    }

    @Override
    public void run() {
        System.out.println("Mario court normalement.");
    }

    @Override
    public void takeDamage(MarioContext context) {
        System.out.println("Mario devient petit !");
        context.setState(new SmallMario());
    }

    @Override
    public float getHeight() {
        return 55;
    }
}
