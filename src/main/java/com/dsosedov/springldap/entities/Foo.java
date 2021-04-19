package com.dsosedov.springldap.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import java.util.Date;

@Data
@Entity
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Foo {

    public Foo(String val) {
        this.val = val;
    }

    @Id
    private String val;

    @CreatedDate
    private Date created;

    @CreatedBy
    private String created_by;

    @LastModifiedDate
    private Date modified;

    @LastModifiedBy
    private String modified_by;

}
