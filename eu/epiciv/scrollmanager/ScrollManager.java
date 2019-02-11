package eu.epiciv.scrollmanager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class ScrollManager implements Screen, GestureDetector.GestureListener {

	private SpriteBatch batch;
	private Sprite sprite;
	OrthographicCamera camera;
	boolean test = false;
	Vector3 touchPosition;
	private float pos_cam_X = 0;
	private float pos_cam_Y = 0;
	private float temp_X = 0;
	private float temp_Y = 0;
	float GAME_WORLD_WIDTH = 1656;
	float GAME_WORLD_HEIGHT = 1248;
	float aspectRatio;

	public ScrollManager() {
		batch = new SpriteBatch();
		sprite = new Sprite(new Texture(Gdx.files.internal("map.png")));
		aspectRatio = (float)Gdx.graphics.getHeight() / (float)Gdx.graphics.getWidth();
		camera = new OrthographicCamera(GAME_WORLD_WIDTH, GAME_WORLD_HEIGHT);
		camera.position.set(GAME_WORLD_WIDTH / 2, GAME_WORLD_HEIGHT / 2, 0);
		camera.viewportWidth = GAME_WORLD_WIDTH / 2;
		camera.viewportHeight = GAME_WORLD_HEIGHT / 2;
		camera.update();
	}

	@Override
	public void show() {
		camera.zoom = 5;
		touchPosition = new Vector3();
		Gdx.input.setInputProcessor(new GestureDetector(this));
	}

	public void render()
	{
		handleInput();
		camera.update();
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		sprite.draw(batch);
		batch.end();
	}

	@Override
	public void render(float delta) {
		handleInput();
		camera.update();
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		sprite.draw(batch);
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

	@Override
	public void dispose() {
		sprite.getTexture().dispose();
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

	private void handleInput() {

		if (Gdx.input.isTouched()) {
			temp_X = Gdx.input.getX();
			temp_Y = Gdx.input.getY();
			if (test) {
				temp_X = pos_cam_X;
				temp_Y = pos_cam_Y;
			}
			pos_cam_X = Gdx.input.getX();
			pos_cam_Y = Gdx.input.getY();
			camera.translate(temp_X - pos_cam_X,  pos_cam_Y - temp_Y, 0 );
		}
		test = Gdx.input.isTouched();
		camera.zoom = MathUtils.clamp(camera.zoom, 0.2f, 0.5f);
		float effectiveViewportWidth = camera.viewportWidth * camera.zoom;
		float effectiveViewportHeight = camera.viewportHeight * camera.zoom;
		camera.position.x = MathUtils.clamp(camera.position.x, effectiveViewportWidth / 2f, GAME_WORLD_WIDTH - effectiveViewportWidth / 2f);
		camera.position.y = MathUtils.clamp(camera.position.y, effectiveViewportHeight / 2f, GAME_WORLD_HEIGHT - effectiveViewportHeight / 2f);
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