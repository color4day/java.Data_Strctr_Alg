package treeHeap;

//import tree.Node;
import java.io.*;
import java.util.Stack;

class Node{
	public int iData; // data item (key)
	public Node leftChild; // this node's left child
	public Node rightChild; // this node's right child
	public Node father;
	//-------------------------------------------------------------
	public Node(int key) // constructor
	{ iData = key; }
	//-------------------------------------------------------------
	public int getKey()
	{ return iData; }
	//------------
}
//------------
class Tree{
	private Node root; // first node of tree
	private int currentSize; 
	//-------------------------------------------------------------
	public Tree() // constructor
	{ root = null; 
	currentSize = 0;} // no nodes in tree yet
	//----------
	public Node dir(Node current, int direction) {
		if(direction == 0)
		{
			return current.leftChild;
		}
		else
			return current.rightChild;
	}
	//--------------
	public Node nodeNumber(int n) {
		//Node current = root;
		if(n == 1) {
		return root;}
		else
		return dir(nodeNumber(n/2), n%2);
	}
	//----------
	public void trickleUp(Node n) {
		int data = n.iData;
		Node current = n;
		while(current.father!=null) {
			if(data<=current.father.iData) {
				break;
			}
			current.iData = current.father.iData;
			current = current.father;
		}
		current.iData =data;
	}
	//----------
	public void trickleDown(Node n) {
		int data = n.iData;
		Node current=n;
		while(current.leftChild!=null) //child is not null
		{
			Node largerChild = current.leftChild;
			if(current.rightChild!= null && current.leftChild.iData<current.rightChild.iData) {
				largerChild = current.rightChild;
			}
			if(data>=largerChild.iData)
				break;
			/*int temp = current.iData;
			int	temp1 = largerChild.iData;
			current.iData = temp1;
			largerChild.iData = temp;*/
			current.iData = largerChild.iData;
			current = largerChild;
		}
		current.iData = data;
	}
	//------
	public Node remove() {
		Node removed = new Node(root.iData);
		if(currentSize == 1) {
			currentSize--;
			return removed;
		}
		/*if(currentSize == 0) {
			System.out.println("the Heap is empty");
			return null;
		}*/
		root.iData = nodeNumber(currentSize).iData;//copy last one to root
		Node current = nodeNumber(currentSize/2);
		if(currentSize%2 == 0)
		{
			current.leftChild=null;
			//newNode.father=current;
		}
		else
		{
			current.rightChild = null;
			//newNode.father=current;
		}
		currentSize--;
		//nodeNumber(currentSize) = null;//set last one void
		trickleDown(root); 
		return removed;
	}
	//---------------
	public void insert(int key) {	
		Node newNode = new Node(key);	
		currentSize++;
		if(currentSize == 1) 
		{
			root = newNode;
			return;
		}
		Node current = nodeNumber(currentSize/2);
		if(currentSize%2 == 0)
		{
			current.leftChild=newNode;
			newNode.father=current;
		}
		else
		{
			current.rightChild = newNode;
			newNode.father=current;
		}
		
		trickleUp(newNode);
	}
	//--------------
	public void change(int index, int newValue)
	{
		if(index<0 || index>currentSize) 
		{
			System.out.println("Invalid index");
			return;
		}
		Node theNode = nodeNumber(index);
		int oldValue = theNode.iData; // remember old
		theNode.iData = newValue; // change to new
		if(oldValue < newValue) // if raised,
			trickleUp(theNode); // trickle it up
		else // if lowered,
			trickleDown(theNode); // trickle it down
	} // end change()
	//----------
	public boolean isEmpty() {
		if(currentSize == 0) {
			return true;
		}
		else 
			return false;
	}
}
//--------------------
class TreeHeapApp{
	public static void main(String[] args) throws IOException{
		Tree theTreeHeap = new Tree();
		theTreeHeap.insert(10);
		theTreeHeap.insert(20);
		theTreeHeap.insert(80);
		theTreeHeap.insert(90);
		theTreeHeap.insert(30);
		theTreeHeap.insert(40);
		theTreeHeap.insert(70);
		
		while(!theTreeHeap.isEmpty()) {
		System.out.print(theTreeHeap.remove().iData+" ");
		}	
		System.out.println();
		theTreeHeap.insert(10);
		theTreeHeap.insert(20);
		theTreeHeap.insert(80);
		theTreeHeap.insert(90);
		theTreeHeap.insert(30);
		theTreeHeap.insert(40);
		theTreeHeap.insert(70);
		theTreeHeap.change(1, 5);
		theTreeHeap.change(4, 50);
		while(!theTreeHeap.isEmpty()) {
		System.out.print(theTreeHeap.remove().iData+" ");
		}	
	}

}

