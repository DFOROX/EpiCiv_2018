package eu.epiciv.scrollmanager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.graphics.Color;

import java.util.ArrayList;

import eu.epiciv.menu.managers.GameStateManager;
import eu.epiciv.menu.states.GameState;

public class Manage_scroll implements Screen, GestureDetector.GestureListener {

    private SpriteBatch batch;
    ArrayList al = new ArrayList();
    private Sprite sprite;
    private Sprite lol;
    OrthographicCamera camera;
    boolean test = false;
    Vector3 touchPosition;
    private float pos_cam_X = 0;
    private float pos_cam_Y = 0;
    private float temp_X = 0;
    private float temp_Y = 0;
    float GAME_WORLD_WIDTH = 2209;
    float GAME_WORLD_HEIGHT = 1761;
    float aspectRatio;
    float delta;
    public Pixmap pixmap;
    public Color color;
    unit u;

    public Manage_scroll() {
        batch = new SpriteBatch();
        unit u;
        for (int i = 0; i < 5; i++)
            al.add((unit) new unit());
        for (int i = 0; i < al.size(); i++) {
            u = (unit) al.get(i);
            u.lancier(480 + i * 32, 640);
        }
        this.pixmap = new Pixmap(Gdx.files.internal("map.png"));
        lol = new Sprite(new Texture(Gdx.files.internal("ui.png")));
        this.color = new Color();
        batch = new SpriteBatch();
        sprite = new Sprite(new Texture(Gdx.files.internal("map.png")));
        aspectRatio = (float) Gdx.graphics.getHeight() / (float) Gdx.graphics.getWidth();
        camera = new OrthographicCamera(GAME_WORLD_WIDTH, GAME_WORLD_HEIGHT);
        camera.position.x = 0;
        camera.position.y = 1761;
        camera.viewportWidth = Gdx.graphics.getWidth() / 2;
        camera.viewportHeight = Gdx.graphics.getHeight() / 2;
        camera.update();
    }

    @Override
    public void show() {
        camera.zoom = 5;
        touchPosition = new Vector3();
        Gdx.input.setInputProcessor(new GestureDetector(this));
    }

    @Override
    public void render(float delta) {
        System.out.println(check(Gdx.input.getX(), Gdx.input.getY(), 32));
        handlenput();
        camera.update();
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        sprite.draw(batch);
        for (int i = 0; i < al.size(); i++) {
            u = (unit) al.get(i);
            batch.draw(u.img, u.x, u.y);
        }
        batch.end();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    public int check(float x1, float y1, int size) {
        for (float y = y1; y < y1 + size; y++) {
            for (float x = x1; x < x1 + size; x++) {
                int val = pixmap.getPixel((int) x, (int) y);
                Color.rgba8888ToColor(color, val);
                if ((int) (color.b * 255f) > 100)
                    return (1);
            }
        }
        return (0);
    }

    @Override
    public void dispose() {
        sprite.getTexture().dispose();
        for (int i = 0; i < al.size(); i++) {
            u = (unit) al.get(i);
            u.img.dispose();
        }
    }

    @Override
    public boolean touchDown(float x, float y, int pointer, int button) {
        touchPosition.set(x, y, 0);
        camera.unproject(touchPosition);
        return false;
    }

    @Override
    public boolean tap(float x, float y, int count, int button) {
        return false;
    }

    @Override
    public boolean longPress(float x, float y) {
        return false;
    }

    @Override
    public boolean fling(float velocityX, float velocityY, int button) {
        return false;
    }

    @Override
    public boolean pan(float x, float y, float deltaX, float deltaY) {
        camera.translate(-deltaX, deltaY);
        return false;
    }

    @Override
    public boolean panStop(float x, float y, int pointer, int button) {
        return false;
    }

    private void handlenput() {

        if (Gdx.input.isTouched()) {
            temp_X = Gdx.input.getX();
            temp_Y = Gdx.input.getY();
            if (test) {
                temp_X = pos_cam_X;
                temp_Y = pos_cam_Y;
            }
            pos_cam_X = Gdx.input.getX();
            pos_cam_Y = Gdx.input.getY();
            camera.translate((temp_X - pos_cam_X) / 8, (pos_cam_Y - temp_Y) / 8, 0);
        }
        test = Gdx.input.isTouched();
        camera.zoom = MathUtils.clamp(camera.zoom, 0.3f, 0.3f);
        float effectiveViewportWidth = camera.viewportWidth * camera.zoom;
        float effectiveViewportHeight = camera.viewportHeight * camera.zoom;
        camera.position.x = MathUtils.clamp(camera.position.x, effectiveViewportWidth / 2f, GAME_WORLD_WIDTH - effectiveViewportWidth / 2f - 1);
        camera.position.y = MathUtils.clamp(camera.position.y, effectiveViewportHeight / 2f + 2, GAME_WORLD_HEIGHT - effectiveViewportHeight / 2f);
    }

    @Override
    public boolean zoom(float initialDistance, float distance) {
        if (initialDistance >= distance) {
            camera.zoom += 0.02;
        } else {
            camera.zoom -= 0.02;
        }
        return false;
    }

    @Override
    public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2) {
        return false;
    }

    @Override
    public void pinchStop() {
    }

    @Override
    public void hide() {

    }
}