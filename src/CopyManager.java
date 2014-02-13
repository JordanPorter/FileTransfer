import java.util.ArrayList;

import javax.swing.JProgressBar;
import javax.swing.JScrollPane;


public class CopyManager extends Thread{

	private ArrayList<FileCopy>activeCopies;
	private ArrayList<JProgressBar>progressBars;
	private JScrollPane infoScreen;
	private int x, y;
	
	public CopyManager(ArrayList<FileCopy> activeCopies, JScrollPane infoScreen)	{
		this.activeCopies = activeCopies;
		this.progressBars = new ArrayList<JProgressBar>();
		this.infoScreen = infoScreen;
		this.x = infoScreen.getX();
		this.y = infoScreen.getY();
	}
	
	public boolean addCopy(FileCopy fc)	{
		return activeCopies.add(fc);
	}
	
	public void run()	{
		for(int i=0; i<activeCopies.size(); i++)	{
			JProgressBar temp = new JProgressBar();
			temp.setMinimum(0);
			temp.setMaximum((int)activeCopies.get(i).getLength());
			temp.setValue(0);
			temp.setBounds(x+10, y+(i*20), infoScreen.getWidth()-10, 10);
			progressBars.add(temp);
			infoScreen.add(temp);
		}
	}
}
