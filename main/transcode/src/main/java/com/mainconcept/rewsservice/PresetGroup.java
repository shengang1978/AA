
package com.mainconcept.rewsservice;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>PresetGroup complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="PresetGroup">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="aqc" type="{http://api.rews.mainconcept.com/xsd}AutoQC" minOccurs="0"/>
 *         &lt;element name="description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="isFilter" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="isTmp" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="name2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="presets" type="{http://api.rews.mainconcept.com/xsd}Preset" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="rawxml" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PresetGroup", namespace = "http://api.rews.mainconcept.com/xsd", propOrder = {
    "aqc",
    "description",
    "id",
    "isFilter",
    "isTmp",
    "name",
    "name2",
    "presets",
    "rawxml"
})
public class PresetGroup {

    @XmlElement(nillable = true)
    protected AutoQC aqc;
    @XmlElement(nillable = true)
    protected String description;
    @XmlElement(nillable = true)
    protected Integer id;
    @XmlElement(nillable = true)
    protected Integer isFilter;
    @XmlElement(nillable = true)
    protected Integer isTmp;
    @XmlElement(nillable = true)
    protected String name;
    @XmlElement(nillable = true)
    protected String name2;
    @XmlElement(nillable = true)
    protected List<Preset> presets;
    @XmlElement(nillable = true)
    protected String rawxml;

    /**
     * 获取aqc属性的值。
     * 
     * @return
     *     possible object is
     *     {@link AutoQC }
     *     
     */
    public AutoQC getAqc() {
        return aqc;
    }

    /**
     * 设置aqc属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link AutoQC }
     *     
     */
    public void setAqc(AutoQC value) {
        this.aqc = value;
    }

    /**
     * 获取description属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescription() {
        return description;
    }

    /**
     * 设置description属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescription(String value) {
        this.description = value;
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
     * 获取isFilter属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getIsFilter() {
        return isFilter;
    }

    /**
     * 设置isFilter属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setIsFilter(Integer value) {
        this.isFilter = value;
    }

    /**
     * 获取isTmp属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getIsTmp() {
        return isTmp;
    }

    /**
     * 设置isTmp属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setIsTmp(Integer value) {
        this.isTmp = value;
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
     * 获取name2属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getName2() {
        return name2;
    }

    /**
     * 设置name2属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setName2(String value) {
        this.name2 = value;
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
     * {@link Preset }
     * 
     * 
     */
    public List<Preset> getPresets() {
        if (presets == null) {
            presets = new ArrayList<Preset>();
        }
        return this.presets;
    }

    /**
     * 获取rawxml属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRawxml() {
        return rawxml;
    }

    /**
     * 设置rawxml属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRawxml(String value) {
        this.rawxml = value;
    }

}
