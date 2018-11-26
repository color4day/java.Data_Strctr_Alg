package hashChain;

//hashChain.java
//demonstrates hash table with separate chaining
//to run this program: C:>java HashChainApp
import java.io.*;

////////////////////////////////////////////////////////////////
class Node
{
	public int iData; // data item (key)
	public Node leftChild; // this node's left child
	public Node rightChild; // this node's right child
	//------------------
	public void displayNode() // display ourself
	{
		System.out.print('{');
		System.out.print(iData);
		
		System.out.print("} ");
	}
	//-----------
	
} // end class Node

////////////////////////////////////////////////////////////////
class Tree
{
	public Node root; // first node of tree
//-------------------------------------------------------------
	public Tree() // constructor
	{ root = null; } // no nodes in tree yet
	//----------
	public Node find(int key) // find node with given key
	{ // (assumes non-empty tree)
		Node current = root; // start at root
		if(current == null) {
			return null;
		}
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
	//----------------
	public void inOrder(Node localRoot)
	{
		if(localRoot != null)
		{
			//System.out.print("(");
			inOrder(localRoot.leftChild);
			System.out.print(localRoot.iData + " ");
			inOrder(localRoot.rightChild);
			//System.out.print(")");
		}
	}
	//-----------
	public void insert(int id)
	{
		Node newNode = new Node(); // make new node
		newNode.iData = id; // insert data
		if(root==null) // no node in root
			root = newNode;
		else // root occupied
		{
			Node current = root; // start at root
			Node parent;
			while(true) // (exits internally)
			{
				parent = current;
				if(id < current.iData) // go left?
				{
					current = current.leftChild;
					if(current == null) // if end of the line,
					{ // insert on left
						parent.leftChild = newNode;
						return;
					}
				} // end if go left
				else // or go right?
				{
					current = current.rightChild;
					if(current == null) // if end of the line
					{ // insert on right
						parent.rightChild = newNode;
						return;
					}
				} // end else go right
			} // end while
		} // end else not root
	} // end insert()
	//-----------------
}
////////////////////////////////////////////////////////////////
class HashTable
{
	private Tree[] hashArray; // array of trees
	private int arraySize;
//-------------------------------------------------------------
	public HashTable(int size) // constructor
	{
		arraySize = size;
		hashArray = new Tree[arraySize]; // create tree array
		for(int j=0; j<arraySize; j++) // fill array
			hashArray[j] = new Tree(); // with trees
	}
//-------------------------------------------------------------
	public void displayTable()
	{
		for(int j=0; j<arraySize; j++) // for each cell,
		{
			System.out.print(j + ". "); // display cell number
			hashArray[j].inOrder(hashArray[j].root); // display list
			System.out.println();
		}
		System.out.println();

	}
//-------------------------------------------------------------
	public int hashFunc(int key) // hash function
	{
		return key % arraySize;
	}
//-------------------------------------------------------------
	public void insert(Node theNode) // insert a Node
	{
		
		int key = theNode.iData;
		if(find(key)!= null) {//already exist this value
			System.out.println(key + " is already exist");
		}
		else {
			int hashVal = hashFunc(key); // hash the key
			hashArray[hashVal].insert(key); // insert at hashVal
		}
		
	} // end insert()
//-------------------------------------------------------------
//-------------------------------------------------------------
	public Node find(int key) // find Node
	{
		int hashVal = hashFunc(key); // hash the key
		Node theNode = hashArray[hashVal].find(key); // get Node
		return theNode; // return Node
	}
//-------------------------------------------------------------
} // end class HashTable
////////////////////////////////////////////////////////////////
class HashChainApp
{
	public static void main(String[] args) throws IOException
	{
		int aKey;
		Node aDataItem;
		int size, n, keysPerCell = 100;
		
	//get sizes
		System.out.print("Enter size of hash table: ");
		size = getInt();
		System.out.print("Enter initial number of items: ");
		n = getInt();
		//make table
		HashTable theHashTable = new HashTable(size);		
		for(int j=0; j<n; j++) // insert data
		{
			aKey = (int)(java.lang.Math.random() *
					keysPerCell * size);
			aDataItem = new Node();
			aDataItem.iData = aKey;
			theHashTable.insert(aDataItem);
		}
		while(true) // interact with user
		{
			System.out.print("Enter first letter of ");
			System.out.print("show, insert, or find: ");
			char choice = getChar();
			switch(choice)
			{
			case 's':
				theHashTable.displayTable();
				break;
			case 'i':
				System.out.print("Enter key value to insert: ");
				aKey = getInt();
				aDataItem = new Node();
				aDataItem.iData = aKey;
				theHashTable.insert(aDataItem);
				break;
			
			case 'f':
				System.out.print("Enter key value to find: ");
				aKey = getInt();
				aDataItem = theHashTable.find(aKey);
				if(aDataItem != null)
					System.out.println("Found " + aKey);
				else
					System.out.println("Could not find " + aKey);
				break;
			default:
				System.out.print("Invalid entry\n");
			} // end switch
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
} // end class HashChainApp
////////////////////////////////////////////////////////////////