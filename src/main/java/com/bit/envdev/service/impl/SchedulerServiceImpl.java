package com.bit.envdev.service.impl;

import com.bit.envdev.constant.BlockState;
import com.bit.envdev.entity.BlockMember;
import com.bit.envdev.entity.Member;
import com.bit.envdev.entity.Report;
import com.bit.envdev.repository.*;
import com.bit.envdev.service.SchedulerService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SchedulerServiceImpl implements SchedulerService {
    private final MemberRepository memberRepository;
    private final ReportRepository reportRepository;
    private final BlockMemberRepository blockMemberRepository;
    private final BlockInquiryRepository blockInquiryRepository;
    private final BlockInquiryCommentRepository blockCommentRepository;

    @Override
    @Transactional
    @Scheduled(cron = "0 0 12 * * *")
    public void autoRegMemberBlock() {
        reportRepository.reportMember().stream()
                .filter(rm -> rm[0] instanceof List)
                .forEach(rm -> processReport((List<Report>) rm[0], (Long) rm[1]));
    }

    private void processReport(List<Report> reportList, Long refId) {

        Member member = memberRepository.findById(refId).orElseThrow();

        List<BlockMember> blockMembers = blockMemberRepository.getAllByMemberId(refId);
        if (isUnblocked(blockMembers)) {
            long count = countValidReports(reportList, blockMembers);
            if (count >= 5) {
                saveMemberBlock(member);
            }
        }
    }

    private boolean isUnblocked(List<BlockMember> blockMembers) {
        return blockMembers.isEmpty() || blockMembers.get(blockMembers.size() - 1).getState().equals(BlockState.UNBLOCKED);
    }

    private long countValidReports(List<Report> reportList, List<BlockMember> blockMembers) {
        LocalDateTime blockPeriod = blockMembers.isEmpty() ? null : blockMembers.get(blockMembers.size() - 1).getBlockPeriod();
        return reportList.stream()
                .filter(report -> blockPeriod == null || (blockPeriod.isBefore(report.getReportDate()) && report.getReportDate().isBefore(LocalDateTime.now())))
                .count();
    }

    private void saveMemberBlock(Member member) {
        BlockMember block = BlockMember.builder()
                .memberId(member.getMemberId())
                .build();
        blockMemberRepository.save(block);
    }

    @Override
    @Transactional
    @Scheduled(cron = "0 0 11 * * *")
    public void unBlocking() {
        blockMemberRepository.updateStateByBlockPeriod(LocalDateTime.now());
        blockInquiryRepository.updateStateByBlockPeriod(LocalDateTime.now());
        blockCommentRepository.updateStateByBlockPeriod(LocalDateTime.now());
    }
}
