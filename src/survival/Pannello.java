package survival;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Pannello extends JFrame implements ActionListener {
    
    final int nBottoni = 50 * 50;
    JButton[] bottoni;
    JButton start;
    JButton stop;
    JButton reset;
    JButton close;
    JTextArea leggenda;
    int[][] cellularStage1;
    int[][] cellularStage2;
    Timer timer;
    Timer timer2;
    int viciniAttivi;
    Border defaultBorder;
    JPanel legendAndButtons;
    JPanel game;
    Font font = new Font("Arial", Font.PLAIN, 17);
    
    public Pannello() {
        
        super("Sopravvivenza Cellulare");
        setSize(1170, 940);
        setLayout(null);
//        setLayout(new GridLayout(1, 2));
//        setLayout(new BorderLayout());
        
        timer = new Timer(50, this);
        timer2 = new Timer(50, this);
        
        leggenda = new JTextArea("Cellular stage white: disabled \n"
                            + "Cellular stage black: enabled \n"
                            + "Cellular stage red: dies \n"
                            + "Cellular stage blue: born");
        leggenda.setEditable(false);
        
        start = new JButton("Start");
        stop = new JButton("Stop");
        reset = new JButton("Reset");
        close = new JButton("Close");
        
        start.addActionListener(this);
        stop.addActionListener(this);
        reset.addActionListener(this);
        close.addActionListener(this);
        
        legendAndButtons = new JPanel();
        legendAndButtons.setLayout(null);
        legendAndButtons.setBounds(0,0, 250, 940);
        
        leggenda.setBounds(0, 300, 250, 100);
        leggenda.setOpaque(false);
        leggenda.setFont(font);
        legendAndButtons.add(leggenda);
        
        start.setBounds(75, 10, 100, 30);
        legendAndButtons.add(start);
        
        stop.setBounds(75, 70, 100, 30);
        legendAndButtons.add(stop);
        
        reset.setBounds(75, 130, 100, 30);
        legendAndButtons.add(reset);
        
        close.setBounds(75, 190, 100, 30);
        legendAndButtons.add(close);
        
        
        game = new JPanel();
        game.setLayout(new GridLayout(50,50));
        game.setBounds(250, 0, 900, 900);
        
        
        add(legendAndButtons);
        add(game);
        
        
        cellularStage1 = new int[50][50];
        cellularStage2 = new int[50][50];
        
        bottoni = new JButton[nBottoni];
        for (int i = 0; i < nBottoni; i++) {
            bottoni[i] = new JButton();
            game.add(bottoni[i]);
            bottoni[i].setBackground(Color.WHITE);
            bottoni[i].addActionListener(this);
        }
        
        defaultBorder = bottoni[0].getBorder();
        
        //Create matrices where state is stored
        for (int i = 0; i < 50; i++) {
            for (int j = 0; j < 50; j++) {
                cellularStage1[i][j] = 0;
                cellularStage2[i][j] = 0;
            }
        }
        
        
        
        
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        
        //Set black color when pressed the button
        for (int i = 0; i < 50 * 50; i++) {
            if (e.getSource() == bottoni[i]) {
                bottoni[i].setBorder(null);
                bottoni[i].setBackground(Color.BLACK);
                int x = i / 50;
                int y = i % 50;
                cellularStage1[x][y] = 1;
//                System.out.println(x);
//                System.out.println(y);
            }
        }
        
        //Set colors of the cells for state 2
        if (e.getSource() == timer) {
            controlloVicini();
            for (int i = 0; i < 50; i++) {
                for (int j = 0; j < 50; j++) {
                    if (cellularStage2[i][j] == 0) {
                        bottoni[50 * i + j].setBackground(Color.WHITE);
                    } else if (cellularStage2[i][j] == 1) {
                        bottoni[50 * i + j].setBackground(Color.BLACK);
                    } else if (cellularStage2[i][j] == -1) {
                        bottoni[50 * i + j].setBackground(Color.RED);
                    } else if (cellularStage2[i][j] == 2) {
                        bottoni[50 * i + j].setBackground(Color.BLUE);
                    }
                    bottoni[50 * i + j].setBorder(defaultBorder);
                    cellularStage2[i][j] = 0;
                }
            }
            timer.stop();
            timer2.start();
        }
        
        //Set colors of the cells for state 1
        if (e.getSource() == timer2) {
            for (int i = 0; i < nBottoni; i++) {
                if (bottoni[i].getBackground() == Color.RED) {
                    bottoni[i].setBackground(Color.WHITE);
                } else if (bottoni[i].getBackground() == Color.BLUE) {
                    bottoni[i].setBackground(Color.BLACK);
                }
                
                if (bottoni[i].getBackground() == Color.BLACK) {
                    cellularStage1[i / 50][i % 50] = 1;
                } else {
                    cellularStage1[i / 50][i % 50] = 0;
                }
            }
            timer.start();
            timer2.stop();
        }
        
        //Start the timer
        if (e.getSource() == start) {
            timer.start();
        }
        
        if (e.getSource() == stop) {
            timer.stop();
            timer2.stop();
        }
        
        if (e.getSource() == reset) {
            for (int i = 0; i < 50; i++) {
                for (int j = 0; j < 50; j++) {
                    cellularStage1[i][j] = 0;
                    cellularStage2[i][j] = 0;
                    bottoni[i*50+j].setBackground(Color.WHITE);
                }
            }
            timer.stop();
            timer2.stop();
        }
        
        if (e.getSource() == close) {
            System.exit(0);
        }
        
    }
    
    public void controlloVicini() {
        int xVicino;
        int yVicino;
        for (int i = 1; i < 49; i++) {
            for (int j = 1; j < 49; j++) {
                
                yVicino = i - 1;
                xVicino = j - 1;
                viciniAttivi = 0;
                
                for (int k = 0; k < 9; k++) {
                    //Count if nearest cells are active or not
                    if ((cellularStage1[yVicino][xVicino] == 1) && (bottoni[50 * yVicino + xVicino] != bottoni[50 * i + j])) {
                        viciniAttivi++;
                    }
                    if (k == 2 || k == 5) {
                        yVicino++;
                        xVicino -= 2;
                    } else {
                        xVicino++;
                    }
                }
                //-1 --> dies
                //0  --> deactivated
                //1  --> activated
                //2  --> born
                if (cellularStage1[i][j] == 0 && viciniAttivi == 3) {
                    cellularStage2[i][j] = 2;
                } else if ((cellularStage1[i][j] == 1 && viciniAttivi == 2) || (cellularStage1[i][j] == 1 && viciniAttivi == 3)) {
                    cellularStage2[i][j] = 1;
                } else if ((cellularStage1[i][j] == 1 && viciniAttivi != 2) || (cellularStage1[i][j] == 1 && viciniAttivi != 3)) {
                    cellularStage2[i][j] = -1;
                }
                
            }
            
        }
    }
}
