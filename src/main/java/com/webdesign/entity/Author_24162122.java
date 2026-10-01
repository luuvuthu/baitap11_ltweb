package com.webdesign.entity;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "author")
public class Author_24162122 implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "author_id")
    private Integer authorId;

    @Column(name = "author_name", length = 100)
    private String authorName;

    @Column(name = "date_of_birth")
    @Temporal(TemporalType.DATE)
    private Date dateOfBirth;

    @ManyToMany(mappedBy = "authors", fetch = FetchType.LAZY)
    private Set<Books_24162122> books = new HashSet<>();

    public Author_24162122() {
    }

    public Integer getAuthorId() { return authorId; }
    public void setAuthorId(Integer authorId) { this.authorId = authorId; }

    public String getAuthorName() { return authorName; }
    public void setAuthorName(String authorName) { this.authorName = authorName; }

    public Date getDateOfBirth() { return dateOfBirth; }
    public void setDateOfBirth(Date dateOfBirth) { this.dateOfBirth = dateOfBirth; }

    public Set<Books_24162122> getBooks() { return books; }
    public void setBooks(Set<Books_24162122> books) { this.books = books; }
}
