package org.example.Server;

import java.net.*;
import java.util.ArrayDeque;
import java.util.Set;

public class Brocker2 {
    ArrayDeque<DatagramPacket> fromclients=new ArrayDeque<>();
    public Brocker2(){
    }
    public void broc_cli(DatagramPacket packet){
        fromclients.addLast(packet);
    }

    public void sendDate(DatagramPacket packet) {
        try {
            //System.out.println("Pered onprav "+packet.getSocketAddress());
            socket.send(packet);
            //System.out.println("brocker otpr");
        } catch (Exception e) {
            //System.out.println("Error ");
            e.printStackTrace();
        }
    }
    DatagramSocket socket;

    //private final InetSocketAddress socketAddress;

    {
        try {
            //socketAddress = new InetSocketAddress("0.0.0.0", 2234);
            socket = new DatagramSocket();
        } catch (SocketException e) {
            throw new RuntimeException(e);
        }
    }
    public synchronized void for_cli(Set<Integer> ports){
        for (DatagramPacket p : fromclients) {
            for(Integer i:ports){
                if(i!=p.getPort()){
                byte[] bt = new byte[p.getLength()];
                System.arraycopy(p.getData(), 0, bt, 0, p.getLength());
                //System.out.println("Packet for client "+p.getPort()+ Arrays.toString(bt));
                DatagramPacket packet = new DatagramPacket(bt, bt.length,p.getSocketAddress());
                packet.setPort(i);
                    sendDate(packet);}
            }
            //System.out.println("Sunx "+p);
            fromclients.remove(p);

        }
    }


}

