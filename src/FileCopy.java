import static java.lang.System.nanoTime;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import javax.swing.JTextArea;


public class FileCopy extends Thread	{
	
	private String destination = "";
	private JTextArea infoScreen;
	private File sourceFile;
	
	public FileCopy(String dest, JTextArea infoScreen, File sourceFile)	{
		this.destination = dest;
		this.infoScreen = infoScreen;
		this.sourceFile = sourceFile;
	}
	
	public void run()	{
		long start = nanoTime();
				try {
					File destinationFile = new File(destination + "/" + sourceFile.getName());
					Files.copy(sourceFile.toPath(), destinationFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
				} catch (IOException ex) {
					ex.printStackTrace(System.err);
				}
		long end = nanoTime();
		infoScreen.append("-------------------------------------\n");
		infoScreen.append("| Finished Copying " + sourceFile.getName() + "\t|\n");
		infoScreen.append("| Copied " + sourceFile.length() + "bytes\t\t|\n");
		infoScreen.append("| Copying Took: " + (end-start)/1000000000 + " seconds\t\t|\n");
		infoScreen.append("-------------------------------------\n");
		infoScreen.setCaretPosition(infoScreen.getDocument().getLength());
	}

}