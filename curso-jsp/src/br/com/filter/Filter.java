package br.com.filter;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

import br.com.connection.SingleConnection;

/**
 * Distribui a conexão para todas as paginas da aplicação 
 * que necessite
 * @author mickael
 *
 */
@WebFilter(urlPatterns = "/*")
public class Filter implements javax.servlet.Filter{

	private static Connection connection;
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		try {
			
			chain.doFilter(request, response);
			connection.commit();
			
		} catch (Exception e) {
			try {
				connection.rollback();
			} catch (Exception e2) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		connection = SingleConnection.getConnection();
	}
	

}
