import java.util.*;

//binary search tree class that does not allow duplicates. Contains integers

public class BinarySearchTree {

	private class TreeNode {

		private int data;
		private TreeNode leftChild;
		private TreeNode rightChild;

		private TreeNode(int data) {
			this.data = data;
			this.leftChild = null;
			this.rightChild = null;
		}

	}

	private TreeNode root;
	private int size;

	// initialize empty tree
	public BinarySearchTree() {
		this.root = null;
		this.size = 0;
	}

	// initialize tree with root
	public BinarySearchTree(int root) {
		this.root = new TreeNode(root);
		this.size = 1;
	}

	/*
	 * initialize tree with array input. Numbers will be added in order given by
	 * array. If there are duplicates, only one copy will be added.
	 */
	public BinarySearchTree(int[] nodes) {
		if (nodes.length == 0) {
			this.size = 0;
			this.root = null;
		} 
		else if (nodes.length == 1) {
			this.root = new TreeNode(nodes[0]);
			this.size = 1;
		} 
		else {
			this.size = 0;
			this.root = new TreeNode(nodes[0]);
			for (int i = 1; i < nodes.length; i++) {
				add(nodes[i]);
			}
		}
	}

	public int getSize() {
		return size;
	}

	// add number to tree
	public boolean add(int data) {
		return addHelper(root, data);
	}

	private boolean addHelper(TreeNode current, int data) {
		if (current == null) {
			root = new TreeNode(data);
			size++;
			return true;
		} 
		else if (current.data == data) {
			return false;
		} 
		else {
			if (data < current.data) {
				if (current.leftChild == null) {
					current.leftChild = new TreeNode(data);
					size++;
					return true;
				} 
				else {
					return addHelper(current.leftChild, data);
				}
			} 
			else {
				if (current.rightChild == null) {
					current.rightChild = new TreeNode(data);
					size++;
					return true;
				} 
				else {
					return addHelper(current.rightChild, data);
				}
			}
		}
	}

	// test to see if tree contains specified number
	public boolean contains(int target) {
		return containsHelper(root, target);
	}

	private boolean containsHelper(TreeNode current, int target) {

		if (current == null){
			return false;
		}
		else if (target == current.data) {
			return true;
		} 
		else if (target < current.data) {
			return containsHelper(current.leftChild, target);
		} 
		else {
			return containsHelper(current.rightChild, target);
		}
	}

	// return ArrayList of inorder traversal (left -> root -> right)
	public ArrayList<Integer> inorder() {
		ArrayList<Integer> inorderTraversal = new ArrayList<Integer>();
		inorderHelper(inorderTraversal, root);
		return inorderTraversal;
	}

	private void inorderHelper(ArrayList<Integer> list, TreeNode current) {
		if (current == null){
			return;
		}
		inorderHelper(list, current.leftChild);
		list.add(current.data);
		inorderHelper(list, current.rightChild);
	}

	// return ArrayList of preorder traversal (root -> left -> right)
	public ArrayList<Integer> preorder() {
		ArrayList<Integer> preorderTraversal = new ArrayList<Integer>();
		preorderHelper(preorderTraversal, root);
		return preorderTraversal;
	}

	private void preorderHelper(ArrayList<Integer> list, TreeNode current) {
		if (current == null) {
			return;
		}
		list.add(current.data);
		preorderHelper(list, current.leftChild);
		preorderHelper(list, current.rightChild);
	}

	// return ArrayList of postorder traversal (left -> right -> root)
	public ArrayList<Integer> postorder() {
		ArrayList<Integer> postorderTraversal = new ArrayList<Integer>();
		postorderHelper(postorderTraversal, root);
		return postorderTraversal;
	}

	private void postorderHelper(ArrayList<Integer> list, TreeNode current) {
		if (current == null)
			return;
		postorderHelper(list, current.leftChild);
		postorderHelper(list, current.rightChild);
		list.add(current.data);
	}

	// returns ArrayList of levelorder traversal
	public ArrayList<Integer> levelorder() {
		ArrayList<Integer> levelorderTraversal = new ArrayList<Integer>();
		levelorderHelper(levelorderTraversal, root);
		return levelorderTraversal;
	}

	private void levelorderHelper(ArrayList<Integer> list, TreeNode root) {
		Queue<TreeNode> q = new LinkedList<TreeNode>();
		if (root != null){
			q.add(root);
		}
		while (!q.isEmpty()) {
			TreeNode current = q.poll();
			list.add(current.data);
			if (current.leftChild != null){
				q.add(current.leftChild);
			}
			if (current.rightChild != null){
				q.add(current.rightChild);
			}
		}
	}

	// remove a specified number from the tree. return true if successful, false
	// otherwise
	public boolean remove(int target) {
		return removeHelper(root, null, target);
	}

	private boolean removeHelper(TreeNode current, TreeNode parent, int target) {
		if (current == null) { //at this point, tree does not contain target
			return false;
		}
		else if (target < current.data) {
			return removeHelper(current.leftChild, current, target);
		} 
		else if (target > current.data) {
			return removeHelper(current.rightChild, current, target);
		} 
		else { // found node to remove
			if (current.leftChild == null && current.leftChild == null) { // node to remove has zero children
				if (current == root) { // node to remove is root, could also use parent == null as condition
					root = null;
				} 
				else { // node to remove is not root
					if (parent.leftChild == current) { // current node is parent's left child
						parent.leftChild = null;
					} 
					else { // current node is parent's right child
						parent.rightChild = null;
					}
				}
			}
			else if (current.leftChild != null && current.rightChild != null) { // node to remove has two children
				current.data = getMaxFromLeft(current.leftChild, current);
			}
			else { // node to remove has one child
				if (current == root) { // node to remove is root
					root = (current.leftChild != null) ? current.leftChild : current.rightChild;
				} 
				else { // node to remove is not root
					if (parent.leftChild == current) { // current node is parent's left child
						parent.leftChild = (current.leftChild != null) ? current.leftChild : current.rightChild;
					} 
					else {// current node is parent's right child
						parent.rightChild = (current.leftChild != null) ? current.leftChild : current.rightChild;
					}
				}
			}
			size--;
			return true;
		}
	}

	//locates the maximum node in target node's left tree, removes that node and returns its value; replaces target nodes value 
	private int getMaxFromLeft(TreeNode node, TreeNode parent) {
		if (node.rightChild == null) {
			parent.leftChild = node.leftChild;
			return node.data;
		} 
		else {
			while (node.rightChild != null) {
				parent = node;
				node = node.rightChild;
			}
			parent.rightChild = null;
			return node.data;
		}

	}

}
