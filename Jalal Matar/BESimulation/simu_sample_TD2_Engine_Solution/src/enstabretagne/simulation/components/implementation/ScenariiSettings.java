package enstabretagne.simulation.components.implementation;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import enstabretagne.base.Settings;
import enstabretagne.simulation.components.messages.ScenariiMessages;

public class ScenariiSettings {
	/** The Constant loggerSettingsFileName. */
	private static final String scenariiSettingsFileName = "scenarii_settings.json";
	
	public static ScenariiSettings settings;
	
	static {
		
		File dir= new File(System.getProperty("user.dir")+File.separator+ Settings.configuration_dir);
		if(!dir.exists()) {
			System.err.println(ScenariiMessages.ScenariiSettingsDirNotFound + ":" + dir.getAbsolutePath());
			System.exit(1);
		}
		File f= new File(dir.getAbsoluteFile()+File.separator+scenariiSettingsFileName);
		if(!f.exists()) {
			System.err.println(ScenariiMessages.ScenariiSettingsFileNotFound+ ":" + f.getAbsolutePath());
			System.exit(1);
		}
		
		try {
			String content;
			content = new String(Files.readAllBytes(Paths.get(f.getAbsolutePath())));
			settings = Settings.jsonb.fromJson(content,ScenariiSettings.class);
			
			

		} catch (Exception e) {
			System.err.println(ScenariiMessages.ScenariiParsingFailed + " : " +e.getMessage());
			System.exit(1);
//			e.printStackTrace();
		}
			
	}
	
	public long nbRepliques;
	public long germeInitial;
	public List<String> scenarioInstanceClassNames;
	
	public ScenariiSettings()
	{
		scenarioInstanceClassNames = new ArrayList<>(); 
	}

}
