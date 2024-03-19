use LOGIN; 
drop table if exists users;
create table users (
  id int primary key auto_increment,
  user_name varchar(50),
  pass varchar(50),
  email varchar(50),
  mobile varchar(20)
);