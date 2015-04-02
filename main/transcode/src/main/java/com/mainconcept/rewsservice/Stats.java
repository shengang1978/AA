
package com.mainconcept.rewsservice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Stats complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
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
     * 获取numPendingJobsPerSG属性的值。
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
     * 设置numPendingJobsPerSG属性的值。
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
     * 获取numPendingTasksPerSG属性的值。
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
     * 设置numPendingTasksPerSG属性的值。
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
     * 获取numStartedTasksPerTWS属性的值。
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
     * 设置numStartedTasksPerTWS属性的值。
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
     * 获取numSubmittedJobsPerPG属性的值。
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
     * 设置numSubmittedJobsPerPG属性的值。
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
     * 获取numSubmittedJobsPerSG属性的值。
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
     * 设置numSubmittedJobsPerSG属性的值。
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
     * 获取numSubmittedJobsPerWF属性的值。
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
     * 设置numSubmittedJobsPerWF属性的值。
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
     * 获取numSubmittedTasksPerPG属性的值。
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
     * 设置numSubmittedTasksPerPG属性的值。
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
     * 获取numSubmittedTasksPerSG属性的值。
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
     * 设置numSubmittedTasksPerSG属性的值。
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
     * 获取numSubmittedTasksPerWF属性的值。
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
     * 设置numSubmittedTasksPerWF属性的值。
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
