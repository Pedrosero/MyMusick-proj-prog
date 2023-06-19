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

	private final static String FINBDALLMG = "SELECT * FROM me_gusta WHERE id_usuario=?";
	private final static String FINDBYID = "SELECT * FROM me_gusta mg WHERE mg.id_usuario = ?";
	private final static String INSERTE = "INSERT INTO me_gusta(id_usuario,id_cancion) VALUES(?,?)";
	private final static String UPDATE = "UPDATE me_gusta SET  WHERE id_cancion=?";
	private final static String DELETE = "DELETE  FROM me_gusta WHERE id_usuario=? AND id_cancion=?";
	private final static String FINDBYIDSONG = "SELECT * FROM me_gusta where id_usuario = ? AND id_cancion = ?";
	
	private Connection conn;

	public megustaDAO(Connection conn) {
		this.conn = conn;
	}

	public megustaDAO() {
		this.conn = Connect.getConnect();
	}

	
	/**
	 * Encontrar según el id del usuario
	 * @param id_u el id del usuario
	 * @return el me gusta
	 * @throws SQLException
	 */
	@Override
	public Me_gusta findById(int id_u) throws SQLException {
		Me_gusta mg = new Me_gusta();
		try (PreparedStatement pst = this.conn.prepareStatement(FINDBYID)) {
			pst.setLong(1, id_u);
			try (ResultSet res = pst.executeQuery()) {
				while (res.next()) {

					mg.setId_u(res.getInt("id_usuario"));
					mg.setId_c(res.getInt("id_cancion"));

					return mg;
				}
			}
		}
		return null;
	}

	/**
	 * Introduce una canción a los me gusta
	 * @param entity del me gusta
	 * @return devuelve el me gusta insertado
	 * @throws SQLException
	 */
	@Override
	public Me_gusta save(Me_gusta entity) throws SQLException {
		Me_gusta result = new Me_gusta();
		if (entity != null) {
			Me_gusta mg = findByIduc(entity.getId_u(), entity.getId_c());
			if (mg == null) {
				try (PreparedStatement pst = this.conn.prepareStatement(INSERTE)) {
					pst.setLong(1, entity.getId_u());
					pst.setLong(2, entity.getId_c());
					pst.executeUpdate();
					return result;
				}
			} else {
				/*
				 * try (PreparedStatement pst = this.conn.prepareStatement(UPDATE)) {
				 * pst.setLong(1, entity.getId_u()); pst.setLong(2, entity.getId_c());
				 * pst.executeUpdate();
				 */

			}
		}
		return null;

	}

	/**
	 * Elimina un me gusta
	 * @param entity me gusta a eliminar
	 * @return el me gusta borrado
	 * @throws SQLException
	 */

	@Override
	public Me_gusta delete(Me_gusta entity) throws SQLException {
		Me_gusta result = new Me_gusta();
		if (entity != null) {
			Me_gusta mg = findByIduc(entity.getId_u(), entity.getId_c());
			if (mg != null) {
				try (PreparedStatement pst = this.conn.prepareStatement(DELETE)) {
					pst.setLong(1, entity.getId_u());
					pst.setLong(2, entity.getId_c());

					pst.executeUpdate();
				}
			} else {
				/*
				 * try (PreparedStatement pst = this.conn.prepareStatement(UPDATE)) {
				 * pst.setLong(1, entity.getId_u()); pst.setLong(2, entity.getId_c());
				 * 
				 * pst.executeUpdate();
				 */

			}
		}
		return result;
	}

	/**
	 * Encontrar por el id del usuario y el de la canción
	 * @param id_u--> id del usuario que le ha dado a me gusta
	 * @param id_c--> id de la canción que se le ha dado a me gusta
	 * @return devuelve el me gusta
	 * @throws SQLException
	 */
	public Me_gusta findByIduc(int id_u, int id_c) throws SQLException {
		Me_gusta mg = new Me_gusta();
		try (PreparedStatement pst = this.conn.prepareStatement(FINDBYIDSONG)) {
			pst.setLong(1, id_u);
			pst.setLong(2, id_c);
			try (ResultSet res = pst.executeQuery()) {
				while (res.next()) {

					mg.setId_u(res.getInt("id_usuario"));
					mg.setId_c(res.getInt("id_cancion"));

					return mg;
				}
			}
		}
		return null;
	}
	
	/*
	 * public List<Me_gusta> findByIdU(int id_u) throws SQLException {
	List<Me_gusta> result = new ArrayList<Me_gusta>();
		try (PreparedStatement pst = this.conn.prepareStatement(FINDBYID)) {
			pst.setLong(1, id_u);
			try (ResultSet res = pst.executeQuery()) {
				while (res.next()) {
					Me_gusta mg = new Me_gusta();
					mg.setId_u(res.getInt("id_u"));
					mg.setId_c(res.getInt("id_c"));
					result.add(mg);
				}
			}
		}
		return result;
	}
	 */
		
	/**
	 * Encuentra una lista de me gusta según el id del usuario
	 * @param id_u--> id del usuario
	 * @return devuelve la lista de me gustas 
	 * @throws SQLException
	 */
	public List<Me_gusta> findAllMg(int id_u) throws SQLException {
		List<Me_gusta> result = new ArrayList<Me_gusta>();
		try (PreparedStatement pst = this.conn.prepareStatement(FINBDALLMG)) {
			pst.setLong(1, id_u);
			try (ResultSet res = pst.executeQuery()) {
				while (res.next()) {
					Me_gusta mg = new Me_gusta();
					mg.setId_u(res.getInt("id_usuario"));
					mg.setId_c(res.getInt("id_cancion"));
					result.add(mg);
				}
			}
		}
		return result;
	}
	
	/*
	 * public Me_gusta validMegusta(int id_c) throws SQLException {
		Me_gusta result = null;
		try (PreparedStatement pst = this.conn.prepareStatement(FINDBYIDSONG)) {
			pst.setLong(1, id_c);
			try (ResultSet res = pst.executeQuery()) {
				if (res.next()) {
					result = new Me_gusta();
					result.setId_c(res.getInt("id_cancion"));
					result.setId_u(res.getInt(res.getString("id_usuario")));
				}
			}
			return result;
		}
	}
	 */
	

	@Override
	public void close() throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public List<Me_gusta> findAll() throws Exception {

		return null;
	}

}
