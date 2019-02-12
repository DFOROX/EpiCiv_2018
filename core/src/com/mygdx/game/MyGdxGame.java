package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.ArrayList;



public class MyGdxGame extends ApplicationAdapter {
	SpriteBatch batch;
    ArrayList al = new ArrayList();

    @Override
	public void create () {
        batch = new SpriteBatch();
        unit u;
        for (int i = 0; i < 5; i++)
            al.add((unit)new unit());
        for(int i = 0; i < al.size(); i++) {
            u = (unit) al.get(i);
            u.lancier(i * 100, i * 200);
        }
	}

	@Override
	public void render () {
        unit u;
        Gdx.gl.glClearColor(1, 2, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
        for(int i = 0; i < al.size(); i++) {
            u = (unit)al.get(i);
            batch.draw(u.img, u.x, u.y);
        }
		batch.end();
	}
	@Override
	public void dispose () {
        unit u;
        batch.dispose();
        for(int i = 0; i < al.size(); i++) {
            u = (unit)al.get(i);
            u.img.dispose();
        }
	}
}
