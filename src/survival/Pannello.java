package survival;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import static java.lang.Math.max;
import static java.lang.Math.min;

public class Pannello extends JFrame implements ActionListener, KeyListener {
    
    JButton[] bottoni;
    final int nBottoni = 50*50;
    int cellularStage1[][];
    int cellularStage2[][];
    Timer timer;
    int viciniAttivi;
    Border defaultBorder;
    
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
            bottoni[i].setBackground(Color.WHITE);
            
            bottoni[i].addActionListener(this);
        }
        
        for (int i = 0; i < 50; i++) {
            for (int j = 0; j < 50; j++) {
                cellularStage1[i][j] = 0;
            }
        }
        
        defaultBorder = bottoni[0].getBorder();
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
                        bottoni[50*i+j].setBackground(Color.WHITE);
                    } else if (cellularStage2[i][j] == 1) {
                        bottoni[50*i+j].setBackground(Color.BLACK);
                    } else if (cellularStage2[i][j] == -1) {
                        bottoni[50*i+j].setBackground(Color.RED);
                    } else if (cellularStage2[i][j] == 2) {
                        bottoni[50*i+j].setBackground(Color.BLUE);
                    }
                    bottoni[50*i+j].setBorder(defaultBorder);
                }
            }
        }
        
        if(e.getSource() == bottoni[2499]){
            timer.start();
        }
        
        if(e.getSource() == bottoni[2498]){
            stampa();
        }
        
    }
    
    public void stampa(){
        for (int i = 0; i < 50; i++) {
            for (int j = 0; j < 50; j++) {
                System.out.print(cellularStage1[i][j]+ " ");
            }
            System.out.println();
        }
        System.out.println("----------------------------------------");
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
        int xVicino;
        int yVicino;
        for (int i = 1; i <49; i++) {
            for (int j = 1; j < 49; j++) {


                yVicino = i-1;
                xVicino = j-1;
                viciniAttivi = 0;

                for (int k = 0; k < 9; k++) {
                    //CONTARE VICINI ATTIVI
                    if ((cellularStage1[yVicino][xVicino] == 1) && (cellularStage1[yVicino][xVicino] != cellularStage1[i][j])){
                        viciniAttivi++;
                    }
                    if(k == 2 || k == 5){
                        yVicino++;
                        xVicino -= 2;
                    }else{
                        xVicino++;
                    }
                }
                //-1 --> muore
                //0  --> disattivato
                //1  --> attivato
                //2  --> nasce
                if(cellularStage1[i][j] == 0 && viciniAttivi == 3){
                    cellularStage2[i][j] = 2;
                } else if (cellularStage1[i][j] == 1 && (viciniAttivi == 2 || viciniAttivi == 3)) {
                    cellularStage2[i][j] = 1;
                } else if (cellularStage1[i][j] == 1 && (viciniAttivi != 2 || viciniAttivi != 3)) {
                    cellularStage2[i][j] = -1;
                }
//                stampa();
            }
        }
        
        
//
//        for(int i = 0; i < 50; i++) {
//            for(int j = 0; j < 50; j++) {
//                viciniAttivi = 0;
//                for(int x = max(i-1,0); x < min(50,i+1); x++) {
//                    for(int y = max(i-1,0); y < min(50,i+1); y++) {
//                        if(cellularStage1[x][y] == 1)
//                            viciniAttivi++;
//                    }
//                }
//                if(cellularStage1[i][j] == 0 && viciniAttivi == 3){
//                    cellularStage2[i][j] = 2;
//                } else if (cellularStage1[i][j] == 1 && (viciniAttivi == 2 || viciniAttivi == 3)) {
//                    cellularStage2[i][j] = 1;
//                } else if (cellularStage1[i][j] == 1 && (viciniAttivi != 2 || viciniAttivi != 3)) {
//                    cellularStage2[i][j] = -1;
//                }
//            }
//        }
        
    }
}
