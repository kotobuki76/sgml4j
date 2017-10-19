package com.brightsconsulting.sgml4j;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;

import org.junit.Test;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import com.brightsconsulting.sgml4j.exceptions.InvalidNodeNameException;

public class SGMLParserTest {

	@Test
	public void test() {
		String t = "<appeal-article-info><filing-info><law>4</law><application-number>1951006751</application-number></filing-info><examined-publication-info><examined-publication-number>1951014240</examined-publication-number><examined-publication-date>19511003</examined-publication-date></examined-publication-info><registration-info><registration-number>0000407315</registration-number><divisional-number>112</divisional-number><registration-date>19520114</registration-date></registration-info><appeal-info><appeal-number>2017300625</appeal-number><appeal-date>20170821</appeal-date><kind-of-instance>1</kind-of-instance></appeal-info><defendant-or-attorney-info><defendant-info><prefecture>20</prefecture><address>長野県上田市</address><type-of-requester>1</type-of-requester><name>川上　秀樹</name></defendant-info><defendant-info><prefecture>20</prefecture><address>長野県上田市</address><type-of-requester>1</type-of-requester><name>▲斉▼　坤玲</name></defendant-info><defendant-info><prefecture>CN</prefecture><address>中国</address><type-of-requester>1</type-of-requester><name>徐　▲亜▼珍</name></defendant-info></defendant-or-attorney-info><detail-of-appeal-info><kind-of-hearing-process>1</kind-of-hearing-process><consolidated-appeal-flag>00</consolidated-appeal-flag><preferential-appeal-flag>0</preferential-appeal-flag></detail-of-appeal-info><intermediate-in-appeal-info><acceptance-in-appeal-type-action><construct-date>20170831</construct-date><intermediate-code>60</intermediate-code><appeal-receipt-number>01727003408</appeal-receipt-number><receipt-date>20170822</receipt-date><formality-check-flag>0</formality-check-flag></acceptance-in-appeal-type-action><acceptance-in-appeal-type-action><construct-date>20170908</construct-date><intermediate-code>51</intermediate-code><appeal-receipt-number>01727003605</appeal-receipt-number><corresponding-dispatch-number>07417016809</corresponding-dispatch-number><receipt-date>20170907</receipt-date><formality-check-flag>0</formality-check-flag></acceptance-in-appeal-type-action><dispatch-in-appeal-type-action><construct-date>20170829</construct-date><intermediate-code>20</intermediate-code><appeal-dispatch-number>07417016489</appeal-dispatch-number><drafting-date>20170828</drafting-date><dispatch-date>20170831</dispatch-date><reason-for-rejection-code>00</reason-for-rejection-code><destination>100</destination></dispatch-in-appeal-type-action><dispatch-in-appeal-type-action><construct-date>20170831</construct-date><intermediate-code>22</intermediate-code><appeal-dispatch-number>07417016686</appeal-dispatch-number><drafting-date>20170830</drafting-date><dispatch-date>20170904</dispatch-date><reason-for-rejection-code>00</reason-for-rejection-code><destination>100</destination></dispatch-in-appeal-type-action><dispatch-in-appeal-type-action><construct-date>20170908</construct-date><intermediate-code>116</intermediate-code><appeal-dispatch-number>07417016809</appeal-dispatch-number><corresponding-receipt-number>01727003408</corresponding-receipt-number><drafting-date>20170831</drafting-date><dispatch-date>20170905</dispatch-date><reason-for-rejection-code>00</reason-for-rejection-code><destination>100</destination></dispatch-in-appeal-type-action><domestic-in-appeal-type-action><construct-date>20170830</construct-date><intermediate-code>95</intermediate-code><disposition-date>20170830</disposition-date><destination>1</destination></domestic-in-appeal-type-action></intermediate-in-appeal-info><renewal-date>20170908</renewal-date></appeal-article-info>";

		SGMLParser p = new SGMLParser();
		ArrayList<SGMLNode> list = p.parseStringToList(t);

		System.out.println(list.size());

		String id = "";
		for (int i = 0; i < list.size(); i++) {
			id += list.get(i).toString();
		}
		System.out.println(id);
		if (!id.equals(t)) {
			System.out.println("not id");
		}
		SGMLDocument doc;
		try {
			doc = p.convertListToDocument(list);
			System.out.println(doc.toString());
			if (!doc.toString().equals(t)) {
				System.out.println("not id");
			}

			Document xml = p.convertSGMLtoXML(doc);
			System.out.println(xml.getChildNodes().item(0).getChildNodes().item(0).getNodeName());

			Document xml2 = p
					.parse(new File("/Users/kotobuki76/T2017-24-01-01-20170920/appealm/contents/data0001/0001.sgm"));
			
			System.out.println(xml2.getChildNodes().item(0).getChildNodes().item(0).getNodeName());

		} catch (InvalidNodeNameException e) {
			// TODO Auto-generated catch block
			System.out.println(e.message);
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
