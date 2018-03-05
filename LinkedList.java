import java.util.NoSuchElementException;

public class LinkedList {
	
	private class Node {
		int data;
		Node next;
		
		private Node(int data) {
			this.data = data;
		}
	}
	
	Node head;
	Node tail;
	int size;
	
	public LinkedList(){
		this.head = null;
		this.tail = null;
		this.size = 0;
	}
	
	public int size() {
		return size;
	}
	
	//add number to beginning of list: O(1)
	public void addFirst(int data){		
		if (size == 0) add(data); //empty list condition
		else {
			Node newNode = new Node(data);
			newNode.next = head;
			head = newNode;
			size++;
		}
	}
	
	//add number to end of list: O(1)
	public void add(int data) {
		Node newNode = new Node(data);
		if (size == 0) head = newNode; //list currently empty, so update head to new element		
		else tail.next = newNode; //add element to end of list
		
		//update tail to most recent element and increment size
		tail = newNode;
		size++;
	}
	
	
	//add number to specified index: O(n)
	public void add(int index, int data) throws IndexOutOfBoundsException{
		if (index < 0 || index > size){
			throw new IndexOutOfBoundsException();
		}
		if (index == 0) addFirst(data);
		else if (index == size) add(data);
		else {
			Node newNode = new Node(data);			
			Node current = head;
			//set current to node before desired index
			for (int i = 1; i < index; i++){
				current = current.next;
			}
			//insert new node into specified index
			newNode.next = current.next;
			current.next = newNode;
			
			//increment size within else statement, since add and addFirst increment size
			size++;
		}
		
	}
	
	//removes all numbers from list: O(1)
	public void clear() {
		this.head =  null;
		this.tail = null;
		this.size = 0;
	}
	
	//returns a new list with same numbers in same order: O(n)
	public LinkedList clone() {
		LinkedList newList =  new LinkedList();
		Node current =  head;
		
		while (current != null){			
			newList.add(current.data);
			current = current.next;
		}
		
		return newList;
	}
	
	//search for number within list: O(n)
	public boolean contains(int data) {
		Node current = head;
		while (current != null){
			if (current.data == data) return true;
			current = current.next;
		}
		return false;
	}
	
	//returns first number in list: O(1)
	public int getFirst() throws NoSuchElementException{
		if (size == 0){
			throw new NoSuchElementException();
		}
		else {
			return head.data;
		}
	}
	
	//returns last number in list: O(1)
	public int getLast() throws NoSuchElementException {
		if (size == 0) {
			throw new NoSuchElementException();
		}
		else {
			return tail.data;
		}
	}
	
	//returns number at specified index: O(n)
	public int get(int index) throws IndexOutOfBoundsException{
		if (index < 0 || index >= size){
			throw new IndexOutOfBoundsException();
		}
		else if (index == 0) return getFirst();
		else if (index == size - 1) return getLast();
		else{
			Node current = head;
			//traverse to desired index
			for (int i = 0; i < index; i++){
				current = current.next;
			}
			return current.data;
		}
	}
	
	//returns index of first occurrence of number, or -1 if list does not contain number: O(n)
	public int indexOf(int target){
		Node current = head;
		int index = 0;
		while (current != null){
			if (current.data == target) return index;
			else{
				current = current.next;
				index++;
			}
		}
		
		//target was never found
		return -1;
	}
	
	//returns index of last occurrence of number, or -1 if list does not contain number: O(n)
	public int lastIndexOf(int target){
		int targetIndex = -1;
		int index = 0;
		Node current = head;
		while (current != null){
			if (current.data == target) targetIndex = index;
			current = current.next;
			index++;
		}
		return targetIndex;
	}
	
	/* returns integer array containing all indices of occurrences
	 * of specified number, returns empty array if list does not 
	 * contain target: O(n)
	 */
	 
	public int[] allIndicesOf(int target){
		StringBuilder sb = new StringBuilder();
		Node current = head;
		int index = 0;
		while (current != null){
			if (current.data == target){
				sb.append(',' + index);
			}
			current = current.next;
			index++;
		}
		if (sb.length() == 0) return new int[0];
		String[] indices = sb.toString().substring(1).split(",");
		int[] indexIntegers = new int[indices.length];
		for (int i = 0; i < indices.length; i++){
			indexIntegers[i] = Integer.parseInt(indices[i]);
		}
		return indexIntegers;
	}
	
	//remove first number from list: O(1)
	public boolean removeFirst(){
		if (size == 0) return false;
		else{
			head = head.next;
			size--;
			return true;
		}
	}
	
	//remove last number from list: O(n)
	public boolean removeLast(){
		if (size == 0) return false;
		else if (size == 1) return removeFirst();
		else {
			Node current = head;
			while (current.next.next != null){
				current = current.next;
			}
			current.next = null;
			tail = current;
			size--;
			return true;
		}
	}
	
	//remove number from specified index: O(n)
	public boolean remove(int index) throws IndexOutOfBoundsException{
		if (index < 0 || index >= size){
			throw new IndexOutOfBoundsException();
		}
		else if (index == 0) return removeFirst();
		else if (index == size - 1) return removeLast();
		else {
			Node current = head;
			//traverse to element just before target index
			for (int i = 1; i < index; i++){
				current = current.next;
			}
			current.next = current.next.next;
			size--;
			return true;
		}
	}

}
