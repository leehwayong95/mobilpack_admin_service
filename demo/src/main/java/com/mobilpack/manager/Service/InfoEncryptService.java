package com.mobilpack.manager.Service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

import org.springframework.stereotype.Service;

@Service
/**
 * 정보 암호화를 위한 Service
 * Type : SHA-256
 * @author LeeHwayong
 *
 */
public class InfoEncryptService {
	
	/**
	 * SHA-256 암호화
	 * @param source Original Data
	 * @param salt Salt
	 * @return Encrypt Data
	 * @throws NoSuchAlgorithmException
	 */
	public static String getEncrypt(String source, String salt) throws NoSuchAlgorithmException {
		return getEncrypt(source, salt.getBytes());
	}
	
	/**
	 * SHA-256 암호화
	 * @param source Original Data
	 * @param salt salt Value
	 * @return
	 * @throws NoSuchAlgorithmException 
	 */
	public static String getEncrypt(String source, byte[] salt) throws NoSuchAlgorithmException {
		String result = "";
		byte[] origin = source.getBytes();
		byte[] mixed = new byte[origin.length + salt.length];
		
		System.arraycopy(origin, 0, mixed, 0, origin.length);
		System.arraycopy(salt, 0, mixed, origin.length, salt.length);

		MessageDigest md = MessageDigest.getInstance("SHA-256");
		md.update(mixed);
		byte[] byteData = md.digest();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < byteData.length; i++) {
			sb.append(Integer.toString((byteData[i] & 0xFF) + 256, 16).substring(1));
		}
		result = sb.toString();
		return result;
	}
	
	/**
	 * get Salt Value
	 * @return Randomize String
	 */
	public static String getSalt() {
		Random random = new Random();
		
		byte[] salt = new byte[8];
		random.nextBytes(salt);
		
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < salt.length; i++) {
			// byte 값을 Hex 값으로 바꾸기.
			sb.append(String.format("%02x",salt[i]));
		}
		return sb.toString();
	}
}
