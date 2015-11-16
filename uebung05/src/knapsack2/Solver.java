package knapsack2;

/**
 * O(nW)
 */
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

        int bestValue = calculate(0, W);

        return backtrack(bestValue);
    }

    public int calculate(int item, int currentVolume) {
        if (item >= instance.getSize()) {
            return 0;
        }

        int itemWeight = instance.getWeight(item);
        int itemValue = instance.getValue(item);

        if (table[item][currentVolume] != -1)
            return table[item][currentVolume];

        int withOutCurrentItem = calculate(item + 1, currentVolume);
        int withCurrentItem = 0;

        if (currentVolume - itemWeight >= 0) {
            withCurrentItem = itemValue + calculate(item + 1, currentVolume - itemWeight);
        }

        table[item][currentVolume] = Math.max(withOutCurrentItem, withCurrentItem);

        return table[item][currentVolume];
    }

    public Solution backtrack(int bestValue) {
        Solution s = new Solution(instance);
        int n = instance.getSize();
        int W = instance.getWeightLimit();
        int currentVolume = -1;

        for (int j = 0; (j <= W) && (currentVolume == -1); j++) {
            if (table[0][j] == bestValue)
                currentVolume = j;
        }

        for (int i = 0; i < n - 1; i++) {
            int weight = instance.getWeight(i);

            if (currentVolume - instance.getWeight(i) >= 0 && table[i + 1][currentVolume - instance.getWeight(i)] == bestValue - instance.getValue(i)) {
                s.set(i, 1);
                bestValue -= instance.getValue(i);
                currentVolume -= instance.getWeight(i);
            }
        }

        if (bestValue > 0)
            s.set(n - 1, 1);

        return s;
    }
}