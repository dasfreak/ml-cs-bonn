
public class Train {

	public static void main(String[] args) {
		Node root = new Node(5);
		insert(root, 1);
		insert(root, 8);
		insert(root, 6);
		insert(root, 3);
		insert(root, 9);
		printInOrder(root);
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