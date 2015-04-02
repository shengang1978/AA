
package com.mainconcept.rewsservice;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>MediaInfo complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="MediaInfo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ass" type="{http://api.rews.mainconcept.com/xsd}AudioStream" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="cffss" type="{http://api.rews.mainconcept.com/xsd}CffStream" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="containerType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="fileName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="isUnknown" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="metass" type="{http://api.rews.mainconcept.com/xsd}MetadataStream" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="subss" type="{http://api.rews.mainconcept.com/xsd}SubtitleStream" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="vss" type="{http://api.rews.mainconcept.com/xsd}VideoStream" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "MediaInfo", namespace = "http://api.rews.mainconcept.com/xsd", propOrder = {
    "ass",
    "cffss",
    "containerType",
    "fileName",
    "isUnknown",
    "metass",
    "subss",
    "vss"
})
public class MediaInfo {

    @XmlElement(nillable = true)
    protected List<AudioStream> ass;
    @XmlElement(nillable = true)
    protected List<CffStream> cffss;
    @XmlElement(nillable = true)
    protected String containerType;
    @XmlElement(nillable = true)
    protected String fileName;
    @XmlElement(nillable = true)
    protected Boolean isUnknown;
    @XmlElement(nillable = true)
    protected List<MetadataStream> metass;
    @XmlElement(nillable = true)
    protected List<SubtitleStream> subss;
    @XmlElement(nillable = true)
    protected List<VideoStream> vss;

    /**
     * Gets the value of the ass property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the ass property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAss().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link AudioStream }
     * 
     * 
     */
    public List<AudioStream> getAss() {
        if (ass == null) {
            ass = new ArrayList<AudioStream>();
        }
        return this.ass;
    }

    /**
     * Gets the value of the cffss property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the cffss property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCffss().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link CffStream }
     * 
     * 
     */
    public List<CffStream> getCffss() {
        if (cffss == null) {
            cffss = new ArrayList<CffStream>();
        }
        return this.cffss;
    }

    /**
     * 获取containerType属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getContainerType() {
        return containerType;
    }

    /**
     * 设置containerType属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setContainerType(String value) {
        this.containerType = value;
    }

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
     * 获取isUnknown属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIsUnknown() {
        return isUnknown;
    }

    /**
     * 设置isUnknown属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIsUnknown(Boolean value) {
        this.isUnknown = value;
    }

    /**
     * Gets the value of the metass property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the metass property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getMetass().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link MetadataStream }
     * 
     * 
     */
    public List<MetadataStream> getMetass() {
        if (metass == null) {
            metass = new ArrayList<MetadataStream>();
        }
        return this.metass;
    }

    /**
     * Gets the value of the subss property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the subss property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSubss().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link SubtitleStream }
     * 
     * 
     */
    public List<SubtitleStream> getSubss() {
        if (subss == null) {
            subss = new ArrayList<SubtitleStream>();
        }
        return this.subss;
    }

    /**
     * Gets the value of the vss property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the vss property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getVss().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link VideoStream }
     * 
     * 
     */
    public List<VideoStream> getVss() {
        if (vss == null) {
            vss = new ArrayList<VideoStream>();
        }
        return this.vss;
    }

}
