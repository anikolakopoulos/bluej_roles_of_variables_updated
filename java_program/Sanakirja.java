package java_program;

import java.util.*;

/* Sanakirja-luokan metodit:
etsi(String)
lisaa(String, String)
poista(String)
tulosta() - tulostaa
 */

class Sanakirja
{
    private Hashtable sanasto; //%%sanasto%%container%%

    Sanakirja() {
        sanasto = new Hashtable(); 
    }

    Sanakirja(int koko) {
        sanasto = new Hashtable(koko);
    }

    /* Metodilla lisδtδδn sanakirjaan alkio */
    public void lisaa(String eng, String fin) {
        sanasto.put(eng, fin);
    }

    /* Metodilla Poistetaan sanakirjasta alkio */
    public String poista(String haettava) {
        String poistettu = "";
        poistettu =(String)sanasto.remove(haettava);
        return poistettu;
    } 

    public String etsi(String hakusana) {
        String haettava = (String)sanasto.get(hakusana);
        return haettava;
    }

    public void tulosta() {
        for (Enumeration at=sanasto.keys(); at.hasMoreElements(); ) {
            String avain = (String) at.nextElement(); 
            System.out.println(avain + ":" + sanasto.get(avain));
        }
    }
}