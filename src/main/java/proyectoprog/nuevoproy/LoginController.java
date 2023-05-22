package proyectoprog.nuevoproy;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;

import javax.swing.JOptionPane;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.stage.Window;
import model.DAO.UsuarioDAO;
import model.domain.Usuario;
import util.Connect;

public class LoginController {

	@FXML
	private TextField NombreUsu;

	@FXML
	private PasswordField ContraUsuario;

	

	@FXML
	private void btnLogin() throws SQLException, IOException{
		String nombre = NombreUsu.getText().trim();
		String contrasena = ContraUsuario.getText().trim();
		contrasena = util.Utils.encryptSHA256(contrasena);
		
		if(nombre.equals("")||contrasena.equals("")) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setHeaderText(null);
			alert.setTitle("ERROR");
			alert.setContentText("Falta algun campo");
			alert.showAndWait();
		}else {
			UsuarioDAO uDao = new UsuarioDAO();
			if(uDao.validLogin(nombre, contrasena)) {
				Alert alerta = new Alert(AlertType.INFORMATION);
			    alerta.setTitle("Login");
			    alerta.setHeaderText("Login exitoso");
			    alerta.setContentText("Se ha logeado el usuario correctamente.");
			    alerta.showAndWait();
			    switchToLCanciones();
			}else {
				Alert alerta = new Alert(AlertType.INFORMATION);
			    alerta.setTitle("Login");
			    alerta.setHeaderText("No se ha podido logear");
			    alerta.setContentText("Intentelo de nuevo.");
			    alerta.showAndWait();
			}
		}
	}
	@FXML
    private void switchToLCanciones() throws IOException {
        App.setRoot("listacanc");
    }
	
	@FXML
	private void btnRegistro()throws IOException{
		App.setRoot("register");
	}
	
}
