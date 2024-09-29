package com.example.pages;

import com.codeborne.selenide.*;
import com.example.models.TableRow;
import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import static com.codeborne.selenide.Selenide.*;

public class MainPage {
    private static final Logger LOGGER = Logger.getLogger(MainPage.class.getName());

    private String tableTabButton = "//button[text()='Table %s']";
    private String tableRows = "//table[@class='table']//tbody//tr";

    /**
     * Opens the web application under test.
     */
    public void openApplication() {
        open("https://ui-automation-app.web.app/");
        LOGGER.info("Opened the web application.");
    }

    /**
     * Navigates to the specified table by clicking on the corresponding tab.
     *
     * @param tableNumber The table number to navigate to (1 or 2).
     */
    public void navigateToTable(int tableNumber) {
        String tabLocator = String.format(tableTabButton, tableNumber);
        $x(tabLocator).click();
        LOGGER.info("Navigated to Table " + tableNumber);
    }

    /**
     * Extracts data from the currently displayed table as a list of TableRow objects.
     *
     * @return A list of TableRow objects representing the table data.
     */
    public List<TableRow> getTableData() {
        LOGGER.info("Starting to extract table data.");
        scrollToLoadAllRows();

        LOGGER.info("Starting to extract table data using JavaScript.");

        String script = "return Array.from(document.querySelectorAll('table.table tbody tr')).map(row => {" +
                "const cells = row.querySelectorAll('td');" +
                "return {" +
                "companyName: cells[0].innerText.trim()," +
                "ticker: cells[1].innerText.trim()," +
                "cobDate: cells[2].innerText.trim()," +
                "stockPrice: cells[3].innerText.trim()," +
                "marketCap: cells[4].innerText.trim()" +
                "};" +
                "});";

        List<Map<String, String>> data = executeJavaScript(script);

        List<TableRow> tableData = data.stream()
                .map(row -> new TableRow(
                        row.get("companyName"),
                        row.get("ticker"),
                        row.get("cobDate"),
                        row.get("stockPrice"),
                        row.get("marketCap")
                ))
                .collect(Collectors.toList());

        LOGGER.info("Completed data extraction from table using JavaScript.");
        return tableData;
    }

    /**
     * Scrolls down the table to ensure all rows are loaded.
     */
    private void scrollToLoadAllRows() {
        int currentRowCount = 0;

        do {
            ElementsCollection rows = getTableRows();
            if (rows.isEmpty()) {
                LOGGER.warning("No rows found in the table.");
                break;
            }
            SelenideElement lastRow = rows.last();
            lastRow.scrollIntoView(true);
            currentRowCount = getTableRows().size();
            LOGGER.info("Scrolled to load rows. Current row count: " + currentRowCount);

        } while (currentRowCount != 1000);
    }

    /**
     * Retrieves all rows from the currently displayed table.
     *
     * @return An ElementsCollection of table rows.
     */
    private ElementsCollection getTableRows() {
        return $$x(tableRows);
    }
}