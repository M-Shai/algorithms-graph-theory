package prog340;

import javax.swing.*;

import delivery.DelivA;
import delivery.DelivB;
import delivery.DelivC;
import delivery.DelivD;
import graph.Graph;
import edge.Edge;
import node.Node;
import node.HeurNode;                ////// This is my change, added import here, see line 200.

import java.io.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;

/** ProgramA simply reads a file containing rows of space-separated Strings, 
 ** your assignment is to print out those strings interpreted as a graph.
 **
 ** @author
 **    Mike Stein
**/

public class Prog340 extends JPanel /*implements ActionListener*/ {
	
	private static final long serialVersionUID = 1L;  // Keep Eclipse happy.
	File inputFile;
	File outputFile;
	PrintWriter output;
	JFileChooser fileChooser;
	Graph g, gh;
	JMenuBar menuBar;
	JMenu mainMenu, runMenu;
	JMenuItem readFile, readHeuristicFile, exit;
	JMenuItem delivA, delivB, delivC, delivD;
	

/** The main method instantiates a Prog340 class, which includes giving you a choice of things to do.
 ** The only one active now will be reading the graph file and having you parse it.
 **
 ** @param args
 **    - Not used
 **
 ** @throws FileNotFoundException
 **    - Thrown if the file selected is not found.  Shouldn't happen with a FileChooser.
**/

  public static void main(String[] args) throws FileNotFoundException {
	  javax.swing.SwingUtilities.invokeLater(new Runnable() {
		  public void run() {
			  createAndShowGUI();
		  }
	  });
  }
  
  /** Create and show the GUI.
   ** For thread safety, this method should be invoked from the event-dispatching thread.
   **/
  private static void createAndShowGUI() {
	  // Create and set up the window
	  JFrame frame = new JFrame("Prog340");
	  frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	  
	  // Create and set up the content pane.
	  JComponent newContentPane = new Prog340();
	  newContentPane.setOpaque(true);;  // content panes must be opaque
	  frame.setContentPane(newContentPane);;
	  
	  // Display the window.
	  frame.pack();
	  frame.setVisible(true);
  }
   
 
/** The constructor creates a new ProgramA object, and sets up the input and output files.
**/
  public Prog340() {
	  
	  super( new BorderLayout() );

	try {
		// I like the colorful FileChooser.
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		System.out.println( "Look and feel set.");
	}
	catch (Exception e) { // exit on exception.
		System.err.format("Exception: %s%n", e);
		System.exit(0);
	}

    fileChooser = new JFileChooser();
    fileChooser.setDialogTitle("Choose a file");
	
	// Start looking for files at the current directory, not home.
	fileChooser.setCurrentDirectory(new File("."));
	inputFile = null;
	
	// Create the menus
	menuBar = new JMenuBar();
	
	mainMenu = new JMenu("Main Menu");
	menuBar.add(mainMenu);
	
	readFile = new JMenuItem("Read File");
	mainMenu.add(readFile);
	
	readHeuristicFile = new JMenuItem("Read Heuristic File");
	mainMenu.add(readHeuristicFile);
	
	runMenu = new JMenu("Run Code");
	mainMenu.add(runMenu);
	
	exit = new JMenuItem("Exit");
	mainMenu.add(exit);
	
	delivA = new JMenuItem("Run Deliv A");
	runMenu.add(delivA);
	delivB = new JMenuItem("Run Deliv B");
	runMenu.add(delivB);
	delivC = new JMenuItem("Run Deliv C");
	runMenu.add(delivC);
	delivD = new JMenuItem("Run Deliv D");
	runMenu.add(delivD);
	
	// Here are the Action Listeners for the Menu Items
	
	readFile.addActionListener( new ActionListener() {
		public void actionPerformed( ActionEvent e ) {
			g = new Graph();
			readGraphInfo(g);
		}
	});
		
	readHeuristicFile.addActionListener( new ActionListener() {
		public void actionPerformed( ActionEvent e ) {
			gh = new Graph();
			readGraphInfo(gh);
		}
	});
	
	exit.addActionListener( new ActionListener() {
		public void actionPerformed( ActionEvent e ) {
			System.out.println("Goodbye");
			System.exit(0);;
		}
	});
	
	delivA.addActionListener( new ActionListener() {
		public void actionPerformed( ActionEvent e ) {
			new DelivA( inputFile, g);
		}
	});
	
	delivB.addActionListener( new ActionListener() {
		public void actionPerformed( ActionEvent e ) {
			new DelivB( inputFile, g);
		}
	});
	
	delivC.addActionListener( new ActionListener() {
		public void actionPerformed( ActionEvent e ) {
			new DelivC( inputFile, g, gh);
		}
	});

		delivD.addActionListener( new ActionListener() {
		public void actionPerformed( ActionEvent e ) {
			new DelivD( inputFile, g);
		}
	});
	
	this.add(menuBar);
  }

  
/** Read the file containing the Strings, line by line, then process each line as it is read.
**/
  public void readGraphInfo( Graph g ) {

	try {
		
	   	if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {

	   		// Instantiate the selected input and output files.
	   		inputFile = fileChooser.getSelectedFile();
	   		System.out.println( "Chosen file = " + inputFile + "\n");
	   	}
	   	
		// read text file
		Scanner sc = new Scanner( inputFile );
		// First line special:  It contains "~", and "val", and the nodes with the edges.
		String firstLine = sc.nextLine();
		String[] splitString = firstLine.split( " +" );
		
		// Ignore first two fields of first line,  Every other field is a node.
		for ( int i = 2; i < splitString.length; i++ ) {
			/** I changed this part and only this part of the code *********************** <- Important Read */
			HeurNode n = new HeurNode( splitString[i] );  ////////////////////////////////////////////////////
			/*********** I used a child class of Node ************************************ <- <- <- <- */
			g.addNode( n );
		}
		
		// Every other line gives the name and value of the Node, and any edges.
		int nodeIndex = 0;
		ArrayList<Node> nodeList = g.getNodeList();
		
		while ( sc.hasNextLine() ) {
			String nextLine = sc.nextLine();
			splitString = nextLine.split(" +");

			Node n = nodeList.get( nodeIndex );
			n.setName( splitString[0] );
			//System.out.println("Line 215: val = " + splitString[1]);
			n.setVal( splitString[1] );

			for ( int i = 2; i < splitString.length; i++ ) {
				if ( !splitString[i].equals("~") ) {
					Node head = nodeList.get(i-2);
					//System.out.println("Line 221: dist = " + splitString[i]);
					/** I changed this line so that it can parse in a signed integer ******* <- Important Read */
					int dist = (int) Double.parseDouble( splitString[i] );    //Added this new line of code.
					//int dist = Integer.parseInt( splitString[i] );     //Comment out original line.
					/* I CHANGED THE ABOVE LINES ****************************************************************/
					Edge e = new Edge( n, head, dist );
					g.addEdge( e );
					n.addOutgoingEdge( e );
					head.addIncomingEdge( e );
				}
			}
			nodeIndex++;

		}
		sc.close();

	}
	catch (Exception x) {
		System.err.format("ExceptionOuter: %s%n", x);
	}
  }		

}