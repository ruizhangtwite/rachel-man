//
// 此文件是由 JavaTM Architecture for XML Binding (JAXB) 引用实现 v2.2.8-b130911.1802 生成的
// 请访问 <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// 在重新编译源模式时, 对此文件的所有修改都将丢失。
// 生成时间: 2018.06.15 时间 06:58:37 PM CST 
//


package com.zhangrui.webservices.domain;

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
 *         &lt;element name="status" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="human" type="{http://com.zhangrui/com.zhangrui.webservices}Human"/>
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
    "status",
    "human"
})
@XmlRootElement(name = "HumanResponse")
public class HumanResponse {

    protected boolean status;
    @XmlElement(required = true)
    protected Human human;

    /**
     * 获取status属性的值。
     * 
     */
    public boolean isStatus() {
        return status;
    }

    /**
     * 设置status属性的值。
     * 
     */
    public void setStatus(boolean value) {
        this.status = value;
    }

    /**
     * 获取human属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Human }
     *     
     */
    public Human getHuman() {
        return human;
    }

    /**
     * 设置human属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Human }
     *     
     */
    public void setHuman(Human value) {
        this.human = value;
    }

    @Override
    public String toString() {
        return "HumanResponse{" +
                "human=" + human +
                ", status=" + status +
                '}';
    }
}
