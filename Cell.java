/**
 * Cell of the 'Minesweeper' game.
 * @author  Alberto-Mur (sr.mur.m@gmail.com)
 * @version 1-July-2015
 */
public class Cell {
    private byte borderMines; // rg=[0,8]. (9 == mine).
    private boolean isFlag;
    private boolean isMine;
    private boolean isOpen;
    
    public Cell() {
        this.borderMines = 0;
        this.isFlag = false;
        this.isMine = false;
        this.isOpen = false;
    }
    
    
    public byte getBorderMines() {
        return this.borderMines;
    }
    
    
    public void setFlag() {
        this.isFlag = true;
    }    
    public void removeFlag() {
        this.isFlag = false;
    }
    public boolean isFlag() { return this.isFlag; }
    
    
    public void setMine() {
        this.borderMines = 9;
        this.isMine = true;
    }
    public boolean isMine() { return this.isMine; }
    
    
    public void increaseBorderMines() {
        if(borderMines<8) {
            this.borderMines++;
        }
    }
    
    
    // game over when return false.
    public boolean openCell() {
        if(this.isMine) {
            return false;
        }
        this.isOpen = true;
        this.isFlag = false;
        return true;
    }
    public boolean isOpen() { return this.isOpen; }
}
