USE `periodicals`;


CREATE TABLE `users` (
    `id` INTEGER NOT NULL,
    `login` VARCHAR(32) NOT NULL UNIQUE,
    `password` VARCHAR(32) NOT NULL,
    /*
    * 0 - ADMINISTRATOR
    * 1 - SUBSCRIBER
    */
    `role` TINYINT NOT NULL,
    `fullName` VARCHAR(150) NOT NULL,
    `zipCode` INTEGER NOT NULL,
    `address` VARCHAR(250) NOT NULL
) ENGINE=InnoDB DEFAULT CHARACTER SET utf8;

/* MySQL ignores PRIMARY KEY CONSTRAINT Name */
ALTER TABLE `users` ADD CONSTRAINT `pk_users` PRIMARY KEY (`id`);
ALTER TABLE `users` CHANGE `id` `id` INTEGER NOT NULL AUTO_INCREMENT;



CREATE TABLE `publications` (
    `id` INTEGER NOT NULL,
    `issn` INTEGER NOT NULL UNIQUE,
    `title` VARCHAR(150) NOT NULL,
    `monthCost` FLOAT unsigned NOT NULL,
    `active` TINYINT NOT NULL,
    `lastUpdate` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARACTER SET utf8;

/* MySQL ignores PRIMARY KEY CONSTRAINT Name */
ALTER TABLE `publications` ADD CONSTRAINT `pk_publications` PRIMARY KEY (`id`);
ALTER TABLE `publications` CHANGE `id` `id` INTEGER NOT NULL AUTO_INCREMENT;



CREATE TABLE `subscriptions` (
    `id` INTEGER NOT NULL,
    `regDate` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    `userId` INTEGER NOT NULL,
    `publicationId` INTEGER NOT NULL,
    `subsYear` INTEGER NOT NULL,
    `subsMonths` INTEGER NOT NULL,
    `paymentSum` FLOAT unsigned NOT NULL
) ENGINE=InnoDB DEFAULT CHARACTER SET utf8;

/* MySQL ignores PRIMARY KEY CONSTRAINT Name */
ALTER TABLE `subscriptions` ADD CONSTRAINT `pk_subscriptions` PRIMARY KEY (`id`);
ALTER TABLE `subscriptions` CHANGE `id` `id` INTEGER NOT NULL AUTO_INCREMENT;

ALTER TABLE `subscriptions` ADD CONSTRAINT `fk_subscriptions_users_id` FOREIGN KEY (`userId`) 
REFERENCES users(id) ON UPDATE CASCADE ON DELETE RESTRICT;

ALTER TABLE `subscriptions` ADD CONSTRAINT `fk_subscriptions_publications_id` FOREIGN KEY (`publicationId`) 
REFERENCES publications(id) ON UPDATE CASCADE ON DELETE RESTRICT;

