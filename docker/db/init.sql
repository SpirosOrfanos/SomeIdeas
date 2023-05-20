drop SCHEMA if exists cardcost;

CREATE SCHEMA cardcost ;

CREATE TABLE cardcost._user_seq (
  next_val bigint DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


CREATE TABLE cardcost.token_seq (
  next_val bigint DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


CREATE TABLE cardcost._user (
  id int NOT NULL,
  password varchar(255) DEFAULT NULL,
  role varchar(255) DEFAULT NULL,
  username varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;



CREATE TABLE cardcost.token (
  id int NOT NULL,
  expired bit(1) NOT NULL,
  revoked bit(1) NOT NULL,
  token varchar(255) DEFAULT NULL,
  token_type varchar(255) DEFAULT NULL,
  user_id int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKiblu4cjwvyntq3ugo31klp1c6` (`user_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;



CREATE TABLE clearance (
  id varchar(45) NOT NULL,
  amount` decimal(3,0) NOT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE cardcost.bin_info (
  id VARCHAR(45) NOT NULL,
  country VARCHAR(45) NOT NULL,
  PRIMARY KEY (id));


INSERT INTO cardcost._user (id, username, password, role) 
VALUES (1, 'adminuser', '$2a$10$H3Yd7DZvq4nzJMquALmAH.WxUnQWaO6T6AxvFtXm0mA3M.kgN14rm', 'BO');
INSERT INTO cardcost._user (id, username, password, role) 
VALUES (2, 'simpleuser', '$2a$10$GlciUEs1mBM9zqzW.AX9auIwqYQ.3Gf8.jdkqKys8a/1v0SoDbYqG', 'USER');
insert into cardcost._user_seq (next_val) values (101);
insert into cardcost.token_seq (next_val) values (101);

insert into cardcost.clearance (id, amount) values ("eg", 50);
insert into cardcost.clearance (id, amount) values ("us", 100);
insert into cardcost.clearance (id, amount) values ("other", 200);