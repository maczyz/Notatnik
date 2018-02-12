package view;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import funkcje.*;


/**klasa Logowanie dziedziczy po klasie JFrame
**
* czyli dziedziczy/korzysta z jej metod
* */
public class Logowanie extends JFrame 								  
{
	private JButton button; // deklaracja zmiennych okreslonych klas
	private JLabel haslo;
	private JPasswordField JPpassword; // JPasswordField - dzieki tej klasie zamiast znaków widac kropki
	
	private static final long serialVersionUID = 1L;
	EncryptNotepad encryptObject;

	public Logowanie(String napis) // konstruktor jedno-parametrowy
	{		
		super(napis); 
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setSize(600,300);
		setLayout(null);
		setBackground(Color.white);
				
		//dodanie Buttona
		button = new JButton("Zaloguj"); // zmiennej button przypisujemy referencje do obiektu klasy JButton
		button.setBounds(250, 150, 100, 30); // (x, y, szerokosc, wysokosc)
		
		// button.addMouseListener - dodanie obiektu nasluchujacego zdarzenia (klikniecia myszy) dla buttona
		// new MouseAdapter(){ ... }; - tworzenie obiektu tylko na potrzeby nasluchiwacza zdarzen,
		//                              który jest reprezentantem wewnetrznej klasy anonimowej
		button.addMouseListener(new MouseAdapter()
		{
			@SuppressWarnings("deprecation")
			@Override
			public void mouseClicked(MouseEvent e) 
			{
				try 
				{
					encryptObject = new EncryptNotepad(JPpassword.getText());
					new Notatnik("Notatnik",encryptObject);
					dispose();
				} 
				catch (Throwable e1) 
				{
					e1.printStackTrace();
				}
			}
		});
		
		this.add(button); // dodanie buttona do ramki (JFrame), slowo "this" odnosi sie do obecnej klasy
				
		//dodanie labela
		haslo = new JLabel("Podaj haslo: ");
		haslo.setBounds(260, 70, 150, 30);
		this.add(haslo);
		
		//dodanie textfielda
		JPpassword = new JPasswordField();
		JPpassword.setBounds(420, 70, 150, 20);
		JPpassword.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mouseClicked(MouseEvent e) {
				JPpassword.setText("");
			}
		});
		
		
		this.add(JPpassword);
		
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}
	
	
}
