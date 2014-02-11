import static java.lang.System.nanoTime;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;


public class FileTransfer extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField sourceFolder;
	private JTextField destinationFolder;
	private JTextField inputFileType;
	private JLabel fileType;
	private JPanel panel;
	private JTextArea infoScreen;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FileTransfer frame = new FileTransfer();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public FileTransfer() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		setTitle("The Files Mover");
		this.setResizable(false);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton selectSourceFolderButton = new JButton("Select Source Folder");
		selectSourceFolderButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				JFileChooser jfc = new JFileChooser();
				jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				jfc.showOpenDialog(contentPane);
				sourceFolder.setText(jfc.getSelectedFile().getPath());
			}
		});
		selectSourceFolderButton.setBounds(6, 6, 183, 29);
		contentPane.add(selectSourceFolderButton);
		
		JButton selectDestinationButton = new JButton("Select Destination Folder");
		selectDestinationButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				JFileChooser jfc = new JFileChooser();
				jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				jfc.showOpenDialog(contentPane);
				destinationFolder.setText(jfc.getSelectedFile().getPath());
			}
		});
		selectDestinationButton.setBounds(6, 34, 183, 29);
		contentPane.add(selectDestinationButton);
		
		sourceFolder = new JTextField("No Folder Selected");
		sourceFolder.setBounds(201, 5, 243, 28);
		sourceFolder.setEditable(false);
		contentPane.add(sourceFolder);
		sourceFolder.setColumns(10);
		
		destinationFolder = new JTextField("No Folder Selected");
		destinationFolder.setBounds(201, 33, 243, 28);
		destinationFolder.setEditable(false);
		contentPane.add(destinationFolder);
		destinationFolder.setColumns(10);
		
		inputFileType = new JTextField();
		inputFileType.setBounds(153, 69, 57, 28);
		contentPane.add(inputFileType);
		inputFileType.setColumns(10);
		
		fileType = new JLabel("Type Of File To Move:");
		fileType.setBounds(16, 75, 173, 16);
		contentPane.add(fileType);
		
		panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Information:", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(0, 113, 450, 165);
		contentPane.add(panel);
		panel.setLayout(null);
		
		infoScreen = new JTextArea("");
		infoScreen.setBounds(6, 18, 438, 141);
		panel.add(infoScreen);
		infoScreen.setEditable(false);
		
		JSeparator s = new JSeparator();
		s.setBounds(6, 97, 438, 10);
		s.setForeground(Color.BLACK);
		contentPane.add(s);
		
		JButton btnMoveFiles = new JButton("Move Files");
		btnMoveFiles.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(inputFileType.getText().length() <= 0)	{
					infoScreen.append("Please Input The Type Of File To Move.\n");
					return;
				}
				if(sourceFolder.getText().equalsIgnoreCase("no folder selected") || 
						destinationFolder.getText().equalsIgnoreCase("no folder selected"))	{
					infoScreen.append("No Source or Destination Folder Selected\n");
				}
				else	{
					infoScreen.append("Moving Files...\n");
					moveFiles();
				}
			}
		});
		btnMoveFiles.setBounds(211, 70, 117, 29);
		contentPane.add(btnMoveFiles);
	}
	
	public void moveFiles()	{
		int count = 0;
		int failed = 0;
		long start = nanoTime();
		for(File f : new File(sourceFolder.getText()).listFiles())	{
			File destinationFile = new File(destinationFolder.getText() + "/" +f.getName());
			if(f.getName().toLowerCase().contains(inputFileType.getText().toLowerCase()))	{
				try {
					Files.copy(f.toPath(), destinationFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
				} catch (IOException ex) {
					failed++;
					ex.printStackTrace(System.err);
				}
				count++;
			}
		}
		long end = nanoTime();
		infoScreen.append("Moving Took: " + (end-start)/1000000000 + " seconds\n");
		infoScreen.append("Moving Complete!\nFiles Moved: " + count + " Failed Moves: " + failed + ".\n");
	}
}
