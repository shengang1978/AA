
package com.mainconcept.rewsservice;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>DirList complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="DirList">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="childs" type="{http://api.rews.mainconcept.com/xsd}DirList" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="isAnalyzed" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="isDirectory" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="path" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="pathRelative" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="size" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DirList", namespace = "http://api.rews.mainconcept.com/xsd", propOrder = {
    "childs",
    "isAnalyzed",
    "isDirectory",
    "name",
    "path",
    "pathRelative",
    "size"
})
public class DirList {

    @XmlElement(nillable = true)
    protected List<DirList> childs;
    @XmlElement(nillable = true)
    protected Boolean isAnalyzed;
    @XmlElement(nillable = true)
    protected Boolean isDirectory;
    @XmlElement(nillable = true)
    protected String name;
    @XmlElement(nillable = true)
    protected String path;
    @XmlElement(nillable = true)
    protected String pathRelative;
    @XmlElement(nillable = true)
    protected Long size;

    /**
     * Gets the value of the childs property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the childs property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getChilds().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DirList }
     * 
     * 
     */
    public List<DirList> getChilds() {
        if (childs == null) {
            childs = new ArrayList<DirList>();
        }
        return this.childs;
    }

    /**
     * 获取isAnalyzed属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIsAnalyzed() {
        return isAnalyzed;
    }

    /**
     * 设置isAnalyzed属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIsAnalyzed(Boolean value) {
        this.isAnalyzed = value;
    }

    /**
     * 获取isDirectory属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIsDirectory() {
        return isDirectory;
    }

    /**
     * 设置isDirectory属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIsDirectory(Boolean value) {
        this.isDirectory = value;
    }

    /**
     * 获取name属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getName() {
        return name;
    }

    /**
     * 设置name属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * 获取path属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPath() {
        return path;
    }

    /**
     * 设置path属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPath(String value) {
        this.path = value;
    }

    /**
     * 获取pathRelative属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPathRelative() {
        return pathRelative;
    }

    /**
     * 设置pathRelative属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPathRelative(String value) {
        this.pathRelative = value;
    }

    /**
     * 获取size属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getSize() {
        return size;
    }

    /**
     * 设置size属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setSize(Long value) {
        this.size = value;
    }

}
