package com.divx.common.main;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;


import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

// Utility for parsing XML file
public class XMLUtil
{

    private static final Logger log = Logger.getLogger(XMLUtil.class);

    private XMLUtil()
    {
    }

    public static Document parseText(String xmlText)
    {
        try
        {
            if (null != xmlText)
            {
                return DocumentHelper.parseText(xmlText);
            }
        } catch (Exception e)
        {
            log.error(e);
        }

        return null;
    }

    public static Document parseXml(String xmlSource)
    {
        try
        {
            if (null != xmlSource)
            {
                InputStream in = Thread.currentThread().getContextClassLoader()
                        .getResourceAsStream(xmlSource);
                return parseXml(in);
            }
        } catch (Exception e)
        {
            log.error(e);
        }

        return null;
    }

    static public Document parseXml(byte[] xmlData) throws Exception
    {
        if (xmlData == null)
            return null;

        java.io.ByteArrayInputStream in = new ByteArrayInputStream(xmlData);
        return parseXml(in);
    }

    static public Document parseXml(File xmlFile) throws Exception
    {
        if (xmlFile == null || !xmlFile.exists())
            return null;
        Document root = null;
        try
        {
            SAXReader saxReader = new SAXReader();
            saxReader.setValidation(false);
            root = saxReader.read(xmlFile);
            return root;
        } catch (Exception e)
        {
            log.error("Failed to read file: " + xmlFile.getName());
            throw e;
        }
    }

    static public Document parseXml(InputStream in) throws Exception
    {
        if (in == null)
            return null;
        Document root = null;
        try
        {
            SAXReader saxReader = new SAXReader();
            saxReader.setValidation(false);
            root = saxReader.read(in);
            return root;
        } catch (Exception e)
        {
            log.error("Failed to read file: " + in);
            throw e;
        } finally
        {
            if (in != null)
                try
                {
                    in.close();
                } catch (Exception e)
                {
                }
        }
    }

    static public boolean isNodeExists(Node startNode, String xql)
    {
        if (startNode == null || xql == null)
        {
            return false;
        }

        Node node = startNode.selectSingleNode(xql);
        return (node != null);
    }

    static public String getString(Node startNode, String xql)
    {
        if (startNode == null || xql == null)
        {
            return null;
        }

        Node node = startNode.selectSingleNode(xql);
        if (node != null)
        {
            return node.getText();
        } else
        {
            return null;
        }
    }

    static public String getString(Node startNode, String xql, String def)
    {
        if (startNode == null || xql == null)
        {
            return def;
        }

        Node node = startNode.selectSingleNode(xql);
        if (node != null)
        {
            return node.getText();
        } else
        {
            return def;
        }
    }

    static public int getInt(Node startNode, String xql, int def)
    {
        String nValue = getString(startNode, xql);
        try
        {
            return Integer.parseInt(nValue);
        } catch (Exception e)
        {
            return def;
        }
    }

    static public float getFloat(Node startNode, String xql, float def)
    {
        String nValue = getString(startNode, xql);
        try
        {
            return Float.parseFloat(nValue);
        } catch (Exception e)
        {
            return def;
        }
    }

    static public long getLong(Node startNode, String xql, long def)
    {
        String nValue = getString(startNode, xql);
        try
        {
            return Long.parseLong(nValue);
        } catch (Exception e)
        {
            return def;
        }
    }

    static public boolean setNodeValue(Node startNode, String xql,
            String nodeValue)
    {
        if (startNode == null || xql == null)
        {
            return false;
        }

        Node node = startNode.selectSingleNode(xql);
        if (node != null)
        {
            node.setText(nodeValue);
            return true;
        } else
        {
            return false;
        }
    }

    static public boolean setNodePropertyValue(Node startNode, String xql,
            String propertyName, String nodeValue)
    {
        if (startNode == null || xql == null)
        {
            return false;
        }

        Element node = (Element) startNode.selectSingleNode(xql);
        if (node != null)
        {
            node.attribute(propertyName).setText(nodeValue);
            return true;
        } else
        {
            return false;
        }
    }

    static public void printAndClose(Document document, OutputStream out,
            boolean indent, boolean newLine)
    {
        org.dom4j.io.XMLWriter writer = null;
        try
        {
            org.dom4j.io.OutputFormat format = org.dom4j.io.OutputFormat
                    .createPrettyPrint();
            format.setEncoding("UTF-8");
            format.setIndent(indent);
            format.setNewlines(newLine);
            format.setOmitEncoding(false);
            format.setSuppressDeclaration(false);
            /*
            System.out.println(format.isExpandEmptyElements());
            System.out.println(format.isNewlines());
            System.out.println(format.isOmitEncoding());
            System.out.println(format.isPadText());
            System.out.println(format.isSuppressDeclaration());
            System.out.println(format.isTrimText());
            System.out.println(format.isXHTML());
            */
            writer = new org.dom4j.io.XMLWriter(out, format);
            writer.write(document);
        } catch (Exception e)
        {
            e.printStackTrace();
        } finally
        {
            if (out != null)
                try
                {
                    out.close();
                } catch (Exception e)
                {
                }
        }
    }

    static public void printAndClose(Document document, OutputStream out)
    {
        org.dom4j.io.XMLWriter writer = null;
        try
        {
            org.dom4j.io.OutputFormat format = org.dom4j.io.OutputFormat
                    .createPrettyPrint();
            format.setEncoding("UTF-8");
            format.setTrimText(true);
            format.setIndent(true);
            format.setNewlines(true);
            format.setOmitEncoding(false);
            format.setSuppressDeclaration(false);
            writer = new org.dom4j.io.XMLWriter(out, format);
            writer.write(document);
        } catch (Exception e)
        {
            e.printStackTrace();
        } finally
        {
            if (out != null)
                try
                {
                    out.close();
                } catch (Exception e)
                {
                }
        }
    }

    static public void printAndClose(Node root, OutputStream out, String charSet)
    {
        org.dom4j.io.XMLWriter writer = null;
        try
        {
            org.dom4j.io.OutputFormat format = OutputFormat.createPrettyPrint();
            format.setEncoding(charSet);
            writer = new org.dom4j.io.XMLWriter(out, format);
            writer.write(root);
        } catch (Exception e)
        {
            log.error(e);
        } finally
        {
            if (out != null)
                try
                {
                    out.close();
                } catch (Exception e)
                {
                }
        }
    }

    static public void print(Document document, OutputStream out)
    {
        org.dom4j.io.XMLWriter writer = null;
        org.dom4j.io.OutputFormat format = new org.dom4j.io.OutputFormat();
        format.setEncoding("UTF-8");
        format.setTrimText(true);
        format.setIndent(false);
        format.setOmitEncoding(false);
        format.setSuppressDeclaration(false);
        try
        {
            writer = new org.dom4j.io.XMLWriter(out, format);
            writer.write(document);
        } catch (Exception e)
        {
            e.printStackTrace();
        } finally
        {
            try
            {
                out.flush();
            } catch (Exception e)
            {
            }
        }
    }

    static public void print(Document document, OutputStream out, String charSet)
    {
        org.dom4j.io.XMLWriter writer = null;
        org.dom4j.io.OutputFormat format = org.dom4j.io.OutputFormat
                .createPrettyPrint();
        format.setNewlines(true);
        format.setEncoding(charSet);
        format.setTrimText(true);
        format.setIndent(true);
        format.setOmitEncoding(false);
        format.setSuppressDeclaration(false);

        try
        {
            writer = new org.dom4j.io.XMLWriter(out, format);
            writer.write(document);
        } catch (Exception e)
        {
            e.printStackTrace();
        } finally
        {
            try
            {
                out.flush();
            } catch (Exception e)
            {
            }
        }
    }
    
    public static void writeDocument(Document document, String outFile)
    {
        try
        {
            OutputFormat xmlFormat = OutputFormat.createPrettyPrint();
            xmlFormat.setEncoding("UTF-8");
            XMLWriter xmlWriter = new XMLWriter(new FileOutputStream(outFile), xmlFormat);
            xmlWriter.write(document);
            xmlWriter.close();
        } catch (Exception e)
        {
            log.error("WriteDocument", e);
        }
    }

    static public void main(String[] args)
    {
        try
        {
            Document document = org.dom4j.DocumentHelper.createDocument();
            org.dom4j.Element root = document.addElement("system");

            java.util.Properties properties = System.getProperties();
            for (java.util.Enumeration elements = properties.propertyNames(); elements
                    .hasMoreElements();)
            {
                String name = (String) elements.nextElement();
                String value = properties.getProperty(name);
                org.dom4j.Element element = root.addElement("property");
                element.addAttribute("name", name);
                element.addText(value);
            }
            root.addElement("tree").addAttribute("text", "test")
                    .addAttribute("src", "./admin/createMenu.do?pId=12");
            XMLUtil.printAndClose(document, System.out, true, true);
        } catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    static private Document createDocument()
    {
        try
        {
            Document document = org.dom4j.DocumentHelper.createDocument();
            org.dom4j.Element root = document.addElement("system");

            java.util.Properties properties = System.getProperties();
            for (java.util.Enumeration elements = properties.propertyNames(); elements
                    .hasMoreElements();)
            {
                String name = (String) elements.nextElement();
                String value = properties.getProperty(name);
                org.dom4j.Element element = root.addElement("property");
                element.addAttribute("name", name);
                element.addText(value);
            }
            root.addElement("tree").addAttribute("text", "test")
                    .addAttribute("src", "./admin/createMenu.do?pId=12");

            return document;
        } catch (Exception ex)
        {
            return null;
        }
    }

}
