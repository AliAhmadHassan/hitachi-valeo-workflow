package br.com.valeoservice.model.dao.mysql.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class BaseDAO {

	protected String driver = "com.mysql.jdbc.Driver";
	protected String url = "jdbc:mysql://mysql01.valeoservice.hospedagemdesites.ws:3306/valeoservice";
	protected String user = "valeoservice";
	protected String pass = "v4l30srv";
	
	/*protected String driver = "com.mysql.jdbc.Driver";
	protected String url = "jdbc:mysql://localhost:3306/valeoservice";
	protected String user = "root";
	protected String pass = "Admin357/";*/
}
