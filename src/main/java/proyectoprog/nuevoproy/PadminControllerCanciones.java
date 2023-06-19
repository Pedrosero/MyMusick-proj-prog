package proyectoprog.nuevoproy;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.scene.control.Alert.AlertType;
import model.DAO.CancionDAO;
import model.domain.Cancion;
import model.enums.Genero;

public class PadminControllerCanciones {

	@FXML
	private TextField txtSearch;

	@FXML
	private TextField txtGenero;

	@FXML
	private TextField txtNombre;

	@FXML
	private TextField txtArtista;

	@FXML
	private TableView<Cancion> tableview;

	@FXML
	private TableColumn<Cancion, Integer> colId_c;

	@FXML
	private TableColumn<Cancion, String> colNombre;

	@FXML
	private TableColumn<Cancion, String> colArtista;

	@FXML
	private TableColumn<Cancion, Genero> colGenero;

	@FXML
	ChoiceBox<Genero> bGenero = new ChoiceBox<>();

	CancionDAO cDAO = new CancionDAO();

	private ObservableList<Cancion> canciones;

	/**
	 * BOTÓN PAAR EDITAR UNA CANCIÓN
	 * 
	 * @throws IOException
	 * @throws SQLException
	 */
	@FXML
	private void btnEdit() throws IOException, SQLException {
		App a = new App();
		String nombre = txtNombre.getText();
		String artista = txtArtista.getText();
		Integer id = colId_c.getCellData(tableview.getSelectionModel().getSelectedIndex());
		Genero genero = bGenero.getValue();
		if (nombre.equals("") || artista.equals("") || genero == null|| id==null) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText("Campos vacíos");
			alert.setContentText("Por favor, complete todos los campos.");
			alert.showAndWait();
			return;
		} else if (cDAO.editc(nombre, artista, genero, id) != null) {
			Alert alerta = new Alert(AlertType.INFORMATION);
			alerta.setTitle("Canciones");
			alerta.setHeaderText("Actualización exitosa");
			alerta.setContentText("Se ha actualizado las canciones correctamente.");
			alerta.showAndWait();
		}

	}

	/**
	 * BOTÓN PARA ELIMINAR UNA CANCIÓN
	 * 
	 * @throws IOException
	 * @throws SQLException
	 */
	@FXML
	private void btnDel() throws IOException, SQLException {
		App a = new App();
		String nombre = txtNombre.getText();
		String artista = txtArtista.getText();
		Genero genero = bGenero.getValue();
		Cancion nCancion = new Cancion(nombre, artista, genero);

		if (nombre.isEmpty() || artista.isEmpty() || genero == null) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText("Campos vacíos");
			alert.setContentText("Por favor, complete todos los campos.");
			alert.showAndWait();
			return;
		} else if (cDAO.validCancion(nombre, artista) == null) {

			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText("No se puede eliminar");
			alert.setContentText("Por favor, seleccione una canción existente.");
			alert.showAndWait();
		} else {
			try {
				cDAO.delete(nCancion);
				Alert alerta = new Alert(AlertType.INFORMATION);
				alerta.setTitle("Canciones");
				alerta.setHeaderText("Actualización exitosa");
				alerta.setContentText("Se ha borrado la cancion correctamente.");
				alerta.showAndWait();
				App.setRoot("padmin");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

	/**
	 * BOTÓN PARA BUSCAR UNA CANCIÓN
	 * 
	 * @throws SQLException
	 */
	@FXML
	private void btnSearch() throws SQLException {
		String busca = txtSearch.getText();
		List<Cancion> canciones = cDAO.BuscarC(busca);
		if (tableview.getItems().isEmpty()) {
			colId_c.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper(cellData.getValue().getId_c()));
			colNombre.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNombre()));
			colArtista.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getArtista()));
			colGenero.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getGenero()));

			tableview.getItems().addAll(canciones);
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
	 * BOTÓN PARA OBTENER TODAS LAS CANCIONES
	 * 
	 * @throws SQLException
	 */
	@FXML
	private void getAll() throws SQLException {
		List<Cancion> canciones = cDAO.findAll();
		if (tableview.getItems().isEmpty()) {
			colId_c.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper(cellData.getValue().getId_c()));
			colNombre.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNombre()));
			colArtista.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getArtista()));
			colGenero.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getGenero()));

			tableview.getItems().addAll(canciones);
		} else {
			tableview.getItems().clear();
		}
	}

	/**
	 * METODO INITIALIZE PARA OBTENER LOS DATOS DE UNA CANCIÓN CUANDO SE SELECCIONA
	 * EN LA TABLA
	 */
	@FXML
	private void initialize() {
		bGenero.setItems(FXCollections.observableArrayList(Genero.values()));

		tableview.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
			if (newSelection != null) {

				Cancion selectedCancion = (Cancion) newSelection;

				txtNombre.setText(selectedCancion.getNombre());
				txtArtista.setText(selectedCancion.getArtista());
				bGenero.setValue(selectedCancion.getGenero());
				tableview.getSelectionModel().clearSelection();

				int rowIndex = tableview.getItems().indexOf(selectedCancion);
				if (rowIndex >= 0) {
					tableview.getSelectionModel().clearAndSelect(rowIndex);
				}
			}
		});
	}

	/**
	 * BOTÓN PARA VOLVER AL MENÚ DE LOGIN
	 * 
	 * @throws IOException
	 */
	@FXML
	private void btnOut() throws IOException {
		App.setRoot("login");
	}

	/**
	 * BOTÓN PARA CAMBIAR A LA PANTALLA DE CONTROL DE USUARIOS
	 * 
	 * @throws IOException
	 */
	@FXML
	private void btncUsuarios() throws IOException {
		App.setRoot("cusuarios");
	}
}
