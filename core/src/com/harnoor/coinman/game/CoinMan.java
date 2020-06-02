package com.harnoor.coinman.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;
import java.util.Random;

public class CoinMan extends ApplicationAdapter {
	SpriteBatch batch;
	Texture background;//texture is used to add images
	Texture[] man ;//man array stores array of images for our character
	int manState=0;
	int pause=0;
	float gravity=0.2f;
	float velocity=0;
	int manY=0;

	ArrayList<Integer> coinXs =new ArrayList<Integer>();
	ArrayList<Integer> coinYs =new ArrayList<Integer>();
	Texture coin;
	int coinCount;
	Random random;
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

        manY=Gdx.graphics.getHeight()/2;
        coin=new Texture("coin.png");
        random=new Random();
	}

	public void makeCoin(){
		float height=random.nextFloat()*Gdx.graphics.getHeight();
		coinYs.add((int)height);
		coinXs.add(Gdx.graphics.getWidth());
	}

	@Override
	//runs constantly
	public void render () {
	batch.begin();
	batch.draw(background,0,0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
	    //0,0 marks the start of our background image(coordinate)
        // we set the height and width to take up the whole screen
		if(coinCount<100){
			coinCount++;
		}else{
			coinCount=0;
			makeCoin();
		}

		for(int i=0;i<coinXs.size();i++){
			batch.draw(coin,coinXs.get(i),coinYs.get(i));
			coinXs.set(i,coinXs.get(i)-4);
		}

		if(Gdx.input.justTouched()){
			velocity=-10;
		}
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

		velocity+=gravity;
		manY-=velocity;
		if(manY<=0){
			manY=0;
		}
       batch.draw(man[manState],Gdx.graphics.getWidth()/2-man[manState].getWidth()/2,manY);



	batch.end();
	}
	
	@Override
	//to start again, not used in our case
	public void dispose () {
		batch.dispose();
	}
}
