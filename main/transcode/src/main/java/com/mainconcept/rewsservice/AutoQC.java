
package com.mainconcept.rewsservice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>AutoQC complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="AutoQC">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="avgPsnrThr" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="avgSegSize" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="minPsnrThr" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="minSegSize" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="nrWorstSegments" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="worstSegmentSize" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AutoQC", namespace = "http://api.rews.mainconcept.com/xsd", propOrder = {
    "avgPsnrThr",
    "avgSegSize",
    "minPsnrThr",
    "minSegSize",
    "nrWorstSegments",
    "worstSegmentSize"
})
public class AutoQC {

    @XmlElement(nillable = true)
    protected Double avgPsnrThr;
    @XmlElement(nillable = true)
    protected Integer avgSegSize;
    @XmlElement(nillable = true)
    protected Double minPsnrThr;
    @XmlElement(nillable = true)
    protected Integer minSegSize;
    @XmlElement(nillable = true)
    protected Integer nrWorstSegments;
    @XmlElement(nillable = true)
    protected Integer worstSegmentSize;

    /**
     * 获取avgPsnrThr属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getAvgPsnrThr() {
        return avgPsnrThr;
    }

    /**
     * 设置avgPsnrThr属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setAvgPsnrThr(Double value) {
        this.avgPsnrThr = value;
    }

    /**
     * 获取avgSegSize属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getAvgSegSize() {
        return avgSegSize;
    }

    /**
     * 设置avgSegSize属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setAvgSegSize(Integer value) {
        this.avgSegSize = value;
    }

    /**
     * 获取minPsnrThr属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getMinPsnrThr() {
        return minPsnrThr;
    }

    /**
     * 设置minPsnrThr属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setMinPsnrThr(Double value) {
        this.minPsnrThr = value;
    }

    /**
     * 获取minSegSize属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getMinSegSize() {
        return minSegSize;
    }

    /**
     * 设置minSegSize属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setMinSegSize(Integer value) {
        this.minSegSize = value;
    }

    /**
     * 获取nrWorstSegments属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getNrWorstSegments() {
        return nrWorstSegments;
    }

    /**
     * 设置nrWorstSegments属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setNrWorstSegments(Integer value) {
        this.nrWorstSegments = value;
    }

    /**
     * 获取worstSegmentSize属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getWorstSegmentSize() {
        return worstSegmentSize;
    }

    /**
     * 设置worstSegmentSize属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setWorstSegmentSize(Integer value) {
        this.worstSegmentSize = value;
    }

}
