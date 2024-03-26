use LOGIN; 
drop table if exists users;
create table users (
  id int primary key auto_increment,
  user_name varchar(50),
  hashed_pass varchar(60),
  email varchar(50),
  mobile varchar(20)
);
