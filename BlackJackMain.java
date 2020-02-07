import java.util.Random;
import java.util.Scanner;

public class BlackJackMain {
	public static void main(String[] args) {
		HetSpel spel = new HetSpel();
		spel.verkrijgKaarten();
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
	
}

class HetSpel {
	Scanner sc = new Scanner(System.in);
	Random r = new Random();
	Kaart[] deKaarten = new Kaart[52];
	String[] suits = {"Hearts", "Spades", "Diamond","Clubs"};
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
	
	void speelRonden(){
		for(int i = 0; 5<6 ; i++) {
			System.out.println("Druk k voor nieuwe kaart of q om te stoppen");
			String invoer = sc.next();
			if(invoer.equals("q")) {
				break;
			}
			if(invoer.equals("k")) {
				int uitzoekInt = r.nextInt(52);
				System.out.println("De kaart die ik krijg is:  "+ deKaarten[uitzoekInt].getSuitName()+ deKaarten[uitzoekInt].getValueName());
			}
		}
	}

}