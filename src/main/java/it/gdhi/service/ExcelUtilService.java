package it.gdhi.service;

import it.gdhi.dto.CategoryHealthScoreDto;
import it.gdhi.dto.CountryHealthScoreDto;
import it.gdhi.dto.IndicatorScoreDto;
import it.gdhi.model.Category;
import it.gdhi.repository.ICategoryRepository;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static it.gdhi.utils.Constants.HEADER_KEY;
import static it.gdhi.utils.Constants.MIME_TYPE;

@Service
public class ExcelUtilService {
    private List<String> headers;
    private static final int BUFFER_SIZE = 4096;
    private static final String HEADER_FORMAT = "attachment; filename=\'%s\'";

    private static final String FILE_WITH_PATH = "/tmp/Global Health Data.xlsx";

    @Autowired
    private ICategoryRepository iCategoryRepository;

    public void convertListToExcel(List<CountryHealthScoreDto> countryHealthScoreDtos) {

        headers = new ArrayList<String>() {{
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
        }};

        Map<String, String> headerDef = populateDefinitionHeader();

        try {

            XSSFWorkbook workbook = new XSSFWorkbook();

            XSSFSheet sheet = workbook.createSheet("Global Health Data");

            int rownum = 0;
            Row row = sheet.createRow(rownum++);

            int cellnum = 0;

            row.createCell(cellnum++).setCellValue("");
            for (String header : headers) {
                row.createCell(cellnum++).setCellValue(header);
            }

            row = sheet.createRow(rownum++);
            addRow(headerDef, row);

            populateHealthIndicatorsWithScores(countryHealthScoreDtos, headerDef, sheet, rownum);

            String excelPath = FILE_WITH_PATH;
            FileOutputStream fileOutputStream = new FileOutputStream(new File(excelPath));
            workbook.write(fileOutputStream);
            fileOutputStream.close();

        } catch (IOException ie) {
            ie.printStackTrace();
        }
    }

    private void populateHealthIndicatorsWithScores(List<CountryHealthScoreDto> countryHealthScoreDtos,
                                                    Map<String, String> headerDef,
                                                    XSSFSheet sheet, int rownum) {
        Row row;
        for (CountryHealthScoreDto countryHealthScoreDto : countryHealthScoreDtos) {
            row = sheet.createRow(rownum++);
            for (String header : headerDef.keySet()) {
                headerDef.put(header, "Missing / Not Available");
                headerDef.put("Country Name", countryHealthScoreDto.getCountryName());
                List<CategoryHealthScoreDto> categories = countryHealthScoreDto.getCategories();
                for (CategoryHealthScoreDto category : categories) {

                    headerDef.put("Category " + category.getId(), category.getPhase() != null ?
                            "Phase " + category.getPhase() : "Missing / Not Available");
                    List<IndicatorScoreDto> indicators = category.getIndicators();
                    for (IndicatorScoreDto indicator : indicators) {
                        headerDef.put("Indicator " + indicator.getId(), indicator.getScore() != null ?
                                "Phase " + indicator.getScore() : "Missing / Not Available");

                    }
                }
                headerDef.put("Overall Phase", countryHealthScoreDto.getOverallScore() != null ?
                        "Phase " + countryHealthScoreDto.getCountryPhase() : "Missing / Not Available");
                addRow(headerDef, row);
            }
        }
    }

    private Map<String, String> populateDefinitionHeader() {
        List<Category> categoryList = iCategoryRepository.findAll();
        Map<String, String> headerDef = new LinkedHashMap<>();
        headerDef.put("Country Name", "Country Name");
        categoryList.stream().forEach(category -> {
           headerDef.put("Category " + category.getId(), category.getName());
           category.getIndicators().stream().forEach(indicator -> {
               headerDef.put("Indicator " + indicator.getIndicatorId(), indicator.getName());
           });
        });
        headerDef.put("Overall Phase", "Overall Phase");
        return headerDef;
    }

    public void downloadFile(HttpServletRequest request, HttpServletResponse response) throws IOException {

        File fileToDownload = new File(FILE_WITH_PATH);
        FileInputStream inputStream = new FileInputStream(fileToDownload);

        ServletContext context = request.getServletContext();
        String mimeTypeFromPath = context.getMimeType(FILE_WITH_PATH);
        String mimeType = mimeTypeFromPath == null ? MIME_TYPE : mimeTypeFromPath;
        String headerValue = String.format(HEADER_FORMAT, fileToDownload.getName());

        response.setContentType(mimeType);
        response.setContentLength((int) fileToDownload.length());
        response.setHeader(HEADER_KEY, headerValue);

        OutputStream outStream = response.getOutputStream();

        byte[] buffer = new byte[BUFFER_SIZE];
        int bytesRead = -1;
        while ((bytesRead = inputStream.read(buffer)) != -1) {
            outStream.write(buffer, 0, bytesRead);
        }
        inputStream.close();
        outStream.close();
    }

    private void addRow(Map<String, String> headerDef, Row row) {
        int cellnum;
        cellnum = 0;
        for (String header : headerDef.keySet()) {
            row.createCell(cellnum++).setCellValue(headerDef.get(header));
        }
    }
}
