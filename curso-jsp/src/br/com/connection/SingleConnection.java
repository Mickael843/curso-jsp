package br.com.connection;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * Classe responsavel por estabelecer uma conex�o com o Banco de Dados 
 * uma �nica vez e compartilhar essa conex�o para toda a aplica��o
 * @author mickael
 *
 */
public class SingleConnection {

	private static String url = "jdbc:postgresql://localhost:5432/curso-jsp?autoReconnect=true";
	private static String password = "admim";
	private static String user = "postgres";
	private static Connection connection = null;
	
	static {
		conectar();
	}
	
	public SingleConnection() {
		conectar();
	}
	
	private static void conectar() {
		try {
			if(connection == null) {
				Class.forName("org.postgresql.Driver");
				connection = DriverManager.getConnection(url, user, password);
				connection.setAutoCommit(false);
			}
			
		} catch (Exception e) {
			throw new RuntimeException("Erro ao estabelecer conex�o com o Banco de dados", e);
		}
	}
	
	/**
	 * M�todo responsavel por dar o retorno da conex�o gerada com o Banco de dados
	 * @return {@link Connection}
	 */
	public static Connection getConnection() {
		return connection;
	}
}
