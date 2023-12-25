package com.mygdx.game.Client;

import com.badlogic.gdx.math.Vector2;
import java.net.DatagramPacket;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class RecieveFromServer {
    byte who;
    String uuid;
    byte nul;//прверка, что пакет не пустой
    byte fire;
    byte lives;
    String nick;
    public Vector2 Direction(DatagramPacket packet){
        Vector2 n =new Vector2(0,0);
        who=packet.getData()[0];
        nul=packet.getData()[1];
        byte[] name=Arrays.copyOfRange(packet.getData(), 1, 37);
        uuid = new String(name, StandardCharsets.UTF_8);
        float x= ByteBuffer.wrap(Arrays.copyOfRange(packet.getData(), 37, 41)).getFloat();
        float y= ByteBuffer.wrap(Arrays.copyOfRange(packet.getData(), 41, 45)).getFloat();
        n.set(x,y);
        fire=packet.getData()[45];
        lives=packet.getData()[46];
        if(who==0) {
            int s = packet.getData()[68];
            byte[] nick1 = Arrays.copyOfRange(packet.getData(), 48, 48 + s);
            nick = new String(nick1, StandardCharsets.UTF_8);
        }
        return n;
    }
    public byte WhoIs(){return who;}
    public String getUuid(){return uuid;}
    public byte Null(){return nul;}
    public byte Fire(){return fire;}
    public byte Lives(){return lives;}
    public String getNick() {return nick;
    }

}
