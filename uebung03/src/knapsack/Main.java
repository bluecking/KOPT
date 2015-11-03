package knapsack;

import java.io.IOException;

public class Main {
	public static void runSolver(SolverInterface solver, Instance instance) {
		System.out.println("=== " + solver.getClass().getName() + " ===");
		long start = System.currentTimeMillis();
		Solution solution = solver.solve(instance);
		long end = System.currentTimeMillis();
		System.out.println("opt solution = " + solution.toString());
		System.out.println("opt value = " + solution.getValue());
		System.out.printf("time = %.3fs\n", (end - start) / 1000.0);
		assert solution.isBinary() : "Solution is not binary!";
	}

	public static void main(String[] args) throws IOException {
		if (args.length == 1) {
			Instance instance = Reader.readInstance(args[0]);
			runSolver(new Solver(), instance);
		} else {
			throw new IllegalArgumentException("Need filename as argument.");
		}
	}
}
