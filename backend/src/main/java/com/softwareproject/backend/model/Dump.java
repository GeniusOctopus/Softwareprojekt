package com.softwareproject.backend.model;

import com.google.common.base.Objects;
import org.jsondoc.core.annotation.ApiObject;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@ApiObject(name = "Dump", description = "Ist nur ein Beispiel")
@Entity
public class Dump {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Id;
    private String testAttribute;

    public Dump() {

    }

    public Dump(int id, String testAttribute) {
        Id = id;
        this.testAttribute = testAttribute;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getTestAttribute() {
        return testAttribute;
    }

    public void setTestAttribute(String testAttribute) {
        this.testAttribute = testAttribute;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Dump dump = (Dump) o;
        return Id == dump.Id && Objects.equal(testAttribute, dump.testAttribute);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(Id, testAttribute);
    }
}
