import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;


import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


import acm.graphics.GLabel;
import acm.graphics.GLine;
import acm.graphics.GObject;
import acm.graphics.GOval;
import acm.graphics.GRect;
import acm.program.GraphicsProgram;
import acm.util.RandomGenerator;




@SuppressWarnings("serial")
public class bSim extends GraphicsProgram implements ChangeListener, ItemListener{
	//required parameters for the program
	private static final int WIDTH = 1200;
	public static final int HEIGHT = 600;
	public static final int OFFSET = 200;
	public static int NUMBALLS = 15;
	private static double MINSIZE = 1;
	private static double MAXSIZE = 8;
	private static double XMIN = 10;
	private static double XMAX = 50;
	private static double YMIN = 50;
	private static double YMAX = 100;
	private static double EMIN = 0;
	private static double EMAX = 1;
	private static double VMIN = 0;
	private static double VMAX = 3;
	
	//Single Ball Param
	private int sColor;
	private double sBallSize;
	private double sELoss;
	private double sVel;
	
	
	private GObject gobj; /* The object being dragged */
	private double lastX; /* The last mouse X position */
	private double lastY; /* The last mouse Y position */
	private GOval clickedOval; /* will cast GObject to this
	variable*/
	private bNode clickedNode; /* you will pass clickedOval to a
	function that will return a node containing the underlying ball
	object that this GOval belongs to */
	
	RandomGenerator rgen = new RandomGenerator();
	bTree myTree = new bTree();	
	
	/**
	 * This method is used to set values according the the accompanied sliders
	 */
	public void stateChanged(ChangeEvent e) {
		JSlider source = (JSlider)e.getSource();
		
		//Global Ball Parameters
		if (source == numballSlider.Slider) {
			NUMBALLS = numballSlider.getISlider();
			numballSlider.setSlider(NUMBALLS);
		}
		
		else if (source == MinSize.Slider) {
			MINSIZE = MinSize.getDSlider();
			MinSize.setSlider(MINSIZE);
		}
		
		else if (source == MaxSize.Slider) {
			MAXSIZE = MaxSize.getDSlider();
			MaxSize.setSlider(MAXSIZE);
		}
		
		else if (source == XMin.Slider) {
			XMIN = XMin.getDSlider();
			XMin.setSlider(XMIN);
		}
		
		else if (source == XMax.Slider) {
			XMAX = XMax.getDSlider();
			XMax.setSlider(XMAX);
		}
		
		else if (source == YMin.Slider) {
			YMIN = YMin.getDSlider();
			YMin.setSlider(YMIN);
		}
		
		else if (source == YMax.Slider) {
			YMAX = YMax.getDSlider();
			YMax.setSlider(YMAX);
		}
		
		else if (source == LMin.Slider) {
			EMIN = LMin.getDSlider();
			LMin.setSlider(EMIN);
		}
		
		else if (source == LMax.Slider) {
			EMAX = LMax.getDSlider();
			LMax.setSlider(EMAX);
		}
		
		else if (source == VelMin.Slider) {
			VMIN = VelMin.getDSlider();
			VelMin.setSlider(VMIN);
		}
		
		else if (source == VelMax.Slider) {
			VMAX = VelMax.getDSlider();
			VelMax.setSlider(VMAX);
		}
		
	}
	
	public void init() {
		addMouseListeners();
	}
	
	/**
	 * this is used to tell the JComboBox the available options
	 */
	JComboBox<String> bSimC;
	void setChoosers() {
		bSimC = new JComboBox<String>();
		bSimC.addItem("bSim");
		bSimC.addItem("Run");
		bSimC.addItem("Clear");
		bSimC.addItem("Stop");
		bSimC.addItem("Resume"); 
		bSimC.addItem("Sort");
		bSimC.addItem("Quit");
		add(bSimC,NORTH);
		addJComboListeners();
				
	}
	
	void addJComboListeners() {
		bSimC.addItemListener((ItemListener)this);
	}
	
	/**
	 * This method is used to describe the functions for each of the options of the JComboBox
	 */
	public void itemStateChanged(ItemEvent e) {
		if (e.getStateChange() == ItemEvent.SELECTED) {
			JComboBox source = (JComboBox)e.getSource();
			
			if (source == bSimC) {
				if (bSimC.getSelectedIndex() == 1) { // Run
					System.out.println("Starting Simulation");
					for(int i = 0; i < numballSlider.getISlider() ; i++) {
						double iX = rgen.nextDouble(XMin.getDSlider(), XMax.getDSlider()) / 100.0;
						double iY = rgen.nextDouble(YMin.getDSlider(), YMax.getDSlider()) / 100.0;
						double iSize = rgen.nextDouble(MinSize.getDSlider(), MaxSize.getDSlider()) / 100.0;
						Color iColor = rgen.nextColor();
						double iE = rgen.nextDouble(LMin.getDSlider(), LMax.getDSlider()) / 100.0;
						double iV = rgen.nextDouble(VelMin.getDSlider(), VelMax.getDSlider()) / 100.0;
						
						gBall iball = new gBall(iX *5,iY *5,iSize *5,iColor ,iE ,iV * 5);
						add(iball.myBall);
						myTree.addNode(iball);
						iball.start();
					}
				}
				
				if (bSimC.getSelectedIndex() == 2) { // Clear
					removeAll();
					GRect ground = new GRect(0,HEIGHT,WIDTH,3);
					ground.setColor(Color.BLACK);
					ground.setFilled(true);
					add(ground);
				}
				
				if (bSimC.getSelectedIndex() == 3) { // Stop
					gBall.intervalTime = 0;
				}
				
				if (bSimC.getSelectedIndex() == 4) { // Resume
					gBall.intervalTime = 0.1;
				}
				
				if (bSimC.getSelectedIndex() == 5) { // Sort
					myTree.moveSort();
				}
				
				if (bSimC.getSelectedIndex() == 6) { // Quit
					System.exit(0);
				}
			}
			
		}
		
	}


	ISliders numballSlider;
	Sliders MinSize;
	Sliders MaxSize;
	Sliders XMin;
	Sliders XMax;
	Sliders YMin;
	Sliders YMax;
	Sliders LMin;
	Sliders LMax;
	Sliders VelMin;
	Sliders VelMax;
	
	ISliders sbColor;
	Sliders sbBallSize;
	Sliders sbELoss;
	Sliders sbVel;
	
	gBall iball;
	
	
	/**
	 * This method is used to generate a GUI for each sliders and display it in the program
	 */
	public void run() {
		JPanel pEast = new JPanel();
		
		//Global Ball Param
		JLabel generalSim = new JLabel("General Simulation Parameters");
		pEast.add(generalSim);
		
		pEast.setLayout(new GridLayout(17,1));
		
		numballSlider = new ISliders("NUMBALLS", 1, 15, 25);
		pEast.add(numballSlider.Panel, "EAST");
		numballSlider.Slider.addChangeListener((ChangeListener)this);
		
		MinSize = new Sliders("MIN SIZE", 1.0, 1.0, 25.0);
		pEast.add(MinSize.Panel,"EAST");
		MinSize.Slider.addChangeListener((ChangeListener)this);
		
		MaxSize = new Sliders("MAX SIZE", 1.0, 8.0, 25.0);
		pEast.add(MaxSize.Panel,"EAST");
		MaxSize.Slider.addChangeListener((ChangeListener)this);
		
		XMin = new Sliders("X MIN", 1.0, 10.0, 200.0);
		pEast.add(XMin.Panel,"EAST");
		XMin.Slider.addChangeListener((ChangeListener)this);
		
		XMax = new Sliders("X MAX", 1.0, 50.0, 200.0);
		pEast.add(XMax.Panel,"EAST");
		XMax.Slider.addChangeListener((ChangeListener)this);
		
		YMin = new Sliders("Y MIN", 1.0, 50.0, 100.0);
		pEast.add(YMin.Panel,"EAST");
		YMin.Slider.addChangeListener((ChangeListener)this);
		
		YMax = new Sliders("Y MAX", 1.0, 100.0, 100.0);
		pEast.add(YMax.Panel, "EAST");
		YMax.Slider.addChangeListener((ChangeListener)this);
		
		LMin = new Sliders("LOSS MIN", 0.0, 0.1, 1.0);
		pEast.add(LMin.Panel,"EAST");
		LMin.Slider.addChangeListener((ChangeListener)this);
		
		LMax = new Sliders("LOSS MAX", 0.0, 0.9, 1.0);
		pEast.add(LMax.Panel, "EAST");
		LMax.Slider.addChangeListener((ChangeListener)this);
		
		VelMin = new Sliders("X VEL MIN", 0.0, 4.0, 10.0);
		pEast.add(VelMin.Panel, "EAST");
		VelMin.Slider.addChangeListener((ChangeListener)this);


		VelMax = new Sliders("X VEL MAX", 0.0, 6.0, 10.0);
		pEast.add(VelMax.Panel, "EAST");
		VelMax.Slider.addChangeListener((ChangeListener)this);
		
		// Single Param Ball
		JLabel SingleBallParam = new JLabel("Single Ball Instance Parameters");
		pEast.add(SingleBallParam);
		
		sbColor = new ISliders("COLOR", 0, 1, 10);
		pEast.add(sbColor.Panel, "EAST");
		sbColor.Slider.addChangeListener((ChangeListener)this);
		
		sbBallSize = new Sliders("BALL SIZE", 1.0, 4.0, 8.0);
		pEast.add(sbBallSize.Panel, "EAST");
		sbBallSize.Slider.addChangeListener((ChangeListener)this);
		
		sbELoss = new Sliders("ENERGY LOSS", 0.0, 0.7, 1.0);
		pEast.add(sbELoss.Panel, "EAST");
		sbELoss.Slider.addChangeListener((ChangeListener)this);
		
		sbVel = new Sliders("X VEL", 0.0, 3.0, 5.0);
		pEast.add(sbVel.Panel, "EAST");
		sbVel.Slider.addChangeListener((ChangeListener)this);
		
		add(pEast);
		
		setChoosers();
		
		//set up display, create and start multiple instances of gball
		this.resize(WIDTH, HEIGHT + OFFSET);
		GRect ground = new GRect(0,HEIGHT,WIDTH,3);
		ground.setColor(Color.BLACK);
		ground.setFilled(true);
		add(ground);
		

	}
	// Sadly the clicking method does not work
//	/** Method called on mouse press to record the coordinates
//	of the click */
//	public void mousePressed(MouseEvent e) {
//		lastX = e.getX();
//		lastY = e.getY();
//		gobj = getElementAt(lastX, lastY);
//		if (!(gobj==null)) {
//			clickedOval = (GOval)gobj; //casting to GOval
//			clickedNode = myTree.findNode(clickedOval);
//			clickedNode.iBall.myBall.setColor(Color.RED);
//			clickedNode.iBall.r = false;
//			}
//		else {
//			System.out.println("null"); /* show in console that click was not
//		on an object */
//		}
	}
}


