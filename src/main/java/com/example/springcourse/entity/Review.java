package com.example.springcourse.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

@Entity
@Table(name = "review")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;


    @Column(name = "comment")
    private String comment;


    @Column(name = "date")
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate createdDate;

    @PrePersist
    protected void onCreate() {
        this.createdDate = LocalDate.now();
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "person_id", referencedColumnName = "id")
    @JsonBackReference
    Person person;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id", referencedColumnName = "id")
    @JsonBackReference("book-reviews")
    Book book;

}
