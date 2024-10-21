package com.github.mkolisnyk.assertex;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.opentest4j.AssertionFailedError;

@DisplayName("AssertEx: Map Assertions")
public class AssertexTest {
    private static final String ENTRY_1 = "Entry 1";
    private Map<String, String> base = new HashMap<String, String>() {
        {
            put(ENTRY_1, "Value 1");
            put("Entry 2", "Value 2");
            put("Entry 3", "Value 3");
            put("Entry 4", "Value 4");
        }
    };
    private Map<String, String> shuffled = new HashMap<String, String>() {
        {
            put(ENTRY_1, "Value 1");
            put("Entry 3", "Value 3");
            put("Entry 2", "Value 2");
            put("Entry 4", "Value 4");
        }
    };
    private Map<String, String> mismatched = new HashMap<String, String>() {
        {
            put(ENTRY_1, "Value 1");
            put("Entry 2", "Value 2");
            put("Entry 3", "Value M");
            put("Entry 4", "Value 4");
        }
    };
    private Map<String, String> missingItem = new HashMap<String, String>() {
        {
            putAll(base);
            remove(ENTRY_1);
        }
    };
    private Map<String, String> extraItem = new HashMap<String, String>() {
        {
            putAll(base);
            put("Entry 5", "Value 5");
        }
    };
    @Test
    @DisplayName("The same map should be equal to itself")
    public void testAssertMapEqual() {
        AssertEx.assertMapEqualsAsStringMap(base, base, "Equal maps should pass");
    }
    @Test
    @DisplayName("The map should be equal to similar map with shuffled eleemnts")
    public void testAssertMapShuffledShouldMatch() {
        AssertEx.assertMapEqualsAsStringMap(base, shuffled, "Shuffled maps should pass");
    }
    @Test
    @DisplayName("The map with mismatched eleemnt")
    public void testAssertMismatchedItem() {
        try {
            AssertEx.assertMapEqualsAsStringMap(base, mismatched, "Mismatched item");
        } catch (AssertionFailedError e) {
            Assertions.assertTrue(
                e.getMessage().contains(
                        "value of \"Entry 3\" key doesn't match actual results. "
                        + "Expected: \"Value 3\". Actual: \"Value M\""),
                "Actual message: " + e.getMessage());
        }
    }
    @Test
    @DisplayName("The map with missing elements")
    public void testAssertItemMissing() {
        try {
            AssertEx.assertMapEqualsAsStringMap(base, missingItem, "Missing item");
        } catch (AssertionFailedError e) {
            Assertions.assertTrue(
                    e.getMessage().contains(
                            "Expected key of \"Entry 1\" wasn't found in actual results"),
                    "Actual message: " + e.getMessage());

        }
    }
    @Test
    @DisplayName("The map with extra element")
    public void testAssertMapExtraItem() {
        try {
            AssertEx.assertMapEqualsAsStringMap(base, extraItem, "Extra item error");
        } catch (AssertionFailedError e) {
            Assertions.assertTrue(
                    e.getMessage()
                    .contains("Actual results map has an extra entry of [\"Entry 5\", \"Value 5\"]"),
                    "Unexpected error message");
        }
    }
}
