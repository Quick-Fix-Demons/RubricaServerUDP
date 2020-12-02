/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rubricaserverudp;

import java.io.Serializable;

/**
 *
 * @author Quick Fix Demons
 */
public class Contatto implements Serializable {
    private String nomeContatto;
    private String numero;

    public Contatto(String nomeContatto, String numero) {
        this.nomeContatto = nomeContatto;
        this.numero = numero;
    }

    public String getNomeContatto() {
        return nomeContatto;
    }

    public void setNomeContatto(String nomeContatto) {
        this.nomeContatto = nomeContatto;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }
    
}
