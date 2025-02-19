package ProjecteTrivial;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Aquesta classe implementa la lògica de la partida del joc Trivial.
 * Es mostra cada pregunta, es fa rotar el torn entre jugadors i es sumen els punts.
 */
public class GameForm extends JFrame {
    // Components de la interfície (vinculats amb el .form)
    private JPanel gamePanel;
    private JLabel lblJugador;      // Indica el jugador que té el torn
    private JLabel lblPregunta;     // Mostra el text de la pregunta
    private JRadioButton rbtnOpcio1;
    private JRadioButton rbtnOpcio2;
    private JRadioButton rbtnOpcio3;
    private JRadioButton rbtnOpcio4;
    private JButton btnEnviarResposta;
    private ButtonGroup opcionsGroup;



    // Llista de preguntes i índex de la pregunta actual
    private ArrayList<Pregunta> preguntes;
    private int indexPreguntaActual = 0;

    // Llista de jugadors i índex del jugador actual
    private ArrayList<String> jugadors;
    private int indexJugadorActual = 0;

    // Map per emmagatzemar la puntuació (nombre de respostes correctes) de cada jugador
    private Map<String, Integer> punts;

    /**
     * Constructor del GameForm.
     *
     * @param preguntes Llista de 10 preguntes.
     * @param jugadors  Llista de jugadors participants.
     */
    public GameForm(ArrayList<Pregunta> preguntes, ArrayList<String> jugadors) {
        this.preguntes = preguntes;
        this.jugadors = jugadors;
        punts = new HashMap<>();
        for (String j : jugadors) {
            punts.put(j, 0);
        }

        setTitle("Partida Trivial");
        setContentPane(gamePanel);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        pack();
        setLocationRelativeTo(null); // Centra la finestra

        // Configurem el ButtonGroup perquè només es pugui seleccionar una opció
        opcionsGroup = new ButtonGroup();
        opcionsGroup.add(rbtnOpcio1);
        opcionsGroup.add(rbtnOpcio2);
        opcionsGroup.add(rbtnOpcio3);
        opcionsGroup.add(rbtnOpcio4);

        loadPregunta();

        // Processament del botó Enviar Resposta
        btnEnviarResposta.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                processarResposta();
            }
        });
    }

    /**
     * Carrega la pregunta actual i actualitza els components de la interfície.
     */
    private void loadPregunta() {
        if (indexPreguntaActual < preguntes.size()) {
            Pregunta p = preguntes.get(indexPreguntaActual);
            lblPregunta.setText(p.getPregunta());
            String[] opcions = p.getOpcions();
            rbtnOpcio1.setText(opcions[0]);
            rbtnOpcio2.setText(opcions[1]);
            rbtnOpcio3.setText(opcions[2]);
            rbtnOpcio4.setText(opcions[3]);
            opcionsGroup.clearSelection();
            // Mostrem el nom del jugador que té el torn actual
            lblJugador.setText("Torn de: " + jugadors.get(indexJugadorActual));
        } else {
            // Quan s'han contestat totes les preguntes, es mostren els resultats
            mostrarResultats();
        }
    }

    /**
     * Processa la resposta del jugador actual.
     */
    private void processarResposta() {
        String opcioSeleccionada = obtenirOpcioSeleccionada();
        if (opcioSeleccionada == null) {
            JOptionPane.showMessageDialog(gamePanel,
                    "Si us plau, selecciona una resposta.",
                    "Atenció",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }
        Pregunta p = preguntes.get(indexPreguntaActual);
        String respostaCorrecta = p.getRespostaCorrecta();
        String jugadorActual = jugadors.get(indexJugadorActual);

        if (opcioSeleccionada.equals(respostaCorrecta)) {
            punts.put(jugadorActual, punts.get(jugadorActual) + 1);
            JOptionPane.showMessageDialog(gamePanel,
                    "Correcte!",
                    "Resultat",
                    JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(gamePanel,
                    "Incorrecte! La resposta correcta era: " + respostaCorrecta,
                    "Resultat",
                    JOptionPane.ERROR_MESSAGE);
        }

        // Avancem a la següent pregunta
        indexPreguntaActual++;
        // Rota el torn entre jugadors
        indexJugadorActual = (indexJugadorActual + 1) % jugadors.size();
        loadPregunta();
    }

    /**
     * Retorna el text de l'opció seleccionada o null si no n'hi ha cap.
     */
    private String obtenirOpcioSeleccionada() {
        if (rbtnOpcio1.isSelected()) return rbtnOpcio1.getText();
        if (rbtnOpcio2.isSelected()) return rbtnOpcio2.getText();
        if (rbtnOpcio3.isSelected()) return rbtnOpcio3.getText();
        if (rbtnOpcio4.isSelected()) return rbtnOpcio4.getText();
        return null;
    }

    /**
     * Mostra els resultats finals amb la puntuació de cada jugador.
     */
    private void mostrarResultats() {
        StringBuilder res = new StringBuilder("Final del joc!\n\nResultats:\n");
        for (String j : jugadors) {
            res.append(j).append(": ").append(punts.get(j)).append(" punts\n");
        }
        JOptionPane.showMessageDialog(gamePanel, res.toString(), "Resultats Finals", JOptionPane.INFORMATION_MESSAGE);
        dispose();
    }

    public static void main(String[] args) {
        // Exemple per provar GameForm directament
        ArrayList<Pregunta> preguntes = new ArrayList<>();
        preguntes.add(new Pregunta("Qui va guanyar el mundial de futbol masculí de Sudàfrica 2010?",
                new String[]{"França", "Brasil", "Espanya", "Alemània"}, "Espanya"));
        preguntes.add(new Pregunta("Quin és el planeta més gran del Sistema Solar?",
                new String[]{"Mercuri", "Venus", "Terra", "Júpiter"}, "Júpiter"));
        preguntes.add(new Pregunta("Quin riu és el més llarg del món?",
                new String[]{"Amazonas", "Nil", "Mississippi", "Yangtze"}, "Nil"));
        preguntes.add(new Pregunta("Qui va escriure 'Don Quixot'?",
                new String[]{"Lope de Vega", "Miguel de Cervantes", "Garcilaso de la Vega", "Federico García Lorca"}, "Miguel de Cervantes"));
        preguntes.add(new Pregunta("Quina és la capital de França?",
                new String[]{"Madrid", "Londres", "París", "Berlín"}, "París"));
        preguntes.add(new Pregunta("Quin element químic té el símbol 'O'?",
                new String[]{"Or", "Oxigen", "Osmio", "Plom"}, "Oxigen"));
        preguntes.add(new Pregunta("En quin any va arribar l'home a la Lluna per primera vegada?",
                new String[]{"1965", "1969", "1972", "1959"}, "1969"));
        preguntes.add(new Pregunta("Quin és l'ocell més gran del món?",
                new String[]{"Àguila", "Estru", "Cisne", "Pingüí"}, "Estru"));
        preguntes.add(new Pregunta("Quin país té forma de bota?",
                new String[]{"França", "Alemanya", "Itàlia", "Espanya"}, "Itàlia"));
        preguntes.add(new Pregunta("Quants colors té un semàfor?",
                new String[]{"2", "3", "4", "5"}, "3"));

        ArrayList<String> jugadors = new ArrayList<>();
        jugadors.add("Alice");
        jugadors.add("Bob");

        SwingUtilities.invokeLater(() -> new GameForm(preguntes, jugadors).setVisible(true));
    }
}
