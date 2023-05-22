package model.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.domain.Usuario;
import util.Connect;

public class UsuarioDAO implements IDAO<Usuario> {

	private final static String FINDALL="SELECT * from usuario";
	private final static String FINDBYID="SELECT * from usuario where id_u=?";
	private final static String INSERTE = "INSERT INTO usuario(id_u,nombre,contrasena) VALUES(?,?,?)";
	private final static String UPDATE = "UPDATE usuario SET nombre=?,contrasena=? WHERE id_u=?";
	private final static String DELETE = "DELETE  from usuario where id_u=? AND nombre=? AND contrasena=?";	
	
	private Connection conn;

	public UsuarioDAO(Connection conn) {
		this.conn = conn;
	}

	public UsuarioDAO() {
		this.conn=Connect.getConnect();
	}

	public List<Usuario> findAll() throws SQLException {
		List<Usuario> result = new ArrayList<Usuario>();
		try(PreparedStatement pst = conn.prepareStatement(FINDALL)){
			try(ResultSet res = pst.executeQuery())	{
				while(res.next()) {
					Usuario u = new Usuario();
					u.setId_u(res.getInt("id_u"));
					u.setNombre(res.getString("nombre"));
					u.setContrasena(res.getString("contrasena"));
				}
			}
		}
		return result;
	}

	public Usuario findById(int id_u) throws SQLException {
		Usuario  result = null;
		try(PreparedStatement pst = this.conn.prepareStatement(FINDBYID)){
			pst.setLong(1, id_u);
			try (ResultSet res = pst.executeQuery()){
				while (res.next()) {
					Usuario u = new Usuario();
					u.setId_u(res.getInt("id_u"));
					u.setNombre(res.getString("nombre"));
					u.setContrasena(res.getString("contrasena"));
				}
			}
		}	
		return result;
	}

	public Usuario save(Usuario entity) throws SQLException {
		Usuario result = new Usuario();
		if (entity !=null) {
			Usuario u = findById(entity.getId_u());
			if (u == null) {
				try (PreparedStatement pst = this.conn.prepareStatement(INSERTE)){
					pst.setLong(1, entity.getId_u());
					pst.setString(2, entity.getNombre());
					pst.setString(3, entity.getContrasena());
					pst.executeUpdate();
				}
			}else {
				try(PreparedStatement pst =this.conn.prepareStatement(UPDATE)){
					pst.setLong(1, entity.getId_u());
					pst.setString(2, entity.getNombre());
					pst.setString(3, entity.getContrasena());
					pst.executeUpdate();
				}
			}
			
		}
		return result;
	}

	public Usuario delete(Usuario entity) throws SQLException {
		Usuario result = new Usuario();
		if(entity!=null) {
			Usuario u = findById(entity.getId_u());
			if(u == null) {
				try(PreparedStatement pst = this.conn.prepareStatement(DELETE)){
					pst.setLong(1, entity.getId_u());
					pst.setString(2, entity.getNombre());
					pst.setString(3, entity.getContrasena());
					pst.executeUpdate();
				}
			}else {
				try(PreparedStatement pst = this.conn.prepareStatement(UPDATE)){
					pst.setLong(1, entity.getId_u());
					pst.setString(2, entity.getNombre());
					pst.setString(3, entity.getContrasena());
					pst.executeUpdate();
				}
			}
		}
		return result;
	}
	
	public void close() throws Exception {

	}
	
	public boolean validLogin(String nombre, String contrasena) throws SQLException {
		String query = "SELECT * FROM usuario WHERE nombre = ? AND contrasena = ?";
		try (PreparedStatement pst = conn.prepareStatement(query)) {
		pst.setString(1, nombre);
		pst.setString(2, contrasena);
		ResultSet rs = pst.executeQuery();
		return rs.next();
		}
		
	}
	
}
