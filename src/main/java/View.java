import javax.swing.*;
import java.awt.*;

public class View extends JFrame {

    private final Model model;
    private final GameBoard gameBoard;
    private final InfoBoard infoBoard;

    public View(Model model) {
        super("Laevade pommitamine");
        this.model = model;

        this.gameBoard = new GameBoard(model);
        this.infoBoard = new InfoBoard();

        JPanel container = new JPanel(new BorderLayout());
        container.add(gameBoard,BorderLayout.CENTER);   // on keskel
        container.add(infoBoard, BorderLayout.EAST); // on vasakul pool

        add(container); // Lisame JFramile conteineri

    }
}
