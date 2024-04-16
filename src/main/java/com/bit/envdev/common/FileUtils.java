package com.bit.envdev.common;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.bit.envdev.configuration.NaverConfiguration;
import com.bit.envdev.dto.ContentsDTO;
import com.bit.envdev.dto.ContentsFileDTO;
import com.bit.envdev.dto.FileDTO;
import com.bit.envdev.dto.InquiryFileDTO;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.UUID;

@Component
public class FileUtils {
    private final AmazonS3 s3;

    public FileUtils(NaverConfiguration naverConfiguration) {
        s3 = AmazonS3ClientBuilder.standard()
                .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(
                        naverConfiguration.getEndPoint(), naverConfiguration.getRegionName()
                ))
                .withCredentials(new AWSStaticCredentialsProvider(
                        new BasicAWSCredentials(
                                naverConfiguration.getAccessKey(), naverConfiguration.getSecretKey()
                        )
                ))
                .build();
    }

    public FileDTO parseFileInfo(MultipartFile multipartFile, String directory) {
        //버킷 이름
        String bucketName = "envdev";

        // 리턴할 BoardFileDTO 객체 생성
        FileDTO fileDTO = new FileDTO();

        String fileOrigin = multipartFile.getOriginalFilename();

        // 같은 파일명으로 파일 업로드 하면 나중에 업로드된 파일로 덮어 써지기 때문에 날짜+랜덤값+파일명으로 파일명 변경
        SimpleDateFormat formater = new SimpleDateFormat("yyyyMMddHHmmsss");
        Date nowDate = new Date();

        String nowDateStr = formater.format(nowDate);

        UUID uuid = UUID.randomUUID();

        // 실제로 db와 서버에 저장될 파일명
        String fileName = nowDateStr + "_" + uuid.toString() + "_" + fileOrigin;

        // 파일 업로드 될 파일 경로
        String filePath = directory;

        // 오브젝트 스토리지에 파일 업로드
        try(InputStream fileIputStream = multipartFile.getInputStream()) {
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentType(multipartFile.getContentType());

            PutObjectRequest putObjectRequest = new PutObjectRequest(
                    bucketName,
                    directory + fileName,
                    fileIputStream,
                    objectMetadata
            ).withCannedAcl(CannedAccessControlList.PublicRead);

            s3.putObject(putObjectRequest);
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }

        // 리턴될 DTO에 값들 세팅
        fileDTO.setItemFileName(fileName);
        fileDTO.setItemFilePath(filePath);
        fileDTO.setItemFileOrigin(fileOrigin);

        return fileDTO;
    }


    public InquiryFileDTO parseInquiryFileInfo(MultipartFile multipartFile, String directory) {

        String bucketName = "envdev";

        InquiryFileDTO inquiryFileDTO = new InquiryFileDTO();

        String fileOrigin = multipartFile.getOriginalFilename();

        SimpleDateFormat formater = new SimpleDateFormat("yyyyMMddHHmmsss");
        Date nowDate = new Date();

        String nowDateStr = formater.format(nowDate);

        UUID uuid = UUID.randomUUID();


        String fileName = nowDateStr + "_" + uuid.toString() + "_" + fileOrigin;


        String filePath = directory;


        try(InputStream fileIputStream = multipartFile.getInputStream()) {
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentType(multipartFile.getContentType());

            PutObjectRequest putObjectRequest = new PutObjectRequest(
                    bucketName,
                    directory + fileName,
                    fileIputStream,
                    objectMetadata
            ).withCannedAcl(CannedAccessControlList.PublicRead);

            s3.putObject(putObjectRequest);
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }


        inquiryFileDTO.setInquiryFileName(fileName);
        inquiryFileDTO.setInquiryFilePath(filePath);
        inquiryFileDTO.setInquiryFileOrigin(fileOrigin);

        return inquiryFileDTO;
    }

    public void upload(MultipartFile[] uploadFiles, String directory) {
        String bucketName = "envdev";

        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmsss");
        Date nowDate = new Date();

        String nowDateStr = formatter.format(nowDate);

        UUID uuid = UUID.randomUUID();

        Arrays.stream(uploadFiles).forEach(multipartFile -> {
            String fileName = nowDateStr + "_" + uuid.toString() + "_" + multipartFile.getOriginalFilename();

            try(InputStream fileIputStream = multipartFile.getInputStream()) {
                ObjectMetadata objectMetadata = new ObjectMetadata();
                objectMetadata.setContentType(multipartFile.getContentType());

                PutObjectRequest putObjectRequest = new PutObjectRequest(
                        bucketName,
                        directory + fileName,
                        fileIputStream,
                        objectMetadata
                ).withCannedAcl(CannedAccessControlList.PublicRead);

                s3.putObject(putObjectRequest);
            } catch(Exception e) {
                System.out.println(e.getMessage());
            }
        });
    }


    public void deleteObject(String image) {
        String bucketName = "envdev";

        s3.deleteObject(new DeleteObjectRequest(bucketName, image));
    }

    public FileDTO uploadFile(MultipartFile multipartFile, String directory) {
        //버킷 이름
        String bucketName = "envdev";

        // 리턴할 BoardFileDTO 객체 생성
        FileDTO fileDTO = new FileDTO();

        String fileOrigin = multipartFile.getOriginalFilename();

        // 같은 파일명으로 파일 업로드 하면 나중에 업로드된 파일로 덮어 써지기 때문에 날짜+랜덤값+파일명으로 파일명 변경
        SimpleDateFormat formater = new SimpleDateFormat("yyyyMMddHHmmsss");
        Date nowDate = new Date();

        String nowDateStr = formater.format(nowDate);

        UUID uuid = UUID.randomUUID();

        // 실제로 db와 서버에 저장될 파일명
        String fileName = nowDateStr + "_" + uuid.toString() + "_" + fileOrigin;

        // 파일 업로드 될 파일 경로
        String filePath = directory;

        // 오브젝트 스토리지에 파일 업로드
        try(InputStream fileIputStream = multipartFile.getInputStream()) {
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentType(multipartFile.getContentType());

            PutObjectRequest putObjectRequest = new PutObjectRequest(
                    bucketName,
                    directory + fileName,
                    fileIputStream,
                    objectMetadata
            ).withCannedAcl(CannedAccessControlList.PublicRead);

            s3.putObject(putObjectRequest);
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }
        // 리턴될 DTO에 값들 세팅
        fileDTO.setItemFileName(fileName);
        fileDTO.setItemFilePath(filePath);
        fileDTO.setItemFileOrigin(fileOrigin);
        System.out.println(fileDTO);
        return fileDTO;
    }

    public ContentsFileDTO parseContentsFileInfo(MultipartFile multipartFile, String directory) {

        String bucketName = "envdev";

        ContentsFileDTO contentsFileDTO = new ContentsFileDTO();

        String fileOrigin = multipartFile.getOriginalFilename();

        SimpleDateFormat formater = new SimpleDateFormat("yyyyMMddHHmmsss");
        Date nowDate = new Date();

        String nowDateStr = formater.format(nowDate);

        UUID uuid = UUID.randomUUID();


        String fileName = nowDateStr + "_" + uuid.toString() + "_" + fileOrigin;


        String filePath = directory;


        try(InputStream fileIputStream = multipartFile.getInputStream()) {
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentType(multipartFile.getContentType());

            PutObjectRequest putObjectRequest = new PutObjectRequest(
                    bucketName,
                    directory + fileName,
                    fileIputStream,
                    objectMetadata
            ).withCannedAcl(CannedAccessControlList.PublicRead);

            s3.putObject(putObjectRequest);
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }


        contentsFileDTO.setContentsFileName(fileName);
        contentsFileDTO.setContentsFilePath(filePath);
        contentsFileDTO.setContentsFileOrigin(fileOrigin);

        return contentsFileDTO;
    }
}