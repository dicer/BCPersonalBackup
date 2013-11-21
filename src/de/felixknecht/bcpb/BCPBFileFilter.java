package de.felixknecht.bcpb;

import java.io.File;
import java.io.FilenameFilter;

public class BCPBFileFilter implements FilenameFilter {

	private String ignoreFileRexexp;

	public BCPBFileFilter(Config config) {
		ignoreFileRexexp = config.getProperty(Config.IGNORE_FILES_WITH_REGEXP);
	}

	@Override
	public boolean accept(File dir, String name) {

		if (ignoreFileRexexp == null || ignoreFileRexexp.isEmpty()) {
			return true;
		}

		return !name.matches(ignoreFileRexexp);
	}
}
