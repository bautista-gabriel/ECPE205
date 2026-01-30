import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.util.*;


public class tictactoe {
    public static void main(String[]args) {
        JFrame frame = new JFrame("Player Select");
        Container container = frame.getContentPane();
        container.setLayout(new GridBagLayout());
        int playerNumX =1;
        int playerNumO=2;
        JLabel choose_player = new JLabel("Select Player");
        choose_player.setFont(new Font(Font.SERIF,Font.BOLD,100));
        JButton PlayerX = new JButton("Player X");
        PlayerX.setFont(new Font(Font.SERIF,Font.BOLD,50));
        JButton PlayerO = new JButton("Player O");
        PlayerO.setFont(new Font(Font.SERIF,Font.BOLD,50));
        addComponents(0,2,1,1,1,1,GridBagConstraints.NONE,frame,PlayerX);
        addComponents(2,2,1,1,1,1,GridBagConstraints.NONE,frame,PlayerO);
        addComponents(1,0,1,1,1,1,GridBagConstraints.NONE,frame,choose_player);


        PlayerX.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame2(playerNumX,playerNumO);
            }
        });

        PlayerO.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame2(playerNumO,playerNumX);
            }
        });
        //  JPanel panel = new JPanel();
        //  panel.setLayout(new GridLayout(3,3));
        //  panel.setPreferredSize(new Dimension(500,500));
        frame.setSize(1920,1080);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);


    }


    public static void addComponents(int gridx, int gridy, int width, int height, double weightx, double weighty, int fill, JFrame frame,Component c ){
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx=gridx;
        constraints.gridy=gridy;
        constraints.gridwidth=width;
        constraints.gridheight=height;
        constraints.weightx=weightx;
        constraints.weighty=weighty;
        constraints.fill=fill;
        frame.add(c,constraints);
    }
    public static void frame2(int player1, int player2){
        JFrame frame2 = new JFrame("Tic Tac Toe");
        Container container = frame2.getContentPane();
        container.setLayout(new GridBagLayout());
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3,3));
        panel.setPreferredSize(new Dimension(500,500));

        int[]playerturn={1};
        int[]xscore={0};
        int[]oscore={0};

        Queue<Object>qX = new LinkedList<>();
        Queue<Object>qO = new LinkedList<>();

        final int[] counterX = {0};
        final int[] counterO = {0};

        JButton newGame = new JButton("New Game");
        newGame.setPreferredSize(new Dimension(200,100));
        newGame.setFont(new Font(Font.SERIF,Font.BOLD,30));

        JLabel XLabel = new JLabel("Player " + player1 +" X Score: " + xscore[0]);
        XLabel.setFont(new Font(Font.SERIF,Font.BOLD,50));

        JLabel OLabel = new JLabel("Player "+ player2 + " O Score: "+ oscore[0]);
        OLabel.setFont(new Font(Font.SERIF,Font.BOLD,50));

        JLabel TurnLabel = new JLabel("It is Player X's turn");
        TurnLabel.setFont(new Font(Font.SERIF,Font.BOLD,50));

        addComponents(0,0,3,3,0,0,GridBagConstraints.NONE,frame2,panel);
        addComponents(0,3,1,1,1,1,GridBagConstraints.NONE,frame2,XLabel);
        addComponents(2,3,1,1,1,1,GridBagConstraints.NONE,frame2,OLabel);
        addComponents(1,3,1,1,1,1,GridBagConstraints.NONE,frame2,newGame);
        addComponents(1,4,1,1,1,1,GridBagConstraints.NONE,frame2,TurnLabel);




        JButton[][]grid = new JButton[3][3];


        for(int i=0;i<3;i++){
            for(int j=0; j<3;j++){
                JButton b = new JButton("");
                b.setPreferredSize(new Dimension(100,100));
                grid[i][j]=b;
                b.setFont(new Font(Font.SERIF,Font.BOLD,50));
                panel.add(b);

                b.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if(b.getText().isEmpty()){
                            if(playerturn[0]==1){
                                b.setText("X");
                                playerturn[0]=0;
                                counterX[0]++;
                                qX.add(b);
                                System.out.println("x count: " + counterX[0]);
                                if(counterX[0] > 3){
                                    JButton  b1= (JButton) qX.remove();
                                    b1.setText("");
                                }
                                checker(grid,XLabel,xscore,OLabel,oscore,playerturn,player1,player2,counterX,counterO,qX,qO);
                                turn(playerturn,TurnLabel);
                                finalWinner(xscore,oscore);
                            }else if(playerturn[0]==0){
                                b.setText("O");
                                playerturn[0]=1;

                                counterO[0]++;
                                qO.add(b);
                                System.out.println("o count: " + counterO[0]);
                                if(counterO[0] > 3){
                                    JButton b2= (JButton) qO.remove();
                                    b2.setText("");
                                }
                                checker(grid,XLabel,xscore,OLabel,oscore,playerturn,player1,player2,counterX,counterO,qX,qO);
                                turn(playerturn,TurnLabel);
                                finalWinner(xscore,oscore);
                            }
                        }
                    }
                });







            }
        }

        newGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clear(grid);
                xscore[0]=0;
                oscore[0]=0;
                XLabel.setText("X Score: " + xscore[0]);
                OLabel.setText("O Score: "+ oscore[0]);
                playerturn[0]=1;
                counterX[0]=0;
                counterO[0]=0;
            }
        });
        frame2.setSize(1920,1080);
        frame2.setVisible(true);
        frame2.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }






    public static void removePrev(JButton[][] b, int row, int col){
        b[row][col].setText("");
    }


    public static void checker(JButton[][] b, JLabel XLabel, int[]XScore, JLabel OLabel, int[]OScore, int[]playerturn, int player1, int player2, int[] counterX, int[] counterO, Queue<Object> qx, Queue<Object>qo){
        //x WIN conditions
        if(b[0][0].getText().equals("X") && b[0][1].getText().equals("X") && b[0][2].getText().equals("X")){
            JOptionPane.showMessageDialog(null,"Player X wins this Round!");
            XWins(b,XLabel,XScore,playerturn,player1,player2,counterX,counterO,qx,qo);
        }else if(b[1][0].getText().equals("X") && b[1][1].getText().equals("X") && b[1][2].getText().equals("X")) {
            JOptionPane.showMessageDialog(null, "Player X wins this Round!");
            XWins(b,XLabel,XScore,playerturn,player1,player2,counterX,counterO,qx,qo);
        }else if (b[2][0].getText().equals("X") && b[2][1].getText().equals("X") && b[2][2].getText().equals("X")) {
            JOptionPane.showMessageDialog(null, "Player X wins this Round!");
            XWins(b,XLabel,XScore,playerturn,player1,player2,counterX,counterO,qx,qo);
        }else if(b[0][0].getText().equals("X") && b[1][0].getText().equals("X") && b[2][0].getText().equals("X")) {
            JOptionPane.showMessageDialog(null, "Player X wins this Round!");
            XWins(b,XLabel,XScore,playerturn,player1,player2,counterX,counterO,qx,qo);
        }else if (b[0][1].getText().equals("X") && b[1][1].getText().equals("X") && b[2][1].getText().equals("X")) {
            JOptionPane.showMessageDialog(null, "Player X wins this Round!");
            XWins(b,XLabel,XScore,playerturn,player1,player2,counterX,counterO,qx,qo);
        }else if(b[0][2].getText().equals("X") && b[1][2].getText().equals("X") && b[2][2].getText().equals("X")) {
            JOptionPane.showMessageDialog(null, "Player X wins this Round!");
            XWins(b,XLabel,XScore,playerturn,player1,player2,counterX,counterO,qx,qo);
        }else if (b[0][0].getText().equals("X") && b[1][1].getText().equals("X") && b[2][2].getText().equals("X")) {
            JOptionPane.showMessageDialog(null, "Player X wins this Round!");
            XWins(b,XLabel,XScore,playerturn,player1,player2,counterX,counterO,qx,qo);
        }else if(b[2][0].getText().equals("X") && b[1][1].getText().equals("X") && b[0][2].getText().equals("X")) {
            JOptionPane.showMessageDialog(null, "Player X wins this Round!");
            XWins(b,XLabel,XScore,playerturn,player1,player2,counterX,counterO,qx,qo);
        }


        //O Win Conditions


        else if(b[0][0].getText().equals("O") && b[0][1].getText().equals("O") && b[0][2].getText().equals("O")){
            JOptionPane.showMessageDialog(null,"Player O wins this Round!");
            OWins(b,OLabel,OScore,playerturn,player1,player2,counterX,counterO,qx,qo);
        }else if(b[1][0].getText().equals("O") && b[1][1].getText().equals("O") && b[1][2].getText().equals("O")) {
            JOptionPane.showMessageDialog(null, "Player O wins this Round!");
            OWins(b,OLabel,OScore,playerturn,player1,player2,counterX,counterO,qx,qo);
        }else if (b[2][0].getText().equals("O") && b[2][1].getText().equals("O") && b[2][2].getText().equals("O")) {
            JOptionPane.showMessageDialog(null, "Player O wins this Round!");
            OWins(b,OLabel,OScore,playerturn,player1,player2,counterX,counterO,qx,qo);
        }else if(b[0][0].getText().equals("O") && b[1][0].getText().equals("O") && b[2][0].getText().equals("O")) {
            JOptionPane.showMessageDialog(null, "Player O wins this Round!");
            OWins(b,OLabel,OScore,playerturn,player1,player2,counterX,counterO,qx,qo);
        }else if (b[0][1].getText().equals("O") && b[1][1].getText().equals("O") && b[2][1].getText().equals("O")) {
            JOptionPane.showMessageDialog(null, "Player O wins this Round!");
            OWins(b,OLabel,OScore,playerturn,player1,player2,counterX,counterO,qx,qo);
        }else if(b[0][2].getText().equals("O") && b[1][2].getText().equals("O") && b[2][2].getText().equals("O")) {
            JOptionPane.showMessageDialog(null, "Player O wins this Round!");
            OWins(b,OLabel,OScore,playerturn,player1,player2,counterX,counterO,qx,qo);
        }else if (b[0][0].getText().equals("O") && b[1][1].getText().equals("O") && b[2][2].getText().equals("O")) {
            JOptionPane.showMessageDialog(null, "Player O wins this Round!");
            OWins(b,OLabel,OScore,playerturn,player1,player2,counterX,counterO,qx,qo);
        }else if(b[2][0].getText().equals("O") && b[1][1].getText().equals("O") && b[0][2].getText().equals("O")) {
            JOptionPane.showMessageDialog(null, "Player O wins this Round!");
            OWins(b,OLabel,OScore,playerturn,player1,player2,counterX,counterO,qx,qo);
        }


        //DRAW


        else if(!b[0][0].getText().isEmpty() && !b[0][1].getText().isEmpty() && !b[0][2].getText().isEmpty() &&
                !b[1][0].getText().isEmpty() && !b[1][1].getText().isEmpty() && !b[1][2].getText().isEmpty() &&
                !b[2][0].getText().isEmpty() && !b[2][1].getText().isEmpty() && !b[2][2].getText().isEmpty()){
            JOptionPane.showMessageDialog(null,"Draw!");
            playerturn[0]=1;
            clear(b);


        }


    }


    public static void clear(JButton[][] b){
        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                b[i][j].setText("");
            }
        }
    }


    public static void XWins(JButton[][]b, JLabel XLabel, int[]XScore, int[]playerturn,int player1, int player2,int[] counterX, int[] counterO, Queue<Object> qx, Queue<Object> qo ){
        XScore[0]++;
        XLabel.setText("Player " + player1 +" X Score: " + XScore[0]);
        clear(b);
        playerturn[0]=1;
        counterX[0] = 0;
        counterO[0] = 0;
        qx.clear();
        qo.clear();
    }


    public static void OWins(JButton[][]b, JLabel OLabel, int[]OScore, int[]playerturn,int player1, int player2, int[] counterX, int[]counterO, Queue<Object> qx, Queue<Object> qo ){
        OScore[0]++;
        OLabel.setText("Player "+ player2 + " O Score: "+ OScore[0]);
        clear(b);
        playerturn[0]=1;
        counterX[0] = 0;
        counterO[0] = 0;
        qx.clear();
        qo.clear();
    }


    public  static void finalWinner(int[] xscore, int[] oscore){
        JFrame frame3 = new JFrame("Winner X");
        JFrame frame4 = new JFrame("Winner O");
        Container container = frame3.getContentPane();
        container.setLayout(new GridBagLayout());
        Container container1 = frame4.getContentPane();
        container1.setLayout(new GridBagLayout());


        JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayout(3,3));
        panel1.setPreferredSize(new Dimension(500,500));


        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3,3));
        panel.setPreferredSize(new Dimension(500,500));


        JLabel Xwin = new JLabel("Player X is the Winner!");
        JLabel Owin = new JLabel("Player O is the Winner!");


        JButton newGame = new JButton("New Game");


        newGame.setPreferredSize(new Dimension(100,100));
        newGame.setFont(new Font(Font.SERIF,Font.BOLD,50));


        Xwin.setFont(new Font(Font.SERIF,Font.BOLD,50));
        Owin.setFont(new Font(Font.SERIF,Font.BOLD,50));
        addComponents(0,2,3,3,0,0,GridBagConstraints.NONE,frame3,panel1);
        addComponents(0,2,3,3,0,0,GridBagConstraints.NONE,frame4,panel);
        addComponents(0,0,1,1,1,1,GridBagConstraints.NONE,frame3,Xwin);
        addComponents(0,0,1,1,1,1,GridBagConstraints.NONE,frame4,Owin);


        if(xscore[0]==3){
            panel1.add(newGame);


            newGame.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    main(new String[0]);
                }
            });
            frame3.setSize(1920,1080);
            frame3.setVisible(true);
            frame3.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        } else if(oscore[0]==3){
            panel.add(newGame);
            newGame.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    main(new String[0]);
                }
            });
            frame4.setSize(1920,1080);
            frame4.setVisible(true);
            frame4.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        }


    }


    public static void turn(int[] playerturn, JLabel TurnLabel){
        if (playerturn[0]==1){ //X turn
            TurnLabel.setText("It is Player X's turn");


        }else if(playerturn[0]==0){ //O Turn
            TurnLabel.setText("It is Player O's turn");


        }
    }
}

