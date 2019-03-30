package com.mysql.demo.utils;

import java.math.BigInteger;
import java.security.SecureRandom;

public class ApiKeyGenerator {
	/**
	 * This meethod is dedicated to generate random API KEY
	 * 
	 * @param byteLength
	 * @return
	 */
	public static String generateRandomHexToken(int byteLength) {
		SecureRandom secureRandom = new SecureRandom();
		byte[] token = new byte[byteLength];
		secureRandom.nextBytes(token);
		return new BigInteger(1, token).toString(16); // hex encoding
	}
}
