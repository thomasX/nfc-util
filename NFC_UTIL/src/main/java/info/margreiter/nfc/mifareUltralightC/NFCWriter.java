package info.margreiter.nfc.mifareUltralightC;


import info.margreiter.nfc.Exceptions.NFCException;

import javax.smartcardio.CardChannel;
import javax.smartcardio.CardException;

public class NFCWriter extends MifareUltraLightCUtil {
	//TODO Test 27.03.2013
	
    public void write(String data) throws NFCException, CardException{
    	CardChannel channel = findCardChannel();
    	readCardIdentity(channel);
 	    write(channel,data);
        channel.getCard().disconnect(false);
    }
	
}
