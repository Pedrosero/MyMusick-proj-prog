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

public class CancionDAO implements IDAO<Cancion>{
	
	private final static String FINDALL="SELECT * from cancion";
	private final static String FINDBYID="SELECT * from cancion where id_c=?";
	private final static String INSERTE = "INSERT INTO cancion(id_c,nombre,artista,genero) VALUES(?,?,?,?)";
	private final static String UPDATE = "UPDATE cancion SET nombre=?,artista=?,genero=? WHERE id_c=?";
	private final static String DELETE = "DELETE  from cancion where id_c=NULL AND nombre=? AND contrasena=?";
	
	private Connection conn;
	
	public CancionDAO(Connection conn) {
		this.conn=conn;
	}
	
	public CancionDAO() {
		this.conn=Connect.getConnect();
	}
	
	
	@Override
	public List<Cancion> findAll() throws SQLException {
		List<Cancion> result = new ArrayList<Cancion>();
		try(PreparedStatement pst = this.conn.prepareStatement(FINDALL)){
			try(ResultSet res = pst.executeQuery()){
				while(res.next()) {
					Cancion c = new Cancion();
					c.setId_c(res.getInt("id_c"));
					c.setNombre(res.getString("nombre"));
					c.setArtista(res.getString("artista"));
					c.setGenero(res.getString("genero"));
					result.add(c);
				}
			}
			
		}
		return result;
	}
	
	@Override
	public Cancion findById(int id_c) throws SQLException {
		Cancion  result =null;
		try(PreparedStatement pst = this.conn.prepareStatement(FINDBYID)){
			pst.setLong(1, id_c);
			try (ResultSet res = pst.executeQuery()){
				while (res.next()) {
					Cancion c = new Cancion();
					c.setId_c(res.getInt("id_c"));
					c.setNombre(res.getString("nombre"));
					c.setArtista(res.getString("artista"));
					c.setGenero(res.getString("genero"));
				}
			}
		}	
		return result;
	}

	public Cancion save(Cancion entity) throws SQLException {
		Cancion result = new Cancion();
		if (entity !=null) {
			Cancion c = findById(entity.getId_c());
			if (c == null) {
				try (PreparedStatement pst = this.conn.prepareStatement(INSERTE)){
					pst.setLong(1, entity.getId_c());
					pst.setString(2, entity.getNombre());
					pst.setString(3, entity.getArtista());
					pst.setString(4, entity.getGenero());
					pst.executeUpdate();
				}
			}else {
				try(PreparedStatement pst =this.conn.prepareStatement(UPDATE)){
					pst.setLong(1, entity.getId_c());
					pst.setString(2, entity.getNombre());
					pst.setString(3, entity.getArtista());
					pst.setString(4, entity.getGenero());
					pst.executeUpdate();
				}
			}
			
		}
		return result;
	}

	public Cancion delete(Cancion entity)throws SQLException {
		Cancion result = new Cancion();
		if(entity!=null) {
			Cancion c = findById(entity.getId_c());
			if(c == null) {
				try(PreparedStatement pst = this.conn.prepareStatement(DELETE)){
					pst.setLong(1, entity.getId_c());
					pst.setString(2, entity.getNombre());
					pst.setString(3, entity.getArtista());
					pst.setString(4, entity.getGenero());
					pst.executeUpdate();
				}
			}else {
				try(PreparedStatement pst = this.conn.prepareStatement(UPDATE)){
					pst.setLong(1, entity.getId_c());
					pst.setString(2, entity.getNombre());
					pst.setString(3, entity.getArtista());
					pst.setString(4, entity.getGenero());
					pst.executeUpdate();
				}
			}
		}
		return result;
		
	}
	
	public void close() throws Exception {
		// TODO Auto-generated method stub
		
	}

	
	

}
