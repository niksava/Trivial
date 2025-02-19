package ProjecteTrivial;

/**
 * Aquesta classe modela una pregunta amb 4 opcions i la resposta correcta.
 */
public class Pregunta {
    private String pregunta;
    private String[] opcions;
    private String respostaCorrecta;

    /**
     * Constructor de la classe Pregunta.
     *
     * @param pregunta         El text de la pregunta.
     * @param opcions          Array de 4 opcions de resposta.
     * @param respostaCorrecta La resposta correcta.
     */
    public Pregunta(String pregunta, String[] opcions, String respostaCorrecta) {
        this.pregunta = pregunta;
        this.opcions = opcions;
        this.respostaCorrecta = respostaCorrecta;
    }

    public String getPregunta() {
        return pregunta;
    }

    public String[] getOpcions() {
        return opcions;
    }

    public String getRespostaCorrecta() {
        return respostaCorrecta;
    }
}
