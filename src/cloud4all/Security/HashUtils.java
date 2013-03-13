package cloud4all.Security;

import java.security.MessageDigest;

import cloud4all.Utils.ByteUtils;

public class HashUtils
{

	private static final String SHA1 = "SHA-1";
	
	// 20B = 40 Nibbles = 40 Hexadecimal characters.
	private static final int SHA1_Length = 20; 
	
	/**
	 * Returns the SHA1 digest of the input string.
	 * This method should be called statically.
	 * @param toDigest The input string to be digest. Could be <code>null</code>.
	 * @return A fixed length String of 40 hexadecimal characters 
	 * containing the SHA1 hash of the input.
	 * This method should not fail but it returns <code>null</code> in case of error.
	 */
	public static String SHA1(String toDigest)
	{
		try
		{
			MessageDigest md = MessageDigest.getInstance(SHA1);
			byte[] hash = new byte[SHA1_Length];
			md.update(toDigest.getBytes());
			hash = md.digest();
			return ByteUtils.toHexString(hash);
		}
		catch (Exception e) { return null; }
	}
}
