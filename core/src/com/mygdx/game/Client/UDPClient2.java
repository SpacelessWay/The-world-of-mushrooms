package com.mygdx.game.Client;

import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * @   Клиент UDP-соединения
 */
public class UDPClient2 implements Runnable {
    private Random random = new Random();
    private String uuid = UUID.randomUUID().toString();
    BlockingQueue<DatagramPacket> packetsF = new ArrayBlockingQueue<>(9);
    byte[] buffer = new byte[69]; // буфер
    DatagramPacket packet=new DatagramPacket(buffer, buffer.length);
    int kol;
    boolean run;
    public UDPClient2(int kol){
        run=true;
        this.kol=kol;}
    public void run() {
        try {
            init();
            byte[] buffer=new byte[69];
            buffer[0]=0;
            byte[] name = uuid.getBytes(StandardCharsets.UTF_8);
            System.arraycopy(name,0,buffer,1,name.length);
            buffer[38]= (byte) kol;
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length, Inet4Address.getByName("localhost"), 2233);
            System.out.println(uuid + ": Send:" + Arrays.toString(name));
            try {
                sendDate(packet);
            } catch (Exception e) {
                e.printStackTrace();
            }
            while (true){try {
                receive();
            } catch (Exception e) {
            }}
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public String getUuid(){return uuid;}
    //Принять
    public void receive() throws Exception {
        try {
            //System.out.println("ES");
            datagramSocket.receive(packet);
            //System.out.println("ES");
            byte[] bt = new byte[69];
            System.arraycopy(packet.getData(), 0, bt, 0, packet.getLength());
            //System.out.println("Prislo "+Arrays.toString(bt));
            DatagramPacket f=new DatagramPacket(bt, bt.length);
            packetsF.put(f);
            //DatagramPacket p=new DatagramPacket(bt,bt.length);
            //packets.add(p);
        } catch (Exception e) {
            throw e;
        }
    }
    //Отправить
    public void sendDate(DatagramPacket packet) {
        try {
            datagramSocket.send(packet);
            //System.out.println("&&&");
            //byte[] bt = new byte[packet.getLength()];
            //System.arraycopy(packet.getData(), 0, bt, 0, packet.getLength());
            //System.out.println("Cli otpr"+Arrays.toString(bt));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    DatagramSocket st;

    //Соединение
    public void init() throws SocketException {
        try {
            int a=random.nextInt(9999);
            //st=new DatagramSocket(a);
            //NetworkInterface nif = NetworkInterface.getByInetAddress(
                    //InetAddress.getByName("192.168.0.149"));
            //InetAddress inetAddress= InetAddress.getByName("192.168.0.1");
            datagramSocket = new DatagramSocket(a);

            //datagramSocket.bind(new InetSocketAddress("0.0.0.0",a));
            datagramSocket.setSoTimeout(10 * 1000);
            System.out.println("Клиент успешно запущен");
            System.out.println(a);
        } catch (Exception e) {
            datagramSocket = null;
            System.out.println("Клиент не запустился");
            e.printStackTrace();
        }
    }
    public DatagramPacket recPacketFriend() {
        //return this.packetFriend;//
        return packetsF.poll();
    }

    private DatagramSocket datagramSocket = null; // объект соединения
        /*public String getIp() throws Exception {
        DocFlavor.URL whatismyip = new DocFlavor.URL("http://checkip.amazonaws.com");
        BufferedReader in = null;
        try {
            in = new BufferedReader(new InputStreamReader(
                    //whatismyip.toString()));
            String ip = in.readLine();
            return ip;
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }*/
}






