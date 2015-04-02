
package com.mainconcept.rewsservice;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>AudioStream complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="AudioStream">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="bitrate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="bitsPerSample" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="channelInfo" type="{http://api.rews.mainconcept.com/xsd}ChannelElement" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="channels" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="codec" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="duration" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="samplerate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AudioStream", namespace = "http://api.rews.mainconcept.com/xsd", propOrder = {
    "bitrate",
    "bitsPerSample",
    "channelInfo",
    "channels",
    "codec",
    "duration",
    "samplerate"
})
public class AudioStream {

    @XmlElement(nillable = true)
    protected String bitrate;
    @XmlElement(nillable = true)
    protected String bitsPerSample;
    @XmlElement(nillable = true)
    protected List<ChannelElement> channelInfo;
    @XmlElement(nillable = true)
    protected String channels;
    @XmlElement(nillable = true)
    protected String codec;
    @XmlElement(nillable = true)
    protected String duration;
    @XmlElement(nillable = true)
    protected String samplerate;

    /**
     * 获取bitrate属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBitrate() {
        return bitrate;
    }

    /**
     * 设置bitrate属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBitrate(String value) {
        this.bitrate = value;
    }

    /**
     * 获取bitsPerSample属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBitsPerSample() {
        return bitsPerSample;
    }

    /**
     * 设置bitsPerSample属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBitsPerSample(String value) {
        this.bitsPerSample = value;
    }

    /**
     * Gets the value of the channelInfo property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the channelInfo property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getChannelInfo().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ChannelElement }
     * 
     * 
     */
    public List<ChannelElement> getChannelInfo() {
        if (channelInfo == null) {
            channelInfo = new ArrayList<ChannelElement>();
        }
        return this.channelInfo;
    }

    /**
     * 获取channels属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getChannels() {
        return channels;
    }

    /**
     * 设置channels属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setChannels(String value) {
        this.channels = value;
    }

    /**
     * 获取codec属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodec() {
        return codec;
    }

    /**
     * 设置codec属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodec(String value) {
        this.codec = value;
    }

    /**
     * 获取duration属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDuration() {
        return duration;
    }

    /**
     * 设置duration属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDuration(String value) {
        this.duration = value;
    }

    /**
     * 获取samplerate属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSamplerate() {
        return samplerate;
    }

    /**
     * 设置samplerate属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSamplerate(String value) {
        this.samplerate = value;
    }

}
