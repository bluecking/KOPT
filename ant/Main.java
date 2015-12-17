import java.io.*;
import java.util.*;


public class Main {
	public static void main(String[] args) {
		if (args.length == 1) {
			File file = new File(args[0]);
			Scanner in = null;
			try {
				in = new Scanner(file);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			int towns = in.nextInt();
			
			ArrayList<Town> list = new ArrayList<Town>();
			
			//read input
			for(int n = 0; n < towns - 1; n++) {
				Town town = new Town(towns);
				for(int i = 0; i < towns - 1 - n; i++) {
					town.setId(in.nextInt());
					int neighbour = in.nextInt();
					double distance = in.nextDouble();
					town.setNeighbour(neighbour, distance);
				}
				list.add(town);
			}
			//fill out missing fields
			for(int n = 0; n < towns - 1; n++) {
				for(int i = 0; i < towns - 2 - n; i++) {
					list.get(list.size() - 1 - n).setNeighbour(i + 1, 
							list.get(i).getNeighbour()[list.size() - 1 - n][0]);
				}
			}
			//fully initializes last town
			Town lastTown = new Town(towns);
			lastTown.setId(towns);
			for(int i = 1; i < towns; i++) {
				lastTown.setNeighbour(i, list.get(i - 1).getNeighbour()[towns - 1][0]);
			}
			
			list.add(lastTown);
			
			System.out.println(args[0]);

			System.out.println();
			Solver solver = new Solver();
			
			ArrayList<Town> solution = new ArrayList<Town>(solver.solve(list, 1, 2));
			for(Town t : solution) {
				System.out.println(t.getId());
			}

		} else {
			throw new IllegalArgumentException("Need filename as argument.");
		}
		
	}
}