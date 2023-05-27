package tutoSwing;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics; 
import java.awt.GradientPaint; 
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;


public class Bouton extends JButton implements MouseListener { 
	private String name;
	private Color couleur;   
	private Color couleur1; 
	
	public Bouton(String str){ 
		super(str); this.name = str;
		this.couleur = Color.PINK;  
		this.couleur1 = Color.blue;  
		this.addMouseListener(this);
		
	}
	
	
	public void paintComponent(Graphics g){ 
		
		
		Graphics2D g2d = (Graphics2D)g;
		GradientPaint gp = new GradientPaint(0, 0, couleur1, 0, 20,couleur, true); 
		g2d.setPaint(gp);
		g2d.fillRect(0, 0, this.getWidth(), this.getHeight());
		g2d.setColor(couleur);
		g2d.drawString(this.name, this.getWidth() / 2 -17, (this.getHeight() / 2) + 5); }
	
	    
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		couleur = Color.BLACK;
	}
	
	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
		if((e.getX() >0 && e.getX() < this.getWidth()) && (e.getY() >0 && e.getY() < this.getHeight() ))
			couleur = Color.cyan;
		else
			couleur = Color.pink;
		
	}
	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		couleur = Color.cyan;
	}
	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		couleur = Color.pink;

	}
}