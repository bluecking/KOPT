import knapsack.Instance;

import javax.xml.bind.SchemaOutputResolver;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;

public class Main {
	public void test(Instance instance) {
		Double[] newArray = new Double[instance.getSize()];

		for(int i = 0; i < instance.getSize(); i++) {
			newArray[i] = (double) instance.getValue(i) / instance.getWeight(i);
		}

		Arrays.sort( newArray, Collections.reverseOrder());
	}
}
