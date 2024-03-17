package com.apollo.art.chat.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.apollo.art.chat.Utils.Status;
import com.apollo.art.chat.model.FilesBody;
import com.apollo.art.chat.service.FileHelper;
import com.apollo.art.chat.service.UploadService;

import lombok.*;

@RestController
@RequestMapping("/upload")
@RequiredArgsConstructor
public class UploadController {

    private final UploadService uploadService;

    // @PostMapping("/file")
    // public Status uploadFile(@RequestPart("files") MultipartFile[]
    // multipartFiles) {
    // try {
    // for (MultipartFile multipartFile : multipartFiles) {
    // uploadService.upload(multipartFile);
    // }
    // return Status.builder().status("ok").details("File uploaded").build();
    // } catch (Exception e) {
    // return Status.builder().status("error").details(e.getMessage()).build();
    // }
    // }

    @PostMapping("/file")
    public Status uploadFile(@RequestBody FilesBody files) {
        try {
            FileHelper file = new FileHelper();
            for (String fileBase64 : files.getFiles()) {
                file.upload(fileBase64);
            }
            return Status.builder().status("ok").details("File uploaded").build();
        } catch (Exception e) {
            return Status.builder().status("error").details(e.getMessage()).build();
        }
    }

}
