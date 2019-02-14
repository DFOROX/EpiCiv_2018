package eu.epiciv.menu.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

import eu.epiciv.gamemanager.Main_game;
import eu.epiciv.menu.managers.GameStateManager;
import eu.epiciv.menu.utils.Constants;
import eu.epiciv.gamemanager.Main_game;

public class MainMenu extends GameState{

    private Texture background, newGameButton, exitButton;
    private BitmapFont gameTitleText;

    private FreeTypeFontGenerator generator;
    private FreeTypeFontGenerator.FreeTypeFontParameter parameter;
    public Main_game scroll;
    private float delta;

    private GlyphLayout gameTitleGlyph;
    private Music music = Gdx.audio.newMusic(Gdx.files.internal("music1.mp3"));
    private int xTouch = 0;
    private int yTouch = 0;

    public MainMenu(GameStateManager gsm) {
        super (gsm);
        this.background = new Texture("home/background.png");
        this.newGameButton = new Texture("buttons/newGameButton.png");
        this.exitButton = new Texture("buttons/exitButton.png");

        this.generator = new FreeTypeFontGenerator(Gdx.files.internal("font/titleFont.ttf"));
        this.parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();

        this.parameter.size = Gdx.graphics.getHeight() / 4;
        this.gameTitleText = this.generator.generateFont(this.parameter);
        this.gameTitleGlyph = new GlyphLayout();

        this.gameTitleGlyph.setText(this.gameTitleText, Constants.GAME_TITLE);
        this.cam.setToOrtho(false, Constants.VIEWPORT_WIDTH, Constants.VIEWPORT_HEIGHT);

    }

    @Override
    protected void handleInput()
    {
        if (Gdx.input.justTouched()) {
            this.xTouch = Gdx.input.getX();
            this.yTouch= Gdx.input.getY();
            if (this.xTouch > Constants.centerX && this.xTouch < Constants.centerX + Constants.buttonSizeX && this.yTouch > Constants.buttonSizeY * 5 && this.yTouch < Constants.buttonSizeY * 6) {
                this.music.stop();
                System.exit(0);
            }
            if (this.xTouch > Constants.centerX && this.xTouch < Constants.centerX + Constants.buttonSizeX && this.yTouch > Constants.buttonSizeY * 3 && this.yTouch < Constants.buttonSizeY * 4) {
                this.music.stop();
                this.gsm.setGameStates(new Main_game(this.gsm));
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

        sb.begin();
        sb.draw(this.background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        sb.draw(this.exitButton, Constants.centerX, Constants.buttonSizeY * 2, Constants.buttonSizeX, Constants.buttonSizeY);
        sb.draw(this.newGameButton, Constants.centerX, Constants.buttonSizeY * 4, Constants.buttonSizeX, Constants.buttonSizeY);

        this.gameTitleText.draw(sb, this.gameTitleGlyph, Constants.VIEWPORT_WIDTH / 2 - this.gameTitleGlyph.width / 2,  Constants.buttonSizeY * 7.75f);

        sb.end();
    }

    @Override
    public void dispose() {
        this.background.dispose();
        this.newGameButton.dispose();
        this.exitButton.dispose();
        this.gameTitleText.dispose();
        this.generator.dispose();
    }
}