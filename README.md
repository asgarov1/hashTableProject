# Hash Table Project

This is a CLI app that allows to manage Aktie files stored inside of a hashtable.

Available commands are: <br/>
ADD | DELETE | IMPORT | SEARCH | PLOT | SAVE [file_name] | LOAD [file_name] | QUIT | 

The commands are case insensitive. Since there is a hashtable serialised and saved already you can
start right away with the load option to have hashtable (hashtable.txt) loaded from resources file and right away start 
using the options of SEARCH, PLOT and others (Presaved Aktien are: Apple, Amazon, Intel and Microsoft).

Alternatively you can choose to not load the hashtable and use the hashtable from clean slate. 

While commands themselves are case insensitive, the nature of the hash functions makes NAME field 
(the field that is used for hashing) case sensitive, and you have to enter is exactly the same for search and import.
Even you you need to enter the Name field for all commands it is the Kuerzel that is going to be used to look for 
.csv file to import.Therefore .csv file's name should match the Kuerzel of the Aktie and also needs to be located at
src/resources/ folder.

Have fun using this program.

Created by: <br>
Javid Asgarov & <br>
Agnaldo Oliveira Moura Junior