import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Controller implements MouseListener, MouseMotionListener {

    private final Model model;
    private final View view;
    private final GameTimer gameTimer;

    private final String dbname = "scores.db";
    String[] columnNames = new String[] {"nimi", "Aeg", "Klikke", "Laua suurus", "M\u00E4ngitud"};
    private JDialog scoreboardDialog;
    private boolean doRdoTbl = true;
    private boolean rdoTblMdl = false;
    private boolean doRdoDb = false;
    private JTable table;

    public Controller(Model model, View view) {
        this.model = model;
        this.view = view;
        this.gameTimer = new GameTimer(view);

        view.registerComboBox(new MyComboBoxListener());
        view.registerNewGAme(new MyNewGameListener());
        view.registerScoreButton(new MyScoreButtoner());
    }

    private class MyNewGameListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if(!gameTimer.isRunning()) { // mäng(aeg) ei käi
                model.setupNewGame();
                gameTimer.startTimer();
                gameTimer.setRunning(true);
                view.getBtnNewGame().setText("L\u00F5peta m\u00E4ng");
                view.getCmbSize().setEnabled(false); //comboboxist ei saa valida
                model.getGame().setupNewGame(); // paneb laevad reaalselt maatriksisse
                view.getLblShip().setText(model.getGame().getShipCounter() + " / " + model.getGame().getShipsParts());
                model.getGame().showGameBoardMatrix(); // Test näita konsoolis laevade asukohta
            } else { // mäng käib
                gameTimer.stopTimer(); // peata aeg
                gameTimer.setRunning(false); // aeg enam ei jookse
                model.getGame().setGameOver();
                view.getBtnNewGame().setText("Uus m\u00E4ng");
                view.getCmbSize().setEnabled(true);
                resetPlayerData();
            }
        }
    }

    private void resetPlayerData() {
        gameTimer.setMinutes(0);
        gameTimer.setSeconds(0);
        model.getGame().resetClickCounter();

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if(gameTimer.isRunning()) { // Mäng käib, võib klikkida
            int id = model.checkGridIndex(e.getX(), e.getY()); //grid data massiivi indeksi teadasaamiseks
            int row = model.getRowById(id);
            int col = model.getColById(id);
            int[][] matrix = model.getGame().getBoardMatrix(); // hetke laud (10 x 10 või suurem, vastavalt mängija valikule
            if(matrix[row][col] == 0) { // vesi ehk mööda
                model.getGame().setUserClick(row, col, 8);
                model.getGame().setClickCounter(1);
                view.getLblShip().setText(model.getGame().getShipCounter() + " / " + model.getGame().getShipsParts() + " m\u00F6\u00F6da");
            } else if(matrix[row][col] > 0 && matrix[row][col] < 5) {// pihtas
                model.getGame().setUserClick(row, col, 7);
                model.getGame().setClickCounter(1);
                model.getGame().setShipCounter(1);
                view.getLblShip().setText(model.getGame().getClickCounter() + " / " + model.getGame().getShipsParts() + " pihtas");
            }
            model.getGame().showGameBoardMatrix(); // Testiks, et näitaks konsoolis
            view.repaint();
            // kontrollime ega mäng pole läbi saanud
            if(model.getGame().isGameOver()) {
                gameTimer.stopTimer();
                gameTimer.setRunning(false);
                String name = JOptionPane.showInputDialog(view, "Kuidas on admirali/mängija nimi?");
                if(name.trim().isEmpty()) {
                    name = "Teadmata";
                }
                JOptionPane.showMessageDialog(view, "M\u00E4ng on l\u00E4bi admiral " + name);
                view.getBtnNewGame().setText("Uus mäng");
                view.getCmbSize().setEnabled(true);
                // TODO Lisa andmebaasi ja teksti faili võitja andmed


                // Kirjuta faili
                view.writeToFile(name, model.getBordSize(), model.getGame().getClickCounter(), gameTimer.getPlayedTimeInSeconds());
                // Kirjuta andmebaasi
                addIntoDatabase(name, model.getBordSize(), model.getGame().getClickCounter(), gameTimer.formatGameTime() );
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        /*String mouse = String.format("x: %03d & Y: %03d", e.getX(), e.getY()); // saame x ja y koordinaadid
        view.getLblMouseXY().setText(mouse);
        // "Algseis"
        int id = model.checkGridIndex(e.getX(), e.getY());
        int row = model.getRowById(id);
        int col = model.getColById(id);

        // ID näitamine
        if(id != -1) {
            view.getLblId().setText(String.valueOf(id + 1)); // näitab laual ID numberit kui hiirega laual liikuda
        }
        // Row ja Col näitamine
        String rowcol = String.format("%d : %d", row + 1, col + 1); // inimlikud numbrid mängulaual
        if(row == -1 || col == -1) {
            rowcol = "Pole mängulaual";
        }
        view.getLblRowCol().setText(rowcol); // näitab reaalselt*/
    }

    private class MyComboBoxListener implements ItemListener {

        @Override
        public void itemStateChanged(ItemEvent e) {
            if(e.getStateChange() == ItemEvent.SELECTED) {
                //System.out.println(e.getItem());
                String number = e.getItem().toString(); // stringiks tehtuna, muidu on objekt nt suurus 15
                int size = Integer.parseInt(number); // Stringi numbriks muutmine
                view.getLblGameBoard().setText(number + " x " + number); // muudab label'i peal teksti
                model.setBordSize(size); // muudab mängulaua suuruse.
                view.pack(); // osade kokkupakimine, et mängu laua suurus muutuks
                view.repaint(); // Joonistab lauale olevad elemendid uuesti kui comboboxist muudetakse laua suurust
            }
        }
    }

    /**
     * Lisab mängija info andmebaasi
     * @param name nimi
     * @param boardsize laua suurus
     * @param clicks tehtud klikkide arv
     * @param gtime mänguaeg sekundites
     */
    private void addIntoDatabase(String name, int boardsize, int clicks, String gtime) {
        File f = new File(dbname); // Vaatame kas db on olema ja kui pole siis teeme.
        if(!f.exists()) {
            String url = "jdbc:sqlite:"+dbname;
            String sql = "CREATE TABLE \"scores\" (\n" +
                    "\t\"id\"\tINTEGER,\n" +
                    "\t\"name\"\tTEXT,\n" +
                    "\t\"board\"\tINTEGER,\n" +
                    "\t\"clicks\"\tINTEGER,\n" +
                    "\t\"gametime\"\tINTEGER,\n" +
                    "\t\"playedtime\"\tTEXT,\n" +
                    "\tPRIMARY KEY(\"id\" AUTOINCREMENT)\n" +
                    ");";
            try(Connection con = DriverManager.getConnection(url);
                Statement stat = con.createStatement()) {
                stat.execute(sql);
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, e.getMessage());
                //System.out.println(e.getMessage());
            }
        }
        try {
            Connection con = DriverManager.getConnection("jdbc:sqlite:"+dbname);
            String[] parts = gtime.split(":");
            int mtime = Integer.parseInt(parts[0]) * 60 + Integer.parseInt(parts[1]);
            String sql = "INSERT INTO scores (name, board, clicks, gametime, playedtime) VALUES (?, ?, ?, ?, datetime('now', 'localtime'))";
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setString(1, name);
            statement.setInt(2, boardsize);
            statement.setInt(3, clicks);
            statement.setInt(4, mtime);
            statement.executeUpdate();
            con.close();
        } catch (SQLException e) {
            System.out.println("Andmebaasiga miski kamm");
            e.printStackTrace();
        }
    }

    private boolean createTable(ArrayList<ScoreData> sdList) {
        if(sdList.size() > 0) {
            orderArrayList(sdList);
            String[][] data = new String[sdList.size()][5]; // 5 on veergude arv
            for(int i = 0; i < sdList.size(); i++) {
                data[i][0] = sdList.get(i).getName();
                data[i][1] = String.valueOf(sdList.get(i).convertSecToMMSS(sdList.get(i).getTime()));
                data[i][2] = String.valueOf((sdList.get(i).getClickcount()));
                data[i][3] = String.valueOf(sdList.get(i).getBoardsize());
                data[i][4] = sdList.get(i).getPalyedTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            }
            table = new JTable(data,columnNames); // Teeb tabeli andmetega
            scoreboardDialog = new ScoreboardDialog(view);
            scoreboardDialog.add(new JScrollPane(table));
            scoreboardDialog.setTitle("Edetabel JTable");
            return true;
        }
        return false;
    }

    private class MyScoreButtoner implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            ArrayList<ScoreData> result;
            if(doRdoTbl) {
                try {
                    result = view.readFromFile();
                    if(createTable(result)) {
                        scoreboardDialog.setModal(true); // Põhi-aknalt ei saa midagi valida
                        scoreboardDialog.pack();
                        scoreboardDialog.setLocationRelativeTo(null);
                        scoreboardDialog.setVisible(true);
                    }
                } catch (IOException ioe) {
                    ioe.printStackTrace();
                }
            } else if(rdoTblMdl) {
                //TODO rdoTblMdl
            } else if(doRdoDb) {
                //TODO rdoDb
            }
        }
    }

    /**
     * Sorteerib massiivi
     * @param scoredatas massiiv mis loetud failist
     */
    private void orderArrayList(ArrayList<ScoreData> scoredatas) {
        scoredatas.sort((o1, o2) -> {
            // Aeg
            Integer x1 = o1.getTime();
            Integer x2 = o2.getTime();
            int sComp = x1.compareTo(x2);
            if (sComp != 0) {
                return sComp;
            }
            // Klikkide arv
            Integer y1 = o1.getClickcount();
            Integer y2 = o2.getClickcount();
            return y1.compareTo(y1);
        });
    }

}
