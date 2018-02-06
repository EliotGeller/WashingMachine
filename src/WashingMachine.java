import java.awt.*; 
import java.awt.event.*; 
import javax.swing.*; 
import javax.swing.border.*;
import java.util.concurrent.TimeUnit;


public class WashingMachine extends JFrame implements ActionListener{
	
		//Declare Constants
		final String DELICATE = "Delicate";
		final String NORMAL = "Normal";
		final String HEAVY = "Heavy";
		final String COLD = "Cold";
		final String WARM = "Warm";
		final String HOT = "Hot";
		
		//Create cycle times
		int delMin = 25;
		int norMin = 30;
		int hvMin = 40;
		int cycSelect = delMin;
		
		//Create UI Panels
		JPanel panel1 = new JPanel(new GridLayout(1,2));
		JPanel panel2 = new JPanel(new GridLayout(2,2));
		JPanel panel3 = new JPanel(new GridLayout(1,3));
		JPanel panel4 = new JPanel(new GridLayout(2,2));
		JPanel panel5 = new JPanel(new FlowLayout());
		JPanel panel6 = new JPanel(new FlowLayout());
		
		//Create UI Controls
		JButton power = new JButton("POWER");
		JButton doorOpen = new JButton("Open door");
		JButton doorClose = new JButton("Close door");
		JButton start = new JButton("START");
		JButton pause = new JButton("Pause");
		
		JLabel powerStat = new JLabel(" ");
		JLabel cycleLab = new JLabel("Select Cycle: ");
		JLabel tempLab = new JLabel("Select Temperature: ");
		JLabel doorStat = new JLabel("Door closed");
		JLabel cycleTimeLab = new JLabel("Time Remaining: ");
		JLabel timer = new JLabel();
		JLabel status = new JLabel("Power Off");
		JLabel statLab = new JLabel("Status: ");
		
		JComboBox cycle = new JComboBox();
		JComboBox temp = new JComboBox();
		
		JFrame frame = new JFrame();
		
		//  Declare static variables:
		static WashingMachine wm;
		static boolean Paused = false;
		static boolean readyToRun = false;
		
	public static void main(String[] args) {
		
		wm = new WashingMachine();
		wm.setUi();
		
		//Create JFrame
		wm.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        wm.frame.setSize(new Dimension(600, 240));
        wm.frame.setTitle("Eliot Geller's Washing Machine");
        wm.frame.setLayout(new GridLayout(3, 2));
		wm.frame.add(wm.panel1);
		wm.frame.add(wm.panel2);
		wm.frame.add(wm.panel3);
		wm.frame.add(wm.panel4);
		wm.frame.add(wm.panel5);
		wm.frame.add(wm.panel6);
		wm.frame.setVisible(true);
		
		//Check to see if Wash Cycle can run: when readyToRun, and no Paused
		while (true){
			if(!Paused && readyToRun){
				wm.runWash();
			}
			try{
				Thread.sleep(5000);
			}
			catch(Exception e){
				
			}
		}
		
		
	}
	
	public void setUi(){
		cycle.addItem(DELICATE);
		cycle.addItem(NORMAL);
		cycle.addItem(HEAVY);
		temp.addItem(COLD);
		temp.addItem(WARM);
		temp.addItem(HOT);
		
		//Build Screen
		//Set controls in panels
		panel1.add(power);
		panel1.add(powerStat);
		panel2.add(cycleLab);
		panel2.add(cycle);
		panel2.add(tempLab);
		panel2.add(temp);
		panel3.add(doorOpen);
		panel3.add(doorClose);
		panel3.add(doorStat);
		panel4.add(cycleTimeLab);
		panel4.add(timer);
		panel4.add(start);
		panel4.add(pause);
		panel5.add(statLab);
		panel6.add(status);
		
		// add action Listeners
		power.addActionListener(this);
		doorOpen.addActionListener(this);
		doorClose.addActionListener(this);
		start.addActionListener(this);
		pause.addActionListener(this);
		cycle.addActionListener(this);
		temp.addActionListener(this);
		
		//Initialize UI controls
		doorOpen.setEnabled(false);
		doorClose.setEnabled(false);
		start.setEnabled(false);
		pause.setEnabled(false);
		cycle.setEnabled(false);
		temp.setEnabled(false);
		
		
	}
	
	public void actionPerformed(ActionEvent event){
	    Object source = event.getSource();
	    if (source == wm.power){
	    	System.out.println("Power Clicked");
	    	doorOpen.setEnabled(true);
			doorClose.setEnabled(true);
			start.setEnabled(true);
			pause.setEnabled(true);
			cycle.setEnabled(true);
			temp.setEnabled(true);
			powerStat.setText("ON");
			powerStat.setForeground(Color.RED);
			status.setText("Waiting for selections...");
			timer.setText("25");
	    }
	    if (source == wm.doorOpen){
	    	System.out.println("Door Open Clicked");
	    	doorStat.setText("Door Open");
	    }
	    if (source == wm.doorClose){
	    	System.out.println("Door Close Clicked");
	    	doorStat.setText("Door Closed");
	    	if (status.getText().equals("Cannot run with door open.  Please close.")){
	    		status.setText("Waiting for selections...");
	    	}
	    }
	    if (source == wm.start){
	    	System.out.println("Start Clicked");
	    	//Check if paused, remove pause so machine can resume
	    	if (Paused){
	    		//Door must be closed
	    		if (doorStat.getText().equals("Door Open")){
		    		status.setText("Cannot run with door open.  Please close.");
	    		}
	    		else{
	    			Paused = false;
	    		}
	    	}
	    	else{
	    		//Door must be closed
	    		if (doorStat.getText().equals("Door Open")){
		    		status.setText("Cannot run with door open.  Please close.");
	    		}
	    		else{
	    			//Wash cycle can start
	    			status.setText("Washing at " + wm.temp.getSelectedItem() + " temperature.");
	    			//runWash();
	    			readyToRun = true;
	    		}
	    	}
	    }
	    if (source == wm.pause){
	    	System.out.println("Pause Clicked");
	    	Paused = true;
	    }
	    if (source == wm.cycle){
	    	System.out.println("cycle: " + wm.cycle.getSelectedItem());
	    	String selection = (String) wm.cycle.getSelectedItem();
	    	if (selection.equals(DELICATE)){
	    		timer.setText("25");
	    		cycSelect = delMin;
	    	}
	    	if (selection.equals(NORMAL)){
	    		timer.setText("30");
	    		cycSelect = norMin;
	    	}
	    	if (selection.equals(HEAVY)){
	    		timer.setText("40");
	    		cycSelect = hvMin;
	    	}
	    }
	    if (source == wm.temp){
	    	System.out.println("cycle: " + wm.temp.getSelectedItem());
	    }
	}
	
	public void runWash(){
		long oneMin = 1000;
		System.out.println("Run Started");
		
		try{
			while (cycSelect>0){
				//System.out.println("SLEEP");
				Thread.sleep(oneMin);
				
				//Decrement run time by one minute
				if (!wm.Paused){
					cycSelect = cycSelect - 1;
					//System.out.println(cycSelect);
					String display = Integer.valueOf(cycSelect).toString();
					System.out.println(display);
					wm.timer.setText(display);
				}
			}
			//When cycle complete: set status message, set readyToRun to false
		}
		catch(Exception ex){
			Thread.currentThread().interrupt();
		}
		wm.readyToRun = false;
		status.setText("Wash complete!");
		
	}

}