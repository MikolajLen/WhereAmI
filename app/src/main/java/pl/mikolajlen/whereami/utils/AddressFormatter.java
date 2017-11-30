package pl.mikolajlen.whereami.utils;

import android.location.Address;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;

/**
 * Created by mikolaj on 14.07.2017.
 */

public class AddressFormatter {

    public static final String EMPTY_STRING = "";
    private static final String NEW_LINE_DELIMITER = "\n";

    public static String getFormattedAddress(Address address) {
        if (address == null) {
            return EMPTY_STRING;
        }
        return Stream.range(0, address.getMaxAddressLineIndex() + 1)
                .map(address::getAddressLine)
                .collect(Collectors.joining(NEW_LINE_DELIMITER));
    }

    public static String getFirstLine(Address address) {
        return address.getAddressLine(0);
    }

    public static String getOtherLines(Address address) {
        if (address.getMaxAddressLineIndex() == 0) {
            return EMPTY_STRING;
        }
        return Stream.range(1, address.getMaxAddressLineIndex() + 1)
                .map(address::getAddressLine)
                .collect(Collectors.joining(NEW_LINE_DELIMITER));
    }
}
