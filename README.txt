STEPS TO CONFIGURE THE DATABASE / APPLICATION
---------------------------------------------

1) The database needs to be running on Localhost:3306 (Default MySQL Config). The AbstractDao class has all the 
   configuration parameters hardwired in it. (Quick and dirty). Here are the parameters:
   
    URL: jdbc:mysql://localhost/" + SCHEMA_NAME, USER_NAME, PASSWORD
   
    ( The below parameters will be configured with step 2 )   
   	private static final String SCHEMA_NAME = "xnerd";
	private static final String USER_NAME = "xuser";
	private static final String PASSWORD = "password";
   

2) Once the database is configured on the machine with the Servlet container. Run this SQL script:
   
   create_users_RUN_FIRST.sql
   
   ...that script will add the user. That script only needs to be run once.
   

3) The third step is to create the schema and test data. Run this SQL script:

   xnerdbox_schema.sql
   
   ...that script will create the schema and the test data. That script can be re-run whenever you want a fresh database to work with.
   

4) Build the WAR. run "ant build" from the xnerd project directory (or install the supplied WAR)


5) Install the WAR and you are done! (Note: Only tested on Tomcat 7) URL:

   http://localhost:8080/xnerd/Xnerd.html
   

6) If the application can connect to your database correctly, you should see the menu and "Games we want..." screen immediately.


   
   
   
   
   
    