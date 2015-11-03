import knapsack.Instance;

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

    /**
     * Assign a quantity to an item
     *
     * @param item     The index of the item
     * @param quantity The quantity to be assigned
     */
    public void set(int item, int quantity) {
        if (sol[item] < quantity) {
            for (int i = 0; i < (quantity - sol[item]); i++) {
                sol[item]++;
                solValue += instance.getValue(item);
                solWeight += instance.getWeight(item);
            }
        } else {
            for (int i = 0; i < (sol[item] - quantity); i++) {
                sol[item]--;
                solValue -= instance.getValue(item);
                solWeight -= instance.getWeight(item);
            }
        }

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
        return (solWeight <= instance.getWeightLimit());
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
