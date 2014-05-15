package com.mobezer.xml;

import java.io.InputStream;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.VisitorSupport;
import org.dom4j.io.SAXReader;


public  class XLoader {
	SAXReader reader = new SAXReader();
	Document document = null;
	public XLoader(InputStream in) {
		try {
			document = reader.read(in);
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void registerVisitor(VisitorSupport visitor)
	{
		document.accept(visitor);
	}
}
