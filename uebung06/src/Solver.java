import java.util.*;

public class Solver implements SolverInterface {
    private Random random;
    private Solution bestSolution;
    private Solution currentSolution;

    private double[] t;
    private static final int ITERATIONS = 200;

    public Solver() {
        random = new Random();
        random.setSeed(new Date().getTime());
        t = new double[ITERATIONS];

        Temperature.setTemperatureByAlpha(ITERATIONS, 0.9, t);
    //    Temperature.setConstantTemperature(100, t);
    }

    @Override
    public Solution solve(Instance instance) {
        //currentSolution = initialSolution(instance);
    	currentSolution = new Solution(instance);
        bestSolution = new Solution(currentSolution);

        for (int i = 0; i < ITERATIONS; i++) {
            findNeighborByFlipping(currentSolution);
            annealing(currentSolution, i);
        }

        return bestSolution;
    }

    private void findNeighborByFlipping(Solution currentSolution) {
        while (true) {
            int pos = random.nextInt(currentSolution.getSize() - 1);
            flip(pos, currentSolution);

            if (!currentSolution.isFeasible())
                flip(pos, currentSolution);
            else
                return;
        }
    }

    private void annealing(Solution neighbor, int iterations) {
        if (neighbor.getValue() > bestSolution.getValue()) {
            bestSolution = new Solution(neighbor);
        }
        else {
            int delta = neighbor.getValue() - bestSolution.getValue();
            double probability = Math.pow(Math.E, -delta / t[iterations]);

            if (Double.compare(random.nextDouble(), probability) > 0) {
                bestSolution = new Solution(neighbor);
            }
        }
    }

    private void flip(int pos, Solution solution) {
        if (pos > solution.getSize() - 1) {
            return;
        }

        if (solution.get(pos) == 1)
            solution.set(pos, 0);
        else
            solution.set(pos, 1);
    }

    private Solution initialSolution(Instance instance) {
        Solution sol = new Solution(instance);
        ArrayList<Pair> listSortedByEfficiency = new ArrayList<>();

        for (int i = 0; i < instance.getSize(); i++) {
            listSortedByEfficiency.add(new Pair(i, instance.getValue(i) / instance.getWeight(i)));
        }

        Collections.sort(listSortedByEfficiency, new Comparator<Pair>() {
            @Override
            public int compare(Pair o, Pair t1) {
                return Double.compare(o.getValue(), t1.getValue());
            }
        });

        for (Pair pair : listSortedByEfficiency) {
            sol.set(pair.getItem(), 1);

            if (!sol.isFeasible())
                sol.set(pair.getItem(), 0);
        }

        return sol;
    }
}