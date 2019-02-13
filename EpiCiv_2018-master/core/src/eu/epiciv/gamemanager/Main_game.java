package eu.epiciv.gamemanager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import eu.epiciv.menu.managers.GameStateManager;
import eu.epiciv.menu.states.GameState;

public class Main_game extends GameState {

	Sprite sprite;
	SpriteBatch batch;
	Sprite turn;
	Manage_game scroll;
	float delta;
	BitmapFont font;
	unit stat;
	int kk = 1;

	public Main_game(GameStateManager gsm) {
		super(gsm);
		stat = new unit();
		turn = new Sprite(new Texture(Gdx.files.internal("turn.png")));
		scroll = new Manage_game();
		font = new BitmapFont();
		batch = new SpriteBatch();
		sprite = new Sprite(new Texture(Gdx.files.internal("ui.png")));
	}

	@Override
	protected void handleInput() {
	}

	@Override
	public void update(float dt) {
	}

	public void turn_mangement () {
		if (Gdx.input.isTouched() && scroll.noTouch == false) {
			if ((Gdx.input.getX())  <= 255  && Gdx.input.getY() <= 255) {
				scroll.temp = scroll.ad;
				scroll.ad = scroll.al;
				scroll.al = scroll.temp;
				scroll.act = null;
				for (int i = 0; i < scroll.al.size(); i++) {
					scroll.u = (unit) scroll.al.get(i);
					scroll.u.mvt = scroll.u.mv;
				}
				kk = (kk == 1) ? 2 : 1;
			}
		}
	}

	@Override
	public void render(SpriteBatch sb) {
	    stat.lancier(1, 2);
		turn_mangement();
        scroll.render(delta);
		batch.begin();
        font.setColor(0.5f,1.5f,1.5f,0.5f);
		sprite.setSize(500, Gdx.graphics.getHeight());
		sprite.setPosition(0,0);
		sprite.draw(batch);
		turn.draw(batch);
        font.getData().setScale(4,4);
		font.draw(batch, "Player " + kk, 40,Gdx.graphics.getHeight() - 50);
		if (scroll.act != null) {
			font.draw(batch, scroll.act.type, 40, Gdx.graphics.getHeight() - 250);
			font.draw(batch, "PV:  " + scroll.act.pv, 40, Gdx.graphics.getHeight() - 400);
			font.draw(batch, "ATK:  " + scroll.act.dgt, 40, Gdx.graphics.getHeight() - 490);
			font.draw(batch, "MOVE:  " + scroll.act.mvt, 40, Gdx.graphics.getHeight() - 600);
		} else {
			font.draw(batch, "No unit selected", 40, Gdx.graphics.getHeight() - 250);
		}
		batch.end();
	}

	@Override
	public void dispose() {
	}
}