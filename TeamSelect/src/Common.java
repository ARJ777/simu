import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Common {

	public static void displayArray(String label, int[][] array) {
		System.out.printf("%s=[\n", label);
		for (int[] arow : array) {
			for (int val : arow)
				System.out.printf("%d,", val);
			System.out.printf("\n");
		}
		System.out.printf("]\n");
	}

	public static Map<String, Integer> listToMap(List<String> list) {
		Map<String,Integer> map = new HashMap<String,Integer>();
		int index = 0;
		for (String key : list)
			map.put(key, index++);
		return map;
	}

	public static int[][] loadArray(String filename) throws IOException {
		File file = new File(filename);
		if (!file.exists()) return null;
		BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
		String line = bufferedReader.readLine();
		String[] dims = line.split(",");
		int numRows = Integer.valueOf(dims[0]);
		int numCols = Integer.valueOf(dims[1]);
		int[][] array = new int[numRows][numCols];
		for (int row = 0; row < numRows; row++) {
			int[] arow = array[row];
			line = bufferedReader.readLine();
			dims = line.split(",");
			for (int col = 0; col < numCols; col++) {
				arow[col] = Integer.valueOf(dims[col]);
			}
		}
		bufferedReader.close();
		return array;
	}

	public static List<String> loadList(String filename) throws IOException {
		List<String> list = new ArrayList<String>();
		BufferedReader bufferedReader = new BufferedReader(new FileReader(filename));
		String line;
		while ((line = bufferedReader.readLine()) != null)
		{
			list.add(line);
		}
		bufferedReader.close();
		return list;
	}

	public static void saveArray(int[][] array, String filename) throws FileNotFoundException {
		PrintStream out = new PrintStream(filename);
		out.printf("%d,%d\n", array.length, array[0].length);
		for (int[] arow : array) {
			boolean first = true;
			for (int val : arow) {
				if (first) {
					first = false;
					out.printf("%d", val);
				} else {
					out.printf(",%d", val);
				}
			}
			out.printf("\n");
		}
		out.close();
	}

}
