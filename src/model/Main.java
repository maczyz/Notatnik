package model;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import view.Logowanie;

public class Main 
{
	public static void main(String[] args) 
	{
		System.out.println(new Integer(10));
		SwingUtilities.invokeLater(new Runnable()
		{
			public void run()
			{
				try {
					UIManager.setLookAndFeel(
					        "com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InstantiationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (UnsupportedLookAndFeelException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				new Logowanie("Logowanie"); // tworzenie nowego obiektu, reprezentanta klasy Logowanie
			}
		});
	}
}
