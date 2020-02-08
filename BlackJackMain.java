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
	private int roundScore;
	private int balance;
	
	public int getRoundScore() {
		return roundScore;
	}
	
	public void addRoundScore(int addToRoundScore) {
		roundScore += addToRoundScore;
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
		Deck.shuffledDeck.remove(eersteKaart);
		
		System.out.printf("Jouw kaarten zijn: %s %s en een %S %S",
						deKaarten[eersteKaart].getSuitName(),
						deKaarten[eersteKaart].getValueName(),
						deKaarten[tweedeKaart].getSuitName(),
						deKaarten[tweedeKaart].getValueName()
						);
		int firstCardsValue = deKaarten[eersteKaart].getWaarde() + deKaarten[tweedeKaart].getWaarde();
		System.out.printf("%nJouw kaarten hebben een waarde van: %d", firstCardsValue);
	}
	
	void speelRonden(){
		Random r = new Random();
		boolean speelRonde = true;
		
		for(int i = 0; speelRonde ; i++) {
			System.out.println("\n\nDruk (K) voor nieuwe kaart, (P) om te passen of (Q) om te stoppen");
			System.out.print("uw invoer:   ");
			String invoer = sc.next().toLowerCase();
			if(invoer.equals("q")) {
				break;
			}
			if(invoer.equals("k")) {
				Integer welkeKaart = Deck.shuffledDeck.get(0);
				Deck.shuffledDeck.remove(welkeKaart);
				System.out.println("De kaart die ik krijg is:  "+ deKaarten[welkeKaart].getSuitName()+ deKaarten[welkeKaart].getValueName());
				System.out.println(Deck.shuffledDeck.toString());
			}
			if(invoer.equals("p")) {
				System.out.println("Je hebt gepast");
				speelRonde = false;
			}
		}
	}

}