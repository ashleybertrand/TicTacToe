
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.*;

/**
 * TTTPanel will display and hold the logic for the game.
 *
 * @author bertrand
 * @version 25 Jan 2015
 */
public class TTTPanel extends JPanel {

    //board dimensions
    private int size = 300;
    
    // back-end: board data
    private String curPlayer;
    private String[][] board;
    private boolean won = false;
    private boolean blocked = false;
    private boolean moved = false;
        

    //constructor
    public TTTPanel() {
        setPreferredSize(new Dimension(size, size));
        addMouseListener(new TTTMouseListener());

        //back-end:  initialize the board
        board = new String[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = " ";
            }
        }
        curPlayer = "X";
    }

    //back-end to front-end.  Get data from board and draw the GUI
    public void paint(Graphics g) {
        g.setColor(Color.GREEN);
        g.fillRect(0, 0, getWidth(), getHeight());

        g.setColor(Color.BLUE);
        g.drawLine(0, 100, 300, 100);   //first horizontal line
        g.drawLine(0, 200, 300, 200);   //second horizontal line
        g.drawLine(100, 0, 100, 300);   //first vertical line
        g.drawLine(200, 0, 200, 300);   //second vertical line

        Font f = new Font("Times", Font.PLAIN, 50);
        g.setFont(f);
        FontMetrics fm = g.getFontMetrics();

        int a = fm.getAscent();
        int h = fm.getHeight();

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                String curSquare = board[i][j];
                int w = fm.stringWidth(curSquare);
                g.drawString(curSquare, 100 * i + 50 - w / 2, 100 * j + 50 + a - h / 2);
            }
        }
    }
    
    // INNER CLASS for a Mouse events:
    private class TTTMouseListener implements MouseListener {

        @Override
        public void mousePressed(MouseEvent e) {
            //System.out.println("press");
        }

        public void mouseReleased(MouseEvent e) {
            //System.out.println("release");
        }

        public void mouseEntered(MouseEvent e) {
            //System.out.println("mouse entered");
        }

        public void mouseExited(MouseEvent e) {
            //System.out.println("mouse exited");
        }

        //front-end to back-end.
        public void mouseClicked(MouseEvent e) {
            //get click data from the GUI and convert to back end board spot reference
            int x = e.getX() / 100;
            int y = e.getY() / 100;
            //process click
            if (!checkForWin("X") && !checkForWin("O") && won == false) {
                if (" ".equals(board[x][y])) {
                    board[x][y] = curPlayer;
                    repaint();
                    checkForGameEnd();
                    computerWin();
                    computerBlock();
                    if (blocked == false) {
                        computerMove();
                    }
                }
                checkForGameEnd();
            }
            checkForGameEnd();
        }
    }
    
    //back-end
    //first priority - the computer will win if it has the opportunity to do so
    public void computerWin() {
        for (int i=0; i<3; i++) {
            for (int j=0; j<3; j++) {
                if (" ".equals(board[i][j])) {
                    //if there is a spot where a new O can be placed for O to win then O will move there to win
                    board[i][j]="O";
                    if (checkForWin("O")) {
                        won = true;
                        checkForGameEnd();
                    }
                    //otherwise set that spot back to blank
                    else {
                        board[i][j]=" ";
                        won = false;
                    } 
                }
            }
        }    
    }
    
    //back-end
    //second priority - the computer will block if the user is one spot away from winning
    public void computerBlock() {
        boolean finished = false;
        for (int i=0; i<3 && finished == false; i++) {
            for (int j=0; j<3 && finished == false; j++) {
                if (" ".equals(board[i][j])) {
                    //if there is a spot where a new X can be placed for X to win then O will move there to block
                    board[i][j]="X";
                    if (checkForWin("X")) {
                        blocked = true;
                        board[i][j]="O";
                        finished = true;
                    }
                    //otherwise set that spot back to blank
                    else {
                        board[i][j]=" ";
                        blocked = false;
                    }
                }
            }
        }  
    }
    
    //back-end
    //last priority - the computer moves randomly
    public void computerMove() {
        moved = false;
        while (!moved) {
            //generates a random int 0, 1, or 2
            int i = (int) (Math.random()*3.0);      
            int j = (int) (Math.random()*3.0);      
            //O is placed in a random open spot
            if(" ".equals(board[i][j])) {
                board[i][j] = "O";
                moved = true;
            } 
        }    
    }
    
    //back-end
    private boolean checkForWin(String p) {
        boolean win = false;
        
        // check row wins:
        for (int i = 0; i < 3; i++) {
            win = win || (p.equals(board[i][0]) && p.equals(board[i][1]) && p.equals(board[i][2]));
        }
        // check column wins:
        for (int j = 0; j < 3; j++) {
            win = win || (p.equals(board[0][j]) && p.equals(board[1][j]) && p.equals(board[2][j]));
        }
        // check diagonal wins:
        win = win || (p.equals(board[0][0]) && p.equals(board[1][1]) && p.equals(board[2][2]));
        win = win || (p.equals(board[0][2]) && p.equals(board[1][1]) && p.equals(board[2][0]));

        return win;
    }

    //back-end
    private boolean checkFullBoard() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (" ".equals(board[i][j]))
                    return false;
            }
        }
        return true;
    }

    public void checkForGameEnd() {
        if (checkForWin("X")) {
            JOptionPane.showMessageDialog(this, "X wins!!!!");
            System.exit(0);
        } else if (checkForWin("O")) {
            JOptionPane.showMessageDialog(this, "O wins!!!!");
            System.exit(0);
        } else if (checkFullBoard()) {
            JOptionPane.showMessageDialog(this, "Game over, draw.");
            System.exit(0);
        }
    }  
}