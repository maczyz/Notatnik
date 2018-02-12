package view;

import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.event.WindowStateListener;
import java.io.File;
import java.util.ArrayList;

import javax.swing.AbstractAction;
import javax.swing.Box;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.ScrollPaneConstants;

import funkcje.EncryptNotepad;

public class Notatnik extends JFrame implements ActionListener, WindowListener, WindowStateListener {

    private static final long serialVersionUID = 1L;

    private final JTextArea areaObszar; // obszar pisania tekstu
    private final JScrollPane scrollPane; // suwaki
    private String whatOpened_Flag = "";

    JPanel myPanel;

    // deklarowanie zmiennych odpowiadaj¹cych za istnienie i dzia³anie menu
    JMenuBar menuBar;
    JMenu menuPlik, menuPomoc, menuOtworz, menuOpcje;
    JMenuItem mplZapisz, mplZamknij, mpoOautorach, otworzFirmowe, otworzOsobiste, mplwyszukaj;

    private final EncryptNotepad encryptObject;
    private final ArrayList<String> allLinesOfNotatnik;

    public Notatnik(String napis, EncryptNotepad encryptObject) // konstruktor klasy Notatnik
    {
        super(napis);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setResizable(true);
        setSize(600, 300);
        getContentPane().setLayout(null);

        myPanel = new JPanel();
        myPanel.setLayout(null);
        myPanel.setSize(Notatnik.WIDTH, Notatnik.HEIGHT);
        myPanel.setVisible(true);

        menuBar = new JMenuBar(); // G³ówny pasek menu zawieraj¹cy opcje : Plik, Pomoc

        menuPlik = new JMenu("Plik"); // Menu Plik i jego podmenu : Otwórz, Zapisz, Zamknij program
        menuOpcje = new JMenu("Opcje");

        menuOtworz = new JMenu("Otwórz");
        otworzOsobiste = new JMenuItem("otworz Osobiste");
        otworzFirmowe = new JMenuItem("otworz Firmowe");
        menuOtworz.add(otworzFirmowe);
        menuOtworz.add(otworzOsobiste);

        otworzFirmowe.addActionListener(this);
        otworzOsobiste.addActionListener(this);

        menuPlik.add(menuOtworz);

        mplZapisz = new JMenuItem("Zapisz", 'Z'); // zapis w postaci 'O' to tzw MEMONIC
                                                  // podkreœla wybran¹ literê; naciskaj¹c j¹ wybrana zostaje dana opcja z menu
        mplZapisz.addActionListener(this);

        mplZamknij = new JMenuItem("Zamknij program");
        mplZamknij.addActionListener(this);
        mplZamknij.setAccelerator(KeyStroke.getKeyStroke("ctrl X")); // skrót klawiaturowy, tzw akcelerator

        setJMenuBar(menuBar); // - setJMenuBar dodaje menuBar, nie add(menuBar)

        menuPomoc = new JMenu("Pomoc");
        mpoOautorach = new JMenuItem("O autorach");
        mpoOautorach.addActionListener(this);

        menuBar.add(menuPlik);
        menuPlik.add(mplZapisz);
        menuPlik.addSeparator(); // separator oddziela wybrane przez nas pozycje menu
        menuPlik.add(mplZamknij);

        // Menu Opcje
        menuBar.add(menuOpcje);
        mplwyszukaj = new JMenuItem("Wyszukaj");
        mplwyszukaj.addActionListener(this);
        menuOpcje.add(mplwyszukaj);

        menuBar.add(Box.createHorizontalGlue()); // ustawia menu POMOC po prawej stronie
        menuBar.add(menuPomoc);
        menuPomoc.add(mpoOautorach);

        // dodanie nasluchiwacza zdarzen do JFrame
        this.addWindowListener(this);

        areaObszar = new JTextArea();
        areaObszar.setWrapStyleWord(true);
        areaObszar.setLineWrap(true);

        scrollPane = new JScrollPane(areaObszar); // od teraz obszar pisania bêdzie siê mieœci³ miêdzy suwakami
        scrollPane.setBounds(0, 0, this.getWidth(), this.getHeight());
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        // scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollPane.setVisible(true);
        myPanel.add(scrollPane);

        // Sets the location of the window relative to the specified component according to the following scenarios
        this.setLocationRelativeTo(null);

        // This is the method of the JComponent class which returns the input map when the component is focused.
        myPanel.getInputMap().put(KeyStroke.getKeyStroke("control F"), "pokazOknoWyszukiwania");

        myPanel.getActionMap().put("pokazOknoWyszukiwania", new AbstractAction("pokazOknoWyszukiwania") {
            private static final long serialVersionUID = 1L;

            @Override
            public void actionPerformed(ActionEvent evt) {
                new FrameWyszukaj(areaObszar);
            }
        });

        // dodanie JPanel do JFrame
        this.setContentPane(myPanel);
        this.setVisible(true);
        this.addWindowStateListener(this);

        // sk³adnia ta pozwala zmieniæ wielkoœæ wybranego komponentu
        this.getContentPane().addComponentListener(new java.awt.event.ComponentAdapter() {
            @Override
            public void componentResized(java.awt.event.ComponentEvent evt) {
                zmienWielkoscAreaObszar(); // zmiana wielkoœci obszaru pisania tekstu
            }
        });

        this.encryptObject = encryptObject;
        allLinesOfNotatnik = new ArrayList<String>();
    }

    private void zmienWielkoscAreaObszar() // dzialanie metody zmienWielkoscAreaObszar()
    {
        scrollPane.setBounds(0, 0, this.getWidth() - 15, this.getHeight() - 60);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object z = e.getSource();
        if (z == mplZamknij) {
            int odp = JOptionPane.showConfirmDialog(this, "Czy na pewno chcesz zamknac program ?", "Wyjscie z programu", JOptionPane.YES_NO_OPTION);
            if (odp == JOptionPane.YES_OPTION) {
                dispose();
            }
        }
        else if (z == mpoOautorach) {
            JOptionPane.showMessageDialog(this, "Debesciacy : Mateusz Czyz oraz Grzegorz Krzeminski");
        }
        else if (z.equals(mplZapisz)) {
            try {
                encryptObject.encrypt(this.readFromTextArea(), whatOpened_Flag);
            } catch (Throwable e1) {
                e1.printStackTrace();
            }
        }
        else if (z.equals(otworzOsobiste)) {
            try {
                whatOpened_Flag = "osobiste";
                areaObszar.setText("");
                String decryptedText = encryptObject.decrypt(whatOpened_Flag);
                // allLinesOfNotatnik = funkcje.UsedMethods.przygotujTekstDoWyswietlenia(decryptedText);
                areaObszar.append(decryptedText);
            } catch (Throwable e1) {
                JOptionPane.showMessageDialog(null, "Haslo podane przy logowaniu jest bledne. Wyloguj sie i zaloguj ponownie z poprawnym haslem",
                        "Niewlasciwe haslo", JOptionPane.INFORMATION_MESSAGE);
            }
        }
        else if (z.equals(otworzFirmowe)) {
            try {
                whatOpened_Flag = "firmowe";
                areaObszar.setText("");
                String decryptedText = encryptObject.decrypt(whatOpened_Flag);
                // allLinesOfNotatnik = funkcje.UsedMethods.przygotujTekstDoWyswietlenia(decryptedText);
                areaObszar.append(decryptedText);
            } catch (Throwable e1) {
                JOptionPane.showMessageDialog(null, "Haslo podane przy logowaniu jest bledne. Wyloguj sie i zaloguj ponownie z poprawnym haslem",
                        "Niewlasciwe haslo", JOptionPane.INFORMATION_MESSAGE);
            }
        }
        else if (z.equals(mplwyszukaj)) {
            new FrameWyszukaj(areaObszar);
        }
    }

    private ArrayList<String> readFromTextArea() {
        String[] linesfromAreaObszar;
        linesfromAreaObszar = areaObszar.getText().split("\\n");

        allLinesOfNotatnik.clear();
        for (String s : linesfromAreaObszar) {
            allLinesOfNotatnik.add(s);
        }
        return allLinesOfNotatnik;
    }

    @Override
    public void windowActivated(WindowEvent arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public void windowClosed(WindowEvent arg0) {
    }

    @Override
    public void windowClosing(WindowEvent arg0) {
        int odp = JOptionPane.showConfirmDialog(this, "Czy na pewno chcesz zamknac program ?", "Wyjscie z programu", JOptionPane.YES_NO_OPTION);
        if (odp == JOptionPane.YES_OPTION) {
            File files = new File("firmowe-decrypted.txt");
            files.delete();

            new File("osobiste-decrypted.txt").delete();

            dispose(); // jeœli po "IF" jest tylko jedna linijka kodu, jedna komenda, nie trzeba nawiasów
        }
    }

    @Override
    public void windowDeactivated(WindowEvent arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public void windowDeiconified(WindowEvent arg0) {
    }

    @Override
    public void windowIconified(WindowEvent arg0) {
    }

    @Override
    public void windowOpened(WindowEvent arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public void windowStateChanged(WindowEvent e) {
        if (e.getNewState() == Frame.MAXIMIZED_BOTH) {
            zmienWielkoscAreaObszar();
        }
    }
}