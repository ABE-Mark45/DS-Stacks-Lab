package eg.edu.alexu.csd.datastructure.stack.cs5.Classes;

import java.util.Scanner;

import eg.edu.alexu.csd.datastructure.stack.cs5.Classes.StackDS;
import eg.edu.alexu.csd.datastructure.stack.cs5.Interfaces.IExpressionEvaluator;

public class ExpCalc implements IExpressionEvaluator 
{
	
	/**
	 * A helper class to better organize the code
	 **/
	public static class Pair
	{
		String s;
		int i;
		
		public Pair(String s, int i)
		{
			this.s = s;
			this.i = i;
		}
	}
	
	
	
	public String infixToPostfix(String expression) 
	{
		StackDS st = new StackDS();
		int numOfNeg = 0;					// Number of negative signs parsed to keep track of cases such as "3---5"
		Boolean lastOperator = true;		// An indicator of whether the last parsed character was an operator
		char prevOp = 0;					// The last operator parsed
		
		StringBuilder exp = new StringBuilder();	// A variable to store the final postfix expression
		
		for(int i = 0; i < expression.length();i++)
		{
			
			i = ignoreWhiteSpace(expression, i);
			
			/**
			 *	if the last parsed block was an operator, that means it is
			 *	a negative sign, not a minus sign
			 **/
			if(lastOperator && expression.charAt(i) == '-')
			{
				numOfNeg++;
				prevOp = '-';
				i = ignoreWhiteSpace(expression, i+1);
				Pair p = extractWord(expression, i);
				i = p.i-1;
				if(p.s.length() != 0)
				{
					lastOperator = false;
					if(numOfNeg % 2 == 1)
					{
						exp.append("0 " + p.s + " - ");
					}
					else
					{
						exp.append(p.s + " ");
					}
					numOfNeg = 0;
				}
			}
			/**
			 *	if the last parsed block was an operator, that means it is
			 *	a unary plus, not a binary plus
			 **/
			else if(lastOperator && expression.charAt(i) == '+')
			{
				i = ignoreWhiteSpace(expression, i+1);
				Pair p = extractWord(expression, i);
				i = p.i-1;
				if(p.s.length() != 0)
				{
					lastOperator = false;
					if(numOfNeg % 2 == 1)
					{
						exp.append("0 " + p.s + " - ");
						numOfNeg = 0;
					}else
					{
						exp.append(p.s + " ");
					}
				}
			}
			/**
			 * pushes "(" to the operators stack
			 **/
			else if(expression.charAt(i) == '(')
			{
				st.push(expression.charAt(i));
				lastOperator = true;

			}
			/**
			 *	This block parses variables and numerals 
			 **/
			else if(isAlphanum(expression.charAt(i)))
			{
				/**
				 * The if statement handles consecutive variables without
				 * an operator in between. (e.g "123 456" is invalid)
				 **/
				if(!lastOperator)
					throw new RuntimeException("Invalid Expression");
				
				Pair p = extractWord(expression, i);
				i = p.i;
				
				if(p.s.length() != 0)
				{
					lastOperator = false;
					exp.append(p.s + " ");
					i--;

				}
			}
			/**
			 * This block begins to evaluate the operations between brackets
			 * and throws error in case of imbalance
			 **/
			else if(expression.charAt(i) == ')')
			{
				while(!st.isEmpty() && (char)st.peek() != '(')
				{
					char x = (char)st.pop();
					exp.append(x);
					exp.append(" ");
				}
				if(st.isEmpty())
					throw new RuntimeException("Brackets are not balanced");
				else
					st.pop();
			}
			/**
			 * This block parses operators and throws error in case of invalid
			 * consecutive operators such as "+*", "+/", etc.
			 **/
			else
			{				
				if(lastOperator && (prevOp == 0 || "+-*/".indexOf(prevOp) != -1) && (expression.charAt(i) == '*' || expression.charAt(i) == '/'))
					throw new RuntimeException("Invalid Expression");
				
				if(lastOperator && !(prevOp == '-') && expression.charAt(i) == prevOp)
					throw new RuntimeException("Invalid Expression. Repeated.");
				if(lastOperator && (prevOp == '+' || prevOp == '-') && expression.charAt(i) == '+')
					continue;
				
				lastOperator = true;
				while(!st.isEmpty() && priority(expression.charAt(i)) <= priority((char)st.peek()))
				{
					char x = (char)st.pop();
					exp.append(x);
					exp.append(" ");
				}
				prevOp = expression.charAt(i);
				st.push(expression.charAt(i));
			}
		}
		
		/**
		 * if The last parsed thing was an operator it throws error
		 **/
		if(lastOperator)
			throw new RuntimeException("Invalid Expression");
		
		/**
		 * This block pushes all the remaining operators to the postfix expression
		 * and throws error in case of brackets imbalance
		 **/
		while(!st.isEmpty())
		{
			char x = (char)st.pop();
			if(x == '(')
				throw new RuntimeException("Invalid Expression");
			exp.append(x);
			exp.append(" ");

		}
		return exp.toString();
	}
	
	
	/**
	* The function applies the operation that corresponds to the operator given
	*
	* @param x	the operator
	* @param a	first operand
	* @param b	second operand
	* @return the value
	*/
	public static double calculate(char x, double b, double a)
	{
		if(x == '+')
			return a + b;
		else if(x == '-')
			return a - b;
		else if(x == '*')
			return a * b;
		else if(x == '/')
		{
			if( b == 0)
				throw new RuntimeException("Cannot divide by 0!");
			else
				return a / b;
		}else
			throw new RuntimeException("Invalid Expression");
	}
		
	public int evaluate(String expression)
	{
		StackDS st = new StackDS();
		int n = expression.length();
		Scanner cin = new Scanner(System.in);
		
		for(int i = 0; i < expression.length();i++)
		{
			/**
			 * The block ignores white space character
			 */
			if(expression.charAt(i) == ' ' || expression.charAt(i) == '\t')
				continue;
			
			if(isAlphanum(expression.charAt(i)))
			{
				StringBuilder var = new StringBuilder();
				Boolean containsAlpha = false;
				/**
				 * This block extracts variable name
				 */
				while(i < n && isAlphanum(expression.charAt(i)))
				{
					if(isAlpha(expression.charAt(i)))
						containsAlpha = true;
					var.append(expression.charAt(i++));
				}
				
				/**
				 * The block throws error if it encounters invalid character
				 */
				if(i < expression.length() && !isAlphanum(expression.charAt(i)) && expression.charAt(i) != ' ' && expression.charAt(i) != '\t' && !isOperator(expression.charAt(i)))
					throw new RuntimeException("Invalid Expression");

				
				i--;
				/**
				 * This block throws error if the variable name starts with numbers
				 * for example "12ab" is invalid. 
				 * However, "ab12" is valid.
				 */

				if(isNum(var.charAt(0)) && containsAlpha)
					throw new RuntimeException("Invalid Expression");
				/**
				 * if the variable name is not a numeric value, the function prompts
				 * the user to enter the variable value
				 */
				else if(containsAlpha)
				{
					System.out.println("Enter the integer value of " + var.toString() + " : ");
					double x = cin.nextDouble();
					st.push(x);
				}
				else
					st.push((double)Integer.parseInt(var.toString()));
			}
			else
				st.push(calculate(expression.charAt(i), (double)st.pop(), (double)st.pop()));
		}
		/**
		 * This block throws error if more than operand is found.
		 */
		
		cin.close();
		if(st.size() != 1)
			throw new RuntimeException("Invalid Expression");
		else
			return ((Double)st.peek()).intValue();
		
	}
	
	/**
	 * The function ignores whitespaces and returns the index of the first
	 * character it encounters in the String s
	 * @param s 
	 * String given
	 * @param i 
	 * the current index
	 * @return the index of the first character after index i
	 **/
	public static int ignoreWhiteSpace(String s, int i)
	{
		while(i != s.length() && (s.charAt(i) == ' ' || s.charAt(i) == '\t'))
			i++;
		return i;
	}
	
	/**
	 * returns whether x is a valid operator or not
	 * @param x
	 * operator
	 * @return whether x is a valid operator or not
	 **/
	public static Boolean isOperator(char x)
	{
		return "+-*/()".indexOf(x) != -1;
	}
	
	/**
	 * The function extracts valid variables and numerals
	 * @param s
	 * expression
	 * @param i
	 * the index at which the parsing should start
	 * @return a pair of the parsed string and the index after parsing
	 */
	public static Pair extractWord(String s, int i)
	{
		StringBuilder var = new StringBuilder();
		Boolean containsAlpha = false;
		while(i != s.length() && isAlphanum(s.charAt(i)))
		{
			if(isAlpha(s.charAt(i)))
				containsAlpha = true;
			
			var.append(s.charAt(i));
			i++;
		}
		
		/**
		 * This block throws error if it encounters invalid character
		 */
		if(i < s.length() && !isAlphanum(s.charAt(i)) && s.charAt(i) != ' ' && s.charAt(i) != '\t' && !isOperator(s.charAt(i)))
				throw new RuntimeException("Invalid Expression");
				
		if(var.length() != 0)
		{
			/**
			 * This block throws error if the variable name starts with numbers
			 * for example "12ab" is invalid. 
			 * However, "ab12" is valid.
			 */
			if(isNum(var.charAt(0)) && containsAlpha)
				throw new RuntimeException("Invalid Expression");
		}
		return new Pair(var.toString(), i);
	}
	
	
	/**
	 * it indicates whether the character x is an alphabetic letter
	 * @param x
	 * character to be evaluated
	 * @return whether the character x is an alphabetic letter
	 */
	public static Boolean isAlpha(char x)
	{
		return (x >= 'a' && x <= 'z') || (x >= 'A' && x <= 'Z');
	}
	
	
	/**
	 * it indicates whether the character x is a numeric character
	 * @param x
	 * character to be evaluated
	 * @return whether the character x is a numeric character
	 */
	public static Boolean isNum(char x)
	{
		return x >= '0' && x <= '9';
	}
	

	/**
	 * it indicates whether the character x is an alpha numeric character
	 * @param x
	 * character to be evaluated
	 * @return whether the character x is an alpha numeric character
	 */
	public static Boolean isAlphanum(char x)
	{
		return isAlpha(x) || isNum(x);
	}
	
	
	/**
	 * This function assigns the precedence laws of mathematical expressions
	 * @param x
	 * operator
	 * @return priority
	 */
	public static int priority(char x)
	{
		if(x == '/' || x == '*')
			return 2;
		else if(x == '+' || x == '-')
			return 1;
		return 0;
	}
	
}
