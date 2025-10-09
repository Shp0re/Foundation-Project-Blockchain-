package test.blockchain;

import main.java.taxreturns.blockchain.Block;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

@DisplayName("Block Test")
class BlockTest {

    Block testBlock;
    Block testBlock2;
    List<String> testData;
    List<String> testData2;

    @BeforeEach
    void init() {

        // Anything needing to be done at the start
        testData = List.of("ImportantInformation", "SensitiveData", "ClassifiedDocuments");
        testData2 = List.of("RestrictedDetails", "ExclusivePlans", "PersonalTransaction");

        testBlock = new Block(29328102, testData);
        testBlock2 = new Block(121838213, testData);
    }

    @Test
    void whenBlockCreated_ThenHashGenerated() {
        assertTrue(testBlock.getHash().length() > 0);
    }

    @Test
    void whenNewHashGeneratedWithEdit_ThenDifferentToOldHash() {
        String oldHash = testBlock.getHash();
        testBlock.setData(testData2);
        String newHash = testBlock2.getHash();

        assertNotEquals(oldHash, newHash);

    }

}