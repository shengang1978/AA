
package com.mainconcept.rewsservice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>anonymous complex type�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="newPG" type="{http://api.rews.mainconcept.com/xsd}PresetGroup" minOccurs="0"/>
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
    "newPG"
})
@XmlRootElement(name = "addPresetGroup")
public class AddPresetGroup {

    @XmlElement(nillable = true)
    protected PresetGroup newPG;

    /**
     * ��ȡnewPG���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link PresetGroup }
     *     
     */
    public PresetGroup getNewPG() {
        return newPG;
    }

    /**
     * ����newPG���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link PresetGroup }
     *     
     */
    public void setNewPG(PresetGroup value) {
        this.newPG = value;
    }

}
