package com.hotelbeds.distribution.hotel_api_sdk.types;

/*
 * #%L
 * hotel-api-sdk
 * %%
 * Copyright (C) 2015 HOTELBEDS, S.L.U.
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 2.1 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Lesser Public License for more details.
 * 
 * You should have received a copy of the GNU General Lesser Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/lgpl-2.1.html>.
 * #L%
 */

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.text.StrSubstitutor;

import com.hotelbeds.hotelapimodel.auto.messages.AvailabilityRS;
import com.hotelbeds.hotelapimodel.auto.messages.BookingCancellationRS;
import com.hotelbeds.hotelapimodel.auto.messages.BookingDetailRS;
import com.hotelbeds.hotelapimodel.auto.messages.BookingListRS;
import com.hotelbeds.hotelapimodel.auto.messages.BookingRS;
import com.hotelbeds.hotelapimodel.auto.messages.BookingVoucherRS;
import com.hotelbeds.hotelapimodel.auto.messages.CheckRateRS;
import com.hotelbeds.hotelapimodel.auto.messages.GenericResponse;
import com.hotelbeds.hotelapimodel.auto.messages.StatusRS;

/**
 * Copyright (c) Hotelbeds Technology S.L.U. All rights reserved.
 */
public enum HotelApiPaths {

    AVAILABILITY("${path}/${version}/hotels", AllowedMethod.POST, AvailabilityRS.class),
    BOOKING_LIST(
        "${path}/${version}/bookings?start=${start}&end=${end}&from=${from}&to=${to}&includeCancelled=${includeCancelled}&filterType=${filterType}",
        AllowedMethod.GET,
        BookingListRS.class),
    BOOKING_DETAIL("${path}/${version}/bookings/${bookingId}", AllowedMethod.GET, BookingDetailRS.class),
    BOOKING_CONFIRM("${path}/${version}/bookings", AllowedMethod.POST, BookingRS.class),
    BOOKING_CANCEL("${path}/${version}/bookings/${bookingId}?cancellationFlag=${flag}", AllowedMethod.DELETE, BookingCancellationRS.class),
    CHECK_AVAIL("${path}/${version}/checkrates", AllowedMethod.POST, CheckRateRS.class),
    STATUS("${path}/${version}/status", AllowedMethod.GET, StatusRS.class),
    BOOKING_VOUCHER("${path}/${version}/vouchers/${bookingId}", AllowedMethod.POST, BookingVoucherRS.class);

    private final String urlTemplate;
    private final AllowedMethod allowedMethod;
    private final Class<? extends GenericResponse> responseClass;

    HotelApiPaths(final String urlTemplate, final AllowedMethod allowedMethod, Class<? extends GenericResponse> responseClass) {
        this.urlTemplate = urlTemplate;
        this.allowedMethod = allowedMethod;
        this.responseClass = responseClass;
    }

    public String getUrlTemplate() {
        return urlTemplate;
    }

    public Class<? extends GenericResponse> getResponseClass() {
        return responseClass;
    }

    public String getUrl(HotelApiService service, HotelApiVersion version, String alternativeHotelApiPath) {
        return getUrl(service, version, null, alternativeHotelApiPath);
    }

    public String getUrl(HotelApiService service, HotelApiVersion version, Map<String, String> params, String alternativeHotelApiPath) {
        if (params == null) {
            params = new HashMap<>();
        }
        params.put("path", service.getHotelApiPath(alternativeHotelApiPath));
        params.put("version", version.getVersion());
        StrSubstitutor strSubstitutor = new StrSubstitutor(params);
        return strSubstitutor.replace(urlTemplate);
    }

    public AllowedMethod getAllowedMethod() {
        return allowedMethod;
    }
}
