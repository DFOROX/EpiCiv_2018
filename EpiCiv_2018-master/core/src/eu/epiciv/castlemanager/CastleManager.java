package eu.epiciv.castlemanager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

import java.util.ArrayList;

import eu.epiciv.gamemanager.Manage_game;
import eu.epiciv.menu.states.GameState;
import eu.epiciv.menu.managers.GameStateManager;
import eu.epiciv.menu.utils.Constants;
import eu.epiciv.gamemanager.Unit;

public class CastleManager extends GameState {

    private Texture ui, spearmanButton, swordmanButton, horsemanButton, builderButton, exitButton;
    private BitmapFont productText/*, spearmanLeftTurnText, swordmanLeftTurnText, horsemantLeftTurnText, builderLeftTurnText*/;

    private FreeTypeFontGenerator generator;
    private FreeTypeFontGenerator.FreeTypeFontParameter parameter;
    public Music music_notif_add = Gdx.audio.newMusic(Gdx.files.internal("music/notif_add_unit.mp3"));
    private GlyphLayout productGlyph/*, spearmanLeftTurnGlyph, swordmanLeftTurnGlyph, horsemanLeftTurnGlyph, builderLeftTurnGlyph*/;

    private int xTouch = 0;
    private int yTouch = 0;
    private String type = null;

    private Manage_game manage_game;
    private SpriteBatch sb;

    public CastleManager(Manage_game manageGame, GameStateManager gsm, SpriteBatch sb) {
        super(gsm);

//        this.type = Integer.toString(0);
        this.manage_game = manageGame;
        this.sb = sb;

        this.ui = new Texture("divers/ui.png");
        this.spearmanButton = new Texture("buttons/spearmanButton.png");
        this.swordmanButton = new Texture("buttons/swordmanButton.png");
        this.horsemanButton = new Texture("buttons/horsemanButton.png");
        this.builderButton = new Texture("buttons/builderButton.png");
        this.exitButton = new Texture("buttons/exitButton.png");

      //  this.generator = new FreeTypeFontGenerator(Gdx.files.internal("font/arial.ttf"));
        this.parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();

        this.parameter.size = Gdx.graphics.getHeight() / 9;
       /* this.productText = this.generator.generateFont(this.parameter);
        this.spearmanLeftTurnText = this.generator.generateFont(this.parameter);
        this.swordmanLeftTurnText = this.generator.generateFont(this.parameter);
        this.horsemantLeftTurnText = this.generator.generateFont(this.parameter);
        this.builderLeftTurnText = this.generator.generateFont(this.parameter);*/

        /*this.productGlyph = new GlyphLayout();
        this.spearmanLeftTurnGlyph  = new GlyphLayout();
        this.swordmanLeftTurnGlyph  = new GlyphLayout();
        this.horsemanLeftTurnGlyph  = new GlyphLayout();
        this.builderLeftTurnGlyph  = new GlyphLayout();*/

//        this.productGlyph.setText(this.productText, (String)("product: " + manageGame.type.product[manageGame.type.productId] + "/ " + manageGame.type.productTurn));
        this.cam.setToOrtho(false, Constants.VIEWPORT_WIDTH, Constants.VIEWPORT_HEIGHT);
    }

    @Override
    protected void handleInput()
    {
        if (Gdx.input.justTouched()) {
            this.xTouch = Gdx.input.getX();
            this.yTouch = Gdx.input.getY();

            if (this.xTouch > Constants.uiWidth || (this.xTouch > Constants.castleButtonCenterX && this.xTouch < Constants.castleButtonCenterX + Constants.castleButtonSizeX && this.yTouch > Constants.castleButtonSizeY * 6 && this.yTouch < Constants.castleButtonSizeY * 7)) {
                this.manage_game.type = null;
                this.gsm.pop();
                return;
            }
            if (this.xTouch > Constants.castleButtonCenterX && this.xTouch < Constants.castleButtonCenterX + Constants.castleButtonSizeX) {
                if (this.yTouch > Constants.castleButtonSizeY * 2 && this.yTouch < Constants.castleButtonSizeY * 3) {
                    this.manage_game.unit = new Unit();
                    this.manage_game.unit.spearman(this.manage_game.type.x, this.manage_game.type.y);
                    this.manage_game.al.add(this.manage_game.unit);
                    this.manage_game.type = null;
                    this.gsm.pop();
                    this.music_notif_add.play();
                    this.music_notif_add.setVolume(0.1f);
                    return;
                }
                if (this.yTouch > Constants.castleButtonSizeY * 3 && this.yTouch < Constants.castleButtonSizeY * 4) {
                    this.manage_game.unit = new Unit();
                    this.manage_game.unit.swordsman(this.manage_game.type.x, this.manage_game.type.y);
                    this.manage_game.al.add(this.manage_game.unit);
                    this.manage_game.type = null;
                    this.gsm.pop();
                    this.music_notif_add.play();
                    this.music_notif_add.setVolume(0.1f);
                    return;
                }
                if (this.yTouch > Constants.castleButtonSizeY * 4 && this.yTouch < Constants.castleButtonSizeY * 5) {
                    this.manage_game.unit = new Unit();
                    this.manage_game.unit.horseman(this.manage_game.type.x, this.manage_game.type.y);
                    this.manage_game.al.add(this.manage_game.unit);
                    this.manage_game.type = null;
                    this.gsm.pop();
                    this.music_notif_add.play();
                    this.music_notif_add.setVolume(0.1f);
                    return;
                }
                if (this.yTouch > Constants.castleButtonSizeY * 5 && this.yTouch < Constants.castleButtonSizeY * 6) {
                    this.manage_game.unit = new Unit();
                    this.manage_game.unit.builder(this.manage_game.type.x, this.manage_game.type.y);
                    this.manage_game.al.add(this.manage_game.unit);
                    this.manage_game.type = null;
                    this.gsm.pop();
                    this.music_notif_add.play();
                    this.music_notif_add.setVolume(0.1f);
                    return;
                }
            }

        }
    }

    @Override
    public void update(float dt) {
        this.handleInput();
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(this.cam.combined);

        this.manage_game.batch.begin();

        this.manage_game.sprite.draw(this.manage_game.batch);
        Unit u2;
        ArrayList<Integer> done = new ArrayList<Integer>();
        for (int i = 0; i < this.manage_game.al.size(); i++) {
            this.manage_game.unit = (Unit) this.manage_game.al.get(i);
            if (this.manage_game.unit.recruit >= 0) {
                this.manage_game.batch.draw(this.manage_game.blue_select, this.manage_game.unit.x, this.manage_game.unit.y);
                this.manage_game.batch.draw(this.manage_game.unit.img, this.manage_game.unit.x, this.manage_game.unit.y);
                for (int j = 0; j < this.manage_game.ad.size(); j++) {
                    u2 = (Unit) this.manage_game.ad.get(j);
                    if (u2.recruit >= 0 && this.manage_game.unit.x - u2.x < 4*32 && this.manage_game.unit.x - u2.x > -4*32 && this.manage_game.unit.y - u2.y < 4*32 && this.manage_game.unit.y - u2.y > -4*32) {
                        boolean test3 = true;
                        for (int k = 0; k < done.size(); k++) {
                            if (done.get(k) == j) {
                                test3 = false;
                            }
                        }
                        if (test3) {
                            this.manage_game.batch.draw(this.manage_game.red_select, u2.x, u2.y);
                            this.manage_game.batch.draw(u2.img, u2.x, u2.y);
                            done.add(j);
                        }
                    }
                }
            }
        }
        this.manage_game.batch.end();

        sb.begin();

        sb.draw(this.ui, 0, 0, Constants.VIEWPORT_WIDTH / 4, Constants.VIEWPORT_HEIGHT);
        sb.draw(this.exitButton, Constants.castleButtonCenterX, Constants.castleButtonSizeY, Constants.castleButtonSizeX, Constants.castleButtonSizeY);
        sb.draw(this.builderButton, Constants.castleButtonCenterX, Constants.castleButtonSizeY * 2, Constants.castleButtonSizeX, Constants.castleButtonSizeY);
        sb.draw(this.horsemanButton, Constants.castleButtonCenterX, Constants.castleButtonSizeY * 3, Constants.castleButtonSizeX, Constants.castleButtonSizeY);
        sb.draw(this.swordmanButton, Constants.castleButtonCenterX, Constants.castleButtonSizeY * 4, Constants.castleButtonSizeX, Constants.castleButtonSizeY);
        sb.draw(this.spearmanButton, Constants.castleButtonCenterX, Constants.castleButtonSizeY * 5, Constants.castleButtonSizeX, Constants.castleButtonSizeY);

    //        this.productText.draw(sb, this.productGlyph, 5, Constants.castleButtonSizeY * 9);

        sb.end();
    }

    @Override
    public void dispose() {
    }
}
