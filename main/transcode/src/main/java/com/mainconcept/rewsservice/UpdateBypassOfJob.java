
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
     * ��ȡjobID���Ե�ֵ��
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
     * ����jobID���Ե�ֵ��
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
     * ��ȡbypass���Ե�ֵ��
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
     * ����bypass���Ե�ֵ��
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
