
package com.mainconcept.rewsservice;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Job complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="Job">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="IMDSource" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="SABETMaxGroupMPixelCount" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="WFRefreshTime" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="WFSourceAction" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="WFSourceActionLocID" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="WFSubmitQueueLen" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="WFextensions" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="acmXMLs" type="{http://api.rews.mainconcept.com/xsd}AudioChannelMappingXML" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="allowOutputOverwrite" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="audioFilterPresetGroup" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="audioFilterPresetName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="bypass" type="{http://api.rews.mainconcept.com/xsd}BypassParameter" minOccurs="0"/>
 *         &lt;element name="hasPostProc" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="isBypass" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="isMezzanineFormat" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="isSabet" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="lastError" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="m2ROutLocID" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="outFileLoc" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="outFileLocID" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="outputFileName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="outputFilePath" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ownerJobID" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="ownerUsername" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="pgID" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="pgPID" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="postProcCommand" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="postProcExCode" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="presetGroup" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="presetGroupProxy" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="priority" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="proxyLink" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ruleID" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="runOnSameTWS" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="state" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="stateExt" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="submitServerGroupName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="submitServerName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="tasks" type="{http://api.rews.mainconcept.com/xsd}Task" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="transferByTWS" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="tsFinish" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="tsStart" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="tsSubmit" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="type" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="videoFilterPresetGroup" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="videoFilterPresetName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="watchLoc" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
@XmlType(name = "Job", namespace = "http://api.rews.mainconcept.com/xsd", propOrder = {
    "imdSource",
    "sabetMaxGroupMPixelCount",
    "wfRefreshTime",
    "wfSourceAction",
    "wfSourceActionLocID",
    "wfSubmitQueueLen",
    "wFextensions",
    "acmXMLs",
    "allowOutputOverwrite",
    "audioFilterPresetGroup",
    "audioFilterPresetName",
    "bypass",
    "hasPostProc",
    "id",
    "isBypass",
    "isMezzanineFormat",
    "isSabet",
    "lastError",
    "m2ROutLocID",
    "name",
    "outFileLoc",
    "outFileLocID",
    "outputFileName",
    "outputFilePath",
    "ownerJobID",
    "ownerUsername",
    "pgID",
    "pgPID",
    "postProcCommand",
    "postProcExCode",
    "presetGroup",
    "presetGroupProxy",
    "priority",
    "proxyLink",
    "ruleID",
    "runOnSameTWS",
    "state",
    "stateExt",
    "submitServerGroupName",
    "submitServerName",
    "tasks",
    "transferByTWS",
    "tsFinish",
    "tsStart",
    "tsSubmit",
    "type",
    "videoFilterPresetGroup",
    "videoFilterPresetName",
    "watchLoc",
    "watchLocID"
})
public class Job {

    @XmlElement(name = "IMDSource", nillable = true)
    protected String imdSource;
    @XmlElement(name = "SABETMaxGroupMPixelCount", nillable = true)
    protected Integer sabetMaxGroupMPixelCount;
    @XmlElement(name = "WFRefreshTime", nillable = true)
    protected Integer wfRefreshTime;
    @XmlElement(name = "WFSourceAction", nillable = true)
    protected String wfSourceAction;
    @XmlElement(name = "WFSourceActionLocID", nillable = true)
    protected Integer wfSourceActionLocID;
    @XmlElement(name = "WFSubmitQueueLen", nillable = true)
    protected Integer wfSubmitQueueLen;
    @XmlElement(name = "WFextensions", nillable = true)
    protected List<String> wFextensions;
    @XmlElement(nillable = true)
    protected List<AudioChannelMappingXML> acmXMLs;
    @XmlElement(nillable = true)
    protected Boolean allowOutputOverwrite;
    @XmlElement(nillable = true)
    protected String audioFilterPresetGroup;
    @XmlElement(nillable = true)
    protected String audioFilterPresetName;
    @XmlElement(nillable = true)
    protected BypassParameter bypass;
    @XmlElement(nillable = true)
    protected Integer hasPostProc;
    @XmlElement(nillable = true)
    protected Integer id;
    @XmlElement(nillable = true)
    protected Boolean isBypass;
    @XmlElement(nillable = true)
    protected Boolean isMezzanineFormat;
    @XmlElement(nillable = true)
    protected Boolean isSabet;
    @XmlElement(nillable = true)
    protected String lastError;
    @XmlElement(nillable = true)
    protected Integer m2ROutLocID;
    @XmlElement(nillable = true)
    protected String name;
    @XmlElement(nillable = true)
    protected String outFileLoc;
    @XmlElement(nillable = true)
    protected Integer outFileLocID;
    @XmlElement(nillable = true)
    protected String outputFileName;
    @XmlElement(nillable = true)
    protected String outputFilePath;
    @XmlElement(nillable = true)
    protected Integer ownerJobID;
    @XmlElement(nillable = true)
    protected String ownerUsername;
    @XmlElement(nillable = true)
    protected Integer pgID;
    @XmlElement(nillable = true)
    protected Integer pgPID;
    @XmlElement(nillable = true)
    protected List<String> postProcCommand;
    @XmlElement(nillable = true)
    protected Integer postProcExCode;
    @XmlElement(nillable = true)
    protected String presetGroup;
    @XmlElement(nillable = true)
    protected String presetGroupProxy;
    @XmlElement(nillable = true)
    protected Integer priority;
    @XmlElement(nillable = true)
    protected String proxyLink;
    @XmlElement(nillable = true)
    protected Integer ruleID;
    @XmlElement(nillable = true)
    protected Boolean runOnSameTWS;
    @XmlElement(nillable = true)
    protected String state;
    @XmlElement(nillable = true)
    protected String stateExt;
    @XmlElement(nillable = true)
    protected String submitServerGroupName;
    @XmlElement(nillable = true)
    protected String submitServerName;
    @XmlElement(nillable = true)
    protected List<Task> tasks;
    @XmlElement(nillable = true)
    protected Boolean transferByTWS;
    @XmlElement(nillable = true)
    protected String tsFinish;
    @XmlElement(nillable = true)
    protected String tsStart;
    @XmlElement(nillable = true)
    protected String tsSubmit;
    @XmlElement(nillable = true)
    protected String type;
    @XmlElement(nillable = true)
    protected String videoFilterPresetGroup;
    @XmlElement(nillable = true)
    protected String videoFilterPresetName;
    @XmlElement(nillable = true)
    protected String watchLoc;
    @XmlElement(nillable = true)
    protected Integer watchLocID;

    /**
     * 获取imdSource属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIMDSource() {
        return imdSource;
    }

    /**
     * 设置imdSource属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIMDSource(String value) {
        this.imdSource = value;
    }

    /**
     * 获取sabetMaxGroupMPixelCount属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getSABETMaxGroupMPixelCount() {
        return sabetMaxGroupMPixelCount;
    }

    /**
     * 设置sabetMaxGroupMPixelCount属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setSABETMaxGroupMPixelCount(Integer value) {
        this.sabetMaxGroupMPixelCount = value;
    }

    /**
     * 获取wfRefreshTime属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getWFRefreshTime() {
        return wfRefreshTime;
    }

    /**
     * 设置wfRefreshTime属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setWFRefreshTime(Integer value) {
        this.wfRefreshTime = value;
    }

    /**
     * 获取wfSourceAction属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getWFSourceAction() {
        return wfSourceAction;
    }

    /**
     * 设置wfSourceAction属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setWFSourceAction(String value) {
        this.wfSourceAction = value;
    }

    /**
     * 获取wfSourceActionLocID属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getWFSourceActionLocID() {
        return wfSourceActionLocID;
    }

    /**
     * 设置wfSourceActionLocID属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setWFSourceActionLocID(Integer value) {
        this.wfSourceActionLocID = value;
    }

    /**
     * 获取wfSubmitQueueLen属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getWFSubmitQueueLen() {
        return wfSubmitQueueLen;
    }

    /**
     * 设置wfSubmitQueueLen属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setWFSubmitQueueLen(Integer value) {
        this.wfSubmitQueueLen = value;
    }

    /**
     * Gets the value of the wFextensions property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the wFextensions property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getWFextensions().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getWFextensions() {
        if (wFextensions == null) {
            wFextensions = new ArrayList<String>();
        }
        return this.wFextensions;
    }

    /**
     * Gets the value of the acmXMLs property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the acmXMLs property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAcmXMLs().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link AudioChannelMappingXML }
     * 
     * 
     */
    public List<AudioChannelMappingXML> getAcmXMLs() {
        if (acmXMLs == null) {
            acmXMLs = new ArrayList<AudioChannelMappingXML>();
        }
        return this.acmXMLs;
    }

    /**
     * 获取allowOutputOverwrite属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isAllowOutputOverwrite() {
        return allowOutputOverwrite;
    }

    /**
     * 设置allowOutputOverwrite属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setAllowOutputOverwrite(Boolean value) {
        this.allowOutputOverwrite = value;
    }

    /**
     * 获取audioFilterPresetGroup属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAudioFilterPresetGroup() {
        return audioFilterPresetGroup;
    }

    /**
     * 设置audioFilterPresetGroup属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAudioFilterPresetGroup(String value) {
        this.audioFilterPresetGroup = value;
    }

    /**
     * 获取audioFilterPresetName属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAudioFilterPresetName() {
        return audioFilterPresetName;
    }

    /**
     * 设置audioFilterPresetName属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAudioFilterPresetName(String value) {
        this.audioFilterPresetName = value;
    }

    /**
     * 获取bypass属性的值。
     * 
     * @return
     *     possible object is
     *     {@link BypassParameter }
     *     
     */
    public BypassParameter getBypass() {
        return bypass;
    }

    /**
     * 设置bypass属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link BypassParameter }
     *     
     */
    public void setBypass(BypassParameter value) {
        this.bypass = value;
    }

    /**
     * 获取hasPostProc属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getHasPostProc() {
        return hasPostProc;
    }

    /**
     * 设置hasPostProc属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setHasPostProc(Integer value) {
        this.hasPostProc = value;
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
     * 获取isMezzanineFormat属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIsMezzanineFormat() {
        return isMezzanineFormat;
    }

    /**
     * 设置isMezzanineFormat属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIsMezzanineFormat(Boolean value) {
        this.isMezzanineFormat = value;
    }

    /**
     * 获取isSabet属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIsSabet() {
        return isSabet;
    }

    /**
     * 设置isSabet属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIsSabet(Boolean value) {
        this.isSabet = value;
    }

    /**
     * 获取lastError属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLastError() {
        return lastError;
    }

    /**
     * 设置lastError属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLastError(String value) {
        this.lastError = value;
    }

    /**
     * 获取m2ROutLocID属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getM2ROutLocID() {
        return m2ROutLocID;
    }

    /**
     * 设置m2ROutLocID属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setM2ROutLocID(Integer value) {
        this.m2ROutLocID = value;
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
     * 获取outputFileName属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOutputFileName() {
        return outputFileName;
    }

    /**
     * 设置outputFileName属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOutputFileName(String value) {
        this.outputFileName = value;
    }

    /**
     * 获取outputFilePath属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOutputFilePath() {
        return outputFilePath;
    }

    /**
     * 设置outputFilePath属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOutputFilePath(String value) {
        this.outputFilePath = value;
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
     * 获取ownerUsername属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOwnerUsername() {
        return ownerUsername;
    }

    /**
     * 设置ownerUsername属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOwnerUsername(String value) {
        this.ownerUsername = value;
    }

    /**
     * 获取pgID属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getPgID() {
        return pgID;
    }

    /**
     * 设置pgID属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setPgID(Integer value) {
        this.pgID = value;
    }

    /**
     * 获取pgPID属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getPgPID() {
        return pgPID;
    }

    /**
     * 设置pgPID属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setPgPID(Integer value) {
        this.pgPID = value;
    }

    /**
     * Gets the value of the postProcCommand property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the postProcCommand property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPostProcCommand().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getPostProcCommand() {
        if (postProcCommand == null) {
            postProcCommand = new ArrayList<String>();
        }
        return this.postProcCommand;
    }

    /**
     * 获取postProcExCode属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getPostProcExCode() {
        return postProcExCode;
    }

    /**
     * 设置postProcExCode属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setPostProcExCode(Integer value) {
        this.postProcExCode = value;
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
     * 获取presetGroupProxy属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPresetGroupProxy() {
        return presetGroupProxy;
    }

    /**
     * 设置presetGroupProxy属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPresetGroupProxy(String value) {
        this.presetGroupProxy = value;
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
     * 获取proxyLink属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProxyLink() {
        return proxyLink;
    }

    /**
     * 设置proxyLink属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProxyLink(String value) {
        this.proxyLink = value;
    }

    /**
     * 获取ruleID属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getRuleID() {
        return ruleID;
    }

    /**
     * 设置ruleID属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setRuleID(Integer value) {
        this.ruleID = value;
    }

    /**
     * 获取runOnSameTWS属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isRunOnSameTWS() {
        return runOnSameTWS;
    }

    /**
     * 设置runOnSameTWS属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setRunOnSameTWS(Boolean value) {
        this.runOnSameTWS = value;
    }

    /**
     * 获取state属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getState() {
        return state;
    }

    /**
     * 设置state属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setState(String value) {
        this.state = value;
    }

    /**
     * 获取stateExt属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStateExt() {
        return stateExt;
    }

    /**
     * 设置stateExt属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStateExt(String value) {
        this.stateExt = value;
    }

    /**
     * 获取submitServerGroupName属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSubmitServerGroupName() {
        return submitServerGroupName;
    }

    /**
     * 设置submitServerGroupName属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSubmitServerGroupName(String value) {
        this.submitServerGroupName = value;
    }

    /**
     * 获取submitServerName属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSubmitServerName() {
        return submitServerName;
    }

    /**
     * 设置submitServerName属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSubmitServerName(String value) {
        this.submitServerName = value;
    }

    /**
     * Gets the value of the tasks property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the tasks property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getTasks().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Task }
     * 
     * 
     */
    public List<Task> getTasks() {
        if (tasks == null) {
            tasks = new ArrayList<Task>();
        }
        return this.tasks;
    }

    /**
     * 获取transferByTWS属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isTransferByTWS() {
        return transferByTWS;
    }

    /**
     * 设置transferByTWS属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setTransferByTWS(Boolean value) {
        this.transferByTWS = value;
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
     * 获取videoFilterPresetGroup属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVideoFilterPresetGroup() {
        return videoFilterPresetGroup;
    }

    /**
     * 设置videoFilterPresetGroup属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVideoFilterPresetGroup(String value) {
        this.videoFilterPresetGroup = value;
    }

    /**
     * 获取videoFilterPresetName属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVideoFilterPresetName() {
        return videoFilterPresetName;
    }

    /**
     * 设置videoFilterPresetName属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVideoFilterPresetName(String value) {
        this.videoFilterPresetName = value;
    }

    /**
     * 获取watchLoc属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getWatchLoc() {
        return watchLoc;
    }

    /**
     * 设置watchLoc属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setWatchLoc(String value) {
        this.watchLoc = value;
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
