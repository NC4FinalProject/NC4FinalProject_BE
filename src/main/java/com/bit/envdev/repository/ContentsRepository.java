package com.bit.envdev.repository;


import com.bit.envdev.dto.ContentsDTO;
import com.bit.envdev.entity.Contents;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface ContentsRepository extends JpaRepository<Contents, Integer>, ContentsRepositoryCustom{
    List<Contents> findTop4ByOrderByRegDateDesc();

    Page<Contents> findByContentsTitleContaining(Pageable pageable, String searchKeyword);

    Page<Contents> findByCategoryAndContentsTitleContaining(Pageable pageable, String searchCondition, String searchKeyword);

    List<Contents> findTop12ByOrderByRegDateDesc();

    @Query(value = "SELECT * FROM contents ORDER BY RAND() LIMIT 12", nativeQuery = true)
    List<Contents> findTop12ByOrderByIdAsc();

    @Query(value = "SELECT A.contents_id, A.category, A.contents_title, A.introduce, A.mod_date, A.price, A.price_type, A.reg_date, A.thumbnail, A.member_id\n" +
            "     , IFNULL(B.REVIEW_RATING, 0) AS REVIEW_RATING\n" +
            "     , IFNULL(B.REVIEW_COUNT, 0) AS REVIEW_COUNT\n" +
            "    FROM Contents A\n" +
            "    LEFT JOIN (\n" +
            "                SELECT C.CONTENTS_ID\n" +
            "                     , AVG(C.REVIEW_RATING) AS REVIEW_RATING\n" +
            "                     , COUNT(C.CONTENTS_ID) AS REVIEW_COUNT\n" +
            "                    FROM REVIEW C\n" +
            "                    GROUP BY C.CONTENTS_ID\n" +
            "    ) B\n" +
            "    ON A.CONTENTS_ID = B.CONTENTS_ID\n" +
            "    WHERE A.price = 0",
            countQuery = "SELECT count(*) " +
                    "FROM (" +
                    "   SELECT A.contents_id, A.category, A.contents_title, A.introduce, A.mod_date, A.price, A.price_type, A.reg_date, A.thumbnail, A.member_id\n" +
                    "     , IFNULL(B.REVIEW_RATING, 0) AS REVIEW_RATING\n" +
                    "     , IFNULL(B.REVIEW_COUNT, 0) AS REVIEW_COUNT\n" +
                    "    FROM Contents A\n" +
                    "    LEFT JOIN (\n" +
                    "                SELECT C.CONTENTS_ID\n" +
                    "                     , AVG(C.REVIEW_RATING) AS REVIEW_RATING\n" +
                    "                     , COUNT(C.CONTENTS_ID) AS REVIEW_COUNT\n" +
                    "                    FROM REVIEW C\n" +
                    "                    GROUP BY C.CONTENTS_ID\n" +
                    "    ) B\n" +
                    "    ON A.CONTENTS_ID = B.CONTENTS_ID\n" +
                    "    WHERE A.price = 0" +
                    ") D",nativeQuery = true)
    Page<Contents> searchAllFree(Pageable pageable);

    @Query(value = "SELECT A.contents_id, A.category, A.contents_title, A.introduce, A.mod_date, A.price, A.price_type, A.reg_date, A.thumbnail, A.member_id\n" +
            "     , IFNULL(B.REVIEW_RATING, 0) AS REVIEW_RATING\n" +
            "     , IFNULL(B.REVIEW_COUNT, 0) AS REVIEW_COUNT\n" +
            "    FROM Contents A\n" +
            "    LEFT JOIN (\n" +
            "                SELECT C.CONTENTS_ID\n" +
            "                     , AVG(C.REVIEW_RATING) AS REVIEW_RATING\n" +
            "                     , COUNT(C.CONTENTS_ID) AS REVIEW_COUNT\n" +
            "                    FROM REVIEW C\n" +
            "                    GROUP BY C.CONTENTS_ID\n" +
            "    ) B\n" +
            "    ON A.CONTENTS_ID = B.CONTENTS_ID\n" +
            "    WHERE A.price != 0" +
            "      AND A.PRICE != -1",
            countQuery = "SELECT count(*) " +
                    "FROM (" +
                    "   SELECT A.contents_id, A.category, A.contents_title, A.introduce, A.mod_date, A.price, A.price_type, A.reg_date, A.thumbnail, A.member_id\n" +
                    "     , IFNULL(B.REVIEW_RATING, 0) AS REVIEW_RATING\n" +
                    "     , IFNULL(B.REVIEW_COUNT, 0) AS REVIEW_COUNT\n" +
                    "    FROM Contents A\n" +
                    "    LEFT JOIN (\n" +
                    "                SELECT C.CONTENTS_ID\n" +
                    "                     , AVG(C.REVIEW_RATING) AS REVIEW_RATING\n" +
                    "                     , COUNT(C.CONTENTS_ID) AS REVIEW_COUNT\n" +
                    "                    FROM REVIEW C\n" +
                    "                    GROUP BY C.CONTENTS_ID\n" +
                    "    ) B\n" +
                    "    ON A.CONTENTS_ID = B.CONTENTS_ID\n" +
                    "    WHERE A.price != 0" +
                    "      AND A.PRICE != -1" +
                    ") D",nativeQuery = true)
    Page<Contents> searchAllPay(Pageable pageable);

    @Query(value = "SELECT A.contents_id, A.category, A.contents_title, A.introduce, A.mod_date, A.price, A.price_type, A.reg_date, A.thumbnail, A.member_id\n" +
            "     , IFNULL(B.REVIEW_RATING, 0) AS REVIEW_RATING\n" +
            "     , IFNULL(B.REVIEW_COUNT, 0) AS REVIEW_COUNT\n" +
            "    FROM Contents A\n" +
            "    LEFT JOIN (\n" +
            "                SELECT C.CONTENTS_ID\n" +
            "                     , AVG(C.REVIEW_RATING) AS REVIEW_RATING\n" +
            "                     , COUNT(C.CONTENTS_ID) AS REVIEW_COUNT\n" +
            "                    FROM REVIEW C\n" +
            "                    GROUP BY C.CONTENTS_ID\n" +
            "    ) B\n" +
            "    ON A.CONTENTS_ID = B.CONTENTS_ID\n" +
            "    WHERE A.price = -1",
            countQuery = "SELECT count(*) " +
                    "FROM (" +
                    "   SELECT A.contents_id, A.category, A.contents_title, A.introduce, A.mod_date, A.price, A.price_type, A.reg_date, A.thumbnail, A.member_id\n" +
                    "     , IFNULL(B.REVIEW_RATING, 0) AS REVIEW_RATING\n" +
                    "     , IFNULL(B.REVIEW_COUNT, 0) AS REVIEW_COUNT\n" +
                    "    FROM Contents A\n" +
                    "    LEFT JOIN (\n" +
                    "                SELECT C.CONTENTS_ID\n" +
                    "                     , AVG(C.REVIEW_RATING) AS REVIEW_RATING\n" +
                    "                     , COUNT(C.CONTENTS_ID) AS REVIEW_COUNT\n" +
                    "                    FROM REVIEW C\n" +
                    "                    GROUP BY C.CONTENTS_ID\n" +
                    "    ) B\n" +
                    "    ON A.CONTENTS_ID = B.CONTENTS_ID\n" +
                    "    WHERE A.price = -1" +
                    ") D",nativeQuery = true)
    Page<Contents> searchAllNational(Pageable pageable);

    @Query(value = "SELECT A.contents_id, A.category, A.contents_title, A.introduce, A.mod_date, A.price, A.price_type, A.reg_date, A.thumbnail, A.member_id\n" +
            "     , IFNULL(B.REVIEW_RATING, 0) AS REVIEW_RATING\n" +
            "     , IFNULL(B.REVIEW_COUNT, 0) AS REVIEW_COUNT\n" +
            "    FROM Contents A\n" +
            "    LEFT JOIN (\n" +
            "                SELECT C.CONTENTS_ID\n" +
            "                     , AVG(C.REVIEW_RATING) AS REVIEW_RATING\n" +
            "                     , COUNT(C.CONTENTS_ID) AS REVIEW_COUNT\n" +
            "                    FROM REVIEW C\n" +
            "                    GROUP BY C.CONTENTS_ID\n" +
            "    ) B\n" +
            "    ON A.CONTENTS_ID = B.CONTENTS_ID\n" +
            "    WHERE A.category = :category",
            countQuery = "SELECT count(*) " +
                    "FROM (" +
                    "   SELECT A.contents_id, A.category, A.contents_title, A.introduce, A.mod_date, A.price, A.price_type, A.reg_date, A.thumbnail, A.member_id\n" +
                    "     , IFNULL(B.REVIEW_RATING, 0) AS REVIEW_RATING\n" +
                    "     , IFNULL(B.REVIEW_COUNT, 0) AS REVIEW_COUNT\n" +
                    "    FROM Contents A\n" +
                    "    LEFT JOIN (\n" +
                    "                SELECT C.CONTENTS_ID\n" +
                    "                     , AVG(C.REVIEW_RATING) AS REVIEW_RATING\n" +
                    "                     , COUNT(C.CONTENTS_ID) AS REVIEW_COUNT\n" +
                    "                    FROM REVIEW C\n" +
                    "                    GROUP BY C.CONTENTS_ID\n" +
                    "    ) B\n" +
                    "    ON A.CONTENTS_ID = B.CONTENTS_ID\n" +
                    "    WHERE A.category = :category" +
                    ") D", nativeQuery = true)
    Page<Contents> searchAllCategory(Pageable pageable, String category);

    @Query(value = "SELECT A.contents_id, A.category, A.contents_title, A.introduce, A.mod_date, A.price, A.price_type, A.reg_date, A.thumbnail, A.member_id\n" +
            "     , IFNULL(B.REVIEW_RATING, 0) AS REVIEW_RATING\n" +
            "     , IFNULL(B.REVIEW_COUNT, 0) AS REVIEW_COUNT\n" +
            "    FROM Contents A\n" +
            "    LEFT JOIN (\n" +
            "                SELECT C.CONTENTS_ID\n" +
            "                     , AVG(C.REVIEW_RATING) AS REVIEW_RATING\n" +
            "                     , COUNT(C.CONTENTS_ID) AS REVIEW_COUNT\n" +
            "                    FROM REVIEW C\n" +
            "                    GROUP BY C.CONTENTS_ID\n" +
            "    ) B\n" +
            "    ON A.CONTENTS_ID = B.CONTENTS_ID\n" +
            "    WHERE A.category = :category" +
            "      AND A.price = 0",
            countQuery = "SELECT count(*) " +
                    "FROM (" +
                    "   SELECT A.contents_id, A.category, A.contents_title, A.introduce, A.mod_date, A.price, A.price_type, A.reg_date, A.thumbnail, A.member_id\n" +
                    "     , IFNULL(B.REVIEW_RATING, 0) AS REVIEW_RATING\n" +
                    "     , IFNULL(B.REVIEW_COUNT, 0) AS REVIEW_COUNT\n" +
                    "    FROM Contents A\n" +
                    "    LEFT JOIN (\n" +
                    "                SELECT C.CONTENTS_ID\n" +
                    "                     , AVG(C.REVIEW_RATING) AS REVIEW_RATING\n" +
                    "                     , COUNT(C.CONTENTS_ID) AS REVIEW_COUNT\n" +
                    "                    FROM REVIEW C\n" +
                    "                    GROUP BY C.CONTENTS_ID\n" +
                    "    ) B\n" +
                    "    ON A.CONTENTS_ID = B.CONTENTS_ID\n" +
                    "    WHERE A.category = :category" +
                    "      AND A.price = 0" +
                    ") D", nativeQuery = true)
    Page<Contents> searchAllCategoryFree(Pageable pageable, String category);

    @Query(value = "SELECT A.contents_id, A.category, A.contents_title, A.introduce, A.mod_date, A.price, A.price_type, A.reg_date, A.thumbnail, A.member_id\n" +
            "     , IFNULL(B.REVIEW_RATING, 0) AS REVIEW_RATING\n" +
            "     , IFNULL(B.REVIEW_COUNT, 0) AS REVIEW_COUNT\n" +
            "    FROM Contents A\n" +
            "    LEFT JOIN (\n" +
            "                SELECT C.CONTENTS_ID\n" +
            "                     , AVG(C.REVIEW_RATING) AS REVIEW_RATING\n" +
            "                     , COUNT(C.CONTENTS_ID) AS REVIEW_COUNT\n" +
            "                    FROM REVIEW C\n" +
            "                    GROUP BY C.CONTENTS_ID\n" +
            "    ) B\n" +
            "    ON A.CONTENTS_ID = B.CONTENTS_ID\n" +
            "    WHERE A.category = :category" +
            "      AND A.price != 0" +
            "      AND A.price != -1",
            countQuery = "SELECT count(*) " +
                    "FROM (" +
                    "   SELECT A.contents_id, A.category, A.contents_title, A.introduce, A.mod_date, A.price, A.price_type, A.reg_date, A.thumbnail, A.member_id\n" +
                    "     , IFNULL(B.REVIEW_RATING, 0) AS REVIEW_RATING\n" +
                    "     , IFNULL(B.REVIEW_COUNT, 0) AS REVIEW_COUNT\n" +
                    "    FROM Contents A\n" +
                    "    LEFT JOIN (\n" +
                    "                SELECT C.CONTENTS_ID\n" +
                    "                     , AVG(C.REVIEW_RATING) AS REVIEW_RATING\n" +
                    "                     , COUNT(C.CONTENTS_ID) AS REVIEW_COUNT\n" +
                    "                    FROM REVIEW C\n" +
                    "                    GROUP BY C.CONTENTS_ID\n" +
                    "    ) B\n" +
                    "    ON A.CONTENTS_ID = B.CONTENTS_ID\n" +
                    "    WHERE A.category = :category" +
                    "      AND A.price != 0" +
                    "      AND A.price != -1" +
                    ") D", nativeQuery = true)
    Page<Contents> searchAllCategoryPay(Pageable pageable, String category);

    @Query(value = "SELECT A.contents_id, A.category, A.contents_title, A.introduce, A.mod_date, A.price, A.price_type, A.reg_date, A.thumbnail, A.member_id\n" +
            "     , IFNULL(B.REVIEW_RATING, 0) AS REVIEW_RATING\n" +
            "     , IFNULL(B.REVIEW_COUNT, 0) AS REVIEW_COUNT\n" +
            "    FROM Contents A\n" +
            "    LEFT JOIN (\n" +
            "                SELECT C.CONTENTS_ID\n" +
            "                     , AVG(C.REVIEW_RATING) AS REVIEW_RATING\n" +
            "                     , COUNT(C.CONTENTS_ID) AS REVIEW_COUNT\n" +
            "                    FROM REVIEW C\n" +
            "                    GROUP BY C.CONTENTS_ID\n" +
            "    ) B\n" +
            "    ON A.CONTENTS_ID = B.CONTENTS_ID\n" +
            "    WHERE A.category = :category" +
            "      AND A.price = -1",
            countQuery = "SELECT count(*) " +
                    "FROM (" +
                    "   SELECT A.contents_id, A.category, A.contents_title, A.introduce, A.mod_date, A.price, A.price_type, A.reg_date, A.thumbnail, A.member_id\n" +
                    "     , IFNULL(B.REVIEW_RATING, 0) AS REVIEW_RATING\n" +
                    "     , IFNULL(B.REVIEW_COUNT, 0) AS REVIEW_COUNT\n" +
                    "    FROM Contents A\n" +
                    "    LEFT JOIN (\n" +
                    "                SELECT C.CONTENTS_ID\n" +
                    "                     , AVG(C.REVIEW_RATING) AS REVIEW_RATING\n" +
                    "                     , COUNT(C.CONTENTS_ID) AS REVIEW_COUNT\n" +
                    "                    FROM REVIEW C\n" +
                    "                    GROUP BY C.CONTENTS_ID\n" +
                    "    ) B\n" +
                    "    ON A.CONTENTS_ID = B.CONTENTS_ID\n" +
                    "    WHERE A.category = :category" +
                    "      AND A.price = -1" +
                    ") D", nativeQuery = true)
    Page<Contents> searchAllCategoryNational(Pageable pageable, String category);

    @Query(value = "SELECT A.contents_id, A.category, A.contents_title, A.introduce, A.mod_date, A.price, A.price_type, A.reg_date, A.thumbnail, A.member_id\n" +
            "     , IFNULL(B.REVIEW_RATING, 0) AS REVIEW_RATING\n" +
            "     , IFNULL(B.REVIEW_COUNT, 0) AS REVIEW_COUNT\n" +
            "    FROM Contents A\n" +
            "    LEFT JOIN (\n" +
            "                SELECT C.CONTENTS_ID\n" +
            "                     , AVG(C.REVIEW_RATING) AS REVIEW_RATING\n" +
            "                     , COUNT(C.CONTENTS_ID) AS REVIEW_COUNT\n" +
            "                    FROM REVIEW C\n" +
            "                    GROUP BY C.CONTENTS_ID\n" +
            "    ) B\n" +
            "    ON A.CONTENTS_ID = B.CONTENTS_ID\n" +
            "    WHERE A.price = 0" +
            "    ORDER BY A.reg_date DESC",
            countQuery = "SELECT count(*) " +
                    "FROM (" +
                    "   SELECT A.contents_id, A.category, A.contents_title, A.introduce, A.mod_date, A.price, A.price_type, A.reg_date, A.thumbnail, A.member_id\n" +
                    "     , IFNULL(B.REVIEW_RATING, 0) AS REVIEW_RATING\n" +
                    "     , IFNULL(B.REVIEW_COUNT, 0) AS REVIEW_COUNT\n" +
                    "    FROM Contents A\n" +
                    "    LEFT JOIN (\n" +
                    "                SELECT C.CONTENTS_ID\n" +
                    "                     , AVG(C.REVIEW_RATING) AS REVIEW_RATING\n" +
                    "                     , COUNT(C.CONTENTS_ID) AS REVIEW_COUNT\n" +
                    "                    FROM REVIEW C\n" +
                    "                    GROUP BY C.CONTENTS_ID\n" +
                    "    ) B\n" +
                    "    ON A.CONTENTS_ID = B.CONTENTS_ID\n" +
                    "    WHERE A.price = 0" +
                    "    ORDER BY A.reg_date DESC" +
                    ") D",nativeQuery = true)
    Page<Contents> searchAllFreeReg(Pageable pageable);

    @Query(value = "SELECT A.contents_id, A.category, A.contents_title, A.introduce, A.mod_date, A.price, A.price_type, A.reg_date, A.thumbnail, A.member_id\n" +
            "     , IFNULL(B.REVIEW_RATING, 0) AS REVIEW_RATING\n" +
            "     , IFNULL(B.REVIEW_COUNT, 0) AS REVIEW_COUNT\n" +
            "    FROM Contents A\n" +
            "    LEFT JOIN (\n" +
            "                SELECT C.CONTENTS_ID\n" +
            "                     , AVG(C.REVIEW_RATING) AS REVIEW_RATING\n" +
            "                     , COUNT(C.CONTENTS_ID) AS REVIEW_COUNT\n" +
            "                    FROM REVIEW C\n" +
            "                    GROUP BY C.CONTENTS_ID\n" +
            "    ) B\n" +
            "    ON A.CONTENTS_ID = B.CONTENTS_ID\n" +
            "    WHERE A.price != 0" +
            "      AND A.price != -1" +
            "    ORDER BY A.reg_date DESC",
            countQuery = "SELECT count(*) " +
                    "FROM (" +
                    "   SELECT A.contents_id, A.category, A.contents_title, A.introduce, A.mod_date, A.price, A.price_type, A.reg_date, A.thumbnail, A.member_id\n" +
                    "     , IFNULL(B.REVIEW_RATING, 0) AS REVIEW_RATING\n" +
                    "     , IFNULL(B.REVIEW_COUNT, 0) AS REVIEW_COUNT\n" +
                    "    FROM Contents A\n" +
                    "    LEFT JOIN (\n" +
                    "                SELECT C.CONTENTS_ID\n" +
                    "                     , AVG(C.REVIEW_RATING) AS REVIEW_RATING\n" +
                    "                     , COUNT(C.CONTENTS_ID) AS REVIEW_COUNT\n" +
                    "                    FROM REVIEW C\n" +
                    "                    GROUP BY C.CONTENTS_ID\n" +
                    "    ) B\n" +
                    "    ON A.CONTENTS_ID = B.CONTENTS_ID\n" +
                    "    WHERE A.price != 0" +
                    "      AND A.price != -1" +
                    "    ORDER BY A.reg_date DESC" +
                    ") D",nativeQuery = true)
    Page<Contents> searchAllPayReg(Pageable pageable);

    @Query(value = "SELECT A.contents_id, A.category, A.contents_title, A.introduce, A.mod_date, A.price, A.price_type, A.reg_date, A.thumbnail, A.member_id\n" +
            "     , IFNULL(B.REVIEW_RATING, 0) AS REVIEW_RATING\n" +
            "     , IFNULL(B.REVIEW_COUNT, 0) AS REVIEW_COUNT\n" +
            "    FROM Contents A\n" +
            "    LEFT JOIN (\n" +
            "                SELECT C.CONTENTS_ID\n" +
            "                     , AVG(C.REVIEW_RATING) AS REVIEW_RATING\n" +
            "                     , COUNT(C.CONTENTS_ID) AS REVIEW_COUNT\n" +
            "                    FROM REVIEW C\n" +
            "                    GROUP BY C.CONTENTS_ID\n" +
            "    ) B\n" +
            "    ON A.CONTENTS_ID = B.CONTENTS_ID\n" +
            "    WHERE A.price = -1" +
            "    ORDER BY A.reg_date DESC",
            countQuery = "SELECT count(*) " +
                    "FROM (" +
                    "   SELECT A.contents_id, A.category, A.contents_title, A.introduce, A.mod_date, A.price, A.price_type, A.reg_date, A.thumbnail, A.member_id\n" +
                    "     , IFNULL(B.REVIEW_RATING, 0) AS REVIEW_RATING\n" +
                    "     , IFNULL(B.REVIEW_COUNT, 0) AS REVIEW_COUNT\n" +
                    "    FROM Contents A\n" +
                    "    LEFT JOIN (\n" +
                    "                SELECT C.CONTENTS_ID\n" +
                    "                     , AVG(C.REVIEW_RATING) AS REVIEW_RATING\n" +
                    "                     , COUNT(C.CONTENTS_ID) AS REVIEW_COUNT\n" +
                    "                    FROM REVIEW C\n" +
                    "                    GROUP BY C.CONTENTS_ID\n" +
                    "    ) B\n" +
                    "    ON A.CONTENTS_ID = B.CONTENTS_ID\n" +
                    "    WHERE A.price = -1" +
                    "    ORDER BY A.reg_date DESC" +
                    ") D",nativeQuery = true)
    Page<Contents> searchAllNationalReg(Pageable pageable);

    @Query(value = "SELECT A.contents_id, A.category, A.contents_title, A.introduce, A.mod_date, A.price, A.price_type, A.reg_date, A.thumbnail, A.member_id\n" +
            "     , IFNULL(B.REVIEW_RATING, 0) AS REVIEW_RATING\n" +
            "     , IFNULL(B.REVIEW_COUNT, 0) AS REVIEW_COUNT\n" +
            "    FROM Contents A\n" +
            "    LEFT JOIN (\n" +
            "                SELECT C.CONTENTS_ID\n" +
            "                     , AVG(C.REVIEW_RATING) AS REVIEW_RATING\n" +
            "                     , COUNT(C.CONTENTS_ID) AS REVIEW_COUNT\n" +
            "                    FROM REVIEW C\n" +
            "                    GROUP BY C.CONTENTS_ID\n" +
            "    ) B\n" +
            "    ON A.CONTENTS_ID = B.CONTENTS_ID\n" +
            "    WHERE A.category = :category" +
            "    ORDER BY A.reg_date DESC",
            countQuery = "SELECT count(*) " +
                    "FROM (" +
                    "   SELECT A.contents_id, A.category, A.contents_title, A.introduce, A.mod_date, A.price, A.price_type, A.reg_date, A.thumbnail, A.member_id\n" +
                    "     , IFNULL(B.REVIEW_RATING, 0) AS REVIEW_RATING\n" +
                    "     , IFNULL(B.REVIEW_COUNT, 0) AS REVIEW_COUNT\n" +
                    "    FROM Contents A\n" +
                    "    LEFT JOIN (\n" +
                    "                SELECT C.CONTENTS_ID\n" +
                    "                     , AVG(C.REVIEW_RATING) AS REVIEW_RATING\n" +
                    "                     , COUNT(C.CONTENTS_ID) AS REVIEW_COUNT\n" +
                    "                    FROM REVIEW C\n" +
                    "                    GROUP BY C.CONTENTS_ID\n" +
                    "    ) B\n" +
                    "    ON A.CONTENTS_ID = B.CONTENTS_ID\n" +
                    "    WHERE A.category = :category" +
                    "    ORDER BY A.reg_date DESC" +
                    ") D",nativeQuery = true)
    Page<Contents> searchAllCategoryReg(Pageable pageable, String category);

    @Query(value = "SSELECT A.contents_id, A.category, A.contents_title, A.introduce, A.mod_date, A.price, A.price_type, A.reg_date, A.thumbnail, A.member_id\n" +
            "     , IFNULL(B.REVIEW_RATING, 0) AS REVIEW_RATING\n" +
            "     , IFNULL(B.REVIEW_COUNT, 0) AS REVIEW_COUNT\n" +
            "    FROM Contents A\n" +
            "    LEFT JOIN (\n" +
            "                SELECT C.CONTENTS_ID\n" +
            "                     , AVG(C.REVIEW_RATING) AS REVIEW_RATING\n" +
            "                     , COUNT(C.CONTENTS_ID) AS REVIEW_COUNT\n" +
            "                    FROM REVIEW C\n" +
            "                    GROUP BY C.CONTENTS_ID\n" +
            "    ) B\n" +
            "    ON A.CONTENTS_ID = B.CONTENTS_ID\n" +
            "    WHERE A.category = :category" +
            "      AND A.price = 0" +
            "    ORDER BY A.reg_date DESC",
            countQuery = "SELECT count(*) " +
                    "FROM (" +
                    "   SELECT A.contents_id, A.category, A.contents_title, A.introduce, A.mod_date, A.price, A.price_type, A.reg_date, A.thumbnail, A.member_id\n" +
                    "     , IFNULL(B.REVIEW_RATING, 0) AS REVIEW_RATING\n" +
                    "     , IFNULL(B.REVIEW_COUNT, 0) AS REVIEW_COUNT\n" +
                    "    FROM Contents A\n" +
                    "    LEFT JOIN (\n" +
                    "                SELECT C.CONTENTS_ID\n" +
                    "                     , AVG(C.REVIEW_RATING) AS REVIEW_RATING\n" +
                    "                     , COUNT(C.CONTENTS_ID) AS REVIEW_COUNT\n" +
                    "                    FROM REVIEW C\n" +
                    "                    GROUP BY C.CONTENTS_ID\n" +
                    "    ) B\n" +
                    "    ON A.CONTENTS_ID = B.CONTENTS_ID\n" +
                    "    WHERE A.category = :category" +
                    "      AND A.price = 0" +
                    "    ORDER BY A.reg_date DESC" +
                    ") D", nativeQuery = true)
    Page<Contents> searchAllCategoryFreeReg(Pageable pageable, String category);

    @Query(value = "SELECT A.contents_id, A.category, A.contents_title, A.introduce, A.mod_date, A.price, A.price_type, A.reg_date, A.thumbnail, A.member_id\n" +
            "     , IFNULL(B.REVIEW_RATING, 0) AS REVIEW_RATING\n" +
            "     , IFNULL(B.REVIEW_COUNT, 0) AS REVIEW_COUNT\n" +
            "    FROM Contents A\n" +
            "    LEFT JOIN (\n" +
            "                SELECT C.CONTENTS_ID\n" +
            "                     , AVG(C.REVIEW_RATING) AS REVIEW_RATING\n" +
            "                     , COUNT(C.CONTENTS_ID) AS REVIEW_COUNT\n" +
            "                    FROM REVIEW C\n" +
            "                    GROUP BY C.CONTENTS_ID\n" +
            "    ) B\n" +
            "    ON A.CONTENTS_ID = B.CONTENTS_ID\n" +
            "    WHERE A.category = :category" +
            "      AND A.price != 0" +
            "      AND A.price != -1" +
            "    ORDER BY A.reg_date DESC",
            countQuery = "SELECT count(*) " +
                    "FROM (" +
                    "   SELECT A.contents_id, A.category, A.contents_title, A.introduce, A.mod_date, A.price, A.price_type, A.reg_date, A.thumbnail, A.member_id\n" +
                    "     , IFNULL(B.REVIEW_RATING, 0) AS REVIEW_RATING\n" +
                    "     , IFNULL(B.REVIEW_COUNT, 0) AS REVIEW_COUNT\n" +
                    "    FROM Contents A\n" +
                    "    LEFT JOIN (\n" +
                    "                SELECT C.CONTENTS_ID\n" +
                    "                     , AVG(C.REVIEW_RATING) AS REVIEW_RATING\n" +
                    "                     , COUNT(C.CONTENTS_ID) AS REVIEW_COUNT\n" +
                    "                    FROM REVIEW C\n" +
                    "                    GROUP BY C.CONTENTS_ID\n" +
                    "    ) B\n" +
                    "    ON A.CONTENTS_ID = B.CONTENTS_ID\n" +
                    "    WHERE A.category = :category" +
                    "      AND A.price != 0" +
                    "      AND A.price != -1" +
                    "    ORDER BY A.reg_date DESC" +
                    ") D", nativeQuery = true)
    Page<Contents> searchAllCategoryPayReg(Pageable pageable, String category);

    @Query(value = "SELECT A.contents_id, A.category, A.contents_title, A.introduce, A.mod_date, A.price, A.price_type, A.reg_date, A.thumbnail, A.member_id\n" +
            "     , IFNULL(B.REVIEW_RATING, 0) AS REVIEW_RATING\n" +
            "     , IFNULL(B.REVIEW_COUNT, 0) AS REVIEW_COUNT\n" +
            "    FROM Contents A\n" +
            "    LEFT JOIN (\n" +
            "                SELECT C.CONTENTS_ID\n" +
            "                     , AVG(C.REVIEW_RATING) AS REVIEW_RATING\n" +
            "                     , COUNT(C.CONTENTS_ID) AS REVIEW_COUNT\n" +
            "                    FROM REVIEW C\n" +
            "                    GROUP BY C.CONTENTS_ID\n" +
            "    ) B\n" +
            "    ON A.CONTENTS_ID = B.CONTENTS_ID\n" +
            "    WHERE A.category = :category" +
            "      AND A.price = -1" +
            "    ORDER BY A.reg_date DESC",
            countQuery = "SELECT count(*) " +
                    "FROM (" +
                    "   SELECT A.contents_id, A.category, A.contents_title, A.introduce, A.mod_date, A.price, A.price_type, A.reg_date, A.thumbnail, A.member_id\n" +
                    "     , IFNULL(B.REVIEW_RATING, 0) AS REVIEW_RATING\n" +
                    "     , IFNULL(B.REVIEW_COUNT, 0) AS REVIEW_COUNT\n" +
                    "    FROM Contents A\n" +
                    "    LEFT JOIN (\n" +
                    "                SELECT C.CONTENTS_ID\n" +
                    "                     , AVG(C.REVIEW_RATING) AS REVIEW_RATING\n" +
                    "                     , COUNT(C.CONTENTS_ID) AS REVIEW_COUNT\n" +
                    "                    FROM REVIEW C\n" +
                    "                    GROUP BY C.CONTENTS_ID\n" +
                    "    ) B\n" +
                    "    ON A.CONTENTS_ID = B.CONTENTS_ID\n" +
                    "    WHERE A.category = :category" +
                    "      AND A.price = -1" +
                    "    ORDER BY A.reg_date DESC" +
                    ") D", nativeQuery = true)
    Page<Contents> searchAllCategoryNationalReg(Pageable pageable, String category);

    @Query(value = "SELECT AA.*\n" +
            "     , IFNULL(BB.REVIEW_RATING, 0) AS REVIEW_RATING\n" +
            "     , IFNULL(BB.REVIEW_COUNT, 0) AS REVIEW_COUNT\n" +
            "    FROM (\n" +
            "            SELECT A.contents_id, A.category, A.contents_title, A.introduce, A.mod_date, A.price, A.price_type, A.reg_date, A.thumbnail, A.member_id\n" +
            "                 , IFNULL(B.PAYMENT_COUNT, 0) AS PAYMENT_COUNT\n" +
            "                FROM contents A\n" +
            "                LEFT JOIN (\n" +
            "                    SELECT C.CONTENTS_ID\n" +
            "                         , COUNT(C.contents_id) AS PAYMENT_COUNT\n" +
            "                        FROM payment_content C\n" +
            "                        GROUP BY C.CONTENTS_ID\n" +
            "                ) B\n" +
            "                ON A.contents_id = B.contents_id\n" +
            "         ) AA\n" +
            "    LEFT JOIN (\n" +
            "        SELECT C.CONTENTS_ID\n" +
            "             , AVG(C.REVIEW_RATING) AS REVIEW_RATING\n" +
            "             , COUNT(C.CONTENTS_ID) AS REVIEW_COUNT\n" +
            "            FROM review C\n" +
            "            GROUP BY C.CONTENTS_ID\n" +
            "    ) BB\n" +
            "    ON AA.contents_id = BB.contents_id" +
            "    WHERE AA.PRICE = 0" +
            "    ORDER BY AA.PAYMENT_COUNT DESC",
    countQuery = "SELECT COUNT(*)" +
            "FROM (" +
            "   SELECT AA.*\n" +
            "     , IFNULL(BB.REVIEW_RATING, 0) AS REVIEW_RATING\n" +
            "     , IFNULL(BB.REVIEW_COUNT, 0) AS REVIEW_COUNT\n" +
            "    FROM (\n" +
            "            SELECT A.contents_id, A.category, A.contents_title, A.introduce, A.mod_date, A.price, A.price_type, A.reg_date, A.thumbnail, A.member_id\n" +
            "                 , IFNULL(B.PAYMENT_COUNT, 0) AS PAYMENT_COUNT\n" +
            "                FROM contents A\n" +
            "                LEFT JOIN (\n" +
            "                    SELECT C.CONTENTS_ID\n" +
            "                         , COUNT(C.contents_id) AS PAYMENT_COUNT\n" +
            "                        FROM payment_content C\n" +
            "                        GROUP BY C.CONTENTS_ID\n" +
            "                ) B\n" +
            "                ON A.contents_id = B.contents_id\n" +
            "         ) AA\n" +
            "    LEFT JOIN (\n" +
            "        SELECT C.CONTENTS_ID\n" +
            "             , AVG(C.REVIEW_RATING) AS REVIEW_RATING\n" +
            "             , COUNT(C.CONTENTS_ID) AS REVIEW_COUNT\n" +
            "            FROM review C\n" +
            "            GROUP BY C.CONTENTS_ID\n" +
            "    ) BB\n" +
            "    ON AA.contents_id = BB.contents_id" +
            "    WHERE AA.PRICE = 0" +
            "    ORDER BY AA.PAYMENT_COUNT DESC" +
            ") D", nativeQuery = true)
    Page<Contents> searchAllFreeSale(Pageable pageable);

    @Query(value = "SELECT AA.*\n" +
            "     , IFNULL(BB.REVIEW_RATING, 0) AS REVIEW_RATING\n" +
            "     , IFNULL(BB.REVIEW_COUNT, 0) AS REVIEW_COUNT\n" +
            "    FROM (\n" +
            "            SELECT A.contents_id, A.category, A.contents_title, A.introduce, A.mod_date, A.price, A.price_type, A.reg_date, A.thumbnail, A.member_id\n" +
            "                 , IFNULL(B.PAYMENT_COUNT, 0) AS PAYMENT_COUNT\n" +
            "                FROM contents A\n" +
            "                LEFT JOIN (\n" +
            "                    SELECT C.CONTENTS_ID\n" +
            "                         , COUNT(C.contents_id) AS PAYMENT_COUNT\n" +
            "                        FROM payment_content C\n" +
            "                        GROUP BY C.CONTENTS_ID\n" +
            "                ) B\n" +
            "                ON A.contents_id = B.contents_id\n" +
            "         ) AA\n" +
            "    LEFT JOIN (\n" +
            "        SELECT C.CONTENTS_ID\n" +
            "             , AVG(C.REVIEW_RATING) AS REVIEW_RATING\n" +
            "             , COUNT(C.CONTENTS_ID) AS REVIEW_COUNT\n" +
            "            FROM review C\n" +
            "            GROUP BY C.CONTENTS_ID\n" +
            "    ) BB\n" +
            "    ON AA.contents_id = BB.contents_id" +
            "    WHERE AA.PRICE != 0" +
            "      AND AA.PRICE != -1" +
            "    ORDER BY AA.PAYMENT_COUNT DESC",
            countQuery = "SELECT COUNT(*)" +
                    "FROM (" +
                    "   SELECT AA.*\n" +
                    "     , IFNULL(BB.REVIEW_RATING, 0) AS REVIEW_RATING\n" +
                    "     , IFNULL(BB.REVIEW_COUNT, 0) AS REVIEW_COUNT\n" +
                    "    FROM (\n" +
                    "            SELECT A.contents_id, A.category, A.contents_title, A.introduce, A.mod_date, A.price, A.price_type, A.reg_date, A.thumbnail, A.member_id\n" +
                    "                 , IFNULL(B.PAYMENT_COUNT, 0) AS PAYMENT_COUNT\n" +
                    "                FROM contents A\n" +
                    "                LEFT JOIN (\n" +
                    "                    SELECT C.CONTENTS_ID\n" +
                    "                         , COUNT(C.contents_id) AS PAYMENT_COUNT\n" +
                    "                        FROM payment_content C\n" +
                    "                        GROUP BY C.CONTENTS_ID\n" +
                    "                ) B\n" +
                    "                ON A.contents_id = B.contents_id\n" +
                    "         ) AA\n" +
                    "    LEFT JOIN (\n" +
                    "        SELECT C.CONTENTS_ID\n" +
                    "             , AVG(C.REVIEW_RATING) AS REVIEW_RATING\n" +
                    "             , COUNT(C.CONTENTS_ID) AS REVIEW_COUNT\n" +
                    "            FROM review C\n" +
                    "            GROUP BY C.CONTENTS_ID\n" +
                    "    ) BB\n" +
                    "    ON AA.contents_id = BB.contents_id" +
                    "    WHERE AA.PRICE != 0" +
                    "      AND AA.PRICE != -1" +
                    "    ORDER BY AA.PAYMENT_COUNT DESC" +
                    ") D", nativeQuery = true)
    Page<Contents> searchAllPaySale(Pageable pageable);

    @Query(value = "SELECT AA.*\n" +
            "     , IFNULL(BB.REVIEW_RATING, 0) AS REVIEW_RATING\n" +
            "     , IFNULL(BB.REVIEW_COUNT, 0) AS REVIEW_COUNT\n" +
            "    FROM (\n" +
            "            SELECT A.contents_id, A.category, A.contents_title, A.introduce, A.mod_date, A.price, A.price_type, A.reg_date, A.thumbnail, A.member_id\n" +
            "                 , IFNULL(B.PAYMENT_COUNT, 0) AS PAYMENT_COUNT\n" +
            "                FROM contents A\n" +
            "                LEFT JOIN (\n" +
            "                    SELECT C.CONTENTS_ID\n" +
            "                         , COUNT(C.contents_id) AS PAYMENT_COUNT\n" +
            "                        FROM payment_content C\n" +
            "                        GROUP BY C.CONTENTS_ID\n" +
            "                ) B\n" +
            "                ON A.contents_id = B.contents_id\n" +
            "         ) AA\n" +
            "    LEFT JOIN (\n" +
            "        SELECT C.CONTENTS_ID\n" +
            "             , AVG(C.REVIEW_RATING) AS REVIEW_RATING\n" +
            "             , COUNT(C.CONTENTS_ID) AS REVIEW_COUNT\n" +
            "            FROM review C\n" +
            "            GROUP BY C.CONTENTS_ID\n" +
            "    ) BB\n" +
            "    ON AA.contents_id = BB.contents_id" +
            "    WHERE AA.PRICE = -1" +
            "    ORDER BY AA.PAYMENT_COUNT DESC",
            countQuery = "SELECT COUNT(*)" +
                    "FROM (" +
                    "   SELECT AA.*\n" +
                    "     , IFNULL(BB.REVIEW_RATING, 0) AS REVIEW_RATING\n" +
                    "     , IFNULL(BB.REVIEW_COUNT, 0) AS REVIEW_COUNT\n" +
                    "    FROM (\n" +
                    "            SELECT A.contents_id, A.category, A.contents_title, A.introduce, A.mod_date, A.price, A.price_type, A.reg_date, A.thumbnail, A.member_id\n" +
                    "                 , IFNULL(B.PAYMENT_COUNT, 0) AS PAYMENT_COUNT\n" +
                    "                FROM contents A\n" +
                    "                LEFT JOIN (\n" +
                    "                    SELECT C.CONTENTS_ID\n" +
                    "                         , COUNT(C.contents_id) AS PAYMENT_COUNT\n" +
                    "                        FROM payment_content C\n" +
                    "                        GROUP BY C.CONTENTS_ID\n" +
                    "                ) B\n" +
                    "                ON A.contents_id = B.contents_id\n" +
                    "         ) AA\n" +
                    "    LEFT JOIN (\n" +
                    "        SELECT C.CONTENTS_ID\n" +
                    "             , AVG(C.REVIEW_RATING) AS REVIEW_RATING\n" +
                    "             , COUNT(C.CONTENTS_ID) AS REVIEW_COUNT\n" +
                    "            FROM review C\n" +
                    "            GROUP BY C.CONTENTS_ID\n" +
                    "    ) BB\n" +
                    "    ON AA.contents_id = BB.contents_id" +
                    "    WHERE AA.PRICE = -1" +
                    "    ORDER BY AA.PAYMENT_COUNT DESC" +
                    ") D", nativeQuery = true)
    Page<Contents> searchAllNationalSale(Pageable pageable);

    @Query(value = "SELECT AA.*\n" +
            "     , IFNULL(BB.REVIEW_RATING, 0) AS REVIEW_RATING\n" +
            "     , IFNULL(BB.REVIEW_COUNT, 0) AS REVIEW_COUNT\n" +
            "    FROM (\n" +
            "            SELECT A.contents_id, A.category, A.contents_title, A.introduce, A.mod_date, A.price, A.price_type, A.reg_date, A.thumbnail, A.member_id\n" +
            "                 , IFNULL(B.PAYMENT_COUNT, 0) AS PAYMENT_COUNT\n" +
            "                FROM contents A\n" +
            "                LEFT JOIN (\n" +
            "                    SELECT C.CONTENTS_ID\n" +
            "                         , COUNT(C.contents_id) AS PAYMENT_COUNT\n" +
            "                        FROM payment_content C\n" +
            "                        GROUP BY C.CONTENTS_ID\n" +
            "                ) B\n" +
            "                ON A.contents_id = B.contents_id\n" +
            "         ) AA\n" +
            "    LEFT JOIN (\n" +
            "        SELECT C.CONTENTS_ID\n" +
            "             , AVG(C.REVIEW_RATING) AS REVIEW_RATING\n" +
            "             , COUNT(C.CONTENTS_ID) AS REVIEW_COUNT\n" +
            "            FROM review C\n" +
            "            GROUP BY C.CONTENTS_ID\n" +
            "    ) BB\n" +
            "    ON AA.contents_id = BB.contents_id" +
            "    WHERE AA.CATEGORY = :category" +
            "    ORDER BY AA.PAYMENT_COUNT DESC",
            countQuery = "SELECT COUNT(*)" +
                    "FROM (" +
                    "   SELECT AA.*\n" +
                    "     , IFNULL(BB.REVIEW_RATING, 0) AS REVIEW_RATING\n" +
                    "     , IFNULL(BB.REVIEW_COUNT, 0) AS REVIEW_COUNT\n" +
                    "    FROM (\n" +
                    "            SELECT A.contents_id, A.category, A.contents_title, A.introduce, A.mod_date, A.price, A.price_type, A.reg_date, A.thumbnail, A.member_id\n" +
                    "                 , IFNULL(B.PAYMENT_COUNT, 0) AS PAYMENT_COUNT\n" +
                    "                FROM contents A\n" +
                    "                LEFT JOIN (\n" +
                    "                    SELECT C.CONTENTS_ID\n" +
                    "                         , COUNT(C.contents_id) AS PAYMENT_COUNT\n" +
                    "                        FROM payment_content C\n" +
                    "                        GROUP BY C.CONTENTS_ID\n" +
                    "                ) B\n" +
                    "                ON A.contents_id = B.contents_id\n" +
                    "         ) AA\n" +
                    "    LEFT JOIN (\n" +
                    "        SELECT C.CONTENTS_ID\n" +
                    "             , AVG(C.REVIEW_RATING) AS REVIEW_RATING\n" +
                    "             , COUNT(C.CONTENTS_ID) AS REVIEW_COUNT\n" +
                    "            FROM review C\n" +
                    "            GROUP BY C.CONTENTS_ID\n" +
                    "    ) BB\n" +
                    "    ON AA.contents_id = BB.contents_id" +
                    "    WHERE AA.category = :category" +
                    "    ORDER BY AA.PAYMENT_COUNT DESC" +
                    ") D", nativeQuery = true)
    Page<Contents> searchAllCategorySale(Pageable pageable, String category);

    @Query(value = "SELECT AA.*\n" +
            "     , IFNULL(BB.REVIEW_RATING, 0) AS REVIEW_RATING\n" +
            "     , IFNULL(BB.REVIEW_COUNT, 0) AS REVIEW_COUNT\n" +
            "    FROM (\n" +
            "            SELECT A.contents_id, A.category, A.contents_title, A.introduce, A.mod_date, A.price, A.price_type, A.reg_date, A.thumbnail, A.member_id\n" +
            "                 , IFNULL(B.PAYMENT_COUNT, 0) AS PAYMENT_COUNT\n" +
            "                FROM contents A\n" +
            "                LEFT JOIN (\n" +
            "                    SELECT C.CONTENTS_ID\n" +
            "                         , COUNT(C.contents_id) AS PAYMENT_COUNT\n" +
            "                        FROM payment_content C\n" +
            "                        GROUP BY C.CONTENTS_ID\n" +
            "                ) B\n" +
            "                ON A.contents_id = B.contents_id\n" +
            "         ) AA\n" +
            "    LEFT JOIN (\n" +
            "        SELECT C.CONTENTS_ID\n" +
            "             , AVG(C.REVIEW_RATING) AS REVIEW_RATING\n" +
            "             , COUNT(C.CONTENTS_ID) AS REVIEW_COUNT\n" +
            "            FROM review C\n" +
            "            GROUP BY C.CONTENTS_ID\n" +
            "    ) BB\n" +
            "    ON AA.contents_id = BB.contents_id" +
            "    WHERE AA.category = :category" +
            "      AND AA.price = 0" +
            "    ORDER BY AA.PAYMENT_COUNT DESC",
            countQuery = "SELECT COUNT(*)" +
                    "FROM (" +
                    "   SELECT AA.*\n" +
                    "     , IFNULL(BB.REVIEW_RATING, 0) AS REVIEW_RATING\n" +
                    "     , IFNULL(BB.REVIEW_COUNT, 0) AS REVIEW_COUNT\n" +
                    "    FROM (\n" +
                    "            SELECT A.contents_id, A.category, A.contents_title, A.introduce, A.mod_date, A.price, A.price_type, A.reg_date, A.thumbnail, A.member_id\n" +
                    "                 , IFNULL(B.PAYMENT_COUNT, 0) AS PAYMENT_COUNT\n" +
                    "                FROM contents A\n" +
                    "                LEFT JOIN (\n" +
                    "                    SELECT C.CONTENTS_ID\n" +
                    "                         , COUNT(C.contents_id) AS PAYMENT_COUNT\n" +
                    "                        FROM payment_content C\n" +
                    "                        GROUP BY C.CONTENTS_ID\n" +
                    "                ) B\n" +
                    "                ON A.contents_id = B.contents_id\n" +
                    "         ) AA\n" +
                    "    LEFT JOIN (\n" +
                    "        SELECT C.CONTENTS_ID\n" +
                    "             , AVG(C.REVIEW_RATING) AS REVIEW_RATING\n" +
                    "             , COUNT(C.CONTENTS_ID) AS REVIEW_COUNT\n" +
                    "            FROM review C\n" +
                    "            GROUP BY C.CONTENTS_ID\n" +
                    "    ) BB\n" +
                    "    ON AA.contents_id = BB.contents_id" +
                    "    WHERE AA.category = :category" +
                    "      AND AA.price = 0" +
                    "    ORDER BY AA.PAYMENT_COUNT DESC" +
                    ") D", nativeQuery = true)
    Page<Contents> searchAllCategoryFreeSale(Pageable pageable, String category);

    @Query(value = "SELECT AA.*\n" +
            "     , IFNULL(BB.REVIEW_RATING, 0) AS REVIEW_RATING\n" +
            "     , IFNULL(BB.REVIEW_COUNT, 0) AS REVIEW_COUNT\n" +
            "    FROM (\n" +
            "            SELECT A.contents_id, A.category, A.contents_title, A.introduce, A.mod_date, A.price, A.price_type, A.reg_date, A.thumbnail, A.member_id\n" +
            "                 , IFNULL(B.PAYMENT_COUNT, 0) AS PAYMENT_COUNT\n" +
            "                FROM contents A\n" +
            "                LEFT JOIN (\n" +
            "                    SELECT C.CONTENTS_ID\n" +
            "                         , COUNT(C.contents_id) AS PAYMENT_COUNT\n" +
            "                        FROM payment_content C\n" +
            "                        GROUP BY C.CONTENTS_ID\n" +
            "                ) B\n" +
            "                ON A.contents_id = B.contents_id\n" +
            "         ) AA\n" +
            "    LEFT JOIN (\n" +
            "        SELECT C.CONTENTS_ID\n" +
            "             , AVG(C.REVIEW_RATING) AS REVIEW_RATING\n" +
            "             , COUNT(C.CONTENTS_ID) AS REVIEW_COUNT\n" +
            "            FROM review C\n" +
            "            GROUP BY C.CONTENTS_ID\n" +
            "    ) BB\n" +
            "    ON AA.contents_id = BB.contents_id" +
            "    WHERE AA.category = :category" +
            "      AND AA.price != 0" +
            "      AND AA.price != -1" +
            "    ORDER BY AA.PAYMENT_COUNT DESC",
            countQuery = "SELECT COUNT(*)" +
                    "FROM (" +
                    "   SELECT AA.*\n" +
                    "     , IFNULL(BB.REVIEW_RATING, 0) AS REVIEW_RATING\n" +
                    "     , IFNULL(BB.REVIEW_COUNT, 0) AS REVIEW_COUNT\n" +
                    "    FROM (\n" +
                    "            SELECT A.contents_id, A.category, A.contents_title, A.introduce, A.mod_date, A.price, A.price_type, A.reg_date, A.thumbnail, A.member_id\n" +
                    "                 , IFNULL(B.PAYMENT_COUNT, 0) AS PAYMENT_COUNT\n" +
                    "                FROM contents A\n" +
                    "                LEFT JOIN (\n" +
                    "                    SELECT C.CONTENTS_ID\n" +
                    "                         , COUNT(C.contents_id) AS PAYMENT_COUNT\n" +
                    "                        FROM payment_content C\n" +
                    "                        GROUP BY C.CONTENTS_ID\n" +
                    "                ) B\n" +
                    "                ON A.contents_id = B.contents_id\n" +
                    "         ) AA\n" +
                    "    LEFT JOIN (\n" +
                    "        SELECT C.CONTENTS_ID\n" +
                    "             , AVG(C.REVIEW_RATING) AS REVIEW_RATING\n" +
                    "             , COUNT(C.CONTENTS_ID) AS REVIEW_COUNT\n" +
                    "            FROM review C\n" +
                    "            GROUP BY C.CONTENTS_ID\n" +
                    "    ) BB\n" +
                    "    ON AA.contents_id = BB.contents_id" +
                    "    WHERE AA.category = :category" +
                    "      AND AA.price != 0" +
                    "      AND AA.price != -1" +
                    "    ORDER BY AA.PAYMENT_COUNT DESC" +
                    ") D", nativeQuery = true)
    Page<Contents> searchAllCategoryPaySale(Pageable pageable, String category);

    @Query(value = "SELECT AA.*\n" +
            "     , IFNULL(BB.REVIEW_RATING, 0) AS REVIEW_RATING\n" +
            "     , IFNULL(BB.REVIEW_COUNT, 0) AS REVIEW_COUNT\n" +
            "    FROM (\n" +
            "            SELECT A.contents_id, A.category, A.contents_title, A.introduce, A.mod_date, A.price, A.price_type, A.reg_date, A.thumbnail, A.member_id\n" +
            "                 , IFNULL(B.PAYMENT_COUNT, 0) AS PAYMENT_COUNT\n" +
            "                FROM contents A\n" +
            "                LEFT JOIN (\n" +
            "                    SELECT C.CONTENTS_ID\n" +
            "                         , COUNT(C.contents_id) AS PAYMENT_COUNT\n" +
            "                        FROM payment_content C\n" +
            "                        GROUP BY C.CONTENTS_ID\n" +
            "                ) B\n" +
            "                ON A.contents_id = B.contents_id\n" +
            "         ) AA\n" +
            "    LEFT JOIN (\n" +
            "        SELECT C.CONTENTS_ID\n" +
            "             , AVG(C.REVIEW_RATING) AS REVIEW_RATING\n" +
            "             , COUNT(C.CONTENTS_ID) AS REVIEW_COUNT\n" +
            "            FROM review C\n" +
            "            GROUP BY C.CONTENTS_ID\n" +
            "    ) BB\n" +
            "    ON AA.contents_id = BB.contents_id" +
            "    WHERE AA.category = :category" +
            "      AND AA.price = -1" +
            "    ORDER BY AA.PAYMENT_COUNT DESC",
            countQuery = "SELECT COUNT(*)" +
                    "FROM (" +
                    "   SELECT AA.*\n" +
                    "     , IFNULL(BB.REVIEW_RATING, 0) AS REVIEW_RATING\n" +
                    "     , IFNULL(BB.REVIEW_COUNT, 0) AS REVIEW_COUNT\n" +
                    "    FROM (\n" +
                    "            SELECT A.contents_id, A.category, A.contents_title, A.introduce, A.mod_date, A.price, A.price_type, A.reg_date, A.thumbnail, A.member_id\n" +
                    "                 , IFNULL(B.PAYMENT_COUNT, 0) AS PAYMENT_COUNT\n" +
                    "                FROM contents A\n" +
                    "                LEFT JOIN (\n" +
                    "                    SELECT C.CONTENTS_ID\n" +
                    "                         , COUNT(C.contents_id) AS PAYMENT_COUNT\n" +
                    "                        FROM payment_content C\n" +
                    "                        GROUP BY C.CONTENTS_ID\n" +
                    "                ) B\n" +
                    "                ON A.contents_id = B.contents_id\n" +
                    "         ) AA\n" +
                    "    LEFT JOIN (\n" +
                    "        SELECT C.CONTENTS_ID\n" +
                    "             , AVG(C.REVIEW_RATING) AS REVIEW_RATING\n" +
                    "             , COUNT(C.CONTENTS_ID) AS REVIEW_COUNT\n" +
                    "            FROM review C\n" +
                    "            GROUP BY C.CONTENTS_ID\n" +
                    "    ) BB\n" +
                    "    ON AA.contents_id = BB.contents_id" +
                    "    WHERE AA.category = :category" +
                    "      AND AA.price = -1" +
                    "    ORDER BY AA.PAYMENT_COUNT DESC" +
                    ") D", nativeQuery = true)
    Page<Contents> searchAllCategoryNationalSale(Pageable pageable, String category);

    @Query(value = "SELECT A.contents_id, A.category, A.contents_title, A.introduce, A.mod_date, A.price, A.price_type, A.reg_date, A.thumbnail, A.member_id\n" +
            "     , IFNULL(B.REVIEW_RATING, 0) AS REVIEW_RATING\n" +
            "     , IFNULL(B.REVIEW_COUNT, 0) AS REVIEW_COUNT\n" +
            "    FROM CONTENTS A\n" +
            "    LEFT JOIN (\n" +
            "        SELECT CONTENTS_ID\n" +
            "             , AVG(review_rating) AS REVIEW_RATING\n" +
            "             , COUNT(CONTENTS_ID) AS REVIEW_COUNT\n" +
            "            FROM review\n" +
            "            GROUP BY CONTENTS_ID\n" +
            "    ) B\n" +
            "    ON A.contents_id = B.contents_id\n" +
            "    WHERE A.price = 0" +
            "    ORDER BY B.REVIEW_RATING DESC, B.REVIEW_COUNT DESC",
    countQuery = "SELECT COUNT(*)" +
            "   FROM (" +
            "   SELECT A.contents_id, A.category, A.contents_title, A.introduce, A.mod_date, A.price, A.price_type, A.reg_date, A.thumbnail, A.member_id\n" +
            "     , IFNULL(B.REVIEW_RATING, 0) AS REVIEW_RATING\n" +
            "     , IFNULL(B.REVIEW_COUNT, 0) AS REVIEW_COUNT\n" +
            "    FROM CONTENTS A\n" +
            "    LEFT JOIN (\n" +
            "        SELECT CONTENTS_ID\n" +
            "             , AVG(review_rating) AS REVIEW_RATING\n" +
            "             , COUNT(CONTENTS_ID) AS REVIEW_COUNT\n" +
            "            FROM review\n" +
            "            GROUP BY CONTENTS_ID\n" +
            "    ) B\n" +
            "    ON A.contents_id = B.contents_id" +
            "    WHERE A.price = 0" +
            "    ORDER BY B.REVIEW_RATING DESC, B.REVIEW_COUNT DESC" +
            ") D", nativeQuery = true)
    Page<Contents> searchAllFreePop(Pageable pageable);

    @Query(value = "SELECT A.contents_id, A.category, A.contents_title, A.introduce, A.mod_date, A.price, A.price_type, A.reg_date, A.thumbnail, A.member_id\n" +
            "     , IFNULL(B.REVIEW_RATING, 0) AS REVIEW_RATING\n" +
            "     , IFNULL(B.REVIEW_COUNT, 0) AS REVIEW_COUNT\n" +
            "    FROM CONTENTS A\n" +
            "    LEFT JOIN (\n" +
            "        SELECT CONTENTS_ID\n" +
            "             , AVG(review_rating) AS REVIEW_RATING\n" +
            "             , COUNT(CONTENTS_ID) AS REVIEW_COUNT\n" +
            "            FROM review\n" +
            "            GROUP BY CONTENTS_ID\n" +
            "    ) B\n" +
            "    ON A.contents_id = B.contents_id\n" +
            "    WHERE A.price != 0" +
            "      AND A.price != -1" +
            "    ORDER BY B.REVIEW_RATING DESC, B.REVIEW_COUNT DESC",
            countQuery = "SELECT COUNT(*)" +
                    "   FROM (" +
                    "   SELECT A.contents_id, A.category, A.contents_title, A.introduce, A.mod_date, A.price, A.price_type, A.reg_date, A.thumbnail, A.member_id\n" +
                    "     , IFNULL(B.REVIEW_RATING, 0) AS REVIEW_RATING\n" +
                    "     , IFNULL(B.REVIEW_COUNT, 0) AS REVIEW_COUNT\n" +
                    "    FROM CONTENTS A\n" +
                    "    LEFT JOIN (\n" +
                    "        SELECT CONTENTS_ID\n" +
                    "             , AVG(review_rating) AS REVIEW_RATING\n" +
                    "             , COUNT(CONTENTS_ID) AS REVIEW_COUNT\n" +
                    "            FROM review\n" +
                    "            GROUP BY CONTENTS_ID\n" +
                    "    ) B\n" +
                    "    ON A.contents_id = B.contents_id" +
                    "    WHERE A.price != 0" +
                    "      AND A.price != -1" +
                    "    ORDER BY B.REVIEW_RATING DESC, B.REVIEW_COUNT DESC" +
                    ") D", nativeQuery = true)
    Page<Contents> searchAllPayPop(Pageable pageable);

    @Query(value = "SELECT A.contents_id, A.category, A.contents_title, A.introduce, A.mod_date, A.price, A.price_type, A.reg_date, A.thumbnail, A.member_id\n" +
            "     , IFNULL(B.REVIEW_RATING, 0) AS REVIEW_RATING\n" +
            "     , IFNULL(B.REVIEW_COUNT, 0) AS REVIEW_COUNT\n" +
            "    FROM CONTENTS A\n" +
            "    LEFT JOIN (\n" +
            "        SELECT CONTENTS_ID\n" +
            "             , AVG(review_rating) AS REVIEW_RATING\n" +
            "             , COUNT(CONTENTS_ID) AS REVIEW_COUNT\n" +
            "            FROM review\n" +
            "            GROUP BY CONTENTS_ID\n" +
            "    ) B\n" +
            "    ON A.contents_id = B.contents_id\n" +
            "    WHERE A.price = -1" +
            "    ORDER BY B.REVIEW_RATING DESC, B.REVIEW_COUNT DESC",
            countQuery = "SELECT COUNT(*)" +
                    "   FROM (" +
                    "   SELECT A.contents_id, A.category, A.contents_title, A.introduce, A.mod_date, A.price, A.price_type, A.reg_date, A.thumbnail, A.member_id\n" +
                    "     , IFNULL(B.REVIEW_RATING, 0) AS REVIEW_RATING\n" +
                    "     , IFNULL(B.REVIEW_COUNT, 0) AS REVIEW_COUNT\n" +
                    "    FROM CONTENTS A\n" +
                    "    LEFT JOIN (\n" +
                    "        SELECT CONTENTS_ID\n" +
                    "             , AVG(review_rating) AS REVIEW_RATING\n" +
                    "             , COUNT(CONTENTS_ID) AS REVIEW_COUNT\n" +
                    "            FROM review\n" +
                    "            GROUP BY CONTENTS_ID\n" +
                    "    ) B\n" +
                    "    ON A.contents_id = B.contents_id" +
                    "    WHERE A.price = -1" +
                    "    ORDER BY B.REVIEW_RATING DESC, B.REVIEW_COUNT DESC" +
                    ") D", nativeQuery = true)
    Page<Contents> searchAllNationalPop(Pageable pageable);

    @Query(value = "SELECT A.contents_id, A.category, A.contents_title, A.introduce, A.mod_date, A.price, A.price_type, A.reg_date, A.thumbnail, A.member_id\n" +
            "     , IFNULL(B.REVIEW_RATING, 0) AS REVIEW_RATING\n" +
            "     , IFNULL(B.REVIEW_COUNT, 0) AS REVIEW_COUNT\n" +
            "    FROM CONTENTS A\n" +
            "    LEFT JOIN (\n" +
            "        SELECT CONTENTS_ID\n" +
            "             , AVG(review_rating) AS REVIEW_RATING\n" +
            "             , COUNT(CONTENTS_ID) AS REVIEW_COUNT\n" +
            "            FROM review\n" +
            "            GROUP BY CONTENTS_ID\n" +
            "    ) B\n" +
            "    ON A.contents_id = B.contents_id\n" +
            "    WHERE A.category = :category" +
            "    ORDER BY B.REVIEW_RATING DESC, B.REVIEW_COUNT DESC",
            countQuery = "SELECT COUNT(*)" +
                    "   FROM (" +
                    "   SELECT A.contents_id, A.category, A.contents_title, A.introduce, A.mod_date, A.price, A.price_type, A.reg_date, A.thumbnail, A.member_id\n" +
                    "     , IFNULL(B.REVIEW_RATING, 0) AS REVIEW_RATING\n" +
                    "     , IFNULL(B.REVIEW_COUNT, 0) AS REVIEW_COUNT\n" +
                    "    FROM CONTENTS A\n" +
                    "    LEFT JOIN (\n" +
                    "        SELECT CONTENTS_ID\n" +
                    "             , AVG(review_rating) AS REVIEW_RATING\n" +
                    "             , COUNT(CONTENTS_ID) AS REVIEW_COUNT\n" +
                    "            FROM review\n" +
                    "            GROUP BY CONTENTS_ID\n" +
                    "    ) B\n" +
                    "    ON A.contents_id = B.contents_id" +
                    "    WHERE A.category = :category" +
                    "    ORDER BY B.REVIEW_RATING DESC, B.REVIEW_COUNT DESC" +
                    ") D", nativeQuery = true)
    Page<Contents> searchAllCategoryPop(Pageable pageable, String category);

    @Query(value = "SELECT A.contents_id, A.category, A.contents_title, A.introduce, A.mod_date, A.price, A.price_type, A.reg_date, A.thumbnail, A.member_id\n" +
            "     , IFNULL(B.REVIEW_RATING, 0) AS REVIEW_RATING\n" +
            "     , IFNULL(B.REVIEW_COUNT, 0) AS REVIEW_COUNT\n" +
            "    FROM CONTENTS A\n" +
            "    LEFT JOIN (\n" +
            "        SELECT CONTENTS_ID\n" +
            "             , AVG(review_rating) AS REVIEW_RATING\n" +
            "             , COUNT(CONTENTS_ID) AS REVIEW_COUNT\n" +
            "            FROM review\n" +
            "            GROUP BY CONTENTS_ID\n" +
            "    ) B\n" +
            "    ON A.contents_id = B.contents_id\n" +
            "    WHERE A.category = :category" +
            "      AND A.price = 0" +
            "    ORDER BY B.REVIEW_RATING DESC, B.REVIEW_COUNT DESC",
            countQuery = "SELECT COUNT(*)" +
                    "   FROM (" +
                    "   SELECT A.contents_id, A.category, A.contents_title, A.introduce, A.mod_date, A.price, A.price_type, A.reg_date, A.thumbnail, A.member_id\n" +
                    "     , IFNULL(B.REVIEW_RATING, 0) AS REVIEW_RATING\n" +
                    "     , IFNULL(B.REVIEW_COUNT, 0) AS REVIEW_COUNT\n" +
                    "    FROM CONTENTS A\n" +
                    "    LEFT JOIN (\n" +
                    "        SELECT CONTENTS_ID\n" +
                    "             , AVG(review_rating) AS REVIEW_RATING\n" +
                    "             , COUNT(CONTENTS_ID) AS REVIEW_COUNT\n" +
                    "            FROM review\n" +
                    "            GROUP BY CONTENTS_ID\n" +
                    "    ) B\n" +
                    "    ON A.contents_id = B.contents_id" +
                    "    WHERE A.category = :category" +
                    "      AND A.price = 0" +
                    "    ORDER BY B.REVIEW_RATING DESC, B.REVIEW_COUNT DESC" +
                    ") D", nativeQuery = true)
    Page<Contents> searchAllCategoryFreePop(Pageable pageable, String category);

    @Query(value = "SELECT A.contents_id, A.category, A.contents_title, A.introduce, A.mod_date, A.price, A.price_type, A.reg_date, A.thumbnail, A.member_id\n" +
            "     , IFNULL(B.REVIEW_RATING, 0) AS REVIEW_RATING\n" +
            "     , IFNULL(B.REVIEW_COUNT, 0) AS REVIEW_COUNT\n" +
            "    FROM CONTENTS A\n" +
            "    LEFT JOIN (\n" +
            "        SELECT CONTENTS_ID\n" +
            "             , AVG(review_rating) AS REVIEW_RATING\n" +
            "             , COUNT(CONTENTS_ID) AS REVIEW_COUNT\n" +
            "            FROM review\n" +
            "            GROUP BY CONTENTS_ID\n" +
            "    ) B\n" +
            "    ON A.contents_id = B.contents_id\n" +
            "    WHERE A.category = :category" +
            "      AND A.price != 0" +
            "      AND A.price != -1" +
            "    ORDER BY B.REVIEW_RATING DESC, B.REVIEW_COUNT DESC",
            countQuery = "SELECT COUNT(*)" +
                    "   FROM (" +
                    "   SELECT A.contents_id, A.category, A.contents_title, A.introduce, A.mod_date, A.price, A.price_type, A.reg_date, A.thumbnail, A.member_id\n" +
                    "     , IFNULL(B.REVIEW_RATING, 0) AS REVIEW_RATING\n" +
                    "     , IFNULL(B.REVIEW_COUNT, 0) AS REVIEW_COUNT\n" +
                    "    FROM CONTENTS A\n" +
                    "    LEFT JOIN (\n" +
                    "        SELECT CONTENTS_ID\n" +
                    "             , AVG(review_rating) AS REVIEW_RATING\n" +
                    "             , COUNT(CONTENTS_ID) AS REVIEW_COUNT\n" +
                    "            FROM review\n" +
                    "            GROUP BY CONTENTS_ID\n" +
                    "    ) B\n" +
                    "    ON A.contents_id = B.contents_id" +
                    "    WHERE A.category = :category" +
                    "      AND A.price != 0" +
                    "      AND A.price != -1" +
                    "    ORDER BY B.REVIEW_RATING DESC, B.REVIEW_COUNT DESC" +
                    ") D", nativeQuery = true)
    Page<Contents> searchAllCategoryPayPop(Pageable pageable, String category);

    @Query(value = "SELECT A.contents_id, A.category, A.contents_title, A.introduce, A.mod_date, A.price, A.price_type, A.reg_date, A.thumbnail, A.member_id\n" +
            "     , IFNULL(B.REVIEW_RATING, 0) AS REVIEW_RATING\n" +
            "     , IFNULL(B.REVIEW_COUNT, 0) AS REVIEW_COUNT\n" +
            "    FROM CONTENTS A\n" +
            "    LEFT JOIN (\n" +
            "        SELECT CONTENTS_ID\n" +
            "             , AVG(review_rating) AS REVIEW_RATING\n" +
            "             , COUNT(CONTENTS_ID) AS REVIEW_COUNT\n" +
            "            FROM review\n" +
            "            GROUP BY CONTENTS_ID\n" +
            "    ) B\n" +
            "    ON A.contents_id = B.contents_id\n" +
            "    WHERE A.category = :category" +
            "      AND A.price = -1" +
            "    ORDER BY B.REVIEW_RATING DESC, B.REVIEW_COUNT DESC",
            countQuery = "SELECT COUNT(*)" +
                    "   FROM (" +
                    "   SELECT A.contents_id, A.category, A.contents_title, A.introduce, A.mod_date, A.price, A.price_type, A.reg_date, A.thumbnail, A.member_id\n" +
                    "     , IFNULL(B.REVIEW_RATING, 0) AS REVIEW_RATING\n" +
                    "     , IFNULL(B.REVIEW_COUNT, 0) AS REVIEW_COUNT\n" +
                    "    FROM CONTENTS A\n" +
                    "    LEFT JOIN (\n" +
                    "        SELECT CONTENTS_ID\n" +
                    "             , AVG(review_rating) AS REVIEW_RATING\n" +
                    "             , COUNT(CONTENTS_ID) AS REVIEW_COUNT\n" +
                    "            FROM review\n" +
                    "            GROUP BY CONTENTS_ID\n" +
                    "    ) B\n" +
                    "    ON A.contents_id = B.contents_id" +
                    "    WHERE A.category = :category" +
                    "      AND A.price = -1" +
                    "    ORDER BY B.REVIEW_RATING DESC, B.REVIEW_COUNT DESC" +
                    ") D", nativeQuery = true)
    Page<Contents> searchAllCategoryNationalPop(Pageable pageable, String category);

    @Query(value = "SELECT AA.*\n" +
            "     , IFNULL(BB.REVIEW_RATING, 0) AS REVIEW_RATING\n" +
            "     , IFNULL(BB.REVIEW_COUNT, 0) AS REVIEW_COUNT\n" +
            "    FROM (\n" +
            "            SELECT A.contents_id, A.category, A.contents_title, A.introduce, A.mod_date, A.price, A.price_type, A.reg_date, A.thumbnail, A.member_id\n" +
            "                 , IFNULL(B.PAYMENT_COUNT, 0) AS PAYMENT_COUNT\n" +
            "                FROM contents A\n" +
            "                LEFT JOIN (\n" +
            "                    SELECT C.CONTENTS_ID\n" +
            "                         , COUNT(C.contents_id) AS PAYMENT_COUNT\n" +
            "                        FROM payment_content C\n" +
            "                        GROUP BY C.CONTENTS_ID\n" +
            "                ) B\n" +
            "                ON A.contents_id = B.contents_id\n" +
            "         ) AA\n" +
            "    LEFT JOIN (\n" +
            "        SELECT C.CONTENTS_ID\n" +
            "             , AVG(C.REVIEW_RATING) AS REVIEW_RATING\n" +
            "             , COUNT(C.CONTENTS_ID) AS REVIEW_COUNT\n" +
            "            FROM review C\n" +
            "            GROUP BY C.CONTENTS_ID\n" +
            "    ) BB\n" +
            "    ON AA.contents_id = BB.contents_id" +
            "    ORDER BY AA.PAYMENT_COUNT DESC",
            countQuery = "SELECT COUNT(*)" +
                    "FROM (" +
                    "   SELECT AA.*\n" +
                    "     , IFNULL(BB.REVIEW_RATING, 0) AS REVIEW_RATING\n" +
                    "     , IFNULL(BB.REVIEW_COUNT, 0) AS REVIEW_COUNT\n" +
                    "    FROM (\n" +
                    "            SELECT A.contents_id, A.category, A.contents_title, A.introduce, A.mod_date, A.price, A.price_type, A.reg_date, A.thumbnail, A.member_id\n" +
                    "                 , IFNULL(B.PAYMENT_COUNT, 0) AS PAYMENT_COUNT\n" +
                    "                FROM contents A\n" +
                    "                LEFT JOIN (\n" +
                    "                    SELECT C.CONTENTS_ID\n" +
                    "                         , COUNT(C.contents_id) AS PAYMENT_COUNT\n" +
                    "                        FROM payment_content C\n" +
                    "                        GROUP BY C.CONTENTS_ID\n" +
                    "                ) B\n" +
                    "                ON A.contents_id = B.contents_id\n" +
                    "         ) AA\n" +
                    "    LEFT JOIN (\n" +
                    "        SELECT C.CONTENTS_ID\n" +
                    "             , AVG(C.REVIEW_RATING) AS REVIEW_RATING\n" +
                    "             , COUNT(C.CONTENTS_ID) AS REVIEW_COUNT\n" +
                    "            FROM review C\n" +
                    "            GROUP BY C.CONTENTS_ID\n" +
                    "    ) BB\n" +
                    "    ON AA.contents_id = BB.contents_id" +
                    "    ORDER BY AA.PAYMENT_COUNT DESC" +
                    ") D",
            nativeQuery = true)
    Page<Contents> searchAllSale(Pageable pageable);

    @Query(value = "SELECT A.contents_id, A.category, A.contents_title, A.introduce, A.mod_date, A.price, A.price_type, A.reg_date, A.thumbnail, A.member_id\n" +
            "     , IFNULL(B.REVIEW_RATING, 0) AS REVIEW_RATING\n" +
            "     , IFNULL(B.REVIEW_COUNT, 0) AS REVIEW_COUNT\n" +
            "    FROM CONTENTS A\n" +
            "    LEFT JOIN (\n" +
            "        SELECT C.CONTENTS_ID\n" +
            "             , AVG(C.review_rating) AS REVIEW_RATING\n" +
            "             , COUNT(C.CONTENTS_ID) AS REVIEW_COUNT\n" +
            "            FROM review C\n" +
            "            GROUP BY C.CONTENTS_ID\n" +
            "    ) B\n" +
            "    ON A.contents_id = B.contents_id\n" +
            "    ORDER BY B.REVIEW_RATING DESC, B.REVIEW_COUNT DESC",
            countQuery = "SELECT COUNT(*)" +
                    "   FROM (" +
                    "   SELECT A.contents_id, A.category, A.contents_title, A.introduce, A.mod_date, A.price, A.price_type, A.reg_date, A.thumbnail, A.member_id\n" +
                    "     , IFNULL(B.REVIEW_RATING, 0) AS REVIEW_RATING\n" +
                    "     , IFNULL(B.REVIEW_COUNT, 0) AS REVIEW_COUNT\n" +
                    "    FROM CONTENTS A\n" +
                    "    LEFT JOIN (\n" +
                    "        SELECT C.CONTENTS_ID\n" +
                    "             , AVG(C.review_rating) AS REVIEW_RATING\n" +
                    "             , COUNT(C.CONTENTS_ID) AS REVIEW_COUNT\n" +
                    "            FROM review C\n" +
                    "            GROUP BY C.CONTENTS_ID\n" +
                    "    ) B\n" +
                    "    ON A.contents_id = B.contents_id" +
                    "    ORDER BY B.REVIEW_RATING DESC, B.REVIEW_COUNT DESC" +
                    ") D", nativeQuery = true)
    Page<Contents> searchAllPop(Pageable pageable);

    @Query(value = "SELECT A.contents_id, A.category, A.contents_title, A.introduce, A.mod_date, A.price, A.price_type, A.reg_date, A.thumbnail, A.member_id\n" +
            "     , IFNULL(B.REVIEW_RATING, 0) AS REVIEW_RATING\n" +
            "     , IFNULL(B.REVIEW_COUNT, 0) AS REVIEW_COUNT\n" +
            "    FROM CONTENTS A\n" +
            "    LEFT JOIN (\n" +
            "                SELECT CONTENTS_ID\n" +
            "                     , AVG(review_rating) AS REVIEW_RATING\n" +
            "                     , COUNT(CONTENTS_ID) AS REVIEW_COUNT\n" +
            "                    FROM review\n" +
            "                    GROUP BY CONTENTS_ID\n" +
            "              ) B\n" +
            "    ON A.contents_id = B.contents_id" +
            "   ORDER BY A.reg_date DESC",
            countQuery = "SELECT count(*) " +
            "FROM (" +
                    "   SELECT A.contents_id, A.category, A.contents_title, A.introduce, A.mod_date, A.price, A.price_type, A.reg_date, A.thumbnail, A.member_id\n" +
                    "     , IFNULL(B.REVIEW_RATING, 0) AS REVIEW_RATING\n" +
                    "     , IFNULL(B.REVIEW_COUNT, 0) AS REVIEW_COUNT\n" +
                    "    FROM CONTENTS A\n" +
                    "    LEFT JOIN (\n" +
                    "                SELECT CONTENTS_ID\n" +
                    "                     , AVG(review_rating) AS REVIEW_RATING\n" +
                    "                     , COUNT(CONTENTS_ID) AS REVIEW_COUNT\n" +
                    "                    FROM review\n" +
                    "                    GROUP BY CONTENTS_ID\n" +
                    "              ) B\n" +
                    "    ON A.contents_id = B.contents_id" +
                    "    ORDER BY A.reg_date DESC" +
            ") D",nativeQuery = true)
    Page<Contents> searchAllReg(Pageable pageable);

    @Query(value = "SELECT A.contents_id, A.category, A.contents_title, A.introduce, A.mod_date, A.price, A.price_type, A.reg_date, A.thumbnail, A.member_id\n" +
            "     , IFNULL(B.REVIEW_RATING, 0) AS REVIEW_RATING\n" +
            "     , IFNULL(B.REVIEW_COUNT, 0) AS REVIEW_COUNT\n" +
            "    FROM CONTENTS A\n" +
            "    LEFT JOIN (\n" +
            "                SELECT CONTENTS_ID\n" +
            "                     , AVG(review_rating) AS REVIEW_RATING\n" +
            "                     , COUNT(CONTENTS_ID) AS REVIEW_COUNT\n" +
            "                    FROM review\n" +
            "                    GROUP BY CONTENTS_ID\n" +
            "              ) B\n" +
            "    ON A.contents_id = B.contents_id",
            countQuery = "SELECT COUNT(*) " +
                    "   FROM (" +
                    "   SELECT A.contents_id, A.category, A.contents_title, A.introduce, A.mod_date, A.price, A.price_type, A.reg_date, A.thumbnail, A.member_id\n" +
                    "     , IFNULL(B.REVIEW_RATING, 0) AS REVIEW_RATING\n" +
                    "     , IFNULL(B.REVIEW_COUNT, 0) AS REVIEW_COUNT\n" +
                    "    FROM CONTENTS A\n" +
                    "    LEFT JOIN (\n" +
                    "                SELECT CONTENTS_ID\n" +
                    "                     , AVG(review_rating) AS REVIEW_RATING\n" +
                    "                     , COUNT(CONTENTS_ID) AS REVIEW_COUNT\n" +
                    "                    FROM review\n" +
                    "                    GROUP BY CONTENTS_ID\n" +
                    "              ) B\n" +
                    "    ON A.contents_id = B.contents_id" +
                    ") D",
            nativeQuery = true
           )
    Page<Contents> searchAll(Pageable pageable);

    @Query(value = "SELECT A.contents_id, A.category, A.contents_title, A.introduce, A.mod_date, A.price, A.price_type, A.reg_date, A.thumbnail, A.member_id\n" +
            "     , IFNULL(B.REVIEW_RATING, 0) AS REVIEW_RATING\n" +
            "     , IFNULL(B.REVIEW_COUNT, 0) AS REVIEW_COUNT\n" +
            "    FROM CONTENTS A\n" +
            "    LEFT JOIN (\n" +
            "                SELECT CONTENTS_ID\n" +
            "                     , AVG(review_rating) AS REVIEW_RATING\n" +
            "                     , COUNT(CONTENTS_ID) AS REVIEW_COUNT\n" +
            "                    FROM review\n" +
            "                    GROUP BY CONTENTS_ID\n" +
            "              ) B\n" +
            "    ON A.contents_id = B.contents_id" +
            "    WHERE A.contents_id = :contentsId", nativeQuery = true)
    Contents searchById(int contentsId);

    @Query(value = "SELECT A.CONTENTS_ID\n" +
            "     , A.CATEGORY\n" +
            "     , A.CONTENTS_TITLE\n" +
            "     , A.INTRODUCE\n" +
            "     , A.PRICE\n" +
            "     , A.PRICE_TYPE\n" +
            "     , A.REG_DATE\n" +
            "     , A.THUMBNAIL\n" +
            "     , A.MEMBER_ID\n" +
            "     , A.MOD_DATE\n" +
            "     , IFNULL(B.REVIEW_COUNT, 0) AS REVIEW_COUNT\n" +
            "     , IFNULL(B.REVIEW_RATING, 0) AS REVIEW_RATING\n" +
            "    FROM CONTENTS A\n" +
            "    LEFT JOIN (\n" +
            "        SELECT C.CONTENTS_ID\n" +
            "             , COUNT(C.CONTENTS_ID) AS REVIEW_COUNT\n" +
            "             , AVG(C.REVIEW_RATING) AS REVIEW_RATING\n" +
            "            FROM REVIEW C\n" +
            "    ) B\n" +
            "    ON A.contents_id = B.contents_id\n" +
            "    JOIN payment_content D\n" +
            "    ON A.contents_id = D.contents_id",
        countQuery = "SELECT COUNT(*) " +
                " FROM (" +
                "   SELECT A.CONTENTS_ID\n" +
                "     , A.CATEGORY\n" +
                "     , A.CONTENTS_TITLE\n" +
                "     , A.INTRODUCE\n" +
                "     , A.PRICE\n" +
                "     , A.PRICE_TYPE\n" +
                "     , A.REG_DATE\n" +
                "     , A.THUMBNAIL\n" +
                "     , A.MEMBER_ID\n" +
                "     , A.MOD_DATE\n" +
                "     , IFNULL(B.REVIEW_COUNT, 0) AS REVIEW_COUNT\n" +
                "     , IFNULL(B.REVIEW_RATING, 0) AS REVIEW_RATING\n" +
                "    FROM CONTENTS A\n" +
                "    LEFT JOIN (\n" +
                "        SELECT C.CONTENTS_ID\n" +
                "             , COUNT(C.CONTENTS_ID) AS REVIEW_COUNT\n" +
                "             , AVG(C.REVIEW_RATING) AS REVIEW_RATING\n" +
                "            FROM REVIEW C\n" +
                "    ) B\n" +
                "    ON A.contents_id = B.contents_id\n" +
                "    JOIN payment_content D\n" +
                "    ON A.contents_id = D.contents_id" +
                ") D", nativeQuery = true
    )
    Page<Contents> searchMyAll(Pageable pageable, long memberId);
}
