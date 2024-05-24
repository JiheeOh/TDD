package test.chap03;

import main.chap03.ExpiryDateCalculator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import main.chap03.PayData;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ExpiryDateCalculatorTest {

    /**
     * 1. 가장 쉬운 것부터 테스트
     */
    @DisplayName("1만원을 납부하면 한달 뒤 같은 날을 만료일로 계산 ")
    @Test
    void getExpireDateWhen10000given() {
        assertExpiryDate(
                PayData.builder()
                        .billingDate(LocalDate.of(2019, 3, 1))
                        .payAmount(10_000)
                        .build(),
                LocalDate.of(2019, 4, 1)
        );
    }

    /**
     * 2-1. 예외 사항 처리
     * - 달마다 마지막 날이 다름
     */
    @DisplayName("납부일과 한달 뒤가 일자가 같지 않음 ")
    @Test
    void notEqualDate() {
        assertExpiryDate(
                PayData.builder()
                        .billingDate(LocalDate.of(2019, 1, 31))
                        .payAmount(10_000)
                        .build()
                , LocalDate.of(2019, 2, 28)

        );
    }

    @BeforeEach

    @DisplayName("첫 납부일과 만료일의 일자가 같지 않을 때 1만원 납부하면 첫 납부일 기준으로 다음 만료일 정함")
    @Test
    void getExpireDateWhenFirstDateAndSecondDateNotEqual() {
        PayData payData = PayData.builder()
                .firstBillingDate(LocalDate.of(2019, 1, 31))
                .billingDate(LocalDate.of(2019, 2, 28))
                .payAmount(10_000)
                .build();

        assertExpiryDate(payData, LocalDate.of(2019, 3, 31));

        PayData payData2 = PayData.builder()
                .firstBillingDate(LocalDate.of(2019, 1, 30))
                .billingDate(LocalDate.of(2019, 2, 28))
                .payAmount(10_000)
                .build();

        assertExpiryDate(payData2, LocalDate.of(2019, 3, 30));

        PayData payData3 = PayData.builder()
                .firstBillingDate(LocalDate.of(2019, 5, 31))
                .billingDate(LocalDate.of(2019, 6, 30))
                .payAmount(10_000)
                .build();

        assertExpiryDate(payData3, LocalDate.of(2019, 7, 31));

    }

    /**
     * 3. 쉬운 테스트 2
     * - 2만원 이상을 내면 만원 추가에 따라 만료일이 더 늘어난다.
     */
    @DisplayName("2만원을 지불하면 만료일이 두달 뒤가 된다")
    @Test
    void getExpireDateWhenMoreThan10000given() {
        assertExpiryDate(
                PayData.builder()
                        .billingDate(LocalDate.of(2019, 3, 1))
                        .payAmount(20_000)
                        .build()
                , LocalDate.of(2019, 5, 1)
        );

        assertExpiryDate(
                PayData.builder()
                        .billingDate(LocalDate.of(2019, 3, 1))
                        .payAmount(30_000)
                        .build()
                , LocalDate.of(2019, 6, 1)
        );
    }

    /**
     * 4. 예외사항
     * - 첫 납부일과 만료일 일자가 다를 때 이만원 이상 납부
     */
    @DisplayName("첫 납부일과 만료일 일자가 다를 때 이만원 이상 납부")
    @Test
    void differentExpireDateBetweenFistAndSecondGivenMoreThan10000() {
        assertExpiryDate(
                PayData.builder()
                        .firstBillingDate(LocalDate.of(2019, 1, 31))
                        .billingDate(LocalDate.of(2019, 2, 28))
                        .payAmount(20_000)
                        .build()
                , LocalDate.of(2019, 4, 30)
        );

        assertExpiryDate(
                PayData.builder()
                        .firstBillingDate(LocalDate.of(2019, 1, 31))
                        .billingDate(LocalDate.of(2019, 2, 28))
                        .payAmount(40_000)
                        .build()
                , LocalDate.of(2019, 6, 30)
        );

        assertExpiryDate(
                PayData.builder()
                        .firstBillingDate(LocalDate.of(2019, 3, 31))
                        .billingDate(LocalDate.of(2019, 4, 30))
                        .payAmount(30_000)
                        .build()
                , LocalDate.of(2019, 7, 31)
        );
    }

    @DisplayName("10개월 요금을 납부하면 1년 제공")
    @Test
    void getExpireDateWhen100_000given() {
        assertExpiryDate(
                PayData.builder()
                        .billingDate(LocalDate.of(2019, 1, 28))
                        .payAmount(100_000)
                        .build(),
                LocalDate.of(2020, 1, 28)
        );

    }


    @DisplayName("윤달 마지막 날에 10만원 납부")
    @Test
    void getExpireDateIntercalation10_000given() {
        // LocalDate에서 자동으로 처리해줌
        assertExpiryDate(
                PayData.builder()
                        .billingDate(LocalDate.of(2020, 2, 29))
                        .payAmount(100_000)
                        .build(),
                LocalDate.of(2021, 2, 28)
        );


    }

    @DisplayName("13만원 납부할 경우")
    @Test
    void getExpireDate130_000given() {
        assertExpiryDate(
                PayData.builder()
                        .billingDate(LocalDate.of(2019, 1, 1))
                        .payAmount(130_000)
                        .build(),
                LocalDate.of(2020, 4, 1)
        );

    }

    /**
     * TDD 심화 퀴즈
     * 첫번째 테스트
     */
    @DisplayName("3만원 지불하고 1개월 후에 환불하면 돈을 얼만큼 받을 수 있는지 계산 (월 기준)")
    @Test
    void getRefundWhen30_000Given() {
        // given : 3만원 지불한 경우 만료일
        assertRefund(PayData.builder()
                        .billingDate(LocalDate.of(2019, 1, 1))
                        .payAmount(30_000)
                        .build()
                , LocalDate.of(2019, 1, 31)
                , 20_000
        );

        assertRefund(PayData.builder()
                        .billingDate(LocalDate.of(2019, 1, 1))
                        .payAmount(30_000)
                        .build()
                , LocalDate.of(2019, 2, 1)
                , 10_000
        );
    }

    /**
     * 예외 케이스 1
     */
    @DisplayName("결제일에 환불 요청할 경우 전액 환불 ")
    @Test
    void getFullRefundFirstDay() {
        // given
        assertRefund(PayData.builder()
                        .billingDate(LocalDate.of(2019, 1, 1))
                        .payAmount(30_000)
                        .build()
                , LocalDate.of(2019, 1, 1)
                , 30_000
        );
    }

    /**
     * 예외 케이스 2
     * 10만원 이상 지불해서 사용기간이 1년일 때
     * 만약 1개월만 사용하고 환불할 경우 9만원만 환불하도록
     * 2개월은 서비스 개념이기 때문에 2개월은 환불 불가
     */
    @DisplayName("10만원 지불하고 한달 사용 후 환불하면 9만원만 환불됨 ")
    @Test
    void getRefundWhen100_000Given() {
        assertRefund(PayData.builder()
                        .billingDate(LocalDate.of(2019, 1, 1))
                        .payAmount(100_000)
                        .build()
                , LocalDate.of(2019, 2, 1)
                , 80_000
        );

        assertRefund(PayData.builder()
                        .billingDate(LocalDate.of(2019, 1, 1))
                        .payAmount(100_000)
                        .build()
                , LocalDate.of(2019, 1, 31)
                , 90_000
        );

    }

    /**
     * 실제 환불금과 예상한 환불금이 같은지 확인
     *
     * @param payData
     * @param now
     * @param expectedRefund
     */
    private void assertRefund(PayData payData, LocalDate now, int expectedRefund) {
        ExpiryDateCalculator cal = new ExpiryDateCalculator();
        LocalDate endDate = cal.calculateExpiryDate(payData);
        payData.setEndDate(endDate);

        // when
        int refund = cal.calculateRefund(payData, now);

        //then
        assertEquals(expectedRefund, refund);
    }

    /**
     * 실제 만료일과 기대한 만료일이 같은지 확인
     *
     * @param payData
     * @param expectedExpiryDate
     */
    private void assertExpiryDate(PayData payData, LocalDate expectedExpiryDate) {
        ExpiryDateCalculator cal = new ExpiryDateCalculator();
        LocalDate realExpiryDate = cal.calculateExpiryDate(payData);
        assertEquals(expectedExpiryDate, realExpiryDate);
    }



}
