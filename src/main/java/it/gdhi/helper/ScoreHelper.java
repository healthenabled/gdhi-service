package it.gdhi.helper;


import org.springframework.stereotype.Component;

import java.math.BigDecimal;

import static java.math.BigDecimal.ONE;
import static java.math.BigDecimal.ROUND_HALF_EVEN;
import static java.math.BigDecimal.ZERO;
import static java.math.RoundingMode.CEILING;

@Component
public class ScoreHelper {

    public static Integer convertScoreToPhase(Double overallScore) {
        Integer result = null;
        if(overallScore != null) {
            BigDecimal overallScoreInBigDecimal = new BigDecimal(overallScore);
            BigDecimal ceiledScore = overallScoreInBigDecimal.setScale(1, ROUND_HALF_EVEN)
                    .setScale(0, CEILING);
            BigDecimal phase = ceiledScore.equals(ZERO) ? ceiledScore.add(ONE) : ceiledScore;
            result = phase.intValue();
        }
        return result;
    }
}
