# Repo for a simple login web app

For the jdbc connector portion in `Login.java` and `RegistrationServlet.java`, one can simply replace that with 

    String url = "jdbc:mysql://{RDS Database Instance endpoint}:{port}/";
    String userName = "{username}";
    String password = "{password}";
    String dbName = "{db_name}"; 
    Connection con = DriverManager.getConnection(url + dbName, userName, password);

# Update
1. Unique user email constraint 
2. SQL injection prevention using prepared statement
3. Storing encrypted password instead of plain text password in database (could do the same for email)

# Thoughts for future improvements
1. Email confirmation
2. Secure cookie
3. HTTPS
