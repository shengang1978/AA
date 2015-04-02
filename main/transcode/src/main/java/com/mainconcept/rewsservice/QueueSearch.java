
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
 *         &lt;element name="queueSearchOpts" type="{http://api.rews.mainconcept.com/xsd}QSearchOpts" minOccurs="0"/>
 *         &lt;element name="fetchAllFields" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
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
    "fetchAllFields"
})
@XmlRootElement(name = "queueSearch")
public class QueueSearch {

    @XmlElement(nillable = true)
    protected QSearchOpts queueSearchOpts;
    protected Boolean fetchAllFields;

    /**
     * ��ȡqueueSearchOpts���Ե�ֵ��
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
     * ����queueSearchOpts���Ե�ֵ��
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
     * ��ȡfetchAllFields���Ե�ֵ��
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
     * ����fetchAllFields���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setFetchAllFields(Boolean value) {
        this.fetchAllFields = value;
    }

}
