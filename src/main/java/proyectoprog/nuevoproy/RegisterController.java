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

	/**
	 * BOTON PARA REGISTRA UN USUARIO
	 * 
	 * @throws IOException
	 * @throws SQLException
	 */
	@FXML
	private void btnR() throws IOException, SQLException {
		App a = new App();
		String nombre = txtNewUser.getText();
		String contra = txtNewPassword.getText();
		boolean isValid=util.Utils.validatePassword(contra);
		
		
		if (nombre.equals("") || contra.equals("")) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText("Campos vacíos");
			alert.setContentText("Por favor, complete todos los campos.");
			alert.showAndWait();
			return;
		}else if (isValid==false){
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText("Contraseña incorrecta");
			alert.setContentText("La contraseña debe tener al menos 8 caractéres y al menos 1 numérico");
			alert.showAndWait();
			return;
		}else if(uDAO.validRegister(nombre)!=null) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText("Nombre repetido");
			alert.setContentText("El nombre de usuario ya está en uso");
			alert.showAndWait();
		}
		
		else {
			contra = util.Utils.encryptSHA256(contra);
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
		
	}

	/**
	 * BOTÓN PAA VOLVER AL LOGIN
	 * 
	 * @throws IOException
	 */
	@FXML
	private void backLogin() throws IOException {
		App.setRoot("login");
	}

}
