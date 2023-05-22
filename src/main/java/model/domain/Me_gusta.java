package model.domain;

import java.sql.Time;

public class Me_gusta {
	private int id_c;
	private int id_u;
	private String nombre;
	private String artista;
	
	private String genero;
	
	public Me_gusta(int id_c, int id_u, String nombre, String artista, String genero) {
		super();
		this.id_c = id_c;
		this.id_u = id_u;
		this.nombre = nombre;
		this.artista = artista;
		this.genero = genero;
	}

	public Me_gusta() {
		super();
	}

	public int getId_c() {
		return id_c;
	}

	public void setId_c(int id_c) {
		this.id_c = id_c;
	}

	public int getId_u() {
		return id_u;
	}

	public void setId_u(int id_u) {
		this.id_u = id_u;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getArtista() {
		return artista;
	}

	public void setArtista(String artista) {
		this.artista = artista;
	}


	public String getGenero() {
		return genero;
	}

	public void setGenero(String genero) {
		this.genero = genero;
	}

	@Override
	public String toString() {
		return "Me_gusta [id_c=" + id_c + ", id_u=" + id_u + ", nombre=" + nombre + ", artista=" + artista
				+ ", duracion=" + ", genero=" + genero + "]";
	}
	
	
	

	
	
	
	
	
	
}
