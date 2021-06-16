import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Model {

    private int bordSize = 10; // vaikimisi mängulaua suurus 10x10
    private List<GridData> gridData;
    private Game game;

    public Model() {
        this.gridData = new ArrayList<>(); // väärtuste omistamine Model konstruktoris
        this.game = new Game(bordSize);
    }

    public void setupNewGame() {
        game = new Game(bordSize); // luuakse, kui vajutatakse uue mäng nupule
    }

    public int getBordSize() {
        return bordSize;
    }

    public void setBordSize(int bordSize) {
        this.bordSize = bordSize;
    }

    public List<GridData> getGridData() {
        return gridData;
    }

    public Game getGame() {
        return game;
    }

    public void setGridData(List<GridData> gridData) {
        this.gridData = gridData;
    }

    public int checkGridIndex(int mouseX, int mouseY) {
        int result = -1; // kui õiget tulemust ei leita
        int index = 0;
        for (GridData gd : gridData) {
            if (mouseX > gd.getX() && mouseX <= (gd.getX() + gd.getWidth())
                    && mouseY > gd.getY() && mouseY <= (gd.getY()) + gd.getHeight()) {
                result = index;
                // for loop katkestus
            }
            index++;
        }
        return result; //kui leiab, on result index kui ei  siis on result -1
    }

    public int getRowById(int id) {
        if (id != -1) {
            return gridData.get(id).getRow();
        }
        return -1;
    }

    public int getColById(int id) {
        if (id != -1) {
            return gridData.get(id).getCol();
        }
        return -1;
    }

    public void drawUserBoard(Graphics g) {
        List<GridData> gd = getGridData(); // sisu on sama aga muutujad erinevad
        int[][] matrix = game.getBoardMatrix();
        int id = 0;
        for (int r = 0; r < game.getBoardMatrix().length; r++) {
            for (int c = 0; c < game.getBoardMatrix()[0].length; c++) {
                if (matrix[r][c] == 0) { // kui on VESI
                    g.setColor(Color.cyan);
                    if (gd.get(id).getRow() == r && gd.get(id).getCol() == c) {
                        g.fillRect(gd.get(id).getX(), gd.get(id).getY(), gd.get(id).getWidth(), gd.get(id).getHeight());
                    }
                } else if (matrix[r][c] == 7) { // Pihtas
                    g.setColor(Color.green);
                    if (gd.get(id).getRow() == r && gd.get(id).getCol() == c) {
                        g.fillRect(gd.get(id).getX() + 3, gd.get(id).getY() + 3, gd.get(id).getWidth() - 6, gd.get(id).getHeight() - 6);
                    }
                } else if (matrix[r][c] == 8) { // Möödas
                    g.setColor(Color.red);
                    if (gd.get(id).getRow() == r && gd.get(id).getCol() == c) {
                        g.fillRect(gd.get(id).getX() + 3, gd.get(id).getY() + 3, gd.get(id).getWidth() - 6, gd.get(id).getHeight() - 6);
                    }
                } else if (matrix[r][c] > 0 && matrix[r][c] < 5) { // Peidab laeva asukohas

                }
                id++;
            }
        }
    }
}
