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

public class NFCReaderTest extends TestCase {

	public NFCReaderTest(String name) {
		super(name);
	}

	//TODO Test 27.03.2013

	protected void setUp() throws Exception {
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testRead(){
		
		String testString = "0300@06<40005000";
		
		NFCReader reader = new NFCReader();
		Properties result= new Properties();
		try {
			new NFCWriter().write(testString);
			result = reader.read();
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NFCException e) {
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
		} catch (CardException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertNotNull(result.get(NFCReader.PROPERTY_TYPE_TAG_VALUE));
		assertEquals(testString, result.get(NFCReader.PROPERTY_TYPE_TAG_VALUE));
	}
}
