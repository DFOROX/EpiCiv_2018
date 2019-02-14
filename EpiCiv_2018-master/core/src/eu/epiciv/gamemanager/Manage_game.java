package eu.epiciv.gamemanager;

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

public class Manage_game implements Screen, GestureDetector.GestureListener {

    private SpriteBatch batch;
    public ArrayList al = new ArrayList();
    public ArrayList ad = new ArrayList();
    public ArrayList temp = new ArrayList();
    private Sprite sprite;
    private OrthographicCamera camera;
    boolean noTouch = false;
    private Vector3 touchPosition;
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
    public Unit unit;
    public Unit type = null;
    public Texture blue_select = new Texture("divers/blue_select.png");
    public Texture red_select = new Texture("divers/red_select.png");

    public Manage_game() {
        batch = new SpriteBatch();
        unit = new Unit();
        unit.castle(480, 640);
        al.add(unit);
        unit = new Unit();
        unit.lancier(480, 640);
        al.add(unit);
        unit = new Unit();
        unit.castle(480, 704);
        ad.add(unit);
        unit = new Unit();
        unit.lancier(480, 704);
        ad.add(unit);
        for (int i = 0; i < al.size(); i++) {
            unit = (Unit) al.get(i);
            unit.mvt = unit.mv;
        }
        this.pixmap = new Pixmap(Gdx.files.internal("divers/map.png"));
        this.color = new Color();
        batch = new SpriteBatch();
        sprite = new Sprite(new Texture(Gdx.files.internal("divers/map.png")));
        aspectRatio = (float) Gdx.graphics.getHeight() / (float) Gdx.graphics.getWidth();
        camera = new OrthographicCamera(GAME_WORLD_WIDTH, GAME_WORLD_HEIGHT);
        unit = (Unit)al.get(0);
        camera.position.x = unit.x;
        camera.position.y = unit.y;
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
        select();
        handlenput();
        camera.update();
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        sprite.draw(batch);
        for (int i = 0; i < al.size(); i++) {
            unit = (Unit) al.get(i);
            if (unit.recruit >= 0) {
                batch.draw(blue_select, unit.x, unit.y);
                batch.draw(unit.img, unit.x, unit.y);
            }
        }
        for (int i = 0; i < ad.size(); i++) {
            unit = (Unit) ad.get(i);
            if (unit.recruit >= 0) {
                batch.draw(red_select, unit.x, unit.y);
                batch.draw(unit.img, unit.x, unit.y);
            }        }
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

    public boolean check(float x1, float y1, int size) {
        size --;
        for (float y = y1 + 1; y < y1 + size; y++) {
            for (float x = x1 + 1; x < x1 + size; x++) {
                int val = pixmap.getPixel((int) x, (int) y);
                Color.rgba8888ToColor(color, val);
                if (((int) (color.b * 255) >= 150 && (int) (color.b * 255) <= 156 && (int) (color.r * 255) >= 18 && (int) (color.r * 255) <= 24 && (int) (color.g * 255) >= 105 && (int) (color.g * 255) <= 111) || ((int) (color.b * 255) >= 34 && (int) (color.b * 255) <= 40 && (int) (color.r * 255) >= 47 && (int) (color.r * 255) <= 53 && (int) (color.g * 255) >= 30 && (int) (color.g * 255) <= 36)) {
                    return (false);
                }
            }
        }
        return (true);
    }


    @Override
    public void dispose() {
        sprite.getTexture().dispose();
        for (int i = 0; i < al.size(); i++) {
            unit = (Unit) al.get(i);
            unit.img.dispose();
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

    public void select() {
        String test = null;
        boolean test2 = false;
        if (Gdx.input.isTouched() && !noTouch) {
            float x = (camera.position.x + (Gdx.input.getX() / (2 / (float)0.3)) - (Gdx.graphics.getWidth() / (4 / (float)0.3))) / 32;
            float y = 1 + (camera.position.y + (Gdx.graphics.getHeight() / 8) - (Gdx.input.getY() / (2 / (float)0.3)) - (Gdx.graphics.getHeight() / (4 / (float)0.3))) / 32;
            for (int i = 0; i < al.size(); i++) {
                unit = (Unit) al.get(i);
                if (unit.x == (int)x * 32 && unit.y == (int)y * 32 && unit.recruit >= 0) {
                    type = unit;
                    test2 = true;
                }
                if (type != null && unit.x == type.x && unit.y == type.y && unit.recruit < 0) {
                    test = unit.type;
                }
            }
            int j = ad.size() - 1;
            for (int i = j; i >= 0 && type != null && (type.mvt > 0 || i < j); i--) {
                unit = (Unit) ad.get(i);
                if (unit.recruit >= 0 && unit.x == (int)x * 32 && unit.y == (int)y * 32 && check((int)x * 32, (this.pixmap.getHeight() - 33 - (int)y * 32),32) && type.x/32 - (int)x < 2 && type.x/32 - (int)x > -2 && type.y/32 - (int)y < 2 && type.y/32 - (int)y > -2) {
                    test2 = true;
                    type.mvt = 0;
                    fight(i);
                }
            }
            if (!test2 && type != null && type.mvt > 0 && check((int)x * 32, (this.pixmap.getHeight() - 33 - (int)y * 32), 32)&& type.x/32 - (int)x < 2 && type.x/32 - (int)x > -2 && type.y/32 - (int)y < 2 && type.y/32 - (int)y > -2) {
                type.x = (int) x * 32;
                type.y = (int) y * 32;
                type.mvt -= 1;
            }
            if (type != null && type.mv == 0 && test == null) {
                unit = new Unit();
                unit.lancier(type.x, type.y);
                al.add(unit);
            }
        }
    }

    public void fight(int i) {
        while (unit.pv > 0 && type.pv > 0) {
            if (Math.random() * (type.win + unit.win) < type.win) {
                unit.pv -= type.dgt;
            } else {
                type.pv -= unit.dgt;
            }
        }
        if (type.pv > 0) {
            type.x = unit.x;
            type.y = unit.y;
            for (int j = i; j < ad.size(); j++) {
                unit = (Unit)ad.get(j);
                if (unit.x == type.x && unit.y == type.y) {
                    ad.remove(j);
                }
            }
        } else {
            for (int j = 0; j < al.size(); j++) {
                unit = (Unit)al.get(j);
                if (unit.x == type.x && unit.y == type.y) {
                    i = j;
                }
            }
            type = null;
            al.remove(i);
        }
    }

    private void handlenput() {

        if (Gdx.input.isTouched()) {
            temp_X = Gdx.input.getX();
            temp_Y = Gdx.input.getY();
            if (noTouch) {
                temp_X = pos_cam_X;
                temp_Y = pos_cam_Y;
            }
            pos_cam_X = Gdx.input.getX();
            pos_cam_Y = Gdx.input.getY();
            camera.translate((temp_X - pos_cam_X) / 8, (pos_cam_Y - temp_Y) / 8, 0);
        }
        noTouch = Gdx.input.isTouched();
        camera.zoom = MathUtils.clamp(camera.zoom, 0.3f, 0.3f);
        float effectiveViewportWidth = camera.viewportWidth * camera.zoom;
        float effectiveViewportHeight = camera.viewportHeight * camera.zoom;
        camera.position.x = MathUtils.clamp(camera.position.x, effectiveViewportWidth / 2f - 75, GAME_WORLD_WIDTH - effectiveViewportWidth / 2f - 1);
        camera.position.y = MathUtils.clamp(camera.position.y, effectiveViewportHeight / 2f + 34, GAME_WORLD_HEIGHT - effectiveViewportHeight / 2f);
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