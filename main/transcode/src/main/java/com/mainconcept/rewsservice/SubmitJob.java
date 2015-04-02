
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
 *         &lt;element name="job" type="{http://api.rews.mainconcept.com/xsd}Job" minOccurs="0"/>
 *         &lt;element name="presets" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="inStreams" type="{http://api.rews.mainconcept.com/xsd}InputStream" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="priority" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="makeProxy" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="tmpPG" type="{http://api.rews.mainconcept.com/xsd}PresetGroup" minOccurs="0"/>
 *         &lt;element name="UVSettings" type="{http://api.rews.mainconcept.com/xsd}UVSettings" maxOccurs="unbounded" minOccurs="0"/>
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
    "job",
    "presets",
    "inStreams",
    "priority",
    "makeProxy",
    "tmpPG",
    "uvSettings"
})
@XmlRootElement(name = "submitJob")
public class SubmitJob {

    @XmlElement(nillable = true)
    protected Job job;
    @XmlElement(nillable = true)
    protected List<String> presets;
    @XmlElement(nillable = true)
    protected List<InputStream> inStreams;
    protected Integer priority;
    protected Boolean makeProxy;
    @XmlElement(nillable = true)
    protected PresetGroup tmpPG;
    @XmlElement(name = "UVSettings", nillable = true)
    protected List<UVSettings> uvSettings;

    /**
     * 获取job属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Job }
     *     
     */
    public Job getJob() {
        return job;
    }

    /**
     * 设置job属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Job }
     *     
     */
    public void setJob(Job value) {
        this.job = value;
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
     * Gets the value of the inStreams property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the inStreams property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getInStreams().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link InputStream }
     * 
     * 
     */
    public List<InputStream> getInStreams() {
        if (inStreams == null) {
            inStreams = new ArrayList<InputStream>();
        }
        return this.inStreams;
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
     * 获取tmpPG属性的值。
     * 
     * @return
     *     possible object is
     *     {@link PresetGroup }
     *     
     */
    public PresetGroup getTmpPG() {
        return tmpPG;
    }

    /**
     * 设置tmpPG属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link PresetGroup }
     *     
     */
    public void setTmpPG(PresetGroup value) {
        this.tmpPG = value;
    }

    /**
     * Gets the value of the uvSettings property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the uvSettings property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getUVSettings().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link UVSettings }
     * 
     * 
     */
    public List<UVSettings> getUVSettings() {
        if (uvSettings == null) {
            uvSettings = new ArrayList<UVSettings>();
        }
        return this.uvSettings;
    }

}
