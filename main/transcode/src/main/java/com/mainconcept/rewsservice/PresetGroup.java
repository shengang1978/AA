
package com.mainconcept.rewsservice;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>PresetGroup complex type�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
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
     * ��ȡaqc���Ե�ֵ��
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
     * ����aqc���Ե�ֵ��
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
     * ��ȡdescription���Ե�ֵ��
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
     * ����description���Ե�ֵ��
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
     * ��ȡisFilter���Ե�ֵ��
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
     * ����isFilter���Ե�ֵ��
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
     * ��ȡisTmp���Ե�ֵ��
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
     * ����isTmp���Ե�ֵ��
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
     * ��ȡname2���Ե�ֵ��
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
     * ����name2���Ե�ֵ��
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
     * ��ȡrawxml���Ե�ֵ��
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
     * ����rawxml���Ե�ֵ��
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
