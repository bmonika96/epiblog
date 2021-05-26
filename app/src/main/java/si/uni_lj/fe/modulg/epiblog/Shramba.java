package si.uni_lj.fe.modulg.epiblog;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;


import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;


public class Shramba  {
    private  Activity self;

    public Shramba(Activity act){
        self=act;
    }


    private void vpsiVDatoteko(String vsebina,String filename){
        try {
            //ustvarimo izhodni tok
            FileOutputStream os = self.openFileOutput(filename, Context.MODE_PRIVATE);
            //zapisemo posredovano vsebino v datoteko
            os.write(vsebina.getBytes());
            //sprostimo izhodni tok
            os.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private String beriIzDatoteke(String filename){

        // ustvarimo vhodni podatkovni tok
        FileInputStream inputStream;

        //ugotovimo, koliko je velika datoteka
        File file = new File(self.getFilesDir(), filename);
        if(!file.exists()) return null;
        int length = (int) file.length();

        //pripravimo spremenljivko, v katero se bodo prebrali podatki
        byte[] bytes = new byte[length];

        //preberemo podatke
        try {
            inputStream = self.openFileInput(filename);
            inputStream.read(bytes);
            inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        //podatke pretvorimo iz polja bajtov v znakovni niz
        String vsebina = new String(bytes);

        return vsebina;
    }

    public Node pridobiUporabnika() {
        try {
            String uporabnikStr=beriIzDatoteke(self.getString(R.string.filename_uporabnik));
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Node uporabnik=null;
            if(uporabnikStr!=null) {
                InputStream is = new ByteArrayInputStream(uporabnikStr.getBytes(StandardCharsets.UTF_8));
                Document doc = dBuilder.parse(is);
                doc.normalize();
                uporabnik = doc.getChildNodes().item(0);

            }
            return uporabnik;

        } catch (Exception e) {e.printStackTrace();}
        return null;
    }

    public String pridobiUporabnikaIme(){
        Node uporabnik=pridobiUporabnika();

        if (uporabnik==null){
        return null;
        }else{
            String ime=getValue(self.getString(R.string.shramba_tag_ime),(Element) uporabnik);
            return ime;
        }
    }
    public String pridobiUporabnikaPriimek(){
        Node uporabnik=pridobiUporabnika();

        if (uporabnik==null){
            return null;
        }else{
            String priimek=getValue(self.getString(R.string.shramba_tag_priimek),(Element) uporabnik);
            return priimek;
        }
    }
    public String pridobiUporabnikaNaslov(){
        Node uporabnik=pridobiUporabnika();

        if (uporabnik==null){
            return null;
        }else{
            String naslov=getValue(self.getString(R.string.shramba_tag_naslov),(Element) uporabnik);
            return naslov;
        }
    }
    public String pridobiUporabnikaOsebnaStevilka(){
        Node uporabnik=pridobiUporabnika();

        if (uporabnik==null){
            return null;
        }else{
            String st=getValue(self.getString(R.string.shramba_tag_osebnastevilka),(Element) uporabnik);
            return st;
        }
    }

    public String pridobiUporabnikaZdravnikovaStevilka(){
        Node uporabnik=pridobiUporabnika();

        if (uporabnik==null){
            return null;
        }else{
            String st=getValue(self.getString(R.string.shramba_tag_zdravnikovastevilka),(Element) uporabnik);
            return st;
        }
    }
    public String pridobiUporabnikaZdravila(){
        Node uporabnik=pridobiUporabnika();

        if (uporabnik==null){

            return null;
        }else{
            String zdravila=getValue(self.getString(R.string.shramba_tag_zdravila),(Element) uporabnik);
            return zdravila;
        }
    }
    public NodeList pridobiZgodovino() {
        try {
            String zgodovinaStr=beriIzDatoteke(self.getString(R.string.filename_zgodovina));
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc;
            NodeList nList;
            if(zgodovinaStr!=null) {
                InputStream is = new ByteArrayInputStream(zgodovinaStr.getBytes(StandardCharsets.UTF_8));
                doc = dBuilder.parse(is);
                doc.normalize();
                //pridobimo vse elemente z tagom "zgodovina"
                nList = doc.getChildNodes().item(0).getChildNodes();
            }
            else{
                nList=null;
            }


            return nList;

        } catch (Exception e) {e.printStackTrace();}
        return null;
    }

    public int pridobiVelikostZgodovine(){
        int velikost=0;
        NodeList zgodovina=pridobiZgodovino();
        if(zgodovina!=null){
            velikost=zgodovina.getLength();
        }
        return velikost;
    }

    public String dodajZgodovino(String cas, String trajanje, String intenzivnost, String sprozilci) {
        try {
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document doc = documentBuilder.newDocument();
            NodeList nList=pridobiZgodovino();
            Element root=doc.createElement(self.getString(R.string.shramba_tag_zgodovina));
            int id=0;
            if (nList!=null&&nList.getLength()>0) {
                id= Integer.parseInt(getValue(self.getString(R.string.shramba_tag_id),(Element)nList.item(nList.getLength()-1)))+1;
                for (int i=0; i<nList.getLength();i++){
                    Node node =nList.item(i);
                    root.appendChild(doc.importNode(node,true));
                }
            }
            Element dogodekEl = doc.createElement(self.getString(R.string.shramba_tag_dogodek));
            Element em = doc.createElement(self.getString(R.string.shramba_tag_trajanje));
            em.appendChild(doc.createTextNode(cas));
            dogodekEl.appendChild(em);

            em=doc.createElement(self.getString(R.string.shramba_tag_cas));
            em.appendChild(doc.createTextNode(trajanje));
            dogodekEl.appendChild(em);

            em=doc.createElement(self.getString(R.string.shramba_tag_intenzivnosst));
            em.appendChild(doc.createTextNode(intenzivnost));
            dogodekEl.appendChild(em);

            em=doc.createElement(self.getString(R.string.shramba_tag_sprozilci));
            em.appendChild(doc.createTextNode(sprozilci));
            dogodekEl.appendChild(em);

            em=doc.createElement(self.getString(R.string.shramba_tag_id));
            em.appendChild(doc.createTextNode(String.valueOf(id)));//pristejemo 1
            dogodekEl.appendChild(em);



            root.appendChild(dogodekEl);
            doc.appendChild(root);

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StringWriter strWriter = new StringWriter();
            StreamResult result = new StreamResult(strWriter);
            transformer.transform(source, result);
            vpsiVDatoteko(strWriter.getBuffer().toString(), self.getString(R.string.filename_zgodovina));
            return String.valueOf(id);
        } catch (Exception e) {e.printStackTrace();}
        return null;
    }

    public void izbrisiZgodovino(String id){

        try {
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document doc = documentBuilder.newDocument();
            NodeList zgodovina =pridobiZgodovino();
            Element root=doc.createElement(self.getString(R.string.shramba_tag_zgodovina));
            if (zgodovina!=null&&zgodovina.getLength()>0) {
                for (int i=0; i<zgodovina.getLength();i++){
                    Node node =zgodovina.item(i);
                    Element el= (Element)node;
                    if(!el.getElementsByTagName(self.getString(R.string.shramba_tag_id)).item(0).getTextContent().equals(id)){
                        root.appendChild(doc.importNode(node, true));
                    }
                }
            }
            doc.appendChild(root);

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StringWriter strWriter = new StringWriter();
            StreamResult result = new StreamResult(strWriter);
            transformer.transform(source, result);
            vpsiVDatoteko(strWriter.getBuffer().toString(), self.getString(R.string.filename_zgodovina));
        } catch (Exception e) {e.printStackTrace();}
    }
    public void ustvariUporabnika(String Ime,String Priimek, String Naslov, String osebnaStevilka, String zdravnikovaStevilka, String Zdravila) {
        try {
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document doc = documentBuilder.newDocument();

            Element root=doc.createElement(self.getString(R.string.shramba_tag_uporabnik));

            Element em = doc.createElement(self.getString(R.string.shramba_tag_ime));
            em.appendChild(doc.createTextNode(Ime));
            root.appendChild(em);

            em = doc.createElement(self.getString(R.string.shramba_tag_priimek));
            em.appendChild(doc.createTextNode(Priimek));
            root.appendChild(em);

            em = doc.createElement(self.getString(R.string.shramba_tag_naslov));
            em.appendChild(doc.createTextNode(Naslov));
            root.appendChild(em);

            em = doc.createElement(self.getString(R.string.shramba_tag_osebnastevilka));
            em.appendChild(doc.createTextNode(osebnaStevilka));
            root.appendChild(em);

            em = doc.createElement(self.getString(R.string.shramba_tag_zdravnikovastevilka));
            em.appendChild(doc.createTextNode(zdravnikovaStevilka));
            root.appendChild(em);

            em = doc.createElement(self.getString(R.string.shramba_tag_zdravila));
            em.appendChild(doc.createTextNode(Zdravila));
            root.appendChild(em);




            doc.appendChild(root);
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StringWriter strWriter = new StringWriter();
            StreamResult result = new StreamResult(strWriter);
            transformer.transform(source, result);
            vpsiVDatoteko(strWriter.getBuffer().toString(), self.getString(R.string.filename_uporabnik));

        } catch (Exception e) {e.printStackTrace();}
    }
    public static String getValue(String tag, Element element) {
        NodeList nodeList = element.getElementsByTagName(tag).item(0).getChildNodes();
        Node node = (Node) nodeList.item(0);
        String value="";
        if (node!=null){
            value=node.getNodeValue();
        }

        return value;

    }

    //za debugiranje
    private static String nodeListToString(NodeList nodes) throws TransformerException {
        DOMSource source = new DOMSource();
        StringWriter writer = new StringWriter();
        StreamResult result = new StreamResult(writer);
        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");

        for (int i = 0; i < nodes.getLength(); ++i) {
            source.setNode(nodes.item(i));
            transformer.transform(source, result);
        }

        return writer.toString();
    }
    public static String nodeToString(Node node) {
        StringWriter sw = new StringWriter();
        try {
            Transformer t = TransformerFactory.newInstance().newTransformer();
            t.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
            t.setOutputProperty(OutputKeys.INDENT, "yes");
            t.transform(new DOMSource(node), new StreamResult(sw));
        } catch (TransformerException te) {
        }
        return sw.toString();
    }
}