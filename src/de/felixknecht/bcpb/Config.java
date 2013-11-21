package de.felixknecht.bcpb;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class Config extends Properties {

	private static final long serialVersionUID = 4275370643713701413L;

	public static final String APIKEY = "access_token";
	public static final String REMOTE_BACKUP_FOLDER = "remoteBackupFolder";
	public static final String LOCAL_BACKUP_FOLDER = "localBackupFolder";
	public static final String DELETE_AFTER_UPLOAD = "deleteAfterUpload";
	public static final String IGNORE_FILES_WITH_REGEXP = "ignoreFilesWithRegexp";
	
	
	
	public Config(String filename) throws FileNotFoundException, IOException {
		File file = new File("config.properties");
		if (!file.exists()) {
			System.out.println("Config file '"+ file.getAbsolutePath() + file.getName() + "' not found!");
		}
		load(new FileReader(file));
	}

}
