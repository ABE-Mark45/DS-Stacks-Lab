package eg.edu.alexu.csd.datastructure.stack.cs5.UIApplication;

import java.util.Scanner;

import eg.edu.alexu.csd.datastructure.stack.cs5.Classes.ExpCalc;

public class ExpCalcUI {
	
	public static void main(String[] args) {
		ExpCalc e = new ExpCalc();
		
		System.out.println("Welcome to Expression Evaluation Program");
		System.out.println("Choose an action: ");
		System.out.println("1. Infix to Postfix");
		System.out.println("2. Evaluate");
		System.out.println("3. Exit");
		
		Scanner cin = new Scanner(System.in);
		
		while(true)
		{
			String in = cin.nextLine();
			if(in.equals("1"))
			{
				System.out.println("Enter your Postfix Expression: ");
				in = cin.nextLine();
				
				try
				{
					System.out.println("Infix: " + e.infixToPostfix(in));
				}catch(Exception ex)
				{
					System.out.println("Expression Invalid! Try again.");
				}
				
			}else if(in.equals("2"))
			{
				System.out.println("Enter your infix Expression: ");
				in = cin.nextLine();
				
				try
				{
					System.out.println("The value is: " + e.evaluate(in));
				}catch(Exception ex)
				{
					System.out.println("Expression Invalid! Try again.");
				}
				
			}else if(in.equals("3"))
			{
				System.out.println("Goodbye :)");
				break;
			}else
			{
				System.out.println("Invalid Action");
			}
			System.out.println("Choose an action: ");
		}
		
		cin.close();
	}
	
}
