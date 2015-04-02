
package com.mainconcept.rewsservice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>InputFile complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="InputFile">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="fileName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="filePath" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="filePath1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="inLoc" type="{http://api.rews.mainconcept.com/xsd}Location" minOccurs="0"/>
 *         &lt;element name="inLocAuth" type="{http://location.common.mainconcept.com/xsd}LocAuthTCE" minOccurs="0"/>
 *         &lt;element name="isAbsPath" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="locID" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="locName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="locPath" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="locPath1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="reference" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="skipDownload" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="type" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "InputFile", namespace = "http://api.rews.mainconcept.com/xsd", propOrder = {
    "fileName",
    "filePath",
    "filePath1",
    "id",
    "inLoc",
    "inLocAuth",
    "isAbsPath",
    "locID",
    "locName",
    "locPath",
    "locPath1",
    "reference",
    "skipDownload",
    "type"
})
public class InputFile {

    @XmlElement(nillable = true)
    protected String fileName;
    @XmlElement(nillable = true)
    protected String filePath;
    @XmlElement(nillable = true)
    protected String filePath1;
    @XmlElement(nillable = true)
    protected Integer id;
    @XmlElement(nillable = true)
    protected Location inLoc;
    @XmlElement(nillable = true)
    protected LocAuthTCE inLocAuth;
    @XmlElement(nillable = true)
    protected Boolean isAbsPath;
    @XmlElement(nillable = true)
    protected Integer locID;
    @XmlElement(nillable = true)
    protected String locName;
    @XmlElement(nillable = true)
    protected String locPath;
    @XmlElement(nillable = true)
    protected String locPath1;
    @XmlElement(nillable = true)
    protected String reference;
    @XmlElement(nillable = true)
    protected Boolean skipDownload;
    @XmlElement(nillable = true)
    protected String type;

    /**
     * 获取fileName属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * 设置fileName属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFileName(String value) {
        this.fileName = value;
    }

    /**
     * 获取filePath属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFilePath() {
        return filePath;
    }

    /**
     * 设置filePath属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFilePath(String value) {
        this.filePath = value;
    }

    /**
     * 获取filePath1属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFilePath1() {
        return filePath1;
    }

    /**
     * 设置filePath1属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFilePath1(String value) {
        this.filePath1 = value;
    }

    /**
     * 获取id属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置id属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setId(Integer value) {
        this.id = value;
    }

    /**
     * 获取inLoc属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Location }
     *     
     */
    public Location getInLoc() {
        return inLoc;
    }

    /**
     * 设置inLoc属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Location }
     *     
     */
    public void setInLoc(Location value) {
        this.inLoc = value;
    }

    /**
     * 获取inLocAuth属性的值。
     * 
     * @return
     *     possible object is
     *     {@link LocAuthTCE }
     *     
     */
    public LocAuthTCE getInLocAuth() {
        return inLocAuth;
    }

    /**
     * 设置inLocAuth属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link LocAuthTCE }
     *     
     */
    public void setInLocAuth(LocAuthTCE value) {
        this.inLocAuth = value;
    }

    /**
     * 获取isAbsPath属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIsAbsPath() {
        return isAbsPath;
    }

    /**
     * 设置isAbsPath属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIsAbsPath(Boolean value) {
        this.isAbsPath = value;
    }

    /**
     * 获取locID属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getLocID() {
        return locID;
    }

    /**
     * 设置locID属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setLocID(Integer value) {
        this.locID = value;
    }

    /**
     * 获取locName属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLocName() {
        return locName;
    }

    /**
     * 设置locName属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLocName(String value) {
        this.locName = value;
    }

    /**
     * 获取locPath属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLocPath() {
        return locPath;
    }

    /**
     * 设置locPath属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLocPath(String value) {
        this.locPath = value;
    }

    /**
     * 获取locPath1属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLocPath1() {
        return locPath1;
    }

    /**
     * 设置locPath1属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLocPath1(String value) {
        this.locPath1 = value;
    }

    /**
     * 获取reference属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReference() {
        return reference;
    }

    /**
     * 设置reference属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReference(String value) {
        this.reference = value;
    }

    /**
     * 获取skipDownload属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isSkipDownload() {
        return skipDownload;
    }

    /**
     * 设置skipDownload属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setSkipDownload(Boolean value) {
        this.skipDownload = value;
    }

    /**
     * 获取type属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getType() {
        return type;
    }

    /**
     * 设置type属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setType(String value) {
        this.type = value;
    }

}
