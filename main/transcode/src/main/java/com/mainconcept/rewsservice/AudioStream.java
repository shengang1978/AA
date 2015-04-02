
package com.mainconcept.rewsservice;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>AudioStream complex type�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
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
     * ��ȡbitrate���Ե�ֵ��
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
     * ����bitrate���Ե�ֵ��
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
     * ��ȡbitsPerSample���Ե�ֵ��
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
     * ����bitsPerSample���Ե�ֵ��
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
     * ��ȡchannels���Ե�ֵ��
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
     * ����channels���Ե�ֵ��
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
     * ��ȡcodec���Ե�ֵ��
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
     * ����codec���Ե�ֵ��
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
     * ��ȡduration���Ե�ֵ��
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
     * ����duration���Ե�ֵ��
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
     * ��ȡsamplerate���Ե�ֵ��
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
     * ����samplerate���Ե�ֵ��
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
