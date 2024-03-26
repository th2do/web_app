# Repo for a simple login web app

For the jdbc connector portion in `Login.java` and `RegistrationServlet.java`, one can simply replace that with 

    String url = "jdbc:mysql://{RDS Database Instance endpoint}:{port}/";
    String userName = "{username}";
    String password = "{password}";
    String dbName = "{db_name}"; 
    Connection con = DriverManager.getConnection(url + dbName, userName, password);

Update for SQL injection prevention and encoded password (could do the same for email)
