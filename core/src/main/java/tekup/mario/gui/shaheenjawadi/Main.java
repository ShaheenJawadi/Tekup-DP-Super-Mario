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
    private float worldScrollSpeed = 200;

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
            shapeRenderer.rect(obstacle.x, obstacle.y, obstacle.width, obstacle.height);
        }

        for (Rectangle powerUp : powerUps) {
            shapeRenderer.setColor(powerUps.indexOf(powerUp) == 0 ? Color.GREEN : Color.YELLOW);
            shapeRenderer.rect(powerUp.x, powerUp.y, powerUp.width, powerUp.height);
        }

        shapeRenderer.end();

        spriteBatch.begin();
        logService.renderLogs(spriteBatch, font);
        spriteBatch.end();
    }

    private void handleInput() {
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            mario.x -= 200 * Gdx.graphics.getDeltaTime();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            mario.x += 200 * Gdx.graphics.getDeltaTime();
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            mario.y += 50;
        }
    }

    private void updateLevel() {
        float delta = Gdx.graphics.getDeltaTime();


        for (Rectangle obstacle : obstacles) {
            obstacle.x -= worldScrollSpeed * delta;
        }
        for (Rectangle powerUp : powerUps) {
            powerUp.x -= worldScrollSpeed * delta;
        }


        obstacles.removeIf(obstacle -> obstacle.x + obstacle.width < 0);
        powerUps.removeIf(powerUp -> powerUp.x + powerUp.width < 0);


        if (obstacles.size() < 5) {
            obstacles.add(new Rectangle(600 + (float) Math.random() * 300, 100, 50, 50));
        }
        if (powerUps.size() < 2) {
            powerUps.add(new Rectangle(1200 + (float) Math.random() * 300, 150, 30, 30));
        }
    }

    private void checkCollisions() {
        // Check collisions
        Iterator<Rectangle> powerUpIterator = powerUps.iterator();
        while (powerUpIterator.hasNext()) {
            Rectangle powerUp = powerUpIterator.next();
            if (mario.overlaps(powerUp)) {
                powerUpIterator.remove();

                if (powerUps.indexOf(powerUp) == 0) { // Mushroom
                    marioContext = new Mushroom(marioContext);
                    marioContext.setState(new BigMario());
                } else { // Star
                    marioContext = new Star(marioContext);
                    marioContext.setState(new InvincibleMario());
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
