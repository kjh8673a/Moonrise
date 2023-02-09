package moonrise.pjt1.board.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class FileService {
    private String S3Bucket = "moonrise";

    @Autowired
    AmazonS3Client amazonS3Client;

    public List<String> upload(MultipartFile[] multipartFileList){
        List<String> imagePathList = new ArrayList<>();
        try {
            for (MultipartFile multipartFile : multipartFileList) {
                String originalName = multipartFile.getOriginalFilename();

                //파일 크기
                long size = multipartFile.getSize();

                ObjectMetadata objectMetadata = new ObjectMetadata();
                objectMetadata.setContentType(multipartFile.getContentType());
                objectMetadata.setContentLength(size);

                // s3에 업로드
                amazonS3Client.putObject(
                        new PutObjectRequest(S3Bucket, originalName, multipartFile.getInputStream(), objectMetadata)
                                .withCannedAcl(CannedAccessControlList.PublicRead)
            );
                String imagePath = amazonS3Client.getUrl(S3Bucket, originalName).toString(); // 접근가능한 URL 가져오기
                log.info("imagePath : {}", imagePath);
                imagePathList.add(imagePath);
            }
        }catch (IOException ioException) {
            log.error(ioException.getMessage());
        }
        return imagePathList;
    }
}
