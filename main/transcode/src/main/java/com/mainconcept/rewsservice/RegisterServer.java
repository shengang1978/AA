
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
     * 获取newServer属性的值。
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
     * 设置newServer属性的值。
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
     * 获取twsClientID属性的值。
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
     * 设置twsClientID属性的值。
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
     * 获取reserved属性的值。
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
     * 设置reserved属性的值。
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
