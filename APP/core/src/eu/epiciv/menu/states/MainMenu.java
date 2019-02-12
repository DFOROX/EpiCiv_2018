package eu.epiciv.menu.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeType;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

import eu.epiciv.menu.managers.GameStateManager;
import eu.epiciv.menu.utils.Constants;

public class MainMenu extends GameState{

    private Texture background, newGameButton, exitButton;
    private BitmapFont gameTitleText;

    private FreeTypeFontGenerator generator;
    private FreeTypeFontGenerator.FreeTypeFontParameter parameter;

    private GlyphLayout gameTitleGlyph;

    private int xTouch = 0;
    private int yTouch = 0;
    private int xImg = 0;
    private int yImg = 0;

    public MainMenu(GameStateManager gsm) {
        super (gsm);

        this.background = new Texture("home/background.png");
        this.newGameButton = new Texture("buttons/newGameButton.png");
        this.exitButton = new Texture("buttons/exitButton.png");

        this.generator = new FreeTypeFontGenerator(Gdx.files.internal("font/titleFont.ttf"));
        this.parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();

        this.parameter.size = 92;
        this.gameTitleText = this.generator.generateFont(this.parameter);
        this.gameTitleGlyph = new GlyphLayout();

        this.gameTitleGlyph.setText(this.gameTitleText, Constants.GAME_TITLE);

        this.cam.setToOrtho(false, Constants.VIEWPORT_WIDTH, Constants.VIEWPORT_HEIGHT);
    }

    @Override
    protected void handleInput()
    {
    }

    @Override
    public void update(float dt) {

    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(this.cam.combined);

        sb.begin();
        sb.draw(this.background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        sb.draw(this.exitButton, Gdx.graphics.getWidth() / 2 - this.exitButton.getWidth() / 2, Gdx.graphics.getHeight() / 3);
        sb.draw(this.newGameButton, Gdx.graphics.getWidth() / 2 - this.newGameButton.getWidth() / 2, Gdx.graphics.getHeight() / 3 * 2);

        this.gameTitleText.draw(sb, this.gameTitleGlyph, Constants.VIEWPORT_WIDTH / 2 - this.gameTitleGlyph.width / 2, Constants.VIEWPORT_HEIGHT / 2 + this.gameTitleGlyph.height / 2 + 12);

        sb.end();


        if (Gdx.input.justTouched()) {
            this.xTouch = Gdx.input.getX();
            this.yTouch= Gdx.input.getY();

            if (this.xTouch > Gdx.graphics.getWidth() / 2 - this.exitButton.getWidth() / 2 && this.xTouch < (Gdx.graphics.getWidth() / 2 - this.exitButton.getWidth() / 2) + this.exitButton.getWidth() && this.yTouch < Gdx.graphics.getHeight() / 3 * 2 && this.yTouch > Gdx.graphics.getHeight() / 3  * 2 - this.exitButton.getHeight()) {
                System.exit(0);
            }

            if (this.xTouch > Gdx.graphics.getWidth() / 2 - this.newGameButton.getWidth() / 2 && this.xTouch < (Gdx.graphics.getWidth() / 2 - this.newGameButton.getWidth() / 2) + this.newGameButton.getWidth() && this.yTouch < Gdx.graphics.getHeight() / 3 && this.yTouch > Gdx.graphics.getHeight() / 3 - this.newGameButton.getHeight()) {

            }

        }
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