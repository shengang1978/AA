
package com.mainconcept.rewsservice;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Task complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="Task">
 *   &lt;complexContent>
 *     &lt;extension base="{http://api.rews.mainconcept.com/xsd}TaskState">
 *       &lt;sequence>
 *         &lt;element name="acmapping" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="beTransferNode" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="inss" type="{http://api.rews.mainconcept.com/xsd}InputStream" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="isBypass" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="isDash" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="isProxy" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="jobId" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="logFileName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="outAnalysisXmlName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="outFileLoc" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="outFileLocID" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="outFileName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="outFilePath" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="outKDFName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ownerAG" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="preset" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="presetGroup" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="presetGroupID" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="priority" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="tsFinish" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="tsStart" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="tsSubmit" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="type" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="username" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="uvInss" type="{http://api.rews.mainconcept.com/xsd}InputStream" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Task", namespace = "http://api.rews.mainconcept.com/xsd", propOrder = {
    "acmapping",
    "beTransferNode",
    "id",
    "inss",
    "isBypass",
    "isDash",
    "isProxy",
    "jobId",
    "logFileName",
    "name",
    "outAnalysisXmlName",
    "outFileLoc",
    "outFileLocID",
    "outFileName",
    "outFilePath",
    "outKDFName",
    "ownerAG",
    "preset",
    "presetGroup",
    "presetGroupID",
    "priority",
    "tsFinish",
    "tsStart",
    "tsSubmit",
    "type",
    "username",
    "uvInss"
})
public class Task
    extends TaskState
{

    @XmlElement(nillable = true)
    protected String acmapping;
    @XmlElement(nillable = true)
    protected Boolean beTransferNode;
    @XmlElement(nillable = true)
    protected Integer id;
    @XmlElement(nillable = true)
    protected List<InputStream> inss;
    @XmlElement(nillable = true)
    protected Boolean isBypass;
    @XmlElement(nillable = true)
    protected Boolean isDash;
    @XmlElement(nillable = true)
    protected Integer isProxy;
    @XmlElement(nillable = true)
    protected Integer jobId;
    @XmlElement(nillable = true)
    protected String logFileName;
    @XmlElement(nillable = true)
    protected String name;
    @XmlElement(nillable = true)
    protected String outAnalysisXmlName;
    @XmlElement(nillable = true)
    protected String outFileLoc;
    @XmlElement(nillable = true)
    protected Integer outFileLocID;
    @XmlElement(nillable = true)
    protected String outFileName;
    @XmlElement(nillable = true)
    protected String outFilePath;
    @XmlElement(nillable = true)
    protected String outKDFName;
    @XmlElement(nillable = true)
    protected String ownerAG;
    @XmlElement(nillable = true)
    protected String preset;
    @XmlElement(nillable = true)
    protected String presetGroup;
    @XmlElement(nillable = true)
    protected Integer presetGroupID;
    @XmlElement(nillable = true)
    protected Integer priority;
    @XmlElement(nillable = true)
    protected String tsFinish;
    @XmlElement(nillable = true)
    protected String tsStart;
    @XmlElement(nillable = true)
    protected String tsSubmit;
    @XmlElement(nillable = true)
    protected String type;
    @XmlElement(nillable = true)
    protected String username;
    @XmlElement(nillable = true)
    protected List<InputStream> uvInss;

    /**
     * 获取acmapping属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAcmapping() {
        return acmapping;
    }

    /**
     * 设置acmapping属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAcmapping(String value) {
        this.acmapping = value;
    }

    /**
     * 获取beTransferNode属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isBeTransferNode() {
        return beTransferNode;
    }

    /**
     * 设置beTransferNode属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setBeTransferNode(Boolean value) {
        this.beTransferNode = value;
    }

    /**
     * 获取id属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置id属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setId(Integer value) {
        this.id = value;
    }

    /**
     * Gets the value of the inss property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the inss property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getInss().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link InputStream }
     * 
     * 
     */
    public List<InputStream> getInss() {
        if (inss == null) {
            inss = new ArrayList<InputStream>();
        }
        return this.inss;
    }

    /**
     * 获取isBypass属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIsBypass() {
        return isBypass;
    }

    /**
     * 设置isBypass属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIsBypass(Boolean value) {
        this.isBypass = value;
    }

    /**
     * 获取isDash属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIsDash() {
        return isDash;
    }

    /**
     * 设置isDash属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIsDash(Boolean value) {
        this.isDash = value;
    }

    /**
     * 获取isProxy属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getIsProxy() {
        return isProxy;
    }

    /**
     * 设置isProxy属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setIsProxy(Integer value) {
        this.isProxy = value;
    }

    /**
     * 获取jobId属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getJobId() {
        return jobId;
    }

    /**
     * 设置jobId属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setJobId(Integer value) {
        this.jobId = value;
    }

    /**
     * 获取logFileName属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLogFileName() {
        return logFileName;
    }

    /**
     * 设置logFileName属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLogFileName(String value) {
        this.logFileName = value;
    }

    /**
     * 获取name属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getName() {
        return name;
    }

    /**
     * 设置name属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * 获取outAnalysisXmlName属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOutAnalysisXmlName() {
        return outAnalysisXmlName;
    }

    /**
     * 设置outAnalysisXmlName属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOutAnalysisXmlName(String value) {
        this.outAnalysisXmlName = value;
    }

    /**
     * 获取outFileLoc属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOutFileLoc() {
        return outFileLoc;
    }

    /**
     * 设置outFileLoc属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOutFileLoc(String value) {
        this.outFileLoc = value;
    }

    /**
     * 获取outFileLocID属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getOutFileLocID() {
        return outFileLocID;
    }

    /**
     * 设置outFileLocID属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setOutFileLocID(Integer value) {
        this.outFileLocID = value;
    }

    /**
     * 获取outFileName属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOutFileName() {
        return outFileName;
    }

    /**
     * 设置outFileName属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOutFileName(String value) {
        this.outFileName = value;
    }

    /**
     * 获取outFilePath属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOutFilePath() {
        return outFilePath;
    }

    /**
     * 设置outFilePath属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOutFilePath(String value) {
        this.outFilePath = value;
    }

    /**
     * 获取outKDFName属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOutKDFName() {
        return outKDFName;
    }

    /**
     * 设置outKDFName属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOutKDFName(String value) {
        this.outKDFName = value;
    }

    /**
     * 获取ownerAG属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOwnerAG() {
        return ownerAG;
    }

    /**
     * 设置ownerAG属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOwnerAG(String value) {
        this.ownerAG = value;
    }

    /**
     * 获取preset属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPreset() {
        return preset;
    }

    /**
     * 设置preset属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPreset(String value) {
        this.preset = value;
    }

    /**
     * 获取presetGroup属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPresetGroup() {
        return presetGroup;
    }

    /**
     * 设置presetGroup属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPresetGroup(String value) {
        this.presetGroup = value;
    }

    /**
     * 获取presetGroupID属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getPresetGroupID() {
        return presetGroupID;
    }

    /**
     * 设置presetGroupID属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setPresetGroupID(Integer value) {
        this.presetGroupID = value;
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
     * 获取tsFinish属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTsFinish() {
        return tsFinish;
    }

    /**
     * 设置tsFinish属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTsFinish(String value) {
        this.tsFinish = value;
    }

    /**
     * 获取tsStart属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTsStart() {
        return tsStart;
    }

    /**
     * 设置tsStart属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTsStart(String value) {
        this.tsStart = value;
    }

    /**
     * 获取tsSubmit属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTsSubmit() {
        return tsSubmit;
    }

    /**
     * 设置tsSubmit属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTsSubmit(String value) {
        this.tsSubmit = value;
    }

    /**
     * 获取type属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getType() {
        return type;
    }

    /**
     * 设置type属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setType(String value) {
        this.type = value;
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
     * Gets the value of the uvInss property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the uvInss property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getUvInss().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link InputStream }
     * 
     * 
     */
    public List<InputStream> getUvInss() {
        if (uvInss == null) {
            uvInss = new ArrayList<InputStream>();
        }
        return this.uvInss;
    }

}
