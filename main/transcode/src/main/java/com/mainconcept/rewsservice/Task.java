
package com.mainconcept.rewsservice;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Task complex type�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
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
     * ��ȡacmapping���Ե�ֵ��
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
     * ����acmapping���Ե�ֵ��
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
     * ��ȡbeTransferNode���Ե�ֵ��
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
     * ����beTransferNode���Ե�ֵ��
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
     * ��ȡid���Ե�ֵ��
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
     * ����id���Ե�ֵ��
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
     * ��ȡisBypass���Ե�ֵ��
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
     * ����isBypass���Ե�ֵ��
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
     * ��ȡisDash���Ե�ֵ��
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
     * ����isDash���Ե�ֵ��
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
     * ��ȡisProxy���Ե�ֵ��
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
     * ����isProxy���Ե�ֵ��
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
     * ��ȡjobId���Ե�ֵ��
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
     * ����jobId���Ե�ֵ��
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
     * ��ȡlogFileName���Ե�ֵ��
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
     * ����logFileName���Ե�ֵ��
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
     * ��ȡname���Ե�ֵ��
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
     * ����name���Ե�ֵ��
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
     * ��ȡoutAnalysisXmlName���Ե�ֵ��
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
     * ����outAnalysisXmlName���Ե�ֵ��
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
     * ��ȡoutFileLoc���Ե�ֵ��
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
     * ����outFileLoc���Ե�ֵ��
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
     * ��ȡoutFileLocID���Ե�ֵ��
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
     * ����outFileLocID���Ե�ֵ��
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
     * ��ȡoutFileName���Ե�ֵ��
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
     * ����outFileName���Ե�ֵ��
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
     * ��ȡoutFilePath���Ե�ֵ��
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
     * ����outFilePath���Ե�ֵ��
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
     * ��ȡoutKDFName���Ե�ֵ��
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
     * ����outKDFName���Ե�ֵ��
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
     * ��ȡownerAG���Ե�ֵ��
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
     * ����ownerAG���Ե�ֵ��
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
     * ��ȡpreset���Ե�ֵ��
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
     * ����preset���Ե�ֵ��
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
     * ��ȡpresetGroup���Ե�ֵ��
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
     * ����presetGroup���Ե�ֵ��
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
     * ��ȡpresetGroupID���Ե�ֵ��
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
     * ����presetGroupID���Ե�ֵ��
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
     * ��ȡpriority���Ե�ֵ��
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
     * ����priority���Ե�ֵ��
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
     * ��ȡtsFinish���Ե�ֵ��
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
     * ����tsFinish���Ե�ֵ��
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
     * ��ȡtsStart���Ե�ֵ��
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
     * ����tsStart���Ե�ֵ��
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
     * ��ȡtsSubmit���Ե�ֵ��
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
     * ����tsSubmit���Ե�ֵ��
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
     * ��ȡtype���Ե�ֵ��
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
     * ����type���Ե�ֵ��
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
