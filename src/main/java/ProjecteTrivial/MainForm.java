package ProjecteTrivial;

import javax.swing.*;
import javax.swing.SpinnerNumberModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class MainForm extends JFrame {
    // Components creats amb el GUI Designer (.form)
    private JPanel mainPanel;
    private JTextField txtNom;
    private JButton btnAfegirUsuari;
    private JList<String> tblUsuaris;
    private JButton btnEsborrarUsuari;
    private JSpinner cbNumJugadors;
    private JButton btnIniciarPartida;

    // Model per la JList i llista interna d'usuaris
    private DefaultListModel<String> modelUsuaris;
    private ArrayList<String> usuaris;

    public MainForm() {
        setContentPane(mainPanel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null); // Centra la finestra
        setVisible(true);
        setMinimumSize(getPreferredSize());

        // Inicialitzem el model i la llista d'usuaris
        modelUsuaris = new DefaultListModel<>();
        tblUsuaris.setModel(modelUsuaris);
        usuaris = new ArrayList<>();

        // Configurem el Spinner: de 1 a 10 jugadors
        cbNumJugadors.setModel(new SpinnerNumberModel(1, 1, 10, 1));

        // Afegir usuari (l'usuari s'introdueix pel camp de text)
        btnAfegirUsuari.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nom = txtNom.getText().trim();
                if (!nom.isEmpty() && !usuaris.contains(nom)) {
                    usuaris.add(nom);
                    modelUsuaris.addElement(nom);
                    txtNom.setText(""); // Neteja el camp de text
                } else {
                    JOptionPane.showMessageDialog(mainPanel,
                            "Nom invàlid o ja existent!",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Esborrar usuari seleccionat
        btnEsborrarUsuari.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int index = tblUsuaris.getSelectedIndex();
                if (index != -1) {
                    usuaris.remove(index);
                    modelUsuaris.remove(index);
                }
            }
        });

        // Iniciar la partida
        btnIniciarPartida.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int numJugadors = (Integer) cbNumJugadors.getValue();
                if (usuaris.size() >= numJugadors) {
                    // Es prenen els primers "numJugadors" de la llista d'usuaris
                    ArrayList<String> jugadorsPartida = new ArrayList<>(usuaris.subList(0, numJugadors));

                    // Creem la llista de 10 preguntes
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

                    // Obrim la finestra del joc
                    new GameForm(preguntes, jugadorsPartida).setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(mainPanel,
                            "No hi ha prou usuaris registrats!",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainForm());
    }
}
