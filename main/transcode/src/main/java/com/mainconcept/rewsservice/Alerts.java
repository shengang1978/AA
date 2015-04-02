
package com.mainconcept.rewsservice;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Alerts complex type�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
 * 
 * <pre>
 * &lt;complexType name="Alerts">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="alertMessages" type="{http://api.rews.mainconcept.com/xsd}AlertMessage" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="healthMessage_Error_WrongAPIVersion" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Alerts", namespace = "http://api.rews.mainconcept.com/xsd", propOrder = {
    "alertMessages",
    "healthMessageErrorWrongAPIVersion"
})
@XmlSeeAlso({
    User.class
})
public class Alerts {

    @XmlElement(nillable = true)
    protected List<AlertMessage> alertMessages;
    @XmlElement(name = "healthMessage_Error_WrongAPIVersion", nillable = true)
    protected String healthMessageErrorWrongAPIVersion;

    /**
     * Gets the value of the alertMessages property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the alertMessages property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAlertMessages().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link AlertMessage }
     * 
     * 
     */
    public List<AlertMessage> getAlertMessages() {
        if (alertMessages == null) {
            alertMessages = new ArrayList<AlertMessage>();
        }
        return this.alertMessages;
    }

    /**
     * ��ȡhealthMessageErrorWrongAPIVersion���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHealthMessageErrorWrongAPIVersion() {
        return healthMessageErrorWrongAPIVersion;
    }

    /**
     * ����healthMessageErrorWrongAPIVersion���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHealthMessageErrorWrongAPIVersion(String value) {
        this.healthMessageErrorWrongAPIVersion = value;
    }

}
