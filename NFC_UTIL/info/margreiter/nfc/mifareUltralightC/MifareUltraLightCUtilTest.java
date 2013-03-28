package info.margreiter.nfc.mifareUltralightC;

import info.margreiter.nfc.Exceptions.NFCException;
import info.margreiter.nfc.mifareUltralightC.NFCWriter;

import javax.smartcardio.CardException;

import junit.framework.TestCase;

public class MifareUltraLightCUtilTest extends TestCase {

	public MifareUltraLightCUtilTest(String name) {
		super(name);
	}

	//TODO Test 27.03.2013

	protected void setUp() throws Exception {
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
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
	public void testDummyTest(){
		assertTrue(true);
	}
}
