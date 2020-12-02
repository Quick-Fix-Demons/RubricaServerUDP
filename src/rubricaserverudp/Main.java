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
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Quick Fix Demons
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
            
            FileManager fm = new FileManager();
            List<Contatto> contatti;
            try {
                contatti = fm.deserializza();
            }
            catch (IOException | ClassNotFoundException e) {
                System.out.println("Primo avvio.");
                contatti = new ArrayList<>();
            }
            
            while (true) {
                socket.receive(request);
                String[] ricevuto = new String(request.getData()).split(" - ");
                InetAddress clientAddress = request.getAddress();
                int clientPort = request.getPort();
                String risposta = null;
                switch(ricevuto[0]) {
                    case "0": /* Ricerca per nome */
                        for(int i = 0; i<contatti.size(); i++) {
                            if(ricevuto[1].equals(contatti.get(i).getNomeContatto())) {
                                risposta = contatti.get(i).getNomeContatto() + " - " + contatti.get(i).getNumero();
                                break;
                            }
                        }
                        if(risposta == null) risposta = "Contatto non trovato.";
                        break;
                    case "1": /* Ricerca per numero */
                        for(int i = 0; i<contatti.size(); i++) {
                            if(ricevuto[1].equals(contatti.get(i).getNumero())) {
                                risposta = contatti.get(i).getNomeContatto() + " - " + contatti.get(i).getNumero();
                                break;
                            }
                        }
                        if(risposta == null) risposta = "Contatto non trovato.";
                        break;
                    case "2": /* Aggiunta alla rubrica */
                        contatti.add(new Contatto(ricevuto[1], ricevuto[2]));
                        System.out.println("Contatto \"" + ricevuto[1] + "\" aggiunto con successo.");
                        break;
                    case "3": /* Modifica alla rubrica */
                        for(int i = 0; i<contatti.size(); i++) {
                            if(ricevuto[2].equals(contatti.get(i).getNomeContatto())) {
                                Contatto old = contatti.get(i);
                                contatti.remove(i);
                                if(ricevuto[1].equals("0")) {
                                    contatti.add(new Contatto(ricevuto[3], old.getNumero()));
                                    risposta = "Contatto modificato con successo.";
                                }
                                else if(ricevuto[1].equals("1")) {
                                    contatti.add(new Contatto(old.getNomeContatto(), ricevuto[3]));
                                    risposta = "Contatto modificato con successo.";
                                }
                                else if(risposta == null) risposta = "Contatto non trovato.";
                                break;
                            }
                        }
                        break;
                }
                DatagramPacket response = new DatagramPacket(risposta.getBytes(), risposta.getBytes().length, clientAddress, clientPort);
                socket.send(response);
                try {
                    fm.serializza(contatti);
                }
                catch(IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (SocketException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
