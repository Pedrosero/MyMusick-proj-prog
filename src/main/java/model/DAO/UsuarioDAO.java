package model.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.domain.Cancion;
import model.domain.Usuario;
import util.Connect;

public class UsuarioDAO implements IDAO<Usuario> {

	private final static String FINDALL = "SELECT * from usuario";
	private final static String FINDBYID = "SELECT * from usuario where id_u=?";
	private final static String INSERTE = "INSERT INTO usuario(id_u,nombre,contrasena) VALUES(?,?,?)";
	private final static String UPDATE = "UPDATE usuario SET nombre=?,contrasena=? WHERE id_u=?";
	private final static String DELETE = "DELETE  from usuario where nombre=?";
	private final static String FINDBYUSERNAME = "SELECT * from usuario WHERE nombre=?";
	private final static String SEARCHUSER = "SELECT * FROM usuario where nombre LIKE CONCAT('%',?,'%')";
	private final static String VALIDLOGIN = "SELECT * FROM usuario WHERE nombre = ? AND contrasena = ?";

	private Connection conn;

	public UsuarioDAO(Connection conn) {
		this.conn = conn;
	}

	public UsuarioDAO() {
		this.conn = Connect.getConnect();
	}

	/**
	 * Encontrar todos los usuarios
	 * 
	 * @RETURN Lista de usuarios
	 * @throws SQLException
	 */
	public List<Usuario> findAll() throws SQLException {
		List<Usuario> result = new ArrayList<Usuario>();
		try (PreparedStatement pst = conn.prepareStatement(FINDALL)) {
			try (ResultSet res = pst.executeQuery()) {
				while (res.next()) {
					Usuario u = new Usuario();
					u.setId_u(res.getInt("id_u"));
					u.setNombre(res.getString("nombre"));
					result.add(u);
				}
			}
		}
		return result;
	}

	/**
	 * Encontrar por id del usuario
	 * @param id_u el id del usuario
	 * @return El usuario
	 * @throws SQLException
	 */
	public Usuario findById(int id_u) throws SQLException {
		Usuario result = null;
		try (PreparedStatement pst = this.conn.prepareStatement(FINDBYID)) {
			pst.setLong(1, id_u);
			try (ResultSet res = pst.executeQuery()) {
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

	/**
	 * Introduce un usuario
	 * 
	 * @param entity usuario a insertar
	 * @return usuario insertado
	 * @throws SQLException
	 */
	public Usuario save(Usuario entity) throws SQLException {
		Usuario result = new Usuario();
		if (entity != null) {
			Usuario u = findById(entity.getId_u());
			if (u == null) {
				try (PreparedStatement pst = this.conn.prepareStatement(INSERTE)) {
					pst.setLong(1, entity.getId_u());
					pst.setString(2, entity.getNombre());
					pst.setString(3, entity.getContrasena());
					pst.executeUpdate();
				}
			} else {
				try (PreparedStatement pst = this.conn.prepareStatement(UPDATE)) {
					pst.setLong(1, entity.getId_u());
					pst.setString(2, entity.getNombre());
					pst.setString(3, entity.getContrasena());
					pst.executeUpdate();
				}
			}

		}
		return result;
	}

	/**
	 * Eliminar un usuario
	 * 
	 * @param entity usuario a eliminar
	 * @return usuario eliminado
	 * @throws SQLException
	 */
	public Usuario delete(Usuario entity) throws SQLException {
		Usuario result = new Usuario();
		if (entity != null) {
			Usuario u = findById(entity.getId_u());
			if (u == null) {
				try (PreparedStatement pst = this.conn.prepareStatement(DELETE)) {
					pst.setString(1, entity.getNombre());
					pst.executeUpdate();
				}
			} else {
				try (PreparedStatement pst = this.conn.prepareStatement(UPDATE)) {
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

	/**
	 * Encuentra un usuario por nombre y contraseña
	 * 
	 * @param nombre-->     nombre del usuario
	 * @param contrasena--> contraseña del usuario
	 * @return devuelve el id del usuario si existe y 0 si no
	 * @throws SQLException
	 */
	public int validLogin(String nombre, String contrasena) throws SQLException {
		try (PreparedStatement pst = this.conn.prepareStatement(VALIDLOGIN)) {
			pst.setString(1, nombre);
			pst.setString(2, contrasena);
			ResultSet rs = pst.executeQuery();
			if (rs.next()) {
				return rs.getInt("id_u");
			} else {
				return 0;
			}

		}

	}

	/**
	 * Encuentra usuarios por el nombre
	 * 
	 * @param nombre--> nombre del usuario
	 * @return el usuario
	 * @throws SQLException
	 */
	public Usuario validRegister(String nombre) throws SQLException {
		Usuario result = null;
		try (PreparedStatement pst = this.conn.prepareStatement(FINDBYUSERNAME)) {
			pst.setString(1, nombre);
			try (ResultSet res = pst.executeQuery()) {
				if (res.next()) {
					result = new Usuario();
					result.setId_u(res.getInt("id_u"));
					result.setNombre(res.getString("nombre"));
					result.setContrasena(res.getString("contrasena"));
				}
			}
		}
		return result;
	}

	/**
	 * Busca un usuario según la cadena de caracteres que le introduzcas
	 * @param busca--> campo que recoge el usuario que quieres buscar
	 * @return una lista de usuarios
	 * @throws SQLException
	 */
	public List<Usuario> BuscarU(String busca) throws SQLException {
		List<Usuario> result = new ArrayList<Usuario>();
		try (PreparedStatement pst = conn.prepareStatement(SEARCHUSER)) {
			pst.setString(1, busca);
			try (ResultSet res = pst.executeQuery()) {
				while (res.next()) {
					Usuario u = new Usuario();
					u.setId_u(res.getInt("id_u"));
					u.setNombre(res.getString("nombre"));
					result.add(u);
				}
			}
		}

		return result;

	}

}
