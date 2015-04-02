
package com.mainconcept.rewsservice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>InputFile complex type�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
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
     * ��ȡfileName���Ե�ֵ��
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
     * ����fileName���Ե�ֵ��
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
     * ��ȡfilePath���Ե�ֵ��
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
     * ����filePath���Ե�ֵ��
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
     * ��ȡfilePath1���Ե�ֵ��
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
     * ����filePath1���Ե�ֵ��
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
     * ��ȡid���Ե�ֵ��
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
     * ����id���Ե�ֵ��
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
     * ��ȡinLoc���Ե�ֵ��
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
     * ����inLoc���Ե�ֵ��
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
     * ��ȡinLocAuth���Ե�ֵ��
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
     * ����inLocAuth���Ե�ֵ��
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
     * ��ȡisAbsPath���Ե�ֵ��
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
     * ����isAbsPath���Ե�ֵ��
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
     * ��ȡlocID���Ե�ֵ��
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
     * ����locID���Ե�ֵ��
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
     * ��ȡlocName���Ե�ֵ��
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
     * ����locName���Ե�ֵ��
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
     * ��ȡlocPath���Ե�ֵ��
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
     * ����locPath���Ե�ֵ��
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
     * ��ȡlocPath1���Ե�ֵ��
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
     * ����locPath1���Ե�ֵ��
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
     * ��ȡreference���Ե�ֵ��
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
     * ����reference���Ե�ֵ��
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
     * ��ȡskipDownload���Ե�ֵ��
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
     * ����skipDownload���Ե�ֵ��
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
     * ��ȡtype���Ե�ֵ��
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
     * ����type���Ե�ֵ��
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
