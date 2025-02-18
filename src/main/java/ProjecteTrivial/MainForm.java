package ProjecteTrivial;

import javax.swing.table.DefaultTableModel;
import javax.swing.*;
import javax.swing.SpinnerNumberModel;
import java.util.ArrayList;

public class MainForm extends JFrame{
    // Aquests noms han de coincidir amb els del .form
    private JPanel mainPanel;
    private JTextField txtNom;
    private JButton btnAfegirUsuari;
    private JList<String> tblUsuaris;
    private JButton btnEsborrarUsuari;
    private JSpinner cbNumJugadors;
    private JButton btnIniciarPartida;
    private JScrollPane scrollPaneUsuaris;

    // Variables de suport per gestionar usuaris
    private DefaultListModel<String> modelUsuaris;
    private ArrayList<String> usuaris;

    public MainForm() {

        setContentPane(mainPanel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null); // Centra la finestra
        setVisible(true);
        setMinimumSize(getPreferredSize());

        // Inicialitzem el model de la JList i la llista interna d'usuaris
        modelUsuaris = new DefaultListModel<>();
        tblUsuaris.setModel(modelUsuaris);
        usuaris = new ArrayList<>();

        // Configurem el JSpinner perquè vagi de 1 a 4 (exemple)
        cbNumJugadors.setModel(new SpinnerNumberModel(1, 1, 4, 1));

        // Acció del botó Afegir Usuari
        btnAfegirUsuari.addActionListener(e -> {
            String nom = txtNom.getText().trim();
            if (!nom.isEmpty() && !usuaris.contains(nom)) {
                usuaris.add(nom);
                modelUsuaris.addElement(nom);
                txtNom.setText(""); // Neteja el camp de text
            } else {
                JOptionPane.showMessageDialog(mainPanel,
                        "Nom invàlid o ja existent!",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        });

        // Acció del botó Esborrar Usuari
        btnEsborrarUsuari.addActionListener(e -> {
            int index = tblUsuaris.getSelectedIndex();
            if (index != -1) {
                usuaris.remove(index);
                modelUsuaris.remove(index);
            }
        });

        // Acció del botó Iniciar Partida
        btnIniciarPartida.addActionListener(e -> {
            int numJugadors = (Integer) cbNumJugadors.getValue();
            if (usuaris.size() >= numJugadors) {
                // Aquí podries crear una nova finestra per al joc
                JOptionPane.showMessageDialog(mainPanel,
                        "Iniciant partida amb " + numJugadors + " jugador(s)!",
                        "Iniciant",
                        JOptionPane.INFORMATION_MESSAGE
                );

            } else {
                JOptionPane.showMessageDialog(mainPanel,
                        "No hi ha prou usuaris registrats!",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        });
    }

    // Mètode per poder afegir el mainPanel al JFrame en la classe Main
    public JPanel getMainPanel() {
        return mainPanel;
    }

    // Classe Main (punt d'entrada) dins el mateix fitxer o en un altre
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                MainForm f=null;
                    f=new MainForm();
            }
        });
    }
}
