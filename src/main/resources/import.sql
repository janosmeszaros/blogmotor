insert into bloguser (id, name, password, email, role) values (0, 'admin', 'admin', 'admin@admin.com', 'admin');
insert into taxonomy (id, name) values (0, 'news');
insert into post (id, post, title,postdate, taxonomy_id, owner_id) values (0, 'This is an example post. Only blogger and admin can add post to blog. So if You have only user role you can only comment posts.', 'First post', 'now', 0, 0);
insert into comment (id, comment, commentdate, owner_id, post_id) values (nextval('hibernate_sequence'), 'first comment', 'now', 0, 0);