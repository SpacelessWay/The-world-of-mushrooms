package com.mygdx.game.Client;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Timer;
import com.mygdx.game.Client.Screen.GameOverScreen;
import com.mygdx.game.Client.Screen.GameScreen;
import com.mygdx.game.Client.Screen.StartScreen;

import java.net.DatagramPacket;


public class MyGame extends Game {
	public SpriteBatch batch;
	private Klaviatura klaviatura = new Klaviatura();
	StartScreen start;


	@Override
	public void create () {//инициализация при запуске
		batch = new SpriteBatch();
		/*nov=new UDPClient2();
		try {
			new Thread(nov).start();
		} catch (Exception e) {
			e.printStackTrace();
		}
		/*while (nov.otvet()!=1){
			System.out.println("Wait...");
			try {
				TimeUnit.SECONDS.sleep(1);
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
		}*/
		start=new StartScreen(this);
		this.setScreen(start);
		//game=new GameScreen(this,nov);
		//this.setScreen(game);







	}

	@Override
	public void render () {//отрисовка кадров
		super.render();
			}

	@Override
	public void dispose () {//при закрытие освобождает ресурсы
		batch.dispose();

	}




}
