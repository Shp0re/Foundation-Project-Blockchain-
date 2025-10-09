package test.smartContracts.conditions;

import main.java.taxreturns.smartContract.conditions.TimeCondition;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Time Condition Test")
public class TimeConditionTest {

    @Test
    void whenIncorrectDateFormat_ThenThrowCorrectException() {
        TimeCondition timeCondition;

        boolean exceptionThrown = false;

        String falseDateString = "Invalid";

        try {
            timeCondition = new TimeCondition(falseDateString);
        } catch (Exception e) {
            exceptionThrown = true;
            assertEquals("Unparseable date: \"Invalid\"", e.getMessage());
        }

        assertTrue(exceptionThrown);
    }
}
