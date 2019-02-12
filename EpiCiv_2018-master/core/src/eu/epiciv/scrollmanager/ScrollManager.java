package eu.epiciv.scrollmanager;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import eu.epiciv.menu.managers.GameStateManager;
import eu.epiciv.menu.states.GameState;

public class ScrollManager extends GameState {
	Manage_scroll scroll;
	float delta;
	public ScrollManager (GameStateManager gsm) {
		super(gsm);
		scroll = new Manage_scroll();
	}

	@Override
	protected void handleInput() {

	}

	@Override
	public void update(float dt) {
	}

	@Override
	public void render(SpriteBatch sb) {
		scroll.render(delta);
	}

	@Override
	public void dispose() {

	}
}