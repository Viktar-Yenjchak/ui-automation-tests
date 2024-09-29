package com.example.tests;

import com.codeborne.selenide.Configuration;
import com.example.pages.MainPage;
import com.example.models.TableRow;
import com.example.utils.DataComparator;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.*;
import java.util.logging.Logger;

public class TableComparisonTest {
    private static final Logger LOGGER = Logger.getLogger(TableComparisonTest.class.getName());
    private static MainPage mainPage;
    private static DataComparator dataComparator;

    @BeforeAll
    public static void setup() {
        Configuration.timeout = 10000;

        mainPage = new MainPage();
        dataComparator = new DataComparator();

        System.setProperty(
                "java.util.logging.SimpleFormatter.format",
                "%1$tF %1$tT %4$s %2$s - %5$s%6$s%n"
        );
    }

    @Test
    public void compareTablesTest() {
        LOGGER.info("Test started: compareTablesTest");
        mainPage.openApplication();
        List<TableRow> table1Data = extractTableData(1);
        List<TableRow> table2Data = extractTableData(2);
        List<String> discrepancies = dataComparator.compareTableData(table1Data, table2Data);
        assertNoDiscrepancies(discrepancies);
    }

    /**
     * Navigates to the specified table and extracts its data.
     *
     * @param tableNumber The table number (1 or 2).
     * @return A list of TableRow objects representing the table data.
     */
    private List<TableRow> extractTableData(int tableNumber) {
        mainPage.navigateToTable(tableNumber);
        List<TableRow> tableData = mainPage.getTableData();
        LOGGER.info(String.format("Extracted data from Table %d. Total rows: %d", tableNumber, tableData.size()));
        return tableData;
    }

    /**
     * Asserts that no discrepancies are found between the tables.
     *
     * @param discrepancies The list of discrepancy messages.
     */
    private void assertNoDiscrepancies(List<String> discrepancies) {
        if (discrepancies.isEmpty()) {
            LOGGER.info("No discrepancies found between Table 1 and Table 2.");
        } else {
            String message = String.join(System.lineSeparator(), discrepancies);
            LOGGER.warning("Discrepancies found:\n" + message);
            fail("Discrepancies found:\n" + message);
        }
    }
}