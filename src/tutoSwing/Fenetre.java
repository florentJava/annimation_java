package tutoSwing;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ButtonGroup;
import javax.swing.JCheckBox;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.KeyStroke;

public class Fenetre extends JFrame  {


	private JMenuBar menuBar = new JMenuBar();
	private JMenu menuAnnimation = new JMenu("Annimation");
	private JMenu menuForme = new JMenu("Forme");
	private JMenu menuPropos = new JMenu("A propos ??");

	private JMenuItem itemLancer = new JMenuItem("lancer");
	private JMenuItem itemArreter = new JMenuItem("Arreter");
	private JMenuItem itemfermer = new JMenuItem("Fermer");
	private JMenuItem itemPropos = new JMenuItem("A propos");

	private JCheckBoxMenuItem morph = new JCheckBoxMenuItem("Morphing");
	private JRadioButtonMenuItem carre = new JRadioButtonMenuItem("CARRE"), 
			rond = new JRadioButtonMenuItem("ROND"), 
			triangle = new JRadioButtonMenuItem("TRIANGLE"), 
			etoile = new JRadioButtonMenuItem("ETOILE");

	private ButtonGroup bg = new ButtonGroup();
	private JMenu typeForme = new JMenu("type forme");



	private Panneau pan = new Panneau();
	private int x, y;
	private boolean backX, backY;


	private Panneau panelTop = new Panneau();
	private JPanel containers = new JPanel();
	private JPanel panBouton = new JPanel();
	private JCheckBox morphi = new JCheckBox("Morphing");

	private Thread t;
	private int x1=this.getWidth()/2,x2=this.getHeight()/2;
	boolean animated = false;
	
	//La déclaration pour le menu contextuel private
	JPopupMenu jpm = new JPopupMenu();
	private JMenu background = new JMenu("Couleur de fond"); 
	private JMenu couleur = new JMenu("Couleur de la forme");
	private JMenuItem launch = new JMenuItem("Lancer l'animation");
	private JMenuItem stop = new JMenuItem("Arrêter l'animation");
	private JMenuItem rouge = new JMenuItem("Rouge"), bleu = new JMenuItem("Bleu"), vert = new JMenuItem("Vert"), rougeBack = new JMenuItem("Rouge"), bleuBack = new JMenuItem("Bleu"),
	vertBack = new JMenuItem("Vert");
	
	public Fenetre(){ 
		this.setTitle("Animation"); 
		this.setSize(300, 300); 
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
		this.setLocationRelativeTo(null);

		this.menuBar.add(menuAnnimation); 
		this.menuBar.add(menuForme);
		this.menuBar.add(menuPropos);


		this.setJMenuBar(menuBar);


		
		rond.addActionListener(new FormeListener());
		carre.addActionListener(new FormeListener());
		etoile.addActionListener(new FormeListener());
		triangle.addActionListener(new FormeListener());

		
		morph.addActionListener(new MorphListener());

		containers.setLayout(new BorderLayout());
		containers.add(panelTop,BorderLayout.NORTH);
		containers.add(panBouton, BorderLayout.SOUTH);
		containers.add(pan,BorderLayout.CENTER);


		//Nous ajoutons notre fenêtre à la liste des auditeurs de notre bouton
		itemLancer.addActionListener(new BoutonListener()); 
		itemArreter.addActionListener(new Bouton2Listener());
		
		launch.addActionListener(new BoutonListener()); 
		stop.addActionListener(new Bouton2Listener());

		pan.addMouseListener(new MouseAdapter() {
			public void mouseReleased(MouseEvent event){ 
				//Seulement s'il s'agit d'un clic droit 
				//if(event.getButton() == MouseEvent.BUTTON3)
				
				if(event.isPopupTrigger()){ 
					background.add(rougeBack);
					background.add(bleuBack);
					background.add(vertBack);
					
					stop.setEnabled(false);
					couleur.add(rouge);
					couleur.add(bleu);
					couleur.add(vert);
					jpm.add(launch);
					jpm.add(stop);
					jpm.add(couleur);
					jpm.add(background);
					
					//La méthode qui va afficher le menu
					jpm.show(pan, event.getX(), event.getY());
				}
			}});

		initMenu();
		this.setContentPane(containers);
		this.setVisible(true);
		this.go();

	}



	private void initMenu(){
		itemLancer.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, KeyEvent.CTRL_MASK));
		menuAnnimation.add(itemLancer);		
		
		itemArreter.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, KeyEvent.CTRL_DOWN_MASK + KeyEvent.SHIFT_DOWN_MASK));
		//Menu animation 
		menuAnnimation.add(itemLancer);
		itemArreter.setEnabled(false); 
		menuAnnimation.add(itemArreter);
		menuAnnimation.addSeparator(); 

		//Pour quitter l'application 
		itemfermer.addActionListener(new ActionListener(){ public void actionPerformed(ActionEvent event){ System.exit(0);}	});
		
		itemfermer.setMnemonic('F');
		menuAnnimation.add(itemfermer);

		//Menu forme
		bg.add(carre); 
		bg.add(triangle); 
		bg.add(rond); 
		bg.add(etoile);

		typeForme.add(rond); typeForme.add(carre); typeForme.add(triangle); typeForme.add(etoile);
		rond.setSelected(true);

		menuForme.add(typeForme); menuForme.add(morph);

		//Menu À propos 
		menuPropos.add(itemPropos);
		//Ajout des menus dans la barre de menus
		menuBar.add(menuAnnimation); menuBar.add(menuForme); menuBar.add(menuPropos);
		//Ajout de la barre de menus sur la fenêtre 
		
		menuAnnimation.setMnemonic('A');
		menuBar.add(menuAnnimation);
		
		menuForme.setMnemonic('F');
		menuBar.add(menuForme);
		
		menuPropos.setMnemonic('P');
		menuBar.add(menuPropos);
		
		this.setJMenuBar(menuBar);
	}


	private void go(){
		x = pan.getPosX();
		y = pan.getPosY();
		while(this.animated){
			//Si le mode morphing est activé, on utilise la taille actuellede la forme

			if(pan.isMorph()){
				if(x < 1)backX = false;
				if(x > pan.getWidth() - pan.getDrawSize()) backX = true;
				if(y < 1)backY = false;
				if(y > pan.getHeight() - pan.getDrawSize()) backY = true;
			}
			//Sinon, on fait comme d'habitude
			else{
				if(x < 1)backX = false;
				if(x > pan.getWidth()-50) backX = true;
				if(y < 1)backY = false;
				if(y > pan.getHeight()-50) backY = true;
			}
			if(!backX) pan.setPosX(++x);

			else pan.setPosX(--x);
			if(!backY) pan.setPosY(++y);
			else pan.setPosY(--y);
			pan.repaint();
			try {
				Thread.sleep(3);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public class BoutonListener implements ActionListener{
		public void actionPerformed(ActionEvent arg0) {
			animated = true;
			t = new Thread(new PlayAnimation());
			t.start();
			
			itemLancer.setEnabled(false);
			launch.setEnabled(false);

			itemArreter.setEnabled(true);
			stop.setEnabled(true);

			
		}
	}
	class Bouton2Listener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			animated = false;
			
			itemLancer.setEnabled(true);
			launch.setEnabled(true);
			
			itemArreter.setEnabled(false);
			stop.setEnabled(false);

			
		}
	}
	
	class PlayAnimation implements Runnable{
		public void run() {
			go();
		}
	}
	
	class FormeListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			pan.setForme(((JRadioButtonMenuItem)e.getSource()).getText());
			System.out.println(((JRadioButtonMenuItem)e.getSource()).getText());
		}
	}
	class MorphListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			//Si la case est cochée, on active le mode morphing
			if(morph.isSelected())pan.setMorph(true);
			//Sinon, on ne fait rien
			else pan.setMorph(false);
		}
	}
}
