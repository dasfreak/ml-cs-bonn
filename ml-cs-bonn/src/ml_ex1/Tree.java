package ml_ex1;

public class Tree {
	private Node   root;
	public Tree(Node root)
	{
		this.root = root;
	}
	public String toString()
	{
		String tree = "";
		Node parent = root;
		tree+=parent.index;
		
		return tree;
	}
}

