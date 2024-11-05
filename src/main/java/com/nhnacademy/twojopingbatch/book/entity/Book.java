package com.nhnacademy.twojopingbatch.book.entity;

import com.nhnacademy.twojopingbatch.book.category.entity.BookCategory;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;


/**
 * Book 엔티티 클래스
 * 책 정보를 관리하는 클래스입니다. 책의 고유 ID를 포함하고 있으며, 여러 카테고리와의 관계를
 * BookCategory 조인 테이블을 통해 관리할 수 있습니다.
 *
 * @since 1.0
 * @author Luha
 */
@Entity
@Table(name = "book")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Book {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long bookId;

        @OneToMany(mappedBy = "book")
        private List<BookCategory> bookCategories;
}