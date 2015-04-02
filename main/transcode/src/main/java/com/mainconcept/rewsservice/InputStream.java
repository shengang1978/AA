
package com.mainconcept.rewsservice;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>InputStream complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="InputStream">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="appendPos" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="channelLayout" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="fileIDs" type="{http://www.w3.org/2001/XMLSchema}int" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="inPoint" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="inputFiles" type="{http://api.rews.mainconcept.com/xsd}InputFile" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="isMain" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="language" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="outPoint" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="reference" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="streamNum" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="streamType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="value1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="value2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="virtualAudioChannels" type="{http://api.rews.mainconcept.com/xsd}VirtualAudioChannel" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "InputStream", namespace = "http://api.rews.mainconcept.com/xsd", propOrder = {
    "appendPos",
    "channelLayout",
    "fileIDs",
    "inPoint",
    "inputFiles",
    "isMain",
    "language",
    "name",
    "outPoint",
    "reference",
    "streamNum",
    "streamType",
    "value1",
    "value2",
    "virtualAudioChannels"
})
public class InputStream {

    @XmlElement(nillable = true)
    protected String appendPos;
    @XmlElement(nillable = true)
    protected String channelLayout;
    @XmlElement(nillable = true)
    protected List<Integer> fileIDs;
    @XmlElement(nillable = true)
    protected String inPoint;
    @XmlElement(nillable = true)
    protected List<InputFile> inputFiles;
    @XmlElement(nillable = true)
    protected Boolean isMain;
    @XmlElement(nillable = true)
    protected String language;
    @XmlElement(nillable = true)
    protected String name;
    @XmlElement(nillable = true)
    protected String outPoint;
    @XmlElement(nillable = true)
    protected String reference;
    @XmlElement(nillable = true)
    protected Integer streamNum;
    @XmlElement(nillable = true)
    protected String streamType;
    @XmlElement(nillable = true)
    protected String value1;
    @XmlElement(nillable = true)
    protected String value2;
    @XmlElement(nillable = true)
    protected List<VirtualAudioChannel> virtualAudioChannels;

    /**
     * 获取appendPos属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAppendPos() {
        return appendPos;
    }

    /**
     * 设置appendPos属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAppendPos(String value) {
        this.appendPos = value;
    }

    /**
     * 获取channelLayout属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getChannelLayout() {
        return channelLayout;
    }

    /**
     * 设置channelLayout属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setChannelLayout(String value) {
        this.channelLayout = value;
    }

    /**
     * Gets the value of the fileIDs property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the fileIDs property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getFileIDs().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Integer }
     * 
     * 
     */
    public List<Integer> getFileIDs() {
        if (fileIDs == null) {
            fileIDs = new ArrayList<Integer>();
        }
        return this.fileIDs;
    }

    /**
     * 获取inPoint属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInPoint() {
        return inPoint;
    }

    /**
     * 设置inPoint属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInPoint(String value) {
        this.inPoint = value;
    }

    /**
     * Gets the value of the inputFiles property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the inputFiles property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getInputFiles().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link InputFile }
     * 
     * 
     */
    public List<InputFile> getInputFiles() {
        if (inputFiles == null) {
            inputFiles = new ArrayList<InputFile>();
        }
        return this.inputFiles;
    }

    /**
     * 获取isMain属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIsMain() {
        return isMain;
    }

    /**
     * 设置isMain属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIsMain(Boolean value) {
        this.isMain = value;
    }

    /**
     * 获取language属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLanguage() {
        return language;
    }

    /**
     * 设置language属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLanguage(String value) {
        this.language = value;
    }

    /**
     * 获取name属性的值。
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
     * 设置name属性的值。
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
     * 获取outPoint属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOutPoint() {
        return outPoint;
    }

    /**
     * 设置outPoint属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOutPoint(String value) {
        this.outPoint = value;
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
     * 获取streamNum属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getStreamNum() {
        return streamNum;
    }

    /**
     * 设置streamNum属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setStreamNum(Integer value) {
        this.streamNum = value;
    }

    /**
     * 获取streamType属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStreamType() {
        return streamType;
    }

    /**
     * 设置streamType属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStreamType(String value) {
        this.streamType = value;
    }

    /**
     * 获取value1属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getValue1() {
        return value1;
    }

    /**
     * 设置value1属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setValue1(String value) {
        this.value1 = value;
    }

    /**
     * 获取value2属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getValue2() {
        return value2;
    }

    /**
     * 设置value2属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setValue2(String value) {
        this.value2 = value;
    }

    /**
     * Gets the value of the virtualAudioChannels property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the virtualAudioChannels property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getVirtualAudioChannels().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link VirtualAudioChannel }
     * 
     * 
     */
    public List<VirtualAudioChannel> getVirtualAudioChannels() {
        if (virtualAudioChannels == null) {
            virtualAudioChannels = new ArrayList<VirtualAudioChannel>();
        }
        return this.virtualAudioChannels;
    }

}
