package com.mygdx.game.Client.emitter;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.DelayedRemovalArray;
import com.badlogic.gdx.utils.Pool;

public class Emitter {
    private String owner;
    private final Vector2 position = new Vector2();
    private float speed = 200;
    private float rate = 10;
    private final Vector2 angle = new Vector2();
    private float distance = 200;
    private float size = 16;
    private float lastParticleEmit = 0;
    BodyDef bodyDef = new BodyDef();

    FixtureDef fixtureDef;
    Fixture fixture;
    Body body;
    World world;
    private final DelayedRemovalArray<Particle> particles = new DelayedRemovalArray<>();
    private final Pool<Particle> particlePool = new Pool<Particle>() {
        @Override
        protected Particle newObject() {
            return new Particle();
        }
    };
    public Emitter(World world,String owner){
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        this.world=world;
        this.owner=owner;
        PolygonShape polygonShape=new PolygonShape();
        polygonShape.setAsBox(8,8);
        fixtureDef = new FixtureDef();
        fixtureDef.shape=polygonShape;
        fixtureDef.filter.categoryBits = 1;
        fixtureDef.filter.maskBits = 1;
    }
    public void start(float delta,Body body){
        lastParticleEmit+=delta;
        if(lastParticleEmit<=1/rate){return;}
        lastParticleEmit=0;
        Particle particle = particlePool.obtain();
        this.body=body;
        fixture = this.body.createFixture(fixtureDef);
        fixture.setUserData("puly");
        particle.fill(this.body,owner,position,angle,size,speed,distance);
        particles.add(particle);
    }
    public Array<Body> act(float delta){
        Array<Body> remove =new Array<>();
        particles.begin();
      for(Particle particle:particles){
          particle.act(delta);
            if(particle.isFinished()){
                remove.add(particle.getBody());
              particles.removeValue(particle,true);
              particlePool.free(particle);
          }
      }
      particles.end();
      return remove;
    }

    public Vector2 getPosition() {
        return position;
    }
    public Vector2 getPovorot() {
        return angle;
    }
    public BodyDef getBodyDef(){
        return bodyDef;
    }

    public DelayedRemovalArray<Particle> getParticles() {
        return particles;
    }
}
