package com.github.himeraoO.textms.model;

import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = "texts")
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Texts {

    @Id
    @NonNull
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id = 0L;

    @NotBlank
    @Column(name = "username", nullable = false, length = 255)
    private String username;

    @NotBlank
    @Column(name = "title", nullable = false, length = 255)
    private String title;

    @NotBlank
    @Column(name = "description", nullable = false, length = 255)
    private String description = "";

    @NotBlank
    @Column(name = "data_text", nullable = false, length = 255)
    private String dataText;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Texts texts = (Texts) o;

        return Objects.equals(username, texts.getUsername()) && Objects.equals(title, texts.getTitle()) && Objects.equals(description, texts.getDescription()) && Objects.equals(dataText, texts.getDataText());
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, title, description, dataText);
    }
}
