package ml_ex1;

public class Tree {
	public Node   root;
	
	
	public Tree(Node root)
	{
		this.root = root;
	}
	
	
	public String toString()
	{
		String tree = "";
		Node parent = root;
		for ( Node n : root.getChildren() )
		{
			tree += n.toString() +"\n";
		}
		return tree;
	}
	

}

