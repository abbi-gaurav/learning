package encoding;

import java.util.Locale;

public class AlloMonde {
	public static void main(String[] args) {
		String string = "All\u00F4 monde!";
		System.out.println(string);

		System.out.println("All\u00F4 mon\u005C\u006Ede!");

		System.out.println("All\u00F4 mon\12Ede!");

		System.out.println("All\u00F4 mon\nde!");

		System.out.println(0x10FFFF);

		System.out.println(ucLength(string));
		
		System.out.println(ucCharAt(string, 4));
		
		capital();
		/*
		 * int count = 0; for(char c =Character.MIN_VALUE; c <= 65535;c++){
		 * if(Character.isJavaIdentifierStart(c)){ count++; } }
		 * 
		 * System.out.println(count);
		 */
	}

	public static int ucLength(String s) {
		return s.codePointCount(0, s.length());
	}

	public static int ucCharAt(String s, int index) {
		int iPos = s.offsetByCodePoints(0, index);
		return s.codePointAt(iPos);
	}
	
	public static void capital(){
		Locale de_DE = new Locale( "de", "DE" );
		String wort = "Straße";
		System.out.println( "word = " + wort );
		String WORT = wort.toUpperCase( de_DE );
		System.out.println( "WORT = " + WORT );
	}
}