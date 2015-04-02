
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
 *         &lt;element name="newServer" type="{http://api.rews.mainconcept.com/xsd}Server" minOccurs="0"/>
 *         &lt;element name="TWSClientID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="reserved" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
    "newServer",
    "twsClientID",
    "reserved"
})
@XmlRootElement(name = "registerServer")
public class RegisterServer {

    @XmlElement(nillable = true)
    protected Server newServer;
    @XmlElement(name = "TWSClientID", nillable = true)
    protected String twsClientID;
    @XmlElement(nillable = true)
    protected String reserved;

    /**
     * ��ȡnewServer���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link Server }
     *     
     */
    public Server getNewServer() {
        return newServer;
    }

    /**
     * ����newServer���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link Server }
     *     
     */
    public void setNewServer(Server value) {
        this.newServer = value;
    }

    /**
     * ��ȡtwsClientID���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTWSClientID() {
        return twsClientID;
    }

    /**
     * ����twsClientID���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTWSClientID(String value) {
        this.twsClientID = value;
    }

    /**
     * ��ȡreserved���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReserved() {
        return reserved;
    }

    /**
     * ����reserved���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReserved(String value) {
        this.reserved = value;
    }

}
