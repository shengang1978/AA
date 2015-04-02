
package com.mainconcept.rewsservice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>VirtualSource complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="VirtualSource">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="srcChannelNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="srcGain" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="srcReference" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="srcStreamNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "VirtualSource", namespace = "http://api.rews.mainconcept.com/xsd", propOrder = {
    "srcChannelNum",
    "srcGain",
    "srcReference",
    "srcStreamNum"
})
public class VirtualSource {

    @XmlElement(nillable = true)
    protected String srcChannelNum;
    @XmlElement(nillable = true)
    protected String srcGain;
    @XmlElement(nillable = true)
    protected String srcReference;
    @XmlElement(nillable = true)
    protected String srcStreamNum;

    /**
     * 获取srcChannelNum属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSrcChannelNum() {
        return srcChannelNum;
    }

    /**
     * 设置srcChannelNum属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSrcChannelNum(String value) {
        this.srcChannelNum = value;
    }

    /**
     * 获取srcGain属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSrcGain() {
        return srcGain;
    }

    /**
     * 设置srcGain属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSrcGain(String value) {
        this.srcGain = value;
    }

    /**
     * 获取srcReference属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSrcReference() {
        return srcReference;
    }

    /**
     * 设置srcReference属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSrcReference(String value) {
        this.srcReference = value;
    }

    /**
     * 获取srcStreamNum属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSrcStreamNum() {
        return srcStreamNum;
    }

    /**
     * 设置srcStreamNum属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSrcStreamNum(String value) {
        this.srcStreamNum = value;
    }

}
