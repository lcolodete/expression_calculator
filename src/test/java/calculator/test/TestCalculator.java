package calculator.test;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import calculator.Calculator;
import calculator.ParseException;
import calculator.ResolveException;

public class TestCalculator {

	private Calculator calc;
	
	@Before
	public void setUp() throws Exception {
		calc = new Calculator();
	}

	@Test
	public void SolveExpression_Add_ReturnsSum() throws ParseException, ResolveException {
		//Arrange
		final String expression = "add(1, 2)";
		final String expectedResult = "3";
		
		//Act
		
		String result = calc.solveExpression(expression);
		
		//Assert
		assertThat(result, is(expectedResult));
	}
	
	@Test
	public void SolveExpression_Sub_ReturnsResult() throws ParseException, ResolveException {
		//Arrange
		final String expression = "sub(111, -30000)";
		final String expectedResult = "30111";
		
		//Act
		
		String result = calc.solveExpression(expression);
		
		//Assert
		assertThat(result, is(expectedResult));
	}
	
	@Test
	public void SolveExpression_Div_ReturnsDivision() throws ParseException, ResolveException {
		//Arrange
		final String expression = "div(5, 2)";
		final String expectedResult = "2";
		
		//Act
		
		String result = calc.solveExpression(expression);
		
		//Assert
		assertThat(result, is(expectedResult));
	}
	
	@Test
	public void SolveExpression_AddWithNestedMultiplication_ReturnsSum() throws ParseException, ResolveException {
		//Arrange
		final String expression = "add(1, mul(2   , 3  ) )    ";
		final String expectedResult = "7";
		
		//Act
		
		String result = calc.solveExpression(expression);
		
		//Assert
		assertThat(result, is(expectedResult));
		
	}
	
	@Test
	public void SolveExpression_Mul_ReturnsProduct() throws ParseException, ResolveException {
		//Arrange
		final String expression = "mul(add(2, 2), div(9, 3))";
		final String expectedResult = "12";
		
		//Act
		
		String result = calc.solveExpression(expression);
		
		//Assert
		assertThat(result, is(expectedResult));
		
	}
	
	@Test
	public void SolveExpression_LetWithNestedAdd_ReturnsResult() throws ParseException, ResolveException {
		//Arrange
		final String expression = "let(a, 5, add(a, a))";
		final String expectedResult = "10";
		
		//Act
		
		String result = calc.solveExpression(expression);
		
		//Assert
		assertThat(result, is(expectedResult));
		
	}
	
	@Test
	public void SolveExpression_LetWithNestedLet_ReturnsResult() throws ParseException, ResolveException {
		//Arrange
		final String expression = "let(a, 5, let(b, mul(a, 10), add(b, a)))";
		final String expectedResult = "55";
		
		//Act
		
		String result = calc.solveExpression(expression);
		
		//Assert
		assertThat(result, is(expectedResult));
		
	}
	
	@Test
	public void SolveExpression_LetWithTwoNestedAdds_ReturnsResult() throws ParseException, ResolveException {
		//Arrange
		final String expression = "let(a, let(b, 10, add(b, b)), let(b, 20, add(a  , b     ) )   )   ";
		final String expectedResult = "40";
		
		//Act
		
		String result = calc.solveExpression(expression);
		
		//Assert
		assertThat(result, is(expectedResult));

	}

	@Test(expected=ParseException.class)
	public void SolveExpression_EmptyExpression_ThrowsException() throws ParseException, ResolveException {
		//Arrange
		final String expression = "";
		
		//Act
		
		String result = calc.solveExpression(expression);
	}
	
	@Test(expected=ParseException.class)
	public void SolveExpression_InvalidExpression_ThrowsException() throws ParseException, ResolveException {
		//Arrange
		final String expression = "su (_a,b";
		
		//Act
		
		try {
			calc.solveExpression(expression);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			throw e;
		}
	}
	
	@Test(expected=ParseException.class)
	public void SolveExpression_InvalidExpressionWhenVarEqualsKeyword_ThrowsException() throws ParseException, ResolveException {
		//Arrange
		final String expression = "let(sub, 5, add(sub, sub))";
		
		//Act
		
		try {
			calc.solveExpression(expression);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			throw e;
		}
	}
	
	@Test(expected=ParseException.class)
	public void SolveExpression_InvalidExpressionMissingRightParen_ThrowsException() throws ParseException, ResolveException {
		//Arrange
		final String expression = "add(1,2";
		
		//Act
		
		try {
			calc.solveExpression(expression);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			throw e;
		}
	}
	
	@Test(expected=ParseException.class)
	public void SolveExpression_InvalidExpressionMissingLeftParen_ThrowsException() throws ParseException, ResolveException {
		//Arrange
		final String expression = "add 1,2)";
		
		//Act
		
		try {
			calc.solveExpression(expression);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			throw e;
		}
	}
	
	@Test(expected=ResolveException.class)
	public void SolveExpression_InvalidExpressionMissingComma_ThrowsException() throws ParseException, ResolveException {
		//Arrange
		final String expression = "add(12)";
		
		//Act
		
		try {
			calc.solveExpression(expression);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			throw e;
		}
	}
	
	@Test(expected=ResolveException.class)
	public void SolveExpression_InvalidExpressionUnresolvedVariable_ThrowsException() throws ParseException, ResolveException {
		//Arrange
		final String expression = "let(b, 20, add(a, b))";
		
		//Act
		
		try {
			calc.solveExpression(expression);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			throw e;
		}
	}
	
	@Test(expected=ResolveException.class)
	public void SolveExpression_InvalidLetExpressionFirstArgNotVar_ThrowsException() throws ParseException, ResolveException {
		//Arrange
		final String expression = "let(2, 2, 3)";
		
		//Act
		
		try {
			calc.solveExpression(expression);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			throw e;
		}
	}

	@Test
	public void SolveExpression_MulPositiveNumbersGreaterThanSpecRange_ReturnsProduct() throws ParseException, ResolveException {
		//Arrange
		final String expression = "mul(32767,32768)";
		final String expectedResult = "1073709056";
		
		//Act
		
		String result = calc.solveExpression(expression);
		
		//Assert
		assertThat(result, is(expectedResult));
		
	}
	
	@Test(expected=ResolveException.class)
	public void SolveExpression_DivideByZero_ThrowsException() throws ParseException, ResolveException {
		//Arrange
		final String expression = "div(32767,0)";
		
		//Act
		
		try {
			calc.solveExpression(expression);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			throw e;
		}
	}
	
	@Test(expected=ResolveException.class)
	public void SolveExpression_InvalidLetExpressionVarReassignedInValueExpression_ThrowsException() throws ParseException, ResolveException {
		//Arrange
		final String expression = "let(a, let(a, 10, add(a, a)), let(b, 20, add(a, b)))";
		
		//Act
		
		try {
			calc.solveExpression(expression);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			throw e;
		}
	}
	
	@Test(expected=ResolveException.class)
	public void SolveExpression_InvalidLetExpressionVarReassignedInResultExpression_ThrowsException() throws ParseException, ResolveException {
		//Arrange
		final String expression = "let(a, let(b, 10, add(b, b)), let(a, 20, add(a, b)))";
		
		//Act
		
		try {
			calc.solveExpression(expression);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			throw e;
		}
	}
	
	@Test(expected=ResolveException.class)
	public void SolveExpression_InvalidLetExpressionVarReassignedInNestedExpression_ThrowsException() throws ParseException, ResolveException {
		//Arrange
		final String expression = "let(a, let(b, let(b, 10, add(b, b)), add(b, b)), let(b, 20, add(a, b)))";
		
		//Act
		
		try {
			calc.solveExpression(expression);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			throw e;
		}
	}

}
