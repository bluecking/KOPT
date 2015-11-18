import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import knapsack3.*;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class SolverTest {
    private Instance instance10;
    private Instance instance15;
    private Instance instance20a;
    private Instance instance20b;
    private Instance instance30;
    private Instance instance40;
    private Instance instance50;
    private Instance instance60;
    private Instance instance100a;
    private Instance instance100b;
    private Instance instance500;
    private Instance instance1000;
    private Instance instance5000a;
    private Instance instance5000b;
    
    @Before
    public void setUp() throws Exception {
        instance10 = Reader.readInstance("rucksack0010.txt");
        instance15 = Reader.readInstance("rucksack0015.txt");
        instance20a = Reader.readInstance("rucksack0020a.txt");
        instance20b = Reader.readInstance("rucksack0020b.txt");
        instance30 = Reader.readInstance("rucksack0030.txt");
        instance40 = Reader.readInstance("rucksack0040.txt");
        instance50 = Reader.readInstance("rucksack0050.txt");
        instance60 = Reader.readInstance("rucksack0060.txt");
        instance100a = Reader.readInstance("rucksack0100a.txt");
        instance100b = Reader.readInstance("rucksack0100b.txt");
        instance500 = Reader.readInstance("rucksack0500.txt");
        instance1000 = Reader.readInstance("rucksack1000.txt");
        instance5000a = Reader.readInstance("rucksack5000a.txt");
        instance5000b = Reader.readInstance("rucksack5000b.txt");
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test(timeout=10000)
    public void testSolve() throws Exception {
        Solution solution = new Solver().solve(instance10);
        assertThat(solution.getValue(),is(254));
        solution = new Solver().solve(instance15);
        assertThat(solution.getValue(),is(337));
        solution = new Solver().solve(instance20a);
        assertThat(solution.getValue(),is(133));
        solution = new Solver().solve(instance20b);
        assertThat(solution.getValue(),is(428));
        solution = new Solver().solve(instance30);
        assertThat(solution.getValue(),is(307));
        solution = new Solver().solve(instance40);
        assertThat(solution.getValue(),is(375));
        solution = new Solver().solve(instance50);
        assertThat(solution.getValue(),is(487));
        solution = new Solver().solve(instance60);
        assertThat(solution.getValue(),is(606));
        solution = new Solver().solve(instance100a);
        assertThat(solution.getValue(),is(705));
        solution = new Solver().solve(instance100b);
        assertThat(solution.getValue(),is(376));
        solution = new Solver().solve(instance500);
        assertThat(solution.getValue(),is(60000));
        solution = new Solver().solve(instance1000);
        assertThat(solution.getValue(),is(1199));
//        solution = new Solver().solve(instance5000a);
//        assertThat(solution.getValue(),is(254));
//        solution = new Solver().solve(instance5000b);
//        assertThat(solution.getValue(),is(254));
    }
}