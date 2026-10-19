SELECT * FROM client3 ;


UPDATE client3 SET adresse = 'b'
WHERE login = 'client1';

SELECT * FROM client3 ; 

COMMIT;

SELECT * FROM client3 ;


UPDATE client3 SET adresse = 'b2'
WHERE login = 'client2';


UPDATE client3 SET adresse = 'b1'
WHERE login = 'client1';

COMMIT ; 

SELECT * FROM client3 ;