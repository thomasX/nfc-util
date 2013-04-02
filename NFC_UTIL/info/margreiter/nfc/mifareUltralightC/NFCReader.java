package info.margreiter.nfc.mifareUltralightC;


import info.margreiter.nfc.Exceptions.NFCException;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Properties;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.smartcardio.CardChannel;
import javax.smartcardio.CardException;

public class NFCReader extends MifareUltraLightCUtil{

	public static final String PROPERTY_TYPE_TAG_IDENTITY= "identity";
	public static final String PROPERTY_TYPE_TAG_VALUE= "value";

	/**
	 * 
	 * @return Pair(tagIdentity,NFCdata)
	 * @throws OutOfRangeException
	 * @throws CardException
	 * @throws InvalidKeyException
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchPaddingException
	 * @throws IllegalBlockSizeException
	 * @throws BadPaddingException
	 */
	public Properties read() throws NFCException, CardException, InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException{
		CardChannel channel = findCardChannel();
		String identity = readCardIdentity(channel);
		String value=read(channel);
        channel.getCard().disconnect(false);
        Properties result = new Properties();
        result.put(PROPERTY_TYPE_TAG_IDENTITY, identity);
        result.put(PROPERTY_TYPE_TAG_VALUE, value);
		return result;
	}

   

	
    	
}
