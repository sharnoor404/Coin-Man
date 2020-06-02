package com.harnoor.coinman.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;

import java.util.ArrayList;
import java.util.Random;

public class CoinMan extends ApplicationAdapter {
	SpriteBatch batch;
	Texture background;//texture is used to add images
	Texture[] man ;//man array stores array of images for our character
	Texture dizzyState;
	int manState=0;
	int pause=0;
	float gravity=0.2f;
	float velocity=0;
	int manY=0;
	int Score=0;
	int gameState=0;
	BitmapFont font;
	Rectangle manRectangle;
	ArrayList<Integer> coinXs =new ArrayList<Integer>();
	ArrayList<Integer> coinYs =new ArrayList<Integer>();
	Texture coin;
	int coinCount;

	ArrayList<Integer> bombXs =new ArrayList<Integer>();
	ArrayList<Integer> bombYs =new ArrayList<Integer>();
	Texture bomb;
	int bombCount;

	ArrayList<Rectangle> coinRectangle =new ArrayList<Rectangle>();
	ArrayList<Rectangle> bombRectangle =new ArrayList<Rectangle>();

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

        dizzyState=new Texture("dizzy-1.png");

        manY=Gdx.graphics.getHeight()/2;
        coin=new Texture("coin.png");
		bomb=new Texture("bomb.png");
        random=new Random();
        font=new BitmapFont();
        font.setColor(Color.WHITE);
        font.getData().setScale(8);
	}



	public void makeCoin(){
		float height=random.nextFloat()*Gdx.graphics.getHeight();
		coinYs.add((int)height);
		coinXs.add(Gdx.graphics.getWidth());
	}

	public void makeBomb(){
		float height=random.nextFloat()*Gdx.graphics.getHeight();
		bombYs.add((int)height);
		bombXs.add(Gdx.graphics.getWidth());
	}



	@Override
	//runs constantly
	public void render () {
	batch.begin();
	batch.draw(background,0,0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
	    //0,0 marks the start of our background image(coordinate)
        // we set the height and width to take up the whole screen

		if(gameState==1){
			//GAME LIVE

			//BOMBS
			if(bombCount<250){
				bombCount++;
			}else{
				bombCount=0;
				makeBomb();
			}
			bombRectangle.clear();
			for(int i=0;i<bombXs.size();i++){
				batch.draw(bomb,bombXs.get(i),bombYs.get(i));
				bombXs.set(i,bombXs.get(i)-8);
				bombRectangle.add(new Rectangle(bombXs.get(i),bombYs.get(i),bomb.getWidth(),bomb.getHeight()));
			}

			//COINS
			if(coinCount<100){
				coinCount++;
			}else{
				coinCount=0;
				makeCoin();
			}
			coinRectangle.clear();
			for(int i=0;i<coinXs.size();i++){
				batch.draw(coin,coinXs.get(i),coinYs.get(i));
				coinXs.set(i,coinXs.get(i)-4);
				coinRectangle.add(new Rectangle(coinXs.get(i),coinYs.get(i),coin.getWidth(),coin.getHeight()));
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

		}else if(gameState==0){
			//WAITING TO START
			if(Gdx.input.justTouched()){
				gameState=1;
			}

		}else if(gameState==2){
			//GAME OVER
			if(Gdx.input.justTouched()){
				gameState=1;
				manY=Gdx.graphics.getHeight()/2;
				Score=0;
				velocity=0;
				coinXs.clear();
				coinYs.clear();
				coinRectangle.clear();
				coinCount=0;
				bombXs.clear();
				bombYs.clear();
				bombRectangle.clear();
				bombCount=0;
			}
		}

		if(gameState==2){
			batch.draw(dizzyState,Gdx.graphics.getWidth()/2-man[manState].getWidth()/2,manY);
		}else{
			batch.draw(man[manState],Gdx.graphics.getWidth()/2-man[manState].getWidth()/2,manY);
		}

		manRectangle=new Rectangle(Gdx.graphics.getWidth()/2-man[manState].getWidth()/2,manY,man[manState].getWidth(),man[manState].getHeight());

		for(int i=0;i<coinRectangle.size();i++){
			if(Intersector.overlaps(manRectangle,coinRectangle.get(i))){
					Score++;

					coinXs.remove(i);
					coinRectangle.remove(i);
					coinYs.remove(i);
					break;
			}

		}
		for(int i=0;i<bombRectangle.size();i++){
			if(Intersector.overlaps(manRectangle,bombRectangle.get(i))){
				Gdx.app.log("Bomb!!","Collision!!");
				gameState=2;
			}

		}

		font.draw(batch,String.valueOf(Score),100,200);
	batch.end();
	}




	@Override
	//to start again, not used in our case
	public void dispose () {
		batch.dispose();
	}
}
