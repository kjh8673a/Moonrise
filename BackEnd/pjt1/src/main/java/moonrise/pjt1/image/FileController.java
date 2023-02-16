package moonrise.pjt1.image;

import lombok.RequiredArgsConstructor;
import moonrise.pjt1.commons.response.ResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/image")
@RequiredArgsConstructor
public class FileController {
    private final FileService fileService;

    @PostMapping("/upload")
    public ResponseEntity<?> imageUpload(MultipartFile[] files){
        ResponseDto responseDto = fileService.upload(files);

        return new ResponseEntity<ResponseDto>(responseDto, HttpStatus.OK);
    }
}