package proyectoprog.nuevoproy;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.SimpleObjectProperty;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.cell.PropertyValueFactory;
import model.DAO.CancionDAO;
import model.DAO.megustaDAO;
import model.domain.Cancion;
import model.domain.Me_gusta;
import model.enums.Genero;

public class SongListController {

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
	megustaDAO mgDAO = new megustaDAO();

	private ObservableList<Cancion> canciones;

	/**
	 * BOTÓN PARA AÑADIR UNA CANCIÓN
	 * 
	 * @throws IOException
	 * @throws SQLException
	 */
	@FXML
	private void btnAdd() throws IOException, SQLException {
		String nombre = txtNombre.getText();
		String artista = txtArtista.getText();
		Genero genero = bGenero.getValue();

		if (nombre.isEmpty() || artista.isEmpty() || genero == null) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText("Campos vacíos");
			alert.setContentText("Por favor, complete todos los campos.");
			alert.showAndWait();
			return;
		}

		try {
			if (cDAO.validCancion(nombre, artista) != null) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Error");
				alert.setHeaderText("Canción ya registrada");
				alert.setContentText("Por favor, prueba con otra canción.");
				alert.showAndWait();
				return;
			}
		} catch (SQLException e) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText("Error en la consulta");
			alert.setContentText("Ocurrió un error al consultar la existencia de la canción.");
			alert.showAndWait();
			e.printStackTrace();
			return;
		}
		Cancion nCancion = new Cancion(nombre, artista, genero);
		try {
			cDAO.save(nCancion);
			Alert alerta = new Alert(AlertType.INFORMATION);
			alerta.setTitle("Canciones");
			alerta.setHeaderText("Actualización exitosa");
			alerta.setContentText("Se ha actualizado las canciones correctamente.");
			alerta.showAndWait();
			App.setRoot("listacanc");
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	/**
	 * BOTÓN PARA OBTENER TODAS LAS CANCIONES DE LA BASE DE DATOS
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
			return;
		}
	}

	/**
	 * BOTÓN PARAR BUSCAR UNA CANCIÓN EN LA BASE DE DATOS
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
	 * BOTÓN PARA AGREGAR UNA CANCIÓN A LA LISTA DE ME GUSTA
	 * 
	 * @throws IOException
	 * @throws SQLException
	 */
	@FXML
	private void btnMg() throws IOException, SQLException {
		App a = new App();

		String nombre = txtNombre.getText();
		String artista = txtArtista.getText();
		Genero genero = bGenero.getValue();

		if (nombre.isEmpty() || artista.isEmpty() || genero == null) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText("Campos vacíos");
			alert.setContentText("Por favor, complete todos los campos.");
			alert.showAndWait();
			return;

		} else {
			int id_c = cDAO.findCancion(nombre).getId_c();
			Me_gusta nMegusta = new Me_gusta(id_c, LoginController.idusuario);
			try {
				if (mgDAO.save(nMegusta) == null) {
					Alert alerta = new Alert(AlertType.WARNING);
					alerta.setTitle("Canciones");
					alerta.setHeaderText("Me gusta repetido");
					alerta.setContentText("Esta canción ya está agregada a tu lista de me gustas");
					alerta.showAndWait();
					App.setRoot("listacanc");
				} else {
					Alert alerta = new Alert(AlertType.INFORMATION);
					alerta.setTitle("Canciones");
					alerta.setHeaderText("Me gusta dado");
					alerta.setContentText("Se ha actualizado tu lista de me gustas correctamente.");
					alerta.showAndWait();
					App.setRoot("listacanc");
				}

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

	/*
	 * @FXML public void keyPressed() {
	 * this.tabla.getSelectionModel().clearSelection(); clearFields(); }
	 */

	/**
	 * BOTÓN QUE CAMBIA LA TABLA DE CANCIONES A LA TABLA DE ME GUSTAS
	 * 
	 * @throws IOException
	 * @throws SQLException
	 */
	@FXML
	private void switchMegusta() throws IOException, SQLException {

		List<Me_gusta> me_gusta = mgDAO.findAllMg(LoginController.idusuario);
		List<Cancion> canciones = new ArrayList<>();
		for (Me_gusta i : me_gusta) {
			canciones.add(cDAO.findById(i.getId_c()));

		}
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
	 * BOTÓN PARA VOLVER AL MENÚ LOGIN
	 * 
	 * @throws IOException
	 */
	@FXML
	private void btnOut() throws IOException {
		App.setRoot("login");
	}

	/**
	 * BOTÓN PARA BORRAR EL ME GUSTA A UNA CANCIÓN
	 * 
	 * @throws SQLException
	 * @throws IOException
	 */
	@FXML
	private void btnDelMg() throws SQLException, IOException {
		App a = new App();

		String nombre = txtNombre.getText();
		String artista = txtArtista.getText();
		Genero genero = bGenero.getValue();

		if (nombre.isEmpty() || artista.isEmpty() || genero == null) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText("Campos vacíos");
			alert.setContentText("Por favor, complete todos los campos.");
			alert.showAndWait();
			return;
		}
		int id_c = cDAO.findCancion(nombre).getId_c();

		Me_gusta nMegusta = new Me_gusta(id_c, LoginController.idusuario);
		try {
			mgDAO.delete(nMegusta);
			Alert alerta = new Alert(AlertType.INFORMATION);
			alerta.setTitle("Canciones");
			alerta.setHeaderText("Has seleccionado que no te gusta la cancion");
			alerta.setContentText("Se ha actualizado tu lista de me gustas correctamente.");
			alerta.showAndWait();
			App.setRoot("listacanc");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
