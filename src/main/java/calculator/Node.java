package calculator;

import java.util.ArrayList;
import java.util.List;

public class Node {

	private String data;
	private Node parent;
	private List<Node> children = new ArrayList<>();
	
	public Node getParent() {
		return parent;
	}
	public void setParent(Node parent) {
		this.parent = parent;
	}
	
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	
	public List<Node> getChildren() {
		return children;
	}
	
	public void addChild(Node childNode) {
		children.add(childNode);
		childNode.setParent(this);
	}
	
	@Override
	public String toString() {
		return data;
	}
	
}
