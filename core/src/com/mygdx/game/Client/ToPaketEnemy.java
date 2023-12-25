package com.mygdx.game.Client;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

public class ToPaketEnemy {
    DatagramPacket packet;


    public DatagramPacket ReturnEnemy(String uuid,float x,float y,byte live){
        byte[] a=new byte[48];
        a[0]=1;
        byte[] name = uuid.getBytes(StandardCharsets.UTF_8);
        byte[] xn=float2ByteArray(x);
        byte[] yn=float2ByteArray(y);
        System.arraycopy(name,0,a,1,name.length);
        System.arraycopy(xn,0,a,37,xn.length);
        System.arraycopy(yn,0,a,41,yn.length);
        a[46]=live;
        try {
            packet = new DatagramPacket(a,a.length, InetAddress.getByName("localhost"), 2233);
            //System.out.println(packet.getData());
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
        //System.out.println("Return"+packet);
        return packet;
    }
    public static byte [] float2ByteArray (float value)
    {
        return ByteBuffer.allocate(4).putFloat(value).array();
    }
}
