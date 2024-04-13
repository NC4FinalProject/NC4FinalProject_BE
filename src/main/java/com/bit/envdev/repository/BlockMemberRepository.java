package com.bit.envdev.repository;

import com.bit.envdev.entity.BlockMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface BlockMemberRepository extends JpaRepository<BlockMember, Long> {
    @Modifying(clearAutomatically = true)
    @Query(value = "update BlockMember b set b.state=1 where b.reportId=:id")
    void updateStateById(Long id);

    @Modifying(clearAutomatically = true)
    @Query(value = "update BlockMember b set b.state=1 where b.blockPeriod<:now")
    void updateStateByBlockPeriod(LocalDateTime now);

    @Query(value = "select mb from BlockMember mb where mb.memberId = :memberId")
    List<BlockMember> getAllByMemberId(@Param("memberId") Long memberId);
}
