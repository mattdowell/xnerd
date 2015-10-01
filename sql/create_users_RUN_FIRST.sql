-- You will have to run this as root, first, then forget about it.
-- All other processes run as this user.

GRANT ALL PRIVILEGES ON *.* TO 'xuser'@'localhost' IDENTIFIED BY 'password' WITH GRANT OPTION;
GRANT ALL PRIVILEGES ON *.* TO 'xuser'@'localhost.localdomain' IDENTIFIED BY 'password' WITH GRANT OPTION;

-- create database xnerd;