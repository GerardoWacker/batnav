package batnav.ui.screens;

import batnav.instance.Game;
import batnav.online.match.Bomb;
import batnav.online.match.Match;
import batnav.online.session.User;
import batnav.ui.boards.OpponentBoard;
import batnav.ui.boards.PlayerBoard;
import batnav.utils.Colour;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MatchScreen extends JFrame implements ActionListener {
    private Match mockMatch;

    private OpponentBoard opponentBoard;
    private PlayerBoard playerBoard;

    public MatchScreen()
    {
        this.mockMatch = new Match("tu vieja", new User("tu viejo", "AR", 9, 1000, false));

        this.mockMatch.addOpponentBomb(new Bomb(4, 4, true, false));
        this.mockMatch.addOpponentBomb(new Bomb(5, 5, true, true));
        this.mockMatch.addOpponentBomb(new Bomb(6, 6, true, false));

        this.mockMatch.addPlayerBomb(new Bomb(4, 5, true, false));
        this.mockMatch.addPlayerBomb(new Bomb(4, 6, true, false));
        this.mockMatch.addPlayerBomb(new Bomb(4, 7, true, false));

        this.opponentBoard = new OpponentBoard(this.mockMatch);
        this.playerBoard = new PlayerBoard(this.mockMatch);
        this.opponentBoard.setPreferredSize(new Dimension(380, 390));
        this.playerBoard.setPreferredSize(new Dimension(380, 390));
        this.setSize(400,820);
        this.setLayout(new BorderLayout());
        this.setResizable(false);

        JPanel divisionPanel = new JPanel();
        divisionPanel.setPreferredSize(new Dimension(400,100));
        divisionPanel.setBackground(Colour.Red);
        this.add(opponentBoard,BorderLayout.SOUTH);
        this.add(divisionPanel,BorderLayout.CENTER);
        this.add(playerBoard,BorderLayout.NORTH);

        this.setVisible(true);
    }

    public static void main(String[] args) {
        new MatchScreen();
    }
    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
