/* Class generating the GUI.
 * 
 * Author : Harshit Singh Lodha (harshitandro@gmail.com)
 * */

package encrypter;

import java.awt.Color;
import java.awt.FlowLayout;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GUILayout {

	JFrame frmAesTextEncrypter;
	
	private JTextField encryptKeyField;
	private JTextField SaltField;
	private JTextArea SecretKeyArea;
	private JTextArea CryptedMessageArea;
	private JTextArea MessageArea;
	private JTextField savePathField;
	private JTextField saveNameField;
	
	private final ButtonGroup rdbtnMode = new ButtonGroup();
	private final ButtonGroup rdbtnGroup = new ButtonGroup();
	private JRadioButton rdbtn128Bits;
	private JRadioButton rdbtn256Bits;
	private JRadioButton rdbtn192Bits;
	private JRadioButton rdbtnEncryption;
	private JRadioButton rdbtnDecryption;
	private JButton btnOpenSecretKey;
	JButton btnGenSecretKey;
	JButton btnEncrypt;
	JButton btnDecrypt;
	JButton btnReset;
	JButton btnGenerateSalt;
	JButton btnSaveKeyFiles;
	JButton btnSaveEncryptedMessage;
	JButton btnSaveDecryptedMessage;
	JButton btnLoadFiles;
	private JButton btnLoadMessage;
	
	private JFileChooser fc = new JFileChooser();
	
	private AESCrypter obj;
	
	
	//update the text fields
	private void updateGUI(){
		SecretKeyArea.setText(obj.getSecretKey());
		MessageArea.setText(obj.getMessage());
	}
	
	// mode switching
	private void modeSwitch(){
		if(obj.isEncryptionMode){
			btnEncrypt.setEnabled(true);
			btnSaveEncryptedMessage.setEnabled(true);
			btnSaveKeyFiles.setEnabled(true);
			btnDecrypt.setEnabled(false);
			btnSaveDecryptedMessage.setEnabled(false);
			btnLoadFiles.setEnabled(false);
			SecretKeyArea.setEditable(false);
			btnLoadMessage.setEnabled(true);
		}
		else{
			btnDecrypt.setEnabled(true);
			btnSaveDecryptedMessage.setEnabled(true);
			btnLoadFiles.setEnabled(true);
			SecretKeyArea.setEditable(true);
			btnEncrypt.setEnabled(false);
			btnSaveEncryptedMessage.setEnabled(false);
			btnSaveKeyFiles.setEnabled(false);
			btnLoadMessage.setEnabled(false);
		}
	}
	
	// reset current session 
	private void resetSession(){
		encryptKeyField.setText(null);
		SecretKeyArea.setText(null);
		CryptedMessageArea.setText(null);
		MessageArea.setText(null);
		savePathField.setText(null);
		rdbtn128Bits.setSelected(true);
		rdbtnEncryption.setSelected(true);
		saveNameField.setText(null);
		obj =new AESCrypter();
		
		modeSwitch();
	}
	
	// cehck if mandatory fields are filled 
	boolean checkMandotaryField(){
		boolean check = false;
		if(obj.isEncryptionMode)check=encryptKeyField.getText().isEmpty();
		check=check^SecretKeyArea.getText().isEmpty();
		check=check^MessageArea.getText().isEmpty();
		check=check^(obj.keyLength==0)?true:false;
		return check;
	}
	
	// Create the application.
	public GUILayout() {
		initialize();		
	}
	
	//Initialize the contents of the frame.
	private void initialize() {
		obj = new AESCrypter();
		frmAesTextEncrypter = new JFrame();
		frmAesTextEncrypter.setTitle("AES Text Encrypter");
		frmAesTextEncrypter.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frmAesTextEncrypter.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmAesTextEncrypter.getContentPane().setLayout(null);
	
		JPanel panelHeader = new JPanel();
		panelHeader.setBounds(50, 5, 1252, 26);
		frmAesTextEncrypter.getContentPane().add(panelHeader);
		panelHeader.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JLabel lblWelcomeToAes = new JLabel("Welcome to AES Text Encrypter");
		panelHeader.add(lblWelcomeToAes);
		lblWelcomeToAes.setHorizontalAlignment(SwingConstants.CENTER);
		
		JPanel panelBody = new JPanel();
		panelBody.setBounds(50, 33, 1252, 652);
		frmAesTextEncrypter.getContentPane().add(panelBody);
		panelBody.setLayout(null);
		
		// AES Key length
		JLabel lblNewLabel = new JLabel("Select Key Length : *");
		lblNewLabel.setToolTipText("");
		lblNewLabel.setBounds(30, 75, 118, 16);
		panelBody.add(lblNewLabel);
		
		rdbtn128Bits = new JRadioButton("128 bits");
		rdbtn128Bits.setSelected(true);
		rdbtn128Bits.setToolTipText("Size of to be generated Secret Key");
		rdbtn128Bits.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				obj.setKeyLength(128);
			}
		});
		rdbtnGroup.add(rdbtn128Bits);
		rdbtn128Bits.setBounds(180, 74, 76, 18);
		panelBody.add(rdbtn128Bits);
		
		rdbtn192Bits = new JRadioButton("192 bits");
		rdbtn192Bits.setToolTipText("Size of to be generated Secret Key");
		rdbtn192Bits.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				obj.setKeyLength(192);
			}
		});
		rdbtnGroup.add(rdbtn192Bits);
		rdbtn192Bits.setBounds(268, 74, 76, 18);
		panelBody.add(rdbtn192Bits);
			
		rdbtn256Bits = new JRadioButton("256 bits");
		rdbtn256Bits.setSelected(true);
		rdbtn256Bits.setToolTipText("Size of to be generated Secret Key");
		rdbtn256Bits.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				obj.setKeyLength(256);
			}
		});
		rdbtnGroup.add(rdbtn256Bits);
		rdbtn256Bits.setBounds(356, 74, 76, 18);
		panelBody.add(rdbtn256Bits);
		
		JLabel lblNewLabel_1 = new JLabel("Encryption Key : *");
		lblNewLabel_1.setToolTipText("");
		lblNewLabel_1.setBounds(30, 120, 98, 16);
		panelBody.add(lblNewLabel_1);
		
		// Encryption key 
		encryptKeyField = new JTextField();
		encryptKeyField.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent arg0) {
				obj.setKey(encryptKeyField.getText());
			}
		});
		encryptKeyField.setToolTipText("Enter Your Password");
		encryptKeyField.setBounds(180, 114, 453, 28);
		panelBody.add(encryptKeyField);
		encryptKeyField.setColumns(10);
		
		JLabel lblSecretKey = new JLabel("Secret Key : *");
		lblSecretKey.setBounds(30, 163, 98, 16);
		panelBody.add(lblSecretKey);
		
		btnOpenSecretKey = new JButton("Open");
		btnOpenSecretKey.setEnabled(false);
		btnOpenSecretKey.setBounds(656, 200, 90, 28);
		panelBody.add(btnOpenSecretKey);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(180, 154, 453, 100);
		panelBody.add(scrollPane_1);
		
		// AES Secret Key
		SecretKeyArea = new JTextArea();
		SecretKeyArea.setToolTipText("Enter or Generate Secret Key");
		SecretKeyArea.setEditable(false);
		SecretKeyArea.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent arg0) {
				obj.setSecretKey(SecretKeyArea.getText());
			}
		});
		scrollPane_1.setViewportView(SecretKeyArea);
		
		btnGenSecretKey = new JButton("Generate");
		btnGenSecretKey.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(encryptKeyField.getText().isEmpty()){
					JOptionPane.showMessageDialog(frmAesTextEncrypter, "Please enter Encryption Key first ! ","Invalid Operation",JOptionPane.WARNING_MESSAGE);
				}
				else{
					obj.setKey(encryptKeyField.getText());
					SecretKeyArea.setText(obj.genSecretKey());
				}
			}
		});
		btnGenSecretKey.setBounds(656, 160, 90, 28);
		panelBody.add(btnGenSecretKey);
		
		//Encryption/Decryption Panel		
		JPanel panelEncryptedMessage = new JPanel();
		panelEncryptedMessage.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0), 2, true), "Encrypted/Decrypted Message", TitledBorder.CENTER, TitledBorder.TOP, null, new Color(64, 64, 64)));
		panelEncryptedMessage.setBounds(629, 270, 617, 376);
		panelBody.add(panelEncryptedMessage);
		panelEncryptedMessage.setLayout(null);
		
		JScrollPane scrollPane_2 = new JScrollPane();
		scrollPane_2.setBounds(20, 30, 574, 288);
		panelEncryptedMessage.add(scrollPane_2);
		
		CryptedMessageArea = new JTextArea();
		CryptedMessageArea.setToolTipText("Output Message");
		CryptedMessageArea.setEditable(false);
		scrollPane_2.setViewportView(CryptedMessageArea);
		
		JPanel panelMessage = new JPanel();
		panelMessage.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0), 2, true), "Message", TitledBorder.CENTER, TitledBorder.TOP, null, new Color(64, 64, 64)));
		panelMessage.setBounds(6, 270, 617, 376);
		panelBody.add(panelMessage);
		panelMessage.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(20, 30, 574, 288);
		panelMessage.add(scrollPane);
		
		// Message Area
		MessageArea = new JTextArea();
		MessageArea.setToolTipText("Enter your MESSAGE here");
		scrollPane.setViewportView(MessageArea);
		MessageArea.setDragEnabled(true);
		
		btnEncrypt = new JButton("Encrypt");
		btnEncrypt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(MessageArea.getText().isEmpty()){
					JOptionPane.showMessageDialog(frmAesTextEncrypter, "Please feed a message to encrypt.","Message Field Empty !",JOptionPane.WARNING_MESSAGE);
				}
				else{
					if(checkMandotaryField())
						JOptionPane.showMessageDialog(frmAesTextEncrypter, "Please fill all MANDATORY Fields.","Mandatory Fields Empty !",JOptionPane.WARNING_MESSAGE);
					else{
						try {
							obj.setMessage(MessageArea.getText());
						} catch (UnsupportedEncodingException e1) {
							// TODO Auto-generated catch block
							//e1.printStackTrace();
							JOptionPane.showMessageDialog(frmAesTextEncrypter, "Unsupported Encoding Format.\nContact Tech Support","Encoding Format Error!",JOptionPane.ERROR_MESSAGE);
						}
						try {
							obj.doEncrypt();
						} catch (Exception e) {
							// TODO Auto-generated catch block
							//e.printStackTrace();
							JOptionPane.showMessageDialog(frmAesTextEncrypter, "Unable to complete the encryption process.\nContact Tech Support","Encryption Error !",JOptionPane.ERROR_MESSAGE);
						}
					}
				}
				CryptedMessageArea.setText(obj.getEncryptedMessage());
			}
		});
		btnEncrypt.setBounds(80, 330, 90, 28);
		panelMessage.add(btnEncrypt);
		
		btnDecrypt = new JButton("Decrypt");
		btnDecrypt.setEnabled(false);
		btnDecrypt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(MessageArea.getText().isEmpty()){
					JOptionPane.showMessageDialog(frmAesTextEncrypter, "Please feed a message to decrypt.","Message Field Empty !",JOptionPane.WARNING_MESSAGE);
				}
				else{
					if(checkMandotaryField())
						JOptionPane.showMessageDialog(frmAesTextEncrypter, "Please fill all MANDATORY Fields.","Mandatory Fields Empty !",JOptionPane.WARNING_MESSAGE);
					else{
						try {
							obj.setMessage(MessageArea.getText());
						} catch (UnsupportedEncodingException e1) {
							// TODO Auto-generated catch block
							//e1.printStackTrace();
							JOptionPane.showMessageDialog(frmAesTextEncrypter, "Unsupported Encoding Format.\nContact Tech Support","Encoding Format Error !",JOptionPane.ERROR_MESSAGE);
						}
						try {
							obj.doDecrypt();
						} catch (Exception e1) {
							// TODO Auto-generated catch block
							//e1.printStackTrace();
							JOptionPane.showMessageDialog(frmAesTextEncrypter, "Unable to complete the decryption process.\nContact Tech Support","Decryption Error!",JOptionPane.ERROR_MESSAGE);
						}
					}
				}
				CryptedMessageArea.setText(obj.getDecryptedMessage());
			}
		});
		btnDecrypt.setBounds(190, 330, 90, 28);
		panelMessage.add(btnDecrypt);
		
		btnReset = new JButton("Reset");
		btnReset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				resetSession();
			}
		});
		btnReset.setBounds(300, 330, 90, 28);
		panelMessage.add(btnReset);
		
		btnLoadMessage = new JButton("Load Message");
		btnLoadMessage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				fc = new JFileChooser();
				fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
				fc.addChoosableFileFilter(new FileNameExtensionFilter("Text Files","KEY","docx","doc","txt","c","cpp","java"));
				fc.setAcceptAllFileFilterUsed(false);
				try {
					if(!(encryptKeyField.getText().isEmpty() && SecretKeyArea.getText().isEmpty())){
						fc.showOpenDialog(panelMessage);
						obj.loadMessageFile(fc.getSelectedFile());
						updateGUI();
					}
					else
						JOptionPane.showMessageDialog(frmAesTextEncrypter, "Please fill all MANDATORY Fields.","Mandatory Fields Empty !",JOptionPane.WARNING_MESSAGE);
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
					JOptionPane.showMessageDialog(frmAesTextEncrypter, "Unsupported Encoding Format.\nContact Tech Support","Encoding Format Error!",JOptionPane.ERROR_MESSAGE);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
					JOptionPane.showMessageDialog(frmAesTextEncrypter, "File loading failed!\nPlease fill all MANDATORY Fields first.","Loading Failed",JOptionPane.ERROR_MESSAGE);
				}
			}			
		});
		btnLoadMessage.setBounds(410, 330, 118, 28);
		panelMessage.add(btnLoadMessage);
				
		JLabel lblNewLabel_2 = new JLabel("Salt :");
		lblNewLabel_2.setBounds(820, 163, 27, 16);
		panelBody.add(lblNewLabel_2);
		
		SaltField = new JTextField();
		SaltField.setToolTipText("Enter or Generate Salt value :");
		SaltField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		SaltField.setBounds(859, 157, 112, 28);
		panelBody.add(SaltField);
		SaltField.setColumns(10);
		
		btnGenerateSalt = new JButton("Generate Salt");
		btnGenerateSalt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
			}
		});
		btnGenerateSalt.setBounds(859, 200, 112, 28);
		panelBody.add(btnGenerateSalt);
		
		JPanel panelSaveOptions = new JPanel();
		panelSaveOptions.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0), 2, true), "Save Options", TitledBorder.CENTER, TitledBorder.TOP, null, new Color(59, 59, 59)));
		panelSaveOptions.setBounds(1056, 30, 190, 224);
		panelBody.add(panelSaveOptions);
		panelSaveOptions.setLayout(null);
		
		btnSaveKeyFiles = new JButton("Key Files");
		btnSaveKeyFiles.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(savePathField.getText().isEmpty() && saveNameField.getText().isEmpty()){
					JOptionPane.showMessageDialog(frmAesTextEncrypter, "Name/Path for saving files not defined", "Invalid Save Name/Location",JOptionPane.ERROR_MESSAGE);
				}
				else{
					try{
						obj.saveFile(1);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						//e.printStackTrace();
						JOptionPane.showMessageDialog(frmAesTextEncrypter, "File saving failed due to I/O error.", "I/O Error",JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});
		btnSaveKeyFiles.setBounds(50, 130, 90, 28);
		panelSaveOptions.add(btnSaveKeyFiles);
		
		btnSaveEncryptedMessage = new JButton("Encrypted Message");
		btnSaveEncryptedMessage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(savePathField.getText().isEmpty()){
					JOptionPane.showMessageDialog(frmAesTextEncrypter, "Path for saving files not defined", "Invalid Save Location",JOptionPane.ERROR_MESSAGE);
				}
				else{
					try{
						obj.saveFile(2);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						//e1.printStackTrace();
						JOptionPane.showMessageDialog(frmAesTextEncrypter, "File saving failed due to I/O error.", "I/O Error",JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});
		btnSaveEncryptedMessage.setBounds(22, 160, 147, 28);
		panelSaveOptions.add(btnSaveEncryptedMessage);
		
		btnSaveDecryptedMessage = new JButton("Decrypted Message");
		btnSaveDecryptedMessage.setEnabled(false);
		btnSaveDecryptedMessage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(savePathField.getText().isEmpty()){
					JOptionPane.showMessageDialog(frmAesTextEncrypter, "Name/Path for saving files not defined", "Invalid Save Location",JOptionPane.ERROR_MESSAGE);
				}
				else{
					try{
						obj.saveFile(3);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						//e1.printStackTrace();
						JOptionPane.showMessageDialog(frmAesTextEncrypter, "File saving failed due to I/O error.", "I/O Error",JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});
		btnSaveDecryptedMessage.setBounds(22, 190, 147, 28);
		panelSaveOptions.add(btnSaveDecryptedMessage);
		
		JLabel lblSaveName = new JLabel("Save Path :");
		lblSaveName.setHorizontalAlignment(SwingConstants.CENTER);
		lblSaveName.setBounds(50, 70, 90, 16);
		panelSaveOptions.add(lblSaveName);
		
		savePathField = new JTextField();
		savePathField.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				//obj.setSaveName(saveNameField.getText());
				fc =new JFileChooser();
				fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				fc.setDialogTitle("Where to save encryption files:");
				fc.showDialog(panelSaveOptions,"Save");
				obj.setSavePath(fc.getSelectedFile().getAbsolutePath());
				savePathField.setText(fc.getSelectedFile().getAbsolutePath());
			}
		});
		savePathField.setBounds(22, 90, 147, 28);
		panelSaveOptions.add(savePathField);
		savePathField.setColumns(10);
		
		JLabel lblSaveName_1 = new JLabel("Save Name :");
		lblSaveName_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblSaveName_1.setBounds(50, 19, 90, 16);
		panelSaveOptions.add(lblSaveName_1);
		
		saveNameField = new JTextField();
		saveNameField.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				obj.setSaveName(saveNameField.getText());
			}
		});
		saveNameField.setBounds(22, 36, 147, 28);
		panelSaveOptions.add(saveNameField);
		saveNameField.setColumns(10);
		
		JLabel lblMode = new JLabel("Mode : *");
		lblMode.setBounds(30, 30, 55, 16);
		panelBody.add(lblMode);
		
		btnLoadFiles = new JButton("Load Files");
		btnLoadFiles.setToolTipText("Load All saved Key Files while in Decryption mode");
		btnLoadFiles.setEnabled(false);
		btnLoadFiles.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				fc =new JFileChooser();
				fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				fc.setDialogTitle("Where are your encryption files ");
				fc.setApproveButtonText("Select");
				fc.showOpenDialog(null);
				try {
					obj.loadFiles(fc.getSelectedFile());
					updateGUI();
				} 
				catch (Exception e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
					JOptionPane.showMessageDialog(frmAesTextEncrypter, "Unable to load files.\nSome files are either missing or tempered",
							"Loading Failure",JOptionPane.ERROR_MESSAGE);
				}
				
			}
		});
		btnLoadFiles.setBounds(430, 24, 90, 28);
		panelBody.add(btnLoadFiles);
		
		rdbtnEncryption = new JRadioButton("Encryption");
		rdbtnEncryption.setMnemonic('E');
		rdbtnEncryption.setSelected(true);
		rdbtnEncryption.setToolTipText("Sets the application in ENCRYPTION Mode");
		rdbtnEncryption.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				obj.setMode(true);
				modeSwitch();
			}
		});
		rdbtnMode.add(rdbtnEncryption);
		rdbtnEncryption.setBounds(180, 29, 98, 18);
		panelBody.add(rdbtnEncryption);
		
		rdbtnDecryption = new JRadioButton("Decryption");
		rdbtnDecryption.setMnemonic('D');
		rdbtnDecryption.setToolTipText("Sets the application in DECRYPTION Mode");
		rdbtnDecryption.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				obj.setMode(false);
				modeSwitch();
			}
		});
		rdbtnMode.add(rdbtnDecryption);
		rdbtnDecryption.setBounds(268, 29, 98, 18);
		panelBody.add(rdbtnDecryption);
						
		JLabel lblProjectBy = new JLabel("Project By : Harshit Singh Lodha (harshitandro@gmail.com)");
		lblProjectBy.setFont(new Font("Segoe Print", Font.PLAIN, 10));
		lblProjectBy.setBounds(1021, 683, 335, 16);
		frmAesTextEncrypter.getContentPane().add(lblProjectBy);
		
		JLabel lblFieldsMarrkedWith = new JLabel("Fields marrked with * are MANDATORY");
		lblFieldsMarrkedWith.setFont(new Font("SansSerif", Font.PLAIN, 10));
		lblFieldsMarrkedWith.setBounds(60, 682, 223, 16);
		frmAesTextEncrypter.getContentPane().add(lblFieldsMarrkedWith);
	}
	public JButton getBtnLoadMessage() {
		return btnLoadMessage;
	}
}
