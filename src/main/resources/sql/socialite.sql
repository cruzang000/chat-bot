use socialite;

CREATE TABLE `Users` (
  `id` int PRIMARY KEY AUTO_INCREMENT,
  `username` int UNIQUE NOT NULL,
  `password` varchar(255) NOT NULL,
  `lastName` varchar(255) NOT NULL,
  `firstName` varchar(255) NOT NULL,
  `email` varchar(255) UNIQUE NOT NULL,
  `isAdmin` bit NOT NULL DEFAULT 0,
  `created` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP(),
  `modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP()
);

CREATE TABLE `Friends` (
  `id` int PRIMARY KEY AUTO_INCREMENT,
  `user_id_one` int NOT NULL,
  `user_id_two` int NOT NULL,
  `created` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP(),
  `modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP()
);

CREATE TABLE `UserSearches` (
  `id` int PRIMARY KEY AUTO_INCREMENT,
  `user_id` int NOT NULL,
  `zipcode` int NOT NULL,
  `city` varchar(50),
  `state` varchar(3),
  `created` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP(),
  `modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP()
);

CREATE TABLE `Locations` (
  `id` int PRIMARY KEY AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `yelpUrl` varchar(100) NOT NULL,
  `price` int NOT NULL,
  `created` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP(),
  `modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP()
);

CREATE TABLE `LocationsAddresses` (
  `id` int PRIMARY KEY AUTO_INCREMENT,
  `locations_id` int NOT NULL,
  `street` varchar(100) NOT NULL,
  `city` varchar(50) NOT NULL,
  `state` varchar(3) NOT NULL,
  `zipcode` int(5) NOT NULL,
  `created` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP(),
  `modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP()
);

CREATE TABLE `LocationRatings` (
  `id` int PRIMARY KEY AUTO_INCREMENT,
  `locations_id` int NOT NULL,
  `rating` int NOT NULL DEFAULT 0,
  `review_count` int NOT NULL DEFAULT 0,
  `created` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP(),
  `modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP()
);

CREATE TABLE `LocationCategories` (
  `id` int PRIMARY KEY AUTO_INCREMENT,
  `locations_id` int,
  `name` varchar(30),
  `created` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP(),
  `modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP()
);

CREATE TABLE `LocationHours` (
  `id` int PRIMARY KEY AUTO_INCREMENT,
  `locations_id` int NOT NULL,
  `startTime` time NOT NULL,
  `endTime` time NOT NULL,
  `created` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP(),
  `modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP()
);

CREATE TABLE `LocationImages` (
  `id` int PRIMARY KEY AUTO_INCREMENT,
  `locations_id` int NOT NULL,
  `img` varchar(200),
  `created` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP(),
  `modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP()
);

CREATE TABLE `UserPlans` (
  `id` int PRIMARY KEY AUTO_INCREMENT,
  `locations_id` int NOT NULL,
  `plan_time` time NOT NULL,
  `removed` bit NOT NULL DEFAULT 0,
  `created` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP(),
  `modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP()
);

CREATE TABLE `UserPlanInvites` (
  `id` int PRIMARY KEY AUTO_INCREMENT,
  `user_plans_id` int NOT NULL,
  `user_id` int NOT NULL,
  `attending` bit NOT NULL DEFAULT 0,
  `closed` bit NOT NULL DEFAULT 0,
  `isMain` bit NOT NULL DEFAULT 0,
  `message` text,
  `created` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP(),
  `modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP()
);

CREATE TABLE `UserFriendRequests` (
  `id` int PRIMARY KEY AUTO_INCREMENT,
  `user_id_one` int NOT NULL,
  `user_id_two` int NOT NULL,
  `closed` bit NOT NULL DEFAULT 0,
  `accepted` bit NOT NULL DEFAULT 0,
  `created` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP(),
  `modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP()
);

ALTER TABLE `UserSearches` ADD FOREIGN KEY (`user_id`) REFERENCES `Users` (`id`);

ALTER TABLE `LocationsAddresses` ADD FOREIGN KEY (`locations_id`) REFERENCES `Locations` (`id`);

ALTER TABLE `LocationCategories` ADD FOREIGN KEY (`locations_id`) REFERENCES `Locations` (`id`);

ALTER TABLE `LocationHours` ADD FOREIGN KEY (`id`) REFERENCES `Locations` (`id`);

ALTER TABLE `UserPlanInvites` ADD FOREIGN KEY (`user_plans_id`) REFERENCES `UserPlans` (`id`);

ALTER TABLE `UserPlanInvites` ADD FOREIGN KEY (`user_id`) REFERENCES `Users` (`id`);

ALTER TABLE `Friends` ADD FOREIGN KEY (`user_id_one`) REFERENCES `Users` (`id`);

ALTER TABLE `Friends` ADD FOREIGN KEY (`user_id_two`) REFERENCES `Users` (`id`);

ALTER TABLE `LocationImages` ADD FOREIGN KEY (`locations_id`) REFERENCES `Locations` (`id`);

ALTER TABLE `LocationRatings` ADD FOREIGN KEY (`locations_id`) REFERENCES `Locations` (`id`);

ALTER TABLE `UserFriendRequests` ADD FOREIGN KEY (`user_id_one`) REFERENCES `Users` (`id`);

ALTER TABLE `UserFriendRequests` ADD FOREIGN KEY (`user_id_two`) REFERENCES `Users` (`id`);
