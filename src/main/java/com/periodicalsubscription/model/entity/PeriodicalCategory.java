package com.periodicalsubscription.model.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "periodicals_categories")
public class PeriodicalCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "periodical_id")
    private Periodical periodical;
    @Enumerated(EnumType.STRING)
    @Column(name = "category")
    private Category category;

    public enum Category {
        ART_AND_ARCHITECTURE,
        SCIENCE,
        BUSINESS_AND_FINANCE,
        NEWS_AND_POLITICS,
        CULTURE_AND_LITERATURE,
        TRAVEL_AND_OUTDOOR
    }
}
