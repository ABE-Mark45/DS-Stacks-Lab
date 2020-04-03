package eg.edu.alexu.csd.datastructure.stack.cs5.Classes;

import java.util.EmptyStackException;

import eg.edu.alexu.csd.datastructure.stack.cs5.Interfaces.IStack;




public class StackDS implements IStack 
{
	/**
	 *  The top element of the stack
	 */
	Node top;
	/**
	 *  A variable to keep hold of the number of elements in the stack
	 *  so that we can get the size in O(1)
	 */
	int numOfElements;
	
	
	/**
	 * A helper class to store stack elements
	 */
	class Node
	{
		Object element;
		Node next;
		
		Node()
		{
			element = null;
			next = null;
		}
		
		Node(Object data)
		{
			element = data;
			next = null;
		}
	}
	
	
	/**
	 * The constructor initializes the stack
	 */
	public StackDS()
	{
		top = null;
		numOfElements = 0;
	}
	
	public Object pop() {
		if(isEmpty())
			throw new EmptyStackException();
		
		numOfElements--;
		Object element = top.element;
		top = top.next;
		return element;
	}

	
	public Object peek() {
		if(top == null)
			throw new EmptyStackException();
		return top.element;
	}

	
	public void push(Object element) {
		if(top == null)
			top = new Node(element);
		else
		{
			Node tmp = new Node(element);
			tmp.next = top;
			top = tmp;
		}
		numOfElements++;
	}

	
	public boolean isEmpty() {
		return numOfElements == 0;
	}

	
	public int size() {
		return numOfElements;
	}	
	
}
