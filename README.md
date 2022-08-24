# VendingMachine

// git add .
// git commit -m "{message}"
// git push .

For MySQL Instruction:

CREATE USER 'vending' IDENTIFIED BY 'VHHSdemo@128';
GRANT PRIVILEGE ON \* TO 'vending';

GRANT ALL PRIVILEGES ON _._ TO 'vending' WITH GRANT OPTION;

FLUSH PRIVILEGES;

mysql -uvending -p

CREATE DATABASE vendingdb;
