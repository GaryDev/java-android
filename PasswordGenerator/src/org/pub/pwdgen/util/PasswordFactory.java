package org.pub.pwdgen.util;

import java.util.Random;

public class PasswordFactory {
	
	private static final String nonAlphabetic = "!@#$%^&*()";
	
	private static final String numeric = "0123456789";
	
	private static final String charactorUpper = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	
	private static final String charactorLower = "abcdefghijklmnopqrstuvwxyz";
	
	private static final String[] charset = new String[] {charactorUpper, charactorLower, numeric, nonAlphabetic};
	
	private int passwordLength;
	
	public void setPasswordLength(int passwordLength) {
		this.passwordLength = passwordLength;
	}

	public PasswordFactory() {
		setPasswordLength(8);
	}
	
	public PasswordFactory(int passwordLength) {
		setPasswordLength(passwordLength);
	}
	
	public String generatePassword() {
		int[] indexArray = randomCommon(0, 3, 3);
		System.out.println(indexArray[0]+","+indexArray[1]+","+indexArray[2]);
		
		Random rand = new Random(System.currentTimeMillis());
		StringBuffer sb = new StringBuffer();
		int index = 0;
		for (int i = 0; i < passwordLength; i++ ) {
			if(index >= indexArray.length) {
				index = 0;
			}
			String policy = charset[indexArray[index++]];	
			int pos = rand.nextInt(policy.length());
			sb.append(policy.charAt(pos));
		}
		return sb.toString();
	}
	
	public static int[] randomCommon(int min, int max, int n) {
		int len = max - min + 1;
		if (max < min || n > len) {
			return null;
		}

		int[] source = new int[len];
		for (int i = min; i < min + len; i++) {
			source[i - min] = i;
		}

		int[] result = new int[n];
		Random rd = new Random();
		int index = 0;
		for (int i = 0; i < result.length; i++) {
			index = Math.abs(rd.nextInt() % len--);
			result[i] = source[index];
			source[index] = source[len];
		}
		return result;
	}	

}
