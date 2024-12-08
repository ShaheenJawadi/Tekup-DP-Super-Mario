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
import tekup.mario.gui.shaheenjawadi.states.NormalMario;
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

    private float velocityY = 0;
    private final float gravity = -400;
    private final float jumpStrength = 250;
    private boolean isJumping = false;

    private float cameraOffset = 0; // Camera offset to track Mario

    private float bigMarioTimer = 0;
    private float invincibleMarioTimer = 0;

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
        powerUps = new ArrayList<>();
        generateLevel();
    }

    @Override
    public void render() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        handleInput();
        checkCollisions();
        updateLevel();
        updateTimers();

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        shapeRenderer.setColor(Color.RED);
        shapeRenderer.rect(mario.x - cameraOffset, mario.y, mario.width, mario.height);

        shapeRenderer.setColor(Color.GRAY);
        for (Rectangle obstacle : obstacles) {
            shapeRenderer.rect(obstacle.x - cameraOffset, obstacle.y, obstacle.width, obstacle.height);
        }

        for (PowerUp powerUp : powerUps) {
            if (powerUp.getType().equals("Mushroom")) {
                shapeRenderer.setColor(Color.GREEN);
            } else if (powerUp.getType().equals("Star")) {
                shapeRenderer.setColor(Color.YELLOW);
            }
            shapeRenderer.rect(powerUp.getBounds().x - cameraOffset, powerUp.getBounds().y, powerUp.getBounds().width, powerUp.getBounds().height);
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
        velocityY += gravity * Gdx.graphics.getDeltaTime();
        mario.y += velocityY * Gdx.graphics.getDeltaTime();

        if (mario.y <= 100) {
            mario.y = 100;
            velocityY = 0;
            isJumping = false;
        }

        cameraOffset = mario.x - 100;

        for (Rectangle obstacle : obstacles) {
            if (mario.overlaps(obstacle)) {
                if (mario.x < obstacle.x) {
                    mario.x = obstacle.x - mario.width;
                } else if (mario.x > obstacle.x + obstacle.width) {
                    mario.x = obstacle.x + obstacle.width;
                }
            }

            if (mario.overlaps(obstacle)) {
                if (mario.y > obstacle.y + obstacle.height) {
                    mario.y = obstacle.y + obstacle.height;
                    velocityY = 0;
                    isJumping = false;
                } else if (mario.y + mario.height < obstacle.y) {
                    velocityY = Math.min(velocityY, 0);
                }
            }
        }

        for (Rectangle obstacle : obstacles) {
            if (obstacle.x + obstacle.width < cameraOffset) {
                obstacle.x = mario.x + 800 + (float) Math.random() * 300;
            }
        }

        for (PowerUp powerUp : powerUps) {
            if (powerUp.getBounds().x + powerUp.getBounds().width < cameraOffset) {
                powerUp.getBounds().x = mario.x + 800 + (float) Math.random() * 300;
            }
        }
    }

    private void updateTimers() {
        float deltaTime = Gdx.graphics.getDeltaTime();

        // Decrease timers
        if (bigMarioTimer > 0) {
            bigMarioTimer -= deltaTime;
            if (bigMarioTimer <= 0) {
                marioContext.setState(new NormalMario());
                mario.height = marioContext.getCurrentHeight();
                logService.log("Big Mario effect ended.");
            }
        }

        if (invincibleMarioTimer > 0) {
            invincibleMarioTimer -= deltaTime;
            if (invincibleMarioTimer <= 0) {
                marioContext.setState(new NormalMario());
                mario.height = marioContext.getCurrentHeight();
                logService.log("Invincibility effect ended.");
            }
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
            velocityY = jumpStrength;
            isJumping = true;
        }
    }

    private void checkCollisions() {
        Iterator<PowerUp> powerUpIterator = powerUps.iterator();
        while (powerUpIterator.hasNext()) {
            PowerUp powerUp = powerUpIterator.next();
            if (mario.overlaps(powerUp.getBounds())) {
                powerUpIterator.remove();

                if (powerUp.getType().equals("Mushroom")) {
                    marioContext.setState(new BigMario());
                    mario.height = marioContext.getCurrentHeight();
                    bigMarioTimer = 10;
                    invincibleMarioTimer = 0;
                    logService.log("Mario took a Mushroom and became Big Mario!");
                } else if (powerUp.getType().equals("Star")) {
                    marioContext.setState(new InvincibleMario());
                    mario.height = marioContext.getCurrentHeight();
                    invincibleMarioTimer = 10;
                    bigMarioTimer = 0;
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
