
package com.mainconcept.rewsservice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>LocAuth complex type�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
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
     * ��ȡftPlogin���Ե�ֵ��
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
     * ����ftPlogin���Ե�ֵ��
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
     * ��ȡftPpassword���Ե�ֵ��
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
     * ����ftPpassword���Ե�ֵ��
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
     * ��ȡs3AccKey���Ե�ֵ��
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
     * ����s3AccKey���Ե�ֵ��
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
     * ��ȡs3SecKey���Ե�ֵ��
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
     * ����s3SecKey���Ե�ֵ��
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
