package tekup.mario.gui.shaheenjawadi.states;

class InvincibleMario implements MarioState {
    @Override
    public void jump() {
        System.out.println("Mario saute avec invincibilité !");
    }

    @Override
    public void run() {
        System.out.println("Mario court sans peur !");
    }

    @Override
    public void takeDamage(MarioContext context) {
        System.out.println("Mario est invincible, aucun dégât !");
    }
}
