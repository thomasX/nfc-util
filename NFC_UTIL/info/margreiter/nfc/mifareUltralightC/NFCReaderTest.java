package info.margreiter.nfc.mifareUltralightC;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import info.margreiter.nfc.Exceptions.NFCException;

import java.awt.Toolkit;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Properties;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.smartcardio.CardException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class NFCReaderTest {


	//TODO Test 27.03.2013
	@Before
	protected void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testSound(){
		try {
			Toolkit.getDefaultToolkit().beep();
			Thread.currentThread().sleep(200);
			Toolkit.getDefaultToolkit().beep();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
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
