package com.periodicalsubscription.model.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Class describing the object Periodical
 */
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = "periodicals")
@SQLDelete(sql = "UPDATE periodicals SET deleted = true WHERE id=?")
@Where(clause = "deleted = false")
public class Periodical {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String title;
    @Column
    private String publisher;
    @Column
    private String description;
    @DateTimeFormat(pattern = "[YYYY-MM-dd]")
    @Column(name = "publication_date")
    private LocalDate publicationDate;
    @Column(name = "issues_amount_in_year")
    private Integer issuesAmountInYear;
    @Column
    private BigDecimal price;
    @Column
    private String language;
    @Column(name = "image")
    private String imagePath;
    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private Type type;
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;
    @ToString.Exclude
    @OneToMany(mappedBy = "periodical", cascade = CascadeType.ALL)
    private List<PeriodicalCategory> categories = new ArrayList<>();

    public void addCategory(PeriodicalCategory category) {
        categories.add(category);
        category.setPeriodical(this);
    }

    public enum Type {
        MAGAZINE,
        NEWSPAPER,
        JOURNAL
    }

    public enum Status {
        AVAILABLE,
        UNAVAILABLE
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Periodical that = (Periodical) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
