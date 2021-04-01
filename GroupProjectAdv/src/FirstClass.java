// khaled
import java.awt.Button;
import java.awt.Label;
import java.awt.Panel;
import java.awt.TextField;

import javax.swing.JFrame;

public class FirstClass {

	public static void main(String[] args) {
		TextField tf1,tf2,tf3;  
		Label l,l2,l3;
	    Button[] numbers = new Button[10] ; 
	    Button b1,b2,b3,b4,b5;
	    JFrame f;
		Panel p;
		Panel p1;
		Panel p2;
		
		 tf1=new TextField();  
	        tf1.setText("Enter First Number");
	        tf1.setBounds(50,50,150,20);  
	        
	        tf2=new TextField();  
	        tf2.setText("Enter Second Number");
	        tf2.setBounds(50,100,150,20);  
	        
	        tf3=new TextField();  
	        tf3.setText("Results");
	        tf3.setBounds(50,250,100,50);  
	        tf3.setEditable(false); 
	}

}