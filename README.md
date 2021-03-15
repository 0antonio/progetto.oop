# progetto.oop
# INTRODUZIONE
 Il programma consente di visualizzare lo storico dei dati uvi prelevati dal sito OpenWeatherMap. I dati sono stati prelevati tramite la chiamata API per le previsioni dei dati uvi e sono stati raccolti in un file locale "listaValori.json". Oltre alla semplice visualizzazione dei dati storici il programma consente di elaborare statistiche su più città scelte contemporeneamente dall'utente. Le città verranno scelte tramite l'inserimento delle coordinate geografiche corrispondenti. L'utente può scegliere un range di giorni su cui effettuare le statistiche e può decidere se filtrare le città in base a quelle con la media più alta o bassa. Le statistiche mostrate sono: media, varianza, massimo e minimo.
# DIAGRAMMI UML
<b>Use Case Diagram</b> 
![diagramma casi d'uso](https://user-images.githubusercontent.com/58080242/111167621-99b5f600-85a1-11eb-8152-5d916a05ed8a.jpg)
L'utente può visulizzare una lista predefinita contenente i nomi di 100 città itliane e le loro coordinate. Le coordinate possono essere successivamente utilizzate per visualizzare l'intero storico dei dati oppure per visualizzare statistiche che saranno filtrate in base ad un numero di giorni scelto dall'utente ed eventualmente filtrate anche in base al massimo o minimo della media tra tutte le città scelte in ogni periodo mostrato.
L'amministratore è un attore equivlente ad un server che aggiorna i dati effettuando la chimata ad OpenWeatherMap. L'aggiornamento deve essere effettuato massimo ogni 2 giorni poichè i dati forniti da OpenWeatherMap coprono solo quel lasso di tempo, e in caso contrario ci sarebbe una mancanza di dati.

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
 
 Sono state implementate 3 eccezioni per gestire 3 tipi di errore:
 
 <i> WrongCoordinatesException </i> se l'utente inserisce delle coordinate che non esistono nell'archivio

<i> WrongFilterException </i> se l'opzione selezionata per il filtro è inesistente

<i> WrongRangeException </i> se il range selezionato per le statistiche è minore o uguale a zero oppure se è maggiore del numero di giorni salvati presenti nell'archivio.

# ROTTE
Tutte le rotte sono utilizzabili con la chimata <b> GET </b>. Per verificarne il funzionamento esse sono state chiamate da Postman attraverso la porta localhost:8080

<b> Lista città disponibili: /listaCittà </b>

L'elenco delle città disponibili viene tramite la rotta <b> /listaCittà </b> : Verranno visualizzati nome e coordinate delle 100 città italiane presenti nell'archivio dati

<b> Visualizzazione dati storici: /coorCittà </b>
Con la rotta <b> /coorCittà </b> è possibile richiedere la visualizzazioni di più città contemporaneamente. La richiesta va fatta inserendo due parametri obbligatori: <b>lat</b> e <b>lon</b>. Essi accettano una lista di valori separati da virgola.

<b> Visualizzazione statistiche storiche: /stats </b>

I dati storici sono  visualizzati dalla rotta <b> /stats </b>. Questa rotta prende in ingresso quttro parametri: <b>lat</b> e <b>lon</b> sono parametri obbligatori ed accettano una lista di valori separati da virgola. Un altro parametro è <b>range</b>, che richiede il numero di giorni su cui effettuare le statistiche (ad esempio giornaliera o di 3 giorni) ed ha valore di default pari a 1.
 L'ultimo parametro è <b>filter</b>, che richiede se si vuole filtrare sul massimo della media, con il valore <i>max</>, o sul minimo col valore <i>min</i>, oppure se non si vuole filtrare tra le varie città è possibile inserire il valore <i>no</i>, che è il valore di default. 
 
 <b> Aggiornamento archivio: /aggiorna </b>
Questa rotta serve per aggioranre l'archivio locale. L'archivio deve essere aggiornato entro due giorni dall'ultimo aggiornamento poichè le previsioni hanno una durata di 2 giorni.
