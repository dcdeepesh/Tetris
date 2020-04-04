package deepe.sh.tetris;

public class PieceFallThread implements Runnable {

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(800);
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (!Board.CurrentPiece.moveDown()) {
                Board.checkBottomRow();
                Board.addNewPiece();
            }
        }
    }
}
