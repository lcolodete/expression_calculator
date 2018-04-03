package calculator;

import java.util.List;

public class Parser {

	private TokenManager st;
	
	private String input;
	
	private Node root;
	
	private StringBuilder printBuffer;
	
	public Parser(TokenManager st) {
		this.st = st;
	}
	
	public Parser() {
	}

	public void setTokenManager(TokenManager st) {
		this.st = st;
	}
	
	public StringBuilder getPrintBuffer() {
		return this.printBuffer;
	}

	public void setInput(String input) {
		this.input = input;
	}
	
	public Node buildTree() throws ParseException {
		
		st.reset();
		
		if (st.hasMoreTokens()) {
			
			String token = st.nextToken();
			
			if (TokenManager.isOperator(token)) {

				if (TokenManager.isLetOperator(token)) 
					root = new LetNode();
				else 
					root = new Node();
				
				root.setData(token);
				
				addChildren(root);
				
			} else {
				String errorMsg = "Error at token \""+token+"\". Expected operator keyword "+TokenManager.REGEX_OPERATOR;
				throw new ParseException(errorMsg);
			}

		} else {
			String errorMsg = "Error : input is empty";
			throw new ParseException(errorMsg);
		}

		return root;
	}
	
	private void addChildren(Node node) throws ParseException {
		
		while (st.hasMoreTokens()) {
			String token = st.nextToken();
			if ( token.equals("(") ) {
				Node leftParen = new Node();
				leftParen.setData(token);
				node.addChild(leftParen);
			} else if ( token.equals(",") ) {
				Node comma = new Node();
				comma.setData(token);
				node.addChild(comma);
			} else if ( TokenManager.isOperator(token) ) {
				//creates a subtree (operator node)
				Node root;
				if (TokenManager.isLetOperator(token)) 
					root = new LetNode();
				else 
					root = new Node();
				
				root.setData(token);
				
				node.addChild(root);
				
				addChildren(root);
				
			} else if ( TokenManager.isNumber(token) ) {
				Node numberNode = new Node();
				numberNode.setData(token);
				node.addChild(numberNode);
			} else if ( TokenManager.isVar(token) ) {
				Node varNode = new Node();
				varNode.setData(token);
				node.addChild(varNode);
				
			} else if ( token.equals(")") ) {
				Node rightParen = new Node();
				rightParen.setData(token);
				node.addChild(rightParen);
				break;
			} else {
				String errorMsg = "Error: unknown token \""+token+"\"";
				throw new ParseException(errorMsg);
			}
		}

	}
	
	public void printTree() {
		this.printBuffer = new StringBuilder();
		printChildren(root);
	}
	
	private void printChildren(Node node) {
		this.printBuffer.append(node);

		List<Node> children = node.getChildren();
		
		if (!children.isEmpty()) {
			for (Node n : children) {
				printChildren(n);
			}
		}
		
	}
	
}