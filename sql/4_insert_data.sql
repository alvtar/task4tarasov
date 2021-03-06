USE `periodicals`;


INSERT INTO `users` (`login`,`password`,`role`,`fullName`,`zipCode`,`address`) VALUES
("admin", "admin", 0, "Администратор системы", 0, "admin@periodicals.vitebsk.by"),
("user1", "111",   1, "Тарасов А.В.", 210017,"ул. Гагарина, д.102, кв. 15"),
("user2", "222",   1, "Шубин А.В.", 210015,"Московский пр-т, 84-4-12"),
("user3", "333",   1, "Грищенко О.П.", 210029,"ул. Смоленская, д.4, к.3, кв.5"),
("user4", "444",   1, "Сидорова С.С.", 210038,"ул. П.Бровки, 10-3"),
("user5", "555",   1, "Петров А.А.", 210035,"пр-т Строителей, д.60, кв.15"),
("user6", "666",   1, "Васечкин В.В", 210029,"ул. Правды, д.46, к.3, кв. 75"),
("user7", "777",   1, "Селезнева А.В.", 210015,"ул. Ленина, д.20, кв. 6"),
("user8", "888",   1, "Гусев К.П.", 210017,"ул. Гагарина, д.98, кв. 1"),
("user9", "999",   1, "Весельчаков Н.А.", 210038,"Московский пр-т, 105-88"),
("user10", "000",  1, "Котиков А.А.", 210009,"ул. Космонавтов, д.10, кв. 24");


INSERT INTO `catalogue`(`issn`,`title`,`monthCost`,`active`) VALUES
(63889, "газета Советская Белоруссия", 6.6, 1), 
(74826, "журнал Бухгалтерский учет и анализ", 7.87, 1),
(74908, "журнал Мишутка", 0.99, 1),
(74996, "журнал Радиолюбитель", 5.96, 1),
(63271, "газета Белорусы и рынок", 5.31, 1),
(63173, "газета Бабушкины сказки", 7.66, 1),
(63355, "газета Комсомольская правда в Белоруссии", 13.99, 1),
(77777, "журнал Мурзилка", 1.2, 0),
(908, "журнал Ветеринарное дело", 28.55, 1),
(802, "журнал Эпизоотология, иммунобиология, фармакология и санитария", 7, 1),
(749232, "журнал Вестник Академии МВД Республики Беларусь", 13.33, 1);


INSERT INTO `subscription`(`user_id`,`issn_id`,`subsYear`,`subsMonths`,`paymentSum`) VALUES
(1,1,2017,123,333),
(2,3,2017,567,777),
(4,7,2017,11234,37),
(6,7,2017,11234,37),
(8,7,2017,11234,37);

/*(`select login from users where id=7`,1,2016,123,555);*/


