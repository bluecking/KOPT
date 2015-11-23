package knapsack2;

public class Solver implements SolverInterface {
    private int[][] table;
    private Instance instance;

    @Override
    public Solution solve(Instance instance) {
        int W = instance.getWeightLimit();
        int n = instance.getSize();
        this.instance = instance;

        /* Initialize table with -1 */
        table = new int[n][W + 1];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j <= W; j++) {
                table[i][j] = -1;
            }
        }

        int bestValue = calculate(n - 1, W);

        return backtrack(bestValue);
    }

    protected int calculate(int item, int currentWeight) {
        if (item < 0) {
            return 0;
        }

        int itemWeight = instance.getWeight(item);
        int itemValue = instance.getValue(item);

        if (table[item][currentWeight] != -1)
            return table[item][currentWeight];

        int withOutCurrentItem = calculate(item - 1, currentWeight);
        int withCurrentItem = 0;

        if (currentWeight - itemWeight >= 0) {
            withCurrentItem = itemValue + calculate(item - 1, currentWeight - itemWeight);
        }

        table[item][currentWeight] = Math.max(withOutCurrentItem, withCurrentItem);

        return table[item][currentWeight];
    }

    protected Solution backtrack(int bestValue) {
        Solution s = new Solution(instance);
        int n = instance.getSize();
        int W = instance.getWeightLimit();

        for (int i = n - 1; 0 < i; i--) {
            if (W - instance.getWeight(i) >= 0 && table[i - 1][W - instance.getWeight(i)] == bestValue - instance.getValue(i)) {
                s.set(i, 1);
                bestValue -= instance.getValue(i);
                W -= instance.getWeight(i);
            }
        }

        if (bestValue > 0)
            s.set(0, 1);

        return s;
    }
}