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
import eu.epiciv.menu.utils.Constants;

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

//	private CastleCreator createCastle;
	private Texture createButton = new Texture("buttons/createButton.png");
	private int xTouch;
	private int yTouch;

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
	    if (Gdx.input.justTouched()) {
            this.xTouch = Gdx.input.getX();
            this.yTouch = Gdx.input.getY();

            if (scroll != null && scroll.type != null && scroll.type.dgt == 0) {
                if (this.xTouch > Constants.createButtonCenterX && this.xTouch < Constants.createButtonCenterX + Constants.buttonSizeX ) {
                    if (this.yTouch > Constants.buttonSizeY * 7 && this.yTouch < Constants.buttonSizeY * 8) {
                        scroll.unit = new Unit();
                        scroll.unit.castle(scroll.type.x, scroll.type.y);
                        scroll.al.add(scroll.unit);
                        //destroy builder
                        for (int j = scroll.al.size() - 1; j >= 0; j -= 1) {
                            scroll.unit = (Unit)scroll.al.get(j);
                            if (scroll.unit == scroll.type) {
								scroll.al.remove(j);
							}
                        }
						scroll.type = null;
                    }
                }
            }
        }
	}

	@Override
	public void update(float dt) {
	    this.handleInput();
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

	        if (Gdx.input.isTouched() && !scroll.noTouch)
                if (scroll.ad.size() == 0)
                    System.exit(0);

			turn_mangement();
			scroll.render(delta);
			batch.begin();
			user_interface.setSize(Gdx.graphics.getWidth() / 4, Gdx.graphics.getHeight());
			user_interface.setPosition(0,0);
			font.setColor(0.5f,1.5f,1.5f,0.5f);
			user_interface.draw(batch);
            turn.setPosition(Gdx.graphics.getWidth() - size_button_X, 0);
            turn.setSize(size_button_X, size_button_Y);
			turn.draw(batch);
			font.getData().setScale((Gdx.graphics.getWidth()/400 + (Gdx.graphics.getHeight()*61)/(524*11)) / 2, (Gdx.graphics.getWidth()/400 + (Gdx.graphics.getHeight()*61)/(524*11)) / 2);
			font.draw(batch, "Player " + bool_turn, Gdx.graphics.getWidth()/40,Gdx.graphics.getHeight());
			font.getData().setScale(Gdx.graphics.getWidth()/400, Gdx.graphics.getWidth()/400);
			if (scroll.type != null) {
				font.setColor(0.5f,1.5f,1.5f,0.5f);
				font.draw(batch, scroll.type.type, 0, Gdx.graphics.getHeight() - ((Gdx.graphics.getHeight()*84)/524));
				font.draw(batch, "PV:  " + scroll.type.pv, 0, Gdx.graphics.getHeight() - 400);
				font.draw(batch, "ATK:  " + scroll.type.dgt, 0, Gdx.graphics.getHeight() - 490);
				font.draw(batch, "MOVE:  " + scroll.type.mvt, 0, Gdx.graphics.getHeight() - 600);
			} else {
				font.setColor(1,0,0,0.5f);
				font.draw(batch, "No unit selected", 0, Gdx.graphics.getHeight() - ((Gdx.graphics.getHeight()*84)/524));
			}
		if (scroll != null && scroll.type != null && scroll.type.dgt == 0) {
			batch.draw(this.createButton, Constants.createButtonCenterX, 0, Constants.buttonSizeX, Constants.buttonSizeY);
		}
        if (scroll.ad.size() == 0) {
            Texture win = new Texture("victory.png");
            batch.draw(win, (Gdx.graphics.getWidth() / 2) - Gdx.graphics.getHeight() / 8, (Gdx.graphics.getHeight() / 2) - Gdx.graphics.getHeight() / 8/*(win.getHeight() / 2)*/, Gdx.graphics.getWidth() / 4, Gdx.graphics.getHeight() / 4);
        }
			batch.end();
	}
	@Override
	public void dispose() {
	}
}