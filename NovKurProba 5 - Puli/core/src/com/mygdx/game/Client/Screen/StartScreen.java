package com.mygdx.game.Client.Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;

import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.game.Client.Chetchik;
import com.mygdx.game.Client.MyGame;
import com.mygdx.game.Client.UDPClient2;


import java.util.ArrayList;
import java.util.HashMap;

public class StartScreen extends ScreenAdapter  {
    BitmapFont font = new BitmapFont();
    SpriteBatch batch;
    MyGame myGame;
    GameScreen game;
    UDPClient2 nov;
    TextField.TextFieldStyle style = new TextField.TextFieldStyle();

    TextField nick;
    TextField kol;
    Table table;
    String textNick;
    int textInt=0;
    Skin skin;
    private TextButton btnLogin;
    private Stage stage;
    Label text;


    public StartScreen(MyGame myGame) {
        this.myGame = myGame;


    }

    @Override
    public void show() {
        skin = new Skin(Gdx.files.classpath("uiskin.json"));
        batch = new SpriteBatch();
        btnLogin = new TextButton("Click", skin);
        stage = new Stage();
        btnLogin.addListener(new ClickListener() {
            public boolean touchDown(InputEvent e, float x, float y, int point, int button) {
                //System.out.println(nick.getText());
                textNick=nick.getText();
                textInt= Integer.parseInt(kol.getText());
                return false;
            }
        });
        skin.getFont("default-font").getData().setScale(2f);

        table = new Table();
        nick = new TextField("", skin);
        nick.setMaxLength(20);
        kol = new TextField("", skin);
        kol.setMaxLength(2);
        table.setBounds(0, 0, (float) Gdx.graphics.getWidth(), (float) Gdx.graphics.getHeight());
        text=new Label("Initilize...", skin);
        table.add(text).colspan(2).pad(10);
        table.row();
        text=new Label("Nick: ", skin);
        table.add(text);
        table.add(nick).width(200).pad(10);
        table.row();
        text=new Label("Int: ", skin);
        table.add(text);
        table.add(kol).width(200).pad(10);
        table.row();
        table.add(btnLogin).width(100).colspan(2).pad(10);

        stage.addActor(table);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(.25f, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.input.setInputProcessor(stage);
        batch.begin();
        // do other rendering ...
        batch.end();

        //Gdx.app.log("MyTextField", nick.getText());

        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
        if(textNick!=null && textInt!=0){
            dispose();
            nov = new UDPClient2(textInt);
            try {
                new Thread(nov).start();
            } catch (Exception e) {
                e.printStackTrace();
            }
            game = new GameScreen(myGame, nov,textNick,textInt);
            myGame.setScreen(game);
        }


    }

    @Override
    public void hide() {

    }
    @Override
    public void dispose() {
stage.dispose();
    }




}
