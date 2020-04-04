package deepe.sh.tetris;

import java.awt.BorderLayout;
import java.awt.Graphics;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JLabel;

public class StatusBar extends JPanel {
    private static final long serialVersionUID = 1L;

    private JLabel scoreLabel;
    private JLabel timerLabel;

    public StatusBar() {
        setBorder(BorderFactory.createEtchedBorder());
        scoreLabel = new JLabel("Score: 0 ");
        timerLabel = new JLabel("0:00");

        add(scoreLabel, BorderLayout.WEST);
        add(timerLabel, BorderLayout.EAST);
    }

    @Override
    public void paintComponent(Graphics g) {
        add(scoreLabel, BorderLayout.WEST);
        add(timerLabel, BorderLayout.EAST);
    }
}
