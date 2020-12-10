/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rubricaserverudp;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.lang.reflect.Type;

/**
 *
 * @author Quick Fix Demons
 */
public class FileManager {
    public void serializza(List<Contatto> contatti) throws IOException {
        // Serializzazione su file .json
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        //System.out.println(gson.toJson(contatti));
        FileWriter fw;
        try {
            fw = new FileWriter("rubrica.json");
            gson.toJson(contatti, fw);
            fw.close();
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public List<Contatto> deserializza() throws IOException, ClassNotFoundException {
        Gson gson = new Gson();

        // Da file .json a lista
        Type userListType = new TypeToken<ArrayList<Contatto>>(){}.getType();
 
        ArrayList<Contatto> contatti = gson.fromJson(new FileReader("rubrica.json"), userListType);

        return contatti;
    }
}
