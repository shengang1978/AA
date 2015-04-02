
package com.mainconcept.rewsservice;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>QSearchOpts complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="QSearchOpts">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="isWatchfolder" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="jobID" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="jobName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="jobType" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="numRows" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="ownerJobID" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="serverGroup" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="startRow" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="stateExcluded" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="stateIncluded" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="tsSubmitEnd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="tsSubmitStart" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="username" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="watchLocID" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "QSearchOpts", namespace = "http://api.rews.mainconcept.com/xsd", propOrder = {
    "isWatchfolder",
    "jobID",
    "jobName",
    "jobType",
    "numRows",
    "ownerJobID",
    "serverGroup",
    "startRow",
    "stateExcluded",
    "stateIncluded",
    "tsSubmitEnd",
    "tsSubmitStart",
    "username",
    "watchLocID"
})
@XmlSeeAlso({
    WatchSearchOpts.class
})
public class QSearchOpts {

    @XmlElement(nillable = true)
    protected Boolean isWatchfolder;
    @XmlElement(nillable = true)
    protected List<String> jobID;
    @XmlElement(nillable = true)
    protected String jobName;
    @XmlElement(nillable = true)
    protected List<String> jobType;
    @XmlElement(nillable = true)
    protected Integer numRows;
    @XmlElement(nillable = true)
    protected Integer ownerJobID;
    @XmlElement(nillable = true)
    protected String serverGroup;
    @XmlElement(nillable = true)
    protected Integer startRow;
    @XmlElement(nillable = true)
    protected List<String> stateExcluded;
    @XmlElement(nillable = true)
    protected List<String> stateIncluded;
    @XmlElement(nillable = true)
    protected String tsSubmitEnd;
    @XmlElement(nillable = true)
    protected String tsSubmitStart;
    @XmlElement(nillable = true)
    protected String username;
    @XmlElement(nillable = true)
    protected Integer watchLocID;

    /**
     * 获取isWatchfolder属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIsWatchfolder() {
        return isWatchfolder;
    }

    /**
     * 设置isWatchfolder属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIsWatchfolder(Boolean value) {
        this.isWatchfolder = value;
    }

    /**
     * Gets the value of the jobID property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the jobID property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getJobID().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getJobID() {
        if (jobID == null) {
            jobID = new ArrayList<String>();
        }
        return this.jobID;
    }

    /**
     * 获取jobName属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getJobName() {
        return jobName;
    }

    /**
     * 设置jobName属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setJobName(String value) {
        this.jobName = value;
    }

    /**
     * Gets the value of the jobType property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the jobType property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getJobType().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getJobType() {
        if (jobType == null) {
            jobType = new ArrayList<String>();
        }
        return this.jobType;
    }

    /**
     * 获取numRows属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getNumRows() {
        return numRows;
    }

    /**
     * 设置numRows属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setNumRows(Integer value) {
        this.numRows = value;
    }

    /**
     * 获取ownerJobID属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getOwnerJobID() {
        return ownerJobID;
    }

    /**
     * 设置ownerJobID属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setOwnerJobID(Integer value) {
        this.ownerJobID = value;
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
     * 获取startRow属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getStartRow() {
        return startRow;
    }

    /**
     * 设置startRow属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setStartRow(Integer value) {
        this.startRow = value;
    }

    /**
     * Gets the value of the stateExcluded property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the stateExcluded property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getStateExcluded().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getStateExcluded() {
        if (stateExcluded == null) {
            stateExcluded = new ArrayList<String>();
        }
        return this.stateExcluded;
    }

    /**
     * Gets the value of the stateIncluded property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the stateIncluded property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getStateIncluded().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getStateIncluded() {
        if (stateIncluded == null) {
            stateIncluded = new ArrayList<String>();
        }
        return this.stateIncluded;
    }

    /**
     * 获取tsSubmitEnd属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTsSubmitEnd() {
        return tsSubmitEnd;
    }

    /**
     * 设置tsSubmitEnd属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTsSubmitEnd(String value) {
        this.tsSubmitEnd = value;
    }

    /**
     * 获取tsSubmitStart属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTsSubmitStart() {
        return tsSubmitStart;
    }

    /**
     * 设置tsSubmitStart属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTsSubmitStart(String value) {
        this.tsSubmitStart = value;
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

    /**
     * 获取watchLocID属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getWatchLocID() {
        return watchLocID;
    }

    /**
     * 设置watchLocID属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setWatchLocID(Integer value) {
        this.watchLocID = value;
    }

}
