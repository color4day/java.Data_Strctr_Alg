package redBlackTree;


import java.io.*;
import java.util.*; // for Stack class
////////////////////////////////////////////////////////////////

//----------
class Node
{
	public int iData; // data item (key)
	public Boolean color;//true=red, false= black
	public Node parent;
	public Node leftChild; // this node's left child
	public Node rightChild; // this node's right child
	//------------------
	public void displayNode() // display ourself
	{
		System.out.print('{');
		System.out.print(iData);
		System.out.print(", ");
		System.out.print("} ");
	}
} // end class Node
////////////////////////////////////////////////////////////////
class Tree
{
	public Node root; // first node of tree
//-------------------------------------------------------------
	public Tree() // constructor
	{ root = null; } // no nodes in tree yet
	//----------
	public void insertroot(Node a) {
		root = a;
	}
//-------------------------------------------------------------
	public void rotateRight(Node top) {
		Node p = top.parent;
		Node newTop = top.leftChild;
		if(p == null) {
			root = newTop;
		}
		else if(top == p.leftChild) {
			p.leftChild = newTop; 
			
		}
		else {
			p.rightChild = newTop;
		}
		newTop.parent = p;//link p and new top
		top.leftChild = newTop.rightChild;
		if(top.leftChild!= null) {
		top.leftChild.parent = top;}
		newTop.rightChild = top;
		top.parent = newTop;
	}
	//-------------------------
	public void rotateLeft(Node top) {
		Node p = top.parent;
		Node newTop = top.rightChild;
		if (p== null) {
			root = newTop;
		}
		else if(top == p.rightChild) {
			p.rightChild = newTop; 
			
		}
		else {
			p.leftChild = newTop;
		}
		newTop.parent = p;//link p and new top
		top.rightChild = newTop.leftChild;
		if(top.rightChild!= null) {
		top.rightChild.parent = top;}
		newTop.leftChild = top;
		top.parent = newTop;
	}
	//-------------------
	public boolean rrconflict(Node child) {
		boolean flag = false;
		if(child.color&&child.parent.color == true) {
			flag = true;
		}
		return flag;
	}
	//----------------------------------
	
	//--------------------------------
	
	public Node find(int key) // find node with given key
	{ // (assumes non-empty tree)
		Node current = root; // start at root
		while(current.iData != key) // while no match,
		{
			if(key < current.iData) // go left?
				current = current.leftChild;
			else // or go right?
				current = current.rightChild;
			if(current == null) // if no child,
				return null; // didn't find it
		}
		return current; // found it
	} // end find()
//-------------------------------------------------------------
	public void checkColor(Node parent) {
		if(parent.leftChild.color && parent.rightChild.color == true && parent.color == false) {
			//black p and 2 red child, flip make child black convenient for inserting new leaf
			flip(parent);
		}
	}
	//----------------------------------
	public void flip(Node p) {
		p.color=!p.color;
		p.leftChild.color=!p.leftChild.color;
		p.rightChild.color=!p.rightChild.color;
		root.color = false;
		
	}
	//----------------------
	public void adjust(Node c) {
		
		if(c!= root && c.color == true && c.parent.color == true) {
			solveRRconflict(c);
		}
		
	}
	//---------------
	public void solveRRconflict(Node c) {//during finding inserting place
		Node p = c.parent;
		Node g = p.parent;
		while(g!= null) {
			if(c== p.leftChild && p == g.leftChild) {
				g.color = !g.color;
				p.color = !p.color;
				rotateRight(g);
				break;
			}
			
			if(c == p.rightChild && p == g.rightChild) {
				g.color = !g.color;
				p.color = !p.color;
				rotateLeft(g);
				break;
			}
			
			if(c == p.leftChild && p == g.rightChild) {
				g.color = !g.color;
				c.color = !c.color;
				rotateRight(p);
				rotateLeft(g);
				break;
			}
			
			if(c == p.rightChild && p == g.leftChild) {
				g.color = !g.color;
				c.color = !c.color;
				rotateLeft(p);
				rotateRight(g);
				break;
			}
		}
		
	}
	//---------------------
	public void insert(int id)
	{
		Node newNode = new Node(); // make new node
		newNode.iData = id; // insert data
		newNode.color = true;
		if(root == null) { // no node in root
			root = newNode;
			root.color = false;//black root
		}
		else // root occupied
		{
			Node current = root; // start at root
			Node parent;
			while(true) // (exits internally)
			{
				parent = current;
				if(current.leftChild!= null && current.rightChild!= null) {
					checkColor(current);
				}//check if p == red and child == black& flip
				adjust(current);//check if p == red and current == red
				if(id < current.iData) // go left?
				{
					current = current.leftChild;
					if(current == null) // if end of the line,
					{ // insert on left
						parent.leftChild = newNode;
						newNode.parent = parent;
						adjust(newNode);//check if newNode parent is red
						return;
					}
				} // end if go left
				else // or go right?
				{
					current = current.rightChild;
					if(current == null) // if end of the line
					{ // insert on right
						parent.rightChild = newNode;
						newNode.parent = parent;
						adjust(newNode);
						return;
					}
				} // end else go right
			} // end while
		} // end else not root
	} // end insert()
//-------------------------------------------------------------

//-------------------------------------------------------------
	public void displayTree()
	{
		Stack globalStack = new Stack();//Stack<Node> globalStack = new Stack<>();
		globalStack.push(root);//node save in a stack
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
					
					System.out.print(temp.iData);
					localStack.push(temp.leftChild);//push child into stack
					localStack.push(temp.rightChild);
					if(temp.leftChild != null ||
							temp.rightChild != null)
						isRowEmpty = false;//row is not empty
				}
				else
				{
					System.out.print("--");//node is null
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
 class TreeApp {
	 public static void main(String[] args) throws IOException
	 {
		 Tree theTree = new Tree();
		 theTree.insert(50);//generate new Node in insert()method instead of generating a new node every time
		 theTree.insert(25);
		 theTree.displayTree();
		 theTree.insert(75);
		 theTree.displayTree();
		 theTree.insert(12);
		 theTree.displayTree();
		 theTree.insert(37);
		 theTree.displayTree();
		 theTree.insert(43);
		 theTree.displayTree();
		 theTree.insert(30);
		 theTree.displayTree();
		 theTree.insert(33);
		 theTree.displayTree();
		 theTree.insert(87);
		 theTree.displayTree();
		 theTree.insert(93);
		 theTree.displayTree();
		 theTree.insert(97);
		 theTree.displayTree();
		
	 } // end main()
	 // -------------------------------------------------------------
	 } // end class TreeApp
	 ////////////////////////////////////////////////////////////////

	/*public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.print("enter a string: ");
		Scanner scanner = new Scanner(System.in);
		String s = scanner.next();
		ArrayList<Node> nodeList = new ArrayList<>();
		//int nElems = s.length()-1;
		
		for (int i=0; i<s.length(); i++) {
			char ch = s.charAt(i);
			Node node = new Node();
			node.cData = ch;
			nodeList.add(node);//make a node list
		}
		//System.out.print(nodeList);
		//ArrayList<Tree> treeList = new ArrayList<>();
		ArrayList<Node> oldtreeList = (ArrayList<Node>) nodeList.clone();
		Node theRoot = new Node();
		while(true) {
			
			int k=0;
			ArrayList<Node> newtreeList = new ArrayList<>();
			while(2*k+1<oldtreeList.size()) 
			{//choose 2 non-empty node to be childnode
				Node rootNode = new Node();
				rootNode.cData = '+';//new father node
				rootNode.leftChild = oldtreeList.get(2*k);
				oldtreeList.get(2*k).parent = rootNode;
				rootNode.rightChild = oldtreeList.get(2*k+1);//link child and father
				oldtreeList.get(2*k+1).parent = rootNode;
				newtreeList.add(rootNode);//add the father node to new list
				k=k+1;
			}// end while
			if(2*k+1==oldtreeList.size()) //only one node left
			{
				newtreeList.add(oldtreeList.get(2*k));//add this node to new list
			}// end if
			oldtreeList = (ArrayList<Node>)newtreeList.clone();	
			if(newtreeList.size()==1) {
				theRoot= newtreeList.get(0);//until one tree left
				break;
			}//end if
		}//end while
		Tree balancedTree = new Tree();
		balancedTree.insertroot(theRoot);//build the final tree
		balancedTree.displayTree();
		balancedTree.rotateRight(theRoot);
		balancedTree.displayTree();
		balancedTree.rotateLeft(theRoot);
		balancedTree.displayTree();
	}*/
	


////////////////////////////////////////////////////////////////