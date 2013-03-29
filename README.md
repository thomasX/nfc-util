nfc-util
========

A small and simple java nfc writer/reader for the mifare ultralight C Tags   written in Java 1.6


desktop usage: 
==============


1.) Reading a Tag:
------------------

NFCReader reader = new NFCReader();

Properties tagContent=reader.read();

String tagID=tagContent.get(NFCReader.PROPERTY_TYPE_TAG_IDENTITY); 

String tagValue=tagContent.get(NFCReader.PROPERTY_TYPE_TAG_VALUE); 



... tagID contains the unique ID of the MIFARE-Ultralight C chip

... tagValue contains the userData of the MIFARE-Ultralight C chip




2.) Writing on the Tag:
-----------------------

NFCWriter writer = new NFCWriter();

writer.write("Hello World");




3.) Make a Tag Writeprotected (Attention this is a onewayticket !!!)
--------------------------------------------------------------------


NFCWriter writer = new NFCWriter();

writer.lockTag();


web usage:
==========
... (see the example in the folder appletDemo) :

1.) add the nfcUtil.jar to the webcontent
-----------------------------------------

2.) add an applet to your site and define the callback method
-------------------------------------------------------------

<applet mayscript="true" code = 'info/margreiter/nfc/applet/NFCReaderApplet.class'

archive = 'nfcUtil.jar' name="rfid" height="50" width="300">

<param name="callback" value="processRFID">

</applet>

3.) create a callback method in the site
----------------------------------------

<script type="text/javascript">

function processRFID(result){

var obj = eval("(" + result + ')');

var id=obj.id;

var content=obj.content;

... do something with the contant ...

}

</script>




