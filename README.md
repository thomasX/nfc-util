nfc-util
========

A small and simple java nfc writer/reader for the mifare ultralight C Tags   written in Java 1.6


usage: 


1.) Reading a Tag:

NFCReader reader = new NFCReader();
Properties tagContent=reader.read();
String tagID=tagContent.get(NFCReader.PROPERTY_TYPE_TAG_IDENTITY);
String tagValue=tagContent.get(NFCReader.PROPERTY_TYPE_TAG_VALUE);

... tagID contains the unique ID of the MIFARE-Ultralight C chip
... tagValue contains the userData of the MIFARE-Ultralight C chip


2.) Writing on the Tag:
NFCWriter writer = new NFCWriter();
writer.write("Hello World");


3.) Make a Tag Writeprotected (Attention this is a onewayticket !!!)
NFCWriter writer = new NFCWriter();
writer.lockTag();







