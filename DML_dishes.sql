USE javabistrot;
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

DROP TABLE dishes;