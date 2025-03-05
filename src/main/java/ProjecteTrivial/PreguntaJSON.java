package ProjecteTrivial;

public class PreguntaJSON {
    private String pregunta;
    private String[] respostes; // Canviem "opcions" per "respostes" perquè coincideixi amb el JSON
    private int correcta;       // Índex de la resposta correcta

    // Getters
    public String getPregunta() {
        return pregunta;
    }

    public String[] getRespostes() {
        return respostes;
    }

    public int getCorrecta() {
        return correcta;
    }

    // Converteix a l'objecte Pregunta
    public Pregunta toPregunta() {
        return new Pregunta(pregunta, respostes, respostes[correcta]);
    }
}