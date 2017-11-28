package it.gdhi.utils;

import it.gdhi.dto.CategoryHealthScoreDto;
import it.gdhi.dto.CountryHealthScoreDto;
import it.gdhi.dto.IndicatorScoreDto;
import it.gdhi.model.Category;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Excel {
    private List<String> headers;

    public void convertJavaToExcel(List<CountryHealthScoreDto> countryHealthScoreDtos) {

        headers = new ArrayList<String>() {{
            add("Country Name");
            add("Indicator 1");
            add("Indicator 2");
            add("Category 1");
            add("Indicator 3");
            add("Indicator 4");
            add("Category 2");
            add("Indicator 5");
            add("Indicator 6");
            add("Indicator 7");
            add("Indicator 8");
            add("Category 3");
            add("Indicator 9");
            add("Indicator 10");
            add("Indicator 11");
            add("Indicator 12");
            add("Category 4");
            add("Indicator 13");
            add("Indicator 14");
            add("Category 5");
            add("Indicator 15");
            add("Indicator 16");
            add("Category 6");
            add("Indicator 17");
            add("Indicator 18");
            add("Indicator 19");
            add("Category 7");
            add("Overall Phase");
        }};

        Map<String, String> headerDef = new HashMap<>();
        headerDef.put("Country Name", "");
        headerDef.put("Indicator 1", "Indicator 1");
        headerDef.put("Indicator 2", "Indicator 2");
        headerDef.put("Category 1", "Category 1");
        headerDef.put("Indicator 3", "Indicator 3");
        headerDef.put("Indicator 4", "Indicator 4");
        headerDef.put("Category 2", "Category 2");
        headerDef.put("Indicator 5", "Indicator 5");
        headerDef.put("Indicator 6", "Indicator 6");
        headerDef.put("Indicator 7", "Indicator 7");
        headerDef.put("Indicator 8", "Indicator 8");
        headerDef.put("Category 3", "Category 3");
        headerDef.put("Indicator 9", "Indicator 9");
        headerDef.put("Indicator 10", "Indicator 10");
        headerDef.put("Indicator 11", "Indicator 11");
        headerDef.put("Indicator 12", "Indicator 12");
        headerDef.put("Category 4", "Category 4");
        headerDef.put("Indicator 13", "Indicator 13");
        headerDef.put("Indicator 14", "Indicator 14");
        headerDef.put("Category 5", "Category 5");
        headerDef.put("Indicator 15", "Indicator 15");
        headerDef.put("Indicator 16", "Indicator 16");
        headerDef.put("Category 6", "Category 6");
        headerDef.put("Indicator 17", "Indicator 17");
        headerDef.put("Indicator 18", "Indicator 18");
        headerDef.put("Indicator 19", "Indicator 19");
        headerDef.put("Category 7", "Category 7");
        headerDef.put("Overall Phase", "");
        try {
            String excelPath = "/tmp/Global Health Data.xlsx";
            FileOutputStream fileOutputStream = new FileOutputStream(new File(excelPath));

            XSSFWorkbook workbook = new XSSFWorkbook();

            XSSFSheet sheet = workbook.createSheet("Global Health Data");

            int rownum = 0;
            Row row = sheet.createRow(rownum++);

            int cellnum = 0;
            for (String header : headers) {
                row.createCell(cellnum++).setCellValue(headerDef.get(header));
            }

            headerDef.put("Country Name", "Country Name");
            headerDef.put("Indicator 1", "Digital health prioritized at the national level through dedicated bodies "+
                    "/ mechanisms for governance");
            headerDef.put("Indicator 2", "Digital Health prioritized at the national level through planning");
            headerDef.put("Category 1", "Leadership and Governance");
            headerDef.put("Indicator 3", "National eHealth/ Digital Health Strategy or Framework");
            headerDef.put("Indicator 4", "Public funding for digital health");
            headerDef.put("Category 2", "Strategy & Investment");
            headerDef.put("Indicator 5", "Legal Framework for Data Protection (Security)");
            headerDef.put("Indicator 6", "Laws or Regulations for privacy, confidentiality and acess to health " +
                            "information (Privacy)");
            headerDef.put("Indicator 7", "Protocol for regulating or certifying devices and/or " +
                            "digital health services");
            headerDef.put("Indicator 8", "Cross-border data security and sharing");
            headerDef.put("Category 3", "Legislation, Policy, and Compliance");
            headerDef.put("Indicator 9", "Digital health integrated in health and related professional pre-service " +
                            "training (prior to deployment)");
            headerDef.put("Indicator 10", "Digital health integrated in health and related professional in-service " +
                            "training (after deployment)");
            headerDef.put("Indicator 11", "Training of digital health work force");
            headerDef.put("Indicator 12", "Maturity of public sector digital health professional careers");
            headerDef.put("Category 4", "Workforce");
            headerDef.put("Indicator 13", "National digital health architecture and/or health information exchange");
            headerDef.put("Indicator 14", "Health information standards");
            headerDef.put("Category 5", "Standards and Interoperability");
            headerDef.put("Indicator 15", "Network readiness");
            headerDef.put("Indicator 16", "Planning and support for ongoing digital health infrastructure maintenance");
            headerDef.put("Category 6", "Infrastructure");
            headerDef.put("Indicator 17", "Nationally scaled digital health systems");
            headerDef.put("Indicator 18", "Identity management of service providers, administrators, and facilities " +
                            "for Digital Health, including location data for GIS mapping");
            headerDef.put("Indicator 19", "Identity management of individuals for Digital Health");
            headerDef.put("Category 7", "Services and Application");
            headerDef.put("Overall Phase", "Overall Phase");

            row = sheet.createRow(rownum++);
            addRow(headerDef, row);

            for (CountryHealthScoreDto countryHealthScoreDto :countryHealthScoreDtos) {
                row = sheet.createRow(rownum++);
                for (String header : headers) {
                    headerDef.put(header, "Missing / Not Available");
                    headerDef.put("Country Name", countryHealthScoreDto.getCountryName());
                    List<CategoryHealthScoreDto> categories = countryHealthScoreDto.getCategories();
                    for (CategoryHealthScoreDto category : categories) {

                        headerDef.put("Category " + category.getId(), category.getPhase() != null ?
                                "Phase "+category.getPhase() : "Missing / Not Available");
                        List<IndicatorScoreDto> indicators = category.getIndicators();
                        for (IndicatorScoreDto indicator : indicators) {
                            headerDef.put("Indicator " + indicator.getId(), indicator.getScore() != null ?
                                    "Phase "+indicator.getScore() : "Missing / Not Available");

                        }
                    }
                    headerDef.put("Overall Phase", countryHealthScoreDto.getOverallScore() != null ?
                            "Phase "+countryHealthScoreDto.getCountryPhase() : "Missing / Not Available");
                    addRow(headerDef, row);
                }

            }

            workbook.write(fileOutputStream);
            fileOutputStream.close();
        } catch (
                IOException ie)

        {
            ie.printStackTrace();
        }
    }

    private void addRow(Map<String, String> headerDef, Row row) {
        int cellnum;
        cellnum = 0;
        for (String header : headers) {
            row.createCell(cellnum++).setCellValue(headerDef.get(header));
        }
    }
}
