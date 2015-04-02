
package com.mainconcept.rewsservice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>BypassParameter complex type�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
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
     * ��ȡaBitrate���Ե�ֵ��
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
     * ����aBitrate���Ե�ֵ��
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
     * ��ȡaDialnormDRC���Ե�ֵ��
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
     * ����aDialnormDRC���Ե�ֵ��
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
     * ��ȡaFollowInput���Ե�ֵ��
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
     * ����aFollowInput���Ե�ֵ��
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
     * ��ȡfuzz���Ե�ֵ��
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
     * ����fuzz���Ե�ֵ��
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
     * ��ȡvFrameAspect���Ե�ֵ��
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
     * ����vFrameAspect���Ե�ֵ��
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
     * ��ȡvFramerate���Ե�ֵ��
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
     * ����vFramerate���Ե�ֵ��
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
     * ��ȡvResolution���Ե�ֵ��
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
     * ����vResolution���Ե�ֵ��
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
