package apd.graph.frame;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;

import org.graphstream.graph.Graph;
import org.graphstream.ui.view.View;
import org.graphstream.ui.view.Viewer;

import apd.graph.utilitaires.Algorithm;
import apd.graph.utilitaires.EveilDistribueAlgorithm;
import apd.graph.utilitaires.GraphAPD;
import mpi.MPI;

public class FenetreGraphAPD extends JFrame {
	
		private String nameFile;
		private GraphAPD graphAPD;
		private Viewer viewer;
	    public FenetreGraphAPD(String[] args) {

	    	createMenuBar(args);

	        setTitle("Graph APD");
	        Dimension dimMax = Toolkit.getDefaultToolkit().getScreenSize();
	        setMaximumSize(dimMax);

	        setExtendedState(JFrame.MAXIMIZED_BOTH);
	        setSize(1000, 600);
	        setLocationRelativeTo(null);
	        setLayout(new FlowLayout());
	        setDefaultCloseOperation(EXIT_ON_CLOSE);
	    }

	    private void createMenuBar(String[] args) {

	        JMenuBar menubar = new JMenuBar();

	  
	        ImageIcon iconOpen = new ImageIcon("open.png");

	        JMenu fileMenu = new JMenu("File");
	        //JButton algos = new JButton("Algorithms");
	        //JButton start = new JButton("Start");
	        //JButton clear = new JButton("Clear");
	        JMenu algos = new JMenu("Algorithms");
	        JMenu initiators = new JMenu("Initiators");
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
	        menubar.add(initiators);
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

        		viewer = g.display();
        		viewer.enableAutoLayout();
                View view = viewer.addDefaultView(false);
                ((Component) view).setBounds(0, 0, pan.getWidth(), pan.getHeight());
                pan.add((Component) view, BorderLayout.CENTER);
                
                this.getContentPane().validate();
                this.getContentPane().repaint();
	        });
	        
	        openMi.addActionListener((ActionEvent event) -> {
	        	JFileChooser chooser = new JFileChooser();
	        	chooser.setApproveButtonText("Choix du fichier..."); 
	        	if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION)
	            {	
	   
	        		nameFile = chooser.getSelectedFile().getName();
	        		this.graphAPD = new GraphAPD(nameFile);

	        		viewer = this.graphAPD.getGraph().display();
	        		viewer.enableAutoLayout();
	                View view = viewer.addDefaultView(true);
	                
	              
	                pan.add((Component) view, BorderLayout.CENTER);
	                this.getContentPane().validate();
	                this.getContentPane().repaint();
	                
	        	}
	        });
	        
	        	
	        //start.addMenuListener(new SampleMenuListener());
	        start.addMenuListener(new MenuListener() {

	            @Override
	            public void menuSelected(MenuEvent e) {
	            	
					try {
						Algorithm a = new EveilDistribueAlgorithm(FenetreGraphAPD.this.graphAPD);
						a.start(args);
					} catch (InterruptedException e1) {
						
						e1.printStackTrace();
					}

	            }

	            @Override
	            public void menuDeselected(MenuEvent e) {
	                System.out.println("menuDeselected");

	            }

	            @Override
	            public void menuCanceled(MenuEvent e) {
	                System.out.println("menuCanceled");

	            }
	        });
	        
	        this.getContentPane().add(pan, BorderLayout.CENTER);
	      
	    }

	    /**
	     * @param args
	     */
	    public static void main(String[] args) {

	    /*	 SwingUtilities.invokeLater(new Runnable() {
	             public void run() {
	            	FenetreGraphAPD ex = new FenetreGraphAPD(args);
	 	            ex.setVisible(true);
	             }
	    
	         });*/
	    	MPI.Init(args);
	    	int rank = MPI.COMM_WORLD.Rank();
			int root = 0;
			FenetreGraphAPD ex= new FenetreGraphAPD(args);
			if(root==rank)
			{
				ex.setVisible(true);
			}
			MPI.Finalize();
	
	    }
}