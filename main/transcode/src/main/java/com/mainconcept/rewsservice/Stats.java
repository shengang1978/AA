
package com.mainconcept.rewsservice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Stats complex type�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
 * 
 * <pre>
 * &lt;complexType name="Stats">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="numPendingJobsPerSG" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="numPendingTasksPerSG" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="numStartedTasksPerTWS" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="numSubmittedJobsPerPG" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="numSubmittedJobsPerSG" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="numSubmittedJobsPerWF" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="numSubmittedTasksPerPG" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="numSubmittedTasksPerSG" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="numSubmittedTasksPerWF" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Stats", namespace = "http://api.rews.mainconcept.com/xsd", propOrder = {
    "numPendingJobsPerSG",
    "numPendingTasksPerSG",
    "numStartedTasksPerTWS",
    "numSubmittedJobsPerPG",
    "numSubmittedJobsPerSG",
    "numSubmittedJobsPerWF",
    "numSubmittedTasksPerPG",
    "numSubmittedTasksPerSG",
    "numSubmittedTasksPerWF"
})
public class Stats {

    @XmlElement(nillable = true)
    protected Integer numPendingJobsPerSG;
    @XmlElement(nillable = true)
    protected Integer numPendingTasksPerSG;
    @XmlElement(nillable = true)
    protected Integer numStartedTasksPerTWS;
    @XmlElement(nillable = true)
    protected Integer numSubmittedJobsPerPG;
    @XmlElement(nillable = true)
    protected Integer numSubmittedJobsPerSG;
    @XmlElement(nillable = true)
    protected Integer numSubmittedJobsPerWF;
    @XmlElement(nillable = true)
    protected Integer numSubmittedTasksPerPG;
    @XmlElement(nillable = true)
    protected Integer numSubmittedTasksPerSG;
    @XmlElement(nillable = true)
    protected Integer numSubmittedTasksPerWF;

    /**
     * ��ȡnumPendingJobsPerSG���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getNumPendingJobsPerSG() {
        return numPendingJobsPerSG;
    }

    /**
     * ����numPendingJobsPerSG���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setNumPendingJobsPerSG(Integer value) {
        this.numPendingJobsPerSG = value;
    }

    /**
     * ��ȡnumPendingTasksPerSG���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getNumPendingTasksPerSG() {
        return numPendingTasksPerSG;
    }

    /**
     * ����numPendingTasksPerSG���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setNumPendingTasksPerSG(Integer value) {
        this.numPendingTasksPerSG = value;
    }

    /**
     * ��ȡnumStartedTasksPerTWS���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getNumStartedTasksPerTWS() {
        return numStartedTasksPerTWS;
    }

    /**
     * ����numStartedTasksPerTWS���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setNumStartedTasksPerTWS(Integer value) {
        this.numStartedTasksPerTWS = value;
    }

    /**
     * ��ȡnumSubmittedJobsPerPG���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getNumSubmittedJobsPerPG() {
        return numSubmittedJobsPerPG;
    }

    /**
     * ����numSubmittedJobsPerPG���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setNumSubmittedJobsPerPG(Integer value) {
        this.numSubmittedJobsPerPG = value;
    }

    /**
     * ��ȡnumSubmittedJobsPerSG���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getNumSubmittedJobsPerSG() {
        return numSubmittedJobsPerSG;
    }

    /**
     * ����numSubmittedJobsPerSG���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setNumSubmittedJobsPerSG(Integer value) {
        this.numSubmittedJobsPerSG = value;
    }

    /**
     * ��ȡnumSubmittedJobsPerWF���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getNumSubmittedJobsPerWF() {
        return numSubmittedJobsPerWF;
    }

    /**
     * ����numSubmittedJobsPerWF���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setNumSubmittedJobsPerWF(Integer value) {
        this.numSubmittedJobsPerWF = value;
    }

    /**
     * ��ȡnumSubmittedTasksPerPG���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getNumSubmittedTasksPerPG() {
        return numSubmittedTasksPerPG;
    }

    /**
     * ����numSubmittedTasksPerPG���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setNumSubmittedTasksPerPG(Integer value) {
        this.numSubmittedTasksPerPG = value;
    }

    /**
     * ��ȡnumSubmittedTasksPerSG���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getNumSubmittedTasksPerSG() {
        return numSubmittedTasksPerSG;
    }

    /**
     * ����numSubmittedTasksPerSG���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setNumSubmittedTasksPerSG(Integer value) {
        this.numSubmittedTasksPerSG = value;
    }

    /**
     * ��ȡnumSubmittedTasksPerWF���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getNumSubmittedTasksPerWF() {
        return numSubmittedTasksPerWF;
    }

    /**
     * ����numSubmittedTasksPerWF���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setNumSubmittedTasksPerWF(Integer value) {
        this.numSubmittedTasksPerWF = value;
    }

}
