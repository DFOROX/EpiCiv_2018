package eu.epiciv.menu.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import eu.epiciv.menu.managers.GameStateManager;

public abstract class GameState {

    public OrthographicCamera cam;
    protected GameStateManager gsm;

    public GameState(GameStateManager gsm) {
        this.gsm = gsm;
        this.cam = new OrthographicCamera();
    }

    protected abstract void handleInput();
    public abstract void update(float dt);
    public abstract void render(SpriteBatch sb);
    public  abstract void dispose();
}