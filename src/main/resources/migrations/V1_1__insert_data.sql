--TRUNCATE TABLE users CASCADE;
--TRUNCATE TABLE roles CASCADE;
INSERT INTO roles (name)
VALUES
    ('ADMIN'),
    ('READER'),
    ('GUEST');
INSERT INTO users (first_name, last_name, email, password, phone_number, role_id)
VALUES
    ('Ivan', 'Ivanov', 'ivanIvanov1997@mail.ru', 'SilRt123Ivan96!', '+375333564524', (SELECT id FROM roles r WHERE r.name = 'ADMIN')),
    ('John', 'Snowman', 'JohnSnowman@gmail.com', 'SnowJ17723!!', '+441612345678', (SELECT id FROM roles r WHERE r.name = 'ADMIN')),
    ('Mathew', 'Patterson', 'PattersonM12@gmail.com', 'PattNew!Silb78', '+441611122444', (SELECT id FROM roles r WHERE r.name = 'ADMIN')),
    ('Marina', 'Petrova','PetrovaMarina992@mail.ru', 'Petrova2020!qaz', '+375291234321', (SELECT id FROM roles r WHERE r.name = 'READER')),
    ('Sergei', 'Sidorov', 'Sidorov12345Ser@gmail.com', 'SergeiBystqw65758!', '+375445672434', (SELECT id FROM roles r WHERE r.name = 'READER')),
    ('Maria', 'Bystrova', 'BystrM67184@yahoo.com', 'Silswwgehi123Bys!', '+48215369742', (SELECT id FROM roles r WHERE r.name = 'READER')),
    ('Pavel', 'Shery', 'PavelSh123@yahoo.com', 'Sh67281Pav!@', '+48215343846', (SELECT id FROM roles r WHERE r.name = 'READER')),
    ('Irene', 'Johnson', 'IreneJohnson@gmail.com', 'PasJohnIr321en!@3', '+441613947162', (SELECT id FROM roles r WHERE r.name = 'READER')),
    ('John', 'Doe', 'JohnDoe1I@yahoo.com', 'wgihwdlA1464!wts', '+12346726835', (SELECT id FROM roles r WHERE r.name = 'READER')),
    ('Ervin', 'Newman', 'Newman1Ervin@online.de', 'wgkwogKLDK!NewEr', '+492416743815', (SELECT id FROM roles r WHERE r.name = 'READER')),
    ('Julia', 'Schmidt', 'SchmidtJulia@gmail.com', 'Julia1233253wege#', '+492416746734', (SELECT id FROM roles r WHERE r.name = 'READER')),
    ('Hans', 'Dieter', 'DieterHans92@yahoo.com', 'HansWexw!23D', '+492036748356', (SELECT id FROM roles r WHERE r.name = 'READER')),
    ('Victoria', 'Huber', 'VikiHub123@yahoo.com', 'Hubwe!2324Vikq', '+48563842558', (SELECT id FROM roles r WHERE r.name = 'READER')),
    ('Maria', 'Bars', 'BarsMariaSt@list.ru', 'MashYRTS!@67', '+79112354657', (SELECT id FROM roles r WHERE r.name = 'READER')),
    ('Julia', 'Anders', 'AndersJulia25@gmail.com', 'Julia1233253and#11', '+492415624857', (SELECT id FROM roles r WHERE r.name = 'READER'));

/*TRUNCATE TABLE periodicals CASCADE;
TRUNCATE TABLE periodical_types CASCADE;
TRUNCATE TABLE periodical_categories CASCADE;
TRUNCATE TABLE periodical_statuses CASCADE;
TRUNCATE TABLE periodical_formats CASCADE;*/
INSERT INTO periodical_types (name)
VALUES
    ('MAGAZINE'),
    ('NEWSPAPER'),
    ('JOURNAL');

INSERT INTO periodical_categories (name)
VALUES
    ('ART_AND_ARCHITECTURE'),
    ('SCIENCE'),
    ('BUSINESS_AND_FINANCE'),
    ('NEWS_AND_POLITICS'),
    ('CULTURE_AND_LITERATURE'),
    ('TRAVEL_AND_OUTDOOR');

INSERT INTO periodical_statuses (name)
VALUES
    ('AVAILABLE'),
    ('UNAVAILABLE');

INSERT INTO periodicals (title, publisher, description, publication_date, issues_amount_in_year, price, language, image, type_id, category_id, status_id)
VALUES
    ('Frame', 'Frame Publishers',
    'Frame is a bi-monthly magazine dedicated to the design of interiors and products. It offers a stunning, global selection of shops, hospitality venues, workplaces, exhibitions and residences on more than 224 pages.
    Well-written articles accompanied by a wealth of high-quality photographs, sketches and drawings make the magazine an indispensable source of inspiration for designers as well as for all those involved in other creative disciplines.',
    '2022-09-01', 6, 24.48, 'English', 'frame_magazine.jpg', (SELECT pt.id FROM periodical_types pt WHERE pt.name = 'MAGAZINE'), (SELECT pc.id FROM periodical_categories pc WHERE pc.name = 'ART_AND_ARCHITECTURE'),
    (SELECT ps.id FROM periodical_statuses ps WHERE ps.name = 'AVAILABLE')),
    ('Watercolor Artist', 'Peak Media Properties, LLC',
    'Packed with page after gorgeous page of illustrations demonstrating tried-and-true techniques, inspirational ideas and the most up-to-date information about must-have painting tools and materials, watercolorists find everything they need in WATERCOLOR ARTIST to help them create stunning art...from start to finish.',
    '2022-09-01', 4, 19.60, 'English', 'watercolor_artist_magazine', (SELECT pt.id FROM periodical_types pt WHERE pt.name = 'MAGAZINE'), (SELECT pc.id FROM periodical_categories pc WHERE pc.name = 'ART_AND_ARCHITECTURE'),
    (SELECT ps.id FROM periodical_statuses ps WHERE ps.name = 'AVAILABLE')),
    ('Wallpaper', 'Future Publishing Ltd',
    'Truly international, consistently intelligent and hugely influential, Wallpaper* attracts the most sophisticated global audience by constantly pushing into new creative territories
    and ensuring its coverage of everything from architecture to motoring, fashion to travel, art to lifestyle, and interiors to jewelry remains unrivaled.',
    '2022-11-01', 12, 78.83, 'English', 'wallpaper_magazine.jpg', (SELECT pt.id FROM periodical_types pt WHERE pt.name = 'MAGAZINE'), (SELECT pc.id FROM periodical_categories pc WHERE pc.name = 'ART_AND_ARCHITECTURE'),
    (SELECT ps.id FROM periodical_statuses ps WHERE ps.name = 'AVAILABLE')),
    ('Nature', 'Nature Publishing Group',
    'Nature is the foremost international weekly scientific journal in the world and is the flagship journal for Nature Portfolio. It publishes the finest peer-reviewed research in all fields of science and technology on the basis of its originality,
    importance, interdisciplinary interest, timeliness, accessibility, elegance and surprising conclusions. Nature publishes landmark papers, award winning news, leading comment and expert opinion on important, topical scientific news and events that enable readers to share the latest discoveries in science and evolve the discussion amongst the global scientific community.',
    '2022-10-01', 51, 181.24, 'English', 'nature_journal.png', (SELECT pt.id FROM periodical_types pt WHERE pt.name = 'JOURNAL'), (SELECT pc.id FROM periodical_categories pc WHERE pc.name = 'SCIENCE'),
    (SELECT ps.id FROM periodical_statuses ps WHERE ps.name = 'AVAILABLE')),
    ('Astronomy', 'Kalmbach Publishing Co. - Magazines',
    'The world''s best-selling astronomy magazine offers you the most exciting, visually stunning, and timely coverage of the heavens above. Each monthly issue includes expert science reporting, vivid color photography, complete sky coverage, spot-on observing tips, informative telescope reviews, and much more! All this in a user-friendly style that''s perfect for astronomers at any level.',
    '2022-11-01', 12, 44.11, 'English', 'astronomy_magazine.jpg', (SELECT pt.id FROM periodical_types pt WHERE pt.name = 'MAGAZINE'), (SELECT pc.id FROM periodical_categories pc WHERE pc.name = 'SCIENCE'),
    (SELECT ps.id FROM periodical_statuses ps WHERE ps.name = 'AVAILABLE')),
    ('Leonardo', 'MIT Press',
    'Leonardo was founded in 1968 in Paris by kinetic artist and astronautical pioneer Frank Malina who saw the need for a journal to serve as an international channel of communication among artists, with emphasis on the writings of artists who use science and developing technologies in their work. ' ||
    'Published by The MIT Press, Leonardo has become the leading international peer-reviewed journal on the use of contemporary science and technology in the arts and music and the application and influence of the arts and humanities on science and technology.',
    '2022-11-01', 6, 150.27, 'English', 'leonardo_journal.jpg', (SELECT pt.id FROM periodical_types pt WHERE pt.name = 'JOURNAL'), (SELECT pc.id FROM periodical_categories pc WHERE pc.name = 'SCIENCE'),
    (SELECT ps.id FROM periodical_statuses ps WHERE ps.name = 'AVAILABLE')),
    ('Forbes', 'Forbes Media LLC',
    'Whether it’s reporting on the “next Facebook” or scrutinizing a new tax law, Forbes covers stories with uncanny insight and conciseness that hurried business folks appreciate. Get Forbes Digital Magazine Subscription today for rigorous, to-the-point business analysis.',
    '2022-08-01', 8, 29.40, 'English', 'forbes_magazine.jpg', (SELECT pt.id FROM periodical_types pt WHERE pt.name = 'MAGAZINE'), (SELECT pc.id FROM periodical_categories pc WHERE pc.name = 'BUSINESS_AND_FINANCE'),
    (SELECT ps.id FROM periodical_statuses ps WHERE ps.name = 'AVAILABLE')),
    ('The Economist', 'The Economist Newspaper Limited',
    'Authoritative global news and analysis. The Economist offers fair-minded, fact-checked coverage of world politics, economics, business, science, culture and more.',
    '2022-10-08', 51, 270.40, 'English', 'the_economist_newspaper.jpg', (SELECT pt.id FROM periodical_types pt WHERE pt.name = 'NEWSPAPER'), (SELECT pc.id FROM periodical_categories pc WHERE pc.name = 'BUSINESS_AND_FINANCE'),
    (SELECT ps.id FROM periodical_statuses ps WHERE ps.name = 'AVAILABLE')),
    ('Frankfurter Allgemeine Sonntagszeitung', 'Carsten Knop, Berthold Kohler',
    'Since 1949, FAZ has held firm their commitment to editorial independence and excellent journalistic quality; it provides thoroughly researched facts, precise analyzes, smart comments and positions capable of being discussed daily. With a distinctive editorial style, FAZ is never shy to express its own opinions, as well as initiate and influence social debate. FAZ is, without a doubt, a newspaper written by first-class journalists for readers with the highest demands.',
    '2022-10-02', 51, 150.45, 'German', 'faz_newspaper.jpg', (SELECT pt.id FROM periodical_types pt WHERE pt.name = 'NEWSPAPER'), (SELECT pc.id FROM periodical_categories pc WHERE pc.name = 'NEWS_AND_POLITICS'),
    (SELECT ps.id FROM periodical_statuses ps WHERE ps.name = 'AVAILABLE')),
    ('Time Magazine International Edition', 'Time Magazine UK Ltd',
    'Time Magazine International Edition is the go-to news magazine for what is happening around the globe. You can rely on TIME''s award winning journalists for analysis and insight into the latest developments in politics,business, health, science, society and entertainment.',
    '2022-10-07', 24, 60.23, 'English', 'time_magazine.jpg', (SELECT pt.id FROM periodical_types pt WHERE pt.name = 'MAGAZINE'), (SELECT pc.id FROM periodical_categories pc WHERE pc.name = 'NEWS_AND_POLITICS'),
    (SELECT ps.id FROM periodical_statuses ps WHERE ps.name = 'AVAILABLE')),
    ('Guardian Weekly', 'Guardian News & Media Limited',
    'The Guardian Weekly magazine is a round-up of the world news, opinion and long reads that have shaped the week. Inside, most memorable stories of the past seven days are reframed with striking photography and insightful companion pieces, all handpicked from The Guardian and The Observer.',
    '2022-10-07', 51, 98.54, 'English', 'guardian_magazine.jpg', (SELECT pt.id FROM periodical_types pt WHERE pt.name = 'MAGAZINE'), (SELECT pc.id FROM periodical_categories pc WHERE pc.name = 'NEWS_AND_POLITICS'),
    (SELECT ps.id FROM periodical_statuses ps WHERE ps.name = 'AVAILABLE')),
    ('Philosophy Now', 'Anja Publications Ltd',
    'Philosophy Now is a magazine for everyone interested in ideas. It isn''t afraid to tackle all the major questions of life, the universe and everything. It tries to corrupt innocent citizens by convincing them that philosophy can be exciting,
    worthwhile and comprehensible, and also to provide some light and enjoyable reading matter for those already ensnared by the muse, such as philosophy students and academics. It contains articles on all aspects of philosophy, plus book reviews, film reviews, news, cartoons, and the occasional short story.',
    '2022-08-01', 6, 21.56, 'English', 'philosophy_now_magazine.jpg', (SELECT pt.id FROM periodical_types pt WHERE pt.name = 'MAGAZINE'), (SELECT pc.id FROM periodical_categories pc WHERE pc.name = 'CULTURE_AND_LITERATURE'),
    (SELECT ps.id FROM periodical_statuses ps WHERE ps.name = 'AVAILABLE')),
    ('Creative Nonfiction', 'Creative Nonfiction',
    'Creative Nonfiction is the voice of the genre. Every issue includes long-form essays blending style with substance; writing that pushes the genre’s boundaries; commentary and notes on craft; conversations with writers; and more. Simply put, Creative Nonfiction demonstrates the depth and versatility of the genre it helped define.',
    '2022-06-01', 4, 24.51, 'English', 'creative_nonfiction_magazine.jpg', (SELECT pt.id FROM periodical_types pt WHERE pt.name = 'MAGAZINE'), (SELECT pc.id FROM periodical_categories pc WHERE pc.name = 'CULTURE_AND_LITERATURE'),
    (SELECT ps.id FROM periodical_statuses ps WHERE ps.name = 'AVAILABLE')),
    ('Ancient History Magazine', 'Karwansaray Publishers',
    'Ancient History looks at every aspect of the ancient world: you will find articles covering politics, society, literature, language, religion, economics, and art - all in one magazine! Like its big brother, Ancient Warfare, Ancient History Magazine is a bi-monthly, 60-page magazine that relies on a thematic approach: each issue is centered around one specific subject. From ancient Egyptian trade and Roman family life to the lost city of Pompeii, there is sure to be something for everyone - all presented in a well-researched but accessible, fun manner.',
    '2022-06-01', 6, 21.03, 'English', 'ancient_history.jpg', (SELECT pt.id FROM periodical_types pt WHERE pt.name = 'MAGAZINE'), (SELECT pc.id FROM periodical_categories pc WHERE pc.name = 'CULTURE_AND_LITERATURE'),
    (SELECT ps.id FROM periodical_statuses ps WHERE ps.name = 'AVAILABLE')),
    ('Mysterious Ways', 'Guideposts',
    'A brand-new magazine filled with true stories of extraordinary moments and everyday miracles that reveal a hidden spiritual force at work in our lives. These fascinating stories will entertain you and remind you that there is something more, something greater in our lives.',
    '2022-10-01', 6, 14.66, 'English', 'mysterious_ways_magazine.jpg', (SELECT pt.id FROM periodical_types pt WHERE pt.name = 'MAGAZINE'), (SELECT pc.id FROM periodical_categories pc WHERE pc.name = 'CULTURE_AND_LITERATURE'),
    (SELECT ps.id FROM periodical_statuses ps WHERE ps.name = 'AVAILABLE')),
    ('BirdWatching', 'Madavor Media, LLC',
    'BirdWatching is a must-read for anyone who loves birds, whether you are a casual birdwatcher or avid birder. Each issue includes articles by the best known, most respected names in birding, identification tips, spectacular photography, hands-on information about the best birding locations in North America, answers to intriguing reader questions, and much more.',
    '2022-11-01', 6, 26.42, 'English', 'bird_watching_magazine.jpg', (SELECT pt.id FROM periodical_types pt WHERE pt.name = 'MAGAZINE'), (SELECT pc.id FROM periodical_categories pc WHERE pc.name = 'TRAVEL_AND_OUTDOOR'),
    (SELECT ps.id FROM periodical_statuses ps WHERE ps.name = 'AVAILABLE')),
    ('Outdoor Life', 'Camden Media Inc.',
    'Our readers'' hands-on spirit is reflected in the magazine''s comprehensive gear tests and personal adventure stories. Whether shopping for a new rifle, searching for the hottest fishing holes this weekend or thirsting for exciting adventure tales, Outdoor Life is the ultimate resource.',
    '2022-05-01', 4, 11.76, 'English', 'outdoor_life_magazine.jpg', (SELECT pt.id FROM periodical_types pt WHERE pt.name = 'MAGAZINE'), (SELECT pc.id FROM periodical_categories pc WHERE pc.name = 'TRAVEL_AND_OUTDOOR'),
    (SELECT ps.id FROM periodical_statuses ps WHERE ps.name = 'AVAILABLE')),
    ('Country Style', 'Are Media Pty Limited',
    'Country Style celebrates the diversity of modern country living. Brings to life the stories of inspirational people and places from around Australia - coast to coast. We visit amazing homes and gardens, travel through Australia''s most vibrant regional centres and sample all the good things the country has to offer.',
    '2022-10-01', 12, 15.75, 'English', 'country_style_magazine.jpg', (SELECT pt.id FROM periodical_types pt WHERE pt.name = 'MAGAZINE'), (SELECT pc.id FROM periodical_categories pc WHERE pc.name = 'TRAVEL_AND_OUTDOOR'),
    (SELECT ps.id FROM periodical_statuses ps WHERE ps.name = 'AVAILABLE')),
    ('National Geographic Traveller (UK)', 'National Geographic Traveller (UK)',
    'Each issue is packed with authentic travel experiences and vivid photography, plus insights and tips to inspire would-be explorers to travel widely, ethically and safely. We are passionate about experiencing the world, championing sustainable travel and celebrating journeys from a local or cultural perspective.',
    '2022-11-01', 12, 25.79, 'English', 'national_geographic_magazine.jpg', (SELECT pt.id FROM periodical_types pt WHERE pt.name = 'MAGAZINE'), (SELECT pc.id FROM periodical_categories pc WHERE pc.name = 'TRAVEL_AND_OUTDOOR'),
    (SELECT ps.id FROM periodical_statuses ps WHERE ps.name = 'AVAILABLE')),
    ('Lonely Planet', 'Immediate Media Company London Limited',
    'Feed your love of travel with award-winning Lonely Planet. Inside you''ll find topical ideas for easy inspirational weekend breaks and more adventurous experiences to try out, helped by the insider knowledge of Lonely Planet''s many experts around the world. You''ll be taken on a journey through words and beautiful photography, with highly atmospheric features transporting you to spectacular landscapes and allowing local people to reveal their culture, history ,food, drink and the natural wonders that surround them.',
    '2020-06-01', 12, 19.15, 'English', 'lonely_planet_magazine.jpg', (SELECT pt.id FROM periodical_types pt WHERE pt.name = 'MAGAZINE'), (SELECT pc.id FROM periodical_categories pc WHERE pc.name = 'TRAVEL_AND_OUTDOOR'),
    (SELECT ps.id FROM periodical_statuses ps WHERE ps.name = 'AVAILABLE'));

/*TRUNCATE TABLE subscriptions;
TRUNCATE TABLE subscription_statuses;*/
INSERT INTO subscription_statuses (name)
VALUES
    ('PENDING'),
    ('AWAITING_PAYMENT'),
    ('PAYED'),
    ('CANCELED'),
    ('COMPLETED');
INSERT INTO subscriptions (user_id, total_cost, status_id)
VALUES
    ((SELECT u.id FROM users u WHERE u.email = 'IreneJohnson@gmail.com'), 19.60, (SELECT sbst.id FROM subscription_statuses sbst WHERE sbst.name = 'AWAITING_PAYMENT')),
    ((SELECT u.id FROM users u WHERE u.email = 'Newman1Ervin@online.de'), 150.45, (SELECT sbst.id FROM subscription_statuses sbst WHERE sbst.name = 'PAYED')),
    ((SELECT u.id FROM users u WHERE u.email = 'VikiHub123@yahoo.com'), 24.51, (SELECT sbst.id FROM subscription_statuses sbst WHERE sbst.name = 'PENDING')),
    ((SELECT u.id FROM users u WHERE u.email = 'SchmidtJulia@gmail.com'), 44.11, (SELECT sbst.id FROM subscription_statuses sbst WHERE sbst.name = 'PAYED')),
    ((SELECT u.id FROM users u WHERE u.email = 'BarsMariaSt@list.ru'), 19.15, (SELECT sbst.id FROM subscription_statuses sbst WHERE sbst.name = 'CANCELED')),
    ((SELECT u.id FROM users u WHERE u.email = 'AndersJulia25@gmail.com'), 26.42, (SELECT sbst.id FROM subscription_statuses sbst WHERE sbst.name = 'COMPLETED'));

--TRUNCATE TABLE subscription_details;
INSERT INTO subscription_details (subscription_id, periodical_id, subscription_duration_in_years, periodical_current_price)
VALUES
(1, (SELECT pd.id FROM periodicals pd WHERE pd.title = 'Watercolor Artist'), 1, 19.60),
(2, (SELECT pd.id FROM periodicals pd WHERE pd.title = 'Frankfurter Allgemeine Sonntagszeitung'), 1, 150.45),
(3, (SELECT pd.id FROM periodicals pd WHERE pd.title = 'Creative Nonfiction'), 1, 24.51),
(4, (SELECT pd.id FROM periodicals pd WHERE pd.title = 'Astronomy'), 1, 44.11),
(5, (SELECT pd.id FROM periodicals pd WHERE pd.title = 'Lonely Planet'), 1, 19.15),
(6, (SELECT pd.id FROM periodicals pd WHERE pd.title = 'BirdWatching'), 1, 26.42);

/*TRUNCATE TABLE payments;
TRUNCATE TABLE payment_methods;*/
INSERT INTO payment_methods (name)
VALUES
('CASH'),
('CHECK'),
('CREDIT_OR_DEBIT_CARD'),
('ONLINE_PAYMENT_SERVICE');
INSERT INTO payments (user_id, subscription_id, payment_time, payment_method_id)
VALUES
((SELECT u.id FROM users u WHERE u.email = 'Newman1Ervin@online.de'), 2, '2022-09-10 15:00', (SELECT ptm.id FROM payment_methods  ptm WHERE name = 'CREDIT_OR_DEBIT_CARD')),
((SELECT u.id FROM users u WHERE u.email = 'SchmidtJulia@gmail.com'), 4, '2022-09-10 15:00', (SELECT ptm.id FROM payment_methods  ptm WHERE name = 'ONLINE_PAYMENT_SERVICE'));

