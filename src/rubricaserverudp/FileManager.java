/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rubricaserverudp;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

/**
 *
 * @author Quick Fix Demons
 */
public class FileManager {
    public void serializza(List<Contatto> contatti) throws IOException {
        // Serializzazione su file .bin
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("rubrica.bin"));
        oos.writeObject(contatti);
        oos.close();
    }
    
    public List<Contatto> deserializza() throws IOException, ClassNotFoundException {
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream("rubrica.bin"));
        List<Contatto> contatti= (List<Contatto>)ois.readObject();
        return contatti;
    }
}
