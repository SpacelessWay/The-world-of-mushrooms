package com.mygdx.game.Client;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.math.Vector2;

public class Klaviatura extends InputAdapter {//Отвечает за связь с клавиатурой
    private boolean RIGHT;//Стрелки клавиатуры
    private boolean LEFT;
    private boolean UP;
    private boolean DOWN;
    private boolean PROBEL;
    private final Vector2 direction = new Vector2();//Создается для экономии ресурсов

    @Override
    public boolean keyDown(int keycode) {
        if(keycode == Input.Keys.RIGHT) RIGHT=true;//Игрок двигается при нажатие на клавишу
        if(keycode == Input.Keys.LEFT) LEFT=true;
        if(keycode == Input.Keys.UP) UP=true;
        if(keycode == Input.Keys.DOWN) DOWN=true;
        if(keycode == Input.Keys.SPACE) PROBEL=true;
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        if(keycode == Input.Keys.RIGHT) RIGHT=false;//Игрок перестает двигаться
        if(keycode == Input.Keys.LEFT) LEFT=false;
        if(keycode == Input.Keys.UP) UP=false;
        if(keycode == Input.Keys.DOWN) DOWN=false;
        if(keycode == Input.Keys.SPACE) PROBEL=false;
        return false;
    }
    public Vector2 getDirection(){//возвращает направление джвижения
        direction.set(0,0);//обнуляем, чтобы координаты неперемешивались
        if (RIGHT){direction.add(5,0);}//Направление по х и у
        if (LEFT){direction.add(-5,0);}
        if (UP){direction.add(0,5);}
        if (DOWN){direction.add(0,-5);}
        return direction;
    }
    public boolean getFire(){
        return PROBEL;
    }
}
