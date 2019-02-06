DELETE FROM message;

INSERT INTO message(id, filename, tag, text, user_id) VALUES
(1, 'filename1', 'tag1', 'text1', 1),
(2, 'filename2', 'tag1', 'text2', 1),
(3, 'filename3', 'tag3', 'text3', 1),
(4, 'filename4', 'tag4', 'text4', 1);

ALTER sequence hibernate_sequence restart with 10;
