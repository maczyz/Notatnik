package funkcje;


import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

/**
 * Javadoc comments
 */
public class EncryptNotepad
{
	private String key;
	private FileInputStream fis;
	private FileOutputStream fos;
	
	public EncryptNotepad(String password)
	{
		try 
		{
			setKey(funkcje.UsedMethods.GET_HashMD5(password)); // needs to be at least 8 characters for DES

		} 
		catch (Throwable e) {
			e.printStackTrace();
		}
	}
	
	public void setFileStream(String whichNotepad, String readOrWrite)
	{
		try
		{   
			if (readOrWrite.equals("read"))
			{
				setFis(new FileInputStream(whichNotepad+"-encrypted.txt"));
				//setFos(new FileOutputStream(whichNotepad+"-decrypted.txt"));
			}
			else
			{
				//setFis(new FileInputStream(whichNotepad+"-decrypted.txt"));
				setFos(new FileOutputStream(whichNotepad+"-encrypted.txt"));
			}
		}
		catch (Throwable e)
		{
			e.printStackTrace();
		}
	}
	
	
	public void encrypt(ArrayList<String> _fileContent, String _whichFile) throws Throwable 
	{
		DESKeySpec dks = new DESKeySpec(key.getBytes());
		SecretKeyFactory skf = SecretKeyFactory.getInstance("DES");
		SecretKey desKey = skf.generateSecret(dks);
		Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding"); 
		
		cipher.init(Cipher.ENCRYPT_MODE, desKey);
		String preparedTextWithNewlineChar = funkcje.UsedMethods.przygotujTekstDoZapisu(_fileContent);
		byte[] decryptedContent = preparedTextWithNewlineChar.getBytes("UTF-8");
		byte[] encryptedContent =  cipher.doFinal(decryptedContent);
		
        FileOutputStream keyfos = new FileOutputStream(_whichFile + "-encrypted.txt");
        keyfos.write(encryptedContent);
        keyfos.close();
	}
	
	
	public String decrypt(String _whichFile) throws Throwable 
	{
		DESKeySpec dks = new DESKeySpec(key.getBytes());
		SecretKeyFactory skf = SecretKeyFactory.getInstance("DES");
		SecretKey desKey = skf.generateSecret(dks);
		
		FileInputStream encryptedTextFis = new FileInputStream(_whichFile + "-encrypted.txt");
	    byte[] encText = new byte[encryptedTextFis.available()];
	    encryptedTextFis.read(encText);
	    encryptedTextFis.close();
		
	    Cipher decrypter = Cipher.getInstance("DES/ECB/PKCS5Padding");
	    decrypter.init(Cipher.DECRYPT_MODE, desKey);
	    byte[] decryptedText = decrypter.doFinal(encText);

		return new String(decryptedText, "UTF-8");
	}
	


	public final String getKey() {
		return key;
	}


	public void setKey(String key) {
		this.key = key;
	}


	public FileInputStream getFis() {
		return fis;
	}


	public void setFis(FileInputStream fis) {
		this.fis = fis;
	}


	public FileOutputStream getFos() {
		return fos;
	}


	public void setFos(FileOutputStream fos) {
		this.fos = fos;
	}

}
