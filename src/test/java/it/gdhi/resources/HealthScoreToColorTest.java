package it.gdhi.resources;

import it.gdhi.model.HealthScoreToColor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class HealthScoreToColorTest {

    @Test
    public void shouldReturnTrueWhenScoreIsCorrectlyTransformedToYMLColorCode() {
        assertThat(HealthScoreToColor.scoreToColor(1), is("#FF5734"));
    }

    @Test
    public void shouldReturnTrueWhenNullScoreIsCorrectlyTransformedToYMLColorCode() {
        assertThat(HealthScoreToColor.scoreToColor(null), is("#FF5733"));
    }

}
