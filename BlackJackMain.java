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
		
		spel.beurtBank();
		
		spel.checkWinnaar();
		
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
	private static boolean heartsAUsed = false;
	private static boolean spadesAUsed = false;
	private static boolean diamondsAUsed = false;
	private static boolean clubsAUsed = false;
	
	private String name;
	private static int roundScore;
	private int balance;
	private static ArrayList<Integer> spelerKaarten = new ArrayList<Integer>();
	
	public static int getRoundScore() {
		return roundScore;
	}
	
	public static void addRoundScore(int addToRoundScore) {
		
		if (roundScore + addToRoundScore > HetSpel.MAX_SCORE) {
			// check of aas is in spel
			if (spelerKaarten.contains(12) && !heartsAUsed) {
				roundScore = roundScore - 10;
				heartsAUsed = true;
			} else if (spelerKaarten.contains(25) && !spadesAUsed) {
				roundScore = roundScore - 10;
				spadesAUsed = true;
			} else if (spelerKaarten.contains(38) && !diamondsAUsed) {
				roundScore = roundScore - 10;
				diamondsAUsed = true;
			} else if (spelerKaarten.contains(51) && !clubsAUsed) {
				roundScore = roundScore - 10;
				clubsAUsed = true;
			}
		}
		roundScore += addToRoundScore;
	}
	
	public static void addKaart(int verkregenKaart) {
		spelerKaarten.add(verkregenKaart);
	}
	
	public static ArrayList<Integer> getSpelerKaarten() {
		return spelerKaarten;
	}
	
}

class Bank {
	static final String bankName = "Bank";
	private static int roundScoreBank;
	private static ArrayList<Integer> bankKaarten = new ArrayList<Integer>();
	
	public static int getBankRoundScore() {
		return roundScoreBank;
	}
	
	public static void addKaartBank(int verkregenKaartBank) {
		bankKaarten.add(verkregenKaartBank);
	}
	
	public static ArrayList<Integer> getBankKaarten() {
		return bankKaarten;
	}
	
	public static void addRoundScoreBank(int addToRoundScoreBank) {
		roundScoreBank += addToRoundScoreBank;
	}
	
	public static int tweedeKaartBank() {
		return bankKaarten.get(1);
	}
	
	
}

class HetSpel {
	final static int MAX_SCORE = 21;
	
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
		Player.addKaart(eersteKaart);
		Integer tweedeKaart = Deck.shuffledDeck.get(0);
		Deck.shuffledDeck.remove(tweedeKaart);
		Player.addKaart(tweedeKaart);
				
		System.out.printf("Jouw kaarten zijn: %s%s en een %s%s",
						deKaarten[eersteKaart].getSuitName(),
						deKaarten[eersteKaart].getValueName(),
						deKaarten[tweedeKaart].getSuitName(),
						deKaarten[tweedeKaart].getValueName()
						);
		Player.addRoundScore(deKaarten[eersteKaart].getWaarde() + deKaarten[tweedeKaart].getWaarde());
		System.out.printf("%nJouw kaarten hebben een totale waarde van: %d", Player.getRoundScore());
		
		Integer eersteKaartBank = Deck.shuffledDeck.get(0);
		Deck.shuffledDeck.remove(eersteKaartBank);
		Bank.addKaartBank(eersteKaartBank);
		Integer tweedeKaartBank = Deck.shuffledDeck.get(0);
		Deck.shuffledDeck.remove(tweedeKaartBank);
		Bank.addKaartBank(tweedeKaartBank);
		
		Bank.addRoundScoreBank(deKaarten[eersteKaartBank].getWaarde());
		System.out.printf("%n%nDe bank toont een: %s%s",
				deKaarten[eersteKaartBank].getSuitName(),
				deKaarten[eersteKaartBank].getValueName()
				);
		
		
	}
	
	void speelRonden(){
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
	
	void beurtBank() {
		System.out.println("\n\nHet is nu de beurt van de bank");
		System.out.printf("\nDe bank zijn 2e kaart is: %s%s%n", 				
						deKaarten[Bank.tweedeKaartBank()].getSuitName(),
						deKaarten[Bank.tweedeKaartBank()].getValueName());
		System.out.printf("De bank haar hand bestaat uit: ");
		for (int num : Bank.getBankKaarten()) {
			System.out.printf("%s%s ",deKaarten[num].getSuitName().charAt(0),deKaarten[num].getValueName());
		}
		System.out.printf("%nMet een totale waarde van: %d",Bank.getBankRoundScore());
		
		while (Bank.getBankRoundScore() < 17) {
			Integer welkeKaartBank = Deck.shuffledDeck.get(0);
			Deck.shuffledDeck.remove(welkeKaartBank);
			Bank.addKaartBank(welkeKaartBank);
			System.out.printf("%nDe bank trekt een: %s%s",
					deKaarten[welkeKaartBank].getSuitName(),
					deKaarten[welkeKaartBank].getValueName());
			Bank.addRoundScoreBank(deKaarten[welkeKaartBank].getWaarde());
			System.out.printf("%n%nDe bank heeft nu %d punten",
								Bank.getBankRoundScore());
								
		}
	}
	
	void checkWinnaar() {
		if (Player.getRoundScore() == 21) {
			System.out.println("\n\nJe hebt blackjack, je hebt gewonnen!");
		} else if (Player.getRoundScore() > 21) {
			System.out.println("\n\nYou busted! je hebt verloren!");
		} else if (Player.getRoundScore() == Bank.getBankRoundScore()) {
			System.out.println("\n\nJe hebt even veel als de bank, je behoudt je inzet");
		} else if (Player.getRoundScore() > Bank.getBankRoundScore()) {
			System.out.println("\n\nJe hebt meer dan de bank, je hebt gewonnen");
		}	else {
			System.out.println("\n\nJe hebt minder dan de bank, je hebt verloren.");
		}
	}

}