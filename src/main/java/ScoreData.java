import java.time.LocalDateTime;

public class ScoreData {
    private final String name;
    private final int time; // mängu aeg sekundites
    private final int boardsize;
    private final int clickcount;
    private final LocalDateTime palyedTime;

    public ScoreData(String name, int time, int boardsize, int clickcount, LocalDateTime palyedTime) {
        this.name = name;
        this.time = time;
        this.boardsize = boardsize;
        this.clickcount = clickcount;
        this.palyedTime = palyedTime;
    }

    public String getName() {
        return name;
    }

    public int getTime() {
        return time;
    }

    public int getBoardsize() {
        return boardsize;
    }

    public int getClickcount() {
        return clickcount;
    }

    public LocalDateTime getPalyedTime() {
        return palyedTime;
    }

    public String convertSecToMMSS(int seconds) {
        int min = seconds / 60;
        int sec = seconds % 60;
        return String.format("%02d:%02d", min, sec);
    }

}
