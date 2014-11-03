import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;


public class Train {

	public static void main(String[] args) throws FileNotFoundException, IOException {

		String[][] dataset = loadDatasetFromFile("data_exercise_1.csv");
		
		System.out.println("Line 0: "+Arrays.toString(dataset[0]));
		System.out.println("Line 100: "+Arrays.toString(dataset[100]));

		Node root = new Node(5);
		insert(root, 1);
		insert(root, 8);
		insert(root, 6);
		insert(root, 3);
		insert(root, 9);
		printInOrder(root);
	}

	public static String[][] loadDatasetFromFile(String Filename) throws FileNotFoundException, IOException {

		String fileInput = "";

		// read file
		try(BufferedReader br = new BufferedReader(new FileReader(Filename))) {
			StringBuilder sb = new StringBuilder();
			String line = br.readLine();

			while (line != null) {
				sb.append(line);
				sb.append(";");
				line = br.readLine();
			}
			fileInput = sb.toString();
		}

		// split at end of line
		String[] splitResult = fileInput.split(";");

		// split every feature 
		String[][] dataset = new String[splitResult.length][];

		for (int x=0; x<splitResult.length; x++){
			dataset[x] = splitResult[x].split(",");
		}

		return dataset;

	}


	public static void insert(Node node, int value) {
		if (value < node.value) {
			if (node.left != null) {
				insert(node.left, value);
			} else {
				System.out.println("  Inserted " + value + " to left of "
						+ node.value);
				node.left = new Node(value);
			}
		} else if (value > node.value) {
			if (node.right != null) {
				insert(node.right, value);
			} else {
				System.out.println("  Inserted " + value + " to right of "
						+ node.value);
				node.right = new Node(value);
			}
		}
	}

	public static void printInOrder(Node node) {
		if (node != null) {
			printInOrder(node.left);
			System.out.println("  Traversed " + node.value);
			printInOrder(node.right);
		}
	}


}