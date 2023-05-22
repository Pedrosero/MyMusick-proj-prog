package proyectoprog.nuevoproy;

import java.io.IOException;
import java.sql.SQLException;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import model.DAO.UsuarioDAO;
import model.domain.Usuario;
import javafx.scene.control.Alert.AlertType;

public class RegisterController {
	
	@FXML
	private TextField txtNewUser;
	
	@FXML
	private PasswordField txtNewPassword;

	UsuarioDAO uDAO = new UsuarioDAO();
	
	@FXML
	private void btnR() throws IOException, SQLException {
		App a = new App();
		String nombre = txtNewUser.getText();
		String contra = txtNewPassword.getText();
		contra = util.Utils.encryptSHA256(contra);
		
		if (nombre.isEmpty()|| contra.isEmpty()) {
			Alert alert = new Alert(AlertType.ERROR);
	        alert.setTitle("Error");
	        alert.setHeaderText("Campos vacíos");
	        alert.setContentText("Por favor, complete todos los campos.");
	        alert.showAndWait();
	        return;
		}
		
		Usuario nUsuario = new Usuario(nombre, contra);
		try {
			uDAO.save(nUsuario);
			Alert alerta = new Alert(AlertType.INFORMATION);
		    alerta.setTitle("Registro de usuario");
		    alerta.setHeaderText("Registro exitoso");
		    alerta.setContentText("Se ha registrado el usuario correctamente.");
		    alerta.showAndWait();
		    backLogin();
		} catch (SQLException e) {
			e.printStackTrace();
			
			
		}
	}
	
	@FXML
	private void backLogin() throws IOException{
		App.setRoot("login");
	}
	
	
}
