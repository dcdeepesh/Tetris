package deepe.sh.tetris;

import java.awt.BorderLayout;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class MainFrame extends JFrame {
    private static final long serialVersionUID = 1L;

    public MainFrame() {
        setTitle("Tetris");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

        // Menu bar
        JMenuBar menuBar = new JMenuBar();

        JMenu optionsMenu = new JMenu("Options");
        JMenuItem exitItem = new JMenuItem("Exit");
        optionsMenu.add(exitItem);

        JMenu helpMenu = new JMenu("Help");

        menuBar.add(optionsMenu);
        menuBar.add(helpMenu);
        setJMenuBar(menuBar);
        
        // The main board component
        JComponent boardComponent = new BoardComponent(18, 2);
        Board.init(15, 20, boardComponent);
        getContentPane().add(boardComponent, BorderLayout.CENTER);
        
        // The status bar
        JComponent statusBar = new StatusBar();
        getContentPane().add(statusBar, BorderLayout.SOUTH);

        Board.addNewPiece();

        pack();
        setVisible(true);

        // Thread that drops piece and updates displayed timer
        ScheduledExecutorService ses =  Executors.newSingleThreadScheduledExecutor();
        ses.scheduleAtFixedRate(new PieceFallThread(), 0, 1, TimeUnit.SECONDS);
    }
}
