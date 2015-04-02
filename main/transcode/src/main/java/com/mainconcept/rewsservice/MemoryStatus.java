
package com.mainconcept.rewsservice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>MemoryStatus complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
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
     * 获取freeMemory属性的值。
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
     * 设置freeMemory属性的值。
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
     * 获取freeMemoryS属性的值。
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
     * 设置freeMemoryS属性的值。
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
     * 获取heapCommited属性的值。
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
     * 设置heapCommited属性的值。
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
     * 获取heapCommitedS属性的值。
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
     * 设置heapCommitedS属性的值。
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
     * 获取heapInit属性的值。
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
     * 设置heapInit属性的值。
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
     * 获取heapInitS属性的值。
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
     * 设置heapInitS属性的值。
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
     * 获取heapMax属性的值。
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
     * 设置heapMax属性的值。
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
     * 获取heapMaxS属性的值。
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
     * 设置heapMaxS属性的值。
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
     * 获取heapUsed属性的值。
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
     * 设置heapUsed属性的值。
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
     * 获取heapUsedS属性的值。
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
     * 设置heapUsedS属性的值。
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
     * 获取nonheapCommited属性的值。
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
     * 设置nonheapCommited属性的值。
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
     * 获取nonheapCommitedS属性的值。
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
     * 设置nonheapCommitedS属性的值。
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
     * 获取nonheapInit属性的值。
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
     * 设置nonheapInit属性的值。
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
     * 获取nonheapInitS属性的值。
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
     * 设置nonheapInitS属性的值。
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
     * 获取nonheapMax属性的值。
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
     * 设置nonheapMax属性的值。
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
     * 获取nonheapMaxS属性的值。
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
     * 设置nonheapMaxS属性的值。
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
     * 获取nonheapUsed属性的值。
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
     * 设置nonheapUsed属性的值。
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
     * 获取nonheapUsedS属性的值。
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
     * 设置nonheapUsedS属性的值。
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
