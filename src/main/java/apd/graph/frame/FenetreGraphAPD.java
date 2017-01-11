package apd.graph.frame;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import org.graphstream.graph.Graph;
import org.graphstream.ui.swingViewer.View;
import org.graphstream.ui.swingViewer.Viewer;

import apd.graph.utilitaires.GraphAPD;

public class FenetreGraphAPD extends JFrame {
	
		private String nameFile;
		private GraphAPD graphAPD;
		
	    public FenetreGraphAPD() {

	    	createMenuBar();

	        setTitle("Graph APD");
	        Dimension dimMax = Toolkit.getDefaultToolkit().getScreenSize();
	        setMaximumSize(dimMax);

	        setExtendedState(JFrame.MAXIMIZED_BOTH);
	        setSize(1000, 600);
	        setLocationRelativeTo(null);
	        setLayout(new FlowLayout());
	        setDefaultCloseOperation(EXIT_ON_CLOSE);
	    }

	    private void createMenuBar() {

	        JMenuBar menubar = new JMenuBar();

	  
	        ImageIcon iconOpen = new ImageIcon("open.png");

	        JMenu fileMenu = new JMenu("File");
	        JMenu algos = new JMenu("Algorithms");
	        JMenu start = new JMenu("Start");
	        JMenu clear = new JMenu("Clear");
	        JMenuItem openMi = new JMenuItem("Open", iconOpen);
	        

	        JMenuItem exMi = new JMenuItem("Fichier d'exemple");
	        exMi.setToolTipText("Créer un graph par défaut");

	        fileMenu.add(openMi);
	        fileMenu.addSeparator();
	        fileMenu.add(exMi);
	        
	        menubar.add(fileMenu);
	        menubar.add(algos);
	        menubar.add(start);
	        menubar.add(Box.createHorizontalGlue());
	        menubar.add(clear);

	        setJMenuBar(menubar);
	        
	        JPanel pan=new JPanel();
	        pan.setBackground(Color.white);
	        pan.setLayout(new BorderLayout());
	     
	        Dimension dimMax = Toolkit.getDefaultToolkit().getScreenSize();
	        pan.setPreferredSize(new Dimension((int)dimMax.getWidth() - 100,(int)dimMax.getHeight() - 70));
	        pan.setBorder(BorderFactory.createLineBorder(Color.black));
	       
            
	       exMi.addActionListener((ActionEvent event) -> {
	        	this.nameFile="aim-100-1_6-no-1.cnf";
	        	this.graphAPD = new GraphAPD(nameFile);
	        	final Graph g = this.graphAPD.getGraph();

        		Viewer viewer = g.display();
        		viewer.enableAutoLayout();
                View view = viewer.addDefaultView(false);
                view.setBounds(0, 0, pan.getWidth(), pan.getHeight());
                pan.add(view, BorderLayout.CENTER);
                
                this.getContentPane().validate();
                this.getContentPane().repaint();
	        });
	        
	        openMi.addActionListener((ActionEvent event) -> {
	        	JFileChooser chooser = new JFileChooser();
	        	chooser.setApproveButtonText("Choix du fichier..."); 
	        	chooser.showOpenDialog(null); 
	        	if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION)
	            {	
	   
	        		nameFile = chooser.getSelectedFile().getName();
	        		this.graphAPD = new GraphAPD(nameFile);

	        		Viewer viewer = this.graphAPD.getGraph().display();
	        		viewer.enableAutoLayout();
	                View view = viewer.addDefaultView(false);
	              
	                pan.add(view, BorderLayout.CENTER);
	                this.getContentPane().validate();
	                this.getContentPane().repaint();
	                
	        	}
	        });
	        
	        
	        this.getContentPane().add(pan, BorderLayout.CENTER);
	      
	    }

	    public static void main(String[] args) {

	    	 SwingUtilities.invokeLater(new Runnable() {
	             public void run() {
	            	 FenetreGraphAPD ex = new FenetreGraphAPD();
	 	            ex.setVisible(true);
	             }
	         });
	
	    }
}