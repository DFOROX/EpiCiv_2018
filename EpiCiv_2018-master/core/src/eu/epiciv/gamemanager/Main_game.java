package eu.epiciv.gamemanager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import eu.epiciv.castlemanager.CastleManager;
import eu.epiciv.menu.managers.GameStateManager;
import eu.epiciv.menu.states.GameState;

public class Main_game extends GameState {

	Sprite user_interface;
	SpriteBatch batch;
	Sprite turn;
	Manage_game scroll;
	private Music music_unit = Gdx.audio.newMusic(Gdx.files.internal("music/unit_create.mp3"));
	float delta;
	BitmapFont font;
	Unit stat;
	float size_button_X;
	float size_button_Y;
	int bool_turn = 1;

	public Main_game(GameStateManager gsm) {
		super(gsm);
		stat = new Unit();
		turn = new Sprite(new Texture(Gdx.files.internal("divers/turn.png")));
		size_button_X = Gdx.graphics.getWidth() / 9;
		size_button_Y = Gdx.graphics.getHeight() / 6;
		scroll = new Manage_game();
		font = new BitmapFont();
		batch = new SpriteBatch();
		user_interface = new Sprite(new Texture(Gdx.files.internal("divers/ui.png")));
	}

	@Override
	protected void handleInput() {
	}

	@Override
	public void update(float dt) {
	}

	public void turn_mangement () {
		if (Gdx.input.isTouched() && scroll.noTouch == false) {
			if ((Gdx.graphics.getWidth() - Gdx.input.getX())  <= size_button_X  && Gdx.graphics.getHeight() - Gdx.input.getY() <= size_button_Y) {
				scroll.temp = scroll.ad;
				scroll.ad = scroll.al;
				scroll.al = scroll.temp;
				scroll.type = null;
				for (int i = 0; i < scroll.al.size(); i++) {
					scroll.unit = (Unit) scroll.al.get(i);
					scroll.unit.mvt = scroll.unit.mv;
					if (scroll.unit.recruit < 0) {
						scroll.unit.recruit++;
						if (scroll.unit.recruit == 0) {
							this.music_unit.play();
							this.music_unit.setVolume(0.1f);
						}
					}
				}
				bool_turn = (bool_turn == 1) ? 2 : 1;
				scroll.unit = (Unit)scroll.al.get(0);
				scroll.camera.position.x = scroll.unit.x;
				scroll.camera.position.y = scroll.unit.y;
			}
			}
		if (scroll != null && scroll.type != null && scroll.test == null && scroll.type.mv == 0) {
			this.gsm.push(new CastleManager(scroll, this.gsm, this.batch));
			return;
		}
	}

	@Override
	public void render(SpriteBatch sb) {
		turn_mangement();
		scroll.render(delta);
		batch.begin();
		font.setColor(0.5f,1.5f,1.5f,0.5f);
		user_interface.setSize(500, Gdx.graphics.getHeight());
		user_interface.setPosition(0,0);
		user_interface.draw(batch);
		turn.setPosition(Gdx.graphics.getWidth() - size_button_X, 0);
		turn.setSize(size_button_X, size_button_Y);
		turn.draw(batch);
		font.getData().setScale(4,4);
		font.draw(batch, "Player " + bool_turn, 40,Gdx.graphics.getHeight() - 50);
		if (scroll.type != null) {
			font.draw(batch, scroll.type.type, 40, Gdx.graphics.getHeight() - 250);
			font.draw(batch, "PV:  " + scroll.type.pv, 40, Gdx.graphics.getHeight() - 400);
			font.draw(batch, "ATK:  " + scroll.type.dgt, 40, Gdx.graphics.getHeight() - 490);
			font.draw(batch, "MOVE:  " + scroll.type.mvt, 40, Gdx.graphics.getHeight() - 600);
		} else {
			font.draw(batch, "No unit selected", 40, Gdx.graphics.getHeight() - 250);
		}
		batch.end();
	}

	@Override
	public void dispose() {
	}
}