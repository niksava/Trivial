package ProjecteTrivial;

import java.io.Serializable;

public class Usuari implements Serializable {
    private String nom;
    private int puntuacioTotal; // Total de respostes correctes
    private int partidesGuanyades;
    private int partidesJugades;

    public Usuari(String nom) {
        this.nom = nom;
        this.puntuacioTotal = 0;
        this.partidesGuanyades = 0;
        this.partidesJugades = 0;
    }

    // Getters i setters
    public String getNom() { return nom; }
    public int getPuntuacioTotal() { return puntuacioTotal; }
    public int getPartidesGuanyades() { return partidesGuanyades; }
    public int getPartidesJugades() { return partidesJugades; }

    public void incrementarPuntuacio(int punts) { this.puntuacioTotal += punts; }
    public void incrementarPartidesGuanyades() { this.partidesGuanyades++; }
    public void incrementarPartidesJugades() { this.partidesJugades++; }
}