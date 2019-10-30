package com.newlife.s4.util;

import com.thoughtworks.xstream.io.xml.XmlFriendlyNameCoder;

public class XStreamNameCoder extends XmlFriendlyNameCoder {
	public XStreamNameCoder() {
		super("_-", "_");
	}
}
