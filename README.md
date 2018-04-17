# Zipconnector Anypoint Connector

[Connector description including destination service or application with]

# Mule supported versions
Examples:
Mule 3.4.x, 3.5.x
Mule 3.4.1

# [Destination service or application name] supported versions
Example:
Oracle E-Business Suite 12.1 and above.

#Service or application supported modules
Example:
Oracle CRM
Oracle Financials
or 
Salesforce API v.24
Salesforce Metadata API


# Installation 
For beta connectors you can download the source code and build it with devkit to find it available on your local repository. Then you can add it to Studio

For released connectors you can download them from the update site in Anypoint Studio. 
Open Anypoint Studio, go to Help → Install New Software and select Anypoint Connectors Update Site where you’ll find all avaliable connectors.

#Usage
For information about usage our documentation at http://github.com/mulesoft/zipconnector.

# Reporting Issues

We use GitHub:Issues for tracking issues with this connector. You can report new issues at this link http://github.com/mulesoft/zipconnector/issues.

This connector can be used to convert one or more files into a zip.
The payload may be a byte array of file, inputstream or list of byte array(each byte representing one file)
The "fileNames" property needs to be set with the all the filename used in the zip process with comma separated e.g abc.txt,test.txt,sample.xml
The first filename from fileNames always used for the payload content the it used sequentially for the additionalContentVariableNames file content.
The "additionalContentVariableNames" property needs to be set if you are using flow variable to store file content or string data that you want to be as a file inside the zip.
