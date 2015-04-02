
package com.mainconcept.rewsservice;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>QSearchResult complex type�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
 * 
 * <pre>
 * &lt;complexType name="QSearchResult">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="hasMore" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="jobs" type="{http://api.rews.mainconcept.com/xsd}Job" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="totalNumJobs" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="totalNumTasks" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "QSearchResult", namespace = "http://api.rews.mainconcept.com/xsd", propOrder = {
    "hasMore",
    "jobs",
    "totalNumJobs",
    "totalNumTasks"
})
public class QSearchResult {

    @XmlElement(nillable = true)
    protected Boolean hasMore;
    @XmlElement(nillable = true)
    protected List<Job> jobs;
    @XmlElement(nillable = true)
    protected Integer totalNumJobs;
    @XmlElement(nillable = true)
    protected Integer totalNumTasks;

    /**
     * ��ȡhasMore���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isHasMore() {
        return hasMore;
    }

    /**
     * ����hasMore���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setHasMore(Boolean value) {
        this.hasMore = value;
    }

    /**
     * Gets the value of the jobs property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the jobs property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getJobs().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Job }
     * 
     * 
     */
    public List<Job> getJobs() {
        if (jobs == null) {
            jobs = new ArrayList<Job>();
        }
        return this.jobs;
    }

    /**
     * ��ȡtotalNumJobs���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getTotalNumJobs() {
        return totalNumJobs;
    }

    /**
     * ����totalNumJobs���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setTotalNumJobs(Integer value) {
        this.totalNumJobs = value;
    }

    /**
     * ��ȡtotalNumTasks���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getTotalNumTasks() {
        return totalNumTasks;
    }

    /**
     * ����totalNumTasks���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setTotalNumTasks(Integer value) {
        this.totalNumTasks = value;
    }

}
