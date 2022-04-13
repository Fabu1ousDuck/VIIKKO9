package com.example.viikko9;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class Theaters {
    ArrayList <Theatre> theatres;
    ArrayList<String> movies = null;

    public Theaters (){
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        theatres = new ArrayList<Theatre>();
        try {
            // parse XML file
            DocumentBuilder db = dbf.newDocumentBuilder();
            String url = "https://www.finnkino.fi/xml/TheatreAreas/";
            Document doc = db.parse(url);
            doc.getDocumentElement().normalize();
            NodeList list = doc.getDocumentElement().getElementsByTagName("TheatreArea");
            for (int temp = 0; temp < list.getLength(); temp++) {

                Node node = list.item(temp);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;

                    String id = element.getElementsByTagName("ID").item(0).getTextContent();
                    String area = element.getElementsByTagName("Name").item(0).getTextContent();
                    theatres.add(new Theatre(area,id));
                    System.out.println(id +" "+ area);
                }
            }
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }


    }
    public ArrayList<String> searchMovies (String name, String date, String start, String end, String mvname){
        Date dt;
        Date dtx;
        Date dty;
        int id = 0;
        for (int i = 0; i < theatres.size(); i++) {
            id = theatres.get(i).getID(name);
            if (id != 0){
                break;
            }
        }
        SimpleDateFormat dateFormatIn = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        SimpleDateFormat dateFormatOut = new SimpleDateFormat("HH:mm");
        SimpleDateFormat dateFormatInx = new SimpleDateFormat("dd.MM.yyyy HH.mm");
        System.out.println("UIOHIUFHE=IFOIFJEFOI");
        String url = "https://www.finnkino.fi/xml/Schedule/?area="+id+"&dt="+date;
        System.out.println(url);
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        movies = new ArrayList<String>();
        try {
            // parse XML file
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(url);
            doc.getDocumentElement().normalize();
            NodeList list = doc.getDocumentElement().getElementsByTagName("Show");
            for (int temp = 0; temp < list.getLength(); temp++) {
                Node node = list.item(temp);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    String showStart = element.getElementsByTagName("dttmShowStart").item(0).getTextContent();
                    String title = element.getElementsByTagName("Title").item(0).getTextContent();
                    String length = element.getElementsByTagName("LengthInMinutes").item(0).getTextContent();

                    dt = dateFormatIn.parse(showStart);
                    String dateStart = date+" "+start;
                    String dateEnd = date+" "+end;
                    dtx = dateFormatInx.parse(dateStart);
                    dty = dateFormatInx.parse(dateEnd);
                    System.out.println("DAMN");
                    assert dt != null;
                    if (dt.after(dtx) && dt.before(dty)){
                        if (title.contains(mvname)) {
                            String times = dateFormatOut.format(dt);
                            movies.add(title + " " + times + " " + length + "min");
                            System.out.println(times + " " + title + " " + length);
                        }
                    }
                }
            }
            return movies;
        } catch (ParserConfigurationException | SAXException | IOException |ParseException e) {
            e.printStackTrace();
        }
        return movies;

    }
    public ArrayList<String> getNames(){
        ArrayList <String> names = new ArrayList<String>();
        theatres.forEach((n)->names.add(n.theater_name));
        System.out.print(names);
        return names;
    }

    public ArrayList<String> searchMoviesName(String name, String date, String start, String end, String mvName){
        Date dt;
        Date dtx;
        Date dty;
        int id = 0;
        for (int i = 0; i < theatres.size(); i++) {
            id = theatres.get(i).getID(name);
            if (id != 0){
                break;
            }
        }
        SimpleDateFormat dateFormatIn = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        SimpleDateFormat dateFormatOut = new SimpleDateFormat("HH:mm");
        SimpleDateFormat dateFormatInx = new SimpleDateFormat("dd.MM.yyyy HH.mm");
        String[] idarray = {"1014", "1015", "1016", "1017", "1041", "1018", "1019", "1021", "1022"};
        ArrayList<String> idlist = new ArrayList<String>(Arrays.asList(idarray));
        movies = new ArrayList<String>();
        movies.add(0, mvName);
        for(String s : idlist){
            String url = "https://www.finnkino.fi/xml/Schedule/?area="+s+"&dt="+date;
            System.out.println(url);
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            try {
                DocumentBuilder db = dbf.newDocumentBuilder();
                Document doc = db.parse(url);
                doc.getDocumentElement().normalize();
                NodeList list = doc.getDocumentElement().getElementsByTagName("Show");
                for (int temp = 0; temp < list.getLength(); temp++) {
                    Node node = list.item(temp);
                    if (node.getNodeType() == Node.ELEMENT_NODE) {
                        Element element = (Element) node;
                        String showStart = element.getElementsByTagName("dttmShowStart").item(0).getTextContent();
                        String title = element.getElementsByTagName("Title").item(0).getTextContent();
                        String length = element.getElementsByTagName("LengthInMinutes").item(0).getTextContent();
                        String place = element.getElementsByTagName("Theatre").item(0).getTextContent();
                        dt = dateFormatIn.parse(showStart);
                        String dateStart = date+" "+start;
                        String dateEnd = date+" "+end;
                        dtx = dateFormatInx.parse(dateStart);
                        dty = dateFormatInx.parse(dateEnd);
                        System.out.println("DAMN");
                        assert dt != null;
                        if (dt.after(dtx) && dt.before(dty)){
                            if (title.contains(mvName)){
                                String times = dateFormatOut.format(dt);
                                movies.add(title+" "+times+" "+length+"min "+place);
                                System.out.println(times+" "+title+" "+length+" "+ place);

                            }
                        }
                    }
                }
            } catch (ParserConfigurationException | SAXException | IOException |ParseException e) {
                e.printStackTrace();
        }
        }
        return movies;
    }
}