package eg.edu.alexu.csd.datastructure.stack.cs5.Tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import eg.edu.alexu.csd.datastructure.stack.cs5.Classes.ExpCalc;

class ExpCalcTest {

	@Test
	void test() {
		ExpCalc e = new ExpCalc();
		
		assertEquals(e.evaluate(e.infixToPostfix("4-(	3* 2+7*9-  44 /(2*2))")), 4-(3*2+7*9-44/(2*2)));
		assertEquals(e.evaluate(e.infixToPostfix("1-+-  --   3- -4")), e.evaluate(e.infixToPostfix("1+3+4")));
		assertEquals(e.infixToPostfix("a1 +b - (cc2*34 +5/xyz)/4"), "a1 b + cc2 34 * 5 xyz / + 4 / - ");
		assertEquals(e.infixToPostfix("a*( b+c)*d"), "a b c + * d * ");
		assertEquals(e.infixToPostfix("a + ((12 - bc))"), "a 12 bc - + ");
		assertEquals("a", "a");
		
		//System.out.println(e.infixToPostfix("+"));
		
		// "1ab" is not valid
		assertThrows(RuntimeException.class, ()->{e.infixToPostfix("1ab + 23");});
		// +* is not valid
		assertThrows(RuntimeException.class, ()->{e.infixToPostfix("ab +* 23");});
		// invalid number of operators
		assertThrows(RuntimeException.class, ()->{e.infixToPostfix("ab + 23*");});
		assertThrows(RuntimeException.class, ()->{e.infixToPostfix("/ab + 23");});
		assertThrows(RuntimeException.class, ()->{e.infixToPostfix("ab ** 23");});
		assertThrows(RuntimeException.class, ()->{e.infixToPostfix("ab */ 23");});
		// no operator found
		assertThrows(RuntimeException.class, ()->{e.infixToPostfix("ab bc");});
		// parentheses are not balanced
		assertThrows(RuntimeException.class, ()->{e.infixToPostfix("a + (ab +bc");});
		assertThrows(RuntimeException.class, ()->{e.infixToPostfix("a + ab +bc)");});
		assertThrows(RuntimeException.class, ()->{e.infixToPostfix("a + (ab +bc))");});
		
		// operator with no operands
		assertThrows(RuntimeException.class, ()->{e.infixToPostfix("+");});
		
		
		
		assertThrows(RuntimeException.class, ()->{assertEquals(e.evaluate(e.infixToPostfix("4-(3**2+7*9-44/(2*2))")), 4-(3*2+7*9-44/(2*2)));});
		assertThrows(RuntimeException.class, ()->{e.evaluate("1 2 )");});
		assertThrows(RuntimeException.class, ()->{e.evaluate("1 2 + +");});
		assertThrows(RuntimeException.class, ()->{e.evaluate("1 2 3 +");});
		assertThrows(RuntimeException.class, ()->{e.evaluate("1 2 + 0 /");});
		
		// division by 0
		assertThrows(RuntimeException.class, ()->{e.evaluate("1 0 /");});
		
		// Edge test case
		assertEquals(e.evaluate("1"), 1);
		
		// Some tests
		assertEquals(e.evaluate("0 1 2 + /"), 0);
		assertEquals(e.evaluate("1 2 3 4 + * -"), -13);
		assertEquals(e.evaluate("4 1 2 + 3 - *"), 0);
	}

}
