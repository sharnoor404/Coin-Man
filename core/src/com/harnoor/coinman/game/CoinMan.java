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
	int manState=0;
	int pause=0;
	@Override
	//runs once when the game starts
	public void create () {
		batch = new SpriteBatch();
		background=new Texture("bg.png");
        man=new Texture[4];
        man[0]=new Texture("frame-1.png");
        man[1]=new Texture("frame-2.png");
        man[2]=new Texture("frame-3.png");
        man[3]=new Texture("frame-4.png");
	}

	@Override
	//runs constantly
	public void render () {
	batch.begin();
	batch.draw(background,0,0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
	    //0,0 marks the start of our background image(coordinate)
        // we set the height and width to take up the whole screen
		if(pause<8){
			pause++;
		}else{
			pause=0;
			if(manState<3){
				manState++;
			}else{
				manState=0;
			}
		}

       batch.draw(man[manState],Gdx.graphics.getWidth()/2-man[manState].getWidth()/2,Gdx.graphics.getHeight()/2);



	batch.end();
	}
	
	@Override
	//to start again, not used in our case
	public void dispose () {
		batch.dispose();
	}
}
