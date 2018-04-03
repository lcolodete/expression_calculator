package calculator.test;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import calculator.ParseException;
import calculator.Parser;
import calculator.TokenManager;

public class TestParser {

	private Parser parser;
	
	@Before
	public void setUp() throws Exception {
		parser = new Parser();
	}

	@Test
	public void Parse_SimpleExpression_Success() throws ParseException {
		//Arrange
		
		final String sInput = "add (1, 2 ) ";
		final String sExpectedOutput = "add(1,2)";
		
		TokenManager st = new TokenManager(sInput);
		parser.setTokenManager(st);
		
		//Act
		
		parser.buildTree();
		parser.printTree();
		
		String printBuffer = parser.getPrintBuffer().toString();
		
		//Assert
		
		assertThat(printBuffer, is(sExpectedOutput));
	}
	
	@Test
	public void Parse_ValidSimpleExpressionWithNegativeNumber_Success() throws ParseException {
		//Arrange
		
		final String sInput = "add (1, -2 ) ";
		final String sExpectedOutput = "add(1,-2)";
		
		TokenManager st = new TokenManager(sInput);
		parser.setTokenManager(st);
		
		//Act
		
		parser.buildTree();
		parser.printTree();
		
		String printBuffer = parser.getPrintBuffer().toString();
		
		//Assert
		
		assertThat(printBuffer, is(sExpectedOutput));
	}
	
	@Test
	public void Parse_ValidExpressionWithOneNestedOperator_Success() throws ParseException {
		//Arrange
		
		final String sInput = "mul(add(2,2), 100)";
		final String sExpectedOutput = "mul(add(2,2),100)";
		
		TokenManager st = new TokenManager(sInput);
		parser.setTokenManager(st);
		
		//Act
		
		parser.buildTree();
		parser.printTree();
		
		String printBuffer = parser.getPrintBuffer().toString();
		
		//Assert
		
		assertThat(printBuffer, is(sExpectedOutput));
	}
	
	@Test
	public void Parse_ValidExpressionWithTwoNestedOperators_Success() throws ParseException {
		//Arrange
		
		final String sInput = "mul( add(2, 2), div(9, 3) )";
		final String sExpectedOutput = "mul(add(2,2),div(9,3))";
		
		TokenManager st = new TokenManager(sInput);
		parser.setTokenManager(st);
		
		//Act
		
		parser.buildTree();
		parser.printTree();
		
		String printBuffer = parser.getPrintBuffer().toString();
		
		//Assert
		
		assertThat(printBuffer, is(sExpectedOutput));
	}
	
	@Test
	public void Parse_ValidExpressionWithMultipleNestedOperators_Success() throws ParseException {
		//Arrange
		
		final String sInput = "mul(add(2,2), div(add(4, 5 ),   sub(6,3)))";
		final String sExpectedOutput = "mul(add(2,2),div(add(4,5),sub(6,3)))";
		
		TokenManager st = new TokenManager(sInput);
		parser.setTokenManager(st);
		
		//Act

		parser.buildTree();
		parser.printTree();
		
		String printBuffer = parser.getPrintBuffer().toString();
		
		//Assert
		
		assertThat(printBuffer, is(sExpectedOutput));
	}

	@Test(expected=ParseException.class)
	public void Parse_InvalidRootOperator_ThrowsException() throws ParseException {
		//Arrange
		
		final String sInput = "ad (1, 2 ) ";
		
		TokenManager st = new TokenManager(sInput);
		parser.setTokenManager(st);
		
		//Act
		parser.buildTree();
	}
	
	@Test
	public void Parse_LetExpression_Success() throws ParseException {
		//Arrange
		
		final String sInput = "let(a,5,add(a,a))";
		final String sExpectedOutput = "let(a,5,add(a,a))";
		
		TokenManager st = new TokenManager(sInput);
		parser.setTokenManager(st);
		
		//Act
		
		parser.buildTree();
		parser.printTree();
		
		String printBuffer = parser.getPrintBuffer().toString();
		
		//Assert
		
		assertThat(printBuffer, is(sExpectedOutput));
	}

}
