
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
 *         &lt;element name="watchSearchOpts" type="{http://api.rews.mainconcept.com/xsd}WatchSearchOpts" minOccurs="0"/>
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
    "watchSearchOpts"
})
@XmlRootElement(name = "listWatchJobs")
public class ListWatchJobs {

    @XmlElement(nillable = true)
    protected WatchSearchOpts watchSearchOpts;

    /**
     * ��ȡwatchSearchOpts���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link WatchSearchOpts }
     *     
     */
    public WatchSearchOpts getWatchSearchOpts() {
        return watchSearchOpts;
    }

    /**
     * ����watchSearchOpts���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link WatchSearchOpts }
     *     
     */
    public void setWatchSearchOpts(WatchSearchOpts value) {
        this.watchSearchOpts = value;
    }

}
