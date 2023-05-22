package util;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;





public class Connect {
	private String file = "conexion.xml";
	private static Connect _newInstance;
	private static Connection con;

	private Connect() {
		ConnectionData cd = loadXML();
		
		try {
			con = DriverManager.getConnection(cd.getServer()+"/"+cd.getDatabase(), cd.getUsername(), cd.getPassword());

		}catch(SQLException e) {
			con = null;
			e.printStackTrace();
		}
	}
	
	public static Connection getConnect() {
		if(_newInstance == null) {
			_newInstance = new Connect();
		}
		return con;
	}
	
	
	
	public ConnectionData loadXML() {
		ConnectionData con = new ConnectionData();
		JAXBContext jaxbContext;
		try {
			jaxbContext = JAXBContext.newInstance(ConnectionData.class);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			con = (ConnectionData) jaxbUnmarshaller.unmarshal(new File(file));
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		return con;
	}

}
