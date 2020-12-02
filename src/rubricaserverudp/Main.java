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
            List<Contatto> contatti;
            int PORT = 1051;

            DatagramSocket socket = new DatagramSocket(PORT);
            byte[] ricevi = new byte[1024];
            DatagramPacket request = new DatagramPacket(ricevi, ricevi.length);
            
            FileManager fm = new FileManager();
            
            try {
                contatti = fm.deserializza();
                for(int i = 0; i<contatti.size(); i++) {
                    System.out.println(contatti.get(i).getNomeContatto() + " - " + contatti.get(i).getNumero());
                }
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
                System.out.println("Ricevuta richiesta con codice: " + ricevuto[0]);
                if(ricevuto[0].equals("0")) {
                    for(int i = 0; i<contatti.size(); i++) {
                        if(ricevuto[1].equals(contatti.get(i).getNomeContatto())) {
                            risposta = contatti.get(i).getNomeContatto() + " - " + contatti.get(i).getNumero();
                            System.out.println(risposta);
                            break;
                        }
                    }
                    if(risposta == null) risposta = "Contatto non trovato.";
                }
                else if(ricevuto[0].equals("1")) {
                    for(int i = 0; i<contatti.size(); i++) {
                        if(ricevuto[1].equals(contatti.get(i).getNumero())) {
                            risposta = contatti.get(i).getNomeContatto() + " - " + contatti.get(i).getNumero();
                            break;
                        }
                    }
                    if(risposta == null) risposta = "Contatto non trovato.";
                }
                else if(ricevuto[0].equals("2")) {
                    contatti.add(new Contatto(ricevuto[1], ricevuto[2]));
                    risposta = "Contatto \"" + ricevuto[1] + "\" aggiunto con successo.";
                    System.out.println(risposta);
                }
                else if(ricevuto[0].equals("3")) {
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
                }
                else if(ricevuto[0].equals("4")) {
                    for(int i = 0; i<contatti.size(); i++) {
                        risposta += contatti.get(i).getNomeContatto() + " - " + contatti.get(i).getNumero() + "\n";
                    }
                    System.out.println(risposta);
                    if(risposta == null) risposta = "Nessun contatto non trovato.";
                }
                else {
                    risposta = "Opzione non valida.";
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
