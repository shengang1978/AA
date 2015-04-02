
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
 *         &lt;element name="jobID" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="bypass" type="{http://api.rews.mainconcept.com/xsd}BypassParameter" minOccurs="0"/>
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
    "jobID",
    "bypass"
})
@XmlRootElement(name = "updateBypassOfJob")
public class UpdateBypassOfJob {

    protected Integer jobID;
    @XmlElement(nillable = true)
    protected BypassParameter bypass;

    /**
     * 获取jobID属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getJobID() {
        return jobID;
    }

    /**
     * 设置jobID属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setJobID(Integer value) {
        this.jobID = value;
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

}
