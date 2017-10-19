package com.brightsconsulting.sgml4j;

import java.util.ArrayList;

class SGMLDocument {
	public ArrayList<SGMLNode> rootNodes;

	public SGMLDocument() {
		this.rootNodes = new ArrayList<SGMLNode>();
	}
	
	public void addRoot(SGMLNode node) {
		this.rootNodes.add(node);
	}
	
	public String toString() {
		String s = "";
		for(SGMLNode n: this.rootNodes) {
			s += n.toString();
		}
		return s;
	}
	
}
