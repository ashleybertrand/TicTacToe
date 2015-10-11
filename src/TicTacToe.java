
import javax.swing.*;


/**
 * TicTacToe with computer strategy
 * 
 * @author bertrand
 * @version 25 Jan 2015
 */
public class TicTacToe extends JFrame {
    //constructor
    public TicTacToe() {
        super("Tic-Tac-Toe");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().add(new TTTPanel());
        pack();
        setLocationRelativeTo(null);    //put the gui in the center of the screen
        setVisible(true);
    }

    public static void main(String[] args) {
        TicTacToe newGame = new TicTacToe(); 
    }
}