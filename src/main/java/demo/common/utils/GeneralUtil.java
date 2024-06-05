package demo.common.utils;

import demo.web.filter.XSSRequestWrapper;

public class GeneralUtil {
	
	/**
	 * @param inputString
	 * @return initialize null or empty string to " "
	 */
	public static String initBlankString(String inputString) {
		if (inputString == null || inputString.isEmpty()) {
			return " ";
		}
		return XSSRequestWrapper.stripXSS(inputString);
	}

}
