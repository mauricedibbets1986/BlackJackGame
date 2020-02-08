import java.util.Random;
import java.util.Scanner;

import java.util.ArrayList;

public class BlackJackMain {
	public static void main(String[] args) {
		HetSpel spel = new HetSpel();
		
		spel.verkrijgKaarten();	
		
		spel.getSpelers();
		
		spel.giveStartBalance(); 
		
		Deck.getDeck();
		Deck.shuffleDeck();

		while (true) {
			spel.plaatsInzet();		
			
			spel.giveFirstCards();
			spel.speelRonden();
			
			spel.beurtBank();
			
			spel.checkWinnaar();
			
		}
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
	
	
	private String playerName;
	private int roundScore;
	private int balance;
	private static int rondeInzet;
	private ArrayList<Integer> spelerKaarten = new ArrayList<Integer>();
	
	public Player(String name) {
		this.playerName = name;
	}
	
	public String getName() {
		return playerName;
	}
	
	
	public int getRoundScore() {
		return roundScore;
	}
	
	public int getBalance() {
		return balance;
	}
	
	public static int getRondeInzet() {
		return rondeInzet;
	}
	
	public void addRoundScore(int addToRoundScore) {
		
		if (roundScore + addToRoundScore > HetSpel.MAX_SCORE) {
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
	
	public void resetRoundScore() {
		roundScore = 0;
	}
	
	public void addKaart(int verkregenKaart) {
		spelerKaarten.add(verkregenKaart);
	}
	
	public ArrayList<Integer> getSpelerKaarten() {
		return spelerKaarten;
	}
	
	public void addBalance(int addedProfit) {
		balance += addedProfit;
	}
	
	public void substractBalance(int substractedLoss) {
		balance -= substractedLoss;
	}
	
	public static void setRondeInzet(int ingevoerdeRondeInzet) {
		rondeInzet = ingevoerdeRondeInzet;
	}
	
}

class Bank {
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
	
	public static void resetRoundScoreBank() {
		roundScoreBank = 0;
	}
	
	public static int tweedeKaartBank() {
		return bankKaarten.get(1);
	}
	
}

class HetSpel {
	final static int MAX_SCORE = 21;
	public static ArrayList <Player> lijstSpelers = new ArrayList <Player> ();
	public static int aantalSpelers = 0;
	static int aanDeBeurt;
	
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
	
	void getSpelers() {
		while (aantalSpelers == 0 || aantalSpelers > 3) {
			System.out.println("Met Hoeveel Spelers wil je spelen? (max 3)");
			int inputSpelers = sc.nextInt();
			if (inputSpelers > 3) {
				System.out.println("Dat is meer dan het maximum aantal spelers");
			}
			else {
				aantalSpelers = inputSpelers;
				System.out.println("Vul de namen in van de spelers");
				
				for (int i = 0; i <= aantalSpelers; i++) {
					System.out.printf("Naam speler %d:   ", i);
					String naam = sc.nextLine();
					lijstSpelers.add(i, new Player(naam));
				}
			}
		}
		System.out.printf("We spelen met %d speler(s):%n", aantalSpelers);
		for(Player spelers : lijstSpelers) {
            System.out.println(spelers.getName());
        }
	}
	
	void giveStartBalance() {
		
		for(Player spelers : lijstSpelers) {
			System.out.printf("%n%s met hoeveel geld wil je aan tafel zitten?%n", spelers.getName());
			System.out.print("uw invoer(alleen cijfers):   ");
			int invoerStartBalans = sc.nextInt();
			lijstSpelers.get(aanDeBeurt).addBalance(invoerStartBalans);
			aanDeBeurt += 1;
		}
		for(Player spelers : lijstSpelers) {
			System.out.printf("%s begint met %d Euro%n", spelers.getName(), spelers.getBalance());
		}
		
	}
	
	void plaatsInzet() {
		
		for(Player spelers : lijstSpelers) {
			System.out.printf("%n%s hoeveel wil je inztten?",
								spelers.getName());
			System.out.printf("%nuw invoer(alleen cijfers):   ");
			int rondeInzet = sc.nextInt();
			spelers.setRondeInzet(rondeInzet);
		}
	
		
	}
	
	void giveFirstCards() {
		aanDeBeurt = 0;
		// loop door spelers geef iedereen een kaart
		for(Player spelers : lijstSpelers) {
			aanDeBeurt += 1;
			Integer eersteKaart = Deck.shuffledDeck.get(0);
			Deck.shuffledDeck.remove(eersteKaart);
			spelers.addKaart(eersteKaart);
			Integer tweedeKaart = Deck.shuffledDeck.get(0);
			Deck.shuffledDeck.remove(tweedeKaart);
			spelers.addKaart(tweedeKaart);
					
			System.out.printf("%n%n%s%nJouw kaarten zijn: %s%s en een %s%s",
							spelers.getName(),
							deKaarten[eersteKaart].getSuitName(),
							deKaarten[eersteKaart].getValueName(),
							deKaarten[tweedeKaart].getSuitName(),
							deKaarten[tweedeKaart].getValueName()
							);
			spelers.addRoundScore(deKaarten[eersteKaart].getWaarde() + deKaarten[tweedeKaart].getWaarde());
			System.out.printf("%nJouw kaarten hebben een totale waarde van: %d", spelers.getRoundScore());
		}
		
		
		// geef bank kaarten
		Integer eersteKaartBank = Deck.shuffledDeck.get(0);
		Deck.shuffledDeck.remove(eersteKaartBank);
		Bank.addKaartBank(eersteKaartBank);
		Integer tweedeKaartBank = Deck.shuffledDeck.get(0);
		Deck.shuffledDeck.remove(tweedeKaartBank);
		Bank.addKaartBank(tweedeKaartBank);
		
		Bank.addRoundScoreBank(deKaarten[eersteKaartBank].getWaarde());
		Bank.addRoundScoreBank(deKaarten[tweedeKaartBank].getWaarde());
		System.out.printf("%n%nDe bank toont een: %s%s",
				deKaarten[eersteKaartBank].getSuitName(),
				deKaarten[eersteKaartBank].getValueName()
				);
		
		
	}
	
	void speelRonden(){
		
		// loop door alle spelers geef iedereen een beurt.
		// wanneer speler quit uit spel halen, rest gaat verder
		// max inzet niet hoger dan balans
		boolean speelRonde = true;
		if (aanDeBeurt >= aantalSpelers) {
			aanDeBeurt = 0;
		}
			
		
		for(Player spelers : lijstSpelers) {
			System.out.printf("%n%n%s is aan de beurt", spelers.getName());
			aanDeBeurt++;
			speelRonde = true;
			
			while (MAX_SCORE > spelers.getRoundScore() && speelRonde) {
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
					spelers.addKaart(welkeKaart);
					System.out.printf("De kaart die je krijgt is een:  %s%s%n",
								deKaarten[welkeKaart].getSuitName(),
								deKaarten[welkeKaart].getValueName());
					spelers.addRoundScore(deKaarten[welkeKaart].getWaarde());
					
				}
				if(invoer.equals("p")) {
					System.out.println("Je hebt gepast");
					speelRonde = false;
				}
				if (spelers.getRoundScore() > MAX_SCORE) {
					System.out.println("Je hebt meer dan 21: You busted");
				}
				
				System.out.printf("Jouw hand bestaat uit: ");
				for (int num : spelers.getSpelerKaarten()) {
					System.out.printf("%s%s ",deKaarten[num].getSuitName().charAt(0),deKaarten[num].getValueName());
				}
				System.out.printf("%nJouw kaarten hebben een totale waarde van: %d", spelers.getRoundScore());	
			}
		}
	}
	
	void beurtBank() {
		System.out.println("\n\nHet is nu de beurt van de bank");
		System.out.printf("\nDe bank haar 2e kaart is: %s%s%n", 				
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
		
		// loop door spelers bekijk bij iedereen gewonnen of verloren add/substract balance
		// blackjack geen 21 nog veranderen als string speler 2 kaarten heeft met een 10 waarde kaart en een A.
		
		for(Player spelers : lijstSpelers) {
			System.out.println(spelers.getName());
			if (spelers.getRoundScore() == 21 && spelers.getSpelerKaarten().size() == 2) {
				System.out.println("Je hebt blackjack, je hebt gewonnen!");
				spelers.addBalance(spelers.getRondeInzet()*2);
			} else if (spelers.getRoundScore() > 21) {
				System.out.println("You busted! je hebt verloren!");
				spelers.substractBalance(spelers.getRondeInzet());
			} else if(Bank.getBankRoundScore() > 21) {
				System.out.println("De bank is busted! Je hebt gewonnen");
				spelers.addBalance(Player.getRondeInzet());
			} else if (spelers.getRoundScore() == Bank.getBankRoundScore()) {
				System.out.println("Je hebt even veel als de bank, je behoudt je inzet");
			} else if (spelers.getRoundScore() > Bank.getBankRoundScore()) {
				System.out.println("Je hebt meer dan de bank, je hebt gewonnen");
				spelers.addBalance(spelers.getRondeInzet());
			}	else {
				System.out.println("Je hebt minder dan de bank, je hebt verloren.");
				spelers.substractBalance(spelers.getRondeInzet());
			}
			System.out.printf("Jouw balans is nu %d%n%n", spelers.getBalance());
		}
		
		Bank.resetRoundScoreBank();
		// reset balance alle spelers
		for(Player spelers : lijstSpelers) {
			spelers.resetRoundScore();
        }
	}

}