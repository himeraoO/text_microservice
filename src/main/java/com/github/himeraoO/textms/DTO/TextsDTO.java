package com.github.himeraoO.textms.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.lang.NonNull;

import java.util.Objects;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class TextsDTO {

    @NonNull
    private Long id = 0L;

    @NotBlank
    private String username;

    @NotBlank
    private String title;

    @NotBlank
    private String description = "";

    @NotBlank
    private String dataText;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TextsDTO textsDTO = (TextsDTO) o;

        return Objects.equals(username, textsDTO.getUsername()) && Objects.equals(title, textsDTO.getTitle()) && Objects.equals(description, textsDTO.getDescription()) && Objects.equals(dataText, textsDTO.getDataText());
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, title, description, dataText);
    }
}
