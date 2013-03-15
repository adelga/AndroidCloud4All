package cloud4all.Utils;

public class ByteUtils
{
	
	/**
	 * Converts a byte array into a String of its hexadecimal representation.
	 * This method should be called statically.
	 * @param data The array of bytes to be converted.
	 * @return The hexadecimal String representation of the byte array.
	 */
	public static String toHexString(byte[] data)
	{
		int c;
		String res = "", s;
		for (byte aux : data)
		{
			c = aux & 0xff;
			s = Integer.toHexString(c);
			if (s.length()==1) res += "0";
			res+=s;
		}
		return res;
	}
}
