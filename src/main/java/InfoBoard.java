import javax.swing.*;
import java.awt.*;

public class InfoBoard extends JPanel {

    private final JPanel pnlComponents = new JPanel(new GridBagLayout()); // Siia Label'id, Buttonid, radiobtn, combox
    private final GridBagConstraints gbc = new GridBagConstraints(); // millised read/veerud on kokku pandud

    private final Font fontBold = new Font("Verdana", Font.BOLD, 14); // Font bold, Verdana
    private final Font fontNormal = new Font("Verdana", Font.PLAIN, 14);

    private final String[] boardSizes = {"10", "11", "12", "13", "14", "15"}; // laua suurus kui valida suurem laud!

    private JLabel lblMouseXY;
    private JLabel lblId; //
    private JLabel lblRowCol; // Rida-Veerg
    private JLabel lblTime; // mängu aeg
    private JLabel lblShip; // Laevade info
    private JLabel lblGameBoard; // kui suurt mängulauda kasutad

    private JComboBox<String> cmbSize; // mängulaua suurus

    private JButton btnNewGame; // uus mäng
    private JButton btnScoreBoard; // edetabeli nupp

    private final JLabel lblScoreBoard = new JLabel("Edetabeli variant:");

    private final JRadioButton rdoTable = new JRadioButton("Lihtne tabel");
    private final JRadioButton rdoTableModel = new JRadioButton("Model tabel");
    private final JRadioButton rdoDatabase = new JRadioButton("Andmebaas");

    private final ButtonGroup groupBtn = new ButtonGroup();

    public InfoBoard() {
        //setBackground(Color.lightGray);
        setPreferredSize(new Dimension(350, 200)); //paneeli suurus
        setLayout(new FlowLayout(FlowLayout.LEFT));

        //pnlComponents.setBackground(Color.orange);

        gbc.anchor = GridBagConstraints.WEST; // Lahtris tekstid vasakul
        gbc.insets = new Insets(2, 2, 2, 2);

        setupLabels();
        setupComboBox();
        setupButtons();
        //setupRadioButtons();

        add(pnlComponents);

    }

    private void setupLabels() {
/*        // Esimene rida
        JLabel lblMouseTxt = new JLabel("Hiir");
        lblMouseTxt.setFont(fontBold);
        gbc.gridx = 0; // veerg
        gbc.gridy = 0; // rida
        pnlComponents.add(lblMouseTxt, gbc);

        lblMouseXY = new JLabel("X: 0 & Y: 0");
        lblMouseXY.setFont(fontNormal);
        gbc.gridx = 1;
        gbc.gridy = 0;
        pnlComponents.add(lblMouseXY, gbc);

        // Teine rida
        JLabel lblIdText = new JLabel("ID");
        lblIdText.setFont(fontBold);
        gbc.gridx = 0; // Veerg
        gbc.gridy = 1; // Rida'
        pnlComponents.add(lblIdText, gbc);

        lblId = new JLabel("Teadmata");
        lblId.setFont(fontNormal);
        gbc.gridx = 1;
        gbc.gridy = 1;
        pnlComponents.add(lblId, gbc);

        // Kolmas rida
        JLabel lblRowColTxt = new JLabel("Rida : Veerg");
        lblRowColTxt.setFont(fontBold);
        gbc.gridx = 0;
        gbc.gridy = 2;
        pnlComponents.add(lblRowColTxt, gbc);

        lblRowCol = new JLabel("1 : 1");
        lblRowCol.setFont(fontNormal);
        gbc.gridx = 1;
        gbc.gridy = 2;
        pnlComponents.add(lblRowCol, gbc);

        // Neljas rida*/
        JLabel lblTimeText = new JLabel("Mängu aeg");
        lblTimeText.setFont(fontBold);
        gbc.gridx = 0;
        gbc.gridy = 3;
        pnlComponents.add(lblTimeText, gbc);

        lblTime = new JLabel("00:00");
        lblTime.setFont(fontNormal);
        gbc.gridx = 1;
        gbc.gridy = 3;
        pnlComponents.add(lblTime, gbc);

        // Viies rida

        JLabel lblShipText = new JLabel("Laevad");
        lblShipText.setFont(fontBold);
        gbc.gridx = 0;
        gbc.gridy = 4;
        pnlComponents.add(lblShipText, gbc);
        lblShip = new JLabel("0 / 0");
        lblShip.setFont(fontNormal);
        gbc.gridx = 1;
        gbc.gridy = 4;
        pnlComponents.add(lblShip, gbc);

        // Kuues rida

        JLabel lblGameBoardSize = new JLabel("Valitud laeva suurus");
        lblGameBoardSize.setFont(fontBold);
        gbc.gridx = 0;
        gbc.gridy = 5;
        pnlComponents.add(lblGameBoardSize, gbc);

        lblGameBoard = new JLabel("10 x 10");
        lblGameBoard.setFont(fontNormal);
        gbc.gridx = 1;
        gbc.gridy = 5;
        pnlComponents.add(lblGameBoard, gbc);
    }


    private void setupComboBox() {
        // Seitsmes rida
        JLabel lblCmbSizeTxt = new JLabel("Vali laua suurus");
        lblCmbSizeTxt.setFont(fontBold);
        gbc.gridx = 0;
        gbc.gridy = 6;
        pnlComponents.add(lblCmbSizeTxt,gbc);

        cmbSize = new JComboBox<>(boardSizes);
        cmbSize.setFont(fontNormal);
        cmbSize.setPreferredSize(new Dimension(106,28));
        gbc.gridx = 1;
        gbc.gridy = 6;
        pnlComponents.add(cmbSize,gbc);

    }

    private void setupButtons() {
        // Kaheksas rida
        JLabel lblNewGameTxt = new JLabel("Uus mäng");
        lblNewGameTxt.setFont(fontBold);
        gbc.gridx = 0;
        gbc.gridy = 7;
        pnlComponents.add(lblNewGameTxt,gbc);

        btnNewGame = new JButton("Uus m\u00E4ng");
        btnNewGame.setFont(fontNormal);
        btnNewGame.setPreferredSize(new Dimension(106,28));;
        gbc.gridx = 1;
        gbc.gridy = 7;
        pnlComponents.add(btnNewGame, gbc);

        // Üheksas rida
        btnScoreBoard = new JButton("Edetabel");
        btnScoreBoard.setFont(fontNormal);
        btnScoreBoard.setPreferredSize(new Dimension(106, 28));
        gbc.gridx = 1;
        gbc.gridy = 8;
        pnlComponents.add(btnScoreBoard,gbc);

    }

    private void setupRadioButtons() {
        // Kümnes rida
        lblScoreBoard.setFont(fontBold);
        gbc.gridwidth = 3; // Üle 3 rea kõrge
        gbc.gridx = 0;
        gbc.gridy = 9;
        pnlComponents.add(lblScoreBoard,gbc);

        // Raadionupud ülevalt alla
        rdoTable.setFont(fontNormal);
        //Taust kui vaja
        gbc.gridx = 1;
        gbc.gridy = 9;
        pnlComponents.add(rdoTable,gbc);

        rdoTableModel.setFont(fontNormal);
        gbc.gridx = 1;
        gbc.gridy = 10;
        pnlComponents.add(rdoTableModel,gbc);

        rdoDatabase.setFont(fontNormal);
        rdoDatabase.setSelected(true);// Vaikimisi valitud
        gbc.gridx = 1;
        gbc.gridy = 11;
        pnlComponents.add(rdoDatabase, gbc);

        // Lisame raadionupud gruppi, et saaks valida vaid ühe valiku
        groupBtn.add(rdoTable);
        groupBtn.add(rdoTableModel);
        groupBtn.add(rdoDatabase);
    }

    public JLabel getLblMouseXY() {
        return lblMouseXY;
    }

    public JLabel getLblId() {
        return lblId;
    }

    public JLabel getLblRowCol() {
        return lblRowCol;
    }

    public JLabel getLblTime() {
        return lblTime;
    }

    public JLabel getLblShip() {
        return lblShip;
    }

    public JLabel getLblGameBoard() {
        return lblGameBoard;
    }

    public JComboBox<String> getCmbSize() {
        return cmbSize;
    }

    public JButton getBtnNewGame() {
        return btnNewGame;
    }

    public JButton getBtnScoreBoard() {
        return btnScoreBoard;
    }

    public JRadioButton getRdoTable() {
        return rdoTable;
    }

    public JRadioButton getRdoTableModel() {
        return rdoTableModel;
    }

    public JRadioButton getRdoDatabase() {
        return rdoDatabase;
    }
}
