# ReloadingLog
Java Swing application to log reloads. This is not intended to replace a reloading manual. Please understand and follow all safety rules.

The reloading log is meant to be datastore independent.  All data access methods are abstractly defined in com.reloadinglog.factory.Factory.  To change the datastore, extend the Factory class and implement the methods to fit your datastore.  Currently, I am working on an XML version, a JSON version and a database version using hibernate.  Currently, the XML and JSon versions are pretty stable. 

To use the ReloadingLog or the TargetEvaluator:
Create directory C:\reloadingLog\configuration
Download the appropropriate configuration file to the directory above and rename it to ReloadingLogConfiguration.properties.

Download the appropropriate datafiles and edit the properties file above to point to them.

Download the executable jar files and double click on them.  They should launch the application.


