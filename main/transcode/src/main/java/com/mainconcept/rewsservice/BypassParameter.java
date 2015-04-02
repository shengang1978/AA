
package com.mainconcept.rewsservice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>BypassParameter complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="BypassParameter">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="aBitrate" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="aDialnormDRC" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="aFollowInput" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="fuzz" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="vFrameAspect" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="vFramerate" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="vResolution" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BypassParameter", namespace = "http://api.rews.mainconcept.com/xsd", propOrder = {
    "aBitrate",
    "aDialnormDRC",
    "aFollowInput",
    "fuzz",
    "vFrameAspect",
    "vFramerate",
    "vResolution"
})
public class BypassParameter {

    protected Boolean aBitrate;
    protected Boolean aDialnormDRC;
    protected Boolean aFollowInput;
    protected Boolean fuzz;
    protected Boolean vFrameAspect;
    protected Boolean vFramerate;
    protected Boolean vResolution;

    /**
     * 获取aBitrate属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isABitrate() {
        return aBitrate;
    }

    /**
     * 设置aBitrate属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setABitrate(Boolean value) {
        this.aBitrate = value;
    }

    /**
     * 获取aDialnormDRC属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isADialnormDRC() {
        return aDialnormDRC;
    }

    /**
     * 设置aDialnormDRC属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setADialnormDRC(Boolean value) {
        this.aDialnormDRC = value;
    }

    /**
     * 获取aFollowInput属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isAFollowInput() {
        return aFollowInput;
    }

    /**
     * 设置aFollowInput属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setAFollowInput(Boolean value) {
        this.aFollowInput = value;
    }

    /**
     * 获取fuzz属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isFuzz() {
        return fuzz;
    }

    /**
     * 设置fuzz属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setFuzz(Boolean value) {
        this.fuzz = value;
    }

    /**
     * 获取vFrameAspect属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isVFrameAspect() {
        return vFrameAspect;
    }

    /**
     * 设置vFrameAspect属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setVFrameAspect(Boolean value) {
        this.vFrameAspect = value;
    }

    /**
     * 获取vFramerate属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isVFramerate() {
        return vFramerate;
    }

    /**
     * 设置vFramerate属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setVFramerate(Boolean value) {
        this.vFramerate = value;
    }

    /**
     * 获取vResolution属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isVResolution() {
        return vResolution;
    }

    /**
     * 设置vResolution属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setVResolution(Boolean value) {
        this.vResolution = value;
    }

}
