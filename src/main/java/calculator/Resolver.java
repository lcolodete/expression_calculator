package calculator;

import static calculator.TokenManager.ADD;
import static calculator.TokenManager.DIV;
import static calculator.TokenManager.MUL;
import static calculator.TokenManager.REGEX_NUMBER;
import static calculator.TokenManager.REGEX_VAR;
import static calculator.TokenManager.SUB;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Resolver {

	private static final String REGEX_ARG = REGEX_NUMBER+"|"+REGEX_VAR;
	
	private static final String REGEX_PAREN_EXP_ARITY_TWO = "\\(("+REGEX_ARG+")(,("+REGEX_ARG+")){1}\\)";
	
	private static final String REGEX_PAREN_EXP_LET = "\\(("+REGEX_VAR+")(,("+REGEX_ARG+")){2}\\)";

	
	public String resolveExpression(Node root) throws ResolveException {
		if (root instanceof LetNode)
			return resolveLetNode((LetNode)root, null);
		return resolveNode(root, null);
	}
	
	private String resolveNode(Node node, Map<String,String> parentContext) throws ResolveException {
		List<Node> children = node.getChildren();
		
		if (!children.isEmpty()) {
			StringBuilder sb = new StringBuilder();
			for (Node childNode : children) {
				String childResult = null;
				if (childNode instanceof LetNode)
					childResult = resolveLetNode((LetNode)childNode, parentContext);
				else
					childResult = resolveNode(childNode, parentContext);
				sb.append( childResult );
			}
			
			String expression = sb.toString();
			
			if (isValidArityTwoExpression(expression)) {
				
				List<String> args = getArguments(expression);
				
				int arg0_int = parseArgument(args.get(0), parentContext);
				int arg1_int = parseArgument(args.get(1), parentContext);
				
				int result = 0;
				
				String operator = node.getData(); 
				
				switch (operator) {
					case ADD:
						result = arg0_int + arg1_int;
						break;
					case MUL:
						result = arg0_int * arg1_int;
						break;
					case DIV:
						try {
							result = arg0_int / arg1_int;
						} catch (Exception e) {
							throw new ResolveException(e.getMessage());
						}
						break;
					case SUB:
							result = arg0_int - arg1_int;
						break;
					default:
						//error
						String errorMsg = "Invalid operator : "+operator;
						throw new ResolveException(errorMsg);
				}
				
				return String.valueOf(result);
				
			} else {
				//error
				String errorMsg = "Invalid expression : "+expression;
				throw new ResolveException(errorMsg);
			}
			
		} else {
			//leaf node
			return node.getData();
		}
	}

	private int parseArgument(String arg, Map<String, String> context) throws ResolveException {
		int parseResult = Integer.MIN_VALUE;
		String valueToParse = arg;
		//if arg is a var, it must be resolved
		if (TokenManager.isVar(arg)) {
			valueToParse = context.get(arg);
			if (valueToParse == null) {
				throw new ResolveException("Unresolved variable : "+arg);
			}
		}
		parseResult = Integer.parseInt(valueToParse);
		return parseResult;
	}

	private String resolveLetNode(LetNode node, Map<String,String> parentContext) throws ResolveException {
		List<Node> children = node.getChildren();

		if (!children.isEmpty()) {
			StringBuilder sb = new StringBuilder();
			for (Node childNode : children) {
				String childResult = null;
				if (childNode instanceof LetNode)
					childResult = resolveLetNode((LetNode)childNode, parentContext);
				else
					childResult = resolveNode(childNode, parentContext);
				sb.append( childResult );
				
				if (sb.toString().matches("\\(("+REGEX_VAR+")")) {
					Pattern p = Pattern.compile(REGEX_VAR);
					Matcher m = p.matcher(sb.toString());
					
					m.find();
					String var = m.group();
					node.setVar(var);
				}
				
				if (sb.toString().matches("\\(("+REGEX_VAR+"),("+REGEX_NUMBER+"),")) {
					//create var context (map) to bind the variable to its value
					Pattern p = Pattern.compile(REGEX_VAR);
					Matcher m = p.matcher(sb.toString());
					
					m.find();
					String var = m.group();

					p = Pattern.compile(REGEX_NUMBER);
					m = p.matcher(sb.toString());
					
					m.find();
					String value = m.group();
					
					//if the variable already exists at the parent node, it cannot be reassigned
					if (node.getParent() instanceof LetNode) {
						LetNode parent = (LetNode) node.getParent();
						if ( parent.getVar() != null && parent.getVar().equals(var) ) {
							String errorMsg = "Invalid let expression : invalid assignment of var \""+var+"\"";
							throw new ResolveException(errorMsg);
						}
					}
				
					if (parentContext == null)
						parentContext = new HashMap<>();
					parentContext.put(var, value);
				}
			}
			
			String expression = sb.toString();
			
			if (isValidLetExpression(expression)) {
				
				List<String> args = getArguments(expression);
				
				return String.valueOf(args.get(2));
				
			} else {
				//error
				String errorMsg = "Invalid expression : "+node.getData()+expression;
				throw new ResolveException(errorMsg);
			}
			
		} else {
			//error
			String errorMsg = "Invalid let expression : no arguments";
			throw new ResolveException(errorMsg);
		}
	}
	
	/**
	 * Tests if a given parenthesized expression takes one of the following forms:
	 * <br><br>
	 * (x,y) -> \\(NUMBER|VAR,NUMBER|VAR\\)<br> 
	 * 
	 * @return
	 */
	public boolean isValidArityTwoExpression(String expression) {
		return expression.matches(REGEX_PAREN_EXP_ARITY_TWO);
	}
	
	/**
	 * Tests if a given let parenthesized expression takes the form:
	 * <br><br>
	 * (x,y,z) -> \\(VAR,NUMBER|VAR,NUMBER|VAR\\)<br>
	 * 
	 * @return
	 */
	public boolean isValidLetExpression(String expression) {
		return expression.matches(REGEX_PAREN_EXP_LET);
	}

	public List<String> getArguments(String validExpression) {
		Pattern p = Pattern.compile(REGEX_ARG);
		Matcher m = p.matcher(validExpression);
		
		List<String> args = new ArrayList<>();
		
		while (m.find()) {
			args.add( m.group() );
		}
		
		return args;
	}
	
}


