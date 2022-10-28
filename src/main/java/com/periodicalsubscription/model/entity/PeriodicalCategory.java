package com.periodicalsubscription.model.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.Objects;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = "periodicals_categories")
public class PeriodicalCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(cascade =  CascadeType.REFRESH, fetch = FetchType.LAZY)
    @JoinColumn(name = "periodical_id")
    @ToString.Exclude
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        PeriodicalCategory periodicalCategory = (PeriodicalCategory) o;
        return id != null && Objects.equals(id, periodicalCategory.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
