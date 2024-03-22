import java.lang.reflect.Array;
import java.util.ArrayList;

public class Deck {
    private int remainingCards;
    private String[] values;
    private ArrayList<Card> deck;

    public Deck() {
        remainingCards = 52;
        values = new String[]{"A", "02", "03", "04", "05", "06", "07", "08", "09", "10", "J", "Q", "K"};
        deck = buildDeck();
    }

    public ArrayList<Card> buildDeck() {
        ArrayList<Card> deck = new ArrayList<>();
        String[] suits = {"clubs", "diamonds", "hearts", "spades"};
        for (String s : suits) {
            for (String v : values) {
                Card c = new Card(s, v);
                deck.add(c);
            }
        }
        return deck;
    }

    public ArrayList<Card> buildHand() {
        ArrayList<Card> hand = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            int r = (int)(Math.random()*deck.size());
            Card c = deck.remove(r);
            hand.add(c);
        }
        remainingCards -= 9;
        return hand;
    }

    public int findValue(String str) {
        for (int i = 0; i < values.length; i++) {
            if (values[i].equals(str)) {
                return i + 1;
            }
        }
        return -1;
    }

    public boolean check11() {
        ArrayList<Card> highlightedCards = new ArrayList<>();
        for (Card card : deck) {
            if (card.getHighlight()) {
                highlightedCards.add(card);
            }
        }

        return switch (highlightedCards.size()) {
            case 2 -> findValue(highlightedCards.get(0).getValue()) + findValue(highlightedCards.get(1).getValue()) == 11;
            case 3 -> findValue(highlightedCards.get(0).getValue()) + findValue(highlightedCards.get(1).getValue()) + findValue(highlightedCards.get(2).getValue()) == 36;
            default -> false;
        };
    }

    public Card replaceCard() {
        int r = (int)(Math.random()*deck.size());
        return deck.get(r);
    }

    public int getRemainingCards() {
        return remainingCards;
    }

    public void setRemainingCards(int remainingCards) {
        this.remainingCards = remainingCards;
    }
}
