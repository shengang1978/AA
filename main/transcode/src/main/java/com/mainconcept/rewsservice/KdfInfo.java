
package com.mainconcept.rewsservice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>KdfInfo complex type�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
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
     * ��ȡapid���Ե�ֵ��
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
     * ����apid���Ե�ֵ��
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
     * ��ȡunencryptedKey1���Ե�ֵ��
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
     * ����unencryptedKey1���Ե�ֵ��
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
     * ��ȡunencryptedKey2���Ե�ֵ��
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
     * ����unencryptedKey2���Ե�ֵ��
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
     * ��ȡvalidApid���Ե�ֵ��
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
     * ����validApid���Ե�ֵ��
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
     * ��ȡvalidKdfFile���Ե�ֵ��
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
     * ����validKdfFile���Ե�ֵ��
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
     * ��ȡvalidUnencryptedKey1���Ե�ֵ��
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
     * ����validUnencryptedKey1���Ե�ֵ��
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
     * ��ȡvalidUnencryptedKey2���Ե�ֵ��
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
     * ����validUnencryptedKey2���Ե�ֵ��
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
