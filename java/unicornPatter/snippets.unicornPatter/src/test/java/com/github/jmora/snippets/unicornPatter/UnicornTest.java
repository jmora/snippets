package com.github.jmora.snippets.unicornPatter;

import java.util.ArrayList;
import junit.framework.TestCase;
import org.junit.Test;


public class UnicornTest extends TestCase {
	@Test
	public void testUnicorn() {
		int i;
		ArrayList<Boolean> res = new ArrayList<Boolean>();
		for (i = 0; i < 15; i++)
			res.add(Unicorn.pat());
		TestCase.assertTrue(res.get(0));
		for (i = 1; i < res.size(); i++)
			TestCase.assertFalse(res.get(i));
		res = new ArrayList<Boolean>();
		for (i = 0; i < 25; i++)
			res.add(Unicorn.pat());
		TestCase.assertTrue(res.get(0));
		for (i = 1; i < res.size(); i++)
			TestCase.assertFalse(res.get(i));
	}
}
