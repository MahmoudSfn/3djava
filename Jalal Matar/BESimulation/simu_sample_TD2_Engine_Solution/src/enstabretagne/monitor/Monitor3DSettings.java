package enstabretagne.monitor;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import enstabretagne.base.Settings;

public class Monitor3DSettings {
		
		/** The settings. */
		public static Monitor3DSettings settings ;
		
		
		/** The Constant loggerSettingsFileName. */
		private static final String monitor3DSettingsFileName = "monitor3D_settings.json";
		static {
			File dir= new File(System.getProperty("user.dir")+File.separator+ Settings.configuration_dir);
			if(!dir.exists()) {
				System.err.println(Monitor3DMessages.Monitor3DSettingsDirNotFound + ":" + dir.getAbsolutePath());
				System.exit(1);
			}
			File f= new File(dir.getAbsoluteFile()+File.separator+monitor3DSettingsFileName);
			if(!f.exists()) {
				System.err.println(Monitor3DMessages.Monitor3DSettingsFileNotFound+ ":" + f.getAbsolutePath());
				System.exit(1);
			}
			
			try {
				String content;
				content = new String(Files.readAllBytes(Paths.get(f.getAbsolutePath())));
				settings = Settings.jsonb.fromJson(content,Monitor3DSettings.class);

			} catch (Exception e) {
				System.err.println(Monitor3DMessages.LoggerParsingFailed + " : " +e.getMessage());
				System.exit(1);
//				e.printStackTrace();
			}
				
		}
		
		/**
		 * Instantiates a new logger settings.
		 */
		public Monitor3DSettings() {
			mappingClasse2Representation3D = new ArrayList<ObjTo3DMappingSettings>();
		}
		
		public List<ObjTo3DMappingSettings> mappingClasse2Representation3D;
		
		public double frameparseconde;

		public String entiteASuivre;
	

}
