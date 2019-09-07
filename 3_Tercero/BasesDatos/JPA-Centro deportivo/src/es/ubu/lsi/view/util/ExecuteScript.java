package es.ubu.lsi.view.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Execute script.
 * 
 * @author <a href="mailto:jmaudes@ubu.es">Jes�s Maudes</a>
 * @author <a href="mailto:rmartico@ubu.es">Ra�l Marticorena</a>
 * @author <a href="mailto:mmabad@ubu.es">Mario Mart�nez</a>
 * @since 1.1
 */
public class ExecuteScript {
	
	/**
	 * Database user (schema).
	 */
	private static final String USER = "HR";
	// WARNING: review with your current DB 
	
	/**
	 * Database password user.
	 */
	private static final String PASSWORD = "hr";
	// WARNING: review with your current DB

	/**
	 * Main.
	 * 
	 * @param args arguments
	 */
	public static void main(String[] args) {
		run(args[0]);
	}

	/**
	 * Run script.
	 * 
	 * @param file_name script path
	 */
	public static void run(String file_name) {
		try {
			String line;
			//No te olvides de hacer que el script acabe con "exit;"
			Process p = Runtime.getRuntime().exec("sqlplus " + USER + "/" + PASSWORD + " @" + file_name);
			
			BufferedReader input = new BufferedReader(new InputStreamReader(
					p.getInputStream()));
			while ((line = input.readLine()) != null) {
				System.out.println(line);
			}
			input.close();
			p.destroy();
		} catch (Exception err) {
			err.printStackTrace();
		}
	}

}
