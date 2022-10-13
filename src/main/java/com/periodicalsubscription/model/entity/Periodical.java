package com.periodicalsubscription.model.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
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
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "periodical", cascade =CascadeType.ALL, fetch = FetchType.EAGER)
    private List<PeriodicalCategory> categories;
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "periodical", cascade = CascadeType.REFRESH)
    private List<SubscriptionDetail> subscriptions;

    public enum Type {
        MAGAZINE,
        NEWSPAPER,
        JOURNAL
    }

    public enum Status {
        AVAILABLE,
        UNAVAILABLE
    }

}
