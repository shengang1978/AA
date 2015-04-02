
package com.mainconcept.rewsservice;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Preset complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="Preset">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="apply2Label" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="channels" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="codecs" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="command" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="command2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="idPreset" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="isDPS" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="isProxy" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="presetGroupName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="profile" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="streams" type="{http://api.rews.mainconcept.com/xsd}PresetStream" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="videoBitrate" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="videoHeight" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="videoWidth" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Preset", namespace = "http://api.rews.mainconcept.com/xsd", propOrder = {
    "apply2Label",
    "channels",
    "codecs",
    "command",
    "command2",
    "idPreset",
    "isDPS",
    "isProxy",
    "name",
    "presetGroupName",
    "profile",
    "streams",
    "videoBitrate",
    "videoHeight",
    "videoWidth"
})
public class Preset {

    @XmlElement(nillable = true)
    protected String apply2Label;
    @XmlElement(nillable = true)
    protected List<String> channels;
    @XmlElement(nillable = true)
    protected List<String> codecs;
    @XmlElement(nillable = true)
    protected String command;
    @XmlElement(nillable = true)
    protected String command2;
    @XmlElement(nillable = true)
    protected Integer idPreset;
    @XmlElement(nillable = true)
    protected Integer isDPS;
    @XmlElement(nillable = true)
    protected Boolean isProxy;
    @XmlElement(nillable = true)
    protected String name;
    @XmlElement(nillable = true)
    protected String presetGroupName;
    @XmlElement(nillable = true)
    protected Integer profile;
    @XmlElement(nillable = true)
    protected List<PresetStream> streams;
    @XmlElement(nillable = true)
    protected Integer videoBitrate;
    @XmlElement(nillable = true)
    protected Integer videoHeight;
    @XmlElement(nillable = true)
    protected Integer videoWidth;

    /**
     * 获取apply2Label属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getApply2Label() {
        return apply2Label;
    }

    /**
     * 设置apply2Label属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setApply2Label(String value) {
        this.apply2Label = value;
    }

    /**
     * Gets the value of the channels property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the channels property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getChannels().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getChannels() {
        if (channels == null) {
            channels = new ArrayList<String>();
        }
        return this.channels;
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
     * {@link String }
     * 
     * 
     */
    public List<String> getCodecs() {
        if (codecs == null) {
            codecs = new ArrayList<String>();
        }
        return this.codecs;
    }

    /**
     * 获取command属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCommand() {
        return command;
    }

    /**
     * 设置command属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCommand(String value) {
        this.command = value;
    }

    /**
     * 获取command2属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCommand2() {
        return command2;
    }

    /**
     * 设置command2属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCommand2(String value) {
        this.command2 = value;
    }

    /**
     * 获取idPreset属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getIdPreset() {
        return idPreset;
    }

    /**
     * 设置idPreset属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setIdPreset(Integer value) {
        this.idPreset = value;
    }

    /**
     * 获取isDPS属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getIsDPS() {
        return isDPS;
    }

    /**
     * 设置isDPS属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setIsDPS(Integer value) {
        this.isDPS = value;
    }

    /**
     * 获取isProxy属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIsProxy() {
        return isProxy;
    }

    /**
     * 设置isProxy属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIsProxy(Boolean value) {
        this.isProxy = value;
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
     * 获取presetGroupName属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPresetGroupName() {
        return presetGroupName;
    }

    /**
     * 设置presetGroupName属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPresetGroupName(String value) {
        this.presetGroupName = value;
    }

    /**
     * 获取profile属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getProfile() {
        return profile;
    }

    /**
     * 设置profile属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setProfile(Integer value) {
        this.profile = value;
    }

    /**
     * Gets the value of the streams property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the streams property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getStreams().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link PresetStream }
     * 
     * 
     */
    public List<PresetStream> getStreams() {
        if (streams == null) {
            streams = new ArrayList<PresetStream>();
        }
        return this.streams;
    }

    /**
     * 获取videoBitrate属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getVideoBitrate() {
        return videoBitrate;
    }

    /**
     * 设置videoBitrate属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setVideoBitrate(Integer value) {
        this.videoBitrate = value;
    }

    /**
     * 获取videoHeight属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getVideoHeight() {
        return videoHeight;
    }

    /**
     * 设置videoHeight属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setVideoHeight(Integer value) {
        this.videoHeight = value;
    }

    /**
     * 获取videoWidth属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getVideoWidth() {
        return videoWidth;
    }

    /**
     * 设置videoWidth属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setVideoWidth(Integer value) {
        this.videoWidth = value;
    }

}
