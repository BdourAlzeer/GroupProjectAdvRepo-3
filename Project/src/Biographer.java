//Yazan Alrayyes
//Feb 25, 2020
//this project displays biographical information of people upon search as well as their picture
//(data from Britannica & picture from Shutterstock)

import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.TextEvent;
import java.awt.event.TextListener;
import java.util.Scanner;
import java.awt.Image;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;

public class Biographer implements ActionListener, KeyListener {
	JButton enter, imagesOnly, next, previous;
	TextArea topicSearch;
	JTextArea searchResult, history;
	JFrame f, f2;
	Panel p1, p2, p3,p4;
	String search, name;
	JLabel label, logoImage;
	Image image, logo;
	String allSearches="You have searched for the following: \n";
	int index;
	public Biographer() { 
		//creating the frame
		enter = new JButton ("Search"); //search button
		enter.addActionListener(this);
		enter.setBackground(Color.WHITE);
		
		imagesOnly = new JButton ("Search for images only"); //search button for images only
		imagesOnly.addActionListener(this);
		imagesOnly.setBackground(Color.WHITE);
		
		previous = new JButton ("View previous searches"); //history button
		previous.addActionListener(this);
		previous.setBackground(Color.LIGHT_GRAY);
		
		
		topicSearch=new TextArea("Enter the name of the person you want to search"); //text area for search
		topicSearch.setSize(500, 500); //search area
		topicSearch.setBackground(Color.LIGHT_GRAY);
		topicSearch.addKeyListener(this);
		
		//creating and styling panels and frame
		p1= new Panel();
		p2= new Panel();
		p3= new Panel();
		p1.add(topicSearch);
		p2.add(enter);	
		p2.add(imagesOnly);
		p2.add(previous);
		f= new JFrame("Biographer");
		f.setSize(650, 700);
		f.setResizable(true);
		f.setFocusable(true);
		p1.setBackground(new Color(47,96,152));
		p2.setBackground(new Color(47,96,152));
		p3.setBackground(new Color(47,96,152));
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setLayout(new BorderLayout());
		//getting logo image
		try {
			logo = ImageIO.read(new URL("https://cdn.britannica.com/mendel/resources/eb-thistle-social-image.jpg"));
			logoImage = new JLabel(new ImageIcon(logo));
			p3.add(logoImage);
			//adding panels to frame
			f.add(p3, BorderLayout.BEFORE_FIRST_LINE);
			f.add(p1, BorderLayout.CENTER);
			f.add(p2, BorderLayout.AFTER_LAST_LINE);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		f.setVisible(true);	
		
		}
			
	public static void main(String[] args) {
		new Biographer();
	}
	//uses the data entered to search britannica.com
	public void search (String search) {
		allSearches+=search+", "; //adds name to search history
		String result="";
		name="";
		for (int i=0;i<search.length();i++) {
			if(search.charAt(i)==' ') {
				name+="-";
				continue;
			}
			name+=search.charAt(i);
		}
		
		//connecting to the website to retrieve info
		try {
			//connecting to page for specific search
			Document page = Jsoup.connect("https://www.britannica.com/biography/" + name).get();
			Element body = page.select("div#content").first();
			Element ps= body.select("p").get(1);
			result=ps.text();
			searchResult.setText(result); //displaying results
			}		
		 catch (IOException e) {
			 try {
				 Document page = Jsoup.connect("https://www.britannica.com/search?query=" + name).get();
				 Elements body = page.select("div#content");
				 Element ps= body.select("ul").select("a").get(0);
				 String correctedName=ps.text();
				 if (correctedName.indexOf("(")!=-1) {
					 correctedName=correctedName.substring(0, correctedName.indexOf("(")-1);
				 }
				 searchResult.setText("No Data Was Found on " + search +"\nDid you mean " + correctedName+"?");
			 }
			 catch (IOException i) {
				 searchResult.setText("No Data Was Found on " + search);
			 }
		}
		imageSearch(search, 0);
	}
	public void imageSearch(String search, int index) {
		allSearches+=search+" (images), "; //adds name to search history
		name="";
		for (int i=0;i<search.length();i++) {
			if(search.charAt(i)==' ') {
				name+="-";
				continue;
			}
			name+=search.charAt(i);
			
		}
		try {
			//searching Shutterstock for pictures
			image= null;
			Document page = Jsoup.connect("https://www.shutterstock.com/search/" + name).get();
			if (page.getElementsByTag("img").size()!=0) {
				Element img = page.getElementsByTag("img").get(index);
			    URL src = new URL(img.attr("abs:src"));
			    image= ImageIO.read(src);
			}	
		}
		 catch (IOException e) {
		}
		
	}
	
	public void executeSearch() {
		//creates the results frame
		search=topicSearch.getText();
		searchResult= new JTextArea();
		searchResult.setLineWrap(true);
		searchResult.setWrapStyleWord(true);
		searchResult.setEditable(false);
		searchResult.setFont(new Font("Times New Roman", Font.BOLD, 20));
		searchResult.setBackground(Color.lightGray);
		f2= new JFrame("Search Results");
		f2.setSize(600, 650);
		f2.setResizable(true);
		f2.setFocusable(true);
		f2.setVisible(true);
		f2.add(searchResult, BorderLayout.CENTER);
		//executes and displays results
		search(search);
		if (image!=null) {
			label = new JLabel(new ImageIcon(image));
			f2.add(label, BorderLayout.BEFORE_FIRST_LINE);
		}
	}
	public void executeImageSearch(int n) {
		//executes the search for image only search
		//creates the results frame
		search=topicSearch.getText();
		f2= new JFrame("Search Results");
		f2.setSize(600, 550);
		f2.setResizable(true);
		f2.setFocusable(true);
		f2.setVisible(true);
		f2.setLayout(new BorderLayout());
		next = new JButton ("Next Image"); //search button
		next.addActionListener(this);
		next.setBackground(Color.WHITE);
		//executes and displays results
		imageSearch(search, n);
		if (image!=null) {
			label = new JLabel(new ImageIcon(image));
			f2.add(label, BorderLayout.BEFORE_FIRST_LINE);
			f2.add(next, BorderLayout.SOUTH);
		}
	}
	//views the search history of user
	public void viewHistory() {
		history= new JTextArea();
		history.setLineWrap(true);
		history.setWrapStyleWord(true);
		history.setEditable(false);
		history.setFont(new Font("Times New Roman", Font.BOLD, 20));
		history.setBackground(Color.lightGray);
		history.setText(allSearches);
		f2= new JFrame("Search Results");
		f2.setSize(600, 650);
		f2.setResizable(true);
		f2.setFocusable(true);
		f2.setVisible(true);
		f2.add(history, BorderLayout.CENTER);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		//executes search when search button is pressed
		if (e.getSource()==enter) 
			executeSearch();
		if (e.getSource()==imagesOnly) {
			index=0;
			executeImageSearch(index);
		}
		if (e.getSource()==next) {
			f2.dispose();
			index+=1;
			executeImageSearch(index);
		}
		if(e.getSource()==previous) {
			viewHistory();
		}
	}
	@Override
	public void keyTyped(KeyEvent e) {
		//executes search when the enter key is pressed
		if (e.getKeyChar()==KeyEvent.VK_ENTER) 
			executeSearch();
	}
	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	}
