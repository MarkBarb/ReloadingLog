# ReloadingLog
Java Swing application to log reloads. This is not intended to replace a reloading manual. Please understand and follow all safety rules.

The reloading manual is meant to be datastore independent.  All data access methods are abstractly defined in com.reloadinglog.factory.Factory.  To change the datastore, extend the Factory class and implement the methods to fit your datastore.

Currently, the XMLFactory is working.  The JSONFactory needs to have the firearms methods implemented.  The SQLFactory needs to have the Firearms methods implemented and there are issues saving reloads.

Usage: java com.reloading.ReloadingLogBrowser <properties filename>
The configuration file will need a line in it like:
FACTORYCLASS=com.reloading.xml.XmlFactory

The browser will use introspection and create an instance of that factory.  It will also then pass the resource bundle from the property file back to the Factory.  In the case of the XmlFactory, there are also paths to the xml files where the components are stored.

There is also a target evaluator. The evaluator can be launched from the reloading log or it can be ran as a standalone app. 

