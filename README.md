# progetto.oop
# INTRODUZIONE
 Il programma consente di visualizzare lo storico dei dati uvi prelevati dal sito OpenWeatherMap. I dati sono stati prelevati tramite la chiamata API per le previsioni dei dati uvi e sono stati raccolti in un file locale "listaValori.json". Oltre alla semplice visualizzazione dei dati storici il programma consente di elaborare statistiche su più città scelte contemporeneamente dall'utente. Le città verranno scelte tramite l'inserimento delle coordinate geografiche corrispondenti. L'utente può scegliere un range di giorni su cui effettuare le statistiche e può decidere se filtrare le città in base a quelle con la media più alta o bassa. Le statistiche mostrate sono: media, varianza, massimo e minimo.
# DIAGRAMMI UML
<b>Use Case Diagram</b> 
![diagramma casi d'uso](https://user-images.githubusercontent.com/58080242/111167621-99b5f600-85a1-11eb-8152-5d916a05ed8a.jpg)
L'utente può visulizzare una lista predefinita contenente i nomi di 100 città itliane e le loro coordinate. Le coordinate possono essere successivamente utilizzate per visualizzare l'intero storico dei dati oppure per visualizzare statistiche che saranno filtrate in base ad un numero di giorni scelto dall'utente ed eventualmente filtrate anche in base al massimo o minimo della media tra tutte le città scelte in ogni periodo mostrato.
L'amministratore è un attore equivlente ad un server che aggiorna i dati effettuando la chimata ad OpenWeatherMap. L'aggiornamento deve essere effettuato massimo ogni 2 giorni poichè i dati forniti da OpenWeatherMap coprono solo quel lasso di tempo, e in caso contrario ci sarebbe una mancanza di dati. In caso di dati mancanti il programma continua a funzionare, ma alcuni dati saranno visualizzati come assenti o parzili. 


<b>Sequence Diagram</b>
![primo diagramma sequenze](https://user-images.githubusercontent.com/58080242/111168518-6cb61300-85a2-11eb-9a18-3abd2bbb3f06.png)

![secondo diagramma sequenze](https://user-images.githubusercontent.com/58080242/111168545-72abf400-85a2-11eb-9e31-b66238d0893b.jpg)

Le sequenze del sistema prevedono uno scambio di informazioni tra il client, il server, il file "city.list.json" e il file "listaValori.json". Il file "city.list.json" contiene le informazioni riguardanti tutte le città di OpenWeatherMap, mentre "listaValori.json" contiene lo storico uvi delle 100 città italine selezionate.

<b> Class Digram </b>
![diagramma classi 1](https://user-images.githubusercontent.com/58080242/111168581-7dff1f80-85a2-11eb-97dd-48318090bf05.png)

Le <b>classi principali</b> sono:

<i> APICoordinates.java </i> che gestisce le chiamate API.

<i> StampaLista.java </i> che serve a riscrivere e aggiornare il file "listaValori.json".

<i> Statistiche.java </i> e <i> MinMax.java </i> che gestiscono le statistiche
 
 Sono state implementate 3 <b>eccezioni </b> per gestire 3 tipi di errore:
 
 <i> WrongCoordinatesException </i> se l'utente inserisce delle coordinate che non esistono nell'archivio

![coordinate](https://user-images.githubusercontent.com/58080242/111182361-21eec800-85af-11eb-9baf-1af4be6b83a0.png)

<i> WrongFilterException </i> se l'opzione selezionata per il filtro è inesistente

![filter](https://user-images.githubusercontent.com/58080242/111182394-27e4a900-85af-11eb-8c35-68c0472dfa2a.png)

<i> WrongRangeException </i> se il range selezionato per le statistiche è minore o uguale a zero oppure se è maggiore del numero di giorni salvati presenti nell'archivio.

![range](https://user-images.githubusercontent.com/58080242/111182460-3763f200-85af-11eb-9cee-32ad3d8d775a.png)

# ROTTE
Tutte le rotte sono utilizzabili con la chimata <b> GET </b>. Per verificarne il funzionamento esse sono state chiamate da Postman attraverso la porta localhost:8080

<b>1. Lista città disponibili: /listaCittà 
 
ESEMPIO: </b> localhost:8080/listaCittà

![Screenshot (32)](https://user-images.githubusercontent.com/58080242/111179695-9bd18200-85ac-11eb-9a3c-2eeb9562046c.png)

L'elenco delle città disponibili viene tramite la rotta <b> /listaCittà </b> : Verranno visualizzati nome e coordinate delle 100 città italiane presenti nell'archivio dati

<b>2. Visualizzazione dati storici: /coorCittà 
 
 ESEMPIO: </b> localhost:8080/coorCittà?lon=16.292089,8.70889&lat=39.31089,39.958611

![coorCitta](https://user-images.githubusercontent.com/58080242/111179830-b99ee700-85ac-11eb-8d7e-00f044c3b6d2.png)
![coorCitta(1)](https://user-images.githubusercontent.com/58080242/111179842-bc014100-85ac-11eb-86ad-ae9e51c36d31.png)

Con la rotta <b> /coorCittà </b> è possibile richiedere la visualizzazioni di più città contemporaneamente. La richiesta va fatta inserendo due parametri obbligatori: <b>lat</b> e <b>lon</b>. Essi accettano una lista di valori separati da virgola.

<b>3. Visualizzazione statistiche storiche: /stats
 
 ESEMPIO:</b> localhost:8080/stats?lon=16.292089,8.70889&lat=39.31089,39.958611 
 
 <b>ESEMPIO CON FILTRI:</b> localhost:8080/stats?lon=16.292089,8.70889&lat=39.31089,39.958611&range=4&filter=max

![Screenshot (41)](https://user-images.githubusercontent.com/58080242/111179917-cfaca780-85ac-11eb-9122-6c401ea7cf31.png)
![Screenshot (42)](https://user-images.githubusercontent.com/58080242/111179924-d0ddd480-85ac-11eb-8348-5f762e47fcea.png)
![Screenshot (43)](https://user-images.githubusercontent.com/58080242/111179927-d20f0180-85ac-11eb-93aa-f975fb3915a9.png)


I dati storici sono  visualizzati dalla rotta <b> /stats </b>. Questa rotta prende in ingresso quttro parametri: <b>lat</b> e <b>lon</b> sono parametri obbligatori ed accettano una lista di valori separati da virgola. Un altro parametro è <b>range</b>, che richiede il numero di giorni su cui effettuare le statistiche (ad esempio giornaliera o di 3 giorni) ed ha valore di default pari a 1.
 L'ultimo parametro è <b>filter</b>, che richiede se si vuole filtrare sul massimo della media, con il valore <i>max</i>, o sul minimo col valore <i>min</i>, oppure se non si vuole filtrare tra le varie città è possibile inserire il valore <i>no</i>, che è il valore di default. 
 
 <b>4. Aggiornamento archivio: /aggiorna </b>
 
 <b>ESEMPIO:</b> localhost:8080/aggiorna
 
 ![aggiorna](https://user-images.githubusercontent.com/58080242/111179966-daffd300-85ac-11eb-9e8c-40333dd299f4.png)

Questa rotta serve per aggioranre l'archivio locale. L'archivio deve essere aggiornato entro due giorni dall'ultimo aggiornamento poichè le previsioni hanno una durata di 2 giorni. In caso di mancato aggiornamento entro 2 giorni, l'archivio verrà riempito con valori contrassegnati come dati assenti. Nelle statistiche i dati assenti sono contrassegnati nella chiave <b> Dati archivio </b> come <b>ASSENTI</b>. Se almeno un dato è presente nel periodo considerato, questa chiave indicherà valori <b>PARZIALI</b>

![esempio dato mancante](https://user-images.githubusercontent.com/78969653/111342709-00f3a900-867b-11eb-899c-4351078b3d38.jpg)
![esempio OOP](https://user-images.githubusercontent.com/78969653/111342730-0650f380-867b-11eb-9316-4b01f9e68db0.jpg)



# TEST
Sono stati effettuati 3 test per le parti principali del programma:

<b> APICoordinatesTest.java</b> è stato usato per verificare la correttezza nella gestione degli attributi delle città

<b> ExceptionTest.java</b> è stato usato per verificare la correttezza nell'intercettamento di eccezioni da parte del programma

<b> TestStatistiche.java </b> è stato usato per verificare la correttezza delle statistiche considerate. In questo caso, visto che il file contenente i dati può cambiare a causa degli aggiornamenti, i test sono stati effettuati su i primi giorni (i più vecchi) contenuti nell'archivio, i quali rimangono inalterati anche dopo un aggiornamento

# AUTORI

Il programma è stato sviluppato da:
Giangrossi Antonio, 
Di Lorenzo Emanuele
