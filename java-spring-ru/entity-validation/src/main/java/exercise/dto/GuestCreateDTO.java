package exercise.dto;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDate;

import static jakarta.persistence.GenerationType.IDENTITY;

// BEGIN
@Setter
@Getter
public class GuestCreateDTO {

    private long id;

    @NotBlank
    private String name;

    @Email
    private String email;
    @Pattern(regexp = "\\+[0-9]{11,13}")
    private String phoneNumber;

    @Size(min = 4, max = 4)
    private String clubCard;

    @FutureOrPresent
    private LocalDate cardValidUntil;

    private LocalDate createdAt;

}
// END
