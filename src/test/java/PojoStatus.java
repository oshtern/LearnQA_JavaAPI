import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
@Builder
public class PojoStatus {
    private String status;
    private String result;

}
