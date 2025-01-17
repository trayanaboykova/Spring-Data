package org.mvc_project.service.model.imports;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import javax.xml.bind.annotation.XmlAttribute;

@JacksonXmlRootElement(localName = "company")
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class CompanyImportModel {

    @JacksonXmlProperty(localName = "name", isAttribute = true)
    private String name;

    public CompanyImportModel() {
    }

    public CompanyImportModel(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
