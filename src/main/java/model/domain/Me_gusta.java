package model.domain;

import java.sql.Time;

public class Me_gusta {
	private int id_c;
	private int id_u;
	
	public Me_gusta(int id_c, int id_u) {
		super();
		this.id_c = id_c;
		this.id_u = id_u;

		
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

	@Override
	public String toString() {
		return "Me_gusta [id_c=" + id_c + ", id_u=" + id_u + "]";
	}

	

	
	
	
	

	
	
	
	
	
	
}
