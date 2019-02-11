package eu.epiciv.main;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import eu.epiciv.scrollmanager.ScrollManager;

public class Main extends ApplicationAdapter {
	ScrollManager scroll;
	float delta;
	
	@Override
	public void create () {
		scroll = new ScrollManager();
	}

	@Override
	public void render () {
		scroll.render(delta);
	}
	
	@Override
	public void dispose () {
		scroll.dispose();
	}
}
