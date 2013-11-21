BCPersonalBackup
================

Personal backup tool to upload files from a folder to Bitcasa. Use this to point this tool to a folder on your hard drive where you copy files that should be backed up/moved to the cloud.
It currently does NOT sync files! With each run it will reupload the file and create "filename (1).xxx", "filename (2).xxx" etc. The main purpose of this is the following:

- Look in a folder for files to upload
- Mirror the folder structure on Bitcasa and upload all files
- Delete uploaded files locally

So you could run a backup, encrypt it and then move it into the folder this tool watches. 

You can also specify a regexp to ignore certain files. This can be used to protect you files from being uploaded while they are still copied to the upload folder. 
Eg with a regexp to ignore files ending in .part, you can do "cp /home/test/mybackupfile.gpg /_backup/mybackupfile.gpg.part && mv /_backup/mybackupfile.gpg.part /_backup/mybackupfile.gpg"


Usage:
======

- Needs bitcasa-sdk-java in the classpath
- Copy config.properties.template to config.properties
- Edit config.properties
- Run (eclipse .props provided, no arguments needed)

Be careful with the deleteAfterUpload as it really does what it says ;) Basically no code is implemented to handle errors yet. It should not delete files when an upload exception occurs, but this has not been tested!
This will also result in total disaster if you backup your root directory...


Auth:
=====

Currently it is not possible to authenticate with your normal Bitcasa login. You need to register an app at developer.bitcasa.com to get your client api keys. 
Then follow the instructions on https://developer.bitcasa.com/docs/auth and do everything manually in a browser until you got the access_token

Take extra care that you NEVER commit that access_token! The .git-ignore file is set to ignore the config.properties. So you should be safe if you do not modify the config loading code to load it from a different place inside your git repo 