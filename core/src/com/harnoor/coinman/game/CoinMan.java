package com.harnoor.coinman.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class CoinMan extends ApplicationAdapter {
	SpriteBatch batch;
	Texture background;
	Texture[] man ;
	//man array stores array of images for our character
	//texture is used to add images
	@Override
	//runs once when the game starts
	public void create () {
		batch = new SpriteBatch();
		background=new Texture("bg.png");

	}

	@Override
	//runs constantly
	public void render () {
	batch.begin();
	batch.draw(background,0,0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
	    //0,0 marks the start of our background image(coordinate)
        // we set the height and width to take up the whole screen
        man=new Texture[4];
        man[0]=new Texture("frame-1.png");
        man[1]=new Texture("frame-2.png");
        man[2]=new Texture("frame-3.png");
        man[3]=new Texture("frame-4.png");



	batch.end();
	}
	
	@Override
	//to start again, not used in our case
	public void dispose () {
		batch.dispose();
	}
}
