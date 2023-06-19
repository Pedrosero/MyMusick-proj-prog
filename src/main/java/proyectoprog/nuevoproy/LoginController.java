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

	/*
	 * Variable estatica para identificar al usuario
	 */
	public static int idusuario = 0;

	/**
	 * BOTÓN PARA LOGEAR UN USUARIO
	 * 
	 * @throws SQLException
	 * @throws IOException
	 */
	@FXML
	private void btnLogin() throws SQLException, IOException {
		String nombre = NombreUsu.getText().trim();
		String contrasena = ContraUsuario.getText().trim();
		

		if (nombre.equals("Admin") && contrasena.equals("Admin")) {
			Alert alerta = new Alert(AlertType.INFORMATION);
			alerta.setTitle("Login");
			alerta.setHeaderText("Login exitoso");
			alerta.setContentText("Se ha logeado el admin correctamente.");
			alerta.showAndWait();
			switchToAdmin();
		} else {

			if (nombre.equals("") || contrasena.equals("")) {
				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setHeaderText(null);
				alert.setTitle("ERROR");
				alert.setContentText("Falta algun campo");
				alert.showAndWait();
			} else {
				contrasena = util.Utils.encryptSHA256(contrasena);
				UsuarioDAO uDao = new UsuarioDAO();
				int id = uDao.validLogin(nombre, contrasena);
				if (id != 0) {
					Alert alerta = new Alert(AlertType.INFORMATION);
					alerta.setTitle("Login");
					alerta.setHeaderText("Login exitoso");
					alerta.setContentText("Se ha logeado el usuario correctamente.");
					alerta.showAndWait();
					LoginController.idusuario = id;
					// System.out.println(LoginController.idusuario);
					switchToLCanciones();
				} else {
					Alert alerta = new Alert(AlertType.INFORMATION);
					alerta.setTitle("Login");
					alerta.setHeaderText("Usuario o contaseña incorrecto");
					alerta.setContentText("Intentelo de nuevo.");
					alerta.showAndWait();
				}
			}
		}
	}

	/**
	 * CAMBIA A LA PANTALLA CON LA LISTA DE CANCIONES DE USUARIO
	 * 
	 * @throws IOException
	 */
	@FXML
	private void switchToLCanciones() throws IOException {
		App.setRoot("listacanc");
	}

	/**
	 * BOTÓN PARA ACCEDER A LA PANTALLA DE REGISTRO DE USUARIOS
	 * @throws IOException
	 */
	@FXML
	private void btnRegistro() throws IOException {
		App.setRoot("register");
	}
	
	/**
	 * CAMBIA A LA PANTALLA DEL MENÚ DE ADMINISTRADOR
	 * @throws IOException
	 */
	@FXML
	private void switchToAdmin() throws IOException {
		App.setRoot("padmin");
	}

}
