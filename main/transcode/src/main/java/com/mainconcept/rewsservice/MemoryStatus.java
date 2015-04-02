
package com.mainconcept.rewsservice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>MemoryStatus complex type�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
 * 
 * <pre>
 * &lt;complexType name="MemoryStatus">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="freeMemory" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="freeMemoryS" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="heapCommited" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="heapCommitedS" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="heapInit" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="heapInitS" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="heapMax" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="heapMaxS" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="heapUsed" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="heapUsedS" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="nonheapCommited" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="nonheapCommitedS" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="nonheapInit" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="nonheapInitS" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="nonheapMax" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="nonheapMaxS" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="nonheapUsed" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="nonheapUsedS" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "MemoryStatus", namespace = "http://monitor.api.rews.mainconcept.com/xsd", propOrder = {
    "freeMemory",
    "freeMemoryS",
    "heapCommited",
    "heapCommitedS",
    "heapInit",
    "heapInitS",
    "heapMax",
    "heapMaxS",
    "heapUsed",
    "heapUsedS",
    "nonheapCommited",
    "nonheapCommitedS",
    "nonheapInit",
    "nonheapInitS",
    "nonheapMax",
    "nonheapMaxS",
    "nonheapUsed",
    "nonheapUsedS"
})
public class MemoryStatus {

    protected Long freeMemory;
    @XmlElement(nillable = true)
    protected String freeMemoryS;
    protected Long heapCommited;
    @XmlElement(nillable = true)
    protected String heapCommitedS;
    protected Long heapInit;
    @XmlElement(nillable = true)
    protected String heapInitS;
    protected Long heapMax;
    @XmlElement(nillable = true)
    protected String heapMaxS;
    protected Long heapUsed;
    @XmlElement(nillable = true)
    protected String heapUsedS;
    protected Long nonheapCommited;
    @XmlElement(nillable = true)
    protected String nonheapCommitedS;
    protected Long nonheapInit;
    @XmlElement(nillable = true)
    protected String nonheapInitS;
    protected Long nonheapMax;
    @XmlElement(nillable = true)
    protected String nonheapMaxS;
    protected Long nonheapUsed;
    @XmlElement(nillable = true)
    protected String nonheapUsedS;

    /**
     * ��ȡfreeMemory���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getFreeMemory() {
        return freeMemory;
    }

    /**
     * ����freeMemory���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setFreeMemory(Long value) {
        this.freeMemory = value;
    }

    /**
     * ��ȡfreeMemoryS���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFreeMemoryS() {
        return freeMemoryS;
    }

    /**
     * ����freeMemoryS���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFreeMemoryS(String value) {
        this.freeMemoryS = value;
    }

    /**
     * ��ȡheapCommited���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getHeapCommited() {
        return heapCommited;
    }

    /**
     * ����heapCommited���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setHeapCommited(Long value) {
        this.heapCommited = value;
    }

    /**
     * ��ȡheapCommitedS���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHeapCommitedS() {
        return heapCommitedS;
    }

    /**
     * ����heapCommitedS���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHeapCommitedS(String value) {
        this.heapCommitedS = value;
    }

    /**
     * ��ȡheapInit���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getHeapInit() {
        return heapInit;
    }

    /**
     * ����heapInit���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setHeapInit(Long value) {
        this.heapInit = value;
    }

    /**
     * ��ȡheapInitS���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHeapInitS() {
        return heapInitS;
    }

    /**
     * ����heapInitS���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHeapInitS(String value) {
        this.heapInitS = value;
    }

    /**
     * ��ȡheapMax���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getHeapMax() {
        return heapMax;
    }

    /**
     * ����heapMax���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setHeapMax(Long value) {
        this.heapMax = value;
    }

    /**
     * ��ȡheapMaxS���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHeapMaxS() {
        return heapMaxS;
    }

    /**
     * ����heapMaxS���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHeapMaxS(String value) {
        this.heapMaxS = value;
    }

    /**
     * ��ȡheapUsed���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getHeapUsed() {
        return heapUsed;
    }

    /**
     * ����heapUsed���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setHeapUsed(Long value) {
        this.heapUsed = value;
    }

    /**
     * ��ȡheapUsedS���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHeapUsedS() {
        return heapUsedS;
    }

    /**
     * ����heapUsedS���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHeapUsedS(String value) {
        this.heapUsedS = value;
    }

    /**
     * ��ȡnonheapCommited���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getNonheapCommited() {
        return nonheapCommited;
    }

    /**
     * ����nonheapCommited���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setNonheapCommited(Long value) {
        this.nonheapCommited = value;
    }

    /**
     * ��ȡnonheapCommitedS���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNonheapCommitedS() {
        return nonheapCommitedS;
    }

    /**
     * ����nonheapCommitedS���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNonheapCommitedS(String value) {
        this.nonheapCommitedS = value;
    }

    /**
     * ��ȡnonheapInit���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getNonheapInit() {
        return nonheapInit;
    }

    /**
     * ����nonheapInit���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setNonheapInit(Long value) {
        this.nonheapInit = value;
    }

    /**
     * ��ȡnonheapInitS���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNonheapInitS() {
        return nonheapInitS;
    }

    /**
     * ����nonheapInitS���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNonheapInitS(String value) {
        this.nonheapInitS = value;
    }

    /**
     * ��ȡnonheapMax���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getNonheapMax() {
        return nonheapMax;
    }

    /**
     * ����nonheapMax���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setNonheapMax(Long value) {
        this.nonheapMax = value;
    }

    /**
     * ��ȡnonheapMaxS���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNonheapMaxS() {
        return nonheapMaxS;
    }

    /**
     * ����nonheapMaxS���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNonheapMaxS(String value) {
        this.nonheapMaxS = value;
    }

    /**
     * ��ȡnonheapUsed���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getNonheapUsed() {
        return nonheapUsed;
    }

    /**
     * ����nonheapUsed���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setNonheapUsed(Long value) {
        this.nonheapUsed = value;
    }

    /**
     * ��ȡnonheapUsedS���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNonheapUsedS() {
        return nonheapUsedS;
    }

    /**
     * ����nonheapUsedS���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNonheapUsedS(String value) {
        this.nonheapUsedS = value;
    }

}
