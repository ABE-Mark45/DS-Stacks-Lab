package eg.edu.alexu.csd.datastructure.stack.cs5.UIApplication;

import java.util.Scanner;

import eg.edu.alexu.csd.datastructure.stack.cs5.Classes.StackDS;

public class StackUIApplication {
	
	public static void main(String[] args) {
		StackDS st = new StackDS();
		System.out.println("Welcome to Stack Application\nEnter the number of operation: ");
		System.out.println("1. Push");
		System.out.println("2. Pop");
		System.out.println("3. Peek");
		System.out.println("4. Get size");
		System.out.println("5. Check if empty");
		System.out.println("6. To exit the program");
		Scanner cin = new Scanner(System.in);
		
		while(true)
		{
			String in = cin.nextLine();
			if(in.equals("1"))
			{
				System.out.println("Enter the string you want to push: ");
				in = cin.nextLine();
				st.push(in);
				System.out.println("\"" + in + "\""+ " is pushed!");
			}else if(in.equals("2"))
			{
				try
				{
					System.out.println("\"" + (String)st.pop() + "\"" + " is popped!");
				}catch(Exception e)
				{
					System.out.println("Stack is empty!");
				}
			}else if(in.equals("3"))
			{
				try
				{
					System.out.println("\"" + (String)st.peek() + "\"" + " is the peek!");
				}catch(Exception e)
				{
					System.out.println("Stack is empty!");
				}
			}else if(in.equals("4"))
			{
				System.out.println("Size: " + st.size());
			}else if(in.equals("5"))
			{
				if(st.isEmpty())
					System.out.println("Stack is empty!");
				else
					System.out.println("Stack is not empty!");
			}else if(in.equals("6"))
			{
				System.out.println("Goodbye :) !");
				break;
			}else
			{
				System.out.println("Invalid Input!");
			}
			System.out.println("Enter the number of operation: ");
		}
		
		cin.close();
	}
}
