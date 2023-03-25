package com.modern.oop;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class FrenchDeck {

    private static final String[] RANKS = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "A"};
    private static final String[] SUITS = {"Spades", "Diamonds", "Clubs", "Hearts"};
    private final List<Card> cards;

    public FrenchDeck() {
        cards = new ArrayList<>();
        for (var suit : SUITS) {
            for (var rank : RANKS) {
                cards.add(new Card(rank, suit));
            }
        }
    }

    public static void main(String[] args) {
        var deck1 = new FrenchDeck();
        var deck2 = new FrenchDeck();

        System.out.println("Deck 1 size: " + deck1.size());
        System.out.println("Deck 2 size: " + deck2.size());

        deck1.shuffle();
        System.out.println("Shuffled deck 1: " + deck1);
        Collections.sort(deck1.cards);
        for (Card c : deck1.cards) {
            System.out.println(c);
        }
    }

    public int size() {
        return cards.size();
    }

    public Card getCard(int index) {
        return cards.get(index);
    }

    public void shuffle() {
        Collections.shuffle(cards);
    }

    enum Suit {
        Spades, Diamonds, Clubs, Hearts;

        public static int compareTo(Suit suite, Suit another) {
            return Integer.compare(suite.ordinal(), another.ordinal());
        }
    }

    public record Card(String rank, String suit) implements Comparable<Card> {
        public String toString() {
            return rank + " of " + suit;
        }

        public Suit getSuit() {
            return Suit.valueOf(suit);
        }

        public Integer getRank() {
            if (rank.length() == 1 && Character.isDigit(rank.charAt(0))) return rank.charAt(0) - '0';
            if (rank.length() == 2) return 10;
            switch (rank) {
                case "J":
                    return 11;
                case "K":
                    return 12;
                case "Q":
                    return 13;
                case "A":
                    return 14;
            }

            throw new IllegalStateException("IllegalStateException");
        }

        @Override
        public int compareTo(Card card) {
            Objects.requireNonNull(card);
            int value = Integer.compare(getRank(), card.getRank());
            if (value != 0) return value;
            return Suit.compareTo(this.getSuit(), card.getSuit());
        }
    }
}
