import java.util.Random;
import java.util.Scanner;
import java.util.ArrayList;

public class BlackJackMain {
	public static void main(String[] args) {
		HetSpel spel = new HetSpel();
		
		System.out.println("#######################");
		System.out.println("welkom bij BlackJack");
		System.out.println("#######################");
		System.out.println("");
		
		spel.verkrijgKaarten();	
		
		spel.getSpelers();
		
		spel.giveStartBalance(); 
		
		Deck.getDeck();
		Deck.shuffleDeck();

		while (spel.getGameStatus()) {
			
			spel.speelRonden();
			
			spel.beurtBank();
			
			spel.checkWinnaar();
			
			spel.setGameStatus();
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
	private boolean isPlaying = true;
	
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
	
	public boolean getPlaying() {
		return isPlaying;
	}
	
	public void setPlaying (boolean playingStatus ) {
		isPlaying = playingStatus;
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
	
	private boolean gameStatus = true;
	
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
	
	public boolean getGameStatus() {
		return gameStatus;
	}
	
	public void setGameStatus() {
		for(Player spelers : lijstSpelers) {
			if (spelers.getPlaying()) {
				gameStatus = true;
				break;
			} else {
				gameStatus = false;
			}
        }
		
		if (!gameStatus) {
			System.out.println("\n#############################\n");
			System.out.println("Iedereen is gestopt met spelen");
			System.out.println("\n#############################\n");
		}
		
	}
	
	void getSpelers() {
		while (aantalSpelers == 0 || aantalSpelers > 3) {
			System.out.println("Met Hoeveel Spelers wil je spelen? (max 3)");
			char inputSpelers = sc.next().charAt(0);
			int inPutSpelersInt = Character.getNumericValue(inputSpelers);
			System.out.println(inPutSpelersInt);
			if( (inPutSpelersInt == 3) || (inPutSpelersInt == 2) || (inPutSpelersInt == 1)){
				aantalSpelers = inPutSpelersInt;
				System.out.println("Vul de namen in van de spelers");
				
				for (int i = 0; i < aantalSpelers; i++) {
					System.out.printf("Naam speler %d:   ", i+1);
					String naam = sc.next();
					lijstSpelers.add(i, new Player(naam));
				}
			} else {
				System.out.println("Dat is onjuiste input probeer (1), (2) of (3)");
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
			boolean rondeInzetJuist = false;
			if (spelers.getPlaying()) {
				System.out.printf("%n%s hoeveel wil je inztten?",
									spelers.getName());
				System.out.printf("%nuw invoer(alleen cijfers):   ");
				int rondeInzet = sc.nextInt();
				
				while (!rondeInzetJuist) {
					if (rondeInzet > spelers.getBalance()) {
						System.out.printf("%nJe kan maximaal %d inzetten",
								spelers.getBalance());
						System.out.printf("%nprobeer het nog een keer:   ");
						rondeInzet = sc.nextInt();
					} else {
						spelers.setRondeInzet(rondeInzet);
						rondeInzetJuist = true;
					}
				}
			}
		}
	}
	
	void giveFirstCards() {
		aanDeBeurt = 0;
		
		// loop door spelers geef iedereen een kaart
		for(Player spelers : lijstSpelers) {
			if (spelers.getPlaying()) {
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
			aanDeBeurt += 1;
			
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
		
		plaatsInzet();	
		giveFirstCards();
		boolean speelRonde = true;
		
		if (aanDeBeurt >= aantalSpelers) {
			aanDeBeurt = 0;
		}
		
		for(Player spelers : lijstSpelers) {
			aanDeBeurt++;
			speelRonde = true;
			
			if (spelers.getPlaying()) {
				System.out.printf("%n%n%s is aan de beurt", spelers.getName());
				while (MAX_SCORE > spelers.getRoundScore() && speelRonde) {
					System.out.println("\nDruk (K) voor nieuwe kaart, (P) om te passen of (Q) om te passen en na ronde te stoppen");
					System.out.print("uw invoer:  ");
					String invoer = sc.next().toLowerCase();
					boolean invoerSpeler = false;
					
					while (!invoerSpeler) {
						if(invoer.equals("q")) {
							System.out.printf("Je stopt met spelen na deze ronde%n", spelers.getBalance());
							spelers.setPlaying(false);
							speelRonde = false;
							invoerSpeler = true;
							break;
						} else if(invoer.equals("k")) {
							Integer welkeKaart = Deck.shuffledDeck.get(0);
							Deck.shuffledDeck.remove(welkeKaart);
							spelers.addKaart(welkeKaart);
							System.out.printf("%nDe kaart die je krijgt is een:  %s%s",
										deKaarten[welkeKaart].getSuitName(),
										deKaarten[welkeKaart].getValueName());
							spelers.addRoundScore(deKaarten[welkeKaart].getWaarde());
							System.out.printf("%nJouw kaarten hebben een totale waarde van: %d", spelers.getRoundScore());
							System.out.printf("%nJouw hand bestaat uit: %n");
							for (int num : spelers.getSpelerKaarten()) {
								System.out.printf("%s%s ",deKaarten[num].getSuitName().charAt(0),deKaarten[num].getValueName());
							}
							invoerSpeler = true;
						} else if(invoer.equals("p")) {
							System.out.println("Je hebt gepast");
							speelRonde = false;
							invoerSpeler = true;
							break;
						} else {
						System.out.println("Onjuist teken, probeer nog een keer");
						System.out.print("uw invoer:  ");
						invoer = sc.next().toLowerCase();
						}
					}
					if (spelers.getRoundScore() > MAX_SCORE) {
						System.out.println("Je hebt meer dan 21: You busted");
					}
				}
			} else {
				speelRonde = false;
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
			System.out.printf("%n%nDe bank trekt een: %s%s",
					deKaarten[welkeKaartBank].getSuitName(),
					deKaarten[welkeKaartBank].getValueName());
			Bank.addRoundScoreBank(deKaarten[welkeKaartBank].getWaarde());
			System.out.printf("%nDe bank heeft nu %d punten%n",
								Bank.getBankRoundScore());
								
		}
	}
	
	void checkWinnaar() {
		
		for(Player spelers : lijstSpelers) {
			System.out.println("\n\n"+spelers.getName());
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
			
			if (spelers.getPlaying()) {
				System.out.printf("Jouw balans is nu %d%n%n", spelers.getBalance());
			}
	
			if (!spelers.getPlaying()) {
				System.out.printf("Je bent gestopt met spelen en neemt naar huis %d%n", 
									spelers.getBalance());
			}
		}
		
		Bank.resetRoundScoreBank();

		for(Player spelers : lijstSpelers) {
			spelers.resetRoundScore();
        }
	}
}