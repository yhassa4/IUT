SET SERVEROUTPUT ON
CREATE TABLE clientfilm(
    numclient NUMBER(5) PRIMARY KEY,
    nomclient VARCHAR2(50),
    prenomclient VARCHAR2(50),
    motpasse VARCHAR2(50),
    adrclient VARCHAR(100),
    cpclient NUMBER(5)
);

CREATE TABLE locationFilm (
    numexemplaire NUMBER(38),
    numclient NUMBER(5),
    datelocation DATE,
    dateenvoi DATE,
    dateretour DATE,
    PRIMARY KEY(numexemplaire, numclient),
    CONSTRAINT FK_loc_client FOREIGN KEY (numclient) REFERENCES clientfilm(numclient)    
);

insert into clientfilm (numclient, nomclient, prenomclient, motpasse, adrclient, cpclient) values (2, 'Swinglehurst', 'Clay', 'kC2>P(@eM0', 'PO Box 87007', null);
insert into clientfilm (numclient, nomclient, prenomclient, motpasse, adrclient, cpclient) values (3, 'Loney', 'Sunny', 'kC8(7nFblj', 'PO Box 25421', null);

insert into locationFilm(numexemplaire, datelocation, numclient, dateenvoi, dateretour) VALUES(1112, '01-01-2008', 2, '01-01-2008', null);
insert into locationFilm(numexemplaire, datelocation, numclient, dateenvoi, dateretour) VALUES(1111, '01-01-2008', 2, '01-01-2008', '04-01-2008');
insert into locationFilm(numexemplaire, datelocation, numclient, dateenvoi, dateretour) VALUES(5503, SYSDATE, 2, null, null);
insert into locationFilm(numexemplaire, datelocation, numclient, dateenvoi, dateretour) VALUES(5443, '02-01-2008', 2, '02-01-2008', '05-01-2008');
insert into locationFilm(numexemplaire, datelocation, numclient, dateenvoi, dateretour) VALUES(2864, '12-01-2008', 3, '13-01-2008', null);
insert into locationFilm(numexemplaire, datelocation, numclient, dateenvoi, dateretour) VALUES(15674, SYSDATE, 3, SYSDATE, null);
insert into locationFilm(numexemplaire, datelocation, numclient, dateenvoi, dateretour) VALUES(3437, SYSDATE, 3, SYSDATE, null);

insert into locationFilm(numexemplaire, datelocation, numclient, dateenvoi, dateretour) VALUES(8519, SYSDATE, 3, SYSDATE, null);
insert into locationFilm(numexemplaire, datelocation, numclient, dateenvoi, dateretour) VALUES(8534, SYSDATE, 3, SYSDATE, sysdate-4);


SELECT * FROM clientfilm;
SELECT * FROM locationFilm;

SET SERVEROUTPUT ON;

---Question 1)a)
create or replace FUNCTION nbrfilm(numIndividu  NUMBER) RETURN NUMBER IS
nbfilm NUMBER(5) := 0 ;
BEGIN
SELECT COUNT(*) INTO nbfilm 
FROM ens2004.film F
WHERE realisateur = numIndividu;
RETURN nbfilm;
END nbrfilm;


SELECT nbrfilm(9761) FROM DUAL;


---Question 1)b)
create or replace FUNCTION nbrsfilm(ens2004.Individu.nomIndividu %Type) RETURN NUMBER IS
nbfilm NUMBER(5) := 0 ;
BEGIN
SELECT COUNT(*) INTO nbfilm 
FROM ens2004.film F
INNER JOIN ens2004.Individu i ON i.numIndividu = f.numFilm
WHERE i.numIndividu = numIndividu;
RETURN nbfilm;
EXCEPTION 
WHEN OTHERS THEN 
 raiseApplication(-2001,'Erreur dans nbrsfilm');
END nbrsfilm;

SELECT nbrsfilm('WENDERS') FROM DUAL;

---Question 1)c)
CREATE OR REPLACE PROCEDURE listFilms(numIndividu IN NUMBER) IS
    CURSOR c IS
        SELECT f.titre
        FROM ens2004.Film f
        INNER JOIN ens2004.Individu i ON i.numIndividu = f.numFilm
        WHERE i.numIndividu = numIndividu;
        
BEGIN
    FOR r IN c LOOP
        DBMS_OUTPUT.PUT_LINE(r.titre);
    END LOOP;
END;
/


exec listFilms(55);

---Question 2
create or replace FUNCTION nbexemplairerestant(
    p_numfilm IN ens2004.exemplaire.numfilm%TYPE
) RETURN NUMBER IS
    v_count1 NUMBER := 0;
BEGIN
SELECT COUNT(*) into v_count1
FROM ens2004.exemplaire
where p_numfilm = numfilm AND numexemplaire NOT IN (select numexemplaire from locationFilm WHERE dateretour IS NOT NULL);



return v_count1 ;

END ;
/

DECLARE
z NUMBER;
BEGIN
z := nbexemplairerestant(9297);
DBMS_OUTPUT.PUT_LINE(z);
END;
/


---Question 3
create or replace trigger trigarlerte
BEFORE INSERT ON locationFilm
FOR EACH ROW
DECLARE
    nbfilmsretard INTEGER;
BEGIN
    SELECT COUNT(*)
    INTO nbfilmsretard
    FROM location
    WHERE NumClient = :NEW.NumClient
      AND dateretour IS NULL
      AND datelocation < ADD_MONTHS(SYSDATE, -1);

    IF nbfilmsretard > 0 THEN
        DBMS_OUTPUT.PUT_LINE('Ce client a un film non rendu depuis plus dun mois'|| nbfilmsretard);
    END IF;
END;
/







---Question 3)b
create or replace trigger trigarlerte2 
BEFORE INSERT ON locationFilm
FOR EACH ROW
DECLARE
    nbfilmsretard INTEGER;
BEGIN
    SELECT COUNT(*)
    INTO nbfilmsretard
    FROM location
    WHERE NumClient = :NEW.NumClient
      AND dateretour IS NULL
      AND datelocation < ADD_MONTHS(SYSDATE, -1);

    IF nbfilmsretard > 0 THEN
        RAISE_APPLICATION_ERROR(-20001, 'Erreur');
    END IF;
END;
/


insert into locationFilm(numexemplaire, datelocation, numclient, dateenvoi, dateretour) VALUES(1000, SYSDATE, 17, SYSDATE, null);


---Question 4
Create OR REPLACE FUNCTION nbNonRendus(
numIndi IN ens2004.individu.numindividu%TYPE
) RETURN NUMBER IS
    v_count NUMBER := 0;
BEGIN
select COUNT(*) INTO v_count1
FROM locationFilm
WHERE numIndi = numclient AND dateretour IS NULL;

return v_count ;
END nbNonRendus;
/


DECLARE
x NUMBER;
BEGIN
x := nbNonRendus(3);
DBMS_OUTPUT.PUT_LINE(x);
END;
/



---Question 4)b)

create or replace trigger trigarlerte3
BEFORE INSERT ON locationFilm
FOR EACH ROW
DECLARE
    nbfilmsretard INTEGER;
    x INTEGER;
BEGIN

    x := nbNonRendus(:NEW.NumClient);
    DBMS_OUTPUT.PUT_LINE(x);
    
    IF x > 3 THEN
        RAISE_APPLICATION_ERROR(-20001, 'Erreur trigarlerte3');
    END IF;
END;
/
insert into locationFilm(numexemplaire, datelocation, numclient, dateenvoi, dateretour) VALUES(1000, SYSDATE, 17, SYSDATE, null);