package util;

public class MyDataSource {
	private  String user = null;
	private  String pwd = null;
	private  String url = null;
	private  String driver = null;
	private String coleccion = null;
	
	
	
	public MyDataSource(String user, String pwd, String url, String driver) {
		super();
		this.user = user;
		this.pwd = pwd;
		this.url = url;
		this.driver = driver;
	}


	public String getUser() {
		return user;
	}


	public void setUser(String user) {
		this.user = user;
	}


	public String getPwd() {
		return pwd;
	}


	public void setPwd(String pwd) {
		this.pwd = pwd;
	}


	public String getUrl() {
		return url;
	}


	public void setUrl(String url) {
		this.url = url;
	}


	public String getDriver() {
		return driver;
	}


	public void setDriver(String driver) {
		this.driver = driver;
	}


	public String getColeccion() {
		return coleccion;
	}


	public void setColeccion(String coleccion) {
		this.coleccion = coleccion;
	}


	
	
	
}
