package eu.epiciv.menu;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import eu.epiciv.menu.managers.GameStateManager;
import eu.epiciv.menu.states.MainMenu;

public class LaunchGame extends ApplicationAdapter {

	private GameStateManager gsm;
	private SpriteBatch sb;

	@Override
	public void create () {
		this.gsm = new GameStateManager();
		this.sb = new SpriteBatch();

		this.gsm.push(new MainMenu(this.gsm));
	}

	@Override
	public void render () {
		this.gsm.update(Gdx.graphics.getDeltaTime());
		this.gsm.render(this.sb);
	}

	@Override
	public void dispose () {
		this.sb.dispose();
	}
}