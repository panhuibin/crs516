package management;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		 String data = "danpw";
		 String md5 = null;
	        try {
	            MessageDigest mdEnc = MessageDigest.getInstance("MD5");
	            mdEnc.update(data.getBytes(), 0, data.length());
	            md5 = new BigInteger(1, mdEnc.digest()).toString(16);
	        } catch (NoSuchAlgorithmException e) {
	            e.printStackTrace();
	        }
	        System.out.println(md5);
	}

}
