
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
 *         &lt;element name="userGroup" type="{http://api.rews.mainconcept.com/xsd}UserGroup" minOccurs="0"/>
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
    "userGroup"
})
@XmlRootElement(name = "updateUserGroup")
public class UpdateUserGroup {

    @XmlElement(nillable = true)
    protected UserGroup userGroup;

    /**
     * ��ȡuserGroup���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link UserGroup }
     *     
     */
    public UserGroup getUserGroup() {
        return userGroup;
    }

    /**
     * ����userGroup���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link UserGroup }
     *     
     */
    public void setUserGroup(UserGroup value) {
        this.userGroup = value;
    }

}
