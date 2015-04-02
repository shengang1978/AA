
package com.mainconcept.rewsservice;

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
 *         &lt;element name="presetGroup" type="{http://api.rews.mainconcept.com/xsd}PresetGroup" minOccurs="0"/>
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
    "presetGroup"
})
@XmlRootElement(name = "updatePresetGroup")
public class UpdatePresetGroup {

    @XmlElement(nillable = true)
    protected PresetGroup presetGroup;

    /**
     * 获取presetGroup属性的值。
     * 
     * @return
     *     possible object is
     *     {@link PresetGroup }
     *     
     */
    public PresetGroup getPresetGroup() {
        return presetGroup;
    }

    /**
     * 设置presetGroup属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link PresetGroup }
     *     
     */
    public void setPresetGroup(PresetGroup value) {
        this.presetGroup = value;
    }

}
