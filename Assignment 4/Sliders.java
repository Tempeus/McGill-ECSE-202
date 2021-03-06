import acm.gui.TableLayout;
import acm.program.*;

import java.awt.Color;
import java.awt.event.*;
import javax.swing.*;

@SuppressWarnings("serial")
public class Sliders extends ConsoleProgram{

	JPanel Panel;
	JLabel labelName;
	JLabel labelMin;
	JLabel labelMax;
	JSlider Slider;
	JLabel sRead;
	Integer imin;
	Integer imax;
		
	public Sliders(String name, double min, double value, double max) {
		Panel = new JPanel();
		labelName = new JLabel(name);
		labelMin = new JLabel(min+"");
		labelMax = new JLabel(max+"");
		Slider = new JSlider((int) (min * 100), (int) (max * 100), (int) (value * 100));
		sRead = new JLabel(value+"");
		sRead.setForeground(Color.blue);
		Panel.setLayout(new TableLayout(1,5));
		Panel.add(labelName, "width = 100");
		Panel.add(labelMin, "width = 25");
		Panel.add(Slider, "width = 100");
		Panel.add(labelMax, "width = 100");
		Panel.add(sRead, "width = 80");
		imin = (int) min;
		imax = (int) max;
	}
	
	
	public double getDSlider() {
		return Slider.getValue();
	}
	
	public void setSlider(double val) {
		Slider.setValue((int)val);
		sRead.setText(val / 100.0 +"");
	}
}
