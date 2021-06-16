import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class View extends JFrame {

    private final Model model;
    private final GameBoard gameBoard;
    private final InfoBoard infoBoard;
    private final String filname = "scores.txt"; // edetabel failis

    public View(Model model) {
        super("Laevade pommitamine");
        this.model = model;

        this.gameBoard = new GameBoard(model);
        this.infoBoard = new InfoBoard();

        JPanel container = new JPanel(new BorderLayout());
        container.add(gameBoard, BorderLayout.CENTER);   // on keskel
        container.add(infoBoard, BorderLayout.EAST); // on vasakul pool

        add(container); // Lisame JFramile conteineri
    }

    // Comboboxi funktsionaalsuse lisamine, et saaks CB'st valida mängulaua suurusi
    public void registerComboBox(ItemListener itemListener) {
        infoBoard.getCmbSize().addItemListener(itemListener);
    }

    public void registerGameBoardMouse(Controller controller) {
        gameBoard.addMouseListener(controller);
        gameBoard.addMouseMotionListener(controller);
    }

    public void registerNewGAme(ActionListener al) {
        infoBoard.getBtnNewGame().addActionListener(al); // uue mängu nupu
    }

    public void registerScoreButton(ActionListener al) {
        infoBoard.getBtnScoreBoard().addActionListener(al);
    }

    public JLabel getLblMouseXY() {
        return infoBoard.getLblMouseXY();
    }

    public JLabel getLblId() {
        return infoBoard.getLblId();
    }

    public JLabel getLblRowCol() {
        return infoBoard.getLblRowCol();
    }

    public JLabel getLblTime() {
        return infoBoard.getLblTime();
    }

    public JLabel getLblShip() {
        return infoBoard.getLblShip();
    }

    public JLabel getLblGameBoard() {
        return infoBoard.getLblGameBoard();
    }

    public JComboBox<String> getCmbSize() {
        return infoBoard.getCmbSize();
    }

    public JButton getBtnNewGame() {
        return infoBoard.getBtnNewGame();
    }

    public JButton getBtnScoreBoard() {
        return infoBoard.getBtnScoreBoard();
    }

    public JRadioButton getRdoTable() {
        return infoBoard.getRdoTable();
    }

    public JRadioButton getRdoTableModel() {
        return infoBoard.getRdoTableModel();
    }

    public JRadioButton getRdoDatabase() {
        return infoBoard.getRdoDatabase();
    }

    public void writeToFile(String name, int boardSize, int clicks, int gtime) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filname, true))) { // true lisab juurde. ei kirjuta üle
            String line = name + ";" + gtime + ";" + boardSize + ";" + clicks + ";" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            bw.write(line);
            bw.newLine(); // Reavahetus
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Edetabeli faili ei leitud" + filname);
        }

    }

    public ArrayList<ScoreData> readFromFile() throws IOException {
        ArrayList<ScoreData> scoreDatas = new ArrayList<>();
        File f = new File(filname);
        if (f.exists()) {
            try (BufferedReader br = new BufferedReader(new FileReader(filname))) {
                for (String line; (line = br.readLine()) != null; ) {
                    String[] parts = line.split(";");
                    if (Integer.parseInt(parts[2]) == model.getBordSize()) {
                        String name = parts[0];
                        int gametime = Integer.parseInt(parts[1]);  // Mängu aeg sekundites
                        int board = Integer.parseInt(parts[2]); // LAua suurus
                        int click = Integer.parseInt(parts[3]); // klickid
                        LocalDateTime played = LocalDateTime.parse(parts[4], DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                        scoreDatas.add(new ScoreData(name, gametime, board, click, played));
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else { // Faili pole, teeb uue faili
            File file = new File(filname);
            file.createNewFile();
        }
        return scoreDatas;
    }
}

