package knapsack;

/**
 * Item for easier iteration through an ArrayList instead of a simple array
 * Methods ought to be self explanatory
 * @author Raya
 *
 */
public class Item {
	private int weight;
	private int value;
	private int position;
	private int amount;
	
	public Item(int value, int weight, int position) {
		this.value = value;
		this.weight = weight;
		this.position = position;
		this.amount = 0;
	}
	
	public Item(Item item) {
		this.weight = item.getWeight();
		this.value = item.getValue();
		this.position = item.getPosition();
		this.amount = item.getAmout();
	}
	
	public int getWeight() {
		return weight;
	}
	
	public int getValue() {
		return value;
	}
	
	public int getPosition() {
		return position;
	}
	
	public int getAmout() {
		return amount;
	}
	
	public void setAmount(int amount) {
		this.amount = amount;
	}
	
	@Override
	public String toString() {
		return "Value :" + value + " ; Weight: " + weight;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj.getClass() != this.getClass()) {
			return false;
		}
		if(((Item)obj).getValue() != this.value) {
			return false;
		}
		if(((Item)obj).getWeight() != this.weight) {
			return false;
		}
		if(((Item)obj).getPosition() != this.position) {
			return false;
		}
		
		return true;
	}
}