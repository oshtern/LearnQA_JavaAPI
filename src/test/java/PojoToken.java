import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
@Builder
public class PojoToken {
    private String token;
    private Integer seconds;

}
