package com.sparta.vikingband.S3;

import com.sparta.vikingband.dto.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/s3")
public class S3Controller {

    private final S3Service S3Service;
    //private static String dirName = "study-img";

    /**
     * Amazon S3에 파일 업로드
     * @return 성공 시 200 Success와 함께 업로드 된 파일의 파일명 리스트 반환
     */
    //@ApiOperation(value = "Amazon S3에 파일 업로드", notes = "Amazon S3에 파일 업로드 ")
    @PostMapping("/file")
    public ApiResponse<List<String>> uploadFile(@RequestPart List<MultipartFile> multipartFile) {
        return ApiResponse.successOf(HttpStatus.CREATED, S3Service.uploadFile(multipartFile));
    }

    /**
     * Amazon S3에 업로드 된 파일을 삭제
     * @return 성공 시 200 Success
     */

   //S3에서 파일은 삭제되지 않고 로컬에서만 파일이 삭제되어있다
    @DeleteMapping("/file")
    public ApiResponse<String> deleteFile(@RequestParam String fileName) {
        S3Service.deleteFile(fileName);
        return ApiResponse.successOf(HttpStatus.OK,null);
    }
}
