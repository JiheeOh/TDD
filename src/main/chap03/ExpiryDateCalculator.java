package main.chap03;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.temporal.ChronoUnit;

public class ExpiryDateCalculator {



    /**
     * 첫 번째 만료일 이후 추가금을 냈을 경우
     * 추가금 낸 만큼 뒤의 만료일이 언제인지 구함
     *
     * @param payData
     * @param addMonth
     * @return LocalDate
     */
    private LocalDate expiryDateUsingFirstBillingDate(PayData payData, int addMonth) {
        LocalDate candidateExp = payData.getBillingDate().plusMonths(addMonth);
        final int dayOfFirstBilling = payData.getFirstBillingDate().getDayOfMonth();

        if (!isSameDayOfMonth(payData.getFirstBillingDate(), candidateExp)) {
            final int dayLenOfCandiMonth = lastDayOfMonth(candidateExp);

            // 후보 만료일이 포함된 달의 마지막 날 < 첫 납부일의 일자면 후보 만료일을 그 달의 마지막날로 조정
            if (dayLenOfCandiMonth < dayOfFirstBilling) {
                return candidateExp.withDayOfMonth(dayLenOfCandiMonth);
            }

            return candidateExp.withDayOfMonth(dayOfFirstBilling);
        } else {
            return candidateExp;
        }
    }

    /**
     * 만료일 계산
     * @param payData
     * @return
     */
    public LocalDate calculateExpiryDate(PayData payData) {
        int addMonth = 0;
        if (payData.getPayAmount() >= 100_000) {
            addMonth = (payData.getPayAmount() / 100_000) * 12 + payData.getPayAmount() % 100_000 / 10_000;
        } else {
            addMonth = payData.getPayAmount() / 10_000;
        }

        if (payData.getFirstBillingDate() != null) {
            return expiryDateUsingFirstBillingDate(payData, addMonth);
        } else {
            return payData.getBillingDate().plusMonths(addMonth);
        }
    }

    /**
     * 환불금을 계산
     *
     * @param payData
     * @param now
     * @return
     */
    public int calculateRefund(PayData payData, LocalDate now) {
        LocalDate endDate = payData.getEndDate();

        // 첫결제일에 바로 환불 요청할 경우
        if (payData.getBillingDate().equals(now)) {
            return payData.getPayAmount();
        } else if (payData.getPayAmount() >= 100_000) {
//             TODO: 이 부분은 향후 리팩토링을 해야할 것
//             현재와 만료일까지의 개월 수 차이를 구한다.
//             ex) 2019.01.31과 2020.01.01은 11개월차이
//             2019.02.01일 경우 12개월 차이로 여기기때문에 임의로 +1처리
            now = now.plusDays(1);
            long tmpPeriod = now.until(endDate, ChronoUnit.MONTHS);
            int period = (int) tmpPeriod;
            return (period - 2) * 10_000;
        } else {
            return (endDate.getMonthValue() - (now.getMonthValue() + 1)) * 10_000;
        }
    }



    /**
     * 같은 달인지 확인
     *
     * @param date1
     * @param date2
     * @return
     */
    private boolean isSameDayOfMonth(LocalDate date1, LocalDate date2) {
        return date1.getDayOfMonth() == date2.getDayOfMonth();
    }

    private int lastDayOfMonth(LocalDate date) {
        return YearMonth.from(date).lengthOfMonth();
    }
}
