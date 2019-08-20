package hash;

//hash.java
//demonstrates hash table with linear probing
//to run this program: C:>java HashTableApp
import java.io.*;
////////////////////////////////////////////////////////////////
class DataItem
{ // (could have more data)
	private int iData; // data item (key)
//--------------------------------------------------------------
	public DataItem(int ii) // constructor
	{ iData = ii; }
//--------------------------------------------------------------
	public int getKey()
	{ return iData; }
//--------------------------------------------------------------
} // end class DataItem
////////////////////////////////////////////////////////////////
class HashTable
{
	public int nElems;
	public int DataS;
	private DataItem[] hashArray; // array holds hash table
	public int arraySize;
	private DataItem nonItem; // for deleted items
//-------------------------------------------------------------
	public HashTable(int size) // constructor
	{
		arraySize = size;
		hashArray = new DataItem[arraySize];
		nElems = 0;
		nonItem = new DataItem(-1); // deleted item key is -1
	}
//-------------------------------------------------------------
	public void displayTable()
	{
		System.out.print("Table: ");
		for(int j=0; j<arraySize; j++)
		{
			if(hashArray[j] != null)
				System.out.print(hashArray[j].getKey() + " ");
			else
				System.out.print("** ");
		}
		System.out.println("");
	}
	//-------------------------------------------------------------
	public int hashFunc(int key)
	{
		return key % arraySize; // hash function
	}
	//---------------
	private int getPrime(int min) // returns 1st prime > min
	{
		for(int j = min+1; true; j++) // for all j > min
			if( isPrime(j) ) // is j prime?
				return j; // yes, return it
	}
	// -------------------------------------------------------------
	private boolean isPrime(int n) // is n prime?
	{
		for(int j=2; (j*j <= n); j++) // for all j
			if( n % j == 0) // divides evenly by j?
				return false; // yes, so not prime
		return true; // no, so prime
	}
	//-------------
	public void rehash() {
		int oldSize = arraySize;
		int newsize = getPrime(2*oldSize);//get new size
		
		DataItem[] temp = new DataItem[arraySize];//array temp
		for(int j=0; j<arraySize; j++) 
		{
			if(hashArray[j] != null && hashArray[j].getKey() != -1) 
			{
			temp[j]=hashArray[j];// copy non-null items
			}
		}
		arraySize = newsize;
		hashArray = new DataItem [arraySize];//new hashArray
		//nElems = 0;
		for(int j=0; j<oldSize; j++) 
		{
			if(temp[j] != null && temp[j].getKey() != -1)
				insert(temp[j]);
		}
		
	}
	//-------------------------------------------------------------
	public void insert(DataItem item) // insert a DataItem
	//(assumes table not full)
	{
		int key = item.getKey(); // extract key
		
		//float loadFactor =(nElems+1)/arraySize;
		if(2*(nElems+1) >= arraySize) {
			nElems = 0;
			rehash();
		}
		int hashVal = hashFunc(key); // hash the key
		//until empty cell or -1,
		while(hashArray[hashVal] != null &&
				hashArray[hashVal].getKey() != -1)//no space
		{
			++hashVal; // go to next cell
			hashVal %= arraySize; // wrap around if necessary 
		}
		hashArray[hashVal] = item; // insert item
		nElems++;
	} // end insert()
	
//-------------------------------------------------------------
	public DataItem delete(int key) // delete a DataItem
	{
		int hashVal = hashFunc(key); // hash the key
		while(hashArray[hashVal] != null) // until empty cell,
		{ // found the key?
			if(hashArray[hashVal].getKey() == key)
			{
				DataItem temp = hashArray[hashVal]; // save item
				hashArray[hashVal] = nonItem; // delete item
				nElems--;
				return temp; // return item
			}
			++hashVal; // go to next cell
			hashVal %= arraySize; // wraparound if necessary
		}
		return null; // can't find item
	} // end delete()
//-------------------------------------------------------------
	public DataItem find(int key) // find item with key
	{
		int hashVal = hashFunc(key); // hash the key
		while(hashArray[hashVal] != null) // until empty cell,
		{ // found the key?
			if(hashArray[hashVal].getKey() == key)
				return hashArray[hashVal]; // yes, return item
			++hashVal; // go to next cell
			hashVal %= arraySize; // wrap around if necessary
		}
		return null; // can't find item
	}
//-------------------------------------------------------------
} // end class HashTable
////////////////////////////////////////////////////////////////
class HashTableApp
{
	public static void main(String[] args) throws IOException
	{
		DataItem aDataItem;
		int aKey, size, n, keysPerCell;//get sizes
		System.out.print("Enter size of hash table: ");
		size = getInt();
		System.out.print("Enter initial number of items: ");
		n = getInt();
		keysPerCell = 10;//make table
		HashTable theHashTable = new HashTable(size);
		for(int j=0; j<n; j++) // insert data
		{
			aKey = (int)(java.lang.Math.random() *
					keysPerCell * size);
			aDataItem = new DataItem(aKey);
			theHashTable.insert(aDataItem);
		}
		while(true) // interact with user
		{
			System.out.print("Enter first letter of ");
			System.out.print("show, insert, delete, or find: ");
			char choice = getChar();
			switch(choice)
			{
			case 's':
				theHashTable.displayTable();
				break;
			case 'i':
				System.out.print("Enter key value to insert: ");
				aKey = getInt();
				aDataItem = new DataItem(aKey);
				theHashTable.insert(aDataItem);
				break;
			case 'd':
				System.out.print("Enter key value to delete: ");
				aKey = getInt();
				theHashTable.delete(aKey);
				break;
			case 'f':
				System.out.print("Enter key value to find: ");
				aKey = getInt();
				aDataItem = theHashTable.find(aKey);
				if(aDataItem != null)
				{
					System.out.println("Found " + aKey);
				}
				else
					System.out.println("Could not find " + aKey);
				break;
			default:
				System.out.print("Invalid entry\n");
			} // end switch
		} // end while*/
	} // end main()
//--------------------------------------------------------------
	public static String getString() throws IOException
	{
		InputStreamReader isr = new InputStreamReader(System.in);
		BufferedReader br = new BufferedReader(isr);
		String s = br.readLine();
		return s;
	}
//--------------------------------------------------------------
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
} // end class HashTableApp
////////////////////////////////////////////////////////////////