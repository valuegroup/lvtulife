package com.lvtulife.base.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.BreakIterator;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;

/**
 * 
 * @author gary
 * @version 1.0.1
 */
public class CommonUtils {
	private static final char[] QUOTE_ENCODE = "&quot;".toCharArray();
	private static final char[] AMP_ENCODE = "&amp;".toCharArray();
	private static final char[] LT_ENCODE = "&lt;".toCharArray();
	private static final char[] GT_ENCODE = "&gt;".toCharArray();
	private static MessageDigest digest = null;

	/**
	 * 把对象型数组转成Long型数组
	 * 
	 * @param ids
	 *            Object[]
	 * @return Long[]
	 */
	public static Long[] objectArr2LongArr(Object[] ids) throws Exception {
		Long[] temp = null;
		if (ids != null && ids.length > 0) {
			temp = new Long[ids.length];
			for (int i = 0; i < ids.length; i++) {
				temp[i] = String2Long(ids[i].toString());
			}
		}
		return temp;
	}

	/**
	 * 把字符型数组转成Long型数组
	 * 
	 * @param strs
	 *            String[]
	 * @return long[]
	 */
	public static Long[] strArr2LongArr(String[] strs) throws Exception {
		if (strs == null)
			return null;
		Long[] longArr = new Long[strs.length];
		for (int i = 0; i < strs.length; i++) {
			longArr[i] = String2Long(strs[i]);
		}
		return longArr;
	}

	/**
	 * 把字符型转成长整型
	 * 
	 * @param id
	 *            String
	 * @return long
	 */
	public static Long String2Long(String id) throws Exception {
		if (isValidLong(id))
			return new Long(id);
		else
			throw new Exception("无效字符，不能转换！");
	}

	/**
	 * 把对象型数组转成String型数组
	 * 
	 * @param ids
	 *            Object[]
	 * @return String[]
	 */
	public static String[] objectArr2StringArr(Object[] ids) throws Exception {
		String[] temp = null;
		if (ids != null && ids.length > 0) {
			temp = new String[ids.length];
			for (int i = 0; i < ids.length; i++) {
				temp[i] = String.valueOf(ids[i]);
			}
		}
		return temp;
	}

	public static String[] listStrArr2StringArr(List<List<String>> ids) throws Exception {
		String[] temp = new String[0];
		if (ids != null && ids.size() > 0) {
			temp = new String[ids.size()];
			int i = 0;
			for (List list : ids) {
				temp[i++] = (String) list.get(0);
			}
		}
		return temp;
	}

	/**
	 * 判断字符串是否有效,有效返回ture
	 * 
	 * @param str
	 *            String
	 * @return boolean
	 */
	public static boolean isValid(String str) {
		return (str == null || str.length() == 0 || str.trim().toLowerCase().equals("null") || str.trim().equals("")) ? false : true;
	}

	/**
	 * 将无效字符串转换成有效数字字符
	 * 
	 * @param str
	 *            String
	 * @return boolean
	 */
	public static String converString2ValidNum(String str) {
		if (!isValid(str)) {
			return "0";
		}
		return str;

	}

	/**
	 * 将无效字符串转换成有效字符
	 * 
	 * @param str
	 *            String
	 * @return boolean
	 */
	public static String converString2Valid(String str) {
		if (str == null || str.equals("null")) {
			return "";
		}
		return str;

	}

	public static boolean validateEmail(String email) {
		// 验证email
		String check = "^([a-z0-9A-Z]+[-\\_]?)+([a-z0-9A-Z]+[-\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
		Pattern regex = Pattern.compile(check);
		Matcher matcher = regex.matcher(email);
		boolean isMatched = matcher.matches();
		if (isMatched) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean validatePassword(String password) {
		// String check = "^(\\w){6,20}";
		String check = "[0-9a-zA-Z-._]{6,20}";
		Pattern regex = Pattern.compile(check);
		Matcher matcher = regex.matcher(password);
		boolean isMatched = matcher.matches();
		if (isMatched) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 判断字符串是否为数字,是数字返回true
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNumeric(String str) {
		if (!StringUtils.isBlank(str)) {
			Pattern pattern = Pattern.compile("[0-9]*");
			Matcher isNum = pattern.matcher(str);
			if (isNum.matches()) {
				return true;
			}
		}
		return false;
	}

	// 判断是否是小数 是返回true
	public static boolean isDouble(String str) {// 判断小数，与判断整型的区别在与d后面的小数点（红色）
		return str.matches("[\\d.]+");
	}

	// 判断字符是否是LONG 是返回true
	public static boolean isValidLong(String str) {
		try {
			Long.parseLong(str);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	// 判断是否是小于1的小数 是返回true;
	public static boolean isDouble01(String str, int maxNum) {// 判断小数，与判断整型的区别在与d后面的小数点（红色）
		Pattern patterni = Pattern.compile("[0-9]*");
		Pattern patternd = Pattern.compile("[\\d.]+");

		Matcher isNumi = patterni.matcher(str);
		Matcher isNumd = patternd.matcher(str);
		if (!isNumi.matches()) {
			// System.out.println("不是整型数据");
			if (!isNumd.matches()) {
				// System.out.println("不是小数");
				return false;
			} else {
				// System.out.println("是小数");
				double dm = Double.valueOf(String.valueOf(maxNum));
				double d = Double.valueOf(str);
				if (d > dm) {
					// System.out.println("是大于maxNum的小数");
					return false;
				}
			}
		} else {
			// System.out.println("是整型数据");
			int n = Integer.parseInt(str);
			if (n > maxNum) {
				// System.out.println("大于maxNum的整型数据");
				return false;
			}
		}
		return true;
	}

	public static boolean validateUserName(String username) {
		Pattern p = Pattern.compile("^\\w+$");
		Matcher m = p.matcher(username);
		if (m.find()) {
			return true;
		}
		return false;
	}

	public static String Array2String(String[] values) {
		String result = "";
		if (values == null) {
			return result;
		}
		int len = values.length;
		for (int i = 0; i < len; i++) {
			result += values[i] + ",";
		}
		if (result.endsWith(",")) {
			result = result.substring(result.length() - 1);
		}
		return result;
	}

	public static String Array2String(Object[] values) {
		String result = "";
		if (values == null) {
			return result;
		}
		int len = values.length;
		for (int i = 0; i < len; i++) {
			result += values[i].toString() + ",";
		}
		if (result.endsWith(",")) {
			result = result.substring(result.length() - 1);
		}
		return result;
	}

	public static String Array2String(List values) {
		String result = "";
		if (values == null) {
			return result;
		}
		int len = values.size();
		for (int i = 0; i < len; i++) {
			result += values.get(i).toString() + ",";
		}
		if (result.endsWith(",")) {
			result = result.substring(0, result.length() - 1);
		}
		return result;
	}

	public static String Array2StringV2(List values) {
		String result = "";
		if (values == null) {
			return result;
		}
		int len = values.size();
		for (int i = 0; i < len; i++) {
			result += "'" + values.get(i).toString() + "',";
		}
		if (result.endsWith(",")) {
			result = result.substring(0, result.length() - 1);
		}
		return result;
	}

	public static String base64Encode(String txt) {
		if (txt != null && txt.length() > 0) {
			txt = new Base64().encodeToString(txt.getBytes());
			// txt = new sun.misc.BASE64Encoder().encode(txt.getBytes());
		}
		return txt;
	}

	public static String base64Encode(byte[] txt) {
		String encodeTxt = "";
		if (txt != null && txt.length > 0) {
			encodeTxt = new Base64().encodeToString(txt);
			// encodeTxt = new sun.misc.BASE64Encoder().encode(txt);
		}
		return encodeTxt;
	}

	public static String base64decode(String txt) {
		if (txt != null && txt.length() > 0) {
			byte[] buf;
			try {
				buf = new Base64().decode(txt);
				// buf = new sun.misc.BASE64Decoder().decodeBuffer(txt);
				txt = new String(buf);
			} catch (Exception ex)
			// catch (IOException ex)
			{
			}
		}
		return txt;
	}

	public static byte[] base64decodebyte(String txt) {
		byte[] buf = null;
		if (txt != null && txt.length() > 0) {
			try {
				buf = new Base64().decode(txt);
				// buf = new sun.misc.BASE64Decoder().decodeBuffer(txt);
			} catch (Exception ex)
			// catch (IOException ex)
			{
			}
		}
		return buf;
	}

	/**
	 * Replaces all instances of oldString with newString in line.
	 * 
	 * @param line
	 *            the String to search to perform replacements on
	 * @param oldString
	 *            the String that should be replaced by newString
	 * @param newString
	 *            the String that will replace all instances of oldString
	 * @return a String will all instances of oldString replaced by newString
	 */
	public static final String replace(String line, String oldString, String newString) {
		if (line == null) {
			return null;
		}
		int i = 0;
		if ((i = line.indexOf(oldString, i)) >= 0) {
			char[] line2 = line.toCharArray();
			char[] newString2 = newString.toCharArray();
			int oLength = oldString.length();
			StringBuffer buf = new StringBuffer(line2.length);
			buf.append(line2, 0, i).append(newString2);
			i += oLength;
			int j = i;
			while ((i = line.indexOf(oldString, i)) > 0) {
				buf.append(line2, j, i - j).append(newString2);
				i += oLength;
				j = i;
			}
			buf.append(line2, j, line2.length - j);
			return buf.toString();
		}
		return line;
	}

	/**
	 * Replaces all instances of oldString with newString in line with the added feature that matches of newString in oldString ignore case.
	 * 
	 * @param line
	 *            the String to search to perform replacements on
	 * @param oldString
	 *            the String that should be replaced by newString
	 * @param newString
	 *            the String that will replace all instances of oldString
	 * @return a String will all instances of oldString replaced by newString
	 */
	public static final String replaceIgnoreCase(String line, String oldString, String newString) {
		if (line == null) {
			return null;
		}
		String lcLine = line.toLowerCase();
		String lcOldString = oldString.toLowerCase();
		int i = 0;
		if ((i = lcLine.indexOf(lcOldString, i)) >= 0) {
			char[] line2 = line.toCharArray();
			char[] newString2 = newString.toCharArray();
			int oLength = oldString.length();
			StringBuffer buf = new StringBuffer(line2.length);
			buf.append(line2, 0, i).append(newString2);
			i += oLength;
			int j = i;
			while ((i = lcLine.indexOf(lcOldString, i)) > 0) {
				buf.append(line2, j, i - j).append(newString2);
				i += oLength;
				j = i;
			}
			buf.append(line2, j, line2.length - j);
			return buf.toString();
		}
		return line;
	}

	/**
	 * Replaces all instances of oldString with newString in line with the added feature that matches of newString in oldString ignore case. The count paramater is set to the number of replaces performed.
	 * 
	 * @param line
	 *            the String to search to perform replacements on
	 * @param oldString
	 *            the String that should be replaced by newString
	 * @param newString
	 *            the String that will replace all instances of oldString
	 * @param count
	 *            a value that will be updated with the number of replaces performed.
	 * @return a String will all instances of oldString replaced by newString
	 */
	public static final String replaceIgnoreCase(String line, String oldString, String newString, int[] count) {
		if (line == null) {
			return null;
		}
		String lcLine = line.toLowerCase();
		String lcOldString = oldString.toLowerCase();
		int i = 0;
		if ((i = lcLine.indexOf(lcOldString, i)) >= 0) {
			int counter = 0;
			char[] line2 = line.toCharArray();
			char[] newString2 = newString.toCharArray();
			int oLength = oldString.length();
			StringBuffer buf = new StringBuffer(line2.length);
			buf.append(line2, 0, i).append(newString2);
			i += oLength;
			int j = i;
			while ((i = lcLine.indexOf(lcOldString, i)) > 0) {
				counter++;
				buf.append(line2, j, i - j).append(newString2);
				i += oLength;
				j = i;
			}
			buf.append(line2, j, line2.length - j);
			count[0] = counter;
			return buf.toString();
		}
		return line;
	}

	/**
	 * Replaces all instances of oldString with newString in line. The count Integer is updated with number of replaces.
	 * 
	 * @param line
	 *            the String to search to perform replacements on
	 * @param oldString
	 *            the String that should be replaced by newString
	 * @param newString
	 *            the String that will replace all instances of oldString
	 * @return a String will all instances of oldString replaced by newString
	 */
	public static final String replace(String line, String oldString, String newString, int[] count) {
		if (line == null) {
			return null;
		}
		int i = 0;
		if ((i = line.indexOf(oldString, i)) >= 0) {
			int counter = 0;
			counter++;
			char[] line2 = line.toCharArray();
			char[] newString2 = newString.toCharArray();
			int oLength = oldString.length();
			StringBuffer buf = new StringBuffer(line2.length);
			buf.append(line2, 0, i).append(newString2);
			i += oLength;
			int j = i;
			while ((i = line.indexOf(oldString, i)) > 0) {
				counter++;
				buf.append(line2, j, i - j).append(newString2);
				i += oLength;
				j = i;
			}
			buf.append(line2, j, line2.length - j);
			count[0] = counter;
			return buf.toString();
		}
		return line;
	}

	/**
	 * This method takes a string which may contain HTML tags (ie, &lt;b&gt;, &lt;table&gt;, etc) and converts the '&lt'' and '&gt;' characters to their HTML escape sequences.
	 * 
	 * @param in
	 *            the text to be converted.
	 * @return the input string with the characters '&lt;' and '&gt;' replaced with their HTML escape sequences.
	 */
	public static final String escapeHTMLTags(String in) {
		if (in == null) {
			return null;
		}
		char ch;
		int i = 0;
		int last = 0;
		char[] input = in.toCharArray();
		int len = input.length;
		StringBuffer out = new StringBuffer((int) (len * 1.3));
		for (; i < len; i++) {
			ch = input[i];
			if (ch > '>') {
				continue;
			} else if (ch == '<') {
				if (i > last) {
					out.append(input, last, i - last);
				}
				last = i + 1;
				out.append(LT_ENCODE);
			} else if (ch == '>') {
				if (i > last) {
					out.append(input, last, i - last);
				}
				last = i + 1;
				out.append(GT_ENCODE);
			}
		}
		if (last == 0) {
			return in;
		}
		if (i > last) {
			out.append(input, last, i - last);
		}
		return out.toString();
	}

	/**
	 * Hashes a String using the Md5 algorithm and returns the result as a String of hexadecimal numbers. This method is synchronized to avoid excessive MessageDigest object creation. If calling this method becomes a bottleneck in your code, you may wish to maintain a pool of MessageDigest objects instead of using this method.
	 * <p>
	 * A hash is a one-way function -- that is, given an input, an output is easily computed. However, given the output, the input is almost impossible to compute. This is useful for passwords since we can store the hash and a hacker will then have a very hard time determining the original password.
	 * <p>
	 * In Jive, every time a user logs in, we simply take their plain text password, compute the hash, and compare the generated hash to the stored hash. Since it is almost impossible that two passwords will generate the same hash, we know if the user gave us the correct password or not. The only negative to this system is that password recovery is basically impossible. Therefore, a reset password method is used instead.
	 * 
	 * @param data
	 *            the String to compute the hash of.
	 * @return a hashed version of the passed-in String
	 */
	public synchronized static final String hash(String data) {
		if (digest == null) {
			try {
				digest = MessageDigest.getInstance("MD5");
			} catch (NoSuchAlgorithmException nsae) {
				System.err.println("Failed to load the MD5 MessageDigest. " + "We will be unable to function normally.");
				nsae.printStackTrace();
			}
		}
		// Now, compute hash.
		digest.update(data.getBytes());
		return encodeHex(digest.digest());
	}

	/**
	 * Turns an array of bytes into a String representing each byte as an unsigned hex number.
	 * <p>
	 * Method by Santeri Paavolainen, Helsinki Finland 1996<br>
	 * (c) Santeri Paavolainen, Helsinki Finland 1996<br>
	 * Distributed under LGPL.
	 * 
	 * @param bytes
	 *            an array of bytes to convert to a hex-string
	 * @return generated hex string
	 */
	public static final String encodeHex(byte[] bytes) {
		StringBuffer buf = new StringBuffer(bytes.length * 2);
		int i;
		for (i = 0; i < bytes.length; i++) {
			if (((int) bytes[i] & 0xff) < 0x10) {
				buf.append("0");
			}
			buf.append(Long.toString((int) bytes[i] & 0xff, 16));
		}
		return buf.toString().toUpperCase();
	}

	/**
	 * Turns a hex encoded string into a byte array. It is specifically meant to "reverse" the toHex(byte[]) method.
	 * 
	 * @param hex
	 *            a hex encoded String to transform into a byte array.
	 * @return a byte array representing the hex String[
	 */
	public static final byte[] decodeHex(String hex) {
		char[] chars = hex.toCharArray();
		byte[] bytes = new byte[chars.length / 2];
		int byteCount = 0;
		for (int i = 0; i < chars.length; i += 2) {
			byte newByte = 0x00;
			newByte |= hexCharToByte(chars[i]);
			newByte <<= 4;
			newByte |= hexCharToByte(chars[i + 1]);
			bytes[byteCount] = newByte;
			byteCount++;
		}
		return bytes;
	}

	/**
	 * Returns the the byte value of a hexadecmical char (0-f). It's assumed that the hexidecimal chars are lower case as appropriate.
	 * 
	 * @param ch
	 *            a hexedicmal character (0-f)
	 * @return the byte value of the character (0x00-0x0F)
	 */
	private static final byte hexCharToByte(char ch) {
		switch (ch) {
		case '0':
			return 0x00;
		case '1':
			return 0x01;
		case '2':
			return 0x02;
		case '3':
			return 0x03;
		case '4':
			return 0x04;
		case '5':
			return 0x05;
		case '6':
			return 0x06;
		case '7':
			return 0x07;
		case '8':
			return 0x08;
		case '9':
			return 0x09;
		case 'a':
			return 0x0A;
		case 'b':
			return 0x0B;
		case 'c':
			return 0x0C;
		case 'd':
			return 0x0D;
		case 'e':
			return 0x0E;
		case 'f':
			return 0x0F;
		}
		return 0x00;
	}

	/**
	 * Converts a line of text into an array of lower case words using a BreakIterator.wordInstance().
	 * <p>
	 * This method is under the Jive Open Source Software License and was written by Mark Imbriaco.
	 * 
	 * @param text
	 *            a String of text to convert into an array of words
	 * @return text broken up into an array of words.
	 */
	public static final String[] toLowerCaseWordArray(String text) {
		if (text == null || text.length() == 0) {
			return new String[0];
		}
		ArrayList<String> wordList = new ArrayList<String>();
		BreakIterator boundary = BreakIterator.getWordInstance();
		boundary.setText(text);
		int start = 0;
		for (int end = boundary.next(); end != BreakIterator.DONE; start = end, end = boundary.next()) {
			String tmp = text.substring(start, end).trim();
			// Remove characters that are not needed.
			tmp = replace(tmp, "+", "");
			tmp = replace(tmp, "/", "");
			tmp = replace(tmp, "\\", "");
			tmp = replace(tmp, "#", "");
			tmp = replace(tmp, "*", "");
			tmp = replace(tmp, ")", "");
			tmp = replace(tmp, "(", "");
			tmp = replace(tmp, "&", "");
			if (tmp.length() > 0) {
				wordList.add(tmp);
			}
		}
		return (String[]) wordList.toArray(new String[wordList.size()]);
	}

	/**
	 * Pseudo-random number generator object for use with randomString(). The Random class is not considered to be cryptographically secure, so only use these random Strings for low to medium security applications.
	 */
	private static Random randGen = new Random();
	/**
	 * Array of numbers and letters of mixed case. Numbers appear in the list twice so that there is a more equal chance that a number will be picked. We can use the array to get a random number or letter by picking a random array index.
	 */
	private static char[] numbersAndLetters = ("0123456789abcdefghijklmnopqrstuvwxyz" + "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ").toCharArray();

	/**
	 * Returns a random String of numbers and letters (lower and upper case) of the specified length. The method uses the Random class that is built-in to Java which is suitable for low to medium grade security uses. This means that the output is only pseudo random, i.e., each number is mathematically generated so is not truly random.
	 * <p>
	 * The specified length must be at least one. If not, the method will return null.
	 * 
	 * @param length
	 *            the desired length of the random String to return.
	 * @return a random String of numbers and letters of the specified length.
	 */
	public static final String randomString(int length) {
		if (length < 1) {
			return null;
		}
		// Create a char buffer to put random letters and numbers in.
		char[] randBuffer = new char[length];
		for (int i = 0; i < randBuffer.length; i++) {
			randBuffer[i] = numbersAndLetters[randGen.nextInt(71)];
		}
		return new String(randBuffer);
	}

	public static final int randomInt(int num) {
		if (num < 1) {
			return 0;
		}
		return randGen.nextInt(num);
	}

	/**
	 * Intelligently chops a String at a word boundary (whitespace) that occurs at the specified index in the argument or before. However, if there is a newline character before <code>length</code>, the String will be chopped there. If no newline or whitespace is found in <code>string</code> up to the index <code>length</code>, the String will chopped at <code>length</code>.
	 * <p>
	 * For example, chopAtWord("This is a nice String", 10) will return "This is a" which is the first word boundary less than or equal to 10 characters into the original String.
	 * 
	 * @param string
	 *            the String to chop.
	 * @param length
	 *            the index in <code>string</code> to start looking for a whitespace boundary at.
	 * @return a substring of <code>string</code> whose length is less than or equal to <code>length</code>, and that is chopped at whitespace.
	 */
	public static final String chopAtWord(String string, int length) {
		if (string == null) {
			return string;
		}
		char[] charArray = string.toCharArray();
		int sLength = string.length();
		if (length < sLength) {
			sLength = length;
		}
		// First check if there is a newline character before length; if so,
		// chop word there.
		for (int i = 0; i < sLength - 1; i++) {
			// Windows
			if (charArray[i] == '\r' && charArray[i + 1] == '\n') {
				return string.substring(0, i + 1);
			}
			// Unix
			else if (charArray[i] == '\n') {
				return string.substring(0, i);
			}
		}
		// Also check boundary case of Unix newline
		if (charArray[sLength - 1] == '\n') {
			return string.substring(0, sLength - 1);
		}
		// Done checking for newline, now see if the total string is less than
		// the specified chop point.
		if (string.length() < length) {
			return string;
		}
		// No newline, so chop at the first whitespace.
		for (int i = length - 1; i > 0; i--) {
			if (charArray[i] == ' ') {
				return string.substring(0, i).trim();
			}
		}
		// Did not find word boundary so return original String chopped at
		// specified length.
		return string.substring(0, length);
	}

	/**
	 * Escapes all necessary characters in the String so that it can be used in an XML doc.
	 * 
	 * @param string
	 *            the string to escape.
	 * @return the string with appropriate characters escaped.
	 */
	public static final String escapeForXML(String string) {
		if (string == null) {
			return null;
		}
		char ch;
		int i = 0;
		int last = 0;
		char[] input = string.toCharArray();
		int len = input.length;
		StringBuffer out = new StringBuffer((int) (len * 1.3));
		for (; i < len; i++) {
			ch = input[i];
			if (ch > '>') {
				continue;
			} else if (ch == '<') {
				if (i > last) {
					out.append(input, last, i - last);
				}
				last = i + 1;
				out.append(LT_ENCODE);
			} else if (ch == '&') {
				if (i > last) {
					out.append(input, last, i - last);
				}
				last = i + 1;
				out.append(AMP_ENCODE);
			} else if (ch == '"') {
				if (i > last) {
					out.append(input, last, i - last);
				}
				last = i + 1;
				out.append(QUOTE_ENCODE);
			}
		}
		if (last == 0) {
			return string;
		}
		if (i > last) {
			out.append(input, last, i - last);
		}
		return out.toString();
	}

	public static final String escapeForSpecial(String string) {
		if (string == null) {
			return null;
		}
		char ch;
		int i = 0;
		int last = 0;
		char[] input = string.toCharArray();
		int len = input.length;
		StringBuffer out = new StringBuffer((int) (len * 1.3));
		for (; i < len; i++) {
			ch = input[i];
			if (ch > '>') {
				continue;
			} else if (ch == '<') {
				if (i > last) {
					out.append(input, last, i - last);
				}
				last = i + 1;
				out.append(LT_ENCODE);
			} else if (ch == '&') {
				if (i > last) {
					out.append(input, last, i - last);
				}
				last = i + 1;
				out.append(AMP_ENCODE);
			} else if (ch == '"') {
				if (i > last) {
					out.append(input, last, i - last);
				}
				last = i + 1;
				out.append(QUOTE_ENCODE);
			} else if (ch == '>') {
				if (i > last) {
					out.append(input, last, i - last);
				}
				last = i + 1;
				out.append(GT_ENCODE);
			}
		}
		if (last == 0) {
			return string;
		}
		if (i > last) {
			out.append(input, last, i - last);
		}
		return out.toString();
	}

	/**
	 * Unescapes the String by converting XML escape sequences back into normal characters.
	 * 
	 * @param string
	 *            the string to unescape.
	 * @return the string with appropriate characters unescaped.
	 */
	public static final String unescapeFromXML(String string) {
		string = replace(string, "&lt;", "<");
		string = replace(string, "&gt;", ">");
		string = replace(string, "&quot;", "\"");
		return replace(string, "&amp;", "&");
	}

	private static final char[] zeroArray = "0000000000000000".toCharArray();

	/**
	 * Pads the supplied String with 0's to the specified length and returns the result as a new String. For example, if the initial String is "9999" and the desired length is 8, the result would be "00009999". This type of padding is useful for creating numerical values that need to be stored and sorted as character data. Note: the current implementation of this method allows for a maximum <tt>length</tt> of 16.
	 * 
	 * @param string
	 *            the original String to pad.
	 * @param length
	 *            the desired length of the new padded String.
	 * @return a new String padded with the required number of 0's.
	 */
	public static final String zeroPadString(String string, int length) {
		if (string == null || string.length() > length) {
			return string;
		}
		StringBuffer buf = new StringBuffer(length);
		buf.append(zeroArray, 0, length - string.length()).append(string);
		return buf.toString();
	}

	/**
	 * Formats a Date as a fifteen character long String made up of the Date's padded millisecond value.
	 * 
	 * @return a Date encoded as a String.
	 */
	public static final String dateToMillis(Date date) {
		return zeroPadString(Long.toString(date.getTime()), 15);
	}

	/**
	 * Formats a Date as a fifteen character long String made up of the Date's padded millisecond value.
	 * 
	 * @return a Date encoded as a String.
	 */
	@SuppressWarnings("unchecked")
	public static final String collectionToString(Collection c, String spilt) {
		if (c == null) {
			return null;
		}
		if (spilt == null) {
			return null;
		}
		String ret = "";
		ArrayList a = new ArrayList(c);
		try {
			for (int i = 0; i < a.size(); i++) {
				String t = (String) a.get(i);
				if (i == a.size() - 1) {
					ret = ret + t;
				} else {
					ret = ret + t + spilt;
				}
			}
			return ret;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static String genPassword(int length) {
		if (length < 1) {
			return null;
		}
		String[] strChars = { "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "m", "n", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z", "a" };
		// 没有0,o,l和I，以免误解
		StringBuffer strPassword = new StringBuffer();
		int nRand = (int) Math.round(Math.random() * 100);
		for (int i = 0; i < length; i++) {
			nRand = (int) Math.round(Math.random() * 100);
			strPassword.append(strChars[nRand % (strChars.length - 1)]);
			// strPassword += strChars[nRand % (strChars.length - 1)];
		}
		return strPassword.toString();
	}

	public static String genNumPassword(int length) {
		if (length < 1) {
			return null;
		}
		String[] strChars = { "1", "2", "3", "4", "5", "6", "7", "8", "9" };
		StringBuffer strPassword = new StringBuffer();
		int nRand = (int) Math.round(Math.random() * 100);
		for (int i = 0; i < length; i++) {
			nRand = (int) Math.round(Math.random() * 100);
			strPassword.append(strChars[nRand % (strChars.length - 1)]);
			// strPassword += strChars[nRand % (strChars.length - 1)];
		}
		return strPassword.toString();
	}

	public static String genEmptyString(int length) {
		if (length < 1) {
			return null;
		}
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < length; i++) {
			sb.append(" ");
		}
		return sb.toString();
	}

	public static String getAsciiString(int digit) {
		byte ret[] = new byte[1];
		ret[0] = (byte) digit;
		return new String(ret);
	}

	public static int getAsciiNum(String s) {
		if (s.length() < 1) {
			return 0;
		}
		byte b = s.getBytes()[0];
		return b;
	}

	/**
	 * 将长时间格式字符串转换为时间 yyyy-MM-dd HH:mm:ss
	 * 
	 * @param strDate
	 * @return
	 */
	public static Date strToDateLong(String strDate) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		ParsePosition pos = new ParsePosition(0);
		Date strtodate = formatter.parse(strDate, pos);
		return strtodate;
	}

	public static String getCurrTime() {
		Date now = new Date();
		SimpleDateFormat outFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		String s = outFormat.format(now);
		return s;
	}

	public static String formatDate(Date date, String format) {
		SimpleDateFormat outFormat = new SimpleDateFormat(format);
		return outFormat.format(date);
	}

	/**
	 * Formats a Date object to return a date using the global locale.
	 */
	public static String formatDate(Date date) {
		SimpleDateFormat outFormat = new SimpleDateFormat("yyyy-MM-dd");
		return outFormat.format(date);
	}

	/**
	 * 得到一个时间延后或前移几天的时间,nowdate为时间,delay为前移或后延的天数
	 */
	public static String getNextDay(Date d, int delay) {
		try {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			String mdate = "";
			long myTime = (d.getTime() / 1000) + delay * 24 * 60 * 60;
			d.setTime(myTime * 1000);
			mdate = format.format(d);
			return mdate;
		} catch (Exception e) {
			return "";
		}
	}

	public static Date longToDate(long time) {
		Date d = new Date();
		d.setTime(time);
		return d;
	}

	/**
	 * Formats a Date object to return a date and time using the global locale.
	 */
	public static String formatDateTime(Date date) {
		SimpleDateFormat outFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return outFormat.format(date);
	}

	public static String formatDate2(Date myDate) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
		String strDate = formatter.format(myDate);
		return strDate;
	}

	public static String formatDate3(Date myDate) {
		SimpleDateFormat formatter = new SimpleDateFormat("MM-dd HH:mm");
		String strDate = formatter.format(myDate);
		return strDate;
	}

	public static String formatDate4(Date myDate) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
		String strDate = formatter.format(myDate);
		return strDate;
	}

	public static String formatDate5(Date myDate) {
		String strDate = getYear(myDate) + "-" + getMonth(myDate) + "-" + getDay(myDate);
		return strDate;
	}

	public static String formatDate6(Date myDate) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		String strDate = formatter.format(myDate);
		return strDate;
	}

	public static long Date2Long(int year, int month, int date) {
		Calendar cld = Calendar.getInstance();
		month = month - 1;
		cld.set(year, month, date);
		return cld.getTime().getTime();
	}

	public static long Time2Long(int year, int month, int date, int hour, int minute, int second) {
		Calendar cld = Calendar.getInstance();
		month = month - 1;
		cld.set(year, month, date, hour, minute, second);
		return cld.getTime().getTime();
	}

	public static int getYear(long t) {
		Calendar cld = Calendar.getInstance();
		if (t > 0) {
			cld.setTime(new Date(t));
		}
		return cld.get(Calendar.YEAR);
	}

	public static int getMonth(long t) {
		Calendar cld = Calendar.getInstance();
		if (t > 0) {
			cld.setTime(new Date(t));
		}
		return cld.get(Calendar.MONTH) + 1;
	}

	public static int getDay(long t) {
		Calendar cld = Calendar.getInstance();
		if (t > 0) {
			cld.setTime(new Date(t));
		}
		return cld.get(Calendar.DAY_OF_MONTH);
	}

	public static int getHour(long t) {
		Calendar cld = Calendar.getInstance();
		if (t > 0) {
			cld.setTime(new Date(t));
		}
		return cld.get(Calendar.HOUR_OF_DAY);
	}

	public static int getMinute(long t) {
		Calendar cld = Calendar.getInstance();
		if (t > 0) {
			cld.setTime(new Date(t));
		}
		return cld.get(Calendar.MINUTE);
	}

	public static int getSecond(long t) {
		Calendar cld = Calendar.getInstance();
		if (t > 0) {
			cld.setTime(new Date(t));
		}
		return cld.get(Calendar.SECOND);
	}

	public static int getYear(Date date) {
		Calendar cld = Calendar.getInstance();
		cld.setTime(date);
		return cld.get(Calendar.YEAR);
	}

	public static int getMonth(Date date) {
		Calendar cld = Calendar.getInstance();
		cld.setTime(date);
		return cld.get(Calendar.MONTH) + 1;
	}

	public static int getDay(Date date) {
		Calendar cld = Calendar.getInstance();
		cld.setTime(date);
		return cld.get(Calendar.DAY_OF_MONTH);
	}

	public static int getHour(Date date) {
		Calendar cld = Calendar.getInstance();
		cld.setTime(date);
		return cld.get(Calendar.HOUR_OF_DAY);
	}

	public static int getMinute(Date date) {
		Calendar cld = Calendar.getInstance();
		cld.setTime(date);
		return cld.get(Calendar.MINUTE);
	}

	public static int getSecond(Date date) {
		Calendar cld = Calendar.getInstance();
		cld.setTime(date);
		return cld.get(Calendar.SECOND);
	}

	public static int getYear() {
		Calendar cld = Calendar.getInstance();
		cld.setTime(new Date());
		return cld.get(Calendar.YEAR);
	}

	public static int getMonth() {
		Calendar cld = Calendar.getInstance();
		cld.setTime(new Date());
		return cld.get(Calendar.MONTH) + 1;
	}

	public static int getDay() {
		Calendar cld = Calendar.getInstance();
		cld.setTime(new Date());
		return cld.get(Calendar.DAY_OF_MONTH);
	}

	public static String replaceComma(String text) {
		if (text != null) {
			text = text.replaceAll("，", ",");
		}
		return text;
	}

	public static String replaceBr(String text) {
		if (text != null) {
			text = text.replaceAll("\n", "<BR>");
		}
		return text;
	}

	public static long getLongTime() {
		return System.currentTimeMillis();
	}

	/**
	 * Check a string null or blank.
	 * 
	 * @param param
	 *            string to check
	 * @return boolean
	 */
	public static boolean nullOrBlank(String param) {
		return (param == null || param.length() == 0 || param.trim().toLowerCase().equals("null") || param.trim().equals("")) ? true : false;
	}

	public static boolean nullOrBlankequalsNull(String param) {
		return (param == null || param.length() == 0 || param.trim().equals("") || param.trim().toLowerCase().equals("null")) ? true : false;
	}

	public static String notNull(String param) {
		return param == null ? "" : param.trim();
	}

	/**
	 * Parse a string to boolean.
	 * 
	 * @param param
	 *            string to parse
	 * @return boolean value, if param begin with(1,y,Y,t,T) return true, on exception return false.
	 */
	public static boolean parseBoolean(String param) {
		if (nullOrBlank(param)) {
			return false;
		}
		switch (param.charAt(0)) {
		case '1':
		case 'y':
		case 'Y':
		case 't':
		case 'T':
			return true;
		}
		return false;
	}

	/**
	 * 取文件里的内容到字符串
	 * 
	 * @param f
	 * @return
	 * @throws Exception
	 */
	public static String readFile2Str(File f) throws Exception {
		StringBuffer sb = new StringBuffer();
		InputStreamReader read = new InputStreamReader(new FileInputStream(f), "GBK");
		BufferedReader reader = new BufferedReader(read);
		String line;
		while ((line = reader.readLine()) != null) {
			sb.append(line);
		}
		return sb.toString();
	}

	/**
	 * 去掉左右空格后字符串是否为空
	 */
	public static boolean isTrimEmpty(String astr) {
		if ((null == astr) || (astr.length() == 0)) {
			return true;
		}
		if (isBlank(astr.trim())) {
			return true;
		}
		return false;
	}

	/**
	 * 字符串是否为空:null或者长度为0.
	 */
	public static boolean isBlank(String astr) {
		if ((null == astr) || (astr.length() == 0)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * @方法描述：首字母小写
	 * @参数描述：
	 * @param str
	 * @return
	 */
	public static String capitalToLower(String str) {
		if (str == null || "".equals(str.trim()))
			return str;
		return String.valueOf(Character.toLowerCase(str.charAt(0))) + str.substring(1);
	}

	/**
	 * @方法描述：首字母大写
	 * @参数描述：
	 * @param str
	 * @return
	 */
	public static String capitalToUpper(String str) {
		if (str == null || "".equals(str.trim()))
			return str;
		return String.valueOf(Character.toUpperCase(str.charAt(0))) + str.substring(1);
	}

	public static void main(String[] args) {
		System.out.println(hash("admin"));

		System.out.println(isValid("01"));
	}
}
