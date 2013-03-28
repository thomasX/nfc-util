package info.margreiter.nfc.Exceptions;

import java.io.Serializable;

/**
 * Created on 02.12.2003
 * @author Thomas
 * 
 * is responsible for identifying a FERPSException
 */
public class NFCException extends Exception implements Serializable{
	private String localizedMessage="";
	/**
	 * constructor<
	 */
	public NFCException() {
		super();
	}

	/**
	 * constructor
	 * @param s
	 */
	public NFCException(String s) {
		super(s);
	}
	/**
	 * constructor
	 * @param s
	 */
	public NFCException(String s,String translatedMessage) {
		super(s);
		this.localizedMessage=translatedMessage;
	}

	@Override
	public String getLocalizedMessage() {
		return localizedMessage;
	}

}
