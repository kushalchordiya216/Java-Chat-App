/*CREATE TABLE `Users` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) DEFAULT NULL UNIQUE,
  `password` char(64) DEFAULT NULL,
  `loggedIn` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`id`)
)



ALTER TABLE Users 
ADD CONSTRAINT username UNIQUE(name)



CREATE TABLE `Channels` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
)



CREATE TABLE `Messages` (
  `senderId` int(11) DEFAULT NULL,
  `channelId` int(11) DEFAULT NULL,
  `content` mediumtext,
  `time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  KEY `senderId` (`senderId`),
  KEY `channelId` (`channelId`),
  CONSTRAINT `Messages_ibfk_1` FOREIGN KEY (`senderId`) REFERENCES `Users` (`id`),
  CONSTRAINT `Messages_ibfk_2` FOREIGN KEY (`channelId`) REFERENCES `Channels` (`id`) ON DELETE CASCADE
)


CREATE TABLE `UserChannels` (
  `userId` int(11) DEFAULT NULL,
  `channelId` int(11) DEFAULT NULL,
  KEY `userId` (`userId`),
  KEY `channelId` (`channelId`),
  CONSTRAINT `UserGroups_ibfk_1` FOREIGN KEY (`userId`) REFERENCES `Users` (`id`),
  CONSTRAINT `UserGroups_ibfk_2` FOREIGN KEY (`channelId`) REFERENCES `Channels` (`id`) ON DELETE CASCADE
)

CREATE TABLE `MultimediaMessages` (
  `senderId` int(11) DEFAULT NULL,
  `channelId` int(11) DEFAULT NULL,
  `content` blob,
  `time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  KEY `senderId` (`senderId`),
  KEY `ChannelId` (`channelId`),
  CONSTRAINT `MultimediaMessages_ibfk_1` FOREIGN KEY (`senderId`) REFERENCES `Users` (`id`),
  CONSTRAINT `MultimediaMessages_ibfk_2` FOREIGN KEY (`channelId`) REFERENCES `Channels` (`id`) ON DELETE CASCADE
)*/
