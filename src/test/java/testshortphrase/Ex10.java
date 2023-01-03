package testshortphrase;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class Ex10 {

    @Test
    public void checkPhraseLength() {
        String phraseLong = "Hello, I am here and I am waiting for this test to pass successfully";
        String phraseShort = "yyy";
        int actualLength = phraseLong.length();
        int actualLength2 = phraseShort.length();
        System.out.println(actualLength);

        assertTrue(actualLength > 15, "The phrase is too short: " + actualLength);
    }


}
