package calculator;

import java.util.Scanner;

public class Lexer {

	private String errorMessage;
	
	private TokenManager st;
	
	public Lexer() {
	}

	public boolean analyzeStep1(String sInput) {
		
		if (sInput.isEmpty()) {
			setErrorMessage("Error : input is empty");
			return false;
		}
		
		try (Scanner s = new Scanner(sInput)) {
			
			s.useDelimiter("");
			
			StringBuilder sb = new StringBuilder();
			
			sb.append(s.next());
			
			while (s.hasNext()) {
				String next = s.next();
				if (next.matches("\\(|,|\\)")) {
					//build token and check if is valid
					if (!buildAndValidateToken(sb))
						return false;
					sb = new StringBuilder();
				} else {
					sb.append(next);
				}
			}
			
			if (!buildAndValidateToken(sb))
				return false;
		}
		
		return true;
	}
	
	private boolean buildAndValidateToken(StringBuilder sb) {
		String token = sb.toString().trim();
		if (!token.isEmpty()) {
			if (!TokenManager.isOperator(token) && !TokenManager.isVar(token) && !TokenManager.isNumber(token)) {
				setErrorMessage("Error: unknown token \""+token+"\"");
				return false;
			}
		}
		return true;
	}
	
	public boolean analyzeStep2(String sInput) {
		
		st = new TokenManager(sInput);
		
		while (st.hasMoreTokens()) {
			String token = st.nextToken();
			
			if ( token.matches("\\(|,|\\)") ) {
				continue;
				
			} else if ( TokenManager.isOperator(token) ) {
				
				if (st.hasMoreTokens()) {
					token = st.nextToken();
					if (!token.equals("(")) {
						setErrorMessage(String.format("Error on token \"%s\" at index %s", token, this.st.getInfo()));
						return false;
					}
				}
				else {
					setErrorMessage("Error: check for missing parenthesis");
					return false;
				}
				
			} else if ( TokenManager.isNumber(token) ||
						TokenManager.isVar(token)) {
				
				if (st.hasMoreTokens()) {
					token = st.nextToken();
					if (!token.matches("\\)|,")) {
						setErrorMessage(String.format("Error on token \"%s\" at index %s", token, this.st.getInfo()));
						return false;
					}
				}
				else {
					setErrorMessage("Error: check for missing parenthesis");					
					return false;
				}
				
			}
		}
		
		return true;
	}

	public void setErrorMessage(String errorMsg) {
		this.errorMessage = errorMsg;
	}
	
	public String getErrorMessage() {
		return this.errorMessage;
	}

}
