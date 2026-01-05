package db;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class CheckDB {

	
	
	public void init(String File) {
		
		
		Path dbFile = Paths.get(File);
		
		if(Files.notExists(dbFile)) {
			
			//System.out.println("Inicializando BD");
			DataInitializer dbInit = new DataInitializer();
			dbInit.InicializarBD();
		}
		
		
	}
	
}
