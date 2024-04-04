import java.util.ArrayList;
import java.util.HashMap;

public class Deck {
    private int remainingCards;
    private String[] values;
    private ArrayList<Card> deck;

    public Deck() {
        remainingCards = 52;
        values = new String[]{"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13"};
        buildDeck();
    }

    public void buildDeck() {
        deck = new ArrayList<>();
        String[] suits = {"clubs", "diamonds", "hearts", "spades"};
        for (String s : suits) {
            for (String v : values) {
                Card c = new Card(s, v);
                deck.add(c);
            }
        }
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

    public int checkEnd(ArrayList<Card> hand) {
        if (remainingCards == 0 && hand.isEmpty()) {
            return 1;
        }

        boolean comboAvailable = false;
        for (int i = 0; i < hand.size(); i++) {
            for (int j = 0; j < hand.size(); j++) {
                int num = Integer.parseInt(hand.get(i).getValue() + hand.get(j).getValue());
                if (i != j && num == 11) {
                    comboAvailable = true;
                }
            }
        }

        if (!comboAvailable){
            return 2;
        }

        return 3;
    }

    public boolean check11(ArrayList<Card> hand) {
        ArrayList<Card> highlightedCards = new ArrayList<>();
        for (Card card : hand) {
            if (card.getHighlight()) {
                highlightedCards.add(card);
            }
        }
        System.out.println(highlightedCards);

        boolean result;
        switch (highlightedCards.size()) {
            case 2 -> result = findValue(highlightedCards.get(0).getValue()) + findValue(highlightedCards.get(1).getValue()) == 11;
            case 3 -> result = findValue(highlightedCards.get(0).getValue()) + findValue(highlightedCards.get(1).getValue()) + findValue(highlightedCards.get(2).getValue()) == 36;
            default -> result = false;
        }

        if (result) {
            for (Card highlightedCard : highlightedCards) {
                deck.remove(highlightedCard);
                if (remainingCards != 0) {
                    hand.set(getHandIndex(hand, highlightedCard), replaceCard());
                    remainingCards--;
                }
                else {
                    hand.remove(getHandIndex(hand, highlightedCard));
                }
            }
        }
        return result;
    }

    public int getHandIndex(ArrayList<Card> hand, Card card) {
        for (int i = 0; i < hand.size(); i++) {
            if (hand.get(i).getValue().equals(card.getValue()) && hand.get(i).getSuit().equals(card.getSuit())) {
                return i;
            }
        }
        return -1;
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
