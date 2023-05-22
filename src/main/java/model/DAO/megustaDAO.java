package model.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.domain.Cancion;
import model.domain.Me_gusta;
import model.domain.Usuario;
import util.Connect;

public class megustaDAO implements IDAO<Me_gusta> {

	private final static String FINDALL= "SELECT c.nombre FROM cancion c JOIN me_gusta mg ON c.id_c = mg.id_c WHERE mg.id_u = ?";
	
	private final static String FINDBYID= "SELECT * FROM canciones c " +
											"INNER JOIN me_gusta mg ON c.id_c = mg.id_c " +
											"WHERE mg.id_u = ?";
	
	private final static String INSERTE = "INSERT INTO megusta(id_usuario,id_cancion,nombre,artista,genero) VALUES(?,?,?,?,?)";
	
	private final static String UPDATE = "UPDATE usuario SET nombre=?,contrasena=? WHERE id_u=?";
	
	private final static String DELETE = "DELETE  from usuario where id_u=? AND nombre=? AND contrasena=?";

	
	private Connection conn;
	
	public megustaDAO(Connection conn) {
		this.conn = conn;
	}

	public megustaDAO() {
		this.conn=Connect.getConnect();
	}

	@Override
	public List<Me_gusta> findAll() throws Exception {
		List<Me_gusta> result = new ArrayList<Me_gusta>();
		try(PreparedStatement pst = conn.prepareStatement(FINDALL)){
			try(ResultSet res = pst.executeQuery())	{
				while(res.next()) {
					Me_gusta mg = new Me_gusta();
					mg.setId_u(res.getInt("id_u"));
					mg.setId_c(res.getInt("id_c"));
					mg.setNombre(res.getString("nombre"));
					mg.setArtista(res.getString("Artista"));
					mg.setGenero(res.getString("genero"));
					
				}
			}
		}
		return result;
	}

	@Override
	public Me_gusta findById(int id_u) throws SQLException {
		Me_gusta  result = new Me_gusta();
		try(PreparedStatement pst = this.conn.prepareStatement(FINDBYID)){
			pst.setLong(1, id_u);
			try (ResultSet res = pst.executeQuery()){
				while (res.next()) {
					Me_gusta mg = new Me_gusta();
					mg.setId_u(res.getInt("id_u"));
					mg.setId_c(res.getInt("id_c"));
					mg.setNombre(res.getString("nombre"));
					mg.setArtista(res.getString("artista"));
					mg.setGenero(res.getString("genero"));
				}
			}
		}	
		return result;
	}

	@Override
	public Me_gusta save(Me_gusta entity) throws SQLException {
		Me_gusta result = new Me_gusta();
		if (entity !=null) {
			Me_gusta mg = findById(entity.getId_u());
			if (mg == null) {
				try (PreparedStatement pst = this.conn.prepareStatement(INSERTE)){
					pst.setLong(1, entity.getId_u());
					pst.setLong(2, entity.getId_c());
					pst.setString(3, entity.getNombre());
					pst.setString(4, entity.getArtista());
					pst.setString(5, entity.getGenero());
					pst.executeUpdate();
				}
			}else {
				try(PreparedStatement pst =this.conn.prepareStatement(UPDATE)){
					pst.setLong(1, entity.getId_u());
					pst.setLong(2, entity.getId_c());
					pst.setString(3, entity.getNombre());
					pst.setString(4, entity.getArtista());
					pst.setString(5, entity.getGenero());
					pst.executeUpdate();
				}
			}
			
		}
		return result;
	}

	@Override
	public Me_gusta delete(Me_gusta entity) throws SQLException {
		Me_gusta result = new Me_gusta();
		if(entity!=null) {
			Me_gusta mg = findById(entity.getId_u());
			if(mg == null) {
				try(PreparedStatement pst = this.conn.prepareStatement(DELETE)){
					pst.setLong(1, entity.getId_u());
					pst.setLong(2, entity.getId_c());
					pst.setString(3, entity.getNombre());
					pst.setString(4, entity.getArtista());
					pst.setString(5, entity.getGenero());
					pst.executeUpdate();
				}
			}else {
				try(PreparedStatement pst = this.conn.prepareStatement(UPDATE)){
					pst.setLong(1, entity.getId_u());
					pst.setLong(2, entity.getId_c());
					pst.setString(3, entity.getNombre());
					pst.setString(4, entity.getArtista());
					pst.setString(5, entity.getGenero());
					pst.executeUpdate();
				}
			}
		}
		return result;
	}
	
	@Override
	public void close() throws Exception {
		// TODO Auto-generated method stub
		
	}


}
