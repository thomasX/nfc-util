package info.margreiter.nfc.mifareUltralightC;


import info.margreiter.nfc.Exceptions.NFCException;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.List;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.smartcardio.Card;
import javax.smartcardio.CardChannel;
import javax.smartcardio.CardException;
import javax.smartcardio.CardTerminal;
import javax.smartcardio.CommandAPDU;
import javax.smartcardio.ResponseAPDU;
import javax.smartcardio.TerminalFactory;

public abstract class MifareUltraLightCUtil {
	private static final int LOCKING_BLOCKNUMBER = 2;
	private static final String ATRS = "3B8F8001804F0CA0000003060300030000000068";
	//TODO Test 27.03.2013
	protected static final int BLOCK_LENGTH = 4;
	protected static final int FIRST_DATA_BLOCK_NUMBER = 4;
	protected static final int LAST_DATA_BLOCK_NUMBER = 41;

	


	public CardChannel findCardChannel() throws CardException, NFCException{
		TerminalFactory factory= TerminalFactory.getDefault();
		List<CardTerminal> terminals;

		terminals = factory.terminals().list();
        CardTerminal terminal = terminals.get(0);
        Card card = terminal.connect("*");
        if (ATRS.equals(new String(card.getATR().getBytes()))) throw new NFCException("weSupportOnlyMifareUltralightCTags");
        CardChannel channel = card.getBasicChannel();
        return channel;
	}
	
	public String readCardIdentity(CardChannel channel) throws CardException{
        byte[] readIdentityAPDU = {(byte) 0xFF, (byte) 0xCA, (byte) 0x00, (byte) 0x00, (byte) 0x00};
        ResponseAPDU response = channel.transmit(new CommandAPDU(readIdentityAPDU));
        return new String(byteArrayToHexString(response.getData()));
	}
	
	protected String read(CardChannel channel) throws NFCException, InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, CardException {
		// TODO Test 26.03.2013
	        byte[] readIdentityAPDU = {(byte) 0xFF, (byte) 0xCA, (byte) 0x00, (byte) 0x00, (byte) 0x00};
            ResponseAPDU response = channel.transmit(new CommandAPDU(readIdentityAPDU));
	        checkResponse(response);
	        String nfcMemory = "";
	        for (int i = FIRST_DATA_BLOCK_NUMBER; i < LAST_DATA_BLOCK_NUMBER; i++) {
	        	ResponseAPDU nfcData = readBlock(channel, i);
        		nfcMemory +=new String(nfcData.getData());
			}
	        return extractUserData(nfcMemory);
	}

	/**
	 * @param channel
	 * @param i
	 * @return
	 * @throws CardException
	 * @throws NFCException
	 */
	private ResponseAPDU readBlock(CardChannel channel, int i) throws CardException, NFCException {
		byte[] readBlockAPDU = createReadBlockAPDU(i);
		ResponseAPDU nfcData = channel.transmit(new CommandAPDU(readBlockAPDU));
		checkResponse(nfcData);
		return nfcData;
	}
	
	private String extractUserData(String nfcMemory) {
		// TODO Test 27.03.2013
		String result = "";
		byte[] totalBytes = nfcMemory.getBytes();
		Integer startbyte=null;
		Integer lastbyte=0;
		for (int i = 0; i < totalBytes.length; i++) {
			boolean used=(totalBytes[i] != ((byte) 0x00));
			if (used) {
				lastbyte=i;
				if (startbyte==null) startbyte=i;
			}
		}
		if (startbyte!=null) {
			byte[] usedBytes=Arrays.copyOfRange(totalBytes, startbyte,lastbyte +1);
			result=new String(usedBytes);
		}
		return result;
	}

	public String byteArrayToHexString(byte[] b) {
        StringBuffer sb = new StringBuffer(b.length * 2);
        for (int i = 0; i < b.length; i++) {
          int v = b[i] & 0xff;
          if (v < 16) {
            sb.append('0');
          }
          sb.append(Integer.toHexString(v));
        }
        return sb.toString().toUpperCase();
    }
	
	public byte[] concatenateByteArrays(byte[] a, byte[] b) {
	    byte[] result = new byte[a.length + b.length]; 
	    System.arraycopy(a, 0, result, 0, a.length); 
	    System.arraycopy(b, 0, result, a.length, b.length); 
	    return result;
	}
	
	public byte[] createDummyblockData(){
		byte[] byteBlock = {(byte) 0x00};
		byte[] nullbyteArray = {(byte) 0x00};
		while (byteBlock.length < BLOCK_LENGTH) {
			byteBlock= concatenateByteArrays(byteBlock,nullbyteArray);			
		}
		return byteBlock;
	}
	/**
	 * @param blockNumber 
	 * @return
	 */
	private byte[] createReadBlockAPDU(int blockNumber) {
		byte[] readAllBlocksAPDU = {(byte) 0xFF, (byte) 0xB0, (byte) 0x00, (byte) blockNumber, (byte) 0x00};
		return readAllBlocksAPDU;
	}
	
	
    public void lockTag() throws NFCException, CardException {
    	//does not work per definition of mifare ultralight contactless single-ticket
    	//locking the tag is a oneway ticket !!!!
    	CardChannel channel = findCardChannel();
    	readCardIdentity(channel);
    	ResponseAPDU lockingBlock = readBlock(channel, LOCKING_BLOCKNUMBER);
    	byte[] lockingBytes = lockingBlock.getData();
    	lockingBytes[2]= (byte) 0xFF;
    	lockingBytes[3]= (byte) 0xFF;
		writeBlock(channel,LOCKING_BLOCKNUMBER ,lockingBytes);
    }
    

	
	
	private CommandAPDU createWriteBlockAPDU(int block, byte[] blockData) {
		// TODO Test 26.03.2013
		byte[] byteBlock = blockData;
		byte[] nullbyteArray = {(byte) 0x00};
		while (byteBlock.length < BLOCK_LENGTH) {
			byteBlock= concatenateByteArrays(byteBlock,nullbyteArray);			
		}
		byte msb= (byte) ((block & 0xff00) >> 8);
		byte lsb= (byte) (block & 0xff);
		byte[] writeBlockAPDU = {(byte) 0xFF, (byte) 0xD6, msb, lsb, (byte) 0x04};
		writeBlockAPDU = concatenateByteArrays(writeBlockAPDU, byteBlock);
		return new CommandAPDU(writeBlockAPDU);
	}
	
	private void checkResponse(ResponseAPDU response) throws NFCException{
			// TODO Test 26.03.2013
	    	if (response.getSW()!=36864) throw new NFCException("cardIOFailure","#" +Integer.toHexString(response.getSW()));
	}

	protected void write(CardChannel channel,String data) throws CardException, NFCException {
        String untransmitted = data;
        int block=0;
        while (untransmitted.length()>0) {
        	String curBlockData=null;
        	if (untransmitted.length()>BLOCK_LENGTH) {
        		curBlockData=untransmitted.substring(0,BLOCK_LENGTH);
        		untransmitted=untransmitted.substring(BLOCK_LENGTH);
        	} else {
				curBlockData=untransmitted;
				untransmitted="";
			}
			writeBlock(channel,block + FIRST_DATA_BLOCK_NUMBER,curBlockData.getBytes());
			block++;
		}	        
        int dummyblockNR = block+FIRST_DATA_BLOCK_NUMBER;
        byte[] dummyBlockdata = createDummyblockData();
        while (dummyblockNR < LAST_DATA_BLOCK_NUMBER) {
        	writeBlock(channel,dummyblockNR, dummyBlockdata);
        	dummyblockNR++;
		}
	}
	
	private void writeBlock(CardChannel channel, int block, byte[] curBlockData) throws CardException, NFCException {
		// TODO Test 26.03.2013
    	CommandAPDU writeBlockAPDU = createWriteBlockAPDU(block,curBlockData);
    	ResponseAPDU response = channel.transmit(writeBlockAPDU);
    	checkResponse(response);
	}
	
	
}
