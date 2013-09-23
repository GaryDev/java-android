package org.pub.pwdgen.util;

import java.util.List;
import java.util.Random;

import org.pub.pwdgen.vo.PasswordPolicy;

public class PasswordFactory {
	
	private static final String nonAlphabetic = "!@#$%^&*()";
	
	private static final String numeric = "0123456789";
	
	private static final String charactorUpper = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	
	private static final String charactorLower = "abcdefghijklmnopqrstuvwxyz";
	
	private static final String[] charset = new String[] {charactorUpper, charactorLower, numeric, nonAlphabetic};
	
	private PasswordPolicy passwordPolicy;
	
	public PasswordFactory(PasswordPolicy passwordPolicy) {
		this.passwordPolicy = passwordPolicy;
	}
	
	public String generatePassword() {
		List<Integer> categories = passwordPolicy.getPasswordCategory();
		int[] indexArray = randomArray(toIntArray(categories));
		
		Random rand = new Random(System.currentTimeMillis());
		StringBuffer sb = new StringBuffer();
		int index = 0;
		for (int i = 0; i < passwordPolicy.getPasswordLength(); i++ ) {
			if(index >= indexArray.length) {
				index = 0;
			}
			String policy = charset[indexArray[index++]];	
			int pos = rand.nextInt(policy.length());
			sb.append(policy.charAt(pos));
		}
		return sb.toString();
	}
	
	private int[] toIntArray(List<Integer> list) {
		int[] ret = new int[list.size()];
		int i = 0;
		for (Integer item : list) {
			ret[i++] = item.intValue();
		}
		return ret;
	}
	
	private int[] randomArray(int[] arr) {
		int[] arr2 = new int[arr.length];
		int count = arr.length;
		int cbRandCount = 0;
		int cbPosition = 0;
		int k = 0;
		do {
			Random rand = new Random();
			int r = count - cbRandCount;
			cbPosition = rand.nextInt(r);
			arr2[k++] = arr[cbPosition];
			cbRandCount++;
			arr[cbPosition] = arr[r - 1];
		} while (cbRandCount < count);
		return arr2;
	}
}
