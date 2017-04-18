package github.customlibrary.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Utils {

	public static  String enCode(String password)
	{
		try {
			
			MessageDigest digest = MessageDigest.getInstance("MD5");
			byte[] bs = digest.digest(password.getBytes());
			StringBuffer sb=new StringBuffer();
			for(byte bt: bs)
			{
			       int i=bt & 0xff;
			       String hexString = Integer.toHexString(i);
			       if(hexString.length()<2)
			    	   hexString="0"+hexString;
			       sb.append(hexString);
				
			}
			return sb.toString();
			
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
