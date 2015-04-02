
package com.mainconcept.rewsservice;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Server complex type�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
 * 
 * <pre>
 * &lt;complexType name="Server">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="address" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="codecs" type="{http://api.rews.mainconcept.com/xsd}Codec" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="errorText" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="isError" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="isFree" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="jreSpec" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="licenseInfo" type="{http://api.rews.mainconcept.com/xsd}LicenseInfo" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="linkState" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="operatingSystem" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="operatingSystemType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="port" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="queueLen" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="twsAPIVersion" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="twsVersion" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="uri" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Server", namespace = "http://api.rews.mainconcept.com/xsd", propOrder = {
    "address",
    "codecs",
    "description",
    "errorText",
    "id",
    "isError",
    "isFree",
    "jreSpec",
    "licenseInfo",
    "linkState",
    "name",
    "operatingSystem",
    "operatingSystemType",
    "port",
    "queueLen",
    "twsAPIVersion",
    "twsVersion",
    "uri"
})
public class Server {

    @XmlElement(nillable = true)
    protected String address;
    @XmlElement(nillable = true)
    protected List<Codec> codecs;
    @XmlElement(nillable = true)
    protected String description;
    @XmlElement(nillable = true)
    protected String errorText;
    @XmlElement(nillable = true)
    protected Integer id;
    @XmlElement(nillable = true)
    protected Boolean isError;
    @XmlElement(nillable = true)
    protected Boolean isFree;
    @XmlElement(nillable = true)
    protected String jreSpec;
    @XmlElement(nillable = true)
    protected List<LicenseInfo> licenseInfo;
    @XmlElement(nillable = true)
    protected String linkState;
    @XmlElement(nillable = true)
    protected String name;
    @XmlElement(nillable = true)
    protected String operatingSystem;
    @XmlElement(nillable = true)
    protected String operatingSystemType;
    @XmlElement(nillable = true)
    protected String port;
    @XmlElement(nillable = true)
    protected Integer queueLen;
    @XmlElement(nillable = true)
    protected String twsAPIVersion;
    @XmlElement(nillable = true)
    protected String twsVersion;
    @XmlElement(nillable = true)
    protected String uri;

    /**
     * ��ȡaddress���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAddress() {
        return address;
    }

    /**
     * ����address���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAddress(String value) {
        this.address = value;
    }

    /**
     * Gets the value of the codecs property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the codecs property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCodecs().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Codec }
     * 
     * 
     */
    public List<Codec> getCodecs() {
        if (codecs == null) {
            codecs = new ArrayList<Codec>();
        }
        return this.codecs;
    }

    /**
     * ��ȡdescription���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescription() {
        return description;
    }

    /**
     * ����description���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescription(String value) {
        this.description = value;
    }

    /**
     * ��ȡerrorText���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getErrorText() {
        return errorText;
    }

    /**
     * ����errorText���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setErrorText(String value) {
        this.errorText = value;
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
     * ��ȡisError���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIsError() {
        return isError;
    }

    /**
     * ����isError���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIsError(Boolean value) {
        this.isError = value;
    }

    /**
     * ��ȡisFree���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIsFree() {
        return isFree;
    }

    /**
     * ����isFree���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIsFree(Boolean value) {
        this.isFree = value;
    }

    /**
     * ��ȡjreSpec���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getJreSpec() {
        return jreSpec;
    }

    /**
     * ����jreSpec���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setJreSpec(String value) {
        this.jreSpec = value;
    }

    /**
     * Gets the value of the licenseInfo property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the licenseInfo property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getLicenseInfo().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link LicenseInfo }
     * 
     * 
     */
    public List<LicenseInfo> getLicenseInfo() {
        if (licenseInfo == null) {
            licenseInfo = new ArrayList<LicenseInfo>();
        }
        return this.licenseInfo;
    }

    /**
     * ��ȡlinkState���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLinkState() {
        return linkState;
    }

    /**
     * ����linkState���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLinkState(String value) {
        this.linkState = value;
    }

    /**
     * ��ȡname���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getName() {
        return name;
    }

    /**
     * ����name���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * ��ȡoperatingSystem���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOperatingSystem() {
        return operatingSystem;
    }

    /**
     * ����operatingSystem���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOperatingSystem(String value) {
        this.operatingSystem = value;
    }

    /**
     * ��ȡoperatingSystemType���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOperatingSystemType() {
        return operatingSystemType;
    }

    /**
     * ����operatingSystemType���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOperatingSystemType(String value) {
        this.operatingSystemType = value;
    }

    /**
     * ��ȡport���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPort() {
        return port;
    }

    /**
     * ����port���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPort(String value) {
        this.port = value;
    }

    /**
     * ��ȡqueueLen���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getQueueLen() {
        return queueLen;
    }

    /**
     * ����queueLen���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setQueueLen(Integer value) {
        this.queueLen = value;
    }

    /**
     * ��ȡtwsAPIVersion���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTwsAPIVersion() {
        return twsAPIVersion;
    }

    /**
     * ����twsAPIVersion���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTwsAPIVersion(String value) {
        this.twsAPIVersion = value;
    }

    /**
     * ��ȡtwsVersion���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTwsVersion() {
        return twsVersion;
    }

    /**
     * ����twsVersion���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTwsVersion(String value) {
        this.twsVersion = value;
    }

    /**
     * ��ȡuri���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUri() {
        return uri;
    }

    /**
     * ����uri���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUri(String value) {
        this.uri = value;
    }

}
