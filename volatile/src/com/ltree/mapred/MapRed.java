package com.ltree.mapred;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Paint;
import java.awt.RenderingHints;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JWindow;

public class MapRed {
public static void main(String[] args) {
	JWindow window = new JWindow();
	//Datum datum = new Datum("<html>K<sub>1</sub>");
	JPanel panel = new JPanel();
	
//	panel.setLayout(new GridLayout(8,1,1,1));
//	for(int i=1; i<=8;i++){
//		panel.add(new KeyValue("<html>K<sub>"+i+"</sub>","<html>V<sub>"+i+"</sub>"));
//	}

	panel.setLayout(new GridLayout(2,1,1,1));
//	for(int i=0; i<=2;i++){
		panel.add(new KeyValue("<html>K<sub>33</sub>","<html>V<sub>33</sub>"));
		panel.add(new KeyValue("<html>K<sub>34</sub>","<html>V<sub>34</sub>"));
//		panel.add(new KeyValue("<html>K<sub>24</sub>","<html>V<sub>26</sub>"));
//		panel.add(new KeyValue("<html>K<sub>24</sub>","<html>V<sub>26</sub>"));

//	}	
	
	window.add(panel);
	window.pack();
	window.setLocation(300, 300);
	window.setVisible(true);
}
}

class Initial extends JFrame{
	
	
}

class KeyValue extends JPanel{
	public KeyValue(String key, String value){
		setLayout(new GridLayout(1,2));
		add(new Datum(key));
		add(new Datum(value));
	}
}

class Datum extends JButton{
	public Datum(String string) {
		super(string);
//		setBackground(Color.RED);
	}
	 public void paintComponent(Graphics g)  
	    {  
		 setContentAreaFilled(false);
	        Graphics2D g2 = (Graphics2D)g;  
	        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,  
	                            RenderingHints.VALUE_ANTIALIAS_ON);  
	        int width = getWidth();  
	        int height = getHeight();  
	        Color c = new Color(208, 125, 240);
	        Paint gradient = new GradientPaint(0, 0, Color.WHITE, 0, height, c);  
	        g2.setPaint(gradient);  
	        g2.fillRect(0, 0, width, height);  
	        super.paintComponent(g);  
	    }  
}