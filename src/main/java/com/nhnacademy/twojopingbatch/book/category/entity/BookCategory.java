package com.nhnacademy.twojopingbatch.book.category.entity;

import com.nhnacademy.twojopingbatch.book.entity.Book;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * BookCategory 엔티티 클래스
 * 책(Book)과 카테고리(Category) 간의 다대다 관계를 나타내는 조인 테이블입니다.
 * 각 책과 카테고리의 관계를 복합 키(BookCategoryId)로 구성하여 고유성을 보장합니다.
 *
 * @author Luha
 * @since 1.0
 */
@Entity
@Table(name = "book_category")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BookCategory {

    @EmbeddedId
    private BookCategoryId id;

    @ManyToOne
    @MapsId("bookId")
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;

    @ManyToOne
    @MapsId("categoryId")
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    // test

}