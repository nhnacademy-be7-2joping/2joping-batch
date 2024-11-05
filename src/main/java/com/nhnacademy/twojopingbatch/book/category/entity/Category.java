package com.nhnacademy.twojopingbatch.book.category.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Category 엔티티 클래스
 * 카테고리 정보를 관리하는 클래스입니다. 각 카테고리는 상위 카테고리를 참조할 수 있는 자기 참조 관계를 가지며,
 * 이를 통해 계층형 카테고리 구조를 구성할 수 있습니다. 각 카테고리는 고유 ID와 이름, 활성 상태를 포함합니다.
 *
 * @author Luha
 * @since 1.0
 */
@Entity
@Table(name = "category")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Category {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long categoryId;

        @Column(length = 50)
        private String name;

        private Boolean isActive;

        @ManyToOne
        @JoinColumn(name = "subcategory_id")
        private Category parentCategory;

        @OneToMany(mappedBy = "parentCategory")
        private List<Category> subCategories;
}