USE javabistrot;

-- Utente MAITRE admin (password: admin)
INSERT INTO users (username, password, role, active) VALUES
('admin', '$2a$12$Wz8lNt.Rv/HgopBS9IOLquizQEXmKGYkrSTrC4heXB/TXzMTtfQgy', 'MAITRE', true);

INSERT INTO dishes (name, description, price, type, active) VALUES
('Vitello Tonnato Classico', 'Fettine sottili di vitello con salsa tonnata cremosa, capperi croccanti e polvere di limone', 12.00, 'ANTIPASTO', true),
('Tartare di Manzo al Profumo di Senape Antica', 'Battuta al coltello con tuorlo marinato e pane croccante', 12.00, 'ANTIPASTO', true),
('Polentina Croccante con Gorgonzola DOP e Noci', 'Cubetti dorati serviti con crema calda di gorgonzola', 12.00, 'ANTIPASTO', true),
('Carpaccio di Bresaola della Valtellina IGP', 'Con rucola fresca, scaglie di Grana Padano e olio al limone', 12.00, 'ANTIPASTO', true),
('Risotto allo zafferano con burrata', 'Riso Carnaroli mantecato allo zafferano, cuore cremoso di burrata e leggera nota di pepe nero', 14.00, 'PRIMO', true),
('Tagliatelle al ragù di funghi porcini', 'Pasta fresca all''uovo con ragù bianco di porcini, prezzemolo fresco e filo d''olio extravergine', 15.00, 'PRIMO', true),
('Gnocchi al pesto di basilico e pinoli', 'Gnocchi di patate fatti in casa con pesto fresco, pinoli tostati e scaglie di Grana Padano', 13.00, 'PRIMO', true),
('Lasagna al forno classica', 'Strati di pasta fresca, ragù tradizionale, besciamella vellutata e Parmigiano Reggiano', 12.00, 'PRIMO', true),
('Filetto di orata al limone e erbe aromatiche', 'Filetto di orata fresca, profumato al limone e timo, servito con verdure grigliate di stagione', 19.00, 'SECONDO', true),
('Pollo alla cacciatora', 'Coscia di pollo cotta lentamente con pomodoro, olive, rosmarino e vino bianco', 16.00, 'SECONDO', true),
('Scaloppine al vino bianco con funghi', 'Fettine di vitello sfumate al vino bianco con funghi champignon e prezzemolo fresco', 18.00, 'SECONDO', true),
('Parmigiana di zucchine', 'Strati di zucchine grigliate, mozzarella filante, salsa di pomodoro e basilico fresco', 15.00, 'SECONDO', true),
('Tiramisù classico', 'Savoiardi al caffè espresso, crema al mascarpone e cacao amaro in polvere', 7.00, 'DOLCE', true),
('Cheesecake ai frutti di bosco', 'Base croccante al burro, crema al formaggio e coulis di frutti rossi', 8.00, 'DOLCE', true),
('Panna cotta alla vaniglia con coulis di fragole', 'Delicata panna cotta artigianale con salsa fresca alle fragole', 7.00, 'DOLCE', true),
('Mousse al cioccolato fondente', 'Morbida mousse al 70% di cacao con scaglie di cioccolato fondente', 8.00, 'DOLCE', true);

INSERT INTO bookings 
(customer_name, email, phone_number, number_of_guests, booking_date_time, notes, active) VALUES
('Marco Rossi', 'marco.rossi@email.com', '+393331112233', 2, '2026-02-25 20:00:00', 'Tavolo vicino alla finestra', TRUE),
('Giulia Bianchi', 'giulia.bianchi@email.com', '+393401234567', 4, '2026-02-25 21:00:00', 'Compleanno - portare torta', TRUE),
('Luca Verdi', 'luca.verdi@email.com', '+393298765432', 6, '2026-02-26 19:30:00', NULL, TRUE),
('Sara Neri', 'sara.neri@email.com', '+393477889900', 3, '2026-02-25 20:30:00', 'Seggiolone per bambino', TRUE),
('Andrea Gallo', 'andrea.gallo@email.com', '+393355667788', 8, '2026-03-10 20:00:00', 'Cena aziendale', TRUE),
('Francesca Romano', 'francesca.romano@email.com', '+393366778899', 2, '2026-03-11 21:15:00', NULL, TRUE),
('Davide Conti', 'davide.conti@email.com', '+393312345678', 5, '2026-03-12 20:45:00', 'Intolleranza al lattosio', TRUE),

