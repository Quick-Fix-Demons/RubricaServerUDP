/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rubricaserverudp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Alessio
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            int port = 1051;
            DatagramSocket socket = new DatagramSocket(port);
            byte[] ricevi = new byte[1024];
            DatagramPacket request = new DatagramPacket(ricevi, ricevi.length);
            
            while (true) {
                socket.receive(request);
                String[] ricevuto = new String(request.getData()).split(" - ");
                InetAddress clientAddress = request.getAddress();
                int clientPort = request.getPort();
                String risposta = null;
                switch(ricevuto[0]) {
                    case "0": /* Ricerca per nome */
                        break;
                    case "1": /* Ricerca per numero */
                        break;
                    case "2": /* Aggiunta alla rubrica */
                        break;
                    case "3": /* Modifica alla rubrica */
                        break;
                }
                DatagramPacket response = new DatagramPacket(risposta.getBytes(), risposta.getBytes().length, clientAddress, clientPort);
                socket.send(response);
            }
        } catch (SocketException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
