package de.felixknecht.bcpb;

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.bitcasa.javalib.BitcasaClient;
import com.bitcasa.javalib.dao.BitcasaFolder;
import com.bitcasa.javalib.dao.BitcasaItem;
import com.bitcasa.javalib.exception.BitcasaException;
import com.bitcasa.javalib.http.BitcasaHttpRequestor.FileExistsOperation;
import com.bitcasa.javalib.http.Uploader;

public class BCPersonalBackup {

	private static BitcasaFolder bitcasaFolder;
	private static Config config;
	private static boolean deleteAfterUpload;

	/**
	 * @param args
	 * @throws BitcasaException
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException, BitcasaException {

		config = new Config("config.properties");

		deleteAfterUpload = Boolean.parseBoolean(config.getProperty(Config.DELETE_AFTER_UPLOAD, "false"));

		BitcasaClient client = new BitcasaClient(config.getProperty(Config.APIKEY));

		bitcasaFolder = new BitcasaFolder();
		bitcasaFolder.setPath("/");

		List<BitcasaItem> createFolder = client.createFolder(bitcasaFolder, config.getProperty(Config.REMOTE_BACKUP_FOLDER));
		BitcasaFolder backupFolder = (BitcasaFolder) createFolder.get(0);

//		List<BitcasaItem> itemsInFolder = client.getItemsInFolder(backupFolder);
//
//		for (BitcasaItem bitcasaItem : itemsInFolder) {
//			System.out.println(bitcasaItem.getName());
//		}

		File backupDir = new File(config.getProperty(Config.LOCAL_BACKUP_FOLDER));

		uploadFiles(client, backupFolder, backupDir);
	}

	private static void uploadFiles(BitcasaClient client, BitcasaFolder remoteFolder, File localFolder) throws BitcasaException, IOException {

		File[] listFiles = localFolder.listFiles(new BCPBFileFilter(config));

		for (int i = 0; i < listFiles.length; i++) {
			File file = listFiles[i];

			if (file.isDirectory()) {
				handleFolder(client, remoteFolder, file);
				continue;
			}

			System.out.println("Uploading file '" + file.getAbsolutePath() + file.getAbsoluteFile() + "'");

			Uploader uploader = new Uploader(remoteFolder, file);
			uploader.ifFileExistsDo(FileExistsOperation.RENAME);
			List<BitcasaItem> uploadFile = client.uploadFile(uploader);
			System.out.println("Uploaded to '" + uploadFile.get(0).getPath() + uploadFile.get(0).getName() + "'");

			if (deleteAfterUpload) {
				file.delete();
			}
		}
	}

	private static void handleFolder(BitcasaClient client, BitcasaFolder remoteFolder, File file) throws IOException, BitcasaException {
		List<BitcasaItem> createFolder = client.createFolder(remoteFolder, file.getName());
		BitcasaFolder newRemoteFolder = (BitcasaFolder) createFolder.get(0);
		System.out.println("Created folder '" + newRemoteFolder.getName() + "'");
		uploadFiles(client, newRemoteFolder, file);
		if (deleteAfterUpload) {
			file.delete();
		}
	}
}
