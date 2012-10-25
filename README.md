aztec
=====

Collection of Spring demo projects, including Hibernate with Spring JPA, Spring JDBC, Spring Transactions, Spring Data with MongoDB, REST with SpringMVC, SOAP with SpringWS, Spring Social.  Maven projects, integration testing with Groovy and SoapUI.

- springdemo:

Collection of Spring module demoes, including Hibernate with JPA, and JDBC.

- mongodb:

MongoDB Spring Data demo.  Requires a running Mongo instance.  See the Unit test for details.

- social:

Spring Social, with Twitter.

Note that the Twitter app requires real credentials.  Add a src/main/resources/application.properties with the following:

twitter.api.key=foo
twitter.api.secret=bar
twitter.access.token=foo
twitter.access.token.secret=bar

...substituting foo/bar with genuine credentials.  If necessary, create a new Twitter app here, to generate these credentials: https://dev.twitter.com/apps

- restdemo:

REST Controller with SpringMVC.  The service is a Map for create/get/update/remove.  Integration testing with Groovy.

- soapdemo:

SOAP Controller with Spring Web Services.  The service is a Map for create/get/update/remove.  Includes SoapUI project for testing.

Open SoapUI and import project src/tests/resources/soapdemo-soapui-project.xml.  On command line deploy war with mvn tomcat:run, and run SoapUI test cases.