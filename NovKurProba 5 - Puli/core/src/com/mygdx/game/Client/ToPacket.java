package com.mygdx.game.Client;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

public class ToPacket {
    DatagramPacket packet;
    public DatagramPacket Return(String uuid,float x,float y,boolean fire,byte live,String nick){
        byte[] a=new byte[69];
        a[0]=0;
        byte[] name = uuid.getBytes(StandardCharsets.UTF_8);
        byte[] xn=float2ByteArray(x);
        byte[] yn=float2ByteArray(y);
        System.arraycopy(name,0,a,1,name.length);
        System.arraycopy(xn,0,a,37,xn.length);
        System.arraycopy(yn,0,a,41,yn.length);
        byte vOut = (byte)0;
        if(fire==true){vOut=(byte)1;}
        a[45]=vOut;
        a[46]=live;
        a[47]=0;
        byte[] ni = nick.getBytes(StandardCharsets.UTF_8);
        System.arraycopy(ni,0,a,48,ni.length);
        a[68]=(byte) ni.length;
        //System.out.println(nick+ni.length);
        //System.out.println(Arrays.toString(a));

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
