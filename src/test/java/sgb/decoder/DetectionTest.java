package sgb.decoder;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.math.BigInteger;
import java.time.OffsetTime;
import java.time.ZoneOffset;

import org.junit.Test;

import sgb.decoder.internal.Bits;
import sgb.decoder.internal.Util;
import sgb.decoder.rotatingfield.ActivationMethod;
import sgb.decoder.rotatingfield.BeaconFeedback;
import sgb.decoder.rotatingfield.Cancellation;
import sgb.decoder.rotatingfield.DeactivationMethod;
import sgb.decoder.rotatingfield.EltDtInFlightEmergency;
import sgb.decoder.rotatingfield.GnssStatus;
import sgb.decoder.rotatingfield.NationalUse;
import sgb.decoder.rotatingfield.ObjectiveRequirements;
import sgb.decoder.rotatingfield.Range;
import sgb.decoder.rotatingfield.RangeEndType;
import sgb.decoder.rotatingfield.Rls;
import sgb.decoder.rotatingfield.RlsProvider;
import sgb.decoder.rotatingfield.RlsType;
import sgb.decoder.rotatingfield.TriggeringEvent;
import sgb.decoder.rotatingfield.UnknownRotatingField;
import sgb.decoder.vesselid.AircraftOperatorAndSerialNumber;
import sgb.decoder.vesselid.AircraftRegistrationMarking;
import sgb.decoder.vesselid.Aviation24BitAddress;
import sgb.decoder.vesselid.Mmsi;
import sgb.decoder.vesselid.RadioCallSign;

public class DetectionTest {

    private final String BITS = "00000000111001100000100011110100" //
            + "11001001100001100001100101100001" //
            + "10001000101000000100011111000000" //
            + "00000000000000000000000000000000" //
            + "00000000000011111111111111000000" //
            + "00010000000011000001101000000000" //
            + "1001011000";

    @Test
    public void testFromHex() {
        Detection d = Detection.fromHexGroundSegmentRepresentation(
                "0039823D32618658622811F0000000000003FFF004030680258");
        checkDetection(d);
    }

    @Test
    public void test() {
        Detection d = Detection.fromBitString(BITS);
        checkDetection(d);
        System.out.println(d);
    }

    private void checkDetection(Detection d) {
        assertEquals(230, d.tac());
        assertEquals(573, d.serialNo());
        assertEquals(201, d.countryCode());
        assertTrue(d.hasAtLeastOneEnabledHomingSignal());
        assertFalse(d.hasEnabledRls());
        assertFalse(d.isTestProtocolMessage());
        EncodedGnssPosition p = d.encodedGnssPosition().get();
        assertEquals(48.793153539336956, p.lat(), 0.00001);
        assertEquals(69.00875866413116, p.lon(), 0.00001);
        assertFalse(d.vesselId().isPresent());
        assertEquals(BeaconType.ELT_NOT_DT, d.beaconType());
        ObjectiveRequirements r = (ObjectiveRequirements) d.rotatingField();
        assertEquals(1, r.elapsedTimeSinceActivationHours());
        assertEquals(6, r.timeSinceLastEncodedLocationMinutes());
        assertEquals(432, r.altitudeEncodedLocationMetres());
        assertEquals(0, r.dilutionPrecisionHdop());
        assertEquals(Range.create(1, RangeEndType.EXCLUSIVE, 2, RangeEndType.INCLUSIVE),
                r.dilutionPrecisionDop().get());
        assertEquals(ActivationMethod.MANUAL_ACTIVATION_BY_USER, r.activationMethod());
        assertEquals(Range.create(75, RangeEndType.EXCLUSIVE, 100, RangeEndType.INCLUSIVE),
                r.remainingBatteryCapacityPercent().get());
        assertEquals(GnssStatus.LOCATION_3D, r.gnssStatus());
        assertEquals("9934039823d000000000000", d.beacon23HexId());
        assertEquals("9934039823d0000", d.beacon15HexId());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testWrongSize() {
        Detection.from(Bits.from("111"));
    }

    @Test
    public void testReadBeaconType() {
        assertEquals(BeaconType.ELT_NOT_DT, Detection.readBeaconType(Bits.from("000")));
        assertEquals(BeaconType.EPIRB, Detection.readBeaconType(Bits.from("001")));
        assertEquals(BeaconType.PLB, Detection.readBeaconType(Bits.from("010")));
        assertEquals(BeaconType.ELT_DT, Detection.readBeaconType(Bits.from("011")));
        assertEquals(BeaconType.SYSTEM, Detection.readBeaconType(Bits.from("111")));
        assertEquals(BeaconType.OTHER, Detection.readBeaconType(Bits.from("100")));
        assertEquals(BeaconType.OTHER, Detection.readBeaconType(Bits.from("101")));
        assertEquals(BeaconType.OTHER, Detection.readBeaconType(Bits.from("110")));
    }

    @Test
    public void testReadVesselIdAviation24BitAddressNoDesignator() {
        String address = "101011001000001011101100";
        Aviation24BitAddress a = Detection
                .readVesselIdAviation24BitAddress(Bits.from(address + zeros(20)));
        assertEquals("ac82ec", a.addressHex());
        assertFalse(a.aircraftOperatorDesignator().isPresent());
    }

    @Test
    public void testReadVesselIdAviation24BitAddressWithDesignator() {
        Aviation24BitAddress a = Detection
                .readVesselIdAviation24BitAddress(createVesselIdAviation24BitAddress());
        assertEquals("ac82ec", a.addressHex());
        assertEquals("ABC", a.aircraftOperatorDesignator().get());
    }

    @Test
    public void testReadVesselIdWithAviation24BitAddress() {
        Aviation24BitAddress a = (Aviation24BitAddress) Detection
                .readVesselId(Bits.from("100").concatWith(createVesselIdAviation24BitAddress()))
                .get();
        assertEquals("ac82ec", a.addressHex());
    }

    private static Bits createVesselIdAviation24BitAddress() {
        String address = "101011001000001011101100";
        String designator = "11000100110111000000";
        return Bits.from(address + designator);
    }

    @Test
    public void testReadVesselIdAicraftRegistrationMarking() {
        // two zeros then a space then VH-ABC using Baudot
        Bits b = createVesselIdAircraftRegistrationMarkingVhAbc();
        AircraftRegistrationMarking a = Detection.readVesselIdAicraftRegistrationMarking(b);
        assertEquals("VH-ABC", a.value().get());
    }

    private Bits createVesselIdAircraftRegistrationMarkingVhAbc() {
        return Bits.from("00100100101111100101011000111000110011101110");
    }

    @Test
    public void testReadVesselIdAicraftRegistrationMarkingNotPresent() {
        // two zeros then a space then VH-ABC using Baudot
        Bits b = Bits.from("00100100100100100100100100100100100100100100");
        AircraftRegistrationMarking a = Detection.readVesselIdAicraftRegistrationMarking(b);
        assertFalse(a.value().isPresent());
    }

    @Test
    public void testReadVesselIdWithAircraftRegistrationMarking() {
        AircraftRegistrationMarking a = (AircraftRegistrationMarking) Detection.readVesselId(
                Bits.from("011").concatWith(createVesselIdAircraftRegistrationMarkingVhAbc()))
                .get();
        assertEquals("VH-ABC", a.value().get());
    }

    @Test
    public void testReadVesselIdRadioCallSign() {
        Bits b = createVesselIdRadioCallSignForBingo();
        RadioCallSign a = Detection.readVesselIdRadioCallSign(b);
        assertEquals("BINGO", a.value().get());
    }

    private static Bits createVesselIdRadioCallSignForBingo() {
        return Bits.from("00110011101100100110101011100011100100100100");
    }

    @Test
    public void testReadVesselIdRadioCallSignNotPresent() {
        Bits b = Bits.from("00100100100100100100100100100100100100100100");
        RadioCallSign a = Detection.readVesselIdRadioCallSign(b);
        assertFalse(a.value().isPresent());
    }

    @Test
    public void testReadVesselIdWithRadioCallSign() {
        RadioCallSign a = (RadioCallSign) Detection
                .readVesselId(Bits.from("010").concatWith(createVesselIdRadioCallSignForBingo()))
                .get();
        assertEquals("BINGO", a.value().get());
    }

    @Test
    public void testPadLeft() {
        assertEquals("001", Detection.padLeftWithZeros(1, 3));
        assertEquals("012", Detection.padLeftWithZeros(12, 3));
        assertEquals("123", Detection.padLeftWithZeros(123, 3));
        assertEquals("1234", Detection.padLeftWithZeros(1234, 3));
    }

    @Test
    public void testGnssStatus() {
        assertEquals(GnssStatus.NO_FIX, Detection.readGnssStatus(Bits.from("00")));
        assertEquals(GnssStatus.LOCATION_2D, Detection.readGnssStatus(Bits.from("01")));
        assertEquals(GnssStatus.LOCATION_3D, Detection.readGnssStatus(Bits.from("10")));
        assertEquals(GnssStatus.OTHER, Detection.readGnssStatus(Bits.from("11")));
    }

    @Test
    public void testRemainingBattery() {
        assertEquals(Range.create(0, RangeEndType.INCLUSIVE, 5, RangeEndType.INCLUSIVE),
                Detection.readBatteryPercent(Bits.from("000")).get());
        assertEquals(Range.create(5, RangeEndType.EXCLUSIVE, 10, RangeEndType.INCLUSIVE),
                Detection.readBatteryPercent(Bits.from("001")).get());
        assertEquals(Range.create(10, RangeEndType.EXCLUSIVE, 25, RangeEndType.INCLUSIVE),
                Detection.readBatteryPercent(Bits.from("010")).get());
        assertEquals(Range.create(25, RangeEndType.EXCLUSIVE, 50, RangeEndType.INCLUSIVE),
                Detection.readBatteryPercent(Bits.from("011")).get());
        assertEquals(Range.create(50, RangeEndType.EXCLUSIVE, 75, RangeEndType.INCLUSIVE),
                Detection.readBatteryPercent(Bits.from("100")).get());
        assertEquals(Range.create(75, RangeEndType.EXCLUSIVE, 100, RangeEndType.INCLUSIVE),
                Detection.readBatteryPercent(Bits.from("101")).get());
        assertFalse(Detection.readBatteryPercent(Bits.from("110")).isPresent());
        assertFalse(Detection.readBatteryPercent(Bits.from("111")).isPresent());
    }

    @Test
    public void testDop() {
        assertEquals(Range.create(0, RangeEndType.INCLUSIVE, 1, RangeEndType.INCLUSIVE),
                Detection.readDop(Bits.from("0000")).get());
        assertEquals(Range.create(1, RangeEndType.EXCLUSIVE, 2, RangeEndType.INCLUSIVE),
                Detection.readDop(Bits.from("0001")).get());
        assertEquals(Range.create(2, RangeEndType.EXCLUSIVE, 3, RangeEndType.INCLUSIVE),
                Detection.readDop(Bits.from("0010")).get());
        assertEquals(Range.create(3, RangeEndType.EXCLUSIVE, 4, RangeEndType.INCLUSIVE),
                Detection.readDop(Bits.from("0011")).get());
        assertEquals(Range.create(4, RangeEndType.EXCLUSIVE, 5, RangeEndType.INCLUSIVE),
                Detection.readDop(Bits.from("0100")).get());
        assertEquals(Range.create(5, RangeEndType.EXCLUSIVE, 6, RangeEndType.INCLUSIVE),
                Detection.readDop(Bits.from("0101")).get());
        assertEquals(Range.create(6, RangeEndType.EXCLUSIVE, 7, RangeEndType.INCLUSIVE),
                Detection.readDop(Bits.from("0110")).get());
        assertEquals(Range.create(7, RangeEndType.EXCLUSIVE, 8, RangeEndType.INCLUSIVE),
                Detection.readDop(Bits.from("0111")).get());
        assertEquals(Range.create(8, RangeEndType.EXCLUSIVE, 10, RangeEndType.INCLUSIVE),
                Detection.readDop(Bits.from("1000")).get());
        assertEquals(Range.create(10, RangeEndType.EXCLUSIVE, 12, RangeEndType.INCLUSIVE),
                Detection.readDop(Bits.from("1001")).get());
        assertEquals(Range.create(12, RangeEndType.EXCLUSIVE, 15, RangeEndType.INCLUSIVE),
                Detection.readDop(Bits.from("1010")).get());
        assertEquals(Range.create(15, RangeEndType.EXCLUSIVE, 20, RangeEndType.INCLUSIVE),
                Detection.readDop(Bits.from("1011")).get());
        assertEquals(Range.create(20, RangeEndType.EXCLUSIVE, 30, RangeEndType.INCLUSIVE),
                Detection.readDop(Bits.from("1100")).get());
        assertEquals(Range.create(30, RangeEndType.EXCLUSIVE, 50, RangeEndType.INCLUSIVE),
                Detection.readDop(Bits.from("1101")).get());
        assertEquals(Range.create(50, RangeEndType.EXCLUSIVE, 0, RangeEndType.MISSING),
                Detection.readDop(Bits.from("1110")).get());
        assertFalse(Detection.readDop(Bits.from("1111")).isPresent());
    }

    @Test
    public void testReadRlsType() {
        assertEquals(RlsType.ACKNOWLEDGEMENT_SERVICE, Detection.readRlsType(Bits.from("0001")));
        assertEquals(RlsType.TEST_SERVICE, Detection.readRlsType(Bits.from("1111")));
        assertEquals(RlsType.OTHER, Detection.readRlsType(Bits.from("1000")));
    }

    @Test
    public void testReadRlsProvider() {
        assertEquals(RlsProvider.GALILEO, Detection.readRlsProvider(Bits.from("001")));
        assertEquals(RlsProvider.GLONASS, Detection.readRlsProvider(Bits.from("010")));
        assertEquals(RlsProvider.OTHER, Detection.readRlsProvider(Bits.from("011")));
    }

    @Test
    public void testReadActivationMethod() {
        assertEquals(ActivationMethod.MANUAL_ACTIVATION_BY_USER,
                Detection.readActivationMethod(Bits.from("00")));
        assertEquals(ActivationMethod.AUTOMATIC_ACTIVATION_BY_BEACON,
                Detection.readActivationMethod(Bits.from("01")));
        assertEquals(ActivationMethod.AUTOMATIC_ACTIVATION_BY_EXTERNAL_MEANS,
                Detection.readActivationMethod(Bits.from("10")));
        assertEquals(ActivationMethod.OTHER, Detection.readActivationMethod(Bits.from("11")));
    }

    @Test
    public void testReadVesselIdAircraftOperatoAndSerialNumber() {
        // XYZ in short baudot form followed by 15 in 12 bits + 17 unused bits
        Bits b = createVesselIdAircraftOperatorAndSerialNumber();
        AircraftOperatorAndSerialNumber a = Detection.readVesselIdAircraftOperatoAndSerialNumber(b);
        assertEquals("XYZ", a.aircraftOperatorDesignator());
        assertEquals(15, a.serialNumber());
    }

    @Test
    public void testReadVesselIdWithAircraftOperatorAndSerialNumber() {
        AircraftOperatorAndSerialNumber a = (AircraftOperatorAndSerialNumber) Detection
                .readVesselId(Bits.from("101")
                        .concatWith(createVesselIdAircraftOperatorAndSerialNumber()))
                .get();
        assertEquals("XYZ", a.aircraftOperatorDesignator());
    }

    @Test
    public void testReadVesselIdUnused() {
        assertFalse(Detection.readVesselId(Bits.from("111").concatWith(ones(44))).isPresent());
    }

    private Bits createVesselIdAircraftOperatorAndSerialNumber() {
        return Bits.from("101111010110001000000001111" + zeros(17));
    }

    @Test
    public void testReadMmsi() {
        Bits bits = createMmsiWithEpirbMmsiBits();
        Mmsi a = Detection.readVesselIdMmsi(bits);
        assertEquals(123456789, (int) a.mmsi().get());
        assertEquals(974454287, (int) a.epirbMmsi().get());
    }

    private static Bits createMmsiWithEpirbMmsiBits() {
        String mmsi = leftPadWithZeros(new BigInteger("123456789").toString(2), 30);
        String last4 = leftPadWithZeros(new BigInteger("4287").toString(2), 14);
        Bits bits = Bits.from(mmsi + last4);
        return bits;
    }

    @Test
    public void testReadMmsiPrimaryNotPresent() {
        String mmsi = leftPadWithZeros(new BigInteger("111111").toString(2), 30);
        String last4 = leftPadWithZeros(new BigInteger("4287").toString(2), 14);
        Bits bits = Bits.from(mmsi + last4);
        Mmsi a = Detection.readVesselIdMmsi(bits);
        assertFalse(a.mmsi().isPresent());
        assertEquals(974114287, (int) a.epirbMmsi().get());
    }

    @Test
    public void testReadMmsiEpirbMmsiNotPresent() {
        String mmsi = leftPadWithZeros(new BigInteger("123456789").toString(2), 30);
        String last4ish = leftPadWithZeros(new BigInteger("10922").toString(2), 14);
        Bits bits = Bits.from(mmsi + last4ish);
        Mmsi a = Detection.readVesselIdMmsi(bits);
        assertEquals(123456789, (int) a.mmsi().get());
        assertFalse(a.epirbMmsi().isPresent());
    }

    @Test
    public void testReadVesselIdMmsi() {
        Bits b = Bits.from("001").concatWith(createMmsiWithEpirbMmsiBits());
        Mmsi a = (Mmsi) Detection.readVesselId(b).get();
        assertEquals(123456789, (int) a.mmsi().get());
    }

    @Test
    public void testBatteryPercentInFlightEmergency() {
        assertEquals(Range.create(0, RangeEndType.INCLUSIVE, 33, RangeEndType.INCLUSIVE),
                Detection.readBatteryPercentInFlightEmergency(Bits.from("00")).get());
        assertEquals(Range.create(33, RangeEndType.EXCLUSIVE, 66, RangeEndType.INCLUSIVE),
                Detection.readBatteryPercentInFlightEmergency(Bits.from("01")).get());
        assertEquals(Range.create(66, RangeEndType.EXCLUSIVE, 100, RangeEndType.INCLUSIVE),
                Detection.readBatteryPercentInFlightEmergency(Bits.from("10")).get());
        assertFalse(Detection.readBatteryPercentInFlightEmergency(Bits.from("11")).isPresent());
    }

    @Test
    public void testReadTriggeringEvent() {
        assertEquals(TriggeringEvent.MANUAL_ACTIVATION_BY_CREW,
                Detection.readTriggeringEvent(Bits.from("0001")));
        assertEquals(TriggeringEvent.G_SWITCH_OR_DEFORMATION_ACTIVATION,
                Detection.readTriggeringEvent(Bits.from("0100")));
        assertEquals(TriggeringEvent.AUTOMATIC_ACTIVATION_FROM_AVIONICS_OR_TRIGGERING_SYSTEM,
                Detection.readTriggeringEvent(Bits.from("1000")));
        assertEquals(TriggeringEvent.OTHER, Detection.readTriggeringEvent(Bits.from("1111")));
    }

    @Test
    public void testReadRotatingFieldCancellationMessage() {
        assertEquals(DeactivationMethod.MANUAL_DEACTIVATION_BY_USER,
                Detection.readRotatingFieldCancellationMessage(Bits.from(ones(42) + "10"))
                        .deactivationMethod());
        assertEquals(DeactivationMethod.AUTOMATIC_DEACTIVATION_BY_EXTERNAL_MEANS,
                Detection.readRotatingFieldCancellationMessage(Bits.from(ones(42) + "01"))
                        .deactivationMethod());
        assertEquals(DeactivationMethod.OTHER,
                Detection.readRotatingFieldCancellationMessage(Bits.from(ones(42) + "11"))
                        .deactivationMethod());
    }

    @Test
    public void testReadRotatingFieldCancellation() {
        Bits b = Bits.from("1111" + ones(42) + "10");
        Cancellation a = (Cancellation) Detection.readRotatingField(b);
        assertEquals(DeactivationMethod.MANUAL_DEACTIVATION_BY_USER, a.deactivationMethod());
    }

    @Test
    public void testReadRotatingFieldNationalUse() {
        Bits b = Bits.from("0011" + ones(44));
        NationalUse a = (NationalUse) Detection.readRotatingField(b);
        assertEquals(ones(44), a.bitString());
    }

    @Test
    public void testReadRotatingFieldUnknown() {
        Bits b = Bits.from("0100" + ones(44));
        UnknownRotatingField a = (UnknownRotatingField) Detection.readRotatingField(b);
        assertEquals(ones(44), a.bitString());
    }

    @Test
    public void testReadPositionNotAvailable() {
        assertFalse(
                Detection.readPosition(Bits.from("11111111000001111100000111111111111110000011111"))
                        .isPresent());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testReadPositionWrongSize() {
        Detection.readPosition(Bits.from("11"));
    }

    @Test
    public void testReadTimeOfLastEncodedLocationSecondsOfZero() {
        OffsetTime time = Detection.readTimeOfLastEncodedLocationSeconds(Bits.from(zeros(17)));
        assertEquals(OffsetTime.of(0, 0, 0, 0, ZoneOffset.UTC), time);
    }

    @Test
    public void testReadTimeOfLastEncodedLocationSecondsOfNonZero() {
        int c = 13;
        int n = new BigInteger(ones(c), 2).intValue();
        int hours = n / 3600;
        int mins = (n - hours * 3600) / 60;
        int secs = n - hours * 3600 - mins * 60;
        OffsetTime time = Detection
                .readTimeOfLastEncodedLocationSeconds(Bits.from(zeros(17 - c) + ones(c)));
        assertEquals(OffsetTime.of(hours, mins, secs, 0, ZoneOffset.UTC), time);
    }

    @Test
    public void testReadRotatingFieldEltDtInFlightEmergency() {
        Bits b = Bits.from("0001" // rotating field type
                + "00001111111111111" // time
                + "0001111010" // altitude
                + "0100" // triggering event
                + "01" // gnss status
                + "10" // battery
                + "000000000"); // spare
        EltDtInFlightEmergency a = (EltDtInFlightEmergency) Detection.readRotatingField(b);
        assertEquals(OffsetTime.of(2, 16, 31, 0, ZoneOffset.UTC),
                a.timeOfLastEncodedLocationSeconds());
        assertEquals(1952 - 400, a.altitudeEncodedLocationMetres());
        assertEquals(TriggeringEvent.G_SWITCH_OR_DEFORMATION_ACTIVATION, a.triggeringEvent());
        assertEquals(GnssStatus.LOCATION_2D, a.gnssStatus());
        assertEquals(Range.create(66, RangeEndType.EXCLUSIVE, 100, RangeEndType.INCLUSIVE),
                a.remainingBatteryCapacityPercent().get());
    }

    @Test
    public void testReadBeaconFeedbackWithGalileo() {
        Bits b = Bits.from("1" + "0" + "0001" + "10" + ones(14) + zeros(11));
        BeaconFeedback a = Detection.readBeaconFeadback(b, RlsProvider.GALILEO).get();
        assertTrue(a.rlmType1FeedbackReceived());
        assertFalse(a.rlmType2FeedbackReceived());
        assertEquals(RlsType.ACKNOWLEDGEMENT_SERVICE, a.rlsType());
        assertEquals(ones(13), a.shortRlmParametersBitString().get());
    }

    @Test
    public void testReadBeaconFeedbackWithGalileoTestService() {
        Bits b = Bits.from("1" + "0" + "1111" + "10" + ones(14) + zeros(11));
        BeaconFeedback a = Detection.readBeaconFeadback(b, RlsProvider.GALILEO).get();
        assertTrue(a.rlmType1FeedbackReceived());
        assertFalse(a.rlmType2FeedbackReceived());
        assertEquals(RlsType.TEST_SERVICE, a.rlsType());
        assertEquals("10" + ones(13), a.shortRlmParametersBitString().get());
    }

    @Test
    public void testReadBeaconFeedbackWithGalileoOtherService() {
        Bits b = Bits.from("1" + "0" + "1110" + "10" + ones(14) + zeros(11));
        BeaconFeedback a = Detection.readBeaconFeadback(b, RlsProvider.GALILEO).get();
        assertTrue(a.rlmType1FeedbackReceived());
        assertFalse(a.rlmType2FeedbackReceived());
        assertEquals(RlsType.OTHER, a.rlsType());
        assertEquals("10" + ones(13), a.shortRlmParametersBitString().get());
    }

    @Test
    public void testReadBeaconFeedbackWithGlonass() {
        Bits b = Bits.from("1" + "0" + "1110" + "10" + ones(14) + zeros(11));
        assertFalse(Detection.readBeaconFeadback(b, RlsProvider.GLONASS).isPresent());
    }

    @Test
    public void testReadRotatingFieldRls() {
        Bits b = Bits.from("0010" // rotating field id
                + "00" // empty
                + "1" //
                + "0" //
                + zeros(4) //
                + "010" // glonass
                + "1" //
                + "0" //
                + "1110" //
                + "10" + ones(14) + zeros(11));
        Rls a = (Rls) Detection.readRotatingField(b);
        assertTrue(a.canProcessAutomaticallyGeneratedAckRlmType1());
        assertFalse(a.canProcessManuallyGeneratedRlm());
        assertEquals(RlsProvider.GLONASS, a.rlsProvider());
        assertFalse(a.beaconFeedback().isPresent());
    }

    @Test
    public void testReadLocationPositiveLatPositiveLon() {
        Bits bits = Bits.from("00110000110010110000110001000101000000100011111");
        EncodedGnssPosition p = Detection.readPosition(bits).get();
        assertEquals(48.79315185546875, p.lat(), 0.0000001);
        assertEquals(69.00875854492188, p.lon(), 0.0000001);
    }

    @Test
    public void testReadLocationNegativeLatNegativeLon() {
        Bits bits = Bits.from("10110000110010110000110101000101000000100011111");
        EncodedGnssPosition p = Detection.readPosition(bits).get();
        assertEquals(-48.79315185546875, p.lat(), 0.0000001);
        assertEquals(-69.00875854492188, p.lon(), 0.0000001);
    }

    private static String leftPadWithZeros(String s, int length) {
        while (s.length() < length) {
            s = "0" + s;
        }
        return s;
    }

    private static String ones(int n) {
        return Util.repeat('1', n);
    }

    private static String zeros(int n) {
        return Util.repeat('0', n);
    }

}
