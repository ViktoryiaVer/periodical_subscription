package com.periodical_subscription.model.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
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
    @Column(name = "type_id")
    private Type type;
    @Column(name = "category_id")
    private Category category;
    @Column(name = "status_id")
    private Status status;
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "periodical", cascade = CascadeType.REFRESH)
    private List<SubscriptionDetail> subscriptions;

    @Getter
    public enum Type {
        MAGAZINE(1),
        NEWSPAPER(2),
        JOURNAL(3);

        private final Integer id;

        Type(Integer id) {
            this.id = id;
        }
    }

    @Getter
    public enum Status {
        AVAILABLE(1),
        UNAVAILABLE(2);

        private final Integer id;

        Status(Integer id) {
            this.id = id;
        }
    }

    @Getter
    public enum Category {
        ART_AND_ARCHITECTURE(1),
        SCIENCE(2),
        BUSINESS_AND_FINANCE(3),
        NEWS_AND_POLITICS(4),
        CULTURE_AND_LITERATURE(5),
        TRAVEL_AND_OUTDOOR(6);

        private final Integer id;

        Category(Integer id) {
            this.id = id;
        }
    }

}
