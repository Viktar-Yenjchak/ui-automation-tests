package com.example.utils;

import com.example.models.TableRow;
import java.util.*;
import java.util.logging.Logger;

public class DataComparator {
    private static final Logger LOGGER = Logger.getLogger(DataComparator.class.getName());

    /**
     * Compares two lists of TableRow objects and identifies discrepancies.
     *
     * @param table1Data List of TableRow objects from Table 1.
     * @param table2Data List of TableRow objects from Table 2.
     * @return A list of discrepancy messages.
     */
    public List<String> compareTableData(List<TableRow> table1Data, List<TableRow> table2Data) {
        LOGGER.info("Starting comparison of table data.");
        List<String> discrepancies = new ArrayList<>();

        // Map data by unique key (Ticker)
        Map<String, TableRow> table1Map = mapDataByTicker(table1Data);
        Map<String, TableRow> table2Map = mapDataByTicker(table2Data);

        // Compare data present in Table 1
        for (String ticker : table1Map.keySet()) {
            TableRow row1 = table1Map.get(ticker);
            TableRow row2 = table2Map.get(ticker);

            if (row2 == null) {
                // Row is missing in Table 2
                String message = String.format("Ticker '%s' is missing in Table 2.", ticker);
                discrepancies.add(message);
                LOGGER.fine(message);
            } else {
                // Compare row data
                compareRows(row1, row2, discrepancies);
            }
        }

        // Check for rows present in Table 2 but missing in Table 1
        for (String ticker : table2Map.keySet()) {
            if (!table1Map.containsKey(ticker)) {
                String message = String.format("Ticker '%s' is missing in Table 1.", ticker);
                discrepancies.add(message);
                LOGGER.fine(message);
            }
        }

        LOGGER.info("Completed comparison of table data.");
        return discrepancies;
    }

    /**
     * Compares two TableRow objects and records any discrepancies.
     *
     * @param row1         The TableRow object from Table 1.
     * @param row2         The TableRow object from Table 2.
     * @param discrepancies The list to record discrepancy messages.
     */
    private void compareRows(TableRow row1, TableRow row2, List<String> discrepancies) {
        if (!Objects.equals(row1.getCompanyName(), row2.getCompanyName())) {
            discrepancies.add(formatDiscrepancy(row1.getTicker(), "Company Name", row1.getCompanyName(), row2.getCompanyName()));
        }
        if (!Objects.equals(row1.getCobDate(), row2.getCobDate())) {
            discrepancies.add(formatDiscrepancy(row1.getTicker(), "COB Date", row1.getCobDate(), row2.getCobDate()));
        }
        if (!Objects.equals(row1.getStockPrice(), row2.getStockPrice())) {
            discrepancies.add(formatDiscrepancy(row1.getTicker(), "Stock Price", row1.getStockPrice(), row2.getStockPrice()));
        }
        if (!Objects.equals(row1.getMarketCap(), row2.getMarketCap())) {
            discrepancies.add(formatDiscrepancy(row1.getTicker(), "Market Cap", row1.getMarketCap(), row2.getMarketCap()));
        }
    }

    /**
     * Formats a discrepancy message.
     *
     * @param ticker   The unique ticker identifier.
     * @param field    The field where the discrepancy was found.
     * @param value1   The value from Table 1.
     * @param value2   The value from Table 2.
     * @return A formatted discrepancy message.
     */
    private String formatDiscrepancy(String ticker, String field, String value1, String value2) {
        String message = String.format(
                "Discrepancy found for Ticker '%s' in field '%s': Table 1='%s', Table 2='%s'",
                ticker, field, value1, value2
        );
        LOGGER.fine(message);
        return message;
    }

    /**
     * Maps a list of TableRow objects by their ticker for quick lookup.
     *
     * @param tableData The list of TableRow objects.
     * @return A map of TableRow objects keyed by ticker.
     */
    private Map<String, TableRow> mapDataByTicker(List<TableRow> tableData) {
        Map<String, TableRow> dataMap = new HashMap<>();
        for (TableRow row : tableData) {
            String ticker = row.getTicker();
            if (ticker != null && !ticker.isEmpty()) {
                dataMap.put(ticker, row);
            } else {
                LOGGER.warning("Encountered a row with missing ticker: " + row);
            }
        }
        return dataMap;
    }
}