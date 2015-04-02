
package com.mainconcept.rewsservice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Snapshot complex type�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
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
     * ��ȡinterval���Ե�ֵ��
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
     * ����interval���Ե�ֵ��
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
     * ��ȡnumJobsAborted���Ե�ֵ��
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
     * ����numJobsAborted���Ե�ֵ��
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
     * ��ȡnumJobsFinished���Ե�ֵ��
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
     * ����numJobsFinished���Ե�ֵ��
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
     * ��ȡnumJobsInError���Ե�ֵ��
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
     * ����numJobsInError���Ե�ֵ��
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
     * ��ȡnumJobsInProgress���Ե�ֵ��
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
     * ����numJobsInProgress���Ե�ֵ��
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
     * ��ȡnumJobsNotStarted���Ե�ֵ��
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
     * ����numJobsNotStarted���Ե�ֵ��
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
     * ��ȡnumJobsPending���Ե�ֵ��
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
     * ����numJobsPending���Ե�ֵ��
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
     * ��ȡnumJobsSubmitted���Ե�ֵ��
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
     * ����numJobsSubmitted���Ե�ֵ��
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
     * ��ȡnumTasksPending���Ե�ֵ��
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
     * ����numTasksPending���Ե�ֵ��
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
     * ��ȡserverGroup���Ե�ֵ��
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
     * ����serverGroup���Ե�ֵ��
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
     * ��ȡusername���Ե�ֵ��
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
     * ����username���Ե�ֵ��
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
