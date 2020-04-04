package deepe.sh.tetris;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.geom.Rectangle2D;

import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.KeyStroke;

public class BoardComponent extends JComponent {
    private static final long serialVersionUID = 1L;

    private int cellSize, margin;

    public BoardComponent(int cellSize, int margin) {
        this.cellSize = cellSize;
        this.margin = margin;

        InputMap imap = getInputMap(WHEN_FOCUSED);
        ActionMap actionMap = getActionMap();

        imap.put(KeyStroke.getKeyStroke("RIGHT"), "key.right");
        actionMap.put("key.right", new AbstractAction() {
            private static final long serialVersionUID = 1L;

            @Override
            public void actionPerformed(ActionEvent event) {
                Board.CurrentPiece.moveRight();
            }
        });

        imap.put(KeyStroke.getKeyStroke("LEFT"), "key.left");
        actionMap.put("key.left", new AbstractAction() {
            private static final long serialVersionUID = 1L;

            @Override
            public void actionPerformed(ActionEvent event) {
                Board.CurrentPiece.moveLeft();
            }
        });

        imap.put(KeyStroke.getKeyStroke("UP"), "key.rotate");
        imap.put(KeyStroke.getKeyStroke("SPACE"), "key.rotate");
        actionMap.put("key.rotate", new AbstractAction() {
            private static final long serialVersionUID = 1L;

            @Override
            public void actionPerformed(ActionEvent event) {
                Board.CurrentPiece.rotate();
            }
        });

        imap.put(KeyStroke.getKeyStroke("DOWN"), "key.down");
        actionMap.put("key.down", new AbstractAction() {
            private static final long serialVersionUID = 1L;

            @Override
            public void actionPerformed(ActionEvent event) {
                for (int i = 0; i < Board.getHeight() / 2; i++)
                    Board.CurrentPiece.moveDown();
            }
        });
    }

    private Rectangle2D getRectangle(int x, int y) {
        return new Rectangle2D.Double(
            x * (cellSize + margin) + margin,
            y * (cellSize + margin) + margin,
            cellSize, cellSize
        );
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setPaint(Color.RED);

        for (int x = 0; x < Board.getWidth(); x++)
            for (int y = 0; y < Board.getHeight(); y++)
                if(Board.isOccupied(x, y))
                    g2d.fill(getRectangle(x, y));

    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(
            Board.getWidth()  * (cellSize + margin) + margin,
            Board.getHeight() * (cellSize + margin) + margin
        );
    }
}
