package funkcje;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.math.BigInteger;


public class UsedMethods 
{
	
	
	static public String GET_HashMD5(String myPass)
	{
		MessageDigest m;
		try 
		{
			m = MessageDigest.getInstance("MD5");
			m.update(myPass.getBytes(),0,myPass.length());
		    return new BigInteger(1,m.digest()).toString(16);
		} 
		
		catch (NoSuchAlgorithmException e) 
		{
			e.printStackTrace();
			return "Error";
		}
		
    }
	
	
	public static ArrayList<String> przygotujTekstDoWyswietlenia(String tekst)
	{
		//zmienna tekst ma postac tekst = [item1, item2, item3]
		//przecinek jest znakiem nowej pozycji w tworzonej liscie ArrayList
		
		tekst = tekst.substring(1, tekst.length()-1); // usuwamy znak [ i ] na poczatku i koncu Stringa
		ArrayList<String> items = new  ArrayList<String>(Arrays.asList(tekst.split("\n")));
		return items;
	}
	
	public static String przygotujTekstDoZapisu(ArrayList<String> tekst)
	{
		StringBuilder strB = new StringBuilder();
		for(String item : tekst)
		{
			strB.append(item);
			strB.append("\n");
		}
		return strB.toString();
	}
	

}
