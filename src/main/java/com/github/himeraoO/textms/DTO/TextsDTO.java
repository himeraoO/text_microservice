package com.github.himeraoO.textms.DTO;

//import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.lang.NonNull;

import java.util.Objects;

@Getter
@Setter
@ToString()
@NoArgsConstructor
@AllArgsConstructor
public class TextsDTO {
    @NonNull
    private Long id;
    //    @NotBlank
    private String username;
    //    @NotBlank
    private String title;
    //    @NotBlank
    private String description = "";
    //    @NotBlank
    private String data;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TextsDTO textsDTO = (TextsDTO) o;
        return Objects.equals(username, textsDTO.username) && Objects.equals(title, textsDTO.title) && Objects.equals(description, textsDTO.description) && Objects.equals(data, textsDTO.data);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, title, description, data);
    }
}
