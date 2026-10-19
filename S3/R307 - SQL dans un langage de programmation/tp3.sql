CREATE TABLE client3 (
    login VARCHAR(50) PRIMARY KEY,
    nomClient VARCHAR(100) NOT NULL,
    prenomClient VARCHAR(100) NOT NULL,
    motDePasse VARCHAR(255) NOT NULL,
    adresse VARCHAR(255)
);


INSERT INTO client3 (login, nomClient, prenomClient, motDePasse, adresse)
VALUES ('client1', 'nom1', 'prenom1', 'mp1', 'adresse1');

INSERT INTO client3 (login, nomClient, prenomClient, motDePasse, adresse)
VALUES ('client2', 'nom2', 'prenom2', 'mp2', 'adresse2');

INSERT INTO client3 (login, nomClient, prenomClient, motDePasse, adresse)
VALUES ('client3', 'nom3', 'prenom3', 'mp3', 'adresse3');

INSERT INTO client3 (login, nomClient, prenomClient, motDePasse, adresse)
VALUES ('client4', 'nom4', 'prenom4', 'mp4', 'adresse4');

INSERT INTO client3 (login, nomClient, prenomClient, motDePasse, adresse)
VALUES ('client5', 'nom5', 'prenom5', 'mp5', 'adresse5');

COMMIT;


DROP TABLE client3;











UPDATE client3 SET adresse = 'adresse1'
WHERE login = 'client1';

SELECT * FROM client3;

COMMIT ;

SELECT * FROM client3 ; 

SELECT * FROM client3 ;



UPDATE client3 SET adresse = 'a1'
WHERE login = 'client1' ;


UPDATE client3 SET adresse = 'a2'
WHERE login = 'client2';

COMMIT ; 

SELECT * FROM client3 ;