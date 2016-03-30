/* Base Class for encryption/decryption functions.
 * 
 * Author : Harshit Singh Lodha (harshitandro@gmail.com)
 * */

package encrypter;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;


public class AESCrypter {
	private String encryptedMessage;					// to store encrypted message string.
	private String decryptedMessage;					// to store decrypted message string.
	private byte[] message;								// to store message to process in byte form.
	private byte[] key;
	private SecretKeySpec secretKey;					// AES Key.
	int keyLength=128;									// AES key length (default 128 bits).
	private String saltString;							// salt string.
	private File keyFile;								// File object to store AES key.
	private File encryptedMessageFile;					// File object to store Encrypted Message.
	private File decryptedMessageFile;					// File object to store Decrypted Message.
	private File saveDir;								// Reference to Save Path object.
	private FilenameFilter KeyFilter;					// File filter for .KEY
	private FilenameFilter encryptedMessageFilter;		// File filter for '-Encrypted Message.txt'
	private FileReader fileReader ;						// File reader object
	private FileWriter fileWriter ; 					// File writer object
	private String saveName;							// to store Save Name
	private String savePath;							// to store Save Path
	boolean isEncryptionMode=true;						// encryption mode : true=encryption & false=decryption.
		
	// Member Functions
	
	// to initialise file save objects with filenames.
	private void initialiseSaveFile(){
		saveDir= new File(savePath, saveName);
		saveDir.mkdir();
		saveDir=saveDir.getAbsoluteFile();
		keyFile =new File(saveDir,saveName+".KEY");
		encryptedMessageFile =new File(saveDir,saveName + "-Encrypted Message.txt");
		decryptedMessageFile =new File(saveDir,saveName + "-Decrypted Message.txt");
	}
	
	// to set Save Name for files.
	void setSaveName(String SaveName){
		saveName=SaveName;
		
	}
	
	// to set Save Path for files.
	void setSavePath(String SavePath){
		savePath=SavePath;
		initialiseSaveFile();
	}
	
	// to set encryption mode (default encryption mode true ). 	
	void setMode(boolean mode){
		isEncryptionMode=mode;
	}
	
	// to set AES key length (default 128 bits)
	void setKeyLength(int x){
		keyLength=x;
	}
	
	// to set AES key.
	void setKey(String encryptionKey){
		MessageDigest sha =null;
		try{
			key=encryptionKey.getBytes("UTF-8");								
			sha=MessageDigest.getInstance("SHA-256");						
			key=sha.digest(key);											// hashing key to SHA 256 bits.
			key=Arrays.copyOf(key,keyLength/8);								// selecting hashed key as per given AES key length.	
		}
		catch(NoSuchAlgorithmException e){
			//e.printStackTrace();
		}
		catch(UnsupportedEncodingException e){
			//e.printStackTrace();
		}
	}
	
	// to set message to process.
	void setMessage(String Message) throws UnsupportedEncodingException{	
		if(isEncryptionMode)												// no base64 decoding when in encryption mode
			message=Message.getBytes();
		else{
			message=Base64.decodeBase64(Message);
		}
	}
	
	// to set AES Secret key for cryption.
	String setSecretKey(String secretKeyString){
		secretKey = new SecretKeySpec(Base64.decodeBase64(secretKeyString),"AES");		// initialising for AES. 
		return secretKeyString;															
	}
	
	// to set salt .
	String setSalt(String SaltString){
		return SaltString;
	}
	
	// to generate AES Secret key from given Encryption key.
	String genSecretKey(){
		secretKey =new SecretKeySpec(key,"AES");							// initialise
		return Base64.encodeBase64String(secretKey.getEncoded());			// return base 64 encoded Secret key.
	}
	
	//String genSalt() throws NoSuchAlgorithmException{
	//	return ;
	//}
	
	// perform encryption.
	void doEncrypt() throws Exception {
		Cipher cipherEnc = Cipher.getInstance("AES/ECB/PKCS5Padding");		// initialising Cipher in AES / ECB mode / PSCS5 padding.
		cipherEnc.init(Cipher.ENCRYPT_MODE,secretKey);						// setting cipher to encrypt mode.
		message =Base64.encodeBase64(message);								// base64 encoding the message before encryption.
		feedEncryptedMessage(cipherEnc.doFinal(message));					// encypting the message.
	}
	
	// perform decryption
	void doDecrypt() throws Exception {
		Cipher cipherDec = Cipher.getInstance("AES/ECB/PKCS5Padding");			// initialising Cipher in AES / ECB mode / PSCS5 padding.
		cipherDec.init(Cipher.DECRYPT_MODE,secretKey);							// setting cipher to decrypt mode.
		decryptedMessage = new String(cipherDec.doFinal(message));				// decrypting the message.
		decryptedMessage = new String (Base64.decodeBase64(decryptedMessage));	// decoding base64 to save the decrypted message.		
	}
	
	// to save desired file
	void saveFile(int FileToSave) throws IOException{
		switch(FileToSave){
		case 1 : keyFile.createNewFile();								// case to save Secret Key file
				 fileWriter = new FileWriter(keyFile);
				 fileWriter.write(getSecretKey());
				 fileWriter.close();
				/* fileWriter = new FileWriter(IVFile);
				 fileWriter.write(getIV());
				 fileWriter.close();*/
				 break;
		case 2 : encryptedMessageFile.createNewFile();					// case to save encrypted message
				 fileWriter = new FileWriter(encryptedMessageFile);
				 fileWriter.write(getEncryptedMessage());
				 fileWriter.close();
				 break;
		case 3 : decryptedMessageFile.createNewFile();					// case to save decrypted message
				 fileWriter = new FileWriter(decryptedMessageFile);
		 		 fileWriter.write(getDecryptedMessage());
		 		 fileWriter.close();
		 		 break;
		default: break;
		}
	}
	
	// to load files in decryption mode
	void loadFiles(File dir) throws Exception {
		KeyFilter =new FilenameFilter() {
			
			@Override
			public boolean accept(File dir, String name) {
				// TODO Auto-generated method stub
				return name.endsWith(".KEY");
			}
		};
		encryptedMessageFilter =new FilenameFilter() {
			
			@Override
			public boolean accept(File dir, String name) {
				// TODO Auto-generated method stub
				return name.endsWith("-Encrypted Message.txt");
			}
		};
		File[] tempKeyFile;
		File[] tempEncMessageFile;
		if((dir.listFiles(KeyFilter)) !=null){
			tempKeyFile=dir.listFiles(KeyFilter);
			setSecretKey(readFile(tempKeyFile[0]));
		}
		if((dir.listFiles(encryptedMessageFilter)) !=null){
			tempEncMessageFile=dir.listFiles(encryptedMessageFilter);
			setMessage(readFile(tempEncMessageFile[0]));
		}
		
	}
	
	// to load message from external file
	void loadMessageFile(File messageFile) throws UnsupportedEncodingException, Exception{
		setMessage(readFile(messageFile));		
	}
	
	// returns encrypted message string
	String getEncryptedMessage(){
		return encryptedMessage;
	}
	
	// returns decrypted message string
	String getDecryptedMessage(){
		return decryptedMessage;
	}
	
	// returns message string
	String getMessage(){
		if(isEncryptionMode)
			return new String (message);
		else	
			return Base64.encodeBase64String(message);	
	}
	
	// returns base64 encoded Secret Key
	String getSecretKey(){
		return Base64.encodeBase64String(secretKey.getEncoded());
	}
	
	//String getIV(){
	//	return Base64.encodeBase64String(IV);
	//}
	
	// to set encrypted message after encryption.
	void feedEncryptedMessage(byte[] encMessage){
		encryptedMessage=Base64.encodeBase64String(encMessage);		// after encoding with base64
	}
	
	// to read given file & return string
	String readFile(File tempFile) throws Exception{
		fileReader =new FileReader(tempFile.getParentFile()+File.separator+tempFile.getName());
		int tempChar=0;
		StringBuilder builder = new StringBuilder();
		while(tempChar!=-1){
			tempChar=fileReader.read();
			builder.append((char)tempChar);
		}	    	
	    return builder.toString();
	}
}
