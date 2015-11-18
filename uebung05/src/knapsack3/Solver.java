package knapsack3;

import java.util.ArrayList;

public class Solver implements SolverInterface {
    Instance instance;

    @Override
    public Solution solve(Instance instance) {
        int W = instance.getWeightLimit();
        int n = instance.getSize();
        this.instance = instance;

        int[] c0 = new int[W + 1];  /* Array of maximum values for each weight */
        int[] c1 = new int[W + 1];  /* Temporary array of c0, for calculating resons */
        ArrayList<ArrayList<Integer>> i0 = new ArrayList<>(); /* List of packed items for each weight */
        ArrayList<ArrayList<Integer>> i1 = new ArrayList<>(); /* Temporary list of i1, for calculating reasons */

        /* Initialize array and lists */
        for (int i = 0; i < c0.length; i++) {
            c0[i] = 0;
            c1[i] = 0;
            i0.add(new ArrayList<Integer>());
            i1.add(new ArrayList<Integer>());
        }

        for (int item = 0; item < n; item++) {
            int itemWeight = instance.getWeight(item);
            int itemValue = instance.getValue(item);

            for (int currentWeight = 0; currentWeight <= W; currentWeight++) {
                /* Does the current item fit in the knapsack? */
                if (currentWeight >= itemWeight && c0[currentWeight] < c0[currentWeight - itemWeight] +
                        itemValue) {
                    c1[currentWeight] = c0[currentWeight - itemWeight] + itemValue;
                    i0.get(currentWeight - itemWeight).add(item);
                    i1.set(currentWeight, new ArrayList<>(i0.get(currentWeight - itemWeight)));
                } else {
                    c1[currentWeight] = c0[currentWeight];
                    i1.set(currentWeight, new ArrayList<>(i0.get(currentWeight)));
                }
            }

            /* copy results to main arrays and lists */
            c0 = c1.clone();
            i0 = new ArrayList<>(i1);
        }

        return createSolution(i0.get(i0.size()-1));
    }

    /* transforms the list to a proper Solution representation */
    private Solution createSolution(ArrayList<Integer> solutionList) {
        Solution s = new Solution(instance);

        for (int i : solutionList) {
            s.set(i, 1);
        }

        return s;
    }
}
