package knapsack;

public class Item implements Comparable{
	private int c;
	private int w;
	private double efficiency;
	private int n;
	private int amount = 0;
	
	public Item(int c, int w) {
		this.c = c;
		this.w = w;
		this.efficiency = (double) c/w;
	}
	
	public Item(int c, int w, int n) {
		this.c = c;
		this.w = w;
		this.efficiency = (double) c/w;
		this.n = n;
	}
	
	public int getN() {
		return n;
	}
	
	public int getValue() {
		return c;
	}
	
	public int getWeight() {
		return w;
	}
	
	public double getEfficiency() {
		return efficiency;
	}
	
	public double getAmount() {
		return amount;
	}
	
	public void setAmount(int amount) {
		this.amount = amount;
	}

	@Override
	public int compareTo(Object o) {
		if (((Item) o).getEfficiency() == this.getEfficiency()) {
			return 0;
		}
		if (((Item)o).getEfficiency() < this.getEfficiency()) {
			return 1;
		} else {
			return -1;
		}
	}
	
	@Override
	public String toString() {
		return "Itemvalue: " + c + ", Itemweight: " + w + " |" + n;
	}
}
