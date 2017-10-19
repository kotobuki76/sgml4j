package com.brightsconsulting.sgml4j;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.Objects;
import java.util.Stack;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.brightsconsulting.sgml4j.SGMLNode.SGMLNodeType;
import com.brightsconsulting.sgml4j.exceptions.InvalidNodeNameException;

public class SGMLParser {

	public Document parse(File file)
			throws InvalidNodeNameException, IOException, ParserConfigurationException, SAXException {
		FileReader fr = new FileReader(file);
		@SuppressWarnings("resource")
		BufferedReader br = new BufferedReader(fr);
		StringBuffer text = new StringBuffer(1024);
		String line;
		String lineSeparator = System.getProperty("line.separator");
		while ((line = br.readLine()) != null) {
			text.append(line);
			text.append(lineSeparator);
		}
		ArrayList<SGMLNode> list = this.parseStringToList(text.toString());
		SGMLDocument sgml = this.convertListToDocument(list);
		return this.convertSGMLtoXML(sgml);

	}

	/**
	 * SGMLDocumentをXMLに変換する
	 * 
	 * @param sgml
	 * @return
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 */
	Document convertSGMLtoXML(SGMLDocument sgml) throws ParserConfigurationException, SAXException, IOException {
		String xmlText = "<sgml>" + sgml.toString() + "</sgml>";
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder documentBuilder = factory.newDocumentBuilder();
		Document document = documentBuilder.parse(new InputSource(new StringReader(xmlText)));
		return document;
	}

	/**
	 * ノードの単純なリスト構造をツリー上のSGMLDocumentに変換する
	 * 
	 * @param list
	 * @return
	 * @throws InvalidNodeNameException
	 */
	SGMLDocument convertListToDocument(ArrayList<SGMLNode> list) throws InvalidNodeNameException {

		SGMLDocument doc = new SGMLDocument();
		Stack<SGMLNode> workStack = new Stack<SGMLNode>();
		for (SGMLNode n : list) {
			if (n.type == SGMLNodeType.StartTag) {
				workStack.push(n);
			} else if (n.type == SGMLNodeType.TextNode) {
				try {
					SGMLNode parentNode = workStack.pop();
					parentNode.addChild(n);
					workStack.push(parentNode);
				} catch (EmptyStackException e) {
					doc.addRoot(n);
				}

			} else if (n.type == SGMLNodeType.EndTag) {
				SGMLNode prevNode = workStack.pop();
				if (Objects.nonNull(prevNode)) {
					if (prevNode.name.equals(n.name)) {
						prevNode.isClose = true;
						prevNode.type = SGMLNodeType.Node;
						try {
							SGMLNode parentNode = workStack.pop();
							parentNode.addChild(prevNode);
							workStack.push(parentNode);
						} catch (EmptyStackException e) {
							doc.addRoot(prevNode);
						}

					} else {
						throw new InvalidNodeNameException(n.toString());
					}
				} else {
					doc.addRoot(n);
				}
			} else {
				workStack.push(n);
			}
		}

		return doc;
	}

	/**
	 * SGML文字列をノードの単純なリスト構造にパースする
	 * 
	 * @param s
	 * @return
	 */
	ArrayList<SGMLNode> parseStringToList(String s) {

		int len = s.length();

		ArrayList<SGMLNode> list = new ArrayList<SGMLNode>();
		SGMLNode node = new SGMLNode();

		for (int i = 0; i < len; i++) {
			String c = s.substring(i, i + 1);
			// System.out.println(c);
			if (c.equals("<")) {
				if (node != null) {
					if (node.type == SGMLNodeType.TextNode) {
						list.add(node.clone());
					}
				}
				node = new SGMLNode();
				node.type = SGMLNodeType.StartTag;
			} else if (c.equals(">")) {
				list.add(node.clone());
				node = new SGMLNode();
			} else if (c.equals("/")) {
				if (node.type == SGMLNodeType.StartTag) {
					node.type = SGMLNodeType.EndTag;
				}
			} else {
				if (node.type == SGMLNodeType.None) {
					node.type = SGMLNodeType.TextNode;
					node.text += c;
				} else if (node.type == SGMLNodeType.TextNode) {
					node.text += c;
				} else if (node.type == SGMLNodeType.StartTag) {
					node.name += c;
				} else if (node.type == SGMLNodeType.EndTag) {
					node.name += c;
				} else {

				}
			}
		}
		return list;
	}
}
