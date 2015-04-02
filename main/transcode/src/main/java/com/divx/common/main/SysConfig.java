package com.divx.common.main;


import org.apache.log4j.Logger;

// Read the configuration file
public final class SysConfig
{
    private static final Logger log = Logger.getLogger(SysConfig.class);

    
    // Separator
    static final public String LINE_SEPARATOR = System
            .getProperty("line.separator");

    // Configuration file name
    static final private String configXML = "SysConfig.xml";
    
    // Document root;
    static private org.dom4j.Document root = null;
    static private String dyjSpId = null;
    static
    {
        try
        {
            root = XMLUtil.parseXml(configXML);
        } catch (Exception e)
        {
            System.err.println("Failed to parse xml: " + configXML);
            log.error("Failed to parse xml: " + configXML, e);
        }
    }

    public static String getValue(String xql)
    {
        return XMLUtil.getString(root, "/sysconfig/" + xql);
    }
    
    public static String getValue(String xql, String def)
    {
    	return XMLUtil.getString(root, "/sysconfig/" + xql, def);
    }

    public static int getIntValue(String xql, int defValue)
    {
        return XMLUtil.getInt(root, "/sysconfig/" + xql, defValue);
    }

}
