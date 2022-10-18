# Video Streaming Platform
### Ion Dragos - Cosmin 

## Relatii intre clase

* Clase realizate de mine:
  - Video: am realizat o reprezentare generala unui video, fie film, fie 
serial in care am adaugat pe langa parametrii prezentati in clasele din fileio
campuri de **favorite**, pentru a tine minte daca un video a fost trecut la favorite,
de **views** pentru a tine minte de cate ori a fost vazut respectivul video, 
de **ratingVideo** pentru a memora rating-ul pe care l-a primit acel video, 
de **noOfAppearancesInFavorite**(destul de evident ce face) si de **duration** 
pentru a memora cat dureaza un anumit video. Am realizat si o metoda 
*increaseFavorite* care creste numarul de aparitii ale unui video cu cat 
apare in mai multe liste de favorite ale user-ilor.

  - Movie: este o clasa mostenita din Video in care am adaugat campul
*ratings*. Am suprascris metoda *getDuration* pentru a retine cate minute are un film si
metoda *getRating* in care calculam rating-ul total de la toti useri.
  
  - Serial: o alta clasa mostenita din Video in care avem in plus campurile de
**numberOfSeasons** si **seasons**. La fel ca la Movie, am suprascris metodele
*getDuration* si *getRating* in care calculam de aceasta data pentru un serial.
Diferenta o face ca trebuie sa tinem cont de toate sezoanele.

  - Actor: reprezentare pentru un actor in care am pus informatiile generale
plus campuri de **rating**, **noOfAppearances**, **noOfSpecifiedAwards** si listele
de filme si seriale. Avem metoda *getAwardsNumber* care numara cate premii are
un actor in total.

  - User: reprezentare pentru un user in care avem numele, tipul sau de subscriptie
listele de filme si seriale pe care le are si numarul de rating-uri pe care le-a oferit.

  - Command: in aceasta clasa am realizat toate metodele ce tin de actiunea command.
    + *favorite*: am parcurs lista de useri, am cautat numele pe care il
dorim, intram in lista de filme mai intai si verificam daca un anumit titlu
este sau nu la favorite. Daca nu, atunci setam acest camp si il introducem in lista
si afisam mesaul dorit. In schimb, o sa afisam alt mesaj si repetam acelasi proces
pentru seriale.
  
    + *view*: similar ca la favorite doar ca de aceasta daca updatam numarul de views.
In cazul in care nu a mai fost vazut atunci o sa creen un nou entry point.
    
    + *rating*: realizam aceleasi itereatii si verificam daca user-ul a dat
sau nu rating la un anumit film, punand mesajele de rigoare pentru fiecare caz si setand aceste
informatii.

  - Query: aici am realizat toate metodele ce tin de actiunea query pentru
actori, video-uri si useri.
    + *actorAward*: am filtrat sa vedem ce actor are toate premiile cerute iar pe urma i-am sortat
    + *actorAverage*: am calculat pentru fiecare actor media rating-urilor si i-am sortat dupa cum ni s-a cerut
    + *actorFilterDescription*: am folosit regex pentru a vedea care actor are fix cuvintele precizate
    + *videoRatingMovies* si *videoRatingSerial*: am luat cele mai bune filme si seriale, le-am filtrat si le-am sortat pe urma
    + *videoFavoriteMovie* si *videoFavoriteShow*: am luat filmele si serialele cele mai apreciate, le-am filtrat si sortat
    + *videoLongestMovie* si *videoLongestShow*: am luat filmele si serialele cele mai lungi, le-am filtrat si sortat
    + *videosMostViewedMovie* si *videosMostViewedShow*: am laut filmele si serialele cele mai vazute, le-am filtrat si sortat
    + *usersRating*: numaram care a fost cel mai activ user si il afisam


  - Recommandation: am realizat toate metodele ce tin de actiunea recommendation
pentru useri basic si premium.
    + *recommandationStandard*: filtram filmele si serialele care nu au fost vazute si afisam pe primul
    + *recommandationBestUnseen*: filtram filmele si serialele care nu au fost vazute si vedeam care e cel mai bun dupa rating din acestea
    + *recommandationPopular*: verificam mai intai ca user-ul sa fie premium, iar dupa filtram filmele si serialele care nu au fost vazute in functie de care e cel mai popular
    + *recommandationFavorite*: verificam mai intai ca user-ul sa fie premium, iar dupa filtram filmele si serialele care nu au fost vazute in functie de care e cel mai des intalnit la favorite
    + *recommandationSearch*: verificam mai intai ca user-ul sa fie premium, iar dupa filtram filmele si serialele care nu au fost vazute dupa anumite filtre

* Main: aici am realizat listele, filmele si userii din database si pe urma luam fiecare tip de actiune si apelam metodele
    
