package info.margreiter.nfc.mifareUltralightC;

import static org.junit.Assert.assertTrue;
import info.margreiter.nfc.Exceptions.NFCException;

import javax.smartcardio.CardException;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class MifareUltraLightCUtilTest {

	//TODO Test 27.03.2013

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}
	
	/**
	 * ATTENTION locking a Mifare Ultralight C tag is a onewayticket !!!
	 * 
	 */
	public void ntestLockTag(){
		NFCWriter writer=new NFCWriter();
		try {
			writer.lockTag();
		} catch (NFCException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CardException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Test
	public void testDummyTest(){
		assertTrue(true);
	}
}
