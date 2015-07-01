/**
 * See this example: www.tlu.ee/~matsak/java/thinking_in_java/TIJ310.htm
 * Timer of the 'Minesweeper' game.
 * @author  Alberto-Mur (sr.mur.m@gmail.com)
 * @version 1-July-2015
 */
    interface Timer extends Runnable {
    byte SECONDS_LVL_BEGINNER     = 15; // 6.25 sec. for open cell.
    byte SECONDS_LVL_INTERMEDIATE = 30; // 6.25 sec.
    byte SECONDS_LVL_ADVANCED     = 60; // 6.25 seg.
    byte SECONDS_LVL_PROFICIENTLY = 45; // 6.25 seg.
    byte SECONDS_LVL_DEFAULT      = 30; // change for test.
    void incrementUncover();
    short getSecond();
    void setTimer(byte level);
    boolean isGameOverTimer();
    
    class Test implements Timer {
        public Thread timer;
        public short second;
        public boolean expiredTimer;
        public byte rows, cols, uncover;
        public Test(byte rows, byte cols, byte uncover) {
            this.rows    = rows;
            this.cols    = cols;
            this.uncover = uncover;
        }
        public void incrementUncover() {
            this.uncover++;
        }
        public short getSecond() {
            return this.second;
        }

        public void setTimer(byte level) {
            this.second = (short)(level*10);
            this.expiredTimer = false;
            timer = new Thread(this);
            timer.start();
        }
        public void run() {
            try {
                while(true) {
                    if(this.second!=(short)0) { // are seconds.
                        this.second--;
                        // System.out.println("Second="+second); // for test.
                        timer.sleep(1000);
                        refreshTimerExpired();
                        if(this.expiredTimer) gameOverTimer();
                    } // else finish run().
                }
            } catch(InterruptedException ex) {
                System.err.println("Caught InterruptedException in Timer.run(): " + ex.getMessage());
            }
        }
        private void refreshTimerExpired() {
            if(this.second==(short)0) this.expiredTimer = true;
        }
        public boolean isGameOverTimer() {
            return this.expiredTimer;
        }
        private void gameOverTimer() {
            int closed = this.rows * this.cols - this.uncover;
            System.out.println("Game Over: Timer expired. Opened: " + this.uncover + ". Closed: " + closed + ".");
        }
    }
}
