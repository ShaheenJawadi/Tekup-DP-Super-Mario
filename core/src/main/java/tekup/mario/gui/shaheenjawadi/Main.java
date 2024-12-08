package tekup.mario.gui.shaheenjawadi;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import tekup.mario.gui.shaheenjawadi.decorator.Mushroom;
import tekup.mario.gui.shaheenjawadi.decorator.Star;
import tekup.mario.gui.shaheenjawadi.states.BigMario;
import tekup.mario.gui.shaheenjawadi.states.InvincibleMario;
import tekup.mario.gui.shaheenjawadi.states.MarioContext;
import tekup.mario.gui.shaheenjawadi.utils.LogService;
import tekup.mario.gui.shaheenjawadi.utils.PowerUp;

import java.util.ArrayList;
import java.util.Iterator;

public class Main extends ApplicationAdapter {
    private ShapeRenderer shapeRenderer;
    private SpriteBatch spriteBatch;
    private BitmapFont font;
    private MarioContext marioContext;
    private Rectangle mario;

    private LogService logService;
    private ArrayList<Rectangle> obstacles;
    private ArrayList<PowerUp> powerUps;
    private double cameraOffset;
    private float velocityY = 0;
    private final float gravity = -400;
    private final float jumpStrength = 250;
    private boolean isJumping = false;
    @Override
    public void create() {
        shapeRenderer = new ShapeRenderer();
        spriteBatch = new SpriteBatch();
        font = new BitmapFont();
        font.setColor(Color.WHITE);
        logService = LogService.getInstance();

        marioContext = new MarioContext();
        mario = new Rectangle(100, 100, 50, marioContext.getCurrentHeight());

        obstacles = new ArrayList<>();
        powerUps = new ArrayList<PowerUp>();
        cameraOffset = 0;

        generateLevel();

    }




    @Override
    public void render() {

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        handleInput();
        checkCollisions();
        updateLevel();

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        shapeRenderer.setColor(Color.RED);
        shapeRenderer.rect(mario.x, mario.y, mario.width, mario.height);

        shapeRenderer.setColor(Color.GRAY);
        for (Rectangle obstacle : obstacles) {
            shapeRenderer.rect(obstacle.x, obstacle.y, obstacle.width, obstacle.height);
        }

        for (PowerUp powerUp : powerUps) {
            if (powerUp.getType().equals("Mushroom")) {
                shapeRenderer.setColor(Color.GREEN);
            } else if (powerUp.getType().equals("Star")) {
                shapeRenderer.setColor(Color.YELLOW);
            }
            shapeRenderer.rect(powerUp.getBounds().x, powerUp.getBounds().y, powerUp.getBounds().width, powerUp.getBounds().height);
        }

        shapeRenderer.end();

        spriteBatch.begin();
        logService.renderLogs(spriteBatch, font);
        spriteBatch.end();
    }











    private void generateLevel() {
        for (int i = 1; i <= 5; i++) {
            obstacles.add(new Rectangle(300 * i, 100, 50, 50));
        }

        powerUps.add(new PowerUp(new Rectangle(350, 150, 30, 30), "Mushroom"));
        powerUps.add(new PowerUp(new Rectangle(700, 150, 30, 30), "Star"));
    }



    private void updateLevel() {
        float speed = 200 * Gdx.graphics.getDeltaTime();

        for (Rectangle obstacle : obstacles) {
            obstacle.x -= speed;
            if (obstacle.x + obstacle.width < mario.x - 100) {
                obstacle.x = (float) (mario.x + 800 + Math.random() * 300);
            }
        }

        for (PowerUp powerUp : powerUps) {
            powerUp.getBounds().x -= speed;
            if (powerUp.getBounds().x + powerUp.getBounds().width < mario.x - 100) {
                if (powerUp.getType().equals("Mushroom")) {
                    powerUp.getBounds().x = (float) (mario.x + 800 + Math.random() * 300);
                } else if (powerUp.getType().equals("Star")) {
                    powerUp.getBounds().x = (float) (mario.x + 1000 + Math.random() * 300);
                }
            }
        }

        if (Math.random() < 0.01) {
            if (Math.random() < 0.5) {
                powerUps.add(new PowerUp(new Rectangle((float) (mario.x + 800 + Math.random() * 300), (float) (Math.random() * 200), 30, 30), "Mushroom"));
            } else {
                obstacles.add(new Rectangle((float) (mario.x + 800 + Math.random() * 300), (float) (Math.random() * 200), 50, 50));
            }
        }

        // Apply gravity to Mario's vertical velocity
        velocityY += gravity * Gdx.graphics.getDeltaTime();  // Apply gravity to velocity

        // Update Mario's vertical position
        mario.y += velocityY * Gdx.graphics.getDeltaTime();


        if (mario.y <= 100) {
            mario.y = 100;  // Ensure Mario is not below the ground
            velocityY = 0;  // Stop downward velocity
            isJumping = false;  // Allow Mario to jump again
        }
    }
    private void handleInput() {

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            mario.x -= 200 * Gdx.graphics.getDeltaTime();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            mario.x += 200 * Gdx.graphics.getDeltaTime();
        }


        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE) && !isJumping) {
            velocityY = jumpStrength;  // Set upward velocity
            isJumping = true;  // Mark Mario as in the air
        }
    }
    private void checkCollisions() {
        // Check collisions with power-ups and obstacles
        Iterator<PowerUp> powerUpIterator = powerUps.iterator();
        while (powerUpIterator.hasNext()) {
            PowerUp powerUp = powerUpIterator.next();
            if (mario.overlaps(new Rectangle((float) (powerUp.getBounds().x - cameraOffset), powerUp.getBounds().y, powerUp.getBounds().width, powerUp.getBounds().height))) {
                powerUpIterator.remove();

                if (powerUp.getType().equals("Mushroom")) {
                    marioContext.setState(new BigMario());
                    mario.height = marioContext.getCurrentHeight();
                    logService.log("Mario took a Mushroom and became Big Mario!");
                } else if (powerUp.getType().equals("Star")) {
                    marioContext.setState(new InvincibleMario());
                    mario.height = marioContext.getCurrentHeight();
                    logService.log("Mario took a Star and became Invincible!");
                }
            }
        }
    }














    @Override
    public void dispose() {
        spriteBatch.dispose();
        shapeRenderer.dispose();
        font.dispose();
    }
}
