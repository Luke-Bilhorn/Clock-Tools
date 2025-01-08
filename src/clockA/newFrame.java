package clockA;

import java.awt.*;
import java.text.*;
import java.util.*;
import javax.swing.*;

public class newFrame extends JFrame {
 
	Calendar calendar;
	int offset = 5;

	SimpleDateFormat regularFormat;
	SimpleDateFormat fullFormat;
	SimpleDateFormat standardFormat;
	SimpleDateFormat hours;
	
	
	JLabel regularLabel;
	JLabel fullLabel;
	JLabel standardLabel;

	JLabel millisLabel;

	
	String regular;
	String full;
	String standard;
	
	long millis;
	
	
	newFrame() {
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	 	this.setTitle("Regularized and Standard Time Formats");
	 	this.setLayout(new FlowLayout());
	 	this.setSize(720,140);
	 	this.setResizable(true);
	 	this.setBackground(Color.black); //added
	 	this.getContentPane().setBackground(Color.black);
	 	//this.setBackground(getForeground());
	 	//fix color later.
	 	

	

	 	standardFormat = new SimpleDateFormat("EEEE, MMMM dd, yyyy - hh:mm a");
	 	regularFormat = new SimpleDateFormat("yyyy-MMdd-HHmm");
	 	fullFormat = new SimpleDateFormat("1-yyyy-MMdd-HHmm-ss.SSS");
	 	hours = new SimpleDateFormat("HH");
	 	int h = hours.HOUR_OF_DAY0_FIELD;
	 	//hours.HOUR_OF_DAY0_FIELD = += 3;
	 	System.out.print(h + " = h\n\n");
	 	
	 	regularLabel = new JLabel();
	 	setUpLabel(regularLabel);
	 	fullLabel = new JLabel();
	 	setUpLabel(fullLabel);
	 	fullLabel.setFont(new Font("Ink Free", Font.PLAIN, 20));
	 	standardLabel = new JLabel();

	 	setUpLabel(standardLabel);
	 	standardLabel.setForeground(new Color(0x0040FF));
	 	
	 	millisLabel = new JLabel();
	 	setUpLabel(millisLabel);
	 

	 	this.add(regularLabel);
	 	//this.add(fullLabel);
	 	this.add(standardLabel);
	 	
	 	//this.add(millisLabel);
	 	

	 	this.setVisible(true);
		String a = toBase(56334482, 12, 10);
		System.out.println(a);
	
	 	setTime();
	}
	
	public void setUpLabel(JLabel lab) {
	 	lab.setFont(new Font("Monospaced", Font.PLAIN, 40));
	 	lab.setForeground(new Color(0x2090FF));
	 	lab.setBackground(Color.black);
	 	lab.setOpaque(true);
	}
	
	public void setTime() {
		while(true) {
			//standard = standardFormat.format(Calendar.getInstance().getTime());
			standard = "" + toBase((int)radixTime(20, 4), 20, 4);
			//standard = adjustHours(Calendar.getInstance().getTime());
			standardLabel.setText(standard);
  
			millis = Calendar.getInstance().getTimeInMillis();
			//millisLabel.setText("" + millis);
			
			long off = Calendar.getInstance().getTimeZone().getOffset(millis);
			//System.out.print(off + "\n");
			

			Date d = Calendar.getInstance().getTime();
			adjustMonthDay(d);
			d.setHours(d.getHours());
			regular = regularFormat.format(d);//Calendar.getInstance().getTime());
			regularLabel.setText(regular);
			
			//full = fullFormat.format(Calendar.getInstance().getTime());
			//fullLabel.setText("\n" + full);
			
			sleep(50);

		}
	}
	
	public String adjustMonthDay(Date d) {
		int DOY = Calendar.getInstance().get(Calendar.DAY_OF_YEAR);
		System.out.print(DOY + "\n");

		
		
		int Month = DOY / 28;
		int DOM = DOY % 28;
		int Week = DOM / 7;
		int Day = DOM % 7;
		
		return extendDigit(Month) + extendDigit(Week) + extendDigit(Day);
		
		
		
		
		
		

	}
	
	public String adjustHours(Date d) {
		int yyyy = d.getYear();
		int MM = d.getMonth();
		int dd = d.getDate();
		int HH = d.getHours();
		int mm = d.getMinutes();
		int ss = d.getSeconds();
		int cc = yyyy / 100;
		int yy = yyyy % 100;
		
		//HH = ((HH - 3) % 24) + 3;
		if(HH < 3) {
			HH += 24;
			dd -= 1;
		}
		
		return cc + "~" + yy + "~" + MM + "~" + dd + "~" + HH + "~" + mm;
	}
	
	public long radixTime(int radix, int precision) {
		int units = (int)Math.pow(radix, precision);
		
		
		long a = (long)units * getMillisOfDay(offset);
		//System.out.print((long)a + "\n");
		a = a / 86400000;
		//System.out.print(units + "\n");
		//System.out.print(radix + " + " + precision);
		//long time = getMillisOfDay() * units / 86400000;
		//int time = (int) ((double)(getFractionOfDay() * (double) units));
		//System.out.print(time + "\n");
		
		return a;//time;
	}
	
	public double getFractionOfDay(int offset) {
		long time = Calendar.getInstance().getTimeInMillis();
		time = time - (offset * 3600000);
		time = time % 86400000;
		//System.out.print((double)time / 86400000 + "\n");
		return time / 86400000;
	}
	
	public String toBase(int num, int base, int length) {
		String ret = "";
		for(int i = 0; i < length; i++) {
			int a = num % base;
			ret = extendDigit(a) + ret;
			num -= a;
			num /= base;
		}
		
		return ret;
	}
	
	public String extendDigit(int a) {
		String[] replacements = {"O", "1", "2", "3", "4", "5", "6", "7", "8", "9", "Ⱶ"/*"ǂ"/*ł/*"߂"*/ ,"ⴷ",
								 "ʖ", "Ϟ", "Ɂ", "ⴶ",  "X", "X", "X", "x", "y", "T", "‡߂ʖϞɁ𝇌", "X", "X", "X", "X"};
		//if(a >= replacements.length) //throw new ErrorArrayOutOfBoundsException();
			//return "TOOBIG";
			//let the aiobes catch themselves.
		return replacements[a];
	}
	
	public int getMillisOfDay(int offset) {
		long time = Calendar.getInstance().getTimeInMillis();
		time += Calendar.getInstance().getTimeZone().getOffset(time);//(offset * 3600000);
		//System.out.println(Calendar.getInstance().getTimeZone().getOffset(time));
		time = time % 86400000;
		//System.out.print(time + "\n");
		//System.out.print((int)time + "\n");
		return (int)time;
	}
	
	public void sleep(int millis) {
		try {
			Thread.sleep(millis); //50 for millis, 3000 for insensetive minutes
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}