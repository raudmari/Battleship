import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class GameBoard extends JPanel {

    private final Model model;
    private final int startX = 5;
    private final int startY = 5;
    private final int width = 30;
    private final int height = 30;
    private final String[] alphabet = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O"};


    public GameBoard(Model model) {
        this.model = model;
        setBackground(Color.CYAN);
    }

    @Override
    public Dimension getPreferredSize() {
        int w = (width * model.getBordSize()) + width + (2 * startX);
        int h = (height * model.getBordSize()) + height + (2 * startY);
        return new Dimension(w, h);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2D = (Graphics2D) g;
        g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_DEFAULT); // graafilised objektid joonistatakse
        g.setFont(new Font("Verdana", Font.BOLD, 14)); // Seadistab fondi gameboardil.
        // Joonista tähestiku ruudustik
        drawColAlphabet(g);
        drawRowNumbers(g);// joonistab reanumbrid
        drawGameGrid(g); // jonistab ülejäänud laua osa
        // Kui on mäng, siis näita laevu ka
        if(!model.getGame().isGameOver()) {
            model.drawUserBoard(g);
        }
        drawGameGrid(g);
    }

    private void drawColAlphabet(Graphics g) {
        int i = 0; // abimuutuja tähestikumassiivist tähe saamiseks.
        g.setColor(Color.blue);
        for (int x = startX; x <= (width * model.getBordSize()) + width; x += width) {
            g.drawRect(x, startY, width, height); // joonistab ruudustiku
            if (x > startX) { // esimene lahter jääb tühjaks
                g.drawString(alphabet[i], x + (width / 2) - 5, 2 * (startY + startY) + 5);
                i++;
            }
        }
    }

    private void drawRowNumbers(Graphics g) {
        int i = 1; // Number, mis joonistatakse jauale
        g.setColor(Color.red);
        for (int y = startY + height; y < (height * model.getBordSize()) + height; y += height) {
            g.drawRect(startX, y, width, height);
            if(i < 10) { // 1-9 numbrid
                g.drawString(String.valueOf(i), startX + (width / 2) - 10, y + 2 * (startY + startY));
            } else {
                g.drawString(String.valueOf(i), startX + (width / 2) - 10, y + 2 * (startY + startY));
            }
            i++; // järgmine number
        }
    }

    private void drawGameGrid(Graphics g) {
        List<GridData> matrix = new ArrayList<>();
        g.setColor(Color.BLACK); // joonte värv
        int x = startX + width;
        int y = startY + height;
        int i = 1;
        for(int r = 0; r < model.getBordSize(); r++) {
            for(int c = 0; c < model.getBordSize(); c++){
                g.drawRect(x, y, width, height); // joonistab jooned lauale
                matrix.add(new GridData(r, c, x, y, width, height));
                x += width; // x kasvab laiuse võrra vasakult paremale
            }
            y = (startY + height) + (height * i); // Ülevalt alla kasvavalt
            i += 1;
            x = startX + width;
        }
        model.setGridData(matrix);

    }
}
