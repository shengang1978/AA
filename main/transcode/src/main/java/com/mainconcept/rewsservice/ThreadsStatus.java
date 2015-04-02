
package com.mainconcept.rewsservice;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>ThreadsStatus complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="ThreadsStatus">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="count" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="info" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="numBlocked" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="numNew" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="numRunnable" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="numTerminated" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="numTimedWaiting" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="numUnknown" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="numWaiting" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="peakCount" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ThreadsStatus", namespace = "http://monitor.api.rews.mainconcept.com/xsd", propOrder = {
    "count",
    "info",
    "numBlocked",
    "numNew",
    "numRunnable",
    "numTerminated",
    "numTimedWaiting",
    "numUnknown",
    "numWaiting",
    "peakCount"
})
public class ThreadsStatus {

    protected Integer count;
    @XmlElement(nillable = true)
    protected List<String> info;
    protected Integer numBlocked;
    protected Integer numNew;
    protected Integer numRunnable;
    protected Integer numTerminated;
    protected Integer numTimedWaiting;
    protected Integer numUnknown;
    protected Integer numWaiting;
    protected Integer peakCount;

    /**
     * 获取count属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getCount() {
        return count;
    }

    /**
     * 设置count属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setCount(Integer value) {
        this.count = value;
    }

    /**
     * Gets the value of the info property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the info property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getInfo().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getInfo() {
        if (info == null) {
            info = new ArrayList<String>();
        }
        return this.info;
    }

    /**
     * 获取numBlocked属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getNumBlocked() {
        return numBlocked;
    }

    /**
     * 设置numBlocked属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setNumBlocked(Integer value) {
        this.numBlocked = value;
    }

    /**
     * 获取numNew属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getNumNew() {
        return numNew;
    }

    /**
     * 设置numNew属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setNumNew(Integer value) {
        this.numNew = value;
    }

    /**
     * 获取numRunnable属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getNumRunnable() {
        return numRunnable;
    }

    /**
     * 设置numRunnable属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setNumRunnable(Integer value) {
        this.numRunnable = value;
    }

    /**
     * 获取numTerminated属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getNumTerminated() {
        return numTerminated;
    }

    /**
     * 设置numTerminated属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setNumTerminated(Integer value) {
        this.numTerminated = value;
    }

    /**
     * 获取numTimedWaiting属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getNumTimedWaiting() {
        return numTimedWaiting;
    }

    /**
     * 设置numTimedWaiting属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setNumTimedWaiting(Integer value) {
        this.numTimedWaiting = value;
    }

    /**
     * 获取numUnknown属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getNumUnknown() {
        return numUnknown;
    }

    /**
     * 设置numUnknown属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setNumUnknown(Integer value) {
        this.numUnknown = value;
    }

    /**
     * 获取numWaiting属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getNumWaiting() {
        return numWaiting;
    }

    /**
     * 设置numWaiting属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setNumWaiting(Integer value) {
        this.numWaiting = value;
    }

    /**
     * 获取peakCount属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getPeakCount() {
        return peakCount;
    }

    /**
     * 设置peakCount属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setPeakCount(Integer value) {
        this.peakCount = value;
    }

}
