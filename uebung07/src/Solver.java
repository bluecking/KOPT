import java.util.ArrayList;

public class Solver implements SolverInterface {

    Instance instance;

    public Solution solve(Instance instance) {
        this.instance = instance;

//        return method1();
//        return method2();
//        return method3();
        return null;
    }

    // Flip, Greedy, statisch, nach iterationen, position
    private Solution method1(int iterations, int maxListSize) {
        Solution currentSolution = new Solution(instance);
        Solution bestSolution = new Solution(currentSolution);
        ArrayList<Integer> list = new ArrayList<>();

        for (int i = 0; i < iterations; i++) {
            Solution bestNeighbour = new Solution(instance);
            int pos = -1;

            // Find the best neighbour
            for (int j = 0; j < currentSolution.getSize(); j++) {
                // Tabu criteria
                if (list.contains(j)) {
                    continue;
                }

                flip(j, currentSolution);

                if (currentSolution.isFeasible() && currentSolution.getValue() > bestNeighbour.getValue()) {
                    pos = j;
                    bestNeighbour = new Solution(currentSolution);
                }

                flip(j, currentSolution);
            }

            if(list.size() == maxListSize) {
                list.remove(list.size() - 1);
            }

            list.add(pos);
            currentSolution = new Solution(bestNeighbour);

            if (currentSolution.getValue() > bestSolution.getValue()) {
                bestSolution = new Solution(currentSolution);
            }
        }

        return bestSolution;
    }

    private Solution method2() {

    }

    private Solution method3(int iterations) {
        Solution bestSolution = new Solution(instance);
        int listSize;

        for (int i = 0; i < iterations; i++) {

        }
        return bestSolution;
    }

    /**
     * Flips a single byte in a @Solution object
     *
     * @param pos      Determines the position of the byte that will be flipped
     * @param solution object that will be manipulated
     */
    private void flip(int pos, Solution solution) {
        if (pos > solution.getSize() - 1) {
            return;
        }

        if (solution.get(pos) == 1)
            solution.set(pos, 0);
        else
            solution.set(pos, 1);
    }

    /**
     * Moves a bit to any position and consequently fixes the amount array in solution.
     *
     * @param from     Bit that will be moved
     * @param to       Target position for the bit stored in 'from'
     * @param solution
     * @Solution object whose 'sol' attribute will be changed
     */
    private void shift(int from, int to, Solution solution) {
        if (from > solution.getSize() - 1 || to > solution.getSize() - 1) {
            return;
        }

        int tmp;
        if (from < to) {
            for (int i = from; i < to; i++) {
                api(i, solution);
            }
        }
        else {
            for (int i = from; to < i; i--) {
                api(i, solution);
            }
        }
    }

    /**
     * Swaps neighboring bits
     *
     * @param pos Position of the bit that will be shifted with the bit at
     *            position pos+1
     */
    private void api(int pos, Solution solution) {
        if (pos > solution.getSize() - 1) {
            return;
        }

        int tmp;
        if (pos == solution.getSize() - 1) {
            tmp = solution.get(pos);

            solution.set(pos, solution.get(0));
            solution.set(0, tmp);
        }
        else {
            tmp = solution.get(pos);
            solution.set(pos, solution.get(pos + 1));
            solution.set(pos + 1, tmp);
        }
    }
}