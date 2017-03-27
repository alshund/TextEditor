package TextEditor;

import javax.swing.*;
import javax.xml.stream.*;
import java.awt.*;
import java.io.*;

/**
 * Created by shund on 27.03.2017.
 */
public class FileHandler {
    private Text text;
    public FileHandler(FrameWindow frameWindow){
        text = frameWindow.getTextPanel().getText();
    }
    public void openFile(){
        JFileChooser fileChooser = new JFileChooser();
        if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION){
            String filePath = fileChooser.getSelectedFile().getPath();
            boolean isTxt = getExtension(fileChooser.getSelectedFile().getName()).equals("txt");
            if(isTxt){
                openTxtFile(filePath);
            } else{
                openXmlFile(filePath);
            }
        }
    }
    public void saveXmlFile(){
        try {
            JFileChooser jFileChooser = new JFileChooser();
            if(jFileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION){
                XMLOutputFactory xmlOutputFactory = XMLOutputFactory.newInstance();
                XMLStreamWriter xmlStreamWriter = xmlOutputFactory.createXMLStreamWriter(new FileWriter(jFileChooser.getSelectedFile() + ".xml"));
                xmlStreamWriter.writeStartDocument("1.0");
                xmlStreamWriter.writeStartElement("Text");
                for (Line line : text.getText()){
                    xmlStreamWriter.writeStartElement("Line");
                    for (Char charElement : line.getLine()){
                        xmlStreamWriter.writeStartElement("Char");
                        xmlStreamWriter.writeAttribute("Font", charElement.getFontType());
                        xmlStreamWriter.writeAttribute("Style", Integer.toString(charElement.getFontStyle()));
                        xmlStreamWriter.writeAttribute("Size", Integer.toString(charElement.getFontSize()));
                        xmlStreamWriter.writeCharacters(charElement.getStringElement());
                        xmlStreamWriter.writeEndElement();
                    }
                    xmlStreamWriter.writeEndElement();
                }
                xmlStreamWriter.writeEndElement();
                xmlStreamWriter.writeEndDocument();
                xmlStreamWriter.flush();
            }
        } catch (Exception exception) {
            JOptionPane.showMessageDialog(null, "Can't save file", "ERROR", JOptionPane.WARNING_MESSAGE|JOptionPane.OK_OPTION);
        }
    }
    private void openTxtFile(String filePath){
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath));
            String readerLine;
            while( (readerLine = bufferedReader.readLine()) != null){
                Line newLine = new Line();
                char[] charArray = readerLine.toCharArray();
                for (char charElement : charArray){
                    newLine.add(new Char(charElement, text.getFont()));
                }
                text.add(newLine);
            }
        } catch (Exception exception){
            JOptionPane.showMessageDialog(null, "Can't open file", "ERROR", JOptionPane.WARNING_MESSAGE|JOptionPane.OK_OPTION);
        }

    }
    private void openXmlFile(String filePath){
        try {
            Line newLine = new Line();
            XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
            XMLStreamReader xmlStreamReader = xmlInputFactory.createXMLStreamReader(filePath, new FileInputStream(filePath));
            while(xmlStreamReader.hasNext()){
                xmlStreamReader.next();
                if (xmlStreamReader.isStartElement()){
                    if (xmlStreamReader.getLocalName().equals("Line")){
                        newLine = new Line();
                    } else if (xmlStreamReader.getLocalName().equals("Char")){
                        String fontType = xmlStreamReader.getAttributeValue(null, "Font");
                        String fontStyle = xmlStreamReader.getAttributeValue(null, "Style");
                        String fontSize = xmlStreamReader.getAttributeValue(null, "Size");
                        xmlStreamReader.next();
                        newLine.add(xmlStreamReader.getText(), fontType, fontStyle, fontSize);
                    }
                } else if(xmlStreamReader.isEndElement()){
                    if (xmlStreamReader.getLocalName().equals("Line")) {
                        text.add(newLine);
                    }
                }
            }
        } catch (Exception exception){
            JOptionPane.showMessageDialog(null, "Can't open file", "ERROR", JOptionPane.WARNING_MESSAGE|JOptionPane.OK_OPTION);
        }
    }
    private String getExtension(String name){
        String extension = null;
        int i = name.lastIndexOf('.');
        if (i > 0 && i < name.length() - 1){
            extension = name.substring(i + 1);
        }
        return  extension;
    }
}
