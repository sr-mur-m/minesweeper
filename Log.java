import java.util.List;
import java.util.ArrayList;
/**
 * Log of the 'Minesweeper' game.
 * @author  Alberto-Mur (sr.mur.m@gmail.com)
 * @version 1-July-2015
 */
public class Log {
    private List<String> log;

    public Log(String alias, byte mines, byte rows, byte cols) {
        this.log = new ArrayList<String>();
        this.log.add(alias);              // pos 0.
        this.log.add(mines+"");           // pos 1.
        this.log.add(rows+"");            // pos 2.
        this.log.add(cols+"");            // pos 3.
        this.log.add(Data.currentDate()); // pos 4.
        this.log.add(Data.currentTime()); // pos 5.
    }
    public void add(byte borderMines, byte row, byte col) {
        String newEntry = Data.currentTime() + " open [" + row + "][" + col + "] with ";
        if(borderMines == 9) { newEntry += "bomb."; }
        else                 { newEntry += borderMines + " mines."; }
        this.log.add(newEntry);
    }



    /// GAME INITIAL VALUES ///
    public String getAlias() {
        return this.log.get(0);
    }
    public String getMines() {
        return this.log.get(1);
    }
    public String getRows() {
        return this.log.get(2);
    }
    public String getCols() {
        return this.log.get(3);
    }
    public String getDate() {
        return this.log.get(4);
    }
    public String getInitialTime() {
        return this.log.get(5);
    }



    /// GAME MOVEMENT VALUES ///
    public List<String> getLog() {
        return this.log;
    }
    // Take the log information of the movement input as parameter.
    // Pre: movement>0 && movement<=NumberOfMovements.
    public String getLog(int movement) {
        String text = "";
        if(isValid(movement)) text = this.log.get(movement+5);
        return text;
    }
    // return is correctly take this log movement value.
    private boolean isValid(int take) {
        return take>5 && take<this.log.size()-5;
    }
}
