
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
 *         &lt;element name="newGroup" type="{http://api.rews.mainconcept.com/xsd}UserGroup" minOccurs="0"/>
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
    "newGroup"
})
@XmlRootElement(name = "addUserGroup")
public class AddUserGroup {

    @XmlElement(nillable = true)
    protected UserGroup newGroup;

    /**
     * ��ȡnewGroup���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link UserGroup }
     *     
     */
    public UserGroup getNewGroup() {
        return newGroup;
    }

    /**
     * ����newGroup���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link UserGroup }
     *     
     */
    public void setNewGroup(UserGroup value) {
        this.newGroup = value;
    }

}
