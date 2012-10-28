aztec
=====

Collection of my demo projects, including Hibernate with Spring JPA, Spring JDBC, Spring Transactions, Spring Data with MongoDB, REST with SpringMVC, REST with JAX-RS, SOAP with SpringWS, Spring Social.  Maven projects, integration testing on various projects demonstrate using Groovy, SoapUI and Jersey.

- springdemo:

Collection of Spring module demoes, including Hibernate with JPA, and JDBC.

- mongodb:

MongoDB Spring Data demo.  Requires a running Mongo instance for the test.  See the Unit test for details.

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

SOAP Controller with Spring Web Services.  The service is a Map for create/get/update/remove.  Includes SoapUI project for integration testing.

Open SoapUI and import project src/tests/resources/soapdemo-soapui-project.xml.  On command line deploy war with mvn tomcat:run, and run SoapUI test cases within SoapUI.  Alternatively run mvn clean install, which will run the SoapUI tests as part of the integration-test phase using the maven tomcat and SoapUI plugins.

- jaxrsdemo:

REST Controller component, with Jax-Rs using Jersey.  The service is a Map for create/get/update/remove.  Includes a Jersey client for integration testing.  Run as part of the mvn clean install.