package info.margreiter.nfc.mifareUltralightC;


import info.margreiter.nfc.Exceptions.NFCException;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Properties;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.smartcardio.CardException;

import junit.framework.TestCase;

public class NFCWriterTest extends TestCase {

	public NFCWriterTest(String name) {
		super(name);
	}

	//TODO Test 27.03.2013

	protected void setUp() throws Exception {
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}
	public void testWrite(){
		NFCWriter writer = new NFCWriter();
		String testString = "0300@06<40005000";
		Properties result= new Properties();
		//		Pair<String,String> result = new Pair<String, String>("","");
		try {
			writer.write(testString);
			result=new NFCReader().read();
		} catch (NFCException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CardException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertNotNull(result.get(NFCReader.PROPERTY_TYPE_TAG_VALUE));
		assertEquals(testString,result.get(NFCReader.PROPERTY_TYPE_TAG_VALUE));
	}
}
