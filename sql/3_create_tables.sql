USE `periodicals`;


CREATE TABLE `users` (
    `id` INTEGER NOT NULL,
    `login` VARCHAR(32) NOT NULL UNIQUE,
    `password` VARCHAR(32) NOT NULL,
    /*
    * 0 - ADMIN
    * 1 - READER
    */
    `role` TINYINT NOT NULL,
    `fullName` VARCHAR(150) NOT NULL,
    `zipCode` INTEGER NOT NULL,
    `address` VARCHAR(250) NOT NULL
) ENGINE=InnoDB DEFAULT CHARACTER SET utf8;

/* MySQL ignores PRIMARY KEY CONSTRAINT Name */
ALTER TABLE `users` ADD CONSTRAINT `pk_users` PRIMARY KEY (`id`);
ALTER TABLE `users` CHANGE `id` `id` INTEGER NOT NULL AUTO_INCREMENT;



CREATE TABLE `catalogue` (
    `id` INTEGER NOT NULL,
    `issn` INTEGER NOT NULL UNIQUE,
    `title` VARCHAR(150) NOT NULL,
    `monthCost` FLOAT unsigned NOT NULL,
    `active` TINYINT NOT NULL,
    `lastUpdate` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARACTER SET utf8;

/* MySQL ignores PRIMARY KEY CONSTRAINT Name */
ALTER TABLE `catalogue` ADD CONSTRAINT `pk_catalogue` PRIMARY KEY (`id`);
ALTER TABLE `catalogue` CHANGE `id` `id` INTEGER NOT NULL AUTO_INCREMENT;



CREATE TABLE `subscription` (
    `id` INTEGER NOT NULL,
    `regDate` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    `user_id` INTEGER NOT NULL,
    `issn_id` INTEGER NOT NULL,
    `subsYear` YEAR NOT NULL,
    `subsMonths` TINYINT NOT NULL,
    `paymentSum` FLOAT unsigned NOT NULL
) ENGINE=InnoDB DEFAULT CHARACTER SET utf8;

/* MySQL ignores PRIMARY KEY CONSTRAINT Name */
ALTER TABLE `subscription` ADD CONSTRAINT `pk_subscription` PRIMARY KEY (`id`);
ALTER TABLE `subscription` CHANGE `id` `id` INTEGER NOT NULL AUTO_INCREMENT;

ALTER TABLE `subscription` ADD CONSTRAINT `fk_subscription_users_id` FOREIGN KEY (`user_id`) REFERENCES users(id);
ALTER TABLE `subscription` ADD CONSTRAINT `fk_subscription_catalogue_id` FOREIGN KEY (`issn_id`) REFERENCES catalogue(id);

