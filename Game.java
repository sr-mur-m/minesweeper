import java.util.Scanner;

/**
 * Main class of the 'Minesweeper' game.
 * @author  Alberto-Mur (sr.mur.m@gmail.com)
 * @version 1-July-2015
 */
public class Game {
    private Scanner scanner;
    private byte rows, cols, percentMines;
    private byte openRow, openCol;
    private static byte KEY_EXIT = -1;
    private boolean go;
    private Grid grid;

    public Game() {
        scanner = new Scanner(System.in);
        this.rows = 0;
        this.cols = 0;
        this.percentMines = 0;
        this.go = true;
    }
    private void readMines() {
        this.percentMines = scanner.nextByte();
    }
    private void readRow() {
        this.rows = scanner.nextByte();
    }
    private void readCol() {
        this.cols = scanner.nextByte();
    }

    private void message(String msg) {
        System.out.print(msg);
    }
    private void messageln(String msg) {
        System.out.println(msg);
    }
    private void newLine() {
        System.out.print("\n");
    }

    private void grid() {
        grid = new Grid(this.percentMines,this.rows,this.cols);
    }
    private void play() {
        while(this.go) {
            newLine();
            message("Fila: ");   this.openRow = scanner.nextByte();
            message("Comuna: "); this.openCol = scanner.nextByte();
            if(correctInputs() && !grid.isEndGame()) { openCell(); }
            if(grid.isEndGame()) { this.go=false; }
            status();
        }
        scanner.close();
    }
    private boolean correctInputs() { // false if is exit key.
        return this.openRow!=this.KEY_EXIT && this.openCol!=this.KEY_EXIT;
    }
    private void openCell() {
        grid.openCell(this.openRow,this.openCol);
    }
    private void status()  {
        System.out.println("Second="+grid.getSecond());
    }
    private void endStatus()  {
        System.out.println("Victoria: " + grid.isVictory() + ". Explosión: " + grid.isGameOver() + ". Tiempo expirado: " + grid.isGameOverTimer() + ".");
    }

    public static void main(String[] args) {
        Game g = new Game();
        g.messageln("** INICIO PARTIDA **");
        g.message  ("   %MINAS   rg=[5,60]: "); g.readMines();
        g.message  ("   FILAS    rg=[4,11]: "); g.readRow();
        g.message  ("   COLUMNAS rg=[4,11]: "); g.readCol();
        g.grid();
        g.messageln("   " + g.rows + " filas    rg=[0," + (g.rows-1) + "], -1 para salir.");
        g.messageln("   " + g.cols + " columnas rg=[0," + (g.cols-1) + "], -1 para salir.");
        g.play();
        g.messageln(".. FIN PARTIDA ..");
        g.endStatus();
    }
}
