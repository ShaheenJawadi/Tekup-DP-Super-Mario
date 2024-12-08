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
import tekup.mario.gui.shaheenjawadi.utils.LogService;

public class Main extends ApplicationAdapter {
    private ShapeRenderer shapeRenderer;
    private BitmapFont font;
    private SpriteBatch spriteBatch;
    private Rectangle mario;
    private Rectangle block;
    private Rectangle mushroom;

    private boolean hasMushroom;
    private LogService logService;
    @Override
    public void create() {
        shapeRenderer = new ShapeRenderer();
        spriteBatch = new SpriteBatch();
        font = new BitmapFont();
        font.setColor(Color.WHITE);
        mario = new Rectangle(100, 100, 50, 50);


        block = new Rectangle(200, 200, 60, 60);


        mushroom = new Rectangle(200, 300, 30, 30);

        hasMushroom = false;

        logService = LogService.getInstance();
    }

    @Override
    public void render() {
        // Clear the screen
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        handleInput();

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        shapeRenderer.setColor(Color.RED);
        shapeRenderer.rect(mario.x, mario.y, mario.width, mario.height);

        shapeRenderer.setColor(Color.GRAY);
        shapeRenderer.rect(block.x, block.y, block.width, block.height);

        if (!hasMushroom) {
            shapeRenderer.setColor(Color.GREEN);
            shapeRenderer.rect(mushroom.x, mushroom.y, mushroom.width, mushroom.height);
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

        if (!hasMushroom && mario.overlaps(mushroom)) {
            hasMushroom = true;

            System.out.println(" mushroom!");
        }
    }

    @Override
    public void dispose() {
        spriteBatch.dispose();
        shapeRenderer.dispose();
        font.dispose();
    }
}
