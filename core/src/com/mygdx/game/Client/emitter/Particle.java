package com.mygdx.game.Client.emitter;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.Pool;

public class Particle implements Pool.Poolable {
    private String owner;
    private float size;
    private float speed;
    private float distance;
    private final Vector2 position = new Vector2();
    private final Vector2 startPoint = new Vector2();
    private final Vector2 nextStepPoint = new Vector2(1,1);
    private float distance2;
    private Body body;

    public void act(float delta){
        float steplenght = speed*delta;
        nextStepPoint.setLength(steplenght);
        position.add(nextStepPoint);
    }
    public void fill(Body body,String owner,Vector2 position,Vector2 angle,float size,float speed,float distance){
        this.body=body;
        this.owner=owner;
        this.position.set(position);
        this.body.setTransform(this.position.x,this.position.y,0);
        this.body.setUserData(owner);
        this.nextStepPoint.set(angle);
        this.startPoint.set(position);
        this.size=size;
        this.speed=speed;
        this.distance2=distance*distance;
    }
    @Override
    public void reset(){
        this.owner="";
        this.position.set(0,0);
        this.nextStepPoint.set(1,1);
        this.startPoint.set(0,0);
        this.size=0;
        this.speed=0;
        this.distance=0;
    }

    public boolean isFinished() {
        return position.dst2(startPoint)>=distance2;
    }

    public Vector2 getPosition() {
        return position;
    }
    public Body getBody(){return body;}
}
