package model.domain;

import java.util.List;

public class Usuario {

	private int id_u;
	private String nombre;
	private String contrasena;

	public Usuario(int id_u, String nombre) {
		this.id_u = id_u;
		this.nombre = nombre;
	}
	
	
	
	public Usuario(String nombre, String contrasena) {

		this.nombre = nombre;
		this.contrasena = contrasena;
	}

	public Usuario() {
		super();
		this.id_u = 0;
		this.nombre = "";
		this.contrasena = "";

	}

	public Usuario(String nombre) {
		this.nombre=nombre;
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

	public String getContrasena() {
		return contrasena;
	}

	public void setContrasena(String contrasena) {
		this.contrasena = contrasena;
	}

	

	@Override
	public String toString() {
		return "Usuario [id_u=" + id_u + ", nombre=" + nombre + ", contrasena=" + contrasena + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Usuario other = (Usuario) obj;
		if (nombre == null) {
			if (other.nombre != null)
				return false;
		} else if (!nombre.equals(other.nombre))
			return false;
		return true;
	}

}
