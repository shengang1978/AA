
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
 *         &lt;element name="queueSearchOpts" type="{http://api.rews.mainconcept.com/xsd}QSearchOpts" minOccurs="0"/>
 *         &lt;element name="fetchAllFields" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="startNo" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="quantity" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
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
    "queueSearchOpts",
    "fetchAllFields",
    "startNo",
    "quantity"
})
@XmlRootElement(name = "queueSearchExportOffset")
public class QueueSearchExportOffset {

    @XmlElement(nillable = true)
    protected QSearchOpts queueSearchOpts;
    protected Boolean fetchAllFields;
    protected Long startNo;
    protected Long quantity;

    /**
     * 获取queueSearchOpts属性的值。
     * 
     * @return
     *     possible object is
     *     {@link QSearchOpts }
     *     
     */
    public QSearchOpts getQueueSearchOpts() {
        return queueSearchOpts;
    }

    /**
     * 设置queueSearchOpts属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link QSearchOpts }
     *     
     */
    public void setQueueSearchOpts(QSearchOpts value) {
        this.queueSearchOpts = value;
    }

    /**
     * 获取fetchAllFields属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isFetchAllFields() {
        return fetchAllFields;
    }

    /**
     * 设置fetchAllFields属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setFetchAllFields(Boolean value) {
        this.fetchAllFields = value;
    }

    /**
     * 获取startNo属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getStartNo() {
        return startNo;
    }

    /**
     * 设置startNo属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setStartNo(Long value) {
        this.startNo = value;
    }

    /**
     * 获取quantity属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getQuantity() {
        return quantity;
    }

    /**
     * 设置quantity属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setQuantity(Long value) {
        this.quantity = value;
    }

}
