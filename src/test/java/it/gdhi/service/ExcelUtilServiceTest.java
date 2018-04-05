package it.gdhi.service;

import it.gdhi.dto.CategoryHealthScoreDto;
import it.gdhi.dto.CountryHealthScoreDto;
import it.gdhi.dto.IndicatorScoreDto;
import it.gdhi.model.Category;
import it.gdhi.model.Indicator;
import it.gdhi.repository.ICategoryRepository;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

import static it.gdhi.service.ExcelUtilService.*;
import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ExcelUtilServiceTest {

    @InjectMocks
    @Spy
    private ExcelUtilService excelUtilService;
    @Mock
    private ICategoryRepository iCategoryRepository;

    @Before
    public void setUp() throws Exception {
        doReturn("/tmp/Digital Health Data.xlsx").when(excelUtilService).getFileWithPath();
    }

    @Test
    public void shouldVerifyFileIsDownloaded() throws IOException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        ServletContext servletContext = mock(ServletContext.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        ServletOutputStream outputStream = mock(ServletOutputStream.class);
        when(response.getOutputStream()).thenReturn(outputStream);
        when(request.getServletContext()).thenReturn(servletContext);

        excelUtilService.downloadFile(request, response);

        verify(request).getServletContext();
        verify(response).setContentType(MIME_TYPE);
        verify(response).setHeader(HEADER_KEY, "attachment; filename='Digital Health Data.xlsx'");
        verify(outputStream).close();
    }

    @Test
    public void shouldPopulateHeaderDefinitionAndNotHealthScoresWhenEmptyCountryScoresPresent() throws IOException {
        List<CountryHealthScoreDto> countryHealthScores = new ArrayList<>();
        List<Indicator> indicators = singletonList(new Indicator(1, "Ind 1", "Ind Def 1"));
        List<Category> categories = singletonList(new Category(1, "Cat 1",
                indicators));
        when(iCategoryRepository.findAll()).thenReturn(categories);

        excelUtilService.convertListToExcel(countryHealthScores);

        ArgumentCaptor<XSSFSheet> sheetArgumentCaptor = ArgumentCaptor.forClass(XSSFSheet.class);
        ArgumentCaptor<Map> mapArgumentCaptor = ArgumentCaptor.forClass(Map.class);

        verify(excelUtilService).populateHeaderNames(any(), sheetArgumentCaptor.capture(), eq(0), eq(categories));
        verify(excelUtilService).populateHeaderDefinitions(any(), sheetArgumentCaptor.capture(), eq(1), eq(categories));
        verify(excelUtilService, times(0)).populateHealthIndicatorsWithDefinitionsAndScores(sheetArgumentCaptor.capture(),
                eq(countryHealthScores), mapArgumentCaptor.capture(), eq(2));
        XSSFSheet sheet = sheetArgumentCaptor.getValue();
        assertThat(sheet.getSheetName(), is("Global Health Data"));
    }

    @Test
    public void shouldPopulateHeaderDefinitionAndNotHealthScoresWhenNullCountryScoresPresent() throws IOException {
        List<CountryHealthScoreDto> countryHealthScores = null;
        List<Indicator> indicators = singletonList(new Indicator(1, "Ind 1", "Ind Def 1"));
        List<Category> categories = singletonList(new Category(1, "Cat 1",
                indicators));
        when(iCategoryRepository.findAll()).thenReturn(categories);

        excelUtilService.convertListToExcel(countryHealthScores);

        ArgumentCaptor<XSSFSheet> sheetArgumentCaptor = ArgumentCaptor.forClass(XSSFSheet.class);
        ArgumentCaptor<Map> mapArgumentCaptor = ArgumentCaptor.forClass(Map.class);
        verify(excelUtilService).populateHeaderNames(any(), sheetArgumentCaptor.capture(), eq(0), eq(categories));
        verify(excelUtilService).populateHeaderDefinitions(any(), sheetArgumentCaptor.capture(), eq(1), eq(categories));
        verify(excelUtilService, times(0)).populateHealthIndicatorsWithDefinitionsAndScores(sheetArgumentCaptor.capture(),
                eq(countryHealthScores), mapArgumentCaptor.capture(), eq(2));
        XSSFSheet sheet = sheetArgumentCaptor.getValue();
        assertThat(sheet.getSheetName(), is("Global Health Data"));
    }

    @Test
    public void shouldPopulateHeaderDefinitionAndHealthScoresWhenCountryScoresPresent() throws IOException {
        List<CountryHealthScoreDto> countryHealthScores = new ArrayList<>();
        countryHealthScores.add(new CountryHealthScoreDto("IND", "INDIA", "IN",
                4.0, emptyList(), 4));
        List<Indicator> indicators = singletonList(new Indicator(1, "Ind 1", "Ind Def 1"));
        List<Category> categories = singletonList(new Category(1, "Cat 1",
                indicators));
        when(iCategoryRepository.findAll()).thenReturn(categories);

        excelUtilService.convertListToExcel(countryHealthScores);

        ArgumentCaptor<XSSFSheet> sheetArgumentCaptor = ArgumentCaptor.forClass(XSSFSheet.class);
        ArgumentCaptor<Map> mapArgumentCaptor = ArgumentCaptor.forClass(Map.class);
        verify(excelUtilService).populateHeaderNames(any(), sheetArgumentCaptor.capture(), eq(0), eq(categories));
        verify(excelUtilService).populateHeaderDefinitions(any(), sheetArgumentCaptor.capture(), eq(1), eq(categories));
        verify(excelUtilService).populateHealthIndicatorsWithDefinitionsAndScores(sheetArgumentCaptor.capture(),
                eq(countryHealthScores), mapArgumentCaptor.capture(), eq(2));
        XSSFSheet sheet = sheetArgumentCaptor.getValue();
        Set<String> keys = mapArgumentCaptor.getValue().keySet();
        List<String> expectedKeys = asList("Country Name", "Indicator 1", "Category 1", "Overall Phase");
        List<String> expectedValues = asList("Country Name", "Ind 1", "Cat 1", "Overall Phase");
        assertTrue(keys.containsAll(expectedKeys));
        Collection<String> values =  mapArgumentCaptor.getValue().values();
        assertTrue(values.containsAll(expectedValues));
        assertThat(sheet.getSheetName(), is("Global Health Data"));
    }

    @Test
    public void shouldPopulateHealthScoresToBeExported() throws IOException {
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet(WORKSHEET_NAME);
        List<CountryHealthScoreDto> countryHealthScores = new ArrayList<>();
        IndicatorScoreDto indicatorScoreDto = new IndicatorScoreDto(1, "1", "Ind 1", "Ind Def 1", 4, "S", "S1");
        CategoryHealthScoreDto categoryHealthScoreDto = new CategoryHealthScoreDto(1, "Cat 1", 4.0, 4,  asList(indicatorScoreDto));
        countryHealthScores.add(new CountryHealthScoreDto("IND", "INDIA", "IN",
                4.0, asList(categoryHealthScoreDto),
                4));
        Map<String, String> headerDef = new LinkedHashMap<>();
        headerDef.put("Country Name", "Country Name");
        headerDef.put("Indicator 1", "Ind 1");
        headerDef.put("Category 1", "Cat 1");
        headerDef.put("Overall Phase", "Overall Phase");

        excelUtilService.populateHealthIndicatorsWithDefinitionsAndScores(sheet, countryHealthScores, headerDef, 3);

        ArgumentCaptor<Map> contentMap = ArgumentCaptor.forClass(Map.class);
        verify(excelUtilService).addRow(contentMap.capture(), any(Row.class), any());
        Map actualMap = contentMap.getValue();
        Object[] keys = actualMap.keySet().toArray();
        Object[] values = actualMap.values().toArray();

        assertThat(keys[0], is("Country Name"));
        assertThat(keys[1], is("Indicator 1"));
        assertThat(keys[2], is("Category 1"));
        assertThat(keys[3], is("Overall Phase"));

        assertThat(values[0], is("INDIA"));
        assertThat(values[1], is("Phase 4"));
        assertThat(values[2], is("Phase 4"));
        assertThat(values[3], is("Phase 4"));
    }

}