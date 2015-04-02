
package com.mainconcept.rewsservice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>License complex type�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
 * 
 * <pre>
 * &lt;complexType name="License">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="featureMap" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="firmItemText" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="numLic" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="productCode" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="productName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="usedLic" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "License", namespace = "http://api.rews.mainconcept.com/xsd", propOrder = {
    "featureMap",
    "firmItemText",
    "numLic",
    "productCode",
    "productName",
    "usedLic"
})
public class License {

    @XmlElement(nillable = true)
    protected Long featureMap;
    @XmlElement(nillable = true)
    protected String firmItemText;
    @XmlElement(nillable = true)
    protected Integer numLic;
    @XmlElement(nillable = true)
    protected Long productCode;
    @XmlElement(nillable = true)
    protected String productName;
    @XmlElement(nillable = true)
    protected Integer usedLic;

    /**
     * ��ȡfeatureMap���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getFeatureMap() {
        return featureMap;
    }

    /**
     * ����featureMap���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setFeatureMap(Long value) {
        this.featureMap = value;
    }

    /**
     * ��ȡfirmItemText���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFirmItemText() {
        return firmItemText;
    }

    /**
     * ����firmItemText���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFirmItemText(String value) {
        this.firmItemText = value;
    }

    /**
     * ��ȡnumLic���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getNumLic() {
        return numLic;
    }

    /**
     * ����numLic���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setNumLic(Integer value) {
        this.numLic = value;
    }

    /**
     * ��ȡproductCode���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getProductCode() {
        return productCode;
    }

    /**
     * ����productCode���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setProductCode(Long value) {
        this.productCode = value;
    }

    /**
     * ��ȡproductName���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProductName() {
        return productName;
    }

    /**
     * ����productName���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProductName(String value) {
        this.productName = value;
    }

    /**
     * ��ȡusedLic���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getUsedLic() {
        return usedLic;
    }

    /**
     * ����usedLic���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setUsedLic(Integer value) {
        this.usedLic = value;
    }

}
