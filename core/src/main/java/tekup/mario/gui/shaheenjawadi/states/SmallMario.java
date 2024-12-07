package tekup.mario.gui.shaheenjawadi.states;

class SmallMario implements MarioState {
    @Override
    public void jump() {
        System.out.println("Mario saute mais atteint moins haut.");
    }

    @Override
    public void run() {
        System.out.println("Mario court mais est plus lent.");
    }

    @Override
    public void takeDamage(MarioContext context) {
        System.out.println("Mario perd une vie !");
        // Reset state or handle game over
    }
}
