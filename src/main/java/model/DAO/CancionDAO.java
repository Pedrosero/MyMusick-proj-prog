package model.DAO;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.domain.Cancion;
import model.domain.Usuario;
import model.enums.Genero;
import util.Connect;

public class CancionDAO implements IDAO<Cancion> {

	private final static String FINDALL = "SELECT * from cancion";
	private final static String FINDBYID = "SELECT * from cancion where id_c=?";
	private final static String INSERTE = "INSERT INTO cancion(id_c,nombre,artista,genero) VALUES(?,?,?,?)";
	private final static String UPDATE = "UPDATE cancion SET nombre=?,artista=?,genero=? WHERE id_c=?";
	private final static String DELETE = "DELETE  from cancion where nombre=? AND artista=? AND genero=?";
	private final static String SEARCHSONG = "SELECT * FROM cancion WHERE nombre LIKE CONCAT('%', ?, '%') OR artista LIKE CONCAT('%', ?, '%')";
	private final static String FINDBYNAMEANDARTIST = "SELECT * FROM cancion where nombre=? AND artista=?";
	private final static String EDITSONG = "UPDATE cancion SET nombre = ?, artista = ?, genero = ? WHERE id_c=?";
	private final static String FINDBYNAME= "SELECT * FROM cancion WHERE nombre=?";
	private Connection conn;

	public CancionDAO(Connection conn) {
		this.conn = conn;
	}

	public CancionDAO() {
		this.conn = Connect.getConnect();
	}
	
	/**
	 * Encontrar todas las canciones
	 * @RETURN Lista de canciones
	 * @throws SQLException
	 */
	@Override
	public List<Cancion> findAll() throws SQLException {
		List<Cancion> result = new ArrayList<Cancion>();
		try (PreparedStatement pst = this.conn.prepareStatement(FINDALL)) {
			try (ResultSet res = pst.executeQuery()) {
				while (res.next()) {
					Cancion c = new Cancion();
					c.setId_c(res.getInt("id_c"));
					c.setNombre(res.getString("nombre"));
					c.setArtista(res.getString("artista"));
					c.setGenero(Genero.valueOf(res.getString("genero")));
					result.add(c);
				}
			}

		}
		return result;
	}

	/**
	 * Encontrar por id de la canción
	 * @param id_c el id de la canción
	 * @return La canción entera
	 * @throws SQLException
	 */
	@Override
	public Cancion findById(int id_c) throws SQLException {
		Cancion c = new Cancion();
		try (PreparedStatement pst = this.conn.prepareStatement(FINDBYID)) {
			pst.setLong(1, id_c);
			try (ResultSet res = pst.executeQuery()) {
				while (res.next()) {
					
					c.setId_c(res.getInt("id_c"));
					c.setNombre(res.getString("nombre"));
					c.setArtista(res.getString("artista"));
					c.setGenero(Genero.valueOf(res.getString("genero")));				
					return c;
				}
				
			}
		}
		return null;
	}

	/**
	 * Introduce una canción 
	 * @param entity canción a insertar
	 * @return canción insertada
	 * @throws SQLException
	 */
	public Cancion save(Cancion entity) throws SQLException {
		Cancion result = new Cancion();
		if (entity != null) {
			Cancion c = findById(entity.getId_c());
			if (c == null) {
				try (PreparedStatement pst = this.conn.prepareStatement(INSERTE)) {
					pst.setLong(1, entity.getId_c());
					pst.setString(2, entity.getNombre());
					pst.setString(3, entity.getArtista());
					pst.setString(4, entity.getGenero().name());
					pst.executeUpdate();
				}
			} else {
				try (PreparedStatement pst = this.conn.prepareStatement(UPDATE)) {
					pst.setLong(1, entity.getId_c());
					pst.setString(2, entity.getNombre());
					pst.setString(3, entity.getArtista());
					pst.setString(4, entity.getGenero().name());
					pst.executeUpdate();
				}
			}

		}
		return result;
	}

	/**
	 * Eliminar una canción
	 * @param entity canción a eliminar
	 * @return canción eliminada 
	 * @throws SQLException
	 */
	public Cancion delete(Cancion entity) throws SQLException {
		Cancion result = new Cancion();
		if (entity != null) {
			Cancion c = findById(entity.getId_c());
			if (c == null) {
				try (PreparedStatement pst = this.conn.prepareStatement(DELETE)) {

					pst.setString(1, entity.getNombre());
					pst.setString(2, entity.getArtista());
					pst.setString(3, entity.getGenero().name());
					pst.executeUpdate();
				}
			} else {
				try (PreparedStatement pst = this.conn.prepareStatement(UPDATE)) {
					pst.setLong(1, entity.getId_c());
					pst.setString(2, entity.getNombre());
					pst.setString(3, entity.getArtista());
					pst.setString(4, entity.getGenero().name());
					pst.executeUpdate();
				}
			}
		}
		return result;

	}
	/**
	 * Encontrar canción por el nombre
	 * @param nombre de la canción
	 * @return canción buscada
	 * @throws SQLException
	 */
	public Cancion findCancion(String nombre) throws SQLException {
		Cancion c = new Cancion();
		try (PreparedStatement pst = this.conn.prepareStatement(FINDBYNAME)) {
			pst.setString(1, nombre);
			try (ResultSet res = pst.executeQuery()) {
				while (res.next()) {
					c.setId_c(res.getInt("id_c"));
					c.setNombre(res.getString("nombre"));
					c.setArtista(res.getString("artista"));
					c.setGenero(Genero.valueOf(res.getString("genero")));				}
			}
		}
		return c;
	}
	/**
	 * Encuentra una canción por nombre y artista
	 * @param nombre--> nombre de la canción
	 * @param artista--> nombre del artista
	 * @return la canción buscada
	 * @throws SQLException
	 */
	public Cancion validCancion(String nombre, String artista) throws SQLException {
		Cancion result = null;
		try (PreparedStatement pst = this.conn.prepareStatement(FINDBYNAMEANDARTIST)) {
			pst.setString(1, nombre);
			pst.setString(2, artista);
			try (ResultSet res = pst.executeQuery()) {
				if (res.next()) {
					result = new Cancion();
					result.setId_c(res.getInt("id_c"));
					result.setNombre(res.getString("nombre"));
					result.setArtista(res.getString("artista"));
					result.setGenero(Genero.valueOf(res.getString("genero")));				}
			}
		}
		return result;
	}

	/**
	 * Edita una canción
	 * @param nombre--> nombre de la canción
	 * @param artista--> nombre del artista
	 * @param genero--> genero de la canción
	 * @param id--> id de la canción
	 * @return la canción editada
	 * @throws SQLException
	 * @throws IOException
	 */
	public Cancion editc( String nombre, String artista, Genero genero, int id) throws SQLException, IOException{
		Cancion result = null;
		try (PreparedStatement pst = this.conn.prepareStatement(EDITSONG)) {
			pst.setString(1, nombre);
			pst.setString(2, artista);
			pst.setString(3, genero.name());
			pst.setInt(4,id);
			pst.executeUpdate();
				/*if (res.next()) {
				 * result = new Cancion();
					result.setNombre(res.getString("nombre"));
					result.setArtista(res.getString("artista"));
					result.setGenero(Genero.valueOf(res.getString("genero")));				}

				 */
		}
		return result;
	}

	/**
	 * busca una canción según lo que le escribas
	 * @param busca--> campo que recoge lo que quieras buscar ya sea una canción por su nombre o por el artista
	 * @return devuelve una lista de canciones
	 * @throws SQLException
	 */
	public List<Cancion> BuscarC(String busca) throws SQLException {
		List<Cancion> result = new ArrayList<Cancion>();
		try (PreparedStatement pst = conn.prepareStatement(SEARCHSONG)) {
			pst.setString(1, busca);
			pst.setString(2, busca);
			try (ResultSet res = pst.executeQuery()) {
				while (res.next()) {
					Cancion c = new Cancion();
					c.setId_c(res.getInt("id_c"));
					c.setNombre(res.getString("nombre"));
					c.setArtista(res.getString("artista"));
					c.setGenero(Genero.valueOf(res.getString("genero")));					
					result.add(c);
				}
			}
		}

		return result;

	}

	public void close() throws Exception {

	}

}
