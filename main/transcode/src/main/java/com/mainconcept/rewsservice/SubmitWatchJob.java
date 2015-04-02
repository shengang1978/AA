
package com.mainconcept.rewsservice;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>anonymous complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="watchJob" type="{http://api.rews.mainconcept.com/xsd}Job" minOccurs="0"/>
 *         &lt;element name="presets" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="priority" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="makeProxy" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="isAutoStart" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="ruleName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="allowConcurrent" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "watchJob",
    "presets",
    "priority",
    "makeProxy",
    "isAutoStart",
    "ruleName",
    "allowConcurrent"
})
@XmlRootElement(name = "submitWatchJob")
public class SubmitWatchJob {

    @XmlElement(nillable = true)
    protected Job watchJob;
    @XmlElement(nillable = true)
    protected List<String> presets;
    protected Integer priority;
    protected Boolean makeProxy;
    protected Boolean isAutoStart;
    @XmlElement(nillable = true)
    protected String ruleName;
    protected Boolean allowConcurrent;

    /**
     * 获取watchJob属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Job }
     *     
     */
    public Job getWatchJob() {
        return watchJob;
    }

    /**
     * 设置watchJob属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Job }
     *     
     */
    public void setWatchJob(Job value) {
        this.watchJob = value;
    }

    /**
     * Gets the value of the presets property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the presets property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPresets().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getPresets() {
        if (presets == null) {
            presets = new ArrayList<String>();
        }
        return this.presets;
    }

    /**
     * 获取priority属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getPriority() {
        return priority;
    }

    /**
     * 设置priority属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setPriority(Integer value) {
        this.priority = value;
    }

    /**
     * 获取makeProxy属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isMakeProxy() {
        return makeProxy;
    }

    /**
     * 设置makeProxy属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setMakeProxy(Boolean value) {
        this.makeProxy = value;
    }

    /**
     * 获取isAutoStart属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIsAutoStart() {
        return isAutoStart;
    }

    /**
     * 设置isAutoStart属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIsAutoStart(Boolean value) {
        this.isAutoStart = value;
    }

    /**
     * 获取ruleName属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRuleName() {
        return ruleName;
    }

    /**
     * 设置ruleName属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRuleName(String value) {
        this.ruleName = value;
    }

    /**
     * 获取allowConcurrent属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isAllowConcurrent() {
        return allowConcurrent;
    }

    /**
     * 设置allowConcurrent属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setAllowConcurrent(Boolean value) {
        this.allowConcurrent = value;
    }

}
