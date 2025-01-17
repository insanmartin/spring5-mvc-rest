package guru.springframework.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

//@Data annotation of project Lombok: @ToString,@EqualsAndHashCode, @Setters, @Getters & @RequiredArgsConstructor
@Data
@Entity
public class Category
{
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private Long id;
    private String name;

}
