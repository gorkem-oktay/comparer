# Comparer

It is a java console application that compares two ftp server files of web projects and databases for added and changed items

## FTP

### Configs
To configure server information, there are two json file in config folder. Just change information there.
    
    {
    	"name": "NAME_OF_THE_SERVER",
    	"url": "URL",
    	"username": "USERNAME",
    	"password": "PASSWORD",
    	"pathes": ["PATHES_TO_COMPARE"]
    }

Output files' styles are at style folder. Can be edited to suit your needs.

### Commands
    cmp [-of]
Compares files and shows which files are added and changed. With -of options, it writes outputs to a result file with given style.

    upload [-all|-added|-changed] | <filename>*
Uploads files with given options or directly with given names

## DB

### Configs
To configure server information, there are two json file in config folder. Just change information there. If you don't need ssh connection, just fill it empty or don't add.
    
    {
      "type": "DATABASE_TYPE",
      "host": "HOST",
      "port": PORT,
      "database": "DATABASE_NAME",
      "username": "USERNAME",
      "password": "PASSWORD",
      "ssh": {
        "host": "SSH_HOST",
        "port": "SSH_PORT",
        "user": "USER",
        "password": "PASSWORD",
        "private_key": "PRIVATE_KEY_PATH"
      }
    }

Output files' styles are at style folder. Can be edited to suit your needs.

### Commands
    cmp [-all|-table|-view] [-of]
Compares tables and shows which tables are added and changed. With -of options, it writes outputs to a result file with given style.