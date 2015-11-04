package knapsack;

import java.util.*;

public class Item implements Comparable{
	private int c;
	private int w;
	private double efficiency;
	private int n; // position of Item in Instance
	private int amount = 0;
	
	public Item() {
		c = 0;
		w = 0;
		efficiency = 0;
		n = 0;
	}
	
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
	
	public Item(int c, int w, int n, int amount) {
		this.c = c;
		this.w = w;
		this.efficiency = (double) c/w;
		this.n = n;
		this.amount = amount;
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
	
	public static Comparator<Item> ratio() {
		return new Comparator<Item>() {
			public int compare(Item i1, Item i2) {
				return Double.compare(i2.efficiency, i1.efficiency);
			}
		};
	}
	
	@Override
	public String toString() {
		return "Itemvalue: " + c + ", Itemweight: " + w + " |" + efficiency;
	}
}
