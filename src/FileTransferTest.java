import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;


public class FileTransferTest {

	private static File destinationFile;
	private static String sourcePath = "";
	private static String destinationPath = "";
	private static String fileTypeToCopy = "";
	
	public static void main(String [] args)	{
		if(args.length < 3)	{
			System.err.println("USAGE: FileTransferTest <Root Folder> <Original Path> <File Type>");
			System.exit(1);
		}
		
		sourcePath = args[0];
		destinationPath = args[1];
		fileTypeToCopy = args[2];
		
		for(File f : new File(sourcePath).listFiles())	{
			destinationFile = new File(destinationPath + f.getName());
			if(f.getName().contains(fileTypeToCopy))	{
				try {
					Files.copy(f.toPath(), destinationFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
				} catch (IOException e) {
					e.printStackTrace(System.err);
				}
			}
		}
	}
}
