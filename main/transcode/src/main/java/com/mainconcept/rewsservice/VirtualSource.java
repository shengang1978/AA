
package com.mainconcept.rewsservice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>VirtualSource complex type�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
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
     * ��ȡsrcChannelNum���Ե�ֵ��
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
     * ����srcChannelNum���Ե�ֵ��
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
     * ��ȡsrcGain���Ե�ֵ��
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
     * ����srcGain���Ե�ֵ��
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
     * ��ȡsrcReference���Ե�ֵ��
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
     * ����srcReference���Ե�ֵ��
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
     * ��ȡsrcStreamNum���Ե�ֵ��
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
     * ����srcStreamNum���Ե�ֵ��
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
