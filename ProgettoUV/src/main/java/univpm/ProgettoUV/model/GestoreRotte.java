package univpm.ProgettoUV.model;

 

import java.util.Vector;

 

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

 

import univpm.ProgettoUV.controller.MainController;
import univpm.ProgettoUV.controller.StatsController;
import univpm.ProgettoUV.exception.WrongCoordinatesException;
import univpm.ProgettoUV.exception.WrongFilterException;
import univpm.ProgettoUV.exception.WrongRangeException;
import univpm.ProgettoUV.stats.MinMax;
import univpm.ProgettoUV.stats.Statistiche;
import univpm.ProgettoUV.stats.StatsService;

 

/**
 * <p> Elabora i dati da visualizzare in {@link MainController}
 * e {@link StatsController}
 * @author Giangrossi Antonio
 * @author Di Lorenzo Emanuele
 *
 */

 

public class GestoreRotte {
    /**
     * <p> restituisci lo storico uvi senza applicare statistiche </p>
     * @param lat latitudine città selezionata
     * @param lon longitudine città selezionata
     * @return <code>JSONArray</code> dello storico uvi
     */
    public JSONArray datiNoStats(String lat, String lon) {
        String message = "";
        String[] listLat = lat.split(",");
        String[] listLon = lon.split(",");
        JSONArray out = new JSONArray(), value = new JSONArray();
        JSONObject[] tmp = new JSONObject[listLat.length];
        StatsService object = new Statistiche(1); 

 

        for (int i = 0; i < listLat.length; i++) {
            String latElement = listLat[i];
            String lonElement = listLon[i];

 

            double latitudine = Double.parseDouble(latElement);
            double longitudine = Double.parseDouble(lonElement);
            try {
                String name = APICoordinates.getCityname(APICoordinates.caricaArray(), latElement, lonElement);
                long id = APICoordinates.getCityId(APICoordinates.caricaArray(), latElement, lonElement);

 

                message = "lat:" + latitudine + " , lon:" + longitudine;

 

                JSONArray ar = (JSONArray)  object.getData(id);
                JSONArray valori = new JSONArray();
                for(int k = 0; k< ar.size(); k++) {
                    JSONObject oggetto = new JSONObject();
                    double raggiUV = (double) ((JSONObject) ar.get(k)).get("uvi");
                    if(raggiUV!=-1.0) {
                    oggetto.put("uvi", raggiUV);}
                    else {
                        oggetto.put("uvi", "DATO ASSENTE");}
                    
                    oggetto.put("data",((JSONObject) ar.get(k)).get("data"));
                    valori.add(oggetto);
                }
                
                tmp[i] = new JSONObject();
                tmp[i].put("name", name);
                tmp[i].put("coor:", message);
                
                tmp[i].put("valori", valori);
                out.add(tmp[i]);

 

            } catch (WrongCoordinatesException e) {
                System.out.println(e.getMessaggio());
                out.clear();
                JSONObject tmpp = new JSONObject();
                tmpp.put("Error", e.getMessaggio());
                out.add(tmpp);
            }

 

        }
        return out;
    }
    
    /**
     * <p>visualizza la lista delle città selezionabili. In particolare
     * sono state selezionate le prime 100 città italiane in "city.list.json" </p>
     * @return <code>JSONArray</code> con nome, stato e coordinate delle città
     */

 

    public JSONArray listaCompleta() {
        JSONArray obj = null, objOut = new JSONArray();
        JSONObject cityObject = new JSONObject(), coordinate = new JSONObject(), tmp = new JSONObject();
        String paese = new String(), nome = new String();
        int contaIT = 0;
        UtilityDati ut = new UtilityDati();
        obj = ut.leggi("city.list.json");

 

        for (int i = 0; i < obj.size(); i++) {
            cityObject = (JSONObject) obj.get(i);
            coordinate = (JSONObject) cityObject.get("coord");
            paese = (String) cityObject.get("country");
            nome = (String) cityObject.get("name");

 

            if (paese.equals("IT") && contaIT < 100) {
                tmp = new JSONObject();
                tmp.put(nome, coordinate);
                objOut.add(tmp);
                contaIT++;
            }

 

        }
        return objOut;

 

    }
    
    /**
     * <p> Visualizza le statistiche </p>
     * @param lati elenco latitudini città selezionate separate da ","
     * @param longi elenco longitudini città selezionate separate da ","
     * @param range numero di giorni su cui effettuare le statistiche. Il valore di Default è 1
     * @param filter opzione per filtrare tra le città con la media più alta ("max")
     * o bassa ("min"). Se si seleziona "no" verranno restituite le statistiche
     * di tutte le città selezionate. Il valore di default è "no"
     * @return <code>JSONArray</code> con le statistiche delle città
     */
    
    public JSONArray stats(String lati, String longi, int range, String filter) {
        String message = "";

 

        String[] lat = lati.split(",");
        String[] lon = longi.split(",");
        JSONArray out = new JSONArray(), value = new JSONArray();
        Vector<Long> dtgiorno = new Vector<>();
        
        StatsService st = new Statistiche(range);

 

        try {
            if (range <= 0 || range > st.giorniDisponibili()) {
                throw new WrongRangeException();
            }
            if (!(filter.equals("max") || filter.equals("min") || filter.equals("no"))) {
                throw new WrongFilterException();
            }
        } catch (WrongRangeException e) {
            System.out.println(e.getMessaggio());
            out.clear();
            JSONObject tmp = new JSONObject();
            tmp.put("Error", e.getMessaggio());
            out.add(tmp);
            return out;
        } catch (WrongFilterException e) {
            System.out.println(e.getMessaggio());
            out.clear();
            JSONObject tmp = new JSONObject();
            tmp.put("Error", e.getMessaggio());
            out.add(tmp);
            return out;
        }

 


        for (int i = 0; i < lat.length; i++) {
            String lonElement = lon[i];
            String latElement = lat[i];
            try {
                String name = APICoordinates.getCityname(APICoordinates.caricaArray(), latElement, lonElement);
                long id = APICoordinates.getCityId(APICoordinates.caricaArray(), latElement, lonElement);

 


                JSONObject tmp = new JSONObject();

 

                tmp.put("name", name);
                tmp.put("lat", latElement);
                tmp.put("lon", lonElement);

 

                JSONArray res = st.generaStats(id);
                tmp.put("stats", res);
                out.add(tmp);
            } catch (WrongCoordinatesException e) {
                System.out.println(e.getMessaggio());
                out.clear();
                JSONObject tmp = new JSONObject();
                tmp.put("Error", e.getMessaggio());
                out.add(tmp);
                return out;
            }
        }

 

        if (filter.equals("no")) {
            return out;
        } else {
            MinMax mm = new MinMax(out);
            return mm.getMinMax(filter);
        }
    }
    
}
