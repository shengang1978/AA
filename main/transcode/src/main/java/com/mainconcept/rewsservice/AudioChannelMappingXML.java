
package com.mainconcept.rewsservice;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>AudioChannelMappingXML complex type�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
 * 
 * <pre>
 * &lt;complexType name="AudioChannelMappingXML">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="mappings" type="{http://api.rews.mainconcept.com/xsd}AudioChannelMappingNode" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="preset" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AudioChannelMappingXML", namespace = "http://api.rews.mainconcept.com/xsd", propOrder = {
    "mappings",
    "preset"
})
public class AudioChannelMappingXML {

    @XmlElement(nillable = true)
    protected List<AudioChannelMappingNode> mappings;
    @XmlElement(nillable = true)
    protected String preset;

    /**
     * Gets the value of the mappings property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the mappings property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getMappings().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link AudioChannelMappingNode }
     * 
     * 
     */
    public List<AudioChannelMappingNode> getMappings() {
        if (mappings == null) {
            mappings = new ArrayList<AudioChannelMappingNode>();
        }
        return this.mappings;
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

}
