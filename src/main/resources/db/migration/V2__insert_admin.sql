INSERT INTO public.usr (id, active, password, username, email, activation_code) VALUES (1, true, '$2a$08$WFp0psgrxGKym1vT49ZFt.EEhuIBqIeUIpThmuz/SaGYKoGQsBJfG', 'admin', 'libuvam@shop4mail.net', null);

INSERT INTO public.user_role (user_id, roles) VALUES (1, 'USER');
INSERT INTO public.user_role (user_id, roles) VALUES (1, 'ADMIN');