package org.acme.file.upload.rest;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.acme.file.upload.model.dto.UserDto;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

@CrossOrigin
@RestController
@RequestMapping("/rest")
public class UploadRest {

  private static final org.slf4j.Logger log = LoggerFactory.getLogger(UploadRest.class);

  record User(int age, String name, String file) {
  }

  @PostMapping("/upload")
  public ResponseEntity<User> uploadJson(@Valid UserDto userDto) throws IOException {
    StringBuilder builder = new StringBuilder();
    builder.append("/home/llam/Pictures/storage/");
    builder.append(userDto.getFile().getOriginalFilename());
    java.nio.file.Path path = Paths.get(builder.toString());
    byte[] bs = userDto.getFile().getBytes();
    Files.write(path, bs);
    return ResponseEntity.ok(null);
  }

  @GetMapping(value = "/image/{filename}", produces = { MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_JPEG_VALUE })
  public ResponseEntity<?> getImage(@PathVariable("filename") String filename) throws IOException {
    byte[] byteFile = null;
    java.io.File file = new java.io.File("/home/llam/Pictures/storage/" + filename);
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
    java.io.File file = new java.io.File("/home/llam/Pictures/storage/" + filename);
    try (InputStream targetStream = new FileInputStream(file)) {
      byteFile = targetStream.readAllBytes();
    } catch (Exception e) {
    }
    return ResponseEntity.ok()
        .contentLength(byteFile != null ? byteFile.length : 0)
        .body(byteFile);

  }
}
