package com.util;

import org.apache.commons.codec.binary.Base64;


public class Base64Encode {
	/**
	 * Description : method designed to get decoded string with Base64 algo
	 * @param arg
	 * @return
	 */
	public static String getDecodeString(String arg){
		byte[] decode = Base64.decodeBase64(arg.getBytes());
		return new String(decode);
	}
	
	/**
	 * Description : method designed to get encoded string with Base64 algo
	 * @param arg
	 * @return
	 */
	private static final String getEncodeString(String arg){
		byte[] encode = Base64.encodeBase64(arg.getBytes());
		return new String(encode);
	}
}