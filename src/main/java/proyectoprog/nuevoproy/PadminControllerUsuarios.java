package proyectoprog.nuevoproy;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import model.DAO.CancionDAO;
import model.DAO.UsuarioDAO;
import model.domain.Cancion;
import model.domain.Usuario;

public class PadminControllerUsuarios {

	@FXML
	private TextField txtSearch;

	@FXML
	private TextField txtId;

	@FXML
	private TextField txtNombre;

	@FXML
	private TableView<Usuario> tableview;

	@FXML
	private TableColumn<Usuario, Integer> colId_u;

	@FXML
	private TableColumn<Usuario, String> colNombre;

	UsuarioDAO uDAO = new UsuarioDAO();

	private ObservableList<Usuario> usuarios;

	/**
	 * BOTÓN PARA BORRAR UN USUARIO
	 * 
	 * @throws IOException
	 * @throws SQLException
	 */
	@FXML
	private void btnDel() throws IOException, SQLException {
		App a = new App();
		String nombre = txtNombre.getText();

		if (nombre.isEmpty()) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText("Campos vacíos");
			alert.setContentText("Por favor, complete todos los campos.");
			alert.showAndWait();
			return;
		} else if (uDAO.validRegister(nombre) == null) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText("Usuario inexistente");
			alert.setContentText("Por favor, introduzca un usuario existente.");
			alert.showAndWait();
			return;
		}
		Usuario nUsuario = new Usuario(nombre);
		try {
			uDAO.delete(nUsuario);
			Alert alerta = new Alert(AlertType.INFORMATION);
			alerta.setTitle("Usuarios");
			alerta.setHeaderText("Actualización exitosa");
			alerta.setContentText("Se ha borrado el usuario correctamente.");
			alerta.showAndWait();
			App.setRoot("cusuarios");
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	/**
	 * BOTÓN PARA OBTENER TODOS LOS USUARIOS
	 * 
	 * @throws SQLException
	 */
	@FXML
	private void getAll() throws SQLException {
		List<Usuario> usuarios = uDAO.findAll();
		if (tableview.getItems().isEmpty()) {
			colId_u.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper(cellData.getValue().getId_u()));
			colNombre.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNombre()));
			tableview.getItems().addAll(usuarios);
		} else {
			tableview.getItems().clear();
		}
	}

	/**
	 * METODO INITIALIZE PARA OBTENER LOS DATOS DE UN USUARIO CUANDO SE SELECCIONA
	 * EN LA TABLA
	 */
	@FXML
	private void initialize() {

		tableview.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
			if (newSelection != null) {

				Usuario selectedUsuario = (Usuario) newSelection;

				txtNombre.setText(selectedUsuario.getNombre());
				tableview.getSelectionModel().clearSelection();

				int rowIndex = tableview.getItems().indexOf(selectedUsuario);
				if (rowIndex >= 0) {
					tableview.getSelectionModel().clearAndSelect(rowIndex);
				}
			}
		});
	}

	/**
	 * BOTÓN QUE BUSCA A LOS USUARIOS SEGÚN EL NOMBRE
	 * 
	 * @throws SQLException
	 */
	@FXML
	private void btnSearch() throws SQLException {
		String busca = txtNombre.getText();
		List<Usuario> usuarios = uDAO.BuscarU(busca);
		if (tableview.getItems().isEmpty()) {
			colId_u.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper(cellData.getValue().getId_u()));
			colNombre.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNombre()));
			tableview.getItems().addAll(usuarios);
		} else if (busca.isEmpty()) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText("Campos vacíos");
			alert.setContentText("Por favor, complete todos los campos.");
			alert.showAndWait();
			return;
		} else {
			tableview.getItems().clear();
			return;

		}
	}

	/**
	 * BOTÓN PARA VOLVER A LA PANTALLA DE LOGIN
	 * 
	 * @throws IOException
	 */
	@FXML
	private void btnOut() throws IOException {
		App.setRoot("login");
	}

	/**
	 * BOTÓN PARA VOLVER A LA PANTALLA DE CONTROL DE CANCIONES DEL ADMINISTRADOR
	 * 
	 * @throws IOException
	 */
	@FXML
	private void btncCanciones() throws IOException {
		App.setRoot("padmin");
	}
}
