package com.bit.envdev.controller;

import java.io.IOException;
import java.util.*;

import com.bit.envdev.common.FileUtils;
import com.bit.envdev.dto.*;
import com.bit.envdev.entity.Contents;
import com.bit.envdev.entity.CustomUserDetails;
import com.bit.envdev.entity.VideoReply;
import com.bit.envdev.service.ContentsService;
import lombok.RequiredArgsConstructor;
import com.bit.envdev.service.ContentsBookmarkService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/contents")
public class ContentsController {

    private final ContentsService contentsService;
    private final ContentsBookmarkService contentsBookmarkService;
    private final FileUtils fileUtils;
    private List<String> temporaryImage = new ArrayList<>();


    // 컨텐츠 등록 하기
    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestPart("insertRequestDTO") InsertRequestDTO insertRequestDTO,
                                    @RequestPart("thumbnail") MultipartFile thumbnail,
                                    @RequestPart("videoFile") MultipartFile[] videoFile,
                                    @RequestPart(value = "contentsFileDTOList", required = false) List<ContentsFileDTO> contentsFileDTOList,
                                    @AuthenticationPrincipal CustomUserDetails customUserDetails) throws IOException {
        ResponseDTO<ContentsDTO> responseDTO = new ResponseDTO<>();
        Long memberId = customUserDetails.getId();
        // 가져온 정보 나눠담기
        ContentsDTO contentsDTO = insertRequestDTO.getContentsDTO();
        contentsDTO.setContentsFileDTOList(contentsFileDTOList);
        List<SectionDTO> sectionDTOList = insertRequestDTO.getSectionDTO();
        List<VideoDTO> videoDTOList = insertRequestDTO.getVideoDTO();
        // 기본 정보 밀어넣기, 썸네일 파일 밀어넣기
        Contents createdContents = contentsService.createContents(contentsDTO, memberId, thumbnail );
        // 비디오 정보 밀어넣기, 동영상 파일 밀어 넣기
        for (int i = 0; i < videoDTOList.size(); i++) {
            contentsService.createVideo(videoDTOList.get(i), createdContents, memberId, videoFile[i]);
        }
        // 섹션 정보 밀어넣기
        sectionDTOList.forEach(sectionDTO -> {contentsService.createSection(sectionDTO, createdContents);});
        return null;
    }
    // // 컨텐츠 상세 보기 * 유저 닉네임 리스폰 추가
    @GetMapping("/detail/{contentsId}")
    public ResponseEntity<?> Detail(@PathVariable(name = "contentsId") int contentsId, @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        ResponseDTO<ContentsDTO> responseDTO = new ResponseDTO<>();
        ContentsDTO contentsDTO = contentsService.findById(contentsId, customUserDetails);
        responseDTO.setItem(contentsDTO);
        return ResponseEntity.ok(responseDTO);
    }

    // // 컨텐츠 목록 보기 * 유저 네임, 유저 닉네임 프로필 리스폰 추가
    @GetMapping("/list")
    public ResponseEntity<?> listContents(@PageableDefault(page = 0, size = 16) Pageable pageable,
                                          @RequestParam("category") String category,
                                          @RequestParam("pricePattern") String pricePattern,
                                          @RequestParam("orderType") String orderType,
                                          @RequestParam("searchKeyword") String searchKeyword) {
        // ResponseDTO 객체 생성
        ResponseDTO<ContentsDTO> responseDTO = new ResponseDTO<>();
        // contentsService에서 모든 컨텐츠를 조회하여 ContentsDTO 리스트로 가져옴
        Page<ContentsDTO> contentsDTOList = contentsService.searchAll(pageable, category, pricePattern, orderType, searchKeyword);
        
        responseDTO.setPageItems(contentsDTOList);
        // ResponseDTO 객체를 ResponseEntity를 통해 클라이언트에게 반환
        return ResponseEntity.ok(responseDTO);
    }

    @PostMapping("/detail/saveVideoReply")
    public ResponseEntity<?> saveVideoReply(@RequestBody VideoReplyDTO videoReplyDTO,
                                    @AuthenticationPrincipal CustomUserDetails customUserDetails) throws IOException {
        ResponseDTO<ContentsDTO> responseDTO = new ResponseDTO<>();
        videoReplyDTO.setMemberId(customUserDetails.getId());

        // 비디오별 댓글 저장하기
        VideoReply videoReply = contentsService.saveVideoReply(videoReplyDTO);
        System.out.println(videoReply);

        return null;
    }

    @GetMapping("/detail/getVideoReplyList")
    public ResponseEntity<?> getVideoReplyList(@RequestParam("contentsId") int contentsId,
                                               @RequestParam("videoId") int videoId,
                                               @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        // 여기서 contentsId와 videoId를 사용하여 필요한 로직 처리
        System.out.println("Requested contentsId: " + contentsId + ", videoId: " + videoId);

        // 예를 들어, 해당 contentsId와 videoId에 대한 댓글 목록을 조회하여 반환
        List<VideoReplyDTO> videoReplyDTOList = contentsService.getVideoReplyList(contentsId, videoId);
        return ResponseEntity.ok().body(videoReplyDTOList);
    }

    @PostMapping("/bookmark/{contentsId}")
    public ResponseEntity<?> bookmark(@PathVariable(name = "contentsId") int contentsId,
                                      @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        int bookmarkCount = contentsBookmarkService.getBookmarkContents(contentsId, customUserDetails.getMember().getMemberId());



        if(bookmarkCount == 0) {
            contentsBookmarkService.addBookmark(contentsId, customUserDetails.getMember().getMemberId());
        } else {
            contentsBookmarkService.removeBookmark(contentsId, customUserDetails.getMember().getMemberId());
        }

        ResponseDTO<ContentsDTO> responseDTO = new ResponseDTO<>();
        responseDTO.setStatusCode(HttpStatus.OK.value());
        return ResponseEntity.ok(responseDTO);
    }

    @PostMapping("/upload")
    public ResponseEntity<?> upload(MultipartFile upload) {
        Map<String, String> result = new HashMap<>();

        System.out.println(upload.getOriginalFilename());

        ContentsFileDTO contentsFileDTO = fileUtils.parseContentsFileInfo(upload, "board/");
        temporaryImage.add(contentsFileDTO.getContentsFilePath() + contentsFileDTO.getContentsFileName());

        result.put("url", "https://kr.object.ncloudstorage.com/envdev/" + contentsFileDTO.getContentsFilePath() + contentsFileDTO.getContentsFileName());
        result.put("inquiryFilePath", contentsFileDTO.getContentsFilePath());
        result.put("inquiryFileName", contentsFileDTO.getContentsFileName());
        result.put("inquiryFileOrigin", contentsFileDTO.getContentsFileOrigin());

        return ResponseEntity.ok(result);
    }

    @GetMapping("/mylist")
    public ResponseEntity<?> mylistContents(@PageableDefault(page = 0, size = 16) Pageable pageable,
                                            @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        // ResponseDTO 객체 생성
        ResponseDTO<ContentsDTO> responseDTO = new ResponseDTO<>();
        // contentsService에서 모든 컨텐츠를 조회하여 ContentsDTO 리스트로 가져옴
        Page<ContentsDTO> contentsDTOList = contentsService.searchMyAll(pageable, customUserDetails.getMember());
        responseDTO.setPageItems(contentsDTOList);
        // ResponseDTO 객체를 ResponseEntity를 통해 클라이언트에게 반환
        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping("/bookmarklist")
    public ResponseEntity<?> bookmarklistContents(@PageableDefault(page = 0, size = 16) Pageable pageable,
                                            @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        // ResponseDTO 객체 생성
        ResponseDTO<ContentsDTO> responseDTO = new ResponseDTO<>();
        // contentsService에서 모든 컨텐츠를 조회하여 ContentsDTO 리스트로 가져옴
        Page<ContentsDTO> contentsDTOList = contentsService.searchBookmarkAll(pageable, customUserDetails.getMember());
        responseDTO.setPageItems(contentsDTOList);
        // ResponseDTO 객체를 ResponseEntity를 통해 클라이언트에게 반환
        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping("/teachercontentslist")
    public ResponseEntity<?> teacherContentsList(@PageableDefault(page = 0, size = 15) Pageable pageable,
                                            @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        // ResponseDTO 객체 생성
        ResponseDTO<ContentsDTO> responseDTO = new ResponseDTO<>();
        // contentsService에서 모든 컨텐츠를 조회하여 ContentsDTO 리스트로 가져옴
        Page<ContentsDTO> contentsDTOList = contentsService.searchTeacherAll(pageable, customUserDetails.getMember());
        responseDTO.setPageItems(contentsDTOList);
        // ResponseDTO 객체를 ResponseEntity를 통해 클라이언트에게 반환
        return ResponseEntity.ok(responseDTO);
    }

    @DeleteMapping("/delete/{contentsId}")
    public ResponseEntity<?> delete(@PageableDefault(page = 0, size = 15) Pageable pageable,
                                     @PathVariable("contentsId") int contentsId,
                                     @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        // ResponseDTO 객체 생성
        ResponseDTO<ContentsDTO> responseDTO = new ResponseDTO<>();

        contentsService.deleteContents(contentsId);

        // contentsService에서 모든 컨텐츠를 조회하여 ContentsDTO 리스트로 가져옴
        Page<ContentsDTO> contentsDTOList = contentsService.searchTeacherAll(pageable, customUserDetails.getMember());
        responseDTO.setPageItems(contentsDTOList);
        // ResponseDTO 객체를 ResponseEntity를 통해 클라이언트에게 반환
        return ResponseEntity.ok(responseDTO);
    }
}

