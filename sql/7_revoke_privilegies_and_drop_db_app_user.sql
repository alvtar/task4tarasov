REVOKE ALL PRIVILEGES
ON `periodicals`.* 
FROM 'app_periodicals'@'localhost';

DROP USER 'app_periodicals'@'localhost';


REVOKE ALL PRIVILEGES 
ON `periodicals`.* 
FROM 'app_periodicals'@'%';

DROP USER 'app_periodicals'@'%';

