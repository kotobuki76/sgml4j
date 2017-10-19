package com.brightsconsulting.sgml4j;

import java.util.ArrayList;

class SGMLNode {

	enum SGMLNodeType {
		None, Node, TextNode, StartTag, EndTag;
	}

	public ArrayList<SGMLNode> childNodes;
	public String name = "";
	public String text = "";
	public boolean isClose;
	public SGMLNodeType type;

	public SGMLNode() {
		this.childNodes = new ArrayList<SGMLNode>();
		this.isClose = false;
		this.type = SGMLNodeType.None;
	}

	public void addChild(SGMLNode node) {
		this.childNodes.add(node);
	}
	
	public SGMLNode clone() {
		SGMLNode n = new SGMLNode();
		n.name = this.name;
		n.text = this.text;
		n.isClose = this.isClose;
		n.type = this.type;
		return n;
	}

	public String toString() {
		String s = "";
		if (this.type == SGMLNodeType.None) {
			s += "None";
		} else if (this.type == SGMLNodeType.TextNode) {
			s += this.text;

		} else if (this.type == SGMLNodeType.StartTag) {
			s += "<" + this.name + ">";
		} else if (this.type == SGMLNodeType.EndTag) {
			s += "</" + this.name + ">";
		} else if (this.type == SGMLNodeType.Node) {
			s += "<" + this.name + ">";
			for(SGMLNode n: this.childNodes) {
				s += n.toString();
			}
			s += "</" + this.name + ">";
		} else {
			s += "?";
		}
		
		

		return s;
	}
}
