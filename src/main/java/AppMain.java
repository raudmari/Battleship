import javax.swing.*;

public class AppMain {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Model model = new Model(); // teeb mudeli
            View view = new View(model); // Teeb vaade JFrame
            Controller controller = new Controller(model, view);

            view.registerGameBoardMouse(controller); //Hiire funktisonaalsus


            view.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            view.pack(); // et objektid JFrame'il oma koha leiaksid
            view.setLocationRelativeTo(null);  // JFrame on keset ekraani
            view.setResizable(true); // suurust ei saa muuta
            view.setVisible(true); // Näita JFrame
        });

    }
}
