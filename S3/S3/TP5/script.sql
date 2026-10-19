

--
-- Structure de la table `utilisateur`
--

CREATE TABLE `utilisateur` (
  `login` varchar(30) NOT NULL,
  `mdp` varchar(64) NOT NULL,
  `nom` varchar(30) NOT NULL,
  `prenom` varchar(30) NOT NULL,
  `email` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `utilisateur`
--

INSERT INTO `utilisateur` (`login`, `mdp`, `nom`, `prenom`, `email`) VALUES
('kthompson', 'UNIX_C_1970', 'Thompson', 'Ken', 'ken.thompson@unix_c.com'),
('bkernighan', '*p=C_1970', 'Kernighan', 'Brian', 'brian.kernighan@languageC.ca'),
('bstroustrup', 'C++_1980', 'Stroustrup', 'Bjarne', 'bs@cplusplus.dk'),
('dritchie', 'ptr=&C_1970', 'Ritchie ', 'Dennis', 'dennis.ritchie@languageC.com'),
('rlerdof', '$PHP_1994', 'Lerdorf', 'Rasmus', 'ramus.lerdorf@php.ca');

-- --------------------------------------------------------

--
-- Structure de la table `voiture`
--

CREATE TABLE `voiture` (
  `immatriculation` varchar(8) NOT NULL,
  `marque` varchar(25) NOT NULL,
  `couleur` varchar(12) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `voiture`
--

INSERT INTO `voiture` (`immatriculation`, `marque`, `couleur`) VALUES
('AAA123ZZ', 'Renault', 'Rouge'),
('BBB456YY', 'Citroën', 'Orange'),
('555TY77', 'Citroën', 'Bleu'),
('2473RF45', 'Peugeot', 'Blanc');

--
-- Index pour les tables déchargées
--

--
-- Index pour la table `utilisateur`
--
ALTER TABLE `utilisateur`
  ADD PRIMARY KEY (`login`);

--
-- Index pour la table `voiture`
--
ALTER TABLE `voiture`
  ADD PRIMARY KEY (`immatriculation`);
COMMIT;


