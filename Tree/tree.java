package arithmeticexpressions;
//tree.java
//demonstrates binary tree
//to run this program: C>java TreeApp
import java.io.*;
import java.util.*; // for Stack class
////////////////////////////////////////////////////////////////
class Node
{
	public int iData; // data item (key)
	public double dData; // data item
	public char cData;
	public Node leftChild; // this node's left child
	public Node rightChild; // this node's right child
	//------------------
	public void displayNode() // display ourself
	{
		System.out.print('{');
		System.out.print(iData);
		System.out.print(", ");
		System.out.print(dData);
		System.out.print("} ");
	}
} // end class Node
////////////////////////////////////////////////////////////////
class Tree
{
	private Node root; // first node of tree
//-------------------------------------------------------------
	public Tree() // constructor
	{ root = null; } // no nodes in tree yet
	//----------
	public void insertroot(Node a) {
		root = a;
	}
	//-------------------------------------------------------------
	public void traverse(int traverseType)
	{
		switch(traverseType)
		{
		case 1: System.out.print("\nPreorder traversal: ");
		preOrder(root);
		break;
		case 2: System.out.print("\nInorder traversal: ");
		inOrder(root);
		break;
		case 3: System.out.print("\nPostorder traversal: ");
		postOrder(root);
		break;
		}
		System.out.println();
	}	
	
//-------------------------------------------------------------
	private void preOrder(Node localRoot)
	{
		if(localRoot != null)
		{
			System.out.print(localRoot.cData + " ");
			preOrder(localRoot.leftChild);
			preOrder(localRoot.rightChild);
		}
	}
	//-------------------------------------------------------------
	private void inOrder(Node localRoot)
	{
		if(localRoot != null)
		{
			System.out.print("(");
			inOrder(localRoot.leftChild);
			System.out.print(localRoot.cData + " ");
			inOrder(localRoot.rightChild);
			System.out.print(")");
		}
}
//-------------------------------------------------------------
	private void postOrder(Node localRoot)
	{
		if(localRoot != null)
		{
			postOrder(localRoot.leftChild);
			postOrder(localRoot.rightChild);
			System.out.print(localRoot.cData + " ");
		}	
	}
//-------------------------------------------------------------
	public void displayTree()
	{
		Stack globalStack = new Stack();//Stack<Node> globalStack = new Stack<>();
		globalStack.push(root);
		int nBlanks = 32;
		boolean isRowEmpty = false;
		System.out.println(
				"......................................................");
		while(isRowEmpty==false)
		{
			Stack localStack = new Stack();
			isRowEmpty = true;
			for(int j=0; j<nBlanks; j++)
				System.out.print(' ');
			while(globalStack.isEmpty()==false)
			{
				Node temp = (Node)globalStack.pop();
				if(temp != null)
				{
					System.out.print(temp.cData);
					localStack.push(temp.leftChild);
					localStack.push(temp.rightChild);
					if(temp.leftChild != null ||
							temp.rightChild != null)
						isRowEmpty = false;
				}
				else
				{
					System.out.print("--");
					localStack.push(null);
					localStack.push(null);
				}
				for(int j=0; j<nBlanks*2-2; j++)
					System.out.print(' ');
			} // end while globalStack not empty
			System.out.println();
			nBlanks /= 2;
			while(localStack.isEmpty()==false)
				globalStack.push( localStack.pop() );
		} // end while isRowEmpty is false
		System.out.println(
				"......................................................");
	} // end displayTree()
//-------------------------------------------------------------
} // end class Tree
////////////////////////////////////////////////////////////////
class ParsePost
{
	private Stack theStack;
	private String input;
//--------------------------------------------------------------
	public ParsePost(String s)
	{ input = s; }
	//-------------------
	public Node buildTree(Node root, Node leftChild, Node rightChild) {
		root.leftChild = leftChild;
		root.rightChild = rightChild;
		return root;
	}
//--------------------------------------------------------------
	public Tree doParse()
	{
		Stack<Node> theStack = new Stack<>();
		//theStack = new StackX(20); // make new stack
		char ch;
		int j;
		Node treeRoot = null;
		Tree arithTree = new Tree();
		for(j=0; j<input.length(); j++) // for each char,
		{
			ch = input.charAt(j); // read from input
			Node node= new Node();
			node.cData=ch;
			if(Character.isLetter(ch)==true)
				theStack.push(node);
			else // it's an operator
			{
				Node nodeR = theStack.pop(); // pop operands
				Node nodeL = theStack.pop();
				treeRoot = buildTree(node,nodeL,nodeR);
				theStack.push(treeRoot); // push result
			} // end else
		} // end for
		treeRoot = theStack.pop(); // get answer
		arithTree.insertroot(treeRoot);
		return arithTree;
	} // end doParse()
} // end class ParsePost
////////////////////////////////////////////////////////////////
class TreeApp
{
	public static void main(String[] args) throws IOException
	{
		int value;
		String input;
		Tree output;
		while(true)
		{
			System.out.print("Enter postfix: ");
			System.out.flush();
			input = getString(); // read a string from kbd
			if( input.equals("") ) // quit if [Enter]
				break;
			//make a parser
			ParsePost aParser = new ParsePost(input);
			output = aParser.doParse(); // do the evaluation
			while(true)
			{
				System.out.print("Enter first letter of show or traverse: ");
				int choice = getChar();
				switch(choice)
				{
				case 's':
				output.displayTree();
				break;
				case 't':
					System.out.print("Enter type 1, 2 or 3: ");
					value = getInt();
					output.traverse(value);
					break;
				default:
					System.out.print("Invalid entry\n");
				} // end switch
			} // end while
			//output.displayTree();
			//System.out.println("Evaluates to " + output);
		} // end while
	} // end main()
	//--------------------------------------------------------------
	public static String getString() throws IOException
	{
		InputStreamReader isr = new InputStreamReader(System.in);
		BufferedReader br = new BufferedReader(isr);
		String s = br.readLine();
		return s;
	}
	//-------------------------------------------------------------
		public static char getChar() throws IOException
		{
			String s = getString();
			return s.charAt(0);
		}
		//-------------------------------------------------------------
		public static int getInt() throws IOException
		{
			String s = getString();
			return Integer.parseInt(s);
		}
//--------------------------------------------------------------
} // end class PostfixApp
///////////////////////////////////////////////////////////