import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Point;
import java.util.ArrayList;
import java.awt.Font;

class DrawPanel extends JPanel implements MouseListener {

    private ArrayList<Card> hand;
    private Deck deck;
    private Rectangle playAgain;
    private Rectangle replaceCards;

    public DrawPanel() {
        deck = new Deck();
        playAgain = new Rectangle(152, 305, 125, 26);
        replaceCards = new Rectangle(133, 340, 160, 26);
        this.addMouseListener(this);
        hand = deck.buildHand();
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int x = 135;
        int y = 30;
        for (int i = 0; i < hand.size(); i++) {
            Card c = hand.get(i);
            if (i % 3 == 0 && i != 0) {
                x = 135;
                y = y +c.getImage().getHeight() + 25;
            }
            if (c.getHighlight()) {
                g.drawRect(x, y, c.getImage().getWidth(), c.getImage().getHeight());
            }
            c.setRectangleLocation(x, y);
            g.drawImage(c.getImage(), x, y, null);

            x = x + c.getImage().getWidth() + 10;
        }
        g.setFont(new Font("Courier New", Font.BOLD, 20));
        g.drawString("PLAY AGAIN", 180, 325);
        g.drawString("Replace Cards", 160, 360);
        g.drawString("Remaining Cards: " + deck.getRemainingCards(), 130, 388);
        g.drawRect((int)playAgain.getX() + 25, (int)playAgain.getY(), (int)playAgain.getWidth(), (int)playAgain.getHeight());
        g.drawRect((int)replaceCards.getX() + 25, (int)replaceCards.getY(), (int)replaceCards.getWidth(), (int)replaceCards.getHeight());
    }

    public void mousePressed(MouseEvent e) {

        Point clicked = e.getPoint();

        if (e.getButton() == 1) {
            if (playAgain.contains(clicked)) {
                deck.setRemainingCards(52);
                hand = deck.buildHand();
            }

            if (replaceCards.contains(clicked)) {

            }

            for (int i = 0; i < hand.size(); i++) {
                Rectangle box = hand.get(i).getCardBox();
                if (box.contains(clicked)) {
                    hand.get(i).flipCard();
                }
            }
        }

        if (e.getButton() == 3) {
            for (int i = 0; i < hand.size(); i++) {
                Rectangle box = hand.get(i).getCardBox();
                if (box.contains(clicked)) {
                    if (hand.get(i).getHighlight()) {
                        hand.set(i, deck.replaceCard());
                    }
                    else {
                        hand.get(i).flipHighlight();
                    }
                }
            }
        }
    }

    public void mouseReleased(MouseEvent e) { }
    public void mouseEntered(MouseEvent e) { }
    public void mouseExited(MouseEvent e) { }
    public void mouseClicked(MouseEvent e) { }
}