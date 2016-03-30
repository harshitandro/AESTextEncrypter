/* The main driver class.
 * 
 * Author : Harshit Singh Lodha (harshitandro@gmail.com)
 * */

package encrypter;

import java.awt.EventQueue;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class ProgramDriver {
	
	public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
		UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");  // settings Nimbus Look & Feel
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUILayout window = new GUILayout();
					window.frmAesTextEncrypter.setVisible(true);
				} 
				catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

	}
}
