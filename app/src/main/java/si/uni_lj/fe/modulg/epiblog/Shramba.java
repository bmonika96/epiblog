package si.uni_lj.fe.modulg.epiblog;

import android.app.Activity;
import android.content.Context;
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
                Log.d("atet",nodeListToString(nList));
            }
            else{
                nList=null;
            }


            return nList;

        } catch (Exception e) {e.printStackTrace();}
        return null;
    }

    public void dodajZgodovino(String cas, String trajanje, String intenzivnost, String sprozilci) {
        try {
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document doc = documentBuilder.newDocument();
            NodeList nList=pridobiZgodovino();
            Element root=doc.createElement("Zgodovina");
            if (nList!=null) {
                for (int i=0; i<nList.getLength();i++){
                    Node node =nList.item(i);
                    root.appendChild(doc.importNode(node,true));
                }
            }
            Element dogodekEl = doc.createElement("Dogodek");
            Element em = doc.createElement("Trajanje");
            em.appendChild(doc.createTextNode(cas));
            dogodekEl.appendChild(em);

            em=doc.createElement("Cas");
            em.appendChild(doc.createTextNode(trajanje));
            dogodekEl.appendChild(em);

            em=doc.createElement("Intenzivnost");
            em.appendChild(doc.createTextNode(intenzivnost));
            dogodekEl.appendChild(em);

            em=doc.createElement("Sprozilci");
            em.appendChild(doc.createTextNode(sprozilci));
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
        } catch (Exception e) {e.printStackTrace();}

    }
    public void ustvariUporabnika(String Ime,String Priimek, String Naslov, String Stevilka, String ZdravilaDa, String Zdravila) {
        try {
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document doc = documentBuilder.newDocument();

            Element root=doc.createElement("Uporabnik");

            Element em = doc.createElement("Ime");
            em.appendChild(doc.createTextNode(Ime));
            root.appendChild(em);

            em = doc.createElement("Priimek");
            em.appendChild(doc.createTextNode(Priimek));
            root.appendChild(em);

            em = doc.createElement("Naslov");
            em.appendChild(doc.createTextNode(Naslov));
            root.appendChild(em);

            em = doc.createElement("Stevilka");
            em.appendChild(doc.createTextNode(Stevilka));
            root.appendChild(em);

            em = doc.createElement("ZdravilaDa");
            em.appendChild(doc.createTextNode(ZdravilaDa));
            root.appendChild(em);

            em = doc.createElement("Zdravila");
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
        return node.getNodeValue();

    }
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
    private static String nodeToString(Node node) {
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