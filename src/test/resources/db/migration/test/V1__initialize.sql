USE `test`;
DROP TABLE IF EXISTS `users`;
CREATE TABLE `users` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `username` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `enabled` tinyint DEFAULT NULL,
  `account_non_expired` tinyint DEFAULT NULL,
  `credentials_non_expired` tinyint DEFAULT NULL,
  `account_non_locked` tinyint DEFAULT NULL,
  `first_name` varchar(255) DEFAULT NULL,
  `last_name` varchar(255) DEFAULT NULL,
  `birth_date` datetime DEFAULT NULL,
  `city` varchar(255) DEFAULT NULL,
  `last_visit` varchar(255) DEFAULT NULL,
  `current_book_id` int DEFAULT NULL,
  PRIMARY KEY (`id`)
);

DROP TABLE IF EXISTS `roles`;
CREATE TABLE `roles` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
);

DROP TABLE IF EXISTS `users_roles`;
CREATE TABLE `users_roles` (
  `user_id` int unsigned NOT NULL,
  `role_id` int unsigned NOT NULL
);

INSERT INTO `users`
(`id`,
`username`,
`password`,
`enabled`,
`account_non_expired`,
`credentials_non_expired`,
`account_non_locked`,
`first_name`,
`last_name`,
`birth_date`,
`city`,
`last_visit`,
`current_book_id`)
VALUES
(1,
'test@test.com',
'$2a$10$ts2FXg1jVvuuEIJqIItTB.Ra1ZklHcSYyrnt3AkGVJkekFUjWcu9K',
1,
1,
1,
1,
'first-name',
'last-name',
'1981-10-21',
'Москва',
'2021-01-06',
1);

INSERT INTO `roles` (`id`, `name`) VALUES (1, 'ROLE_ADMIN');
INSERT INTO `roles` (`id`, `name`) VALUES (2, 'ROLE_USER');

INSERT INTO `users_roles` (`user_id`, `role_id`) VALUES (1, 1);