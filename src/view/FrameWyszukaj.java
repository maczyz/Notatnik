package view;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Document;
import javax.swing.text.JTextComponent;

import javax.swing.text.Highlighter;

import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class FrameWyszukaj extends JFrame 
{
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtSzukanyTekst;
	private JTextArea tekst;

	public FrameWyszukaj(final JTextArea _tekst) 
	{
		super("Wyszukiwanie");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 272, 199);
		contentPane = new JPanel();
		contentPane.setLayout(null);
		setContentPane(contentPane);
		
		tekst = _tekst;
		
		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 366, 168);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Co chcesz znalezc:");
		lblNewLabel.setBounds(10, 21, 114, 14);
		panel.add(lblNewLabel);
		
		txtSzukanyTekst = new JTextField();
		txtSzukanyTekst.setText("wpisz szukana fraze");
		txtSzukanyTekst.setBounds(10, 46, 229, 20);
		panel.add(txtSzukanyTekst);
		txtSzukanyTekst.setColumns(10);
		
		JButton btnNewButton = new JButton("Szukaj");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) 
			{
				highlight(_tekst, txtSzukanyTekst.getText());
			}
		});
		btnNewButton.setBounds(82, 116, 89, 23);
		panel.add(btnNewButton);
		
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}
	
	
	
	//Highlighter - An interface for an object that allows one to mark up the background with colored areas.
	Highlighter.HighlightPainter myHighlightPainter = new MyHighlightPainter(Color.red);
	
	
	public void removeHighlights(JTextComponent textComp)
	{
		Highlighter hilite = textComp.getHighlighter();
		Highlighter.Highlight[] hilities = hilite.getHighlights();
		
		for (int i = 0; i < hilities.length; i++)
		{
			if(hilities[i].getPainter() instanceof MyHighlightPainter)
			{
				hilite.removeHighlight(hilities[i]);
			}
		}
	}
	
	public void highlight(JTextComponent textComp, String pattern)
	{
		try
		{
			removeHighlights(textComp);
			Highlighter hilite = textComp.getHighlighter(); //Fetches (powoduje/przynosi/zwraca) the object responsible (odpowiedzialny) for making highlights.
			
			//Fetches (powoduje/przynosi/zwraca) the model associated (polaczony, powiazany) with the editor. This is primarily (glownie/przede wszystkim) for the UI to get at (siegnac po...) the minimal amount (liczba) of state required to be a text editor. Subclasses will return the actual type of the model which will typically be something that extends Document.
			//Returns: the model
			Document doc = textComp.getDocument();
			
			String text = doc.getText(0, doc.getLength());
			
			int pos = 0;
			
			while((pos = text.toUpperCase().indexOf(pattern.toUpperCase(),pos)) > 0)
			{
				hilite.addHighlight(pos, pos+pattern.length(), myHighlightPainter);
				pos += pattern.length();
			}
		}
		catch(Exception e)
		{
			
		}
	}
	
	
	
	class MyHighlightPainter extends DefaultHighlighter.DefaultHighlightPainter
	{
		public MyHighlightPainter(Color color)
		{
			super(color);
		}
		
		/*public void highlight(JTextComponent textComp, String pattern)
		{
			try
			{
				Highlighter hilite = textComp.getHighlighter();
				Document doc = textComp.getDocument();
				String text = doc.getText(0, doc.getLength());
				
				int pos = 0;
				
				while((pos = text.toUpperCase().indexOf(pattern.toUpperCase(),pos)) > 0)
				{
					hilite.addHighlight(pos, pos+pattern.length(), myHighlightPainter);
					pos += pattern.length();
				}
			}
			catch(Exception e)
			{
			}
		}*/
	
	}
}
