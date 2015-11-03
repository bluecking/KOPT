import knapsack.Instance;

public class Solver implements SolverInterface {
    @Override
    public Solution solve(Instance instance) {
        Solution solution = new Solution(instance);
        Solution optSolution = new Solution(instance);

        /**
         * Iterates from {0,0,...,0} -> {1,1,...,1}
         * Loops if {1,1,...,1} is not reached yet.
         */
        while (!isComplete(solution.getArray())) {
            incrementInstance(solution);

            if (solution.isFeasible()) {
                System.out.println("+++ " + solution);

                /**
                 * If the value of the current solution is better than the saved solution,
                 * then replace the saved solution with the current solution.
                 */
                if (optSolution.getValue() <= solution.getValue()) {
                    copySolution(solution, optSolution);
                }
            } else {
                System.out.println("--- " + solution);
            }
        }

        return optSolution;
    }

    private void incrementInstance(Solution solution) {
        for (int i = 0; i < solution.getArray().length; i++) {
            if (solution.getArray()[i] == 0) {
                solution.set(i, 1);
                break;
            } else {
                solution.set(i, 0);
            }
        }
    }

    private void copySolution(Solution solution, Solution opt_solution) {
        for (int i = 0; i < solution.getArray().length; i++) {
            opt_solution.set(i, solution.get(i));
        }
    }

    private boolean isComplete(int[] array) {
        for (int value : array) {
            if (value == 0) return false;
        }

        return true;
    }
}