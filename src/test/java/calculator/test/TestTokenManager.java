package calculator.test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import calculator.TokenManager;

public class TestTokenManager {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void ValidateOperator_AddOperator_ReturnsTrue() {
		//Arrange
		
		String sInput = "add";
		
		//Act
		
		boolean result = TokenManager.isOperator(sInput); 
		
		//Assert
		
		assertThat(result, is(true));
	}
	
	@Test
	public void ValidateOperator_SubOperator_ReturnsTrue() {
		//Arrange
		
		String sInput = "sub";
		
		//Act
		
		boolean result = TokenManager.isOperator(sInput); 
		
		//Assert
		
		assertThat(result, is(true));
	}
	
	@Test
	public void ValidateOperator_MulOperator_ReturnsTrue() {
		//Arrange
		
		String sInput = "mul";
		
		//Act
		
		boolean result = TokenManager.isOperator(sInput); 
		
		//Assert
		
		assertThat(result, is(true));
	}
	
	@Test
	public void ValidateOperator_DivOperator_ReturnsTrue() {
		//Arrange
		
		String sInput = "div";
		
		//Act
		
		boolean result = TokenManager.isOperator(sInput); 
		
		//Assert
		
		assertThat(result, is(true));
	}
	
	@Test
	public void ValidateOperator_LetOperator_ReturnsTrue() {
		//Arrange
		
		String sInput = "let";
		
		//Act
		
		boolean result = TokenManager.isOperator(sInput); 
		
		//Assert
		
		assertThat(result, is(true));
	}
	
	@Test
	public void ValidateOperator_UnknownOperator_ReturnsFalse() {
		//Arrange
		
		String sInput = "leat";
		
		//Act
		
		boolean result = TokenManager.isOperator(sInput); 
		
		//Assert
		
		assertThat(result, is(false));
	}
	
	@Test
	public void ValidateNumber_Zero_ReturnsTrue() {
		//Arrange
		
		String sInput = "0";
		
		//Act
		
		boolean result = TokenManager.isNumber(sInput); 
		
		//Assert
		
		assertThat(result, is(true));
	}
	
	@Test
	public void ValidateNumber_PositiveNumber_ReturnsTrue() {
		//Arrange
		
		String sInput = "10";
		
		//Act
		
		boolean result = TokenManager.isNumber(sInput); 
		
		//Assert
		
		assertThat(result, is(true));
	}
	
	@Test
	public void ValidateNumber_NegativeNumber_ReturnsTrue() {
		//Arrange
		
		String sInput = "-10";
		
		//Act
		
		boolean result = TokenManager.isNumber(sInput); 
		
		//Assert
		
		assertThat(result, is(true));
	}
	
	@Test
	public void ValidateVar_ValidVar_ReturnsTrue() {
		//Arrange
		
		String sInput = "adda";
		
		//Act
		
		boolean result = TokenManager.isVar(sInput); 
		
		//Assert
		
		assertThat(result, is(true));
	}
	
	@Test
	public void ValidateVar_BeginsWithUnderscore_ReturnsTrue() {
		//Arrange
		
		String sInput = "_adda";
		
		//Act
		
		boolean result = TokenManager.isVar(sInput); 
		
		//Assert
		
		assertThat(result, is(true));
	}
	
	@Test
	public void ValidateVar_ContainsUnderscoreAndDigit_ReturnsTrue() {
		//Arrange
		
		String sInput = "_ad_d1a";
		
		//Act
		
		boolean result = TokenManager.isVar(sInput); 
		
		//Assert
		
		assertThat(result, is(true));
	}
	
	@Test
	public void ValidateVar_ContainsInvalidCharacter_ReturnsFalse() {
		//Arrange
		
		String sInput = "ad-da";
		
		//Act
		
		boolean result = TokenManager.isVar(sInput); 
		
		//Assert
		
		assertThat(result, is(false));
	}
	
	@Test
	public void ValidateVar_InvalidFirstCharacter_ReturnsFalse() {
		//Arrange
		
		String sInput = "$adda";
		
		//Act
		
		boolean result = TokenManager.isVar(sInput); 
		
		//Assert
		
		assertThat(result, is(false));
	}
	
	@Test
	public void ValidateVar_BeginsWithNumber_ReturnsFalse() {
		//Arrange
		
		String sInput = "1adda";
		
		//Act
		
		boolean result = TokenManager.isVar(sInput); 
		
		//Assert
		
		assertThat(result, is(false));
	}
	
	@Test
	public void ValidateVar_EqualsAddKeyword_ReturnsFalse() {
		//Arrange
		
		String sInput = "add";
		
		//Act
		
		boolean result = TokenManager.isVar(sInput); 
		
		//Assert
		
		assertThat(result, is(false));
	}
	
	@Test
	public void ValidateVar_EqualsSubKeyword_ReturnsFalse() {
		//Arrange
		
		String sInput = "sub";
		
		//Act
		
		boolean result = TokenManager.isVar(sInput); 
		
		//Assert
		
		assertThat(result, is(false));
	}
	
	@Test
	public void ValidateVar_EqualsMulKeyword_ReturnsFalse() {
		//Arrange
		
		String sInput = "mul";
		
		//Act
		
		boolean result = TokenManager.isVar(sInput); 
		
		//Assert
		
		assertThat(result, is(false));
	}
	
	@Test
	public void ValidateVar_EqualsDivKeyword_ReturnsFalse() {
		//Arrange
		
		String sInput = "div";
		
		//Act
		
		boolean result = TokenManager.isVar(sInput); 
		
		//Assert
		
		assertThat(result, is(false));
	}

}
