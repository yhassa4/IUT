CREATE OR REPLACE FUNCTION nbreFilms1(numIndividu NUMBER)
RETURN NUMBER
IS
    nbFilm NUMBER := 0;
BEGIN
    SELECT COUNT(*)
    INTO nbFilm
    FROM ens2004.Film F
    NATURAL JOIN ens2004.Acteur A
    WHERE A.numIndividu = numIndividu;

    RETURN nbFilm;
END;

BEGIN
    DBMS_OUTPUT.PUT_LINE( nbreFilms1(2289) );
END;
/



CREATE OR REPLACE FUNCTION nbreFilms2(nomRealisateur VARCHAR2)
RETURN NUMBER
IS
    nbFilm NUMBER := 0;
BEGIN
    SELECT COUNT(*)
    INTO nbFilm
    FROM ens2004.Film F
    JOIN ens2004.Acteur A
        ON F.Realisateur = A.numIndividu
    JOIN ens2004.Individu I
        ON I.numIndividu = A.numIndividu
    WHERE UPPER(I.NomIndividu) = UPPER(nomRealisateur);

    RETURN nbFilm;
END;
/

BEGIN
    DBMS_OUTPUT.PUT_LINE( nbreFilms2('FONDA') );
END;
/

select Realisateur
from ens2004.Film;






CREATE OR REPLACE PROCEDURE unTitre(
    nomActeur IN VARCHAR2,
    prenom OUT VARCHAR2,
    titreFilm OUT VARCHAR2
)
IS
BEGIN
    SELECT I.PrenomIndividu, F.Titre
    INTO prenom, titreFilm
    FROM ens2004.Individu I
    JOIN ens2004.Acteur A 
        ON I.NumIndividu = A.NumIndividu
    JOIN ens2004.Film F
        ON A.NumFilm = F.NumFim
    WHERE UPPER(I.NomIndividu) = UPPER(nomActeur)
    AND ROWNUM = 1;   -- on renvoie seulement un film

EXCEPTION
    WHEN NO_DATA_FOUND THEN
        prenom := NULL;
        titreFilm := NULL;
END;
/
