package calculator;

import java.util.Objects;

public class Calculator {

	public static void main(String[] args) {

		String sInput = null;
		
		if (args.length > 0)
			sInput = args[0];
		
		Calculator calc = new Calculator();
		try {
			calc.solveExpression(sInput);
		} catch (Exception e) {
			System.out.println("Error while solving expression : "+ e.getClass().getSimpleName() + " ("+e.getMessage()+")");
		}
	}

	public String solveExpression(String sInput) throws ParseException, ResolveException {
		
		Objects.requireNonNull(sInput, () -> "Error: Input is null");
		
		TokenManager st = new TokenManager(sInput);
		
		Lexer lexer = new Lexer();
		
		//Step 0 : Check valid tokens
		//fails if an invalid token is found (eg: #)
		boolean isValidInput = lexer.analyzeStep1(sInput);
		if (!isValidInput) {
			throw new ParseException(lexer.getErrorMessage());
		}
		
		//Step 1 : Check if operators,numbers,vars conform to the naming rules and appear in the right places 
		
		isValidInput = lexer.analyzeStep2(sInput);
		if (!isValidInput) {
			throw new ParseException(lexer.getErrorMessage());
		}
		
		//Step 2 : build parse tree
		Parser p = new Parser(st);
		p.setInput(sInput);
		Node root = p.buildTree();
		p.printTree();
//		System.out.println(p.getPrintBuffer().toString());

		//Step 3 : resolve expression

		Resolver resolver = new Resolver();
		String result = resolver.resolveExpression(root);
		System.out.println(result);
		return result;
	}
	
}
