package survival;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Pannello extends JFrame implements ActionListener, KeyListener {
    
    JButton[] bottoni;
    final int nBottoni = 50*50;
    int cellularStage1[][];
    int cellularStage2[][];
    Timer timer;
    int viciniAttivi;
    
    public Pannello(){
        super("Sopravvivenza Cellulare");
        setSize(900, 900);
        setLayout(new GridLayout(50, 50));
        timer = new Timer(1000, this);
        
        
        cellularStage1 = new int[50][50];
//        cellularStage2 = new int[50][50];
        
        bottoni = new JButton[nBottoni];
        
        for (int i = 0; i < nBottoni; i++) {
            bottoni[i] = new JButton();
            add(bottoni[i]);
//            bottoni[i].setBorder(b);
            
            bottoni[i].addActionListener(this);
        }
        
        for (int i = 0; i < 50; i++) {
            for (int j = 0; j < 50; j++) {
                cellularStage1[i][j] = 0;
            }
        }
        
        cellularStage2 = cellularStage1;
        
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        
        
        
        for (int i = 0; i < 50*50; i++) {
            if(e.getSource() == bottoni[i]){
                System.out.println("Bottone premuto = " + i);
                bottoni[i].setBorder(null);
                bottoni[i].setBackground(Color.BLACK);
                int x = (int) i/50;
                int y = (i%50);
                cellularStage1[x][y] = 1;
                System.out.println(x);
                System.out.println(y);
            }
        }
        
        if(e.getSource() == timer){
            controlloVicini();
            for (int i = 0; i <50; i++) {
                for (int j = 0; j < 50; j++) {
                    if(cellularStage2[i][j] == 0){
                        bottoni[i*j].setBackground(Color.WHITE);
                    } else if (cellularStage2[i][j] == 1) {
                        bottoni[i*j].setBackground(Color.BLACK);
                    } else if (cellularStage2[i][j] == -1) {
                        bottoni[i*j].setBackground(Color.ORANGE);
                    } else if (cellularStage2[i][j] == 2) {
                        bottoni[i*j].setBackground(Color.BLUE);
                    }
                }
            }
        }
        
        if(e.getSource() == bottoni[2499]){
            timer.start();
        }
        
        if(e.getSource() == bottoni[2498]){
            for (int i = 0; i < 50; i++) {
                for (int j = 0; j < 50; j++) {
                    System.out.print(cellularStage1[i][j]);
                }
                System.out.println();
            }
        }
        
    }
    
    @Override
    public void keyTyped(KeyEvent e) {
    
    }
    
    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() ==  KeyEvent.VK_ENTER) {
            if (!timer.isRunning()) {
                timer.start();
                System.out.println("PARTI");
            }
        }
    }
    
    @Override
    public void keyReleased(KeyEvent e) {
    
    }
    
    public void controlloVicini(){
        //-1 --> muore
        //0  --> disattivato
        //1  --> attivato
        //2  --> nasce
        int xVicino;
        int yVicino;
        for (int i = 1; i <50; i++) {
            for (int j = 1; j < 50; j++) {
                viciniAttivi = 0;
                xVicino = i-1;
                yVicino = j-1;
                
                //cellulaSelezionata = 50*i + j;
                //cellulaVicina = 50*xVicino + yVicino;
                
                for (int k = 0; k < 9; k++) {
                    if (cellularStage1[i][j] == 1 && cellularStage1[xVicino][yVicino] != cellularStage1[i][j] ){
                        viciniAttivi++;
                    }
                    if(k == 2 || k == 5){
                        xVicino++;
                        yVicino -= 2;
                    }
                    yVicino++;
                }
                if(viciniAttivi == 3 && cellularStage1[i][j] == 0){
                    cellularStage2[i][j] = 2;
                }else if(viciniAttivi == 2 || viciniAttivi == 3 && cellularStage1[i][j] == 1){
                    cellularStage2[i][j] = 1;
                } else if(viciniAttivi != 2 || viciniAttivi != 3 && cellularStage1[i][j] == 1){
                    cellularStage2[i][j] = -1;
                }
                
            }
        }
        
        
    }
}
