package it.gdhi.utils;

import java.math.BigDecimal;

import static java.math.BigDecimal.ONE;
import static java.math.BigDecimal.ROUND_HALF_EVEN;
import static java.math.BigDecimal.ZERO;
import static java.math.RoundingMode.CEILING;

public class ScoreUtils {
    public static Integer convertScoreToPhase(Double score) {
        Integer result = null;
        if(score != null) {
            BigDecimal overallScoreInBigDecimal = new BigDecimal(score);
            BigDecimal ceiledScore = overallScoreInBigDecimal.setScale(1, ROUND_HALF_EVEN)
                    .setScale(0, CEILING);
            BigDecimal phase = ceiledScore.equals(ZERO) ? ceiledScore.add(ONE) : ceiledScore;
            result = phase.intValue();
        }
        return result;
    }
}
