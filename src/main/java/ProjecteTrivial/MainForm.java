package ProjecteTrivial;

import com.google.gson.Gson;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainForm extends JFrame {
    private JPanel mainPanel;
    private JTextField txtNom;
    private JButton btnAfegirUsuari;
    private JList<String> tblUsuaris;
    private JButton btnEsborrarUsuari;
    private JSpinner cbNumJugadors;
    private JButton btnIniciarPartida;

    private DefaultListModel<String> modelUsuaris;
    private ArrayList<Usuari> usuaris;

    public MainForm() {
        setContentPane(mainPanel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);

        modelUsuaris = new DefaultListModel<>();
        tblUsuaris.setModel(modelUsuaris);
        usuaris = carregarUsuarisDesDeDAT("usuaris.dat");
        if (usuaris == null) usuaris = new ArrayList<>();
        for (Usuari u : usuaris) modelUsuaris.addElement(u.getNom());

        cbNumJugadors.setModel(new SpinnerNumberModel(1, 1, 10, 1));

        btnAfegirUsuari.addActionListener(e -> {
            String nom = txtNom.getText().trim();
            if (!nom.isEmpty() && usuaris.stream().noneMatch(u -> u.getNom().equals(nom))) {
                Usuari nouUsuari = new Usuari(nom);
                usuaris.add(nouUsuari);
                modelUsuaris.addElement(nom);
                txtNom.setText("");
                guardarUsuarisEnDAT("usuaris.dat");
            } else {
                JOptionPane.showMessageDialog(mainPanel, "Nom invàlid o ja existent!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnEsborrarUsuari.addActionListener(e -> {
            int index = tblUsuaris.getSelectedIndex();
            if (index != -1) {
                usuaris.remove(index);
                modelUsuaris.remove(index);
                guardarUsuarisEnDAT("usuaris.dat");
            }
        });

        btnIniciarPartida.addActionListener(e -> {
            int numJugadors = (Integer) cbNumJugadors.getValue();
            if (usuaris.size() >= numJugadors) {
                ArrayList<String> jugadorsPartida = new ArrayList<>();
                for (int i = 0; i < numJugadors; i++) jugadorsPartida.add(usuaris.get(i).getNom());
                ArrayList<Pregunta> preguntes = carregarPreguntesDesDeJSON("preguntes.json");

                if (preguntes != null && !preguntes.isEmpty()) {
                    GameForm game = new GameForm(preguntes, jugadorsPartida);
                    game.setVisible(true);
                    game.addWindowListener(new java.awt.event.WindowAdapter() {
                        @Override
                        public void windowClosed(java.awt.event.WindowEvent windowEvent) {
                            actualitzarPuntuacions(game.getPunts());
                        }
                    });
                }
            } else {
                JOptionPane.showMessageDialog(mainPanel, "No hi ha prou usuaris registrats!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    private ArrayList<Pregunta> carregarPreguntesDesDeJSON(String resourcePath) {
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(resourcePath)) {
            if (inputStream == null) {
                throw new FileNotFoundException("No s'ha trobat el fitxer: " + resourcePath);
            }
            Gson gson = new Gson();
            PreguntaJSON[] preguntesJSON = gson.fromJson(new InputStreamReader(inputStream), PreguntaJSON[].class);
            ArrayList<Pregunta> preguntes = new ArrayList<>();
            for (PreguntaJSON p : preguntesJSON) {
                preguntes.add(p.toPregunta());
            }
            return preguntes;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    private ArrayList<Usuari> carregarUsuarisDesDeDAT(String filePath) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) {
            return (ArrayList<Usuari>) ois.readObject();
        } catch (Exception ex) {
            return null;
        }
    }

    private void guardarUsuarisEnDAT(String filePath) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            oos.writeObject(usuaris);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void actualitzarPuntuacions(Map<String, Integer> punts) {
        for (Usuari u : usuaris) {
            if (punts.containsKey(u.getNom())) {
                u.incrementarPuntuacio(punts.get(u.getNom()));
                u.incrementarPartidesJugades();
                // Aquí podries afegir lògica per determinar guanyador i incrementar partidesGuanyades
            }
        }
        guardarUsuarisEnDAT("usuaris.dat");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainForm::new);
    }
}