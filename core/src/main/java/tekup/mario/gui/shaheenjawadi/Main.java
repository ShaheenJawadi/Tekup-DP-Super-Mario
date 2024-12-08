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
    private ArrayList<Rectangle> powerUps;
    private float cameraOffset;

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
        cameraOffset = 0;

        generateLevel();
    }

    private void generateLevel() {
        // Generate initial obstacles and power-ups
        for (int i = 1; i <= 5; i++) {
            obstacles.add(new Rectangle(300 * i, 100, 50, 50));
        }

        powerUps.add(new Rectangle(600, 150, 30, 30)); // Mushroom
        powerUps.add(new Rectangle(1200, 150, 30, 30)); // Star
    }

    @Override
    public void render() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        handleInput();
        updateLevel();
        checkCollisions();

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);


        shapeRenderer.setColor(Color.RED);
        shapeRenderer.rect(mario.x, mario.y, mario.width, marioContext.getCurrentHeight());


        shapeRenderer.setColor(Color.GRAY);
        for (Rectangle obstacle : obstacles) {
            shapeRenderer.rect(obstacle.x - cameraOffset, obstacle.y, obstacle.width, obstacle.height);
        }

        for (Rectangle powerUp : powerUps) {
            shapeRenderer.setColor(powerUps.indexOf(powerUp) == 0 ? Color.GREEN : Color.YELLOW);
            shapeRenderer.rect(powerUp.x - cameraOffset, powerUp.y, powerUp.width, powerUp.height);
        }

        shapeRenderer.end();

        spriteBatch.begin();
        logService.renderLogs(spriteBatch, font);
        spriteBatch.end();
    }

    private void handleInput() {
        float delta = Gdx.graphics.getDeltaTime();
        float speed = 200 * delta;

        float leftBoundary = 100;
        float rightBoundary = Gdx.graphics.getWidth() * 0.7f;

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) && mario.x > leftBoundary) {
            mario.x -= speed;

        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {

            if (mario.x < rightBoundary) {
                mario.x += speed;
            } else {

                cameraOffset += speed;
            }

        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            mario.y += 50;

        }
    }


    private void updateLevel() {
        // Remove obstacles and power-ups that are off-screen
        obstacles.removeIf(obstacle -> obstacle.x - cameraOffset + obstacle.width < 0);
        powerUps.removeIf(powerUp -> powerUp.x - cameraOffset + powerUp.width < 0);

        // Add new obstacles and power-ups dynamically
        while (obstacles.size() < 5) {
            obstacles.add(new Rectangle(cameraOffset + Gdx.graphics.getWidth() + (float) Math.random() * 300, 100, 50, 50));
        }
        while (powerUps.size() < 2) {
            powerUps.add(new Rectangle(cameraOffset + Gdx.graphics.getWidth() + (float) Math.random() * 300, 150, 30, 30));
        }
    }


    private void checkCollisions() {
        // Check collisions with power-ups
        Iterator<Rectangle> powerUpIterator = powerUps.iterator();
        while (powerUpIterator.hasNext()) {
            Rectangle powerUp = powerUpIterator.next();
            if (mario.overlaps(new Rectangle(powerUp.x - cameraOffset, powerUp.y, powerUp.width, powerUp.height))) {
                powerUpIterator.remove();

                if (powerUps.indexOf(powerUp) == 0) { // Mushroom
                    marioContext = new Mushroom(marioContext);
                    marioContext.setState(new BigMario());
                    mario.height = marioContext.getCurrentHeight();
                    logService.log("Mario took a Mushroom and became Big Mario!");
                } else { // Star
                    marioContext = new Star(marioContext);
                    marioContext.setState(new InvincibleMario());
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
