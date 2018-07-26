# Comparer

It is a java console application that compares two ftp server files of web projects and databases for added and changed items

## Configs
To configure server information, there are two json file in config folder. Just change information there.
    
    {
    	"name": "NAME_OF_THE_SERVER",
    	"url": "URL",
    	"username": "USERNAME",
    	"password": "PASSWORD",
    	"pathes": ["PATHES_TO_COMPARE"]
    }

Output file styles are at style folder. Can be edited to suit your needs.

## FTP
### Commands
    cmp [-of]
Compares files and shows which files are added and changed. With -of options writes outputs to a result file with given style

    upload [-all|-added|-changed] | <filename>*
Uploads files with given options or directly with given names

##DB
### Commands