package calculator.test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import calculator.Node;
import calculator.ParseException;
import calculator.Parser;
import calculator.ResolveException;
import calculator.Resolver;
import calculator.TokenManager;

public class TestResolver {

	private Resolver resolver;
	
	@Before
	public void setUp() throws Exception {
		resolver = new Resolver();
	}

	@Test
	public void ValidateExpression_ValidExpressionTwoArgs_ReturnsTrue() {
		//Arrange
		
		String sInput = "(100,20)";
		
		//Act
		
		boolean result = resolver.isValidArityTwoExpression(sInput);
		
		//Assert
		
		assertThat(result, is(true));
	}
	
	@Test
	public void ValidateExpression_ValidLetParenExpression_ReturnsTrue() {
		//Arrange
		
		String sInput = "(aa,20,4)";
		
		//Act
		
		boolean result = resolver.isValidLetExpression(sInput);
		
		//Assert
		
		assertThat(result, is(true));
	}
	
	@Test
	public void GetArguments_ValidExpressionTwoArgs_ReturnsCorrectArguments() {
		//Arrange
		
		String sInput = "(100,20)";
		List<String> expectedArgs = Arrays.asList("100","20");
		
		//Act
		
		List<String> args = resolver.getArguments(sInput);
		
		//Assert
		
		assertThat(args, is(expectedArgs));
	}
	
	@Test
	public void GetArguments_ValidExpressionThreeArgs_ReturnsCorrectArguments() {
		//Arrange
		
		String sInput = "(100,20,4)";
		List<String> expectedArgs = Arrays.asList("100", "20", "4");
		
		//Act
		
		List<String> args = resolver.getArguments(sInput);
		
		//Assert
		
		assertThat(args, is(expectedArgs));
	}
	
	@Test
	public void ValidateExpression_InvalidExpressionMissingLeftParen_ReturnsFalse() {
		//Arrange
		
		String sInput = "100,20)";
		
		//Act
		
		boolean result = resolver.isValidArityTwoExpression(sInput);
		
		//Assert
		
		assertThat(result, is(false));
		
	}
	
	@Test
	public void ValidateExpression_InvalidExpressionMissingRightParen_ReturnsFalse() {
		//Arrange
		
		String sInput = "(100,20";
		
		//Act
		
		boolean result = resolver.isValidArityTwoExpression(sInput);
		
		//Assert
		
		assertThat(result, is(false));
		
	}
	
	@Test
	public void ValidateExpression_InvalidExpressionMissingLastArg_ReturnsFalse() {
		//Arrange
		
		String sInput = "(100,)";
		
		//Act
		
		boolean result = resolver.isValidArityTwoExpression(sInput);
		
		//Assert
		
		assertThat(result, is(false));
		
	}
	
	@Test
	public void ValidateExpression_InvalidExpressionMissingLastArg2_ReturnsFalse() {
		//Arrange
		
		String sInput = "(100,10,)";
		
		//Act
		
		boolean result = resolver.isValidArityTwoExpression(sInput);
		
		//Assert
		
		assertThat(result, is(false));
		
	}
	
	@Test
	public void ValidateExpression_ValidExpressionWithOneVar_ReturnsTrue() {
		//Arrange
		
		String sInput = "(a,20)";
		
		//Act
		
		boolean result = resolver.isValidArityTwoExpression(sInput);
		
		//Assert
		
		assertThat(result, is(true));
		
	}
	
	@Test
	public void ValidateExpression_ValidExpressionWithTwoVars_ReturnsTrue() {
		//Arrange
		
		String sInput = "(a,b)";
		
		//Act
		
		boolean result = resolver.isValidArityTwoExpression(sInput);
		
		//Assert
		
		assertThat(result, is(true));
		
	}
	
	@Test
	public void GetArguments_ValidExpressionWithTwoVars_ReturnsCorrectArguments() {
		//Arrange
		
		String sInput = "(a,b)";
		List<String> expectedArgs = Arrays.asList("a","b");
		
		//Act
		
		List<String> args = resolver.getArguments(sInput);
		
		//Assert
		
		assertThat(args, is(expectedArgs));
	}

	
	@Test
	public void ValidateExpression_InvalidExpressionTwoVarsMissingLeftParen_ReturnsFalse() {
		//Arrange
		
		String sInput = "a,b)";
		
		//Act
		
		boolean result = resolver.isValidArityTwoExpression(sInput);
		
		//Assert
		
		assertThat(result, is(false));
	}
	
	@Test
	public void ValidateExpression_InvalidExpressionTwoVarsMissingRightParen_ReturnsFalse() {
		//Arrange
		
		String sInput = "(a,b";
		
		//Act
		
		boolean result = resolver.isValidArityTwoExpression(sInput);
		
		//Assert
		
		assertThat(result, is(false));
	}
	
	@Test
	public void ValidateExpression_InvalidExpressionOneArg_ReturnsFalse() {
		//Arrange
		
		String sInput = "(1)";
		
		//Act
		
		boolean result = resolver.isValidArityTwoExpression(sInput);
		
		//Assert
		
		assertThat(result, is(false));
	}
	
	@Test
	public void ValidateExpression_InvalidExpressionFourArgs_ReturnsFalse() {
		//Arrange
		
		String sInput = "(1,2,3,4)";
		
		//Act
		
		boolean result = resolver.isValidArityTwoExpression(sInput);
		
		//Assert
		
		assertThat(result, is(false));
	}
	
	@Test
	public void ResolveExpression_LetExample1_ReturnsResult() throws ParseException, ResolveException {
		//Arrange
		
		String sInput = "let(a,5,add(a,a))";
		String expectedResult = "10";
		
		TokenManager st = new TokenManager(sInput);
		Parser parser = new Parser(); 
		parser.setTokenManager(st);
		Node root = parser.buildTree();
		
		//Act
		String result = resolver.resolveExpression(root);
		
		//Assert
		
		assertThat(result, is(expectedResult));
	}
	
	@Test
	public void ResolveExpression_LetExample2_ReturnsResult() throws ParseException, ResolveException {
		//Arrange
		
		String sInput = "let(a,5, let(b, mul(a,10), add(b,a)) )";
		String expectedResult = "55";
		
		TokenManager st = new TokenManager(sInput);
		Parser parser = new Parser(); 
		parser.setTokenManager(st);
		Node root = parser.buildTree();
		
		//Act
		String result = resolver.resolveExpression(root);
		
		//Assert
		
		assertThat(result, is(expectedResult));
	}
	
	@Test
	public void ResolveExpression_LetExample3_ReturnsResult() throws ParseException, ResolveException {
		//Arrange
		
		String sInput = "let(a, let(b, 10, add(b, b)), let(b, 20, add(a, b)))";
		String expectedResult = "40";
		
		TokenManager st = new TokenManager(sInput);
		Parser parser = new Parser(); 
		parser.setTokenManager(st);
		Node root = parser.buildTree();
		
		//Act
		String result = resolver.resolveExpression(root);
		
		//Assert
		
		assertThat(result, is(expectedResult));
	}
	
	@Test
	public void ResolveExpression_AddTwoPositiveNumbers_ReturnsSum() throws ParseException, ResolveException {
		//Arrange
		
		String sInput = "add(1,2)";
		String expectedResult = "3";
		
		TokenManager st = new TokenManager(sInput);
		Parser parser = new Parser(); 
		parser.setTokenManager(st);
		Node root = parser.buildTree();
		
		//Act
		String result = resolver.resolveExpression(root);
		
		//Assert
		
		assertThat(result, is(expectedResult));
	}
	
	@Test
	public void ResolveExpression_SubTwoPositiveNumbers_ReturnsResult() throws ParseException, ResolveException {
		//Arrange
		
		String sInput = "sub(2,1)";
		String expectedResult = "1";
		
		TokenManager st = new TokenManager(sInput);
		Parser parser = new Parser(); 
		parser.setTokenManager(st);
		Node root = parser.buildTree();
		
		//Act
		String result = resolver.resolveExpression(root);
		
		//Assert
		
		assertThat(result, is(expectedResult));
	}
	
	@Test
	public void ResolveExpression_SubTwoPositiveNumbers_ReturnsNegativeResult() throws ParseException, ResolveException {
		//Arrange
		
		String sInput = "sub(1,2)";
		String expectedResult = "-1";
		
		TokenManager st = new TokenManager(sInput);
		Parser parser = new Parser(); 
		parser.setTokenManager(st);
		Node root = parser.buildTree();
		
		//Act
		String result = resolver.resolveExpression(root);
		
		//Assert
		
		assertThat(result, is(expectedResult));
	}
	
	@Test
	public void ResolveExpression_SubTwoNumbersRightNegative_ReturnsResult() throws ParseException, ResolveException {
		//Arrange
		
		String sInput = "sub(111,-30000)";
		String expectedResult = "30111";
		
		TokenManager st = new TokenManager(sInput);
		Parser parser = new Parser(); 
		parser.setTokenManager(st);
		Node root = parser.buildTree();
		
		//Act
		String result = resolver.resolveExpression(root);
		
		//Assert
		
		assertThat(result, is(expectedResult));
	}
	
	@Test
	public void ResolveExpression_MulTwoPositiveNumbers_ReturnsProduct() throws ParseException, ResolveException {
		//Arrange
		
		String sInput = "mul(3,2)";
		String expectedResult = "6";
		
		TokenManager st = new TokenManager(sInput);
		Parser parser = new Parser(); 
		parser.setTokenManager(st);
		Node root = parser.buildTree();
		
		//Act
		String result = resolver.resolveExpression(root);
		
		//Assert
		
		assertThat(result, is(expectedResult));
	}
	
	@Test
	public void ResolveExpression_DivTwoPositiveNumbers_ReturnsDivision() throws ParseException, ResolveException {
		//Arrange
		
		String sInput = "div(10,2)";
		String expectedResult = "5";
		
		TokenManager st = new TokenManager(sInput);
		Parser parser = new Parser(); 
		parser.setTokenManager(st);
		Node root = parser.buildTree();
		
		//Act
		String result = resolver.resolveExpression(root);
		
		//Assert
		
		assertThat(result, is(expectedResult));
	}
	
	@Test
	public void ResolveExpression_DivTwoPositiveNumbers_ReturnsRoundedResult() throws ParseException, ResolveException {
		//Arrange
		
		String sInput = "div(5,2)";
		String expectedResult = "2";
		
		TokenManager st = new TokenManager(sInput);
		Parser parser = new Parser(); 
		parser.setTokenManager(st);
		Node root = parser.buildTree();
		
		//Act
		String result = resolver.resolveExpression(root);
		
		//Assert
		
		assertThat(result, is(expectedResult));
	}
	
	@Test(expected=ResolveException.class)
	public void ResolveExpression_DivByZero_ThrowsException() throws ParseException, ResolveException {
		//Arrange
		
		String sInput = "div(3,0)";
		
		TokenManager st = new TokenManager(sInput);
		Parser parser = new Parser(); 
		parser.setTokenManager(st);
		Node root = parser.buildTree();
		
		//Act
		String result = resolver.resolveExpression(root);
	}
	
	@Test
	public void ResolveExpression_AddWithNegativeNumber_ReturnsSum() throws ParseException, ResolveException {
		//Arrange
		
		String sInput = "add(1,-2)";
		String expectedResult = "-1";
		
		TokenManager st = new TokenManager(sInput);
		Parser parser = new Parser(); 
		parser.setTokenManager(st);
		Node root = parser.buildTree();
		
		//Act
		String result = resolver.resolveExpression(root);
		
		//Assert
		
		assertThat(result, is(expectedResult));
	}
	
	@Test
	public void ResolveExpression_ValidExpressionWithOneNestedOperator_ReturnsResult() throws ParseException, ResolveException {
		//Arrange
		
		String sInput = "mul(add(2,2), 100)";
		String expectedResult = "400";
		
		TokenManager st = new TokenManager(sInput);
		Parser parser = new Parser(); 
		parser.setTokenManager(st);
		Node root = parser.buildTree();
		
		//Act
		String result = resolver.resolveExpression(root);
		
		//Assert
		
		assertThat(result, is(expectedResult));
	}
	
	@Test
	public void ResolveExpression_ValidExpressionWithOneNestedOperator2_ReturnsResult() throws ParseException, ResolveException {
		//Arrange
		
		String sInput = "add(1, mul(2,3))";
		String expectedResult = "7";
		
		TokenManager st = new TokenManager(sInput);
		Parser parser = new Parser(); 
		parser.setTokenManager(st);
		Node root = parser.buildTree();
		
		//Act
		String result = resolver.resolveExpression(root);
		
		//Assert
		
		assertThat(result, is(expectedResult));
	}
	
	@Test
	public void ResolveExpression_ValidExpressionWithTwoNestedOperators_ReturnsResult() throws ParseException, ResolveException {
		//Arrange
		
		String sInput = "mul(add(2,2), div(9,3))";
		String expectedResult = "12";
		
		TokenManager st = new TokenManager(sInput);
		Parser parser = new Parser(); 
		parser.setTokenManager(st);
		Node root = parser.buildTree();
		
		//Act
		String result = resolver.resolveExpression(root);
		
		//Assert
		
		assertThat(result, is(expectedResult));
	}
	
	@Test
	public void ResolveExpression_ValidExpressionWithMultipleNestedOperators_ReturnsResult() throws ParseException, ResolveException {
		//Arrange
		
		String sInput = "mul(add(2,2), div(add(4, 5 ), sub(6,3)) )";
		String expectedResult = "12";
		
		TokenManager st = new TokenManager(sInput);
		Parser parser = new Parser(); 
		parser.setTokenManager(st);
		Node root = parser.buildTree();
		
		//Act
		String result = resolver.resolveExpression(root);
		
		//Assert
		
		assertThat(result, is(expectedResult));
	}

}
