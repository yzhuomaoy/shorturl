CREATE TABLE `shorturl` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `code` varchar(20) UNIQUE,
  `url` varchar(256) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE INDEX IDX_SHORTURL_CODE ON shorturl (code);