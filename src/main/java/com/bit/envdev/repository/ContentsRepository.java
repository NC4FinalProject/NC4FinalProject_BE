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

    @Query(value = "SELECT * FROM Contents WHERE price = 0", countQuery = "SELECT count(*) FROM Contents WHERE price = 0",nativeQuery = true)
    Page<Contents> searchAllFree(Pageable pageable);

    @Query(value = "SELECT * FROM Contents WHERE price != 0 AND price != -1", countQuery = "SELECT count(*) FROM Contents WHERE price != 0 AND price != -1",nativeQuery = true)
    Page<Contents> searchAllPay(Pageable pageable);

    @Query(value = "SELECT * FROM Contents WHERE price = -1", countQuery = "SELECT count(*) FROM Contents WHERE price != -1",nativeQuery = true)
    Page<Contents> searchAllNational(Pageable pageable);

    @Query(value = "SELECT * FROM Contents WHERE category = :category", countQuery = "SELECT count(*) FROM Contents WHERE category = :category", nativeQuery = true)
    Page<Contents> searchAllCategory(Pageable pageable, String category);

    @Query(value = "SELECT * FROM Contents WHERE category = :category AND price = 0", countQuery = "SELECT count(*) FROM Contents WHERE category = :category AND price = 0", nativeQuery = true)
    Page<Contents> searchAllCategoryFree(Pageable pageable, String category);

    @Query(value = "SELECT * FROM Contents WHERE category = :category AND price != 0 AND price != -1", countQuery = "SELECT count(*) FROM Contents WHERE category = :category AND price != 0 AND price != -1", nativeQuery = true)
    Page<Contents> searchAllCategoryPay(Pageable pageable, String category);

    @Query(value = "SELECT * FROM Contents WHERE category = :category AND price = -1", countQuery = "SELECT count(*) FROM Contents WHERE category = :category AND price = -1", nativeQuery = true)
    Page<Contents> searchAllCategoryNational(Pageable pageable, String category);

    @Query(value = "SELECT * FROM Contents WHERE price = 0 ORDER BY reg_date DESC", countQuery = "SELECT count(*) FROM Contents WHERE price = 0 ORDER BY reg_date DESC",nativeQuery = true)
    Page<Contents> searchAllFreeReg(Pageable pageable);

    @Query(value = "SELECT * FROM Contents WHERE price != 0 AND price != -1 ORDER BY reg_date DESC", countQuery = "SELECT count(*) FROM Contents WHERE price != 0 AND price != -1 ORDER BY reg_date DESC",nativeQuery = true)
    Page<Contents> searchAllPayReg(Pageable pageable);

    @Query(value = "SELECT * FROM Contents WHERE price = -1 ORDER BY reg_date DESC", countQuery = "SELECT count(*) FROM Contents WHERE price != -1 ORDER BY reg_date DESC",nativeQuery = true)
    Page<Contents> searchAllNationalReg(Pageable pageable);

    @Query(value = "SELECT * FROM Contents WHERE category = :category ORDER BY reg_date DESC", countQuery = "SELECT count(*) FROM Contents WHERE category = :category ORDER BY reg_date DESC",nativeQuery = true)
    Page<Contents> searchAllCategoryReg(Pageable pageable, String category);

    @Query(value = "SELECT * FROM Contents WHERE category = :category AND price = 0 ORDER BY reg_date DESC", countQuery = "SELECT count(*) FROM Contents WHERE category = :category AND price = 0 ORDER BY reg_date DESC", nativeQuery = true)
    Page<Contents> searchAllCategoryFreeReg(Pageable pageable, String category);

    @Query(value = "SELECT * FROM Contents WHERE category = :category AND price != 0 AND price != -1 ORDER BY reg_date DESC", countQuery = "SELECT count(*) FROM Contents WHERE category = :category AND price != 0 AND price != -1 ORDER BY reg_date DESC", nativeQuery = true)
    Page<Contents> searchAllCategoryPayReg(Pageable pageable, String category);

    @Query(value = "SELECT * FROM Contents WHERE category = :category AND price = -1 ORDER BY reg_date DESC", countQuery = "SELECT count(*) FROM Contents WHERE category = :category AND price = -1 ORDER BY reg_date DESC", nativeQuery = true)
    Page<Contents> searchAllCategoryNationalReg(Pageable pageable, String category);

    @Query(value = "SELECT A.*\n" +
            "     , IFNULL(B.PAYMENT_COUNT, 0) AS PAYMENT_COUNT\n" +
            "    FROM CONTENTS A\n" +
            "    LEFT JOIN (\n" +
            "        SELECT CONTENTS_ID\n" +
            "             , COUNT(CONTENTS_ID) AS PAYMENT_COUNT\n" +
            "            FROM payment_content\n" +
            "            GROUP BY CONTENTS_ID\n" +
            "    ) B\n" +
            "    ON A.contents_id = B.contents_id\n" +
            "   WHERE A.price = 0" +
            "    ORDER BY B.PAYMENT_COUNT DESC",
    countQuery = "SELECT COUNT(D.*)" +
            "FROM (" +
            "   SELECT A.*\n" +
            "     , IFNULL(B.PAYMENT_COUNT, 0) AS PAYMENT_COUNT\n" +
            "    FROM CONTENTS A\n" +
            "    LEFT JOIN (\n" +
            "        SELECT CONTENTS_ID\n" +
            "             , COUNT(CONTENTS_ID) AS PAYMENT_COUNT\n" +
            "            FROM payment_content\n" +
            "            GROUP BY CONTENTS_ID\n" +
            "    ) B\n" +
            "    ON A.contents_id = B.contents_id\n" +
            "   WHERE A.price = 0" +
            "    ORDER BY B.PAYMENT_COUNT DESC" +
            ") D", nativeQuery = true)
    Page<Contents> searchAllFreeSale(Pageable pageable);

    @Query(value = "SELECT A.*\n" +
            "     , IFNULL(B.PAYMENT_COUNT, 0) AS PAYMENT_COUNT\n" +
            "    FROM CONTENTS A\n" +
            "    LEFT JOIN (\n" +
            "        SELECT CONTENTS_ID\n" +
            "             , COUNT(CONTENTS_ID) AS PAYMENT_COUNT\n" +
            "            FROM payment_content\n" +
            "            GROUP BY CONTENTS_ID\n" +
            "    ) B\n" +
            "    ON A.contents_id = B.contents_id\n" +
            "   WHERE A.price != 0" +
            "     AND A.price != -1" +
            "    ORDER BY B.PAYMENT_COUNT DESC",
            countQuery = "SELECT COUNT(D.*)" +
                    "FROM (" +
                    "   SELECT A.*\n" +
                    "     , IFNULL(B.PAYMENT_COUNT, 0) AS PAYMENT_COUNT\n" +
                    "    FROM CONTENTS A\n" +
                    "    LEFT JOIN (\n" +
                    "        SELECT CONTENTS_ID\n" +
                    "             , COUNT(CONTENTS_ID) AS PAYMENT_COUNT\n" +
                    "            FROM payment_content\n" +
                    "            GROUP BY CONTENTS_ID\n" +
                    "    ) B\n" +
                    "    ON A.contents_id = B.contents_id\n" +
                    "   WHERE A.price = 0" +
                    "     AND A.price != -1" +
                    "    ORDER BY B.PAYMENT_COUNT DESC" +
                    ") D", nativeQuery = true)
    Page<Contents> searchAllPaySale(Pageable pageable);

    @Query(value = "SELECT A.*\n" +
            "     , IFNULL(B.PAYMENT_COUNT, 0) AS PAYMENT_COUNT\n" +
            "    FROM CONTENTS A\n" +
            "    LEFT JOIN (\n" +
            "        SELECT CONTENTS_ID\n" +
            "             , COUNT(CONTENTS_ID) AS PAYMENT_COUNT\n" +
            "            FROM payment_content\n" +
            "            GROUP BY CONTENTS_ID\n" +
            "    ) B\n" +
            "    ON A.contents_id = B.contents_id\n" +
            "   WHERE A.price = -1" +
            "    ORDER BY B.PAYMENT_COUNT DESC",
            countQuery = "SELECT COUNT(D.*)" +
                    "FROM (" +
                    "   SELECT A.*\n" +
                    "     , IFNULL(B.PAYMENT_COUNT, 0) AS PAYMENT_COUNT\n" +
                    "    FROM CONTENTS A\n" +
                    "    LEFT JOIN (\n" +
                    "        SELECT CONTENTS_ID\n" +
                    "             , COUNT(CONTENTS_ID) AS PAYMENT_COUNT\n" +
                    "            FROM payment_content\n" +
                    "            GROUP BY CONTENTS_ID\n" +
                    "    ) B\n" +
                    "    ON A.contents_id = B.contents_id\n" +
                    "   WHERE A.price = -1" +
                    "    ORDER BY B.PAYMENT_COUNT DESC" +
                    ") D", nativeQuery = true)
    Page<Contents> searchAllNationalSale(Pageable pageable);

    @Query(value = "SELECT A.*\n" +
            "     , IFNULL(B.PAYMENT_COUNT, 0) AS PAYMENT_COUNT\n" +
            "    FROM CONTENTS A\n" +
            "    LEFT JOIN (\n" +
            "        SELECT CONTENTS_ID\n" +
            "             , COUNT(CONTENTS_ID) AS PAYMENT_COUNT\n" +
            "            FROM payment_content\n" +
            "            GROUP BY CONTENTS_ID\n" +
            "    ) B\n" +
            "    ON A.contents_id = B.contents_id\n" +
            "   WHERE A.category = :category" +
            "    ORDER BY B.PAYMENT_COUNT DESC",
            countQuery = "SELECT COUNT(D.*)" +
                    "FROM (" +
                    "   SELECT A.*\n" +
                    "     , IFNULL(B.PAYMENT_COUNT, 0) AS PAYMENT_COUNT\n" +
                    "    FROM CONTENTS A\n" +
                    "    LEFT JOIN (\n" +
                    "        SELECT CONTENTS_ID\n" +
                    "             , COUNT(CONTENTS_ID) AS PAYMENT_COUNT\n" +
                    "            FROM payment_content\n" +
                    "            GROUP BY CONTENTS_ID\n" +
                    "    ) B\n" +
                    "    ON A.contents_id = B.contents_id\n" +
                    "   WHERE A.category = :category" +
                    "    ORDER BY B.PAYMENT_COUNT DESC" +
                    ") D", nativeQuery = true)
    Page<Contents> searchAllCategorySale(Pageable pageable, String category);

    @Query(value = "SELECT A.*\n" +
            "     , IFNULL(B.PAYMENT_COUNT, 0) AS PAYMENT_COUNT\n" +
            "    FROM CONTENTS A\n" +
            "    LEFT JOIN (\n" +
            "        SELECT CONTENTS_ID\n" +
            "             , COUNT(CONTENTS_ID) AS PAYMENT_COUNT\n" +
            "            FROM payment_content\n" +
            "            GROUP BY CONTENTS_ID\n" +
            "    ) B\n" +
            "    ON A.contents_id = B.contents_id\n" +
            "   WHERE A.category = :category" +
            "     AND A.price = 0" +
            "    ORDER BY B.PAYMENT_COUNT DESC",
            countQuery = "SELECT COUNT(D.*)" +
                    "FROM (" +
                    "   SELECT A.*\n" +
                    "     , IFNULL(B.PAYMENT_COUNT, 0) AS PAYMENT_COUNT\n" +
                    "    FROM CONTENTS A\n" +
                    "    LEFT JOIN (\n" +
                    "        SELECT CONTENTS_ID\n" +
                    "             , COUNT(CONTENTS_ID) AS PAYMENT_COUNT\n" +
                    "            FROM payment_content\n" +
                    "            GROUP BY CONTENTS_ID\n" +
                    "    ) B\n" +
                    "    ON A.contents_id = B.contents_id\n" +
                    "   WHERE A.category = :category" +
                    "     AND A.price = 0" +
                    "    ORDER BY B.PAYMENT_COUNT DESC" +
                    ") D", nativeQuery = true)
    Page<Contents> searchAllCategoryFreeSale(Pageable pageable, String category);

    @Query(value = "SELECT A.*\n" +
            "     , IFNULL(B.PAYMENT_COUNT, 0) AS PAYMENT_COUNT\n" +
            "    FROM CONTENTS A\n" +
            "    LEFT JOIN (\n" +
            "        SELECT CONTENTS_ID\n" +
            "             , COUNT(CONTENTS_ID) AS PAYMENT_COUNT\n" +
            "            FROM payment_content\n" +
            "            GROUP BY CONTENTS_ID\n" +
            "    ) B\n" +
            "    ON A.contents_id = B.contents_id\n" +
            "   WHERE A.category = :category" +
            "     AND A.price = 0" +
            "     AND A.price != -1" +
            "    ORDER BY B.PAYMENT_COUNT DESC",
            countQuery = "SELECT COUNT(D.*)" +
                    "FROM (" +
                    "   SELECT A.*\n" +
                    "     , IFNULL(B.PAYMENT_COUNT, 0) AS PAYMENT_COUNT\n" +
                    "    FROM CONTENTS A\n" +
                    "    LEFT JOIN (\n" +
                    "        SELECT CONTENTS_ID\n" +
                    "             , COUNT(CONTENTS_ID) AS PAYMENT_COUNT\n" +
                    "            FROM payment_content\n" +
                    "            GROUP BY CONTENTS_ID\n" +
                    "    ) B\n" +
                    "    ON A.contents_id = B.contents_id\n" +
                    "   WHERE A.category = :category" +
                    "     AND A.price != 0" +
                    "     AND A.price != -1" +
                    "    ORDER BY B.PAYMENT_COUNT DESC" +
                    ") D", nativeQuery = true)
    Page<Contents> searchAllCategoryPaySale(Pageable pageable, String category);

    @Query(value = "SELECT A.*\n" +
            "     , IFNULL(B.PAYMENT_COUNT, 0) AS PAYMENT_COUNT\n" +
            "    FROM CONTENTS A\n" +
            "    LEFT JOIN (\n" +
            "        SELECT CONTENTS_ID\n" +
            "             , COUNT(CONTENTS_ID) AS PAYMENT_COUNT\n" +
            "            FROM payment_content\n" +
            "            GROUP BY CONTENTS_ID\n" +
            "    ) B\n" +
            "    ON A.contents_id = B.contents_id\n" +
            "   WHERE A.category = :category" +
            "     AND A.price = -1" +
            "    ORDER BY B.PAYMENT_COUNT DESC",
            countQuery = "SELECT COUNT(D.*)" +
                    "FROM (" +
                    "   SELECT A.*\n" +
                    "     , IFNULL(B.PAYMENT_COUNT, 0) AS PAYMENT_COUNT\n" +
                    "    FROM CONTENTS A\n" +
                    "    LEFT JOIN (\n" +
                    "        SELECT CONTENTS_ID\n" +
                    "             , COUNT(CONTENTS_ID) AS PAYMENT_COUNT\n" +
                    "            FROM payment_content\n" +
                    "            GROUP BY CONTENTS_ID\n" +
                    "    ) B\n" +
                    "    ON A.contents_id = B.contents_id\n" +
                    "   WHERE A.category = :category" +
                    "     AND A.price = -1" +
                    "    ORDER BY B.PAYMENT_COUNT DESC" +
                    ") D", nativeQuery = true)
    Page<Contents> searchAllCategoryNationalSale(Pageable pageable, String category);

    @Query(value = "SELECT A.*\n" +
            "     , IFNULL(B.REVIEW_RAITING, 0) AS REVIEW_RAITING\n" +
            "     , IFNULL(B.REVIEW_COUNT, 0) AS REVIEW_COUNT\n" +
            "    FROM CONTENTS A\n" +
            "    LEFT JOIN (\n" +
            "        SELECT CONTENTS_ID\n" +
            "             , AVG(review_rating) AS REVIEW_RAITING\n" +
            "             , COUNT(CONTENTS_ID) AS REVIEW_COUNT\n" +
            "            FROM review\n" +
            "            GROUP BY CONTENTS_ID\n" +
            "    ) B\n" +
            "    ON A.contents_id = B.contents_id\n" +
            "    WHERE A.price = 0" +
            "    ORDER BY B.REVIEW_RAITING DESC, B.REVIEW_COUNT DESC",
    countQuery = "SELECT COUNT(D.*)" +
            "   FROM (" +
            "   SELECT A.*\n" +
            "     , IFNULL(B.REVIEW_RAITING, 0) AS REVIEW_RAITING\n" +
            "     , IFNULL(B.REVIEW_COUNT, 0) AS REVIEW_COUNT\n" +
            "    FROM CONTENTS A\n" +
            "    LEFT JOIN (\n" +
            "        SELECT CONTENTS_ID\n" +
            "             , AVG(review_rating) AS REVIEW_RAITING\n" +
            "             , COUNT(CONTENTS_ID) AS REVIEW_COUNT\n" +
            "            FROM review\n" +
            "            GROUP BY CONTENTS_ID\n" +
            "    ) B\n" +
            "    ON A.contents_id = B.contents_id" +
            "    WHERE A.price = 0" +
            "    ORDER BY B.REVIEW_RAITING DESC, B.REVIEW_COUNT DESC" +
            ") D", nativeQuery = true)
    Page<Contents> searchAllFreePop(Pageable pageable);

    @Query(value = "SELECT A.*\n" +
            "     , IFNULL(B.REVIEW_RAITING, 0) AS REVIEW_RAITING\n" +
            "     , IFNULL(B.REVIEW_COUNT, 0) AS REVIEW_COUNT\n" +
            "    FROM CONTENTS A\n" +
            "    LEFT JOIN (\n" +
            "        SELECT CONTENTS_ID\n" +
            "             , AVG(review_rating) AS REVIEW_RAITING\n" +
            "             , COUNT(CONTENTS_ID) AS REVIEW_COUNT\n" +
            "            FROM review\n" +
            "            GROUP BY CONTENTS_ID\n" +
            "    ) B\n" +
            "    ON A.contents_id = B.contents_id\n" +
            "    WHERE A.price != 0" +
            "      AND A.price != -1" +
            "    ORDER BY B.REVIEW_RAITING DESC, B.REVIEW_COUNT DESC",
            countQuery = "SELECT COUNT(D.*)" +
                    "   FROM (" +
                    "   SELECT A.*\n" +
                    "     , IFNULL(B.REVIEW_RAITING, 0) AS REVIEW_RAITING\n" +
                    "     , IFNULL(B.REVIEW_COUNT, 0) AS REVIEW_COUNT\n" +
                    "    FROM CONTENTS A\n" +
                    "    LEFT JOIN (\n" +
                    "        SELECT CONTENTS_ID\n" +
                    "             , AVG(review_rating) AS REVIEW_RAITING\n" +
                    "             , COUNT(CONTENTS_ID) AS REVIEW_COUNT\n" +
                    "            FROM review\n" +
                    "            GROUP BY CONTENTS_ID\n" +
                    "    ) B\n" +
                    "    ON A.contents_id = B.contents_id" +
                    "    WHERE A.price != 0" +
                    "      AND A.price != -1" +
                    "    ORDER BY B.REVIEW_RAITING DESC, B.REVIEW_COUNT DESC" +
                    ") D", nativeQuery = true)
    Page<Contents> searchAllPayPop(Pageable pageable);

    @Query(value = "SELECT A.*\n" +
            "     , IFNULL(B.REVIEW_RAITING, 0) AS REVIEW_RAITING\n" +
            "     , IFNULL(B.REVIEW_COUNT, 0) AS REVIEW_COUNT\n" +
            "    FROM CONTENTS A\n" +
            "    LEFT JOIN (\n" +
            "        SELECT CONTENTS_ID\n" +
            "             , AVG(review_rating) AS REVIEW_RAITING\n" +
            "             , COUNT(CONTENTS_ID) AS REVIEW_COUNT\n" +
            "            FROM review\n" +
            "            GROUP BY CONTENTS_ID\n" +
            "    ) B\n" +
            "    ON A.contents_id = B.contents_id\n" +
            "    WHERE A.price = -1" +
            "    ORDER BY B.REVIEW_RAITING DESC, B.REVIEW_COUNT DESC",
            countQuery = "SELECT COUNT(D.*)" +
                    "   FROM (" +
                    "   SELECT A.*\n" +
                    "     , IFNULL(B.REVIEW_RAITING, 0) AS REVIEW_RAITING\n" +
                    "     , IFNULL(B.REVIEW_COUNT, 0) AS REVIEW_COUNT\n" +
                    "    FROM CONTENTS A\n" +
                    "    LEFT JOIN (\n" +
                    "        SELECT CONTENTS_ID\n" +
                    "             , AVG(review_rating) AS REVIEW_RAITING\n" +
                    "             , COUNT(CONTENTS_ID) AS REVIEW_COUNT\n" +
                    "            FROM review\n" +
                    "            GROUP BY CONTENTS_ID\n" +
                    "    ) B\n" +
                    "    ON A.contents_id = B.contents_id" +
                    "    WHERE A.price = -1" +
                    "    ORDER BY B.REVIEW_RAITING DESC, B.REVIEW_COUNT DESC" +
                    ") D", nativeQuery = true)
    Page<Contents> searchAllNationalPop(Pageable pageable);

    @Query(value = "SELECT A.*\n" +
            "     , IFNULL(B.REVIEW_RAITING, 0) AS REVIEW_RAITING\n" +
            "     , IFNULL(B.REVIEW_COUNT, 0) AS REVIEW_COUNT\n" +
            "    FROM CONTENTS A\n" +
            "    LEFT JOIN (\n" +
            "        SELECT CONTENTS_ID\n" +
            "             , AVG(review_rating) AS REVIEW_RAITING\n" +
            "             , COUNT(CONTENTS_ID) AS REVIEW_COUNT\n" +
            "            FROM review\n" +
            "            GROUP BY CONTENTS_ID\n" +
            "    ) B\n" +
            "    ON A.contents_id = B.contents_id\n" +
            "    WHERE A.category = :category" +
            "    ORDER BY B.REVIEW_RAITING DESC, B.REVIEW_COUNT DESC",
            countQuery = "SELECT COUNT(D.*)" +
                    "   FROM (" +
                    "   SELECT A.*\n" +
                    "     , IFNULL(B.REVIEW_RAITING, 0) AS REVIEW_RAITING\n" +
                    "     , IFNULL(B.REVIEW_COUNT, 0) AS REVIEW_COUNT\n" +
                    "    FROM CONTENTS A\n" +
                    "    LEFT JOIN (\n" +
                    "        SELECT CONTENTS_ID\n" +
                    "             , AVG(review_rating) AS REVIEW_RAITING\n" +
                    "             , COUNT(CONTENTS_ID) AS REVIEW_COUNT\n" +
                    "            FROM review\n" +
                    "            GROUP BY CONTENTS_ID\n" +
                    "    ) B\n" +
                    "    ON A.contents_id = B.contents_id" +
                    "    WHERE A.category = :category" +
                    "    ORDER BY B.REVIEW_RAITING DESC, B.REVIEW_COUNT DESC" +
                    ") D", nativeQuery = true)
    Page<Contents> searchAllCategoryPop(Pageable pageable, String category);

    @Query(value = "SELECT A.*\n" +
            "     , IFNULL(B.REVIEW_RAITING, 0) AS REVIEW_RAITING\n" +
            "     , IFNULL(B.REVIEW_COUNT, 0) AS REVIEW_COUNT\n" +
            "    FROM CONTENTS A\n" +
            "    LEFT JOIN (\n" +
            "        SELECT CONTENTS_ID\n" +
            "             , AVG(review_rating) AS REVIEW_RAITING\n" +
            "             , COUNT(CONTENTS_ID) AS REVIEW_COUNT\n" +
            "            FROM review\n" +
            "            GROUP BY CONTENTS_ID\n" +
            "    ) B\n" +
            "    ON A.contents_id = B.contents_id\n" +
            "    WHERE A.category = :category" +
            "      AND A.price = 0" +
            "    ORDER BY B.REVIEW_RAITING DESC, B.REVIEW_COUNT DESC",
            countQuery = "SELECT COUNT(D.*)" +
                    "   FROM (" +
                    "   SELECT A.*\n" +
                    "     , IFNULL(B.REVIEW_RAITING, 0) AS REVIEW_RAITING\n" +
                    "     , IFNULL(B.REVIEW_COUNT, 0) AS REVIEW_COUNT\n" +
                    "    FROM CONTENTS A\n" +
                    "    LEFT JOIN (\n" +
                    "        SELECT CONTENTS_ID\n" +
                    "             , AVG(review_rating) AS REVIEW_RAITING\n" +
                    "             , COUNT(CONTENTS_ID) AS REVIEW_COUNT\n" +
                    "            FROM review\n" +
                    "            GROUP BY CONTENTS_ID\n" +
                    "    ) B\n" +
                    "    ON A.contents_id = B.contents_id" +
                    "    WHERE A.category = :category" +
                    "      AND A.price = 0" +
                    "    ORDER BY B.REVIEW_RAITING DESC, B.REVIEW_COUNT DESC" +
                    ") D", nativeQuery = true)
    Page<Contents> searchAllCategoryFreePop(Pageable pageable, String category);

    @Query(value = "SELECT A.*\n" +
            "     , IFNULL(B.REVIEW_RAITING, 0) AS REVIEW_RAITING\n" +
            "     , IFNULL(B.REVIEW_COUNT, 0) AS REVIEW_COUNT\n" +
            "    FROM CONTENTS A\n" +
            "    LEFT JOIN (\n" +
            "        SELECT CONTENTS_ID\n" +
            "             , AVG(review_rating) AS REVIEW_RAITING\n" +
            "             , COUNT(CONTENTS_ID) AS REVIEW_COUNT\n" +
            "            FROM review\n" +
            "            GROUP BY CONTENTS_ID\n" +
            "    ) B\n" +
            "    ON A.contents_id = B.contents_id\n" +
            "    WHERE A.category = :category" +
            "      AND A.price != 0" +
            "      AND A.price != -1" +
            "    ORDER BY B.REVIEW_RAITING DESC, B.REVIEW_COUNT DESC",
            countQuery = "SELECT COUNT(D.*)" +
                    "   FROM (" +
                    "   SELECT A.*\n" +
                    "     , IFNULL(B.REVIEW_RAITING, 0) AS REVIEW_RAITING\n" +
                    "     , IFNULL(B.REVIEW_COUNT, 0) AS REVIEW_COUNT\n" +
                    "    FROM CONTENTS A\n" +
                    "    LEFT JOIN (\n" +
                    "        SELECT CONTENTS_ID\n" +
                    "             , AVG(review_rating) AS REVIEW_RAITING\n" +
                    "             , COUNT(CONTENTS_ID) AS REVIEW_COUNT\n" +
                    "            FROM review\n" +
                    "            GROUP BY CONTENTS_ID\n" +
                    "    ) B\n" +
                    "    ON A.contents_id = B.contents_id" +
                    "    WHERE A.category = :category" +
                    "      AND A.price != 0" +
                    "      AND A.price != -1" +
                    "    ORDER BY B.REVIEW_RAITING DESC, B.REVIEW_COUNT DESC" +
                    ") D", nativeQuery = true)
    Page<Contents> searchAllCategoryPayPop(Pageable pageable, String category);

    @Query(value = "SELECT A.*\n" +
            "     , IFNULL(B.REVIEW_RAITING, 0) AS REVIEW_RAITING\n" +
            "     , IFNULL(B.REVIEW_COUNT, 0) AS REVIEW_COUNT\n" +
            "    FROM CONTENTS A\n" +
            "    LEFT JOIN (\n" +
            "        SELECT CONTENTS_ID\n" +
            "             , AVG(review_rating) AS REVIEW_RAITING\n" +
            "             , COUNT(CONTENTS_ID) AS REVIEW_COUNT\n" +
            "            FROM review\n" +
            "            GROUP BY CONTENTS_ID\n" +
            "    ) B\n" +
            "    ON A.contents_id = B.contents_id\n" +
            "    WHERE A.category = :category" +
            "      AND A.price = -1" +
            "    ORDER BY B.REVIEW_RAITING DESC, B.REVIEW_COUNT DESC",
            countQuery = "SELECT COUNT(D.*)" +
                    "   FROM (" +
                    "   SELECT A.*\n" +
                    "     , IFNULL(B.REVIEW_RAITING, 0) AS REVIEW_RAITING\n" +
                    "     , IFNULL(B.REVIEW_COUNT, 0) AS REVIEW_COUNT\n" +
                    "    FROM CONTENTS A\n" +
                    "    LEFT JOIN (\n" +
                    "        SELECT CONTENTS_ID\n" +
                    "             , AVG(review_rating) AS REVIEW_RAITING\n" +
                    "             , COUNT(CONTENTS_ID) AS REVIEW_COUNT\n" +
                    "            FROM review\n" +
                    "            GROUP BY CONTENTS_ID\n" +
                    "    ) B\n" +
                    "    ON A.contents_id = B.contents_id" +
                    "    WHERE A.category = :category" +
                    "      AND A.price = -1" +
                    "    ORDER BY B.REVIEW_RAITING DESC, B.REVIEW_COUNT DESC" +
                    ") D", nativeQuery = true)
    Page<Contents> searchAllCategoryNationalPop(Pageable pageable, String category);

    @Query(value = "SELECT A.*\n" +
            "     , IFNULL(B.PAYMENT_COUNT, 0) AS PAYMENT_COUNT\n" +
            "    FROM CONTENTS A\n" +
            "    LEFT JOIN (\n" +
            "        SELECT C.CONTENTS_ID\n" +
            "             , COUNT(C.CONTENTS_ID) AS PAYMENT_COUNT\n" +
            "            FROM payment_content C\n" +
            "            GROUP BY C.CONTENTS_ID\n" +
            "    ) B\n" +
            "    ON A.contents_id = B.contents_id\n" +
            "    ORDER BY B.PAYMENT_COUNT DESC",
            countQuery = "SELECT COUNT(D.*)" +
                    "FROM (" +
                    "   SELECT A.*\n" +
                    "     , IFNULL(B.PAYMENT_COUNT, 0) AS PAYMENT_COUNT\n" +
                    "    FROM CONTENTS A\n" +
                    "    LEFT JOIN (\n" +
                    "        SELECT C.CONTENTS_ID\n" +
                    "             , COUNT(C.CONTENTS_ID) AS PAYMENT_COUNT\n" +
                    "            FROM payment_content C\n" +
                    "            GROUP BY C.CONTENTS_ID\n" +
                    "    ) B\n" +
                    "    ON A.contents_id = B.contents_id\n" +
                    "    ORDER BY B.PAYMENT_COUNT DESC" +
                    ") D", nativeQuery = true)
    Page<ContentsDTO> searchAllSale(Pageable pageable);

    @Query(value = "SELECT A.*\n" +
            "     , IFNULL(B.REVIEW_RAITING, 0) AS REVIEW_RAITING\n" +
            "     , IFNULL(B.REVIEW_COUNT, 0) AS REVIEW_COUNT\n" +
            "    FROM CONTENTS A\n" +
            "    LEFT JOIN (\n" +
            "        SELECT C.CONTENTS_ID\n" +
            "             , AVG(C.review_rating) AS REVIEW_RAITING\n" +
            "             , COUNT(C.CONTENTS_ID) AS REVIEW_COUNT\n" +
            "            FROM review C\n" +
            "            GROUP BY C.CONTENTS_ID\n" +
            "    ) B\n" +
            "    ON A.contents_id = B.contents_id\n" +
            "    ORDER BY B.REVIEW_RAITING DESC, B.REVIEW_COUNT DESC",
            countQuery = "SELECT COUNT(D.*)" +
                    "   FROM (" +
                    "   SELECT A.*\n" +
                    "     , IFNULL(B.REVIEW_RAITING, 0) AS REVIEW_RAITING\n" +
                    "     , IFNULL(B.REVIEW_COUNT, 0) AS REVIEW_COUNT\n" +
                    "    FROM CONTENTS A\n" +
                    "    LEFT JOIN (\n" +
                    "        SELECT C.CONTENTS_ID\n" +
                    "             , AVG(C.review_rating) AS REVIEW_RAITING\n" +
                    "             , COUNT(C.CONTENTS_ID) AS REVIEW_COUNT\n" +
                    "            FROM review C\n" +
                    "            GROUP BY C.CONTENTS_ID\n" +
                    "    ) B\n" +
                    "    ON A.contents_id = B.contents_id" +
                    "    ORDER BY B.REVIEW_RAITING DESC, B.REVIEW_COUNT DESC" +
                    ") D", nativeQuery = true)
    Page<ContentsDTO> searchAllPop(Pageable pageable);

    @Query(value = "SELECT * FROM Contents ORDER BY reg_date DESC", countQuery = "SELECT count(*) FROM Contents ORDER BY reg_date DESC",nativeQuery = true)
    Page<ContentsDTO> searchAllReg(Pageable pageable);
}
