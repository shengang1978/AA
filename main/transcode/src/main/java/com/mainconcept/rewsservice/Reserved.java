
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
 *         &lt;element name="reserved1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="reserved2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="reserved3" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
    "reserved1",
    "reserved2",
    "reserved3"
})
@XmlRootElement(name = "Reserved")
public class Reserved {

    @XmlElement(nillable = true)
    protected String reserved1;
    @XmlElement(nillable = true)
    protected String reserved2;
    @XmlElement(nillable = true)
    protected String reserved3;

    /**
     * ��ȡreserved1���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReserved1() {
        return reserved1;
    }

    /**
     * ����reserved1���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReserved1(String value) {
        this.reserved1 = value;
    }

    /**
     * ��ȡreserved2���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReserved2() {
        return reserved2;
    }

    /**
     * ����reserved2���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReserved2(String value) {
        this.reserved2 = value;
    }

    /**
     * ��ȡreserved3���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReserved3() {
        return reserved3;
    }

    /**
     * ����reserved3���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReserved3(String value) {
        this.reserved3 = value;
    }

}
