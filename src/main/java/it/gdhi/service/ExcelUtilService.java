package it.gdhi.service;

import it.gdhi.dto.CategoryHealthScoreDto;
import it.gdhi.dto.CountryHealthScoreDto;
import it.gdhi.dto.IndicatorScoreDto;
import it.gdhi.model.Category;
import it.gdhi.repository.ICategoryRepository;
import lombok.Getter;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static it.gdhi.model.CountryHealthIndicator.SCORE_DESCRIPTION_NOT_AVAILABLE;


@Service
public class ExcelUtilService {

    static final String MIME_TYPE = "application/octet-stream";
    static final String HEADER_KEY = "Content-Disposition";
    static final String WORKSHEET_NAME = "Global Health Data";
    private static final String COUNTRY_NAME = "Country Name";
    private static final String PHASE = "Phase ";
    private static final String CATEGORY = "Category ";
    private static final String INDICATOR = "Indicator ";
    private static final String OVERALL_PHASE = "Overall Phase";
    private static final int BUFFER_SIZE = 4096;
    private static final String HEADER_FORMAT = "attachment; filename=%s";

    @Value("${excelFileLocation}")
    @Getter
    private String fileWithPath;

    @Autowired
    private ICategoryRepository iCategoryRepository;

    void convertListToExcel(List<CountryHealthScoreDto> countryHealthScoreDtos) {
        List<Category> categories = iCategoryRepository.findAll();

        try {
            XSSFWorkbook workbook = new XSSFWorkbook();
            XSSFSheet sheet = workbook.createSheet(WORKSHEET_NAME);

            int rownum = 0;
            int noOfColumns = populateHeaderNames(workbook, sheet, rownum++, categories);

            Map<String, String> headerDefinitions = populateHeaderDefinitions(workbook, sheet, rownum++, categories);
            if (countryHealthScoreDtos != null && !countryHealthScoreDtos.isEmpty()) {
                populateHealthIndicatorsWithDefinitionsAndScores(sheet, countryHealthScoreDtos,
                                                                 headerDefinitions, rownum);
            }
            for(int i = 0; i<= noOfColumns; i++) {
                sheet.autoSizeColumn(i);
            }
            FileOutputStream fileOutputStream = new FileOutputStream(new File(this.getFileWithPath()));
            workbook.write(fileOutputStream);
            fileOutputStream.close();

        } catch (IOException ie) {
            ie.printStackTrace();
        }
    }

    int populateHeaderNames(XSSFWorkbook workBook, XSSFSheet sheet, int rownum,
                            List<Category> categories) {
        Map<String, String> headerDef = new LinkedHashMap<>();
        headerDef.put("Blank Cell", "");

        categories.forEach(category -> {
            category.getIndicators().forEach(indicator -> headerDef.put(INDICATOR + indicator.getIndicatorId(),
                    INDICATOR + indicator.getCode()));
            headerDef.put(CATEGORY + category.getId(), CATEGORY + category.getId());
        });
        Row row = sheet.createRow(rownum);
        addRow(headerDef, row, getFontStyle(workBook));

        return headerDef.size();
    }

    Map<String, String> populateHeaderDefinitions(XSSFWorkbook workBook, XSSFSheet sheet, int rownum,
                                                  List<Category> categories) {
        Map<String, String> headerDef = new LinkedHashMap<>();
        headerDef.put(COUNTRY_NAME, COUNTRY_NAME);
        categories.forEach(category -> {
            category.getIndicators().forEach(indicator -> headerDef.put(INDICATOR + indicator.getIndicatorId(),
                    indicator.getName()));
            headerDef.put(CATEGORY + category.getId(), category.getName());
        });
        headerDef.put(OVERALL_PHASE, OVERALL_PHASE);
        Row row = sheet.createRow(rownum);
        addRow(headerDef, row, getFontStyle(workBook));
        return headerDef;
    }

    void populateHealthIndicatorsWithDefinitionsAndScores(XSSFSheet sheet,
                                                          List<CountryHealthScoreDto> countryHealthScoreDtos,
                                                          Map<String, String> headerDef,
                                                          int rownum) {
        Row row;
        for (CountryHealthScoreDto countryHealthScoreDto : countryHealthScoreDtos) {
            row = sheet.createRow(rownum++);
            Map<String, String> content = new LinkedHashMap<>();
            for (String header : headerDef.keySet()) {
                content.put(header, SCORE_DESCRIPTION_NOT_AVAILABLE);
            }
            content.put(COUNTRY_NAME, countryHealthScoreDto.getCountryName());
            List<CategoryHealthScoreDto> categories = countryHealthScoreDto.getCategories();
            for (CategoryHealthScoreDto category : categories) {
                List<IndicatorScoreDto> indicators = category.getIndicators();
                for (IndicatorScoreDto indicator : indicators) {
                    content.put(INDICATOR + indicator.getId(),
                            indicator.getScore() != null && indicator.getScore() >=0 ?
                            PHASE + indicator.getScore() : SCORE_DESCRIPTION_NOT_AVAILABLE);

                }
                content.put(CATEGORY + category.getId(), category.getPhase() != null ?
                        PHASE + category.getPhase() : SCORE_DESCRIPTION_NOT_AVAILABLE);
            }
            content.put(OVERALL_PHASE, countryHealthScoreDto.getCountryPhase() != null ?
                    PHASE + countryHealthScoreDto.getCountryPhase() : SCORE_DESCRIPTION_NOT_AVAILABLE);
            addRow(content, row, null);
        }
    }

    void downloadFile(HttpServletRequest request, HttpServletResponse response) throws IOException {

        File fileToDownload = new File(this.getFileWithPath());
        FileInputStream inputStream = new FileInputStream(fileToDownload);

        ServletContext context = request.getServletContext();
        String mimeTypeFromPath = context.getMimeType(this.getFileWithPath());
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

    void addRow(Map<String, String> headerDef, Row row, XSSFCellStyle fontStyle) {
        int cellnum = 0;
        for (String header : headerDef.keySet()) {
            Cell cell = row.createCell(cellnum++);
            if(fontStyle != null) {
                cell.setCellStyle(fontStyle);
            }
            cell.setCellValue(headerDef.get(header));
        }
    }

    private XSSFCellStyle getFontStyle(XSSFWorkbook wb) {
        XSSFCellStyle style = wb.createCellStyle();

        XSSFFont font= wb.createFont();
        font.setFontHeightInPoints((short)10);
        font.setFontName("Arial");
        font.setBold(true);
        font.setItalic(false);

        style.setAlignment(CellStyle.ALIGN_CENTER);
        style.setFont(font);
        return style;
    }
}
