import java.util.Random;
import java.util.Scanner;
import java.util.ArrayList;

public class BlackJackMain {
	public static void main(String[] args) {
		HetSpel spel = new HetSpel();
		
		spel.verkrijgKaarten();
		
		Deck.getDeck();
		Deck.shuffleDeck();
		
		spel.giveFirstCards();
		spel.speelRonden();
	}

	
}

class Kaart{
	private String suit;
	private String value;
	private int waarde;
	
	public Kaart(String suit, String value, int waarde) {
		this.suit = suit;
		this.value = value;
		this.waarde = waarde;
	}
	
	public String getSuitName() {
		return suit;
	}
	
	public String getValueName() {
		return value;
	}
	
	public int getWaarde() {
		return waarde;
	}
	
}

class Deck {
	private static final int DECK_SIZE = 52;
	static ArrayList<Integer> deck = new ArrayList<Integer>();
	static ArrayList<Integer> shuffledDeck = new ArrayList<Integer>();
	
	public static void getDeck() {
		for (int i = 0; i < DECK_SIZE; ++i) {
            deck.add(i);
        }
	}
	
	public static void shuffleDeck() {
		while (deck.size() > 0) {
            int index = (int) (Math.random() * deck.size());
            shuffledDeck.add(deck.remove(index));
        }
	}

}

class Player {
	private String name;
	private static int roundScore;
	private int balance;
	private static ArrayList<Integer> spelerKaarten = new ArrayList<Integer>();
	
	public static int getRoundScore() {
		return roundScore;
	}
	
	public static void addRoundScore(int addToRoundScore) {
		if (roundScore + addToRoundScore > 21) {
			// check of aas is in spel
			roundScore += addToRoundScore - 10;
		} else {
			roundScore += addToRoundScore;
		}
	}
	
	public static void addKaart(int verkregenKaart) {
		spelerKaarten.add(verkregenKaart);
	}
	
	public static ArrayList<Integer> getSpelerKaarten() {
		return spelerKaarten;
	}
	
}

class HetSpel {
	Scanner sc = new Scanner(System.in);
	Kaart[] deKaarten = new Kaart[52];
	String[] suits = {"Hearts", "Spades", "Diamonds","Clubs"};
	String[] values = {"2", "3", "4","5","6", "7", "8","9","10", "J", "Q","K","A"};
	int [] waarden = {2,3,4,5,6,7,8,9,10,10,10,10,11};
	
	void verkrijgKaarten() {
		int totaalTeller = 0;
		for( int x = 0 ; x < suits.length ; x++ ) {
			for( int y = 0 ; y < values.length ; y++ ) {
				deKaarten[totaalTeller] = new Kaart(suits[x], values[y], waarden[y]);
				totaalTeller++;
			}
		}
	}
	
	void giveFirstCards() {
		Integer eersteKaart = Deck.shuffledDeck.get(0);
		Deck.shuffledDeck.remove(eersteKaart);
		Integer tweedeKaart = Deck.shuffledDeck.get(0);
		Deck.shuffledDeck.remove(tweedeKaart);
		
		Player.addKaart(eersteKaart);
		Player.addKaart(tweedeKaart);
		
		
		System.out.printf("Jouw kaarten zijn: %s%s en een %s%s",
						deKaarten[eersteKaart].getSuitName(),
						deKaarten[eersteKaart].getValueName(),
						deKaarten[tweedeKaart].getSuitName(),
						deKaarten[tweedeKaart].getValueName()
						);
		Player.addRoundScore(deKaarten[eersteKaart].getWaarde() + deKaarten[tweedeKaart].getWaarde());
		System.out.printf("%nJouw kaarten hebben een totale waarde van: %d", Player.getRoundScore());
	}
	
	void speelRonden(){
		final int MAX_SCORE = 21;
		boolean speelRonde = true;
		
		while (MAX_SCORE > Player.getRoundScore() && speelRonde) {
			System.out.println("\n\nDruk (K) voor nieuwe kaart, (P) om te passen of (Q) om te stoppen");
			System.out.print("uw invoer:   ");
			String invoer = sc.next().toLowerCase();
			if(invoer.equals("q")) {
				System.out.printf("Het spel is gestopt");
				break;
			}
			if(invoer.equals("k")) {
				Integer welkeKaart = Deck.shuffledDeck.get(0);
				Deck.shuffledDeck.remove(welkeKaart);
				Player.addKaart(welkeKaart);
				System.out.printf("De kaart die je krijgt is een:  %s%s%n",
				deKaarten[welkeKaart].getSuitName(),
				deKaarten[welkeKaart].getValueName());
				Player.addRoundScore(deKaarten[welkeKaart].getWaarde());
				
			}
			if(invoer.equals("p")) {
				System.out.println("Je hebt gepast");
				speelRonde = false;
			}
			if (Player.getRoundScore() > MAX_SCORE) {
				System.out.println("Je hebt meer dan 21: You busted");
			}
			
			System.out.printf("Jouw hand bestaat uit: ");
			for (int num : Player.getSpelerKaarten()) {
				System.out.printf("%s%s ",deKaarten[num].getSuitName().charAt(0),deKaarten[num].getValueName());
			}
			System.out.printf("%nJouw kaarten hebben een totale waarde van: %d", Player.getRoundScore());
		}
	}

}