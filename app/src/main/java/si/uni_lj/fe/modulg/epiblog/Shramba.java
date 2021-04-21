package si.uni_lj.fe.modulg.epiblog;

import android.app.Activity;
import android.content.Context;
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
import javax.xml.transform.Transformer;
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

    public NodeList pridobiUporabnika(String uporabnikStr) {
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            InputStream is = new ByteArrayInputStream(uporabnikStr.getBytes(StandardCharsets.UTF_8));
            Document doc = dBuilder.parse(is);
            Element element=doc.getDocumentElement();
            element.normalize();
            NodeList nList = doc.getElementsByTagName("employee");
            return nList;

        } catch (Exception e) {e.printStackTrace();}
        return null;
    }
    public NodeList pridobiZgodovino() {
        try {
            String zgodovinaStr=beriIzDatoteke(self.getString(R.string.filename_zgodovina));
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            InputStream is = new ByteArrayInputStream(zgodovinaStr.getBytes(StandardCharsets.UTF_8));
            Document doc = dBuilder.parse(is);
            //pridobimo vse elemente z tagom "zgodovina"
            NodeList nList = doc.getElementsByTagName(self.getString(R.string.tagname_zgodovina));
            return nList;

        } catch (Exception e) {e.printStackTrace();}
        return null;
    }

    public void dodajZgodovino(String trajanje, String cas) {
        try {
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document doc = documentBuilder.newDocument();
            NodeList nList=pridobiZgodovino();
            for (int i=0; i<nList.getLength(); i++) {
                Node node = nList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE){
                    doc.appendChild(node);
                }
            }//end of for loop
            Element zgoElement = doc.createElement(self.getString(R.string.tagname_zgodovina));

            Element em = doc.createElement("Trajanje");
            em.appendChild(doc.createTextNode(trajanje));
            zgoElement.appendChild(em);

            em=doc.createElement("Cas");
            em.appendChild(doc.createTextNode(trajanje));
            zgoElement.appendChild(em);

            doc.appendChild(zgoElement);

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StringWriter strWriter = new StringWriter();
            StreamResult result = new StreamResult(strWriter);
            transformer.transform(source, result);
            vpsiVDatoteko(strWriter.getBuffer().toString(), self.getString(R.string.filename_zgodovina));
        } catch (Exception e) {e.printStackTrace();}

    }
    public void ustvariUporabnika(String Ime,String Priimek, String Naslov, String Stevilka) {
        try {
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document doc = documentBuilder.newDocument();

            Element em = doc.createElement("ime");
            em.appendChild(doc.createTextNode("Rita"));
            doc.appendChild(em);

            em = doc.createElement("Priimek");
            em.appendChild(doc.createTextNode("Roy"));
            doc.appendChild(em);

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
}
