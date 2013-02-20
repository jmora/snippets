package com.github.jmora.snippets.unicornPatter;

public class MagicalLand {
	public static void main(String[] args) {
		for (int i = 0; i < Math.random() * 500 + 2; i++)
			if (Unicorn.pat())
				System.out.println("UNICORN #1: PAT THIS UNICORN ONCE");

		for (int i = 0; i < Math.random() * 500 + 2; i++)
			if (Unicorn.pat())
				System.out.println("UNICORN #2: PAT THIS UNICORN ONCE");
		System.out.println("END OF PROGRAM");
	}
}
