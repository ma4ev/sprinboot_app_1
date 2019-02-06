DELETE FROM user_role;
DELETE FROM usr;

INSERT INTO usr(id, active, password, username, email) VALUES
  (1, true, '$2a$08$31NC8FDbo2YEozig6EdNmu6It4eSUrFmspls2oXJnqU2/bgJ6f.pm', 'userAdm', 'libuvam@shop4mail.net'),
  (2, true, '$2a$08$31NC8FDbo2YEozig6EdNmu6It4eSUrFmspls2oXJnqU2/bgJ6f.pm', 'simpleUser', 'libuvam@shop4mail.net');
-- pass is 'qqq'!!!

INSERT INTO user_role(user_id, roles) VALUES
(1, 'USER'),
(1, 'ADMIN'),
(2, 'USER')