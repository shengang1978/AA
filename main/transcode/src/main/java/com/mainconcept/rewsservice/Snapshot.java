
package com.mainconcept.rewsservice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Snapshot complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="Snapshot">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="interval" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="numJobsAborted" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="numJobsFinished" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="numJobsInError" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="numJobsInProgress" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="numJobsNotStarted" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="numJobsPending" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="numJobsSubmitted" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="numTasksPending" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="serverGroup" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="username" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Snapshot", namespace = "http://api.rews.mainconcept.com/xsd", propOrder = {
    "interval",
    "numJobsAborted",
    "numJobsFinished",
    "numJobsInError",
    "numJobsInProgress",
    "numJobsNotStarted",
    "numJobsPending",
    "numJobsSubmitted",
    "numTasksPending",
    "serverGroup",
    "username"
})
public class Snapshot {

    @XmlElement(nillable = true)
    protected String interval;
    @XmlElement(nillable = true)
    protected Integer numJobsAborted;
    @XmlElement(nillable = true)
    protected Integer numJobsFinished;
    @XmlElement(nillable = true)
    protected Integer numJobsInError;
    @XmlElement(nillable = true)
    protected Integer numJobsInProgress;
    @XmlElement(nillable = true)
    protected Integer numJobsNotStarted;
    @XmlElement(nillable = true)
    protected Integer numJobsPending;
    @XmlElement(nillable = true)
    protected Integer numJobsSubmitted;
    @XmlElement(nillable = true)
    protected Integer numTasksPending;
    @XmlElement(nillable = true)
    protected String serverGroup;
    @XmlElement(nillable = true)
    protected String username;

    /**
     * 获取interval属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInterval() {
        return interval;
    }

    /**
     * 设置interval属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInterval(String value) {
        this.interval = value;
    }

    /**
     * 获取numJobsAborted属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getNumJobsAborted() {
        return numJobsAborted;
    }

    /**
     * 设置numJobsAborted属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setNumJobsAborted(Integer value) {
        this.numJobsAborted = value;
    }

    /**
     * 获取numJobsFinished属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getNumJobsFinished() {
        return numJobsFinished;
    }

    /**
     * 设置numJobsFinished属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setNumJobsFinished(Integer value) {
        this.numJobsFinished = value;
    }

    /**
     * 获取numJobsInError属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getNumJobsInError() {
        return numJobsInError;
    }

    /**
     * 设置numJobsInError属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setNumJobsInError(Integer value) {
        this.numJobsInError = value;
    }

    /**
     * 获取numJobsInProgress属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getNumJobsInProgress() {
        return numJobsInProgress;
    }

    /**
     * 设置numJobsInProgress属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setNumJobsInProgress(Integer value) {
        this.numJobsInProgress = value;
    }

    /**
     * 获取numJobsNotStarted属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getNumJobsNotStarted() {
        return numJobsNotStarted;
    }

    /**
     * 设置numJobsNotStarted属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setNumJobsNotStarted(Integer value) {
        this.numJobsNotStarted = value;
    }

    /**
     * 获取numJobsPending属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getNumJobsPending() {
        return numJobsPending;
    }

    /**
     * 设置numJobsPending属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setNumJobsPending(Integer value) {
        this.numJobsPending = value;
    }

    /**
     * 获取numJobsSubmitted属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getNumJobsSubmitted() {
        return numJobsSubmitted;
    }

    /**
     * 设置numJobsSubmitted属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setNumJobsSubmitted(Integer value) {
        this.numJobsSubmitted = value;
    }

    /**
     * 获取numTasksPending属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getNumTasksPending() {
        return numTasksPending;
    }

    /**
     * 设置numTasksPending属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setNumTasksPending(Integer value) {
        this.numTasksPending = value;
    }

    /**
     * 获取serverGroup属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getServerGroup() {
        return serverGroup;
    }

    /**
     * 设置serverGroup属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setServerGroup(String value) {
        this.serverGroup = value;
    }

    /**
     * 获取username属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUsername() {
        return username;
    }

    /**
     * 设置username属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUsername(String value) {
        this.username = value;
    }

}
