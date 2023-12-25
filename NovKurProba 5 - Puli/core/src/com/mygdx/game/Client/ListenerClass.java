package com.mygdx.game.Client;

import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;


public class ListenerClass implements ContactListener {
    Array<Body> died =new Array<>();
    World world;
    public ListenerClass(World world){
        super();
        this.world = world;
    }

    @Override
    public void beginContact(Contact contact) {
        Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();
        //System.out.println(fixtureA.getUserData()+" "+fixtureB.getUserData());
        if(fixtureA.getUserData()!=null && fixtureB.getUserData()!=null){
            if(fixtureA.getUserData().equals("puly") && fixtureB.getUserData().equals("enemy") || fixtureA.getUserData().equals("enemy") && fixtureB.getUserData().equals("puly")){
                if(fixtureA.getUserData().equals("enemy")){
                    fixtureA.getBody().setUserData(fixtureA.getBody().getUserData()+" "+fixtureB.getBody().getUserData());
                    //Gdx.app.log("aray ", (String) fixtureA.getBody().getUserData());
                    died.add(fixtureA.getBody());
                }
                else{fixtureB.getBody().setUserData(fixtureB.getBody().getUserData()+" "+fixtureA.getBody().getUserData());
                    //Gdx.app.log("aray ", (String) fixtureB.getBody().getUserData());
                    died.add(fixtureB.getBody());
                }
            } else if (fixtureA.getUserData().equals("enemy") && fixtureB.getUserData().equals("enemy")) {

            }
            else if (fixtureA.getUserData().equals("player") && fixtureB.getUserData().equals("enemy") || fixtureA.getUserData().equals("enemy") && fixtureB.getUserData().equals("player")) {
                if(fixtureA.getUserData().equals("player")){
                    died.add(fixtureA.getBody());

                }
                else{died.add(fixtureB.getBody());
                }
            }
            else{
            }}



    }

    @Override
    public void endContact(Contact contact) {
    }
    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
    }
    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {
        }
    public Array<Body> getDied(){return died;
        }

}