
public class BlackJackMain {
	public static void main(String[] args) {
		HetSpel spel = new HetSpel();
		spel.verkrijgKaarten();
	}
}

class Kaart{
	static String suit;
	static String value;
	static int waarde;
}

class HetSpel {
	Kaart[] deKaarten = new Kaart[52];
	String[] suits = {"Hearts", "Spades", "Diamond","Clubs"};
	String[] values = {"2", "3", "4","5","6", "7", "8","9","10", "J", "Q","K","A"};
	int [] waarden = {2,3,4,5,6,7,8,9,10,10,10,10,11};
	
	void verkrijgKaarten() {
		int totaalTeller = 0;
		for( int x = 0 ; x < suits.length ; x++ ) {
			for( int y = 0 ; y < values.length ; y++ ) {
				deKaarten[totaalTeller] = new Kaart();
				Kaart.suit = suits[x];
				Kaart.value = values[y];
				Kaart.waarde = waarden[y];
				
				System.out.println("kaart is "+Kaart.suit+Kaart.value+"\nmet een waarde van " + Kaart.waarde + "\n");
				totaalTeller++;
			}
		}
	}

}