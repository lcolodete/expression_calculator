package calculator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TokenManager {

	public static final String ADD = "add";
	public static final String SUB = "sub";
	public static final String DIV = "div";
	public static final String MUL = "mul";
	public static final String LET = "let";
	
	
	public static final String REGEX_OPERATOR = String.format("(%s|%s|%s|%s|%s)", ADD, SUB, DIV, MUL, LET);
	
	//cannot be equal to "add", "sub", "mul", "div", or "let"
	public static final String REGEX_VAR = "([A-Za-z_]([A-Za-z_\\d])*)";
	
	public static final String REGEX_NUMBER = "\\-[1-9](\\d)*|(\\d)+";
	
	public static final String REGEX_TOKENS = REGEX_OPERATOR + "|" + REGEX_VAR + "|" + REGEX_NUMBER + "|" + "\\(|\\)|,"; 

	private Pattern pattern;
	private Matcher matcher;
	
	public TokenManager(String input) {
		pattern = Pattern.compile(TokenManager.REGEX_TOKENS);
		matcher = pattern.matcher(input);
	}
	
	public boolean hasMoreTokens() {
		return matcher.find();
	}
	
	public String nextToken() {
	    return matcher.group();
	}

	public void reset() {
		matcher.reset();
	}
	
	public String getInfo() {
		return String.format("%d", matcher.start());
	}
	
	public static boolean isLetOperator(String operator) {
		return operator.matches(LET);
	}
	
	public static boolean isOperator(String operator) {
		return operator.matches(REGEX_OPERATOR);
	}
	
	public static boolean isVar(String var) {
		return var.matches(REGEX_VAR) && !var.matches(REGEX_OPERATOR); 
	}
	
	public static boolean isNumber(String number) {
		return number.matches(REGEX_NUMBER);
	}
}
