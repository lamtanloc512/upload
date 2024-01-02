package org.acme.file.upload.model.dto;

import org.springframework.web.multipart.MultipartFile;
import java.util.Objects;

public class UserDto {
  int age;
  String name;
  MultipartFile file;

  public UserDto() {
  }

  public UserDto(int age, String name, MultipartFile file) {
    this.age = age;
    this.name = name;
    this.file = file;
  }

  public int getAge() {
    return this.age;
  }

  public void setAge(int age) {
    this.age = age;
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public MultipartFile getFile() {
    return this.file;
  }

  public void setFile(MultipartFile file) {
    this.file = file;
  }

  public UserDto age(int age) {
    setAge(age);
    return this;
  }

  public UserDto name(String name) {
    setName(name);
    return this;
  }

  public UserDto file(MultipartFile file) {
    setFile(file);
    return this;
  }

  @Override
  public boolean equals(Object o) {
    if (o == this)
      return true;
    if (!(o instanceof UserDto)) {
      return false;
    }
    UserDto userDto = (UserDto) o;
    return age == userDto.age && Objects.equals(name, userDto.name) && Objects.equals(file, userDto.file);
  }

  @Override
  public int hashCode() {
    return Objects.hash(age, name, file);
  }

  @Override
  public String toString() {
    return "{" +
        " age='" + getAge() + "'" +
        ", name='" + getName() + "'" +
        ", file='" + getFile() + "'" +
        "}";
  }

}
