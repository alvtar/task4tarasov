CREATE USER 'app_periodicals'@'localhost' IDENTIFIED BY 'app_pass711';

GRANT SELECT,INSERT,UPDATE,DELETE
ON `periodicals`.*
TO 'app_periodicals'@'localhost';

CREATE USER 'app_periodicals'@'%' IDENTIFIED BY 'app_pass711';

GRANT SELECT,INSERT,UPDATE,DELETE
ON `periodicals`.*
TO 'app_periodicals'@'%';
