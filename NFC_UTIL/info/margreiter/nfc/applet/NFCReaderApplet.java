package info.margreiter.nfc.applet;

import info.margreiter.nfc.mifareUltralightC.NFCReader;

import java.applet.Applet;
import java.util.Date;
import java.util.Properties;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeoutException;

import netscape.javascript.JSObject;


public class NFCReaderApplet extends Applet {
	//TODO Test 28.03.2013
	private JSObject jso=null;
	private Properties tagContent=new Properties();
	private String callback;
	
	 @Override
	    public void start() {
		 
		 
		 	callback=getParameter("callback");
		 	System.out.println("registered Callbackfunction:" + callback);
	        jso=JSObject.getWindow(this);
	        NFCReader reader= new NFCReader();
	        boolean interrupted=false;
	        while (! interrupted) {
				readCard(reader);
				delay(300);
			}
	    }
	 
	    private void readCard(NFCReader reader){
	    	try{
	    		  Properties card = reader.read();
	    		  String tagID = (String) card.get(NFCReader.PROPERTY_TYPE_TAG_IDENTITY);
	    		  String tagValue = (String) card.get(NFCReader.PROPERTY_TYPE_TAG_VALUE);
	    		  if (! card.equals(tagContent)) {
	    			  tagContent=card;
//	    			  System.out.println("tagID:" + tagID + "    tagValue:" + tagValue);
	    			  System.out.println(new Date().toString() + " .... tag wurde gelesen:\007");
	    			  String result="{ id: '" + tagID + "',content: '" + tagValue + "' }";
	    			  jso.call(callback,new String[]{result});	    			  
	    		  }
	    	}catch (Exception e) {
				// TODO: handle exception
	    		tagContent=new Properties();
			}
	    }

		private void delay(int millis) {
			// TODO Test 28.03.2013
			try {
				Thread.currentThread().sleep(millis);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
//				e.printStackTrace();
			}
		}
}
