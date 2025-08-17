package org.cklautests.dsa.stringoperation;

import java.util.List;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class ValidateCouponTest {

    final ValidateCoupon vc = new ValidateCoupon();

    @Test
    void testValidateCouponWithoutActiveCode() {
        String[] code = new String[] {"TsCwKhY", "qCeVkfb", "ZGjX07H"};
        String[] busline = new String[] {"restaurant", "electronics", "pharmacy"};
        boolean[] isActive = new boolean[] {false, false, false};

        // expected: []

        List<String> result = vc.validateCoupons(code, busline, isActive);
        assertThat(result)
                .as("Test validating coupon without active code, size").isEmpty();

    }

    @Test
    void testValidateCouponWithInvalidBusline() {
        String[] code = new String[] {"GROCERY15", "ELECTRONICS_50", "DISCOUNT10"};
        String[] busline = new String[] {"grocery", "electronics", "invalid"};
        boolean[] isActive = new boolean[] {true, true, true};

        List<String> result = vc.validateCoupons(code, busline, isActive);
        System.out.println(result);
        assertThat(result.size())
                .as("Test validating coupon with invalid busline, size").isEqualTo(2);
        assertThat(result.getFirst())
                .as("Test validating coupon with invalid busline, 1st entry").isEqualTo(code[1]);
        assertThat(result.get(1))
                .as("Test validating coupon sorted with invalid busline, 2nd entry").isEqualTo(code[0]);

    }
    @Test
    void testValidateCouponWithSortedByBusinessLine() {

        String[] code = new String[] {"TsCwKhY", "qCeVkfb", "ZGjX07H"};
        String[] busline = new String[] {"restaurant", "electronics", "pharmacy"};
        boolean[] isActive = new boolean[] {true, true, true};

        // expected: ["qCeVkfb","ZGjX07H","TsCwKhY"]

        List<String> result = vc.validateCoupons(code, busline, isActive);
        assertThat(result.size())
                .as("Test validating coupon sorted by busline, size").isEqualTo(isActive.length);
        assertThat(result.getFirst())
                .as("Test validating coupon sorted by busline, 1st entry").isEqualTo(code[1]);
        assertThat(result.get(1))
                .as("Test validating coupon sorted by busline, 2nd entry").isEqualTo(code[2]);
        assertThat(result.get(2))
                .as("Test validating coupon sorted by busline, 3rd entry").isEqualTo(code[0]);
    }

    @Test
    void testValidateCouponWithEmptyCoupon() {

        String[] code = new String[] {""};
        String[] busline = new String[] {"restaurant"};
        boolean[] isActive = new boolean[] {true};

        List<String> result = vc.validateCoupons(code, busline, isActive);
        assertThat(result)
                .as("Test validating coupon with empty code, size").isEmpty();

    }

    @Test
    void testValidateCouponWithInvalidCodeName() {

        String[] code = new String[] {"invalid-code-name"};
        String[] busline = new String[] {"restaurant"};
        boolean[] isActive = new boolean[] {true};

        List<String> result = vc.validateCoupons(code, busline, isActive);
        assertThat(result)
                .as("Test validating coupon with invalid code name, size").isEmpty();

    }

}
