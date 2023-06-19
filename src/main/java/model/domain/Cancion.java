package model.domain;

import java.sql.Time;
import java.util.Objects;

import model.enums.Genero;

public class Cancion {

	private int id_c;
	private String nombre;
	private String artista;
	//private String genero;
	private Genero genero;

	public Cancion(String nombre, String artista, Genero genero) {

		this.nombre = nombre;
		this.artista = artista;
		this.genero = genero;
	}

	public Cancion() {
		super();
		this.id_c = 0;
		this.nombre = "";
		this.artista = "";
		this.genero = null;
	}
	
	public Cancion(int id_c, String nombre, String artista, Genero genero) {
		this.id_c = id_c;
		this.nombre = nombre;
		this.artista = artista;
		this.genero = genero;
	}

	public int getId_c() {
		return id_c;
	}

	public void setId_c(int id_c) {
		this.id_c = id_c;
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

	public Genero getGenero() {
		return genero;
	}

	public void setGenero(Genero genero) {
		this.genero = genero;
	}

	@Override
	public String toString() {
		return "Cancion [id_c=" + id_c + ", nombre=" + nombre + ", artista=" + artista + ", genero=" + genero + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(id_c);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cancion other = (Cancion) obj;
		return id_c == other.id_c;
	}

}
