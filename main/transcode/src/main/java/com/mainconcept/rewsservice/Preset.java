
package com.mainconcept.rewsservice;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Preset complex type�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
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
     * ��ȡapply2Label���Ե�ֵ��
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
     * ����apply2Label���Ե�ֵ��
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
     * ��ȡcommand���Ե�ֵ��
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
     * ����command���Ե�ֵ��
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
     * ��ȡcommand2���Ե�ֵ��
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
     * ����command2���Ե�ֵ��
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
     * ��ȡidPreset���Ե�ֵ��
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
     * ����idPreset���Ե�ֵ��
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
     * ��ȡisDPS���Ե�ֵ��
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
     * ����isDPS���Ե�ֵ��
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
     * ��ȡisProxy���Ե�ֵ��
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
     * ����isProxy���Ե�ֵ��
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
     * ��ȡpresetGroupName���Ե�ֵ��
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
     * ����presetGroupName���Ե�ֵ��
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
     * ��ȡprofile���Ե�ֵ��
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
     * ����profile���Ե�ֵ��
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
     * ��ȡvideoBitrate���Ե�ֵ��
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
     * ����videoBitrate���Ե�ֵ��
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
     * ��ȡvideoHeight���Ե�ֵ��
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
     * ����videoHeight���Ե�ֵ��
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
     * ��ȡvideoWidth���Ե�ֵ��
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
     * ����videoWidth���Ե�ֵ��
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
