package util;

import java.security.MessageDigest;
import java.util.regex.Pattern;

import javafx.scene.control.Alert;
import javafx.stage.Stage;

public class Utils {
	/**
	 * La pantalla de alertas que uso en mi aplicación
	 * @param title
	 * @param header
	 * @param text
	 * @param type
	 * @return
	 */
	public static Alert showPopUp(String title, String header, String text, Alert.AlertType type) {
		Alert alertDialog = new Alert(type);
		alertDialog.setTitle(title);
		alertDialog.setHeaderText(header);
		alertDialog.setContentText(text);
		alertDialog.show();
		Stage s = (Stage) alertDialog.getDialogPane().getScene().getWindow();
		s.toFront();
		return alertDialog;
	}
	/**
	 * Encripta en hash una contraseña
	 * @param s la contaseña que le pasas
	 * @return la contaseña hasheada
	 */
	public static String encryptSHA256(String s) {
		String result = null;
		try {
			MessageDigest md = MessageDigest.getInstance("SHA256");
			md.update(s.getBytes());
			StringBuilder sb = new StringBuilder();
			for (byte aByte : md.digest()) {
				sb.append(Integer.toString((aByte & 0xff) + 0x100, 16).substring(1));
			}
			result = sb.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	/**
	 * Valida que la contraseña cumple unos parámetros con una expesión regular
	 * @param password--> la contraseña que le pasas
	 * @return--> true si cumple los requisitos y false si no
	 */
	public static boolean validatePassword(String password) {
        String contra = "^(?=.*\\d).{8,}$";
        if(Pattern.matches(contra, password)){
        	return true;
        }else {
        	return false;
        }
    }
}
