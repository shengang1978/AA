
package com.mainconcept.rewsservice;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>ThreadsStatus complex type�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
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
     * ��ȡcount���Ե�ֵ��
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
     * ����count���Ե�ֵ��
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
     * ��ȡnumBlocked���Ե�ֵ��
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
     * ����numBlocked���Ե�ֵ��
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
     * ��ȡnumNew���Ե�ֵ��
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
     * ����numNew���Ե�ֵ��
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
     * ��ȡnumRunnable���Ե�ֵ��
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
     * ����numRunnable���Ե�ֵ��
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
     * ��ȡnumTerminated���Ե�ֵ��
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
     * ����numTerminated���Ե�ֵ��
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
     * ��ȡnumTimedWaiting���Ե�ֵ��
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
     * ����numTimedWaiting���Ե�ֵ��
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
     * ��ȡnumUnknown���Ե�ֵ��
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
     * ����numUnknown���Ե�ֵ��
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
     * ��ȡnumWaiting���Ե�ֵ��
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
     * ����numWaiting���Ե�ֵ��
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
     * ��ȡpeakCount���Ե�ֵ��
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
     * ����peakCount���Ե�ֵ��
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
