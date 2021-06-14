import javax.swing.*;
import java.awt.*;

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
    }

    private void drawColAlphabet(Graphics g){
        int i = 0; // abimuutuja tähestikumassiivist tähe saamiseks.
        g.setColor(Color.blue);
        for(int x = startX; x <= (width * model.getBordSize()) + width; x+=width){
            g.drawRect(x, startY, width, height); // joonistab ruudustiku
            if(x > startX) { // esimene lahter jääb tühjaks
                g.drawString(alphabet[i], x + (width/2) - 5, 2 * (startY + startY) + 5 );
                i++;
            }
        }
    }
}
