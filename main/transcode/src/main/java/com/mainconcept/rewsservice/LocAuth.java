
package com.mainconcept.rewsservice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>LocAuth complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="LocAuth">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="FTPlogin" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="FTPpassword" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="s3AccKey" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="s3SecKey" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "LocAuth", namespace = "http://api.rews.mainconcept.com/xsd", propOrder = {
    "ftPlogin",
    "ftPpassword",
    "s3AccKey",
    "s3SecKey"
})
@XmlSeeAlso({
    LocAuthTCE.class
})
public class LocAuth {

    @XmlElement(name = "FTPlogin", nillable = true)
    protected String ftPlogin;
    @XmlElement(name = "FTPpassword", nillable = true)
    protected String ftPpassword;
    @XmlElement(nillable = true)
    protected String s3AccKey;
    @XmlElement(nillable = true)
    protected String s3SecKey;

    /**
     * 获取ftPlogin属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFTPlogin() {
        return ftPlogin;
    }

    /**
     * 设置ftPlogin属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFTPlogin(String value) {
        this.ftPlogin = value;
    }

    /**
     * 获取ftPpassword属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFTPpassword() {
        return ftPpassword;
    }

    /**
     * 设置ftPpassword属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFTPpassword(String value) {
        this.ftPpassword = value;
    }

    /**
     * 获取s3AccKey属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getS3AccKey() {
        return s3AccKey;
    }

    /**
     * 设置s3AccKey属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setS3AccKey(String value) {
        this.s3AccKey = value;
    }

    /**
     * 获取s3SecKey属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getS3SecKey() {
        return s3SecKey;
    }

    /**
     * 设置s3SecKey属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setS3SecKey(String value) {
        this.s3SecKey = value;
    }

}
