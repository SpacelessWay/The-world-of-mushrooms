package org.example.Server;


import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.util.HashSet;
import java.util.Set;

public class UDPServer2 {

    Set<Integer> ports = new HashSet<Integer>();
    static Brocker2 a=new Brocker2();
    public UDPServer2() {
        try {
            init();
            byte [] buffer = new byte [69]; // буфер
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
            try {
            receive(packet);}
            catch (Exception ignored) {
            }
            int z=packet.getData()[38];
            while (ports.size()<z){
                try {
                    receive(packet);
                    ports.add(packet.getPort());
                    send();
                } catch (Exception ignored) {
                }}
            while(true){
                try {
                    receive(packet);
                    ports.add(packet.getPort());

                    send();

                } catch (Exception ignored) {
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * Получать пакеты данных, этот метод вызовет блокировку потоков
     */
    public static DatagramPacket receive(DatagramPacket packet) throws Exception {
        try {
            datagramSocket.receive(packet);
            System.out.println("Port "+packet.getPort()+packet.getSocketAddress());
            a.broc_cli(packet);
            System.out.println("Yes");
            return packet;
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * Начальное соединение
     * @throws SocketException
     */
    public static void init(){
        try {
            socketAddress = new InetSocketAddress("localhost", 2233);
            datagramSocket = new DatagramSocket(socketAddress);
            datagramSocket.setSoTimeout(5 * 1000);
            System.out.println ("Сервер запущен");
        } catch (Exception e) {
            datagramSocket = null;
            System.out.println ("Не удалось запустить сервер");
            e.printStackTrace();
        }
    }
    private static InetSocketAddress socketAddress = null; // адрес прослушивания службы
    private static DatagramSocket datagramSocket = null; // объект соединения
    /**
     * Отправить ответный пакет запрашивающей стороне
     */
    public void send() {
        try {
            System.out.println("response");
            a.for_cli(ports);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


