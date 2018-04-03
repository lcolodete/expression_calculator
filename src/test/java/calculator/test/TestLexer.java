package calculator.test;


import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import calculator.Lexer;


public class TestLexer {

	private Lexer lexer;
	
	@Before
	public void setUp() {
		lexer = new Lexer();
	}

	@Test
	public void Analyze_ValidSimpleExpression_ReturnsTrue() {
		//Arrange
		
		String sInput = "add(1,2)";
		
		//Act
		
		boolean result = lexer.analyzeStep2(sInput);
		
		//Assert
		
		assertThat(result, is(true));
		System.out.println(lexer.getErrorMessage());
	}
	
	@Test
	public void Analyze_ValidSimpleExpressionWithNegativeNumber_ReturnsTrue() {
		//Arrange
		
		String sInput = "add(1,-2)";
		
		//Act
		
		boolean result = lexer.analyzeStep2(sInput);
		
		//Assert
		
		assertThat(result, is(true));
		System.out.println(lexer.getErrorMessage());
	}

	
	@Test
	public void Analyze_ValidExpressionWithOneNestedOperator_ReturnsTrue() {
		//Arrange
		
		String sInput = "mul(add(2,2), 100)";
		
		//Act
		
		boolean result = lexer.analyzeStep2(sInput);
		
		//Assert
		
		assertThat(result, is(true));
		System.out.println(lexer.getErrorMessage());
	}
	
	@Test
	public void Analyze_ValidExpressionWithTwoNestedOperators_ReturnsTrue() {
		//Arrange
		
		String sInput = "mul(add(2,2), div(9,3))";
		
		//Act
		
		boolean result = lexer.analyzeStep2(sInput);
		
		//Assert
		
		assertThat(result, is(true));
		System.out.println(lexer.getErrorMessage());
	}
	
	@Test
	public void Analyze_ValidExpressionWithMultipleNestedOperators_ReturnsTrue() {
		//Arrange
		
		String sInput = "mul(add(2,2), div(add(4, 5 ),   sub(6,3)))";
		
		//Act
		
		boolean result = lexer.analyzeStep2(sInput);
		
		//Assert
		
		assertThat(result, is(true));
		System.out.println(lexer.getErrorMessage());
	}
	
	@Test
	public void Analyze_InvalidRootOperator_ReturnsFalse() {
		//Arrange
		
		String sInput = "ad   ( -1 , mul(2, 3) ) ";
		
		//Act
		
		boolean result = lexer.analyzeStep2(sInput);
		
		//Assert
		
		assertThat(result, is(false));
		System.out.println(lexer.getErrorMessage());
	}
	
	@Test
	public void Analyze_InvalidNestedOperator_ReturnsFalse() {
		//Arrange
		
		String sInput = "add   ( -1 , ml(2, 3) ) ";
		
		//Act
		
		boolean result = lexer.analyzeStep2(sInput);
		
		//Assert
		
		assertThat(result, is(false));
		System.out.println(lexer.getErrorMessage());
	}
	
	@Test
	public void Analyze_InvalidMultipleNestedOperators_ReturnsFalse() {
		//Arrange
		
		String sInput = "mul(add(2,2), div(ad(4, 5 ),   sub(6,3)))   ";
		
		//Act
		
		boolean result = lexer.analyzeStep2(sInput);
		
		//Assert
		
		assertThat(result, is(false));
		System.out.println(lexer.getErrorMessage());
	}

	@Test
	public void Analyze_InvalidExpressionWithNegativeNumber_ReturnsFalse() {
		//Arrange
		
		String sInput = "add(1,-0)";
		
		//Act
		
		boolean result = lexer.analyzeStep1(sInput);
		
		//Assert
		
		assertThat(result, is(false));
		System.out.println(lexer.getErrorMessage());
	}

	@Test
	public void Analyze_NumberWithSpace_ReturnsFalse() {
		//Arrange
		
		String sInput = "add(1 0,2)";
		
		//Act
		
		boolean result = lexer.analyzeStep2(sInput);
		
		//Assert
		
		assertThat(result, is(false));
		System.out.println(lexer.getErrorMessage());
	}
	
	@Test
	public void Analyze_VarWithSpace_ReturnsFalse() {
		//Arrange
		
		String sInput = "add(dog, ca t)";
		
		//Act
		
		boolean result = lexer.analyzeStep2(sInput);
		
		//Assert
		
		assertThat(result, is(false));
		System.out.println(lexer.getErrorMessage());
	}
	
	@Test
	public void Analyze_InvalidVar_ReturnsFalse() {
		//Arrange
		
		String sInput = "add(1dog, 2)";
		
		//Act
		
		boolean result = lexer.analyzeStep2(sInput);
		
		//Assert
		
		assertThat(result, is(false));
		System.out.println(lexer.getErrorMessage());
	}
	
	@Test
	public void Analyze_OperatorWithSpace_ReturnsFalse() {
		//Arrange
		
		String sInput = "ad d(1, 2)";
		
		//Act
		
		boolean result = lexer.analyzeStep2(sInput);
		
		//Assert
		
		assertThat(result, is(false));
		System.out.println(lexer.getErrorMessage());
	}
	
	@Test
	public void Analyze_UnknownToken_ReturnsFalse() {
		//Arrange
		
		String sInput = "add #(1, 2)";
		
		//Act
		
		boolean result = lexer.analyzeStep1(sInput);
		
		//Assert
		
		assertThat(result, is(false));
		System.out.println(lexer.getErrorMessage());
	}
	
	@Test
	public void Analyze_UnknownToken_ReturnsFalse2() {
		//Arrange
		
		String sInput = "add (#a, 2)";
		
		//Act
		
		boolean result = lexer.analyzeStep1(sInput);
		
		//Assert
		
		assertThat(result, is(false));
		System.out.println(lexer.getErrorMessage());
	}
	
	@Test
	public void Analyze_LetValidExpression_ReturnsTrue() {
		//Arrange
		
		String sInput = "let(a,5,add(a,a))";
		
		//Act
		
		boolean result = lexer.analyzeStep2(sInput);
		
		//Assert
		
		assertThat(result, is(true));
		System.out.println(lexer.getErrorMessage());
	}

}
