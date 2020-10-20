package com.example.bookinventory.model;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "USERBOOKS")
public class UserBooks {

    @Id
    @GeneratedValue
    private int id;

    @ManyToOne
    @JoinColumn(name = "userId")
    private UserRoles userId;

    @ManyToOne
    @JoinColumn(name = "bookId")
    private Books bookId;

    @Column(name = "isReturned")
    private boolean isReturned;

    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    private Date createdDate;

    @Temporal(TemporalType.TIMESTAMP)
    @LastModifiedDate
    private Date modifiedDate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public UserRoles getUserId() {
        return userId;
    }

    public void setUserId(UserRoles userId) {
        this.userId = userId;
    }

    public Books getBookId() {
        return bookId;
    }

    public void setBookId(Books bookId) {
        this.bookId = bookId;
    }

    public boolean isReturned() {
        return isReturned;
    }

    public void setReturned(boolean returned) {
        isReturned = returned;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate;
    }
}
