/**
 * An interface for knapsack problem solvers
 *
 * @author Stephan Beyer
 */
public interface SolverInterface {
	/**
	 * Compute a solution for the given instance
	 *
	 * @param instance The given knapsack instance
	 * @return The solution
	 */
	Solution solve(Instance instance);
}
