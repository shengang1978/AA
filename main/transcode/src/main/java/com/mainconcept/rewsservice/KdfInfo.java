
package com.mainconcept.rewsservice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>KdfInfo complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="KdfInfo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="apid" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="unencryptedKey1" type="{http://api.rews.mainconcept.com/xsd}UnencryptedKey" minOccurs="0"/>
 *         &lt;element name="unencryptedKey2" type="{http://api.rews.mainconcept.com/xsd}UnencryptedKey" minOccurs="0"/>
 *         &lt;element name="validApid" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="validKdfFile" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="validUnencryptedKey1" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="validUnencryptedKey2" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "KdfInfo", namespace = "http://api.rews.mainconcept.com/xsd", propOrder = {
    "apid",
    "unencryptedKey1",
    "unencryptedKey2",
    "validApid",
    "validKdfFile",
    "validUnencryptedKey1",
    "validUnencryptedKey2"
})
public class KdfInfo {

    @XmlElement(nillable = true)
    protected String apid;
    @XmlElement(nillable = true)
    protected UnencryptedKey unencryptedKey1;
    @XmlElement(nillable = true)
    protected UnencryptedKey unencryptedKey2;
    protected Boolean validApid;
    protected Boolean validKdfFile;
    protected Boolean validUnencryptedKey1;
    protected Boolean validUnencryptedKey2;

    /**
     * 获取apid属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getApid() {
        return apid;
    }

    /**
     * 设置apid属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setApid(String value) {
        this.apid = value;
    }

    /**
     * 获取unencryptedKey1属性的值。
     * 
     * @return
     *     possible object is
     *     {@link UnencryptedKey }
     *     
     */
    public UnencryptedKey getUnencryptedKey1() {
        return unencryptedKey1;
    }

    /**
     * 设置unencryptedKey1属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link UnencryptedKey }
     *     
     */
    public void setUnencryptedKey1(UnencryptedKey value) {
        this.unencryptedKey1 = value;
    }

    /**
     * 获取unencryptedKey2属性的值。
     * 
     * @return
     *     possible object is
     *     {@link UnencryptedKey }
     *     
     */
    public UnencryptedKey getUnencryptedKey2() {
        return unencryptedKey2;
    }

    /**
     * 设置unencryptedKey2属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link UnencryptedKey }
     *     
     */
    public void setUnencryptedKey2(UnencryptedKey value) {
        this.unencryptedKey2 = value;
    }

    /**
     * 获取validApid属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isValidApid() {
        return validApid;
    }

    /**
     * 设置validApid属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setValidApid(Boolean value) {
        this.validApid = value;
    }

    /**
     * 获取validKdfFile属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isValidKdfFile() {
        return validKdfFile;
    }

    /**
     * 设置validKdfFile属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setValidKdfFile(Boolean value) {
        this.validKdfFile = value;
    }

    /**
     * 获取validUnencryptedKey1属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isValidUnencryptedKey1() {
        return validUnencryptedKey1;
    }

    /**
     * 设置validUnencryptedKey1属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setValidUnencryptedKey1(Boolean value) {
        this.validUnencryptedKey1 = value;
    }

    /**
     * 获取validUnencryptedKey2属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isValidUnencryptedKey2() {
        return validUnencryptedKey2;
    }

    /**
     * 设置validUnencryptedKey2属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setValidUnencryptedKey2(Boolean value) {
        this.validUnencryptedKey2 = value;
    }

}
