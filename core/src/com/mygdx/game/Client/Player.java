package com.mygdx.game.Client;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.game.Client.emitter.Emitter;

public class Player {
    private final float size = 64;
    private final float halfsize = size/2;
    private Vector2 position = new Vector2();//Заменяет координаты x и y
    private final Texture texture;
    private final TextureRegion textureRegion;
    private final Vector2 angle = new Vector2();
    private final Vector2 anglePli = new Vector2();
    BodyDef bodyDef = new BodyDef();
    FixtureDef fixtureDef;
    Fixture fixture;
    Body body;
    World world;
    public Emitter emitter;

    byte fire;
    String uuid;
    byte lives;
    BitmapFont font = new BitmapFont();
    String Nick;
    Texture died;

    public Player(World w,String uuid,byte lives,float x,float y) {
        this(w,uuid,lives,x,y,"p3.png");
    }
    public Player(World w,String uuid,byte lives,float x,float y,String Nametexture) {
        this.uuid=uuid;
        this.lives=lives;
        this.world=w;
        Nick=new String("");
        emitter=new Emitter(world,this.uuid);
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        texture = new Texture(Nametexture);//картинка аватара
        died=new Texture("Died.png");
        textureRegion=new TextureRegion(texture);

        PolygonShape polygonShape=new PolygonShape();
        polygonShape.setAsBox(30,30);
        fixtureDef = new FixtureDef();
        fixtureDef.shape=polygonShape;
        fixtureDef.filter.categoryBits = 1;
        fixtureDef.filter.maskBits = 1;

        position.set(x,y);

    }
    public void render(Batch batch){
        if(lives>(byte) 0){

        batch.draw(textureRegion,
                body.getPosition().x-32,
                body.getPosition().y-32,
                halfsize,
                halfsize,
                size,
                size,
                1,
                1,
                angle.angleDeg()
        );
            font.draw(batch,Nick,body.getPosition().x-32,body.getPosition().y+46);
            font.draw(batch,"Lives: "+lives,0,450);
            }
        else{
            batch.draw(textureRegion,
                position.x,
                position.y,
                halfsize,
                halfsize,
                size,
                size,
                1,
                1,
                angle.angleDeg()
        );
            font.draw(batch,"Lives: "+lives,0,450);}
    }
    public void Body(Body body,String str){
        this.body=body;
        fixture = this.body.createFixture(fixtureDef);
        fixture.setUserData(str);
        body.setUserData(uuid);
    }

    public void dispose(){
        world.destroyBody(body);
    }
    public void move(Vector2 direction){
        position.add(direction);//движение
        body.setTransform(position.x+32,position.y+32,0);
        if(lives<0){
            textureRegion.setTexture(died);
        }
        Position();
    }
    public void povorot(Vector2 direction){
        angle.set(direction);
        if(direction.x!=0   || direction.y!=0) {
            anglePli.set(direction);
        }}
    public Vector2 Povorot(){
        return anglePli;
    }
    public byte getFire(){
        return fire;
    }
    public void moveFriend(Vector2 direction,byte fire){
        angle.set(direction.x-this.position.x,direction.y-this.position.y);
        povorot(angle);
        if(lives==0){
            textureRegion.setTexture(died);
        }
       this.position=direction;//движение
        this.fire=fire;
        body.setTransform(position.x+32,position.y+32,0);
        Position();
    }
    public Vector2 Position(){
        Vector2 position=this.position;
        return position;
    }
    public BodyDef getBodyDef(){
        return bodyDef;
    }
    public void moveEnemy(Vector2 positionPlayer){
        if (positionPlayer!=position){
            float x=positionPlayer.x-position.x;
            float y=positionPlayer.y-position.y;
            if(x>0){
                x=1;
            } else {
                x=-1;
            }
            if(y>0){
                y=1;
            } else {
                y=-1;
            }
            position.add(x,y);
            body.setTransform(position.x+32,position.y+32,0);
        }
    }
    public byte getLives() {
        return lives;
    }

    public void Lives() {
        lives-=1;
    }
    public void Nick(String nick) {
        this.Nick=new String(nick);
    }
}
