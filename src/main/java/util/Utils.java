package util;

import java.security.MessageDigest;

import javafx.scene.control.Alert;
import javafx.stage.Stage;

public class Utils {
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
}
