package com.mygdx.game.Client.Screen;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.mygdx.game.Client.Chetchik;
import com.mygdx.game.Client.MyGame;

import java.util.HashMap;

public class GameOverScreen extends ScreenAdapter {

    MyGame game;
    Table table;
    Label nameLabel;
    Label.LabelStyle style = new Label.LabelStyle();
    BitmapFont font = new BitmapFont();

    public GameOverScreen(MyGame game, HashMap<String , Chetchik> results) {
        style.font=font;
        this.game = game;
        table = new Table();
        table.setBounds(0,0, (float) Gdx.graphics.getWidth(), (float) Gdx.graphics.getHeight());
        results.keySet().forEach(key ->{
            nameLabel=new Label(results.get(key).getNick()+" - ",style);
            table.add(nameLabel);
            nameLabel=new Label(Byte.toString(results.get(key).getChet()),style);
            table.add(nameLabel);
            table.row();
        });
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(.25f, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.begin();
        table.draw(game.batch,1);
        game.batch.end();

    }

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
    }
}