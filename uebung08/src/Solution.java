/**
 * Solution of a knapsack problem
 *
 * @author Stephan Beyer
 */
public class Solution {
	private Instance instance;
	private int[] sol;
	private int solValue;
	private int solWeight;

	public Solution(Instance instance) {
		this.instance = instance;
		sol = new int[instance.getSize()];
	}

	public Solution(Solution solution) {
		instance = solution.instance;
		sol = new int[instance.getSize()];
		for (int i = 0; i < solution.getSize(); ++i) {
			set(i, solution.get(i));
		}
	}

	/**
	 * Returns the size of the instance / of the solution.
	 */
	public int getSize() {
		return instance.getSize();
	}

	/**
	 * Assign a quantity to an item
	 *
	 * @param item The index of the item
	 * @param quantity The quantity to be assigned
	 */
	public void set(int item, int quantity) {
		solValue += (quantity - sol[item]) * instance.getValue(item);
		solWeight += (quantity - sol[item]) * instance.getWeight(item);
		sol[item] = quantity;
	}

	/**
	 * Get the quantity of an item
	 *
	 * @param item The index of the item
	 */
	public int get(int item) {
		return sol[item];
	}

	/**
	 * Get the array of all quantities
	 */
	public int[] getArray() {
		return sol;
	}

	/**
	 * Get the solution value
	 */
	public int getValue() {
		return solValue;
	}

	/**
	 * Get the solution weight
	 */
	public int getWeight() {
		return solWeight;
	}

	/**
	 * Check if the solution is feasible
	 */
	public boolean isFeasible() {
		return getWeight() <= instance.getWeightLimit();
	}

	/**
	 * Check if the solution is a binary solution
	 */
	public boolean isBinary() {
		for (int quantity : sol) {
			if (quantity != 0 && quantity != 1) {
				return false;
			}
		}
		return true;
	}

	@Override
	public String toString() {
		String str = "";
		for (int quantity : sol) {
			str += quantity + " ";
		}
		return str.trim();
	}
}
