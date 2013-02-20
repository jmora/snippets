package com.github.jmora.snippets.unicornPatter;

public class Unicorn {
	private static int lastCallLine = 0;

	public static boolean pat() {
		int currentCallLine = Thread.currentThread().getStackTrace()[2].getLineNumber();
		if (Unicorn.lastCallLine == currentCallLine)
			return false;
		Unicorn.lastCallLine = currentCallLine;
		return true;
	}
}
