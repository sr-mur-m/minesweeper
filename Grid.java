import java.util.Random;
import java.util.List;
import java.util.ListIterator;
import java.util.ArrayList;

/**
 * Grid of the 'Minesweeper' game.
 * @author  Alberto-Mur (sr.mur.m@gmail.com)
 * @version 1-July-2015
 */
public class Grid implements GameModality {
    public Cell[][] grid;
    private byte numMines;   // rg=[0,127].
    private byte rows, cols; // rg=[3,11].
    private byte uncover;    // opened.
    private boolean gameOver, victory;
    private Random rand;
    private List<Byte> mines;
    private Log log;
    private Timer tim;

    public Grid(byte level) {
        byte timerLevel;
        if (level==1) { // beginner.
            setAttributesLevel((byte)5, (byte)4, (byte)4);
            timerLevel = Timer.SECONDS_LVL_BEGINNER;
        } else if(level==3) { // advance.
            setAttributesLevel((byte)50,(byte)11,(byte)11);
            timerLevel = Timer.SECONDS_LVL_ADVANCED;
        } else if(level==4) {
            setAttributesLevel((byte)60,(byte)11,(byte)11);
            timerLevel = Timer.SECONDS_LVL_PROFICIENTLY;
        } else { // intermediate (level==2) && default.
            setAttributesLevel((byte)20,(byte)8, (byte)8);
            timerLevel = Timer.SECONDS_LVL_INTERMEDIATE;
        }
        setAttributesGame();
        tim.setTimer(timerLevel);
    }
    public short getSecond() {
        return tim.getSecond();
    }

    // rg(rows)=rg(cols)=[4,11] && rg(percentMines)=[5,60].
    public Grid(byte percentMines, byte rows, byte cols) {
        byte mines = (byte)(rows*cols*percentMines/100);
        setAttributesLevel(mines,rows,cols);
        validateAttribsLvl(percentMines,rows,cols);
        setAttributesGame();
        tim.setTimer(Timer.SECONDS_LVL_DEFAULT);
    }


    private void setAttributesLevel(byte mines, byte rows, byte cols) {
        this.numMines = mines;
        this.rows = rows;
        this.cols = cols;
    }
    private void validateAttribsLvl(double percentMines, int rows, int cols) {
        if(percentMines<5 || percentMines>60 || rows<4 || rows>11 || cols<4 || cols>11) {
            setAttributesLevel((byte)20,(byte)8, (byte)8);
        }
    }
    private void setAttributesGame() {
        this.grid = new Cell[rows][cols];
        this.uncover = 0;
        this.gameOver = false;
        this.victory = false;
        this.rand = new Random();
        this.mines = new ArrayList<Byte>();
        this.log = createLog();
        initializeGrid();        // OK
        generatePositionMines(); // OK
        fillGrid();
        tim = new Timer.Test(this.rows,this.cols,this.uncover);    
    }


    private Log createLog() {
        String alias = "Alberto"; // change and save dinamically.
        return new Log(alias,this.numMines,this.rows,this.cols);
    }
    private void initializeGrid() {       // All '0'.
        for (byte i=0; i<rows; i++) {     // Width.
            for (byte j=0; j<cols; j++) { // High.
                grid[i][j]=new Cell();
            }
        }
    }


    private void generatePositionMines() { // pos=[0,rows*cols].
        byte generated = 1;
        while(generated <= this.numMines) { // missing mines.
            byte position = Maths.random(this.rand,(byte)0,this.rows*this.cols);
            if(isNewValue(position)) { generated++; }
        }
    }
    private boolean isNewValue(byte pos) {
        if(!mines.contains(pos)) { // is new.
            mines.add(pos);
            return true;
        }
        return false;
    }


    private void fillGrid() {
        ListIterator<Byte> posMines = mines.listIterator(mines.size());
        while(posMines.hasPrevious()) {
            byte position = posMines.previous();
            byte posX = (byte) (position/this.cols);
            byte posY = (byte) (position%this.cols);
            printGrid(); // for test.
            grid[posX][posY].setMine();
            increaseBorderMines(posX, posY);
            printGrid(); // for test.
        }
    }
    private void increaseBorderMines(byte row, byte col) { // mine is in [row][col].
        for(    byte r=Maths.max(0,row-1); r<=Maths.min(this.rows-1,row+1); r++) {
            for(byte c=Maths.max(0,col-1); c<=Maths.min(this.cols-1,col+1); c++) {
                if( !grid[r][c].isMine()) { // not mine.
                     grid[r][c].increaseBorderMines();
                }
            }
        }
    }


    private void printGrid() { // for test.    
        for (byte x=0; x<grid.length; x++) {
            System.out.print("|");
            for (byte y=0; y<grid[x].length; y++) {
                System.out.print(grid[x][y].getBorderMines());
                if (y!=grid[x].length-1) System.out.print("\t");
            }
            System.out.println("|");
        }
        System.out.println("");
    }


    public void openCell(byte row, byte col) {
        if(grid[row][col].isMine()) { // is mine.
            gameOver(row,col);
        } else if(!grid[row][col].isOpen()) { // not mine && closed.
            grid[row][col].openCell();
            byte neighbour = grid[row][col].getBorderMines();
            log.add(neighbour,row,col);
            System.out.println("Log. neighbour" + neighbour + ". row" + row + ". col" + col + ".");
            increaseUncover(row,col);
        }
        openAroundCells(row,col); // recursive.
    }
    private void gameOver(byte row, byte col) {
        this.gameOver = true;
        int closed = this.rows * this.cols - this.uncover;
        System.out.println("Game Over: Explode cell[" + row + "][" + col + "]. Opened: " + this.uncover + ". Closed: " + closed + ".");
    }
    private void gameWin() {
        this.victory = true;
        System.out.println("You Win: Remain " + tim.getSecond() + " seconds.");
    }
    public boolean isEndGame() {
        return isGameOver() || isVictory() || isGameOverTimer();
    }
    private void increaseUncover(byte row, byte col) { // arguments for print the current uncover++.
        this.uncover++;
        tim.incrementUncover(); // update timer.
        // printGrid(); // for test.
        System.out.println("uncover=" + this.uncover + " ([" + row + "][" + col + "])."); // for test.
        if(isAllMovements()) { gameWin(); }
    }
    private boolean isAllMovements() {
        return (this.uncover == this.rows*this.cols-this.numMines);
    }
    private void openAroundCells(byte row, byte col) {
        if(grid[row][col].getBorderMines()==0) {
            for(    byte r=Maths.max(0,row-1); r<=Maths.min(this.rows-1,row+1); r++) {
                for(byte c=Maths.max(0,col-1); c<=Maths.min(this.cols-1,col+1); c++) {
                    // System.out.println(r + "-" + c); // for test recursivity.
                    // state 0 && not last && not open.
                    if(grid[r][c].getBorderMines()==0 && !grid[r][c].isOpen()) {
                        grid[r][c].openCell();
                        // System.out.println("row" + r + " col" + c); // for test recursivity.
                        byte neighbour = grid[r][c].getBorderMines();
                        log.add(neighbour,r,c);
                        System.out.println("Log. neighbour" + neighbour + ". row" + r + ". col" + c + ".");
                        increaseUncover(r,c);
                        openAroundCells(r,c);
                    }
                }
            }
        }
    }


    public byte getNumMines() {
        return this.numMines;
    }
    public byte getUncover() {
        return this.uncover;
    }
    public boolean isGameOver() {
        return this.gameOver;
    }
    public boolean isGameOverTimer() {
        return tim.isGameOverTimer();
    }
    public boolean isVictory() {
        return this.victory;
    }
}
