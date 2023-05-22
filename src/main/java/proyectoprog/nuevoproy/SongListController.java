package proyectoprog.nuevoproy;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Time;
import java.util.List;


import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import model.DAO.CancionDAO;
import model.domain.Cancion;

public class SongListController {

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
	private TableColumn<Cancion, String> colGenero;

	CancionDAO cDAO = new CancionDAO();

	private ObservableList<Cancion> canciones;

	@FXML
	private void btnAdd() throws IOException, SQLException {
		App a = new App();
		String nombre = txtNombre.getText();
		String artista = txtArtista.getText();
		String genero = txtGenero.getText();

		if (nombre.isEmpty() || artista.isEmpty() || genero.isEmpty()) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText("Campos vacíos");
			alert.setContentText("Por favor, complete todos los campos.");
			alert.showAndWait();
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

	@FXML
	private void getAll() throws SQLException {
		List<Cancion> canciones = cDAO.findAll();
		if (tableview.getItems().isEmpty()) {
			colId_c.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper(cellData.getValue().getId_c()));
			colNombre.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNombre()));
			colArtista.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getArtista()));
			colGenero.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getGenero()));

			tableview.getItems().addAll(canciones);
		} else {
			tableview.getItems().clear();
		}
	}
	
	 @FXML
	    private void initialize() {

	        tableview.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
	            if (newSelection != null) {

	                Cancion selectedCancion = (Cancion) newSelection;

	                
	                txtNombre.setText(selectedCancion.getNombre());
	                txtArtista.setText(selectedCancion.getArtista());
	                txtGenero.setText(selectedCancion.getGenero());
	                tableview.getSelectionModel().clearSelection();
	                
	                int rowIndex = tableview.getItems().indexOf(selectedCancion);
	                if (rowIndex >= 0) {
	                    tableview.getSelectionModel().clearAndSelect(rowIndex);
	                }
	            }
	        });
	    }

	 
	@FXML
	private void btnMg () throws IOException, SQLException{

		}
	
	 
	@FXML
	private void btnOut() throws IOException {
		App.setRoot("login");
	}
}
