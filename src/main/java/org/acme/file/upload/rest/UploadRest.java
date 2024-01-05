package org.acme.file.upload.rest;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.acme.file.upload.config.AppConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.Valid;

@CrossOrigin
@RestController
@RequestMapping("/rest")
public class UploadRest {

  // private static final org.slf4j.Logger log =
  // LoggerFactory.getLogger(UploadRest.class);

  @Autowired
  private AppConfiguration configuration;

  @PostMapping("/upload")
  public ResponseEntity<Boolean> uploadJson(@Valid UserDto userDto) throws IOException {
    StringBuilder builder = new StringBuilder();
    builder.append(configuration.getLocation());
    builder.append(userDto.file().getOriginalFilename());
    java.nio.file.Path path = Paths.get(builder.toString());
    byte[] bs = userDto.file().getBytes();
    Files.write(path, bs);
    return ResponseEntity.ok(Boolean.TRUE);
  }

  @PostMapping("/upload/singlefile")
  public ResponseEntity<?> uploadSingleFile(MultipartFile file) throws IOException {
    StringBuilder builder = new StringBuilder();
    builder.append(configuration.getLocation());
    builder.append(file.getOriginalFilename());
    java.nio.file.Path path = Paths.get(builder.toString());
    byte[] bs = file.getBytes();
    Files.write(path, bs);
    return ResponseEntity.ok(Boolean.TRUE);
  }

  @GetMapping(value = "/image/{filename}", produces = { MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_JPEG_VALUE })
  public ResponseEntity<?> getImage(@PathVariable("filename") String filename) throws IOException {
    byte[] byteFile = null;
    java.io.File file = new java.io.File(configuration.getLocation() + filename);
    try (InputStream targetStream = new FileInputStream(file)) {
      byteFile = targetStream.readAllBytes();
    } catch (Exception e) {
    }
    return ResponseEntity.ok()
        .contentLength(byteFile != null ? byteFile.length : 0)
        .body(byteFile);

  }

  @GetMapping(value = "/image/download/{filename}", produces = { MediaType.APPLICATION_OCTET_STREAM_VALUE })
  public ResponseEntity<?> download(@PathVariable("filename") String filename) throws IOException {
    byte[] byteFile = null;
    java.io.File file = new java.io.File(configuration.getLocation() + filename);
    try (InputStream targetStream = new FileInputStream(file)) {
      byteFile = targetStream.readAllBytes();
    } catch (Exception e) {
    }
    return ResponseEntity.ok()
        .contentLength(byteFile != null ? byteFile.length : 0)
        .body(byteFile);

  }
}

record User(int age, String name, String file) {
}

record UserDto(int age, String name, MultipartFile file) {
}