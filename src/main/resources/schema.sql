CREATE TABLE IF NOT EXISTS app_user (
  user_id integer NOT NULL,
  username varchar(45) NOT NULL,
  password varchar(450) NOT NULL,
  enabled integer NOT NULL DEFAULT '1',
  PRIMARY KEY (user_id)
);